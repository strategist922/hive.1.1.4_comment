package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFDateCompare extends UDF
{
  public static UDFDateFormat dateformat = new UDFDateFormat();
  Text result = new Text();

  public Text evaluate(String databaseDate, String inputDate, int days)
  {
    try
    {
      if (databaseDate.endsWith(".0")) {
        databaseDate = databaseDate.substring(0, databaseDate.length() - 2);
      }

      if (databaseDate.indexOf("-") >= 0) {
        databaseDate = databaseDate.substring(0, 10);
        databaseDate = databaseDate.replaceAll("-", "");
      } else {
        databaseDate = databaseDate.substring(0, 8);
      }

      String date1 = getDay(databaseDate, 0);
      String date2 = getDay(inputDate, days);

      if (Integer.parseInt(date1) > Integer.parseInt(date2)) {
        this.result.set("1");
        return this.result;
      }if (Integer.parseInt(date1) < Integer.parseInt(date2)) {
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

  public Text evaluate(String inputdate1, String format1, String inputdate2, String format2, String flag)
  {
    try
    {
      String date1 = dateformat.evaluate(inputdate1, format1, "yyyyMMddHHmmss").toString();
      String date2 = dateformat.evaluate(inputdate2, format2, "yyyyMMddHHmmss").toString();

      DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
      Date d1 = format.parse(date1);
      Date d2 = format.parse(date2);
      double haomiao = d1.getTime() - d2.getTime();

      int tian = (int)haomiao / 1000 / 60 / 60 / 24;
      String flag_xiaoxie = flag.toLowerCase();
      if ("ss".equals(flag_xiaoxie)) {
        tian = (int)haomiao / 1000;
      }
      if ("mm".equals(flag_xiaoxie)) {
        tian = (int)haomiao / 1000 / 60;
      }
      if ("hh".equals(flag_xiaoxie)) {
        tian = (int)haomiao / 1000 / 60 / 60;
      }
      if ("day".equals(flag_xiaoxie)) {
        tian = (int)haomiao / 1000 / 60 / 60 / 24;
      }

      this.result.set(tian + "");
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static String getDay(String strBeginDate, int n)
  {
    Date dtBegin = new Date();
    try {
      dtBegin = new SimpleDateFormat("yyyyMMdd").parse(strBeginDate);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
    Calendar cld = Calendar.getInstance();
    cld.setTime(dtBegin);
    int day = cld.get(6);

    String strYear = strBeginDate.substring(0, 4);
    String strInputPath = "";
    String strDate = "";
    try {
      int nDays = 1;

      cld.setTime(dtBegin);
      cld.set(6, day + n);
      Date dt = cld.getTime();
      strDate = new SimpleDateFormat("yyyyMMdd").format(dt);
      strYear = strDate.substring(0, 4);

      strInputPath = strDate;
    }
    catch (NumberFormatException e) {
      e.printStackTrace();
    }

    return strInputPath;
  }

  public static void main(String[] args)
  {
    UDFDateCompare test = new UDFDateCompare();

    System.out.println(test.evaluate("20100208 16:43", "yyyyMMdd HH:mm", "20100209 05:32:23", "yyyyMMdd HH:mm:ss", "mm"));
  }
}