package com.taobao.ad.data.search.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils.ReturnObjectInspectorResolver;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters.Converter;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.io.LongWritable;

@Description(name="UDFcsum", value="_FUNC_(col1,key1,key2) - Returns the preceding sum of col1's value partition by key1,key2")
public class UDFcsum extends GenericUDF
{
  private ObjectInspector[] inputOI;
  ObjectInspectorConverters.Converter cvt;
  private LongWritable Lwresult = new LongWritable(0L);
  private DoubleWritable Dwresult = new DoubleWritable(0.0D);
  private Object[] pre_keys;
  private boolean thesamekey = true;
  private boolean total_firstrow = true;
  private GenericUDFUtils.ReturnObjectInspectorResolver returnOIResolver;

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
      throw new UDFArgumentTypeException(i, new StringBuilder().append("The ").append(GenericUDFUtils.getOrdinal(i + 1)).append(" argument of function ELT is expected to a ").append(ObjectInspector.Category.PRIMITIVE.toString().toLowerCase()).append(" type, but ").append(category.toString().toLowerCase()).append(" is found").toString());
    }

    switch (1.$SwitchMap$org$apache$hadoop$hive$serde2$objectinspector$PrimitiveObjectInspector$PrimitiveCategory[((PrimitiveObjectInspector)this.inputOI[0]).getPrimitiveCategory().ordinal()]) {
    case 1:
      break;
    case 2:
      break;
    case 3:
      break;
    case 4:
      break;
    case 5:
      break;
    default:
      throw new UDFArgumentTypeException(0, new StringBuilder().append("Only numeric type arguments are accepted but ").append(this.inputOI[0].getTypeName()).append(" is passed.").toString());
    }

    this.pre_keys = new Object[arguments.length - 1];
    this.returnOIResolver = new GenericUDFUtils.ReturnObjectInspectorResolver();

    if (!this.returnOIResolver.update(arguments[0])) {
      throw new UDFArgumentTypeException(0, new StringBuilder().append("The expressions after csum should all have the same type: \"").append(this.returnOIResolver.get().getTypeName()).append("\" is expected but \"").append(arguments[0].getTypeName()).append("\" is found").toString());
    }

    switch (1.$SwitchMap$org$apache$hadoop$hive$serde2$objectinspector$PrimitiveObjectInspector$PrimitiveCategory[((PrimitiveObjectInspector)this.inputOI[0]).getPrimitiveCategory().ordinal()]) {
    case 1:
    case 2:
    case 3:
    case 4:
      this.cvt = ObjectInspectorConverters.getConverter(arguments[0], PrimitiveObjectInspectorFactory.writableLongObjectInspector);
    case 5:
    }

    return this.returnOIResolver.get();
  }

  public Object evaluate(GenericUDF.DeferredObject[] arguments)
    throws HiveException
  {
    for (int idx = 1; idx < arguments.length; idx++) {
      if (arguments[idx].get() == null) {
        if (this.pre_keys[(idx - 1)] != null) {
          this.thesamekey = false;
        }
        this.pre_keys[(idx - 1)] = null;
      } else {
        if (PrimitiveObjectInspectorUtils.comparePrimitiveObjects(arguments[idx].get(), (PrimitiveObjectInspector)this.inputOI[idx], this.pre_keys[(idx - 1)], (PrimitiveObjectInspector)this.inputOI[idx])) {
          continue;
        }
        this.thesamekey = false;
        this.pre_keys[(idx - 1)] = ((PrimitiveObjectInspector)this.inputOI[idx]).copyObject(arguments[idx].get());
      }
    }

    if ((!this.thesamekey) || (this.total_firstrow)) {
      this.total_firstrow = false;
      this.thesamekey = true;
      switch (1.$SwitchMap$org$apache$hadoop$hive$serde2$objectinspector$PrimitiveObjectInspector$PrimitiveCategory[((PrimitiveObjectInspector)this.inputOI[0]).getPrimitiveCategory().ordinal()]) {
      case 1:
      case 2:
      case 3:
      case 4:
        if (arguments[0].get() != null)
          this.Lwresult.set(((LongWritable)this.cvt.convert(arguments[0].get())).get());
        return this.Lwresult;
      case 5:
        if (arguments[0].get() != null)
          this.Dwresult.set(((DoubleWritable)(DoubleWritable)arguments[0].get()).get());
        return this.Dwresult;
      }
    } else {
      this.thesamekey = true;
      switch (1.$SwitchMap$org$apache$hadoop$hive$serde2$objectinspector$PrimitiveObjectInspector$PrimitiveCategory[((PrimitiveObjectInspector)this.inputOI[0]).getPrimitiveCategory().ordinal()]) {
      case 1:
      case 2:
      case 3:
      case 4:
        if (arguments[0].get() != null)
          this.Lwresult.set(((LongWritable)this.cvt.convert(arguments[0].get())).get() + this.Lwresult.get());
        return this.Lwresult;
      case 5:
        if (arguments[0].get() != null)
          this.Dwresult.set(((DoubleWritable)(DoubleWritable)arguments[0].get()).get() + this.Dwresult.get());
        return this.Dwresult;
      }
    }
    return null;
  }

  public String getDisplayString(String[] children)
  {
    assert (children.length >= 2);
    StringBuilder sb = new StringBuilder();
    sb.append("UDFcsum(");
    for (int i = 0; i < children.length - 1; i++)
    {
      sb.append(children[i]).append(", ");
    }
    sb.append(children[(children.length - 1)]).append(")");
    return sb.toString();
  }
}