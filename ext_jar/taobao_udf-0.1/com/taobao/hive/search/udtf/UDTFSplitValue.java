package com.taobao.hive.search.udtf;

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

public class UDTFSplitValue extends GenericUDTF
{
  private String splitchar = ",";

  public StructObjectInspector initialize(ObjectInspector[] args)
    throws UDFArgumentException
  {
    if ((args.length != 1) && (args.length != 2)) {
      throw new UDFArgumentLengthException("UDTFSplitValue takes only one or two argument");
    }

    if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
      throw new UDFArgumentException("UDTFSplitValue takes string as a parameter");
    }

    ArrayList fieldNames = new ArrayList();
    ArrayList fieldOIs = new ArrayList();

    fieldNames.add("col");
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

    return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
  }

  public void process(Object[] args)
    throws HiveException
  {
    try
    {
      if ((args != null) && (args[0] != null) && (!"".equals(args[0].toString()))) {
        String input = args[0].toString();

        if (args.length == 2)
        {
          if ((args[1] == null) || ("".equals(args[1].toString()) == true)) {
            String[] temp = { args[0].toString() };
            forward(temp);
            return;
          }

          this.splitchar = args[1].toString();
        }

        String[] line = input.split(this.splitchar);
        for (int i = 0; i < line.length; i++)
        {
          String[] temp = { line[i] };
          forward(temp);
        }
      }
    }
    catch (Exception e)
    {
    }
  }

  public void close()
    throws HiveException
  {
  }
}