package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetDate extends UDF
{
  Text result = new Text();

  public Text evaluate(String riqi, int days)
  {
    try
    {
      String year = riqi.substring(0, 2);
      if ((!"18".equals(year)) && (!"19".equals(year)) && (!"20".equals(year)) && (!"21".equals(year))) {
        return null;
      }

      String date = "";
      if (riqi.endsWith(".0")) {
        riqi = riqi.substring(0, riqi.length() - 2);
      }

      if (riqi.indexOf("-") >= 0) {
        riqi = riqi.substring(0, 10);
        riqi = riqi.replaceAll("-", "");
      } else {
        riqi = riqi.substring(0, 8);
      }
      try {
        date = getDay(riqi, days);
      } catch (Exception e) {
        this.result.set("err");
        return this.result;
      }
      this.result.set(date);
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public Text evaluate(String riqi, int n, String flag)
  {
    try
    {
      String year = riqi.substring(0, 2);
      if ((!"18".equals(year)) && (!"19".equals(year)) && (!"20".equals(year)) && (!"21".equals(year)))
        return null;
    }
    catch (Exception e) {
      return null;
    }

    try
    {
      String date = "";
      if (riqi.endsWith(".0")) {
        riqi = riqi.substring(0, riqi.length() - 2);
      }

      if (riqi.indexOf("-") >= 0) {
        riqi = riqi.substring(0, 10);
        riqi = riqi.replaceAll("-", "");
      } else {
        riqi = riqi.substring(0, 8);
      }
      try
      {
        if ("first".equals(flag))
          date = getMonthStartDate(riqi, n);
        else if ("last".equals(flag))
          date = getMonthEndDate(riqi, n);
        else
          date = getMonthDate(riqi, n, flag);
      }
      catch (Exception e) {
        this.result.set("err");
        return this.result;
      }
      this.result.set(date);
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public Text evaluate(int n)
  {
    String date = getNmonthAgo(n);
    this.result.set(date);
    return this.result;
  }

  public static String getMonthDate(String riqi, int n, String flag)
  {
    Calendar ca = Calendar.getInstance();
    Date dtBegin = new Date();
    try {
      dtBegin = new SimpleDateFormat("yyyyMMdd").parse(riqi);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
    int day = Integer.parseInt(flag);
    ca.setTime(dtBegin);
    ca.add(2, n);
    ca.set(11, 0);
    ca.set(12, 0);
    ca.set(13, 0);
    ca.set(5, day);

    Date firstDate = ca.getTime();

    return ymdFormat(firstDate);
  }

  public static String getNmonthAgo(int n) {
    Calendar ca = Calendar.getInstance();
    Date dtBegin = new Date();
    ca.setTime(dtBegin);
    ca.add(2, n);
    Date firstDate = ca.getTime();

    return ymdhmsFormat(firstDate);
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

  public static String getCurrentMonthEndDate()
  {
    Calendar ca = Calendar.getInstance();

    ca.setTime(new Date());
    ca.set(11, 23);
    ca.set(12, 59);
    ca.set(13, 59);
    ca.set(5, 1);
    ca.add(2, 1);
    ca.add(5, -1);

    Date lastDate = new Date(ca.getTime().getTime());

    return ymdFormat(lastDate);
  }

  public static String getCurrentMonthStartDate()
  {
    Calendar ca = Calendar.getInstance();

    ca.setTime(new Date());
    ca.set(11, 0);
    ca.set(12, 0);
    ca.set(13, 0);
    ca.set(5, 1);

    Date firstDate = ca.getTime();

    return ymdFormat(firstDate);
  }

  public static String getMonthStartDate(String riqi, int n) {
    Calendar ca = Calendar.getInstance();
    Date dtBegin = new Date();
    try {
      dtBegin = new SimpleDateFormat("yyyyMMdd").parse(riqi);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
    ca.setTime(dtBegin);
    ca.add(2, n);
    ca.set(11, 0);
    ca.set(12, 0);
    ca.set(13, 0);
    ca.set(5, 1);

    Date firstDate = ca.getTime();

    return ymdFormat(firstDate);
  }

  public static String getMonthEndDate(String riqi, int n) {
    Calendar ca = Calendar.getInstance();
    Date dtBegin = new Date();
    try {
      dtBegin = new SimpleDateFormat("yyyyMMdd").parse(riqi);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
    ca.setTime(dtBegin);
    ca.add(2, n);
    ca.set(11, 23);
    ca.set(12, 59);
    ca.set(13, 59);
    ca.set(5, 1);
    ca.add(2, 1);
    ca.add(5, -1);

    Date lastDate = new Date(ca.getTime().getTime());

    return ymdFormat(lastDate);
  }

  public static final String ymdFormat(Date date)
  {
    if (date == null) {
      return "";
    }

    DateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
    return ymdFormat.format(date);
  }

  public static final String ymdhmsFormat(Date date) {
    if (date == null) {
      return "";
    }

    DateFormat ymdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    return ymdFormat.format(date);
  }
  public static void main(String[] args) {
    UDFGetDate test = new UDFGetDate();

    System.out.println(test.evaluate("19870900", 1, "1"));
  }
}