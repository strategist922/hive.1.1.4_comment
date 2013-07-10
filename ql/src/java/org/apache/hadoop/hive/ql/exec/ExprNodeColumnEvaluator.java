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

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.ExprNodeColumnDesc;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.UnionObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StandardUnionObjectInspector.StandardUnion;

/**
 * This evaluator gets the column from the row object.得到特定的列值。无计算
 */
public class ExprNodeColumnEvaluator extends ExprNodeEvaluator {

  protected ExprNodeColumnDesc expr;

  transient StructObjectInspector[] inspectors;//对应下面说的递进
  transient StructField[] fields;//fields 这里递进说明一个field。[Key[0]] 和 [VALUE[2]] 
  transient boolean[] unionField;

  public ExprNodeColumnEvaluator(ExprNodeColumnDesc expr) {
    this.expr = expr;
  }
//输入的是行级别的OI
  @Override
  public ObjectInspector initialize(ObjectInspector rowInspector) throws HiveException {

    // We need to support field names like KEY.0, VALUE.1 between
    // map-reduce boundary.
    String[] names = expr.getColumn().split("\\.");// 类似VALUE. _col0
    inspectors = new StructObjectInspector[names.length];
    fields = new StructField[names.length];
    unionField = new boolean[names.length];
    int unionIndex = -1;

    for (int i = 0; i < names.length; i++) {//递进使用该实例的fields
      if (i == 0) {
        inspectors[0] = (StructObjectInspector) rowInspector;//行的OI是类似有3种fields [ key,  value,  alias]
      } else {
        if (unionIndex != -1) {
          inspectors[i] = (StructObjectInspector) (
            (UnionObjectInspector)fields[i-1].getFieldObjectInspector()).
            getObjectInspectors().get(unionIndex);
        } else {
          inspectors[i] = (StructObjectInspector) fields[i - 1]
            .getFieldObjectInspector();
        }
      }
      // to support names like _colx:1._coly         是说_colx:1 和 _coly 吗？
      String[] unionfields = names[i].split("\\:");
      fields[i] = inspectors[i].getStructFieldRef(unionfields[0]);//再进一层
      if (unionfields.length > 1) {
        unionIndex = Integer.parseInt(unionfields[1]);
        unionField[i] = true;
      } else {
        unionIndex = -1;
        unionField[i] = false;
      }
    }
    return fields[names.length - 1].getFieldObjectInspector();//取出最深的field的OI
  }
//从LazyStruct中取出fields描述的元素field值
  @Override
  public Object evaluate(Object row) throws HiveException {
    Object o = row;
    for (int i = 0; i < fields.length; i++) {//fields 这里递进说明field。[a[b[c]]]
      o = inspectors[i].getStructFieldData(o, fields[i]);//o递进求值
      if (unionField[i]) {
        o = ((StandardUnion)o).getObject();
      }
    }
    return o;
  }

}
