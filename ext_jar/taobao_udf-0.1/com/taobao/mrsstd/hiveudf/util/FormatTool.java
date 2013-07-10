package com.taobao.mrsstd.hiveudf.util;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatTool
{
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
  private static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

  public static Date getDateSubDay(Date time, int day) throws ParseException {
    Date date = formateDateToDate(time);
    Calendar caledar = Calendar.getInstance();
    caledar.setTime(date);
    caledar.add(5, day);
    return caledar.getTime();
  }

  public static Date formatStr2Date(String dateStr) throws ParseException
  {
    if ((dateStr == null) || ("".equals(dateStr))) {
      return formateDateToDate(new Date());
    }
    return dateFormat.parse(dateStr);
  }

  public static Date formateDateToDate(Date date)
    throws ParseException
  {
    if (date == null) {
      return dateFormat.parse(dateFormat.format(new Date()));
    }
    return dateFormat.parse(dateFormat.format(date));
  }

  public static String getDateBeforeMonth(Date date, int monthCount)
    throws ParseException
  {
    Calendar caledar = Calendar.getInstance();
    caledar.setTime(date);
    caledar.set(5, caledar.getActualMinimum(5));
    caledar.set(11, 0);
    caledar.set(12, 0);
    caledar.set(13, 0);
    caledar.add(2, monthCount);

    return dateFormat1.format(caledar.getTime());
  }

  public static void main(String[] args)
  {
    try {
      System.out.println("getDateBeforeMonth:" + getDateBeforeMonth(new Date(), -8));
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
  }
}