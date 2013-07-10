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
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorConverter.TextConverter;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

public class ConcatEx extends GenericUDF
{
  PrimitiveObjectInspectorConverter.TextConverter cvt;

  public ObjectInspector initialize(ObjectInspector[] arguments)
    throws UDFArgumentException
  {
    if (arguments.length < 2)
    {
      throw new UDFArgumentLengthException("The function ConcatEx(str1,str2,str3,...) needs at least two arguments.");
    }

    for (int i = 0; i < arguments.length; i++)
    {
      ObjectInspector.Category category = arguments[i].getCategory();
      if (category == ObjectInspector.Category.PRIMITIVE)
        continue;
      throw new UDFArgumentTypeException(i, new StringBuilder().append("The ").append(GenericUDFUtils.getOrdinal(i + 1)).append(" argument of function ELT is expected to a ").append(ObjectInspector.Category.PRIMITIVE.toString().toLowerCase()).append(" type, but ").append(category.toString().toLowerCase()).append(" is found").toString());
    }

    this.cvt = new PrimitiveObjectInspectorConverter.TextConverter(PrimitiveObjectInspectorFactory.writableStringObjectInspector);
    return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
  }

  public Object evaluate(GenericUDF.DeferredObject[] arguments)
    throws HiveException
  {
    StringBuilder sb = new StringBuilder();
    for (int idx = 0; idx < arguments.length; idx++)
    {
      Text txt = this.cvt.convert(arguments[idx].get());
      if (txt == null) continue; sb.append(txt.toString());
    }

    return new Text(sb.toString());
  }

  public String getDisplayString(String[] children)
  {
    assert (children.length >= 2);
    StringBuilder sb = new StringBuilder();
    sb.append("ConcatEx(");
    for (int i = 0; i < children.length - 1; i++)
    {
      sb.append(children[i]).append(", ");
    }
    sb.append(children[(children.length - 1)]).append(")");
    return sb.toString();
  }
}