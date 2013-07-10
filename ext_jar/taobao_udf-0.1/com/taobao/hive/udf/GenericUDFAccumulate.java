package com.taobao.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters.Converter;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

@Description(name="row_number", value="_FUNC_(x) - Returns the rank of a set of collector")
public class GenericUDFAccumulate extends GenericUDF
{
  ObjectInspectorConverters.Converter[] cvt;
  private Text[] pre_args;
  private DoubleWritable pre_count = new DoubleWritable(0.0D);
  private double pre_count_long = 0.0D;
  private boolean thesamekey = false;
  private boolean total_firstrow = true;
  private Text txt = new Text();
  private Text value = new Text();

  public ObjectInspector initialize(ObjectInspector[] arguments)
    throws UDFArgumentException
  {
    if (arguments.length < 1)
    {
      throw new UDFArgumentLengthException("The function row_number(str1,str2,str3,...) needs at least one arguments.");
    }

    for (int i = 0; i < arguments.length; i++)
    {
      ObjectInspector.Category category = arguments[i].getCategory();
      if (category == ObjectInspector.Category.PRIMITIVE)
        continue;
      throw new UDFArgumentTypeException(i, new StringBuilder().append("The ").append(GenericUDFUtils.getOrdinal(i + 1)).append(" argument of function ELT is expected to a ").append(ObjectInspector.Category.PRIMITIVE.toString().toLowerCase()).append(" type, but ").append(category.toString().toLowerCase()).append(" is found").toString());
    }

    this.cvt = new ObjectInspectorConverters.Converter[arguments.length];
    for (int i = 0; i < arguments.length; i++) {
      this.cvt[i] = ObjectInspectorConverters.getConverter(arguments[i], PrimitiveObjectInspectorFactory.writableStringObjectInspector);
    }

    return PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
  }

  public Object evaluate(GenericUDF.DeferredObject[] arguments)
    throws HiveException
  {
    this.value = ((Text)this.cvt[(arguments.length - 1)].convert(arguments[(arguments.length - 1)].get()));

    if (!this.total_firstrow) {
      for (int idx = 0; idx < arguments.length - 1; idx++) {
        this.txt = ((Text)this.cvt[idx].convert(arguments[idx].get()));
        if (this.txt == null) {
          if (this.pre_args[idx] == null) {
            this.thesamekey = true;
          } else {
            this.thesamekey = false;
            break;
          }
        } else {
          this.thesamekey = this.txt.equals(this.pre_args[idx]);
          if (!this.thesamekey)
            break;
        }
      }
    }
    else {
      this.total_firstrow = false;
      this.thesamekey = false;
      this.pre_args = new Text[arguments.length - 1];
    }

    if (this.thesamekey) {
      this.pre_count_long += parseDoubleValue(this.value.toString());
    } else {
      this.pre_count_long = parseDoubleValue(this.value.toString());
      for (int idx = 0; idx < arguments.length - 1; idx++) {
        this.txt = ((Text)this.cvt[idx].convert(arguments[idx].get()));
        if (this.txt == null)
          this.pre_args[idx] = new Text("");
        else {
          this.pre_args[idx] = new Text(this.txt.toString());
        }
      }
    }
    this.pre_count.set(this.pre_count_long);
    return this.pre_count;
  }

  private double parseDoubleValue(String str)
  {
    try {
      return Double.parseDouble(str); } catch (Exception e) {
    }
    return 0.0D;
  }

  public String getDisplayString(String[] children)
  {
    assert (children.length >= 2);
    StringBuilder sb = new StringBuilder();
    sb.append("row_number(");
    for (int i = 0; i < children.length - 1; i++)
    {
      sb.append(children[i]).append(", ");
    }
    sb.append(children[(children.length - 1)]).append(")");
    return sb.toString();
  }
}