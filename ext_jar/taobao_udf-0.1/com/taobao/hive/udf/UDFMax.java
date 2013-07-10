package com.taobao.hive.udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters.Converter;
import org.apache.hadoop.io.Text;

public class UDFMax extends YouLiangUDF
{
  Text result = new Text();
  private Text text = new Text();

  public Text evaluate(GenericUDF.DeferredObject[] arguments) throws HiveException
  {
    try
    {
      double yuansu = 0.0D;
      this.text = ((Text)this.cvt[0].convert(arguments[0].get()));
      double max = Double.parseDouble(this.text.toString());
      for (int i = 0; i < arguments.length; i++) {
        this.text = ((Text)this.cvt[i].convert(arguments[i].get()));
        try {
          yuansu = Double.parseDouble(this.text.toString());
        } catch (Exception e) {
          continue;
        }

        if (yuansu > max) {
          max = yuansu;
        }
      }
      this.result.set(max + "");
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }
}