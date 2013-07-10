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

public class UDTFExplodeEX extends GenericUDTF
{
  private String splitter = ",";

  public void close()
    throws HiveException
  {
  }

  public StructObjectInspector initialize(ObjectInspector[] args)
    throws UDFArgumentException
  {
    if ((args.length != 1) && (args.length != 2)) {
      throw new UDFArgumentLengthException("UDTFExplodeEX takes one or two argument(s): ExplodeEX(str) or ExplodeEX(str,splitter)");
    }

    if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
      throw new UDFArgumentException("UDTFExplodeEX takes string as a parameter");
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
    if ((args == null) || (args[0] == null)) {
      forward(new Object[2]);
      return;
    }if ((args.length >= 1) && (args.length <= 2)) {
      this.splitter = ((args[1] == null) || ("".equals(args[1].toString())) ? "," : args[1].toString());
    }

    String input = args[0].toString();
    String[] test = input.split(this.splitter);
    for (int i = 0; i < test.length; i++)
      try {
        String[] result = { test[i], String.valueOf(i) };
        forward(result);
      }
      catch (Exception e)
      {
      }
  }
}