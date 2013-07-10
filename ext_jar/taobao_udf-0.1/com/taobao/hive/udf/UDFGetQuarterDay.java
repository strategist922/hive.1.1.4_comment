package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetQuarterDay extends UDF
{
  Text result = new Text();

  public Text evaluate(String today)
  {
    try
    {
      if (today == null) {
        return null;
      }

      if (today.endsWith(".0")) {
        today = today.substring(0, today.length() - 2);
      }

      if (today.indexOf("-") >= 0) {
        today = today.substring(0, 10);
        today = today.replaceAll("-", "");
      } else {
        today = today.substring(0, 8);
      }
      String yuefen = today.substring(4, 6);
      int yue = Integer.parseInt(yuefen);
      if ((yue >= 1) && (yue <= 3)) {
        yuefen = "01";
      }
      if ((yue >= 4) && (yue <= 6)) {
        yuefen = "04";
      }
      if ((yue >= 7) && (yue <= 9)) {
        yuefen = "07";
      }
      if ((yue >= 10) && (yue <= 12)) {
        yuefen = "10";
      }
      String QuarterDay = today.substring(0, 4) + yuefen + "01";
      this.result.set(QuarterDay);
      return this.result; } catch (Exception e) {
    }
    return null;
  }

  public static void main(String[] args)
  {
    UDFGetQuarterDay test = new UDFGetQuarterDay();
    System.out.println(test.evaluate("20090311"));
    System.out.println(test.evaluate("2010-12-11"));
    System.out.println(test.evaluate("2010-08-11 aa"));
  }
}