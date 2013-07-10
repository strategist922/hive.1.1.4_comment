package org.apache.hadoop.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Function
{
  public static String dateFormat(Date date, String format)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(date);
  }

  public static Date dateFormatFromStr(String date, String format) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.parse(date);
  }

  public static String format(String date, String fromFormat, String toFormcat) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat(fromFormat);
    Date tmp = dateFormat.parse(date);
    SimpleDateFormat dateFormat2 = new SimpleDateFormat(toFormcat);
    return dateFormat2.format(tmp);
  }
}