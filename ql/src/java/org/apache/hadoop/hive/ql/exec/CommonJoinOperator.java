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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.exec.persistence.AbstractRowContainer;
import org.apache.hadoop.hive.ql.exec.persistence.RowContainer;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.JoinCondDesc;
import org.apache.hadoop.hive.ql.plan.JoinDesc;
import org.apache.hadoop.hive.ql.plan.TableDesc;
import org.apache.hadoop.hive.serde2.lazybinary.LazyBinarySerDe;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.BooleanWritable;

/**
 * Join operator implementation.不同的表（Byte）下
 */
public abstract class CommonJoinOperator<T extends JoinDesc> extends
    Operator<T> implements Serializable {
  private static final long serialVersionUID = 1L;
  protected static final Log LOG = LogFactory.getLog(CommonJoinOperator.class
      .getName());

  /**
   * IntermediateObject.
   *
   */
  public static class IntermediateObject {
    ArrayList<Object>[] objs;
    int curSize;

    public IntermediateObject(ArrayList<Object>[] objs, int curSize) {
      this.objs = objs;
      this.curSize = curSize;
    }

    public ArrayList<Object>[] getObjs() {
      return objs;
    }

    public int getCurSize() {
      return curSize;
    }

    public void pushObj(ArrayList<Object> newObj) {
      objs[curSize++] = newObj;
    }

    public void popObj() {
      curSize--;
    }

    public Object topObj() {
      return objs[curSize - 1];
    }
  }

  protected transient int numAliases; // number of aliases
  /**
   * The expressions for join inputs.这里是不同表中需要select的列集合吗？而joinkey是隐含在分组机制里面？
   */
  protected transient Map<Byte, List<ExprNodeEvaluator>> joinValues;

  /**
   * The filters for join
   */
  protected transient Map<Byte, List<ExprNodeEvaluator>> joinFilters;

  /**
   * The ObjectInspectors for the join inputs.
   */
  protected transient Map<Byte, List<ObjectInspector>> joinValuesObjectInspectors;

  /**
   * The ObjectInspectors for join filters.
   */
  protected transient Map<Byte, List<ObjectInspector>> joinFilterObjectInspectors;
  /**
   * The standard ObjectInspectors for the join inputs.
   */
  protected transient Map<Byte, List<ObjectInspector>> joinValuesStandardObjectInspectors;
  /**
   * The standard ObjectInspectors for the row container.
   */
  protected transient
    Map<Byte, List<ObjectInspector>> rowContainerStandardObjectInspectors;

  protected transient Byte[] order; // order in which the results should

  // be output
  protected transient JoinCondDesc[] condn;//join的类型 左外之类的
  public transient boolean noOuterJoin;
  protected transient Object[] dummyObj; // for outer joins, contains the
  // potential nulls for the concerned
  // aliases
  protected transient RowContainer<ArrayList<Object>>[] dummyObjVectors; // empty
  // rows
  // for
  // each
  // table
  protected transient int totalSz; // total size of the composite object 所有表加起来的相关value列

  // keys are the column names. basically this maps the position of the column
  // in
  // the output of the CommonJoinOperator to the input columnInfo.
  private transient Map<Integer, Set<String>> posToAliasMap;

  transient LazyBinarySerDe[] spillTableSerDe;
  protected transient Map<Byte, TableDesc> spillTableDesc; // spill tables are
  // used if the join
  // input is too large
  // to fit in memory

  HashMap<Byte, AbstractRowContainer<ArrayList<Object>>> storage; // map b/w table alias
  // to RowContainer
  int joinEmitInterval = -1;
  int joinCacheSize = 0;
  int nextSz = 0;
  transient Byte lastAlias = null;

  transient boolean handleSkewJoin = false;

  transient boolean hasLeftSemiJoin = false;

  protected static final int NOTSKIPBIGTABLE = -1;

  public CommonJoinOperator() {
  }

  public CommonJoinOperator(CommonJoinOperator<T> clone) {
    this.joinEmitInterval = clone.joinEmitInterval;
    this.joinCacheSize = clone.joinCacheSize;
    this.nextSz = clone.nextSz;
    this.childOperators = clone.childOperators;
    this.parentOperators = clone.parentOperators;
    this.counterNames = clone.counterNames;
    this.jobProgress = clone.jobProgress;
    this.done = clone.done;
    this.operatorId = clone.operatorId;
    this.storage = clone.storage;
    this.condn = clone.condn;
    this.conf = clone.getConf();
    this.setSchema(clone.getSchema());
    this.alias = clone.alias;
    this.beginTime = clone.beginTime;
    this.inputRows = clone.inputRows;
    this.childOperatorsArray = clone.childOperatorsArray;
    this.childOperatorsTag = clone.childOperatorsTag;
    this.colExprMap = clone.colExprMap;
    this.counters = clone.counters;
    this.dummyObj = clone.dummyObj;
    this.dummyObjVectors = clone.dummyObjVectors;
    this.forwardCache = clone.forwardCache;
    this.groupKeyObject = clone.groupKeyObject;
    this.handleSkewJoin = clone.handleSkewJoin;
    this.hconf = clone.hconf;
    this.id = clone.id;
    this.inputObjInspectors = clone.inputObjInspectors;
    this.inputRows = clone.inputRows;
    this.noOuterJoin = clone.noOuterJoin;
    this.numAliases = clone.numAliases;
    this.operatorId = clone.operatorId;
    this.posToAliasMap = clone.posToAliasMap;
    this.spillTableDesc = clone.spillTableDesc;
    this.statsMap = clone.statsMap;
    this.joinFilters = clone.joinFilters;
    this.joinFilterObjectInspectors = clone.joinFilterObjectInspectors;
  }

  protected static <T extends JoinDesc> ObjectInspector getJoinOutputObjectInspector(
      Byte[] order, Map<Byte, List<ObjectInspector>> aliasToObjectInspectors,
      T conf) {
    ArrayList<ObjectInspector> structFieldObjectInspectors = new ArrayList<ObjectInspector>();
    for (Byte alias : order) {
      List<ObjectInspector> oiList = aliasToObjectInspectors.get(alias);
      structFieldObjectInspectors.addAll(oiList);
    }

    StructObjectInspector joinOutputObjectInspector = ObjectInspectorFactory
        .getStandardStructObjectInspector(conf.getOutputColumnNames(),
        structFieldObjectInspectors);
    return joinOutputObjectInspector;
  }

  Configuration hconf;
//似乎是从序列化执行plan中得到的初始化数据。生成计划时候就有
  @Override
  protected void initializeOp(Configuration hconf) throws HiveException {
    this.handleSkewJoin = conf.getHandleSkewJoin();
    this.hconf = hconf;
    totalSz = 0;
    // Map that contains the rows for each alias
    storage = new HashMap<Byte, AbstractRowContainer<ArrayList<Object>>>();

    numAliases = conf.getExprs().size();//这个conf是JoinDes

    joinValues = new HashMap<Byte, List<ExprNodeEvaluator>>();//需要select 的列

    joinFilters = new HashMap<Byte, List<ExprNodeEvaluator>>();

    order = conf.getTagOrder();
    condn = conf.getConds();
    noOuterJoin = conf.isNoOuterJoin();
//  为join的输出value 列 填充 表达式求值器。根据desc
    totalSz = JoinUtil.populateJoinKeyValue(joinValues, conf.getExprs(),//所有的输入源对应的表达式
        order,NOTSKIPBIGTABLE);

    //process join filters //  为join的过滤器 列 填充 表达式求值器。根据desc
    joinFilters = new HashMap<Byte, List<ExprNodeEvaluator>>();
    JoinUtil.populateJoinKeyValue(joinFilters, conf.getFilters(),order,NOTSKIPBIGTABLE);

//根据对应select列的求值器，求得对应OI。alias为key。这里只有2条记录，都是各个表的一个value。如果有多个value呢？
    joinValuesObjectInspectors = JoinUtil.getObjectInspectorsFromEvaluators(joinValues,
        inputObjInspectors,NOTSKIPBIGTABLE);// inputObjInspectors这里是row级别的，取出其他的列级别。多个tag
    joinFilterObjectInspectors = JoinUtil.getObjectInspectorsFromEvaluators(joinFilters,
        inputObjInspectors,NOTSKIPBIGTABLE);
    joinValuesStandardObjectInspectors = JoinUtil.getStandardObjectInspectors(
        joinValuesObjectInspectors,NOTSKIPBIGTABLE);//245行对应的writale OI,跳过大表

    if (noOuterJoin) {
      rowContainerStandardObjectInspectors = joinValuesStandardObjectInspectors;
    } else {
      Map<Byte, List<ObjectInspector>> rowContainerObjectInspectors =
        new HashMap<Byte, List<ObjectInspector>>();
      for (Byte alias : order) {
        ArrayList<ObjectInspector> rcOIs = new ArrayList<ObjectInspector>();
        rcOIs.addAll(joinValuesObjectInspectors.get(alias));
        // for each alias, add object inspector for boolean as the last element
        rcOIs.add(
            PrimitiveObjectInspectorFactory.writableBooleanObjectInspector);
        rowContainerObjectInspectors.put(alias, rcOIs);
      }
      rowContainerStandardObjectInspectors =
        JoinUtil.getStandardObjectInspectors(rowContainerObjectInspectors,NOTSKIPBIGTABLE);//263行对应的writale OI
    }

    dummyObj = new Object[numAliases];
    dummyObjVectors = new RowContainer[numAliases];

    joinEmitInterval = HiveConf.getIntVar(hconf,
        HiveConf.ConfVars.HIVEJOINEMITINTERVAL);
    joinCacheSize = HiveConf.getIntVar(hconf,
        HiveConf.ConfVars.HIVEJOINCACHESIZE);

    // construct dummy null row (indicating empty table) and
    // construct spill table serde which is used if input is too
    // large to fit into main memory.
    byte pos = 0;
    for (Byte alias : order) {
      int sz = conf.getExprs().get(alias).size();
      ArrayList<Object> nr = new ArrayList<Object>(sz);

      for (int j = 0; j < sz; j++) {
        nr.add(null);
      }

      if (!noOuterJoin) {
        // add whether the row is filtered or not
        // this value does not matter for the dummyObj
        // because the join values are already null
        nr.add(new BooleanWritable(false));
      }
      dummyObj[pos] = nr;
      // there should be only 1 dummy object in the RowContainer
      RowContainer<ArrayList<Object>> values = JoinUtil.getRowContainer(hconf,
          rowContainerStandardObjectInspectors.get((byte)pos),
          alias, 1, spillTableDesc, conf, noOuterJoin);

      values.add((ArrayList<Object>) dummyObj[pos]);
      dummyObjVectors[pos] = values;

      // if serde is null, the input doesn't need to be spilled out
      // e.g., the output columns does not contains the input table
      RowContainer rc = JoinUtil.getRowContainer(hconf,
          rowContainerStandardObjectInspectors.get((byte)pos),
          alias, joinCacheSize,spillTableDesc, conf,noOuterJoin);
      storage.put(pos, rc);

      pos++;
    }

    forwardCache = new Object[totalSz];

    outputObjInspector = getJoinOutputObjectInspector(order,
        joinValuesStandardObjectInspectors, conf);

    for( int i = 0; i < condn.length; i++ ) {
      if(condn[i].getType() == JoinDesc.LEFT_SEMI_JOIN) {
        hasLeftSemiJoin = true;
      }
    }

    LOG.info("JOIN "
        + ((StructObjectInspector) outputObjInspector).getTypeName()
        + " totalsz = " + totalSz);

  }

  transient boolean newGroupStarted = false;
  @Override
  public void startGroup() throws HiveException {
    LOG.trace("Join: Starting new group");
    newGroupStarted = true;
    for (AbstractRowContainer<ArrayList<Object>> alw : storage.values()) {
      alw.clear();
    }
  }

  protected int getNextSize(int sz) {
    // A very simple counter to keep track of join entries for a key
    if (sz >= 100000) {
      return sz + 100000;
    }

    return 2 * sz;
  }

  protected transient Byte alias;//输入源的标志位

  transient Object[] forwardCache;
// 对一行的forward操作？
  private void createForwardJoinObject(IntermediateObject intObj,
      boolean[] nullsArr) throws HiveException {
    int p = 0;
    for (int i = 0; i < numAliases; i++) {//依次遍历表
      Byte alias = order[i];
      int sz = joinValues.get(alias).size();//取得该i表应输出的列数
      if (nullsArr[i]) {//如果该表该行没关联上，则输出null
        for (int j = 0; j < sz; j++) {//每一该表的value列
          forwardCache[p++] = null;
        }
      } else {
        ArrayList<Object> obj = intObj.getObjs()[i];//取出该i表的value实际值
        for (int j = 0; j < sz; j++) {//每一列
          forwardCache[p++] = obj.get(j);
        }
      }
    }
    forward(forwardCache, outputObjInspector);//输出一条完整join value记录
  }

  private void copyOldArray(boolean[] src, boolean[] dest) {
    for (int i = 0; i < src.length; i++) {
      dest[i] = src[i];
    }
  }
//返回这次能join上的，join后的记录标志
  private ArrayList<boolean[]> joinObjectsInnerJoin(
      ArrayList<boolean[]> resNulls, ArrayList<boolean[]> inputNulls,
      ArrayList<Object> newObj, IntermediateObject intObj, int left,
      boolean newObjNull) {
    if (newObjNull) {
      return resNulls;//这里也回是null？
    }  //下面语句是当前表不为null
    Iterator<boolean[]> nullsIter = inputNulls.iterator();
    while (nullsIter.hasNext()) {
      boolean[] oldNulls = nullsIter.next();
      boolean oldObjNull = oldNulls[left];//左表是否也是null了
      if (!oldObjNull) {//如果左表有值，且当前表不为null。则输出当前表能join。
        boolean[] newNulls = new boolean[intObj.getCurSize()];
        copyOldArray(oldNulls, newNulls);
        newNulls[oldNulls.length] = false;
        resNulls.add(newNulls);
      }
    }
    return resNulls;
  }

  /**
   * Implement semi join operator.
   */
  private ArrayList<boolean[]> joinObjectsLeftSemiJoin(
      ArrayList<boolean[]> resNulls, ArrayList<boolean[]> inputNulls,
      ArrayList<Object> newObj, IntermediateObject intObj, int left,
      boolean newObjNull) {
    if (newObjNull) {
      return resNulls;
    }
    Iterator<boolean[]> nullsIter = inputNulls.iterator();
    while (nullsIter.hasNext()) {
      boolean[] oldNulls = nullsIter.next();
      boolean oldObjNull = oldNulls[left];
      if (!oldObjNull) {
        boolean[] newNulls = new boolean[intObj.getCurSize()];
        copyOldArray(oldNulls, newNulls);
        newNulls[oldNulls.length] = false;
        resNulls.add(newNulls);
      }
    }
    return resNulls;
  }

  private ArrayList<boolean[]> joinObjectsLeftOuterJoin(
      ArrayList<boolean[]> resNulls, ArrayList<boolean[]> inputNulls,
      ArrayList<Object> newObj, IntermediateObject intObj, int left,
      boolean newObjNull) {
    // newObj is null if is already null or
    // if the row corresponding to the left alias does not pass through filter
    int filterIndex = joinValues.get(order[left]).size();
    if(filterIndex < intObj.getObjs()[left].size()) {
      newObjNull = newObjNull || ((BooleanWritable) (intObj.getObjs()[left].get(filterIndex))).get();
    }

    Iterator<boolean[]> nullsIter = inputNulls.iterator();
    while (nullsIter.hasNext()) {
      boolean[] oldNulls = nullsIter.next();
      boolean oldObjNull = oldNulls[left];
      boolean[] newNulls = new boolean[intObj.getCurSize()];
      copyOldArray(oldNulls, newNulls);
      if (oldObjNull) {
        newNulls[oldNulls.length] = true;
      } else {
        newNulls[oldNulls.length] = newObjNull;
      }
      resNulls.add(newNulls);
    }
    return resNulls;
  }

  private ArrayList<boolean[]> joinObjectsRightOuterJoin(
      ArrayList<boolean[]> resNulls, ArrayList<boolean[]> inputNulls,
      ArrayList<Object> newObj, IntermediateObject intObj, int left,
      boolean newObjNull, boolean firstRow) {
    if (newObjNull) {
      return resNulls;
    }

    if (inputNulls.isEmpty() && firstRow) {
      boolean[] newNulls = new boolean[intObj.getCurSize()];
      for (int i = 0; i < intObj.getCurSize() - 1; i++) {
        newNulls[i] = true;
      }
      newNulls[intObj.getCurSize() - 1] = newObjNull;
      resNulls.add(newNulls);
      return resNulls;
    }

    boolean allOldObjsNull = firstRow;

    Iterator<boolean[]> nullsIter = inputNulls.iterator();
    while (nullsIter.hasNext()) {
      boolean[] oldNulls = nullsIter.next();
      if (!oldNulls[left]) {
        allOldObjsNull = false;
        break;
      }
    }

    // if the row does not pass through filter, all old Objects are null
    if (((BooleanWritable)newObj.get(newObj.size()-1)).get()) {
      allOldObjsNull = true;
    }
    nullsIter = inputNulls.iterator();
    while (nullsIter.hasNext()) {
      boolean[] oldNulls = nullsIter.next();
      boolean oldObjNull = oldNulls[left] || allOldObjsNull;

      if (!oldObjNull) {
        boolean[] newNulls = new boolean[intObj.getCurSize()];
        copyOldArray(oldNulls, newNulls);
        newNulls[oldNulls.length] = newObjNull;
        resNulls.add(newNulls);
      } else if (allOldObjsNull) {
        boolean[] newNulls = new boolean[intObj.getCurSize()];
        for (int i = 0; i < intObj.getCurSize() - 1; i++) {
          newNulls[i] = true;
        }
        newNulls[oldNulls.length] = newObjNull;
        resNulls.add(newNulls);
        return resNulls;
      }
    }
    return resNulls;
  }

  private ArrayList<boolean[]> joinObjectsFullOuterJoin(
      ArrayList<boolean[]> resNulls, ArrayList<boolean[]> inputNulls,
      ArrayList<Object> newObj, IntermediateObject intObj, int left,
      boolean newObjNull, boolean firstRow) {
    if (newObjNull) {
      Iterator<boolean[]> nullsIter = inputNulls.iterator();
      while (nullsIter.hasNext()) {
        boolean[] oldNulls = nullsIter.next();
        boolean[] newNulls = new boolean[intObj.getCurSize()];
        copyOldArray(oldNulls, newNulls);
        newNulls[oldNulls.length] = newObjNull;
        resNulls.add(newNulls);
      }
      return resNulls;
    }

    if (inputNulls.isEmpty() && firstRow) {
      boolean[] newNulls = new boolean[intObj.getCurSize()];
      for (int i = 0; i < intObj.getCurSize() - 1; i++) {
        newNulls[i] = true;
      }
      newNulls[intObj.getCurSize() - 1] = newObjNull;
      resNulls.add(newNulls);
      return resNulls;
    }

    boolean allOldObjsNull = firstRow;

    Iterator<boolean[]> nullsIter = inputNulls.iterator();
    while (nullsIter.hasNext()) {
      boolean[] oldNulls = nullsIter.next();
      if (!oldNulls[left]) {
        allOldObjsNull = false;
        break;
      }
    }

    // if the row does not pass through filter, all old Objects are null
    if (((BooleanWritable)newObj.get(newObj.size()-1)).get()) {
      allOldObjsNull = true;
    }
    boolean rhsPreserved = false;

    nullsIter = inputNulls.iterator();
    while (nullsIter.hasNext()) {
      boolean[] oldNulls = nullsIter.next();
      // old obj is null even if the row corresponding to the left alias
      // does not pass through filter
      boolean oldObjNull = oldNulls[left] || ((BooleanWritable)
        (intObj.getObjs()[left].get(joinValues.get(order[left]).size()))).get()
        || allOldObjsNull;
      if (!oldObjNull) {
        boolean[] newNulls = new boolean[intObj.getCurSize()];
        copyOldArray(oldNulls, newNulls);
        newNulls[oldNulls.length] = newObjNull;
        resNulls.add(newNulls);
      } else if (oldObjNull) {
        boolean[] newNulls = new boolean[intObj.getCurSize()];
        copyOldArray(oldNulls, newNulls);
        newNulls[oldNulls.length] = true;
        resNulls.add(newNulls);

        if (allOldObjsNull && !rhsPreserved) {
          newNulls = new boolean[intObj.getCurSize()];
          for (int i = 0; i < oldNulls.length; i++) {
            newNulls[i] = true;
          }
          newNulls[oldNulls.length] = false;
          resNulls.add(newNulls);
          rhsPreserved = true;
        }
      }
    }
    return resNulls;
  }

  /*
   * The new input is added to the list of existing inputs. Each entry in the
   * array of inputNulls denotes the entries in the intermediate object to be
   * used. The intermediate object is augmented with the new object, and list of
   * nulls is changed appropriately. The list will contain all non-nulls for a
   * inner join. The outer joins are processed appropriately. 返回关联结果的矩阵（新。一般只有一行？）。标记是否join后改cell为null。
   */
  private ArrayList<boolean[]> joinObjects(ArrayList<boolean[]> inputNulls,////左边表的能否join情况。如果true表示join后无值，false表示join后有值。null表示左表们row还是无值
      ArrayList<Object> newObj, IntermediateObject intObj, int joinPos,//joinPos是表标记alias
      boolean firstRow) {
    ArrayList<boolean[]> resNulls = new ArrayList<boolean[]>();
    boolean newObjNull = newObj == dummyObj[joinPos] ? true : false;//该输入row是否null object
    if (joinPos == 0) {//如果是第一张表
      if (newObjNull) {//如果该输入row是null object，无值。左值是dummyObj说明这里要过滤？
        return null;//那么返回null
      }
      //否则当前表标记为join后有值，即能关联上。
      boolean[] nulls = new boolean[1];
      nulls[0] = newObjNull;//false代表有值
      resNulls.add(nulls);
      return resNulls;
    }
  //如果是第2: 张表
    int left = condn[joinPos - 1].getLeft();// 这里是上一个表id？
    int type = condn[joinPos - 1].getType();

    // process all nulls for RIGHT and FULL OUTER JOINS
    if (((type == JoinDesc.RIGHT_OUTER_JOIN) || (type == JoinDesc.FULL_OUTER_JOIN))
        && !newObjNull && (inputNulls == null) && firstRow) {//如果左表输入是null object，当前输入不是null的话，则right ，full outerjoin 可以输出。
      boolean[] newNulls = new boolean[intObj.getCurSize()];
      for (int i = 0; i < newNulls.length - 1; i++) {
        newNulls[i] = true; //左表都标记为join后null
      }//构造一条
      newNulls[newNulls.length - 1] = false;//当前表标记为join后有值
      resNulls.add(newNulls);
      return resNulls;
    }

    if (inputNulls == null) {//如果左表们还是row无值。。？
      return null;
    }

    if (type == JoinDesc.INNER_JOIN) {
      return joinObjectsInnerJoin(resNulls, inputNulls, newObj, intObj, left,
          newObjNull);
    } else if (type == JoinDesc.LEFT_OUTER_JOIN) {
      return joinObjectsLeftOuterJoin(resNulls, inputNulls, newObj, intObj,
          left, newObjNull);
    } else if (type == JoinDesc.RIGHT_OUTER_JOIN) {
      return joinObjectsRightOuterJoin(resNulls, inputNulls, newObj, intObj,
          left, newObjNull, firstRow);
    } else if (type == JoinDesc.LEFT_SEMI_JOIN) {
      return joinObjectsLeftSemiJoin(resNulls, inputNulls, newObj, intObj,
          left, newObjNull);
    }

    assert (type == JoinDesc.FULL_OUTER_JOIN);
    return joinObjectsFullOuterJoin(resNulls, inputNulls, newObj, intObj, left,
        newObjNull, firstRow);
  }

  /*主要join的递归方法。针对一个组
   * genObject is a recursive function. For the inputs, a array of bitvectors is
   * maintained (inputNulls) where each entry denotes whether the element is to
   * be used or not (whether it is null or not). The size of the bitvector is
   * same as the number of inputs under consideration currently. When all inputs
   * are accounted for, the output is forwarded appropriately.
   */
  private void genObject(ArrayList<boolean[]> inputNulls, int aliasNum,//inputNulls是标记join到的，dfs的每一层是一样的。
      IntermediateObject intObj, boolean firstRow) throws HiveException {
    boolean childFirstRow = firstRow;
    boolean skipping = false;
    if (aliasNum < numAliases) {

      // search for match in the rhs table
      AbstractRowContainer<ArrayList<Object>> aliasRes = storage.get(order[aliasNum]);//取出组内的该表
      for (ArrayList<Object> newObj = aliasRes.first(); newObj != null; newObj = aliasRes
          .next()) {//对该表每一条记录newObj。对应join value

    	  
        // check for skipping in case of left semi join
        if (aliasNum > 0
            && condn[aliasNum - 1].getType() == JoinDesc.LEFT_SEMI_JOIN
            && newObj != dummyObj[aliasNum]) { // successful match  这里要说明newObj != dummyObj[aliasNum]是有join到值？
          skipping = true;
        }

        intObj.pushObj(newObj);//该源表记录先压入容器list

        // execute the actual join algorithm 根据不同类型的join，执行不同算法。
        ArrayList<boolean[]> newNulls = joinObjects(inputNulls, newObj, intObj,
            aliasNum, childFirstRow); // 返回能join到的记录的结果。所以用了list由于一个key可能有join出多条记录？？

        // recursively call the join the other rhs tables
        genObject(newNulls, aliasNum + 1, intObj, firstRow);//递归进入下一个表，直到输出一行join记录

        intObj.popObj();//清理上一行输出的中间记录。
        firstRow = false;

        // if left-semi-join found a match, skipping the rest of the rows in the
        // rhs table of the semijoin
        if (skipping) {
          break;
        }
      }
    } else {//当到达最后一个表+1时候，递归返回。inputnull经历了所有表后，能join到的返回值。
      if (inputNulls == null) {//如果inputNulls为null，说明这行不用输出。joinObjects的作用
        return;
      }
      Iterator<boolean[]> nullsIter = inputNulls.iterator();
      while (nullsIter.hasNext()) {//对一行的forward操作？
        boolean[] nullsVec = nullsIter.next();
        createForwardJoinObject(intObj, nullsVec);//forward
      }
    }
    
  }

  /**
   * Forward a record of join results.
   *
   * @throws HiveException
   */
  @Override
  public void endGroup() throws HiveException {
    LOG.trace("Join Op: endGroup called: numValues=" + numAliases);

    checkAndGenObject();
  }
//输出 forward。这里是unique join 处理。多表递进
  private void genUniqueJoinObject(int aliasNum, int forwardCachePos)
      throws HiveException {
    AbstractRowContainer<ArrayList<Object>> alias = storage.get(order[aliasNum]);
    for (ArrayList<Object> row = alias.first(); row != null; row = alias.next()) {//先序遍历
      int sz = joinValues.get(order[aliasNum]).size();
      int p = forwardCachePos;
      for (int j = 0; j < sz; j++) {
        forwardCache[p++] = row.get(j);
      }
      if (aliasNum == numAliases - 1) {
        forward(forwardCache, outputObjInspector);
      } else {
        genUniqueJoinObject(aliasNum + 1, p);
      }
    }
  }
//只产出一条结果的join
  private void genAllOneUniqueJoinObject()
      throws HiveException {
    int p = 0;
    for (int i = 0; i < numAliases; i++) {//遍历左右表
      int sz = joinValues.get(order[i]).size();
      ArrayList<Object> obj = storage.get(order[i]).first();//取出i表的一条记录
      for (int j = 0; j < sz; j++) {
        forwardCache[p++] = obj.get(j);//各个表的join字段，加一起。这里应该是select字段吧？
      }
    }

    forward(forwardCache, outputObjInspector);//只产出一条
  }
//执行join的地方
  protected void checkAndGenObject() throws HiveException {
    if (condn[0].getType() == JoinDesc.UNIQUE_JOIN) {//执行 unique join 。。目前忽略掉
      new IntermediateObject(new ArrayList[numAliases], 0);

      // Check if results need to be emitted.
      // Results only need to be emitted if there is a non-null entry in a table
      // that is preserved or if there are no non-null entries
      boolean preserve = false; // Will be true if there is a non-null entry
      // in a preserved table
      boolean hasNulls = false; // Will be true if there are null entries
      boolean allOne = true;
      for (int i = 0; i < numAliases; i++) { //预处理所有表的来源
        Byte alias = order[i];
        AbstractRowContainer<ArrayList<Object>> alw = storage.get(alias);

        if (alw.size() != 1) {
          allOne = false;
        }

        if (alw.size() == 0) {//如果该表该组为null，加一个假的对象
          alw.add((ArrayList<Object>) dummyObj[i]);
          hasNulls = true;
        } else if (condn[i].getPreserved()) {
          preserve = true;
        }
      }

      if (hasNulls && !preserve) {
        return;
      }

      if (allOne) {
        LOG.info("calling genAllOneUniqueJoinObject");
        genAllOneUniqueJoinObject();
        LOG.info("called genAllOneUniqueJoinObject");
      } else {
        LOG.trace("calling genUniqueJoinObject");
        genUniqueJoinObject(0, 0);
        LOG.trace("called genUniqueJoinObject");
      }
    } else {//非 unique join 。这里只是在做dummy填充，unique条件判断
      // does any result need to be emitted
      boolean mayHasMoreThanOne = false;
      boolean hasEmpty = false;
      for (int i = 0; i < numAliases; i++) {
        Byte alias = order[i];
        AbstractRowContainer<ArrayList<Object>> alw = storage.get(alias);//有多行row容器。一组。
        if (noOuterJoin) {
          if (alw.size() == 0) {//如果是inner join，且有表没记录的话，直接跳过
            LOG.trace("No data for alias=" + i);
            return;
          } else if (alw.size() > 1) {
            mayHasMoreThanOne = true;
          }
        } else {//如果是outer join
          if (alw.size() == 0) //有表没记录的话，对于outerjoin 则添加dummy ----错了。这里也是null outer join null时候，左右表都进入的地方。他们一条为一组，size==0为对方
            hasEmpty = true;
            alw.add((ArrayList<Object>) dummyObj[i]);//处理null的记录时候，默认值为dummy
          } else if (!hasEmpty && alw.size() == 1) {//只有一条记录的null的key特殊处理？这里应该是null outer join nul？
            ArrayList<Object> row = alw.first();//一行 有多列
            int numValues = joinValues.get(alias).size();// 结果应该取的列数
            if (row == dummyObj[alias]
                || (row.size() > numValues && ( ((row.get(numValues)) == null) ||
                    ((BooleanWritable) (row.get(numValues))).get() ) )) {//如果是dummy 或者是存储列数大于结果列数且最后一列是true或者null？
              hasEmpty = true;
            }
          } else {
            mayHasMoreThanOne = true;
            if (!hasEmpty) {
              int numValues = joinValues.get(alias).size();
              for (ArrayList<Object> row = alw.first(); row != null; row = alw.next()) {
                if (row == dummyObj[alias]
                    || (row.size() > numValues && ((BooleanWritable) (row.get(numValues))).get())) {
                  hasEmpty = true;
                  break;
                }
              }
            }
          }
        }
      }
      
      if (!hasEmpty && !mayHasMoreThanOne) {//执行 unique join 。。目前忽略掉
        LOG.trace("calling genAllOneUniqueJoinObject");
        genAllOneUniqueJoinObject();
        LOG.trace("called genAllOneUniqueJoinObject");
      } else if (!hasEmpty && !hasLeftSemiJoin) {//执行 unique join 。。目前忽略掉
        LOG.trace("calling genUniqueJoinObject");
        genUniqueJoinObject(0, 0);
        LOG.trace("called genUniqueJoinObject");
      } else {// 重点看这里
        LOG.trace("calling genObject");
        genObject(null, 0, new IntermediateObject(new ArrayList[numAliases], 0),//递归访问，从第一个表开始。IntermediateObject是初始容器
            true);
        LOG.trace("called genObject");
      }
    }
  }

  /**
   * Returns true if the row does not pass through filters.
   */
  protected static Boolean isFiltered(Object row,
      List<ExprNodeEvaluator> filters, List<ObjectInspector> ois)
      throws HiveException {
    // apply join filters on the row. 
    Boolean ret = false;
    for (int j = 0; j < filters.size(); j++) {
      Object condition = filters.get(j).evaluate(row);
      ret = (Boolean) ((PrimitiveObjectInspector)
          ois.get(j)).getPrimitiveJavaObject(condition);
      if (ret == null || !ret) {
        return true;
      }
    }
    return false;
  }

  /**
   * All done.
   *
   */
  @Override
  public void closeOp(boolean abort) throws HiveException {
    LOG.trace("Join Op close");
    for (AbstractRowContainer<ArrayList<Object>> alw : storage.values()) {
      if (alw != null) {
        alw.clear(); // clean up the temp files
      }
    }
    storage.clear();
  }

  @Override
  public String getName() {
    return "JOIN";
  }

  /**
   * @return the posToAliasMap
   */
  public Map<Integer, Set<String>> getPosToAliasMap() {
    return posToAliasMap;
  }

  /**
   * @param posToAliasMap
   *          the posToAliasMap to set
   */
  public void setPosToAliasMap(Map<Integer, Set<String>> posToAliasMap) {
    this.posToAliasMap = posToAliasMap;
  }

}
