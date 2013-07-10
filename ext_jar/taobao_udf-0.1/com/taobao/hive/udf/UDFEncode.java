package com.taobao.hive.udf;

import java.io.UnsupportedEncodingException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.io.Text;

public class UDFEncode extends GenericUDF
{
  private ObjectInspector[] argumentOIs;
  private String charset;

  public Object evaluate(GenericUDF.DeferredObject[] arguments)
    throws HiveException
  {
    if (arguments[0].get() == null) {
      return null;
    }

    Text src = ((StringObjectInspector)this.argumentOIs[0]).getPrimitiveWritableObject(arguments[0].get());

    if (arguments.length == 2) {
      this.charset = ((StringObjectInspector)this.argumentOIs[1]).getPrimitiveJavaObject(arguments[1].get());
    }

    try
    {
      String res = new String(src.getBytes(), 0, src.getLength(), this.charset);
      return new Text(res); } catch (UnsupportedEncodingException e) {
    }
    return null;
  }

  public String getDisplayString(String[] children)
  {
    assert (children.length >= 1);
    StringBuilder sb = new StringBuilder();
    sb.append("encode(");
    for (int i = 0; i < children.length - 1; i++) {
      sb.append(children[i]).append(", ");
    }
    sb.append(children[(children.length - 1)]).append(")");
    return sb.toString();
  }

  public ObjectInspector initialize(ObjectInspector[] arguments)
    throws UDFArgumentException
  {
    if ((arguments.length > 2) || (arguments.length < 1)) {
      throw new UDFArgumentLengthException("The function encode(str, charset) needs one or two arguments.");
    }

    for (int i = 0; i < arguments.length; i++) {
      if ((arguments[i].getTypeName() == "string") || (arguments[i].getTypeName() == "void"))
        continue;
      throw new UDFArgumentTypeException(i, new StringBuilder().append("Argument ").append(i + 1).append(" of function encode must be \"").append("string").append("\", but \"").append(arguments[i].getTypeName()).append("\" was found.").toString());
    }

    this.argumentOIs = arguments;
    if (this.argumentOIs.length == 1) {
      this.charset = "GBK";
    }
    return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
  }
}