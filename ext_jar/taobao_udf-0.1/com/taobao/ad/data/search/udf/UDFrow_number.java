package com.taobao.ad.data.search.udf;

import org.apache.hadoop.hive.ql.exec.Description;
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
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

@Description(name="row_number", value="_FUNC_(x) - Returns the rank of a set of collector")
public class UDFrow_number extends GenericUDF
{
  ObjectInspectorConverters.Converter[] cvt;
  private Text[] pre_args;
  private LongWritable pre_count = new LongWritable(0L);
  private long pre_count_long = 1L;
  private boolean thesamekey = false;
  private boolean total_firstrow = true;
  private Text txt = new Text();

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

    return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
  }

  public Object evaluate(GenericUDF.DeferredObject[] arguments)
    throws HiveException
  {
    if (!this.total_firstrow) {
      for (int idx = 0; idx < arguments.length; idx++) {
        this.txt = ((Text)this.cvt[idx].convert(arguments[idx].get()));
        if (this.txt == null) {
          if (this.pre_args[idx] == null) {
            this.thesamekey = true;
          }
          else {
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
      this.pre_args = new Text[arguments.length];
    }

    if (this.thesamekey) {
      this.pre_count_long += 1L;
    } else {
      this.pre_count_long = 1L;
      for (int idx = 0; idx < arguments.length; idx++) {
        this.txt = ((Text)this.cvt[idx].convert(arguments[idx].get()));
        if (this.txt == null) {
          this.pre_args[idx] = new Text("");
        }
        else {
          this.pre_args[idx] = new Text(this.txt.toString());
        }
      }
    }
    this.pre_count.set(this.pre_count_long);
    return this.pre_count;
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