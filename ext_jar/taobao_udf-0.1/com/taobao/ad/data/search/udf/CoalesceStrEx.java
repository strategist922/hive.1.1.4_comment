package com.taobao.ad.data.search.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters.Converter;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

public class CoalesceStrEx extends GenericUDF
{
  ObjectInspectorConverters.Converter[] converters;

  public ObjectInspector initialize(ObjectInspector[] arguments)
    throws UDFArgumentException
  {
    if (arguments.length < 2)
    {
      throw new UDFArgumentLengthException("The function CoalesceStrEx(str1,str2,str3,...) needs at least two arguments.");
    }

    for (int i = 0; i < arguments.length; i++)
    {
      ObjectInspector.Category category = arguments[i].getCategory();
      if (category == ObjectInspector.Category.PRIMITIVE)
        continue;
      throw new UDFArgumentTypeException(i, new StringBuilder().append("The ").append(GenericUDFUtils.getOrdinal(i + 1)).append(" argument of function ELT is expected to a ").append(ObjectInspector.Category.PRIMITIVE.toString().toLowerCase()).append(" type, but ").append(category.toString().toLowerCase()).append(" is found").toString());
    }

    this.converters = new ObjectInspectorConverters.Converter[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      this.converters[i] = ObjectInspectorConverters.getConverter(arguments[i], PrimitiveObjectInspectorFactory.writableStringObjectInspector);
    }

    return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
  }

  public Object evaluate(GenericUDF.DeferredObject[] arguments)
    throws HiveException
  {
    for (int idx = 0; idx < arguments.length; idx++)
    {
      Text txt = (Text)this.converters[idx].convert(arguments[idx].get());
      if ((txt != null) && (!txt.toString().equals(""))) return txt;
    }

    return null;
  }

  public String getDisplayString(String[] children)
  {
    assert (children.length >= 2);
    StringBuilder sb = new StringBuilder();
    sb.append("CoalesceStrEx(");
    for (int i = 0; i < children.length - 1; i++)
    {
      sb.append(children[i]).append(", ");
    }
    sb.append(children[(children.length - 1)]).append(")");
    return sb.toString();
  }
}