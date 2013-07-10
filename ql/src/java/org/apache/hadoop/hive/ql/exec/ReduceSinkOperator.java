/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.exec;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.io.HiveKey;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.ExprNodeDesc;
import org.apache.hadoop.hive.ql.plan.ReduceSinkDesc;
import org.apache.hadoop.hive.ql.plan.TableDesc;
import org.apache.hadoop.hive.ql.plan.api.OperatorType;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.Serializer;
import org.apache.hadoop.hive.serde2.objectinspector.InspectableObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.StandardUnionObjectInspector.StandardUnion;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.UnionObjectInspector;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * Reduce Sink Operator sends output to the reduce stage.
 **/
public class ReduceSinkOperator extends TerminalOperator<ReduceSinkDesc>
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The evaluators for the key columns. Key columns decide the sort order on
   * the reducer side. Key columns are passed to the reducer in the "key". 从父操作输出的中，作为key输出的列的求值器
   */
  protected transient ExprNodeEvaluator[] keyEval;
  /**
   * The evaluators for the value columns. Value columns are passed to reducer
   * in the "value". 从父操作输出的中，作为value输出的列的求值器
   */
  protected transient ExprNodeEvaluator[] valueEval;
  /**
   * The evaluators for the partition columns (CLUSTER BY or DISTRIBUTE BY in
   * Hive language). Partition columns decide the reducer that the current row
   * goes to. Partition columns are not passed to reducer.通过语句CLUSTER BY or DISTRIBUTE BY指定，或者语义分析？joinkey ，group key。指定这个分区列
   */
  protected transient ExprNodeEvaluator[] partitionEval;

  // TODO: we use MetadataTypedColumnsetSerDe for now, till DynamicSerDe is
  // ready
  transient Serializer keySerializer;
  transient boolean keyIsText;
  transient Serializer valueSerializer;
  transient int tag;
  transient byte[] tagByte = new byte[1];
  transient protected int numDistributionKeys;//难道是说keywrapper的前几个key，为mr分区？
  transient protected int numDistinctExprs;
  transient protected boolean optimizeSkew;

  @Override
  protected void initializeOp(Configuration hconf) throws HiveException {

    try {
      keyEval = new ExprNodeEvaluator[conf.getKeyCols().size()];
      int i = 0;
      for (ExprNodeDesc e : conf.getKeyCols()) {
        keyEval[i++] = ExprNodeEvaluatorFactory.get(e);//输入的keywrapper中，能作为mr分区的列？
      }

      numDistributionKeys = conf.getNumDistributionKeys();
      distinctColIndices = conf.getDistinctColumnIndices();
      numDistinctExprs = distinctColIndices.size();

      valueEval = new ExprNodeEvaluator[conf.getValueCols().size()];
      i = 0;
      for (ExprNodeDesc e : conf.getValueCols()) {
        valueEval[i++] = ExprNodeEvaluatorFactory.get(e);
      }

      partitionEval = new ExprNodeEvaluator[conf.getPartitionCols().size()];
      i = 0;
      for (ExprNodeDesc e : conf.getPartitionCols()) {
        partitionEval[i++] = ExprNodeEvaluatorFactory.get(e);
      }

      optimizeSkew = conf.isOptimizeSkew();
      tag = conf.getTag();
      tagByte[0] = (byte) tag;
      LOG.info("Using tag = " + tag);

      TableDesc keyTableDesc = conf.getKeySerializeInfo();
      keySerializer = (Serializer) keyTableDesc.getDeserializerClass()
          .newInstance();
      keySerializer.initialize(null, keyTableDesc.getProperties());
      keyIsText = keySerializer.getSerializedClass().equals(Text.class);//针对key能序列化为Text的优化？275行

      TableDesc valueTableDesc = conf.getValueSerializeInfo();
      valueSerializer = (Serializer) valueTableDesc.getDeserializerClass()
          .newInstance();
      valueSerializer.initialize(null, valueTableDesc.getProperties());

      firstRow = true;
      initializeChildren(hconf);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  transient InspectableObject tempInspectableObject = new InspectableObject();
  transient HiveKey keyWritable = new HiveKey();
  transient Writable value;

  transient StructObjectInspector keyObjectInspector;
  transient StructObjectInspector valueObjectInspector;
  transient ObjectInspector[] partitionObjectInspectors;
  transient ObjectInspector[] keyColObjectInspectors;

  transient Object[][] cachedKeys;
  transient Object[] cachedValues;
  transient List<List<Integer>> distinctColIndices;

  boolean firstRow;

  transient Random random;

  /**
   * Initializes array of ExprNodeEvaluator. Adds Union field for distinct
   * column indices for group by.
   * Puts the return values into a StructObjectInspector with output column
   * names.
   *
   * If distinctColIndices is empty, the object inspector is same as
   * {@link Operator#initEvaluatorsAndReturnStruct(ExprNodeEvaluator[], List, ObjectInspector)}
   */
  protected static StructObjectInspector initEvaluatorsAndReturnStruct(
      ExprNodeEvaluator[] evals, List<List<Integer>> distinctColIndices,
      List<String> outputColNames,
      int length, ObjectInspector rowInspector)
      throws HiveException {
    int inspectorLen = evals.length > length ? length + 1 : evals.length;
    List<ObjectInspector> sois = new ArrayList<ObjectInspector>(inspectorLen);

    // keys
    ObjectInspector[] fieldObjectInspectors = initEvaluators(evals, 0, length, rowInspector);
    sois.addAll(Arrays.asList(fieldObjectInspectors));

    if (evals.length > length) {
      // union keys
      List<ObjectInspector> uois = new ArrayList<ObjectInspector>();
      for (List<Integer> distinctCols : distinctColIndices) {//[[distin列。。],[distin列。。]。。] 
        List<String> names = new ArrayList<String>();
        List<ObjectInspector> eois = new ArrayList<ObjectInspector>();
        int numExprs = 0;
        for (int i : distinctCols) {
          names.add(HiveConf.getColumnInternalName(numExprs));
          eois.add(evals[i].initialize(rowInspector));
          numExprs++;
        }
        uois.add(ObjectInspectorFactory.getStandardStructObjectInspector(names, eois));
      }
      UnionObjectInspector uoi =
        ObjectInspectorFactory.getStandardUnionObjectInspector(uois);
      sois.add(uoi);
    }
    return ObjectInspectorFactory.getStandardStructObjectInspector(outputColNames, sois );
  }

  @Override
  public void processOp(Object row, int tag) throws HiveException {
    try {
      ObjectInspector rowInspector = inputObjInspectors[tag];
      if (firstRow) {//做了什么初始化？
        firstRow = false;
        keyObjectInspector = initEvaluatorsAndReturnStruct(keyEval,
            distinctColIndices,
            conf.getOutputKeyColumnNames(), numDistributionKeys, rowInspector);//构造用于输出的key的序列化OI （cachedKeys）
        valueObjectInspector = initEvaluatorsAndReturnStruct(valueEval, conf
            .getOutputValueColumnNames(), rowInspector);
        partitionObjectInspectors = initEvaluators(partitionEval, rowInspector);//是说用于MR的分区的列 
        keyColObjectInspectors = initEvaluators(keyEval, rowInspector);
        int numKeys = numDistinctExprs > 0 ? numDistinctExprs : 1;//numDistinctExprs是distinct的函数个数
        int keyLen = numDistinctExprs > 0 ? numDistributionKeys + 1 :// 如果有distinct关键字，那么发布键+1
          numDistributionKeys;
        cachedKeys = new Object[numKeys][keyLen];// distinct关键字个数，或者1。 
        cachedValues = new Object[valueEval.length];
      }

      // Evaluate the value
      for (int i = 0; i < valueEval.length; i++) {
        cachedValues[i] = valueEval[i].evaluate(row);//这里是将lazystruct对象中parse，解析出其中对应列表达式的lazystring等lazyobject
      }
      // Serialize the value 输出前的序列化。从lazyObject -> ByteWritable 特有格式的byte数组
      value = valueSerializer.serialize(cachedValues, valueObjectInspector);//valueOI 这里必须是Struct

      // Evaluate the keys
      Object[] distributionKeys = new Object[numDistributionKeys];//分区的键。包括了joinkey 或者 groupby key  和 distinct key。可以是从groupop输出的keywrapper
      for (int i = 0; i < numDistributionKeys; i++) {
        distributionKeys[i] = keyEval[i].evaluate(row);//取keywrapper的前numDistributionKeys个，作为mr分区
      }

      // Evaluate the HashCode
      int currentHashCode = 0;
      int keyHashCode[] = new int[numDistinctExprs > 0 ? numDistinctExprs : 1];
      if (partitionEval.length == 0 && numDistinctExprs == 0) {//如果sql没有分区key，使用随机的hashcode
        // If no partition cols, just distribute the data uniformly to provide
        // better
        // load balance. If the requirement is to have a single reducer, we
        // should set
        // the number of reducers to 1.
        // Use a constant seed to make the code deterministic.
        if (random == null) {
          random = new Random(12345);
        }
        currentHashCode = random.nextInt();
      } else {
        for (int i = 0; i < partitionEval.length; i++) {
          Object o = partitionEval[i].evaluate(row);//取出lazystruct的lazystring。对应列
          currentHashCode = currentHashCode * 31
              + ObjectInspectorUtils.hashCode(o, partitionObjectInspectors[i]);//加入分组列hash，依次
        }
      }

      if (numDistinctExprs > 0) {//这里和distinct key 函数个数有关
        // with distinct key(s)
        for (int i = 0; i < numDistinctExprs; i++) {//每一个distinct关键字下
          System.arraycopy(distributionKeys, 0, cachedKeys[i], 0, numDistributionKeys);//cachedkeys[i]初始化为distributionkey
          Object[] distinctParameters =     //一个distinct后的列，可能是n个
            new Object[distinctColIndices.get(i).size()];
          keyHashCode[i] = currentHashCode;
          for (int j = 0; j < distinctParameters.length; j++) {//对于每一个distinct后的列
            distinctParameters[j] =
                keyEval[distinctColIndices.get(i).get(j)].evaluate(row);//求得该列的值
            if (optimizeSkew) {//如果优化倾斜的话， 加入该列的值hash？
              keyHashCode[i] = keyHashCode[i] * 31
                  + ObjectInspectorUtils.hashCode(distinctParameters[j],
                      keyColObjectInspectors[distinctColIndices.get(i).get(j)]);
            }
          }
          cachedKeys[i][numDistributionKeys] =
              new StandardUnion((byte)i, distinctParameters);//如果有distinct的话，cachedkeys[i][末尾]加入distinctkeys值数组的对象
        }
      } else {
        // no distinct key
        keyHashCode[0] = currentHashCode;
        System.arraycopy(distributionKeys, 0, cachedKeys[0], 0, numDistributionKeys);//缓存分区键做什么？
      }
      // Serialize the keys and append the tag
      for (int i = 0; i < cachedKeys.length; i++) {
        if (keyIsText) {
          Text key = (Text) keySerializer.serialize(cachedKeys[i],
              keyObjectInspector);
          if (tag == -1) {
            keyWritable.set(key.getBytes(), 0, key.getLength());
          } else {
            int keyLength = key.getLength();
            keyWritable.setSize(keyLength + 1);
            System.arraycopy(key.getBytes(), 0, keyWritable.get(), 0, keyLength);
            keyWritable.get()[keyLength] = tagByte[0];
          }
        } else {
          // Must be BytesWritable
          BytesWritable key = (BytesWritable) keySerializer.serialize(
              cachedKeys[i], keyObjectInspector);// 把 cachekey 序列化成writable
          if (tag == -1) {
            keyWritable.set(key.getBytes(), 0, key.getLength());
          } else {//如果有tag的话，tag写在key的最后一位
            int keyLength = key.getLength();
            keyWritable.setSize(keyLength + 1);
            System.arraycopy(key.getBytes(), 0, keyWritable.get(), 0, keyLength);
            keyWritable.get()[keyLength] = tagByte[0];
          }
        }
        keyWritable.setHashCode(keyHashCode[i]);//hashcode设置了partitionEval的hash。与distinct无关，除非控制倾斜的计划//分区hivekey的hashcode更重要,用于hadoop partitioner.也使用这里HiveKey的hashcode进行分组控制
        if (out != null) {
          out.collect(keyWritable, value);
          // Since this is a terminal operator, update counters explicitly -
          // forward is not called
          if (getJobProgress()) {
            ++outputRows;
            if (outputRows % 1000 == 0) {
              incrCounter(numOutputRowsCntr, outputRows);
              outputRows = 0;
            }
          }
        }
      }
    } catch (SerDeException e) {
      throw new HiveException(e);
    } catch (IOException e) {
      throw new HiveException(e);
    }
  }

  /**
   * @return the name of the operator
   */
  @Override
  public String getName() {
    return new String("RS");
  }

  @Override
  public int getType() {
    return OperatorType.REDUCESINK;
  }
}
