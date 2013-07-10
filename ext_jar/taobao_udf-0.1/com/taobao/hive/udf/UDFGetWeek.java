package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetWeek extends UDF
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

      SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
      Date date = format.parse(today);
      Calendar calendar = Calendar.getInstance();
      calendar.setFirstDayOfWeek(2);
      calendar.setTime(date);
      int week_number = calendar.get(3);

      this.result.set(week_number + "");
      return this.result; } catch (Exception e) {
    }
    return null;
  }

  public static void main(String[] args)
  {
    UDFGetWeek test = new UDFGetWeek();
    System.out.println(test.evaluate("20090111"));
    System.out.println(test.evaluate("2010-01-11"));
    System.out.println(test.evaluate("2010-01-11 aa"));
  }
}