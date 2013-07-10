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
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters.Converter;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.io.LongWritable;

@Description(name="UDFrow_rank", value="_FUNC_(col1,key1,key2) - Returns the preceding sum of col1's value partition by key1,key2")
public class UDFrow_rank extends GenericUDF
{
  private ObjectInspector[] inputOI;
  ObjectInspectorConverters.Converter cvt;
  private LongWritable Lwresult = new LongWritable(1L);
  private long Lcount = 1L;
  private long Lcount1 = 1L;
  private Object[] pre_keys;
  private boolean thesamekey = true;
  private boolean total_firstrow = true;

  public ObjectInspector initialize(ObjectInspector[] arguments)
    throws UDFArgumentException
  {
    if (arguments.length < 1)
    {
      throw new UDFArgumentLengthException("The function UDFcsum(col1,...) needs at least one arguments.");
    }

    this.inputOI = arguments;
    for (int i = 0; i < arguments.length; i++)
    {
      ObjectInspector.Category category = arguments[i].getCategory();
      if (category == ObjectInspector.Category.PRIMITIVE)
        continue;
      throw new UDFArgumentTypeException(i, new StringBuilder().append("The ").append(GenericUDFUtils.getOrdinal(i + 1)).append(" argument of function row_rank is expected to a ").append(ObjectInspector.Category.PRIMITIVE.toString().toLowerCase()).append(" type, but ").append(category.toString().toLowerCase()).append(" is found").toString());
    }

    this.pre_keys = new Object[arguments.length];
    return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
  }

  public Object evaluate(GenericUDF.DeferredObject[] arguments)
    throws HiveException
  {
    for (int idx = 1; idx < arguments.length; idx++) {
      if (arguments[idx].get() == null) {
        if (this.pre_keys[idx] != null) {
          this.thesamekey = false;
        }
        this.pre_keys[idx] = null;
      } else {
        if (PrimitiveObjectInspectorUtils.comparePrimitiveObjects(arguments[idx].get(), (PrimitiveObjectInspector)this.inputOI[idx], this.pre_keys[idx], (PrimitiveObjectInspector)this.inputOI[idx])) {
          continue;
        }
        this.thesamekey = false;
        this.pre_keys[idx] = ((PrimitiveObjectInspector)this.inputOI[idx]).copyObject(arguments[idx].get());
      }
    }

    if ((!this.thesamekey) || (this.total_firstrow)) {
      this.total_firstrow = false;
      this.thesamekey = true;
      this.Lcount1 = 1L;
      this.Lcount = this.Lcount1;
      this.pre_keys[0] = ((PrimitiveObjectInspector)this.inputOI[0]).copyObject(arguments[0].get());
    } else {
      this.thesamekey = true;
      this.Lcount1 += 1L;
      if (arguments[0].get() == null) {
        if (this.pre_keys[0] != null) {
          this.Lcount = this.Lcount1;
        }
        this.pre_keys[0] = null;
      }
      else if (!PrimitiveObjectInspectorUtils.comparePrimitiveObjects(arguments[0].get(), (PrimitiveObjectInspector)this.inputOI[0], this.pre_keys[0], (PrimitiveObjectInspector)this.inputOI[0]))
      {
        this.Lcount = this.Lcount1;
        this.pre_keys[0] = ((PrimitiveObjectInspector)this.inputOI[0]).copyObject(arguments[0].get());
      }
    }

    this.Lwresult.set(this.Lcount);
    return this.Lwresult;
  }

  public String getDisplayString(String[] children)
  {
    assert (children.length >= 2);
    StringBuilder sb = new StringBuilder();
    sb.append("UDFrow_rank(");
    for (int i = 0; i < children.length - 1; i++)
    {
      sb.append(children[i]).append(", ");
    }
    sb.append(children[(children.length - 1)]).append(")");
    return sb.toString();
  }
}