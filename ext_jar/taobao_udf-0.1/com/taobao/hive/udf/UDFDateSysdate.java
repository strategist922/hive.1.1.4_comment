package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFDateSysdate extends UDF
{
  Text result = new Text();
  private String lastpreFormat;
  private DateFormat ymdhmsFormat;

  public Text evaluate(String format)
  {
    Date date = new Date();
    if ((this.lastpreFormat == null) || (!this.lastpreFormat.equals(format))) {
      this.ymdhmsFormat = new SimpleDateFormat(format);
      this.lastpreFormat = format;
    }
    this.result.set(this.ymdhmsFormat.format(date));
    return this.result;
  }

  public static void main(String[] args) {
    UDFDateSysdate test = new UDFDateSysdate();
    System.out.println(test.evaluate("yyyyMMddHHmmss"));
  }
}