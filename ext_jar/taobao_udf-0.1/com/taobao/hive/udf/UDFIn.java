package com.taobao.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils.ReturnObjectInspectorResolver;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.BooleanWritable;

public class UDFIn extends GenericUDF
{
  private ObjectInspector[] argumentOIs;
  BooleanWritable bw = new BooleanWritable();

  GenericUDFUtils.ReturnObjectInspectorResolver conversionHelper = null;
  ObjectInspector compareOI;

  public ObjectInspector initialize(ObjectInspector[] arguments)
    throws UDFArgumentException
  {
    if (arguments.length < 2) {
      throw new UDFArgumentLengthException(new StringBuilder().append("The function IN requires at least two arguments, got ").append(arguments.length).toString());
    }

    this.argumentOIs = arguments;

    this.conversionHelper = new GenericUDFUtils.ReturnObjectInspectorResolver(true);

    for (ObjectInspector oi : arguments) {
      if (!this.conversionHelper.update(oi)) {
        StringBuilder sb = new StringBuilder();
        sb.append("The arguments for IN should be the same type! Types are: {");
        sb.append(arguments[0].getTypeName());
        sb.append(" IN (");
        for (int i = 1; i < arguments.length; i++) {
          if (i != 1) {
            sb.append(", ");
          }
          sb.append(arguments[i].getTypeName());
        }
        sb.append(")}");
        throw new UDFArgumentException(sb.toString());
      }
    }
    this.compareOI = this.conversionHelper.get();

    return PrimitiveObjectInspectorFactory.writableBooleanObjectInspector;
  }

  public Object evaluate(GenericUDF.DeferredObject[] arguments) throws HiveException
  {
    this.bw.set(false);

    if (arguments[0].get() == null) {
      return null;
    }

    for (int i = 1; i < arguments.length; i++) {
      if (ObjectInspectorUtils.compare(this.conversionHelper.convertIfNecessary(arguments[0].get(), this.argumentOIs[0]), this.compareOI, this.conversionHelper.convertIfNecessary(arguments[i].get(), this.argumentOIs[i]), this.compareOI) != 0)
      {
        continue;
      }

      this.bw.set(true);
      return this.bw;
    }

    for (int i = 1; i < arguments.length; i++) {
      if (arguments[i].get() == null) {
        return null;
      }
    }
    return this.bw;
  }

  public String getDisplayString(String[] children)
  {
    assert (children.length >= 2);
    StringBuilder sb = new StringBuilder();

    sb.append("(");
    sb.append(children[0]);
    sb.append(") ");
    sb.append("IN (");
    for (int i = 1; i < children.length; i++) {
      sb.append(children[i]);
      if (i + 1 != children.length) {
        sb.append(", ");
      }
    }
    sb.append(")");
    return sb.toString();
  }
}