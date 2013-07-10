package com.taobao.hive.udtf;

import java.util.ArrayList;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class UDTFExplodeParallelArray extends GenericUDTF
{
  public StructObjectInspector initialize(ObjectInspector[] args)
    throws UDFArgumentException
  {
    if (args.length != 3) {
      throw new UDFArgumentLengthException("explode_parallel_array takes three argument");
    }
    if ((args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) || (args[1].getCategory() != ObjectInspector.Category.PRIMITIVE) || (args[2].getCategory() != ObjectInspector.Category.PRIMITIVE))
    {
      throw new UDFArgumentException("explode_parallel_array takes string as a parameter");
    }

    ArrayList fieldNames = new ArrayList();
    ArrayList fieldOIs = new ArrayList();
    fieldNames.add("col1");
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    fieldNames.add("col2");
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

    return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
  }

  public void process(Object[] args)
    throws HiveException
  {
    if ((args == null) || (args.length != 3))
      return;
    String[] arr2;
    String[] arr1;
    String[] arr2;
    if (args[2] == null) {
      String[] arr1 = { args[0] == null ? null : args[0].toString() };
      arr2 = new String[] { args[1] == null ? null : args[1].toString() };
    } else {
      String splitor = args[2].toString();
      arr1 = args[0] == null ? null : args[0].toString().split(splitor);
      arr2 = args[1] == null ? null : args[1].toString().split(splitor);
    }
    int len1 = arr1 == null ? 0 : arr1.length;
    int len2 = arr2 == null ? 0 : arr2.length;
    int len = len1 > len2 ? len1 : len2;

    for (int i = 0; i < len; i++) {
      String[] result = new String[2];
      result[0] = (i < len1 ? arr1[i] : null);
      result[1] = (i < len2 ? arr2[i] : null);
      forward(result);
    }
  }

  public void close()
    throws HiveException
  {
  }
}