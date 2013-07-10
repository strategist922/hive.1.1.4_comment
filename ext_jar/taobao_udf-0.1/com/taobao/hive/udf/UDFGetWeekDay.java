package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetWeekDay extends UDF
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

      String riqi = today;
      Calendar ca = Calendar.getInstance();
      Date dtBegin = new Date();
      try {
        dtBegin = new SimpleDateFormat("yyyyMMdd").parse(riqi);
      } catch (ParseException e1) {
        e1.printStackTrace();
      }
      ca.setTime(dtBegin);
      String[] dayNames = { "7", "1", "2", "3", "4", "5", "6" };

      int dayOfWeek = ca.get(7) - 1;
      String zhouji = dayNames[dayOfWeek];

      this.result.set(zhouji);
      return this.result; } catch (Exception e) {
    }
    return null;
  }

  public static void main(String[] args)
  {
    UDFGetWeekDay test = new UDFGetWeekDay();
    System.out.println(test.evaluate("20090111"));
    System.out.println(test.evaluate("2010-01-11"));
    System.out.println(test.evaluate("2010-01-11 aa"));
  }
}