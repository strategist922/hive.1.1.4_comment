package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFTimeCompare extends UDF
{
  Text result = new Text();

  public Text evaluate(String date1, String date2, int diff)
  {
    try
    {
      String time1 = getTime(date1, 0);
      String time2 = getTime(date2, diff);

      if (Long.valueOf(time1).longValue() > Long.valueOf(time2).longValue()) {
        this.result.set("1");
        return this.result;
      }if (Long.valueOf(time1).longValue() < Long.valueOf(time2).longValue()) {
        this.result.set("-1");
        return this.result;
      }
      this.result.set("0");
      return this.result;
    }
    catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public Text evaluate(String date1, String date2)
  {
    try
    {
      String a = getTime(date1, 0);
      String b = getTime(date2, 0);
      Double c = Double.valueOf(Double.parseDouble(a) - Double.parseDouble(b));
      int s = (int)(c.doubleValue() / 1000.0D);
      this.result.set(s + "");
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static String getTime(String strBeginDate, int n)
  {
    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

    Date d = new Date();
    try {
      d = format.parse(strBeginDate);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }

    if (d == null) {
      return null;
    }

    return String.valueOf(d.getTime() + n * 1000);
  }

  public static void main(String[] args)
  {
    UDFTimeCompare test = new UDFTimeCompare();
    System.out.println(test.evaluate("20100404123454", "20100404123444", 30));
    System.out.println(test.evaluate(null, "20100404123444", 30));
    System.out.println(test.evaluate("20100404123454", "20100404123444"));
  }
}