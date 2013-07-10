package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFDateFormat extends UDF
{
  private SimpleDateFormat internalFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private SimpleDateFormat lastDateFormater = null;

  private Text lastDateFormatStr = null;

  private SimpleDateFormat sdfy = null;

  private SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);

  private SimpleDateFormat lastDateFormater2 = null;
  private String lastDateFormatStr1;
  private String lastDateFormatStr2;
  Text t = new Text();

  public Text evaluate(Text date, Text datepattern)
  {
    if ((date == null) || (datepattern == null)) {
      return null;
    }

    try
    {
      String year = date.toString().substring(0, 2);
      if ((!"18".equals(year)) && (!"19".equals(year)) && (!"20".equals(year)) && (!"21".equals(year)))
        return null;
    }
    catch (Exception e) {
      return null;
    }

    if (this.lastDateFormater == null) {
      this.lastDateFormater = new SimpleDateFormat(datepattern.toString());
      this.lastDateFormatStr = datepattern;
    }

    if ((this.lastDateFormater != null) && (!datepattern.equals(this.lastDateFormatStr))) {
      this.lastDateFormater = new SimpleDateFormat(datepattern.toString());
      this.lastDateFormatStr = datepattern;
    }
    try
    {
      Date date1 = this.internalFormatter.parse(date.toString());
      this.t.set(this.lastDateFormater.format(date1));
    } catch (Exception e) {
      Date d = new Date();
      try {
        d = this.lastDateFormater.parse(date.toString());
        this.t.set(this.lastDateFormater.format(d));
      } catch (ParseException ee) {
        try {
          String date_yuanlai = date.toString();
          String year = date_yuanlai.substring(0, 4);
          String month = date_yuanlai.substring(4, 6);
          String day = date_yuanlai.substring(6, 8);
          String date_now = year + "-" + month + "-" + day;
          d = this.lastDateFormater.parse(date_now);
          this.t.set(this.lastDateFormater.format(d));
        } catch (Exception eee) {
          return null;
        }
      }
      return this.t;
    }

    return this.t;
  }

  public Text evaluate(String date, String prePattern, String nowPattern) throws ParseException
  {
    try {
      if (prePattern.equals("english")) {
        if (this.sdfy == null) {
          this.sdfy = new SimpleDateFormat(nowPattern);
        }
        String result = this.sdfy.format(this.sdf.parse(date));
        this.t.set(result);
        return this.t;
      }
      String year = date.substring(0, 2);
      if ((!"18".equals(year)) && (!"19".equals(year)) && (!"20".equals(year)) && (!"21".equals(year))) {
        return null;
      }

      if (this.lastDateFormater == null) {
        this.lastDateFormater = new SimpleDateFormat(prePattern);
        this.lastDateFormatStr1 = prePattern;
      }
      if ((this.lastDateFormater != null) && (!prePattern.equals(this.lastDateFormatStr1))) {
        this.lastDateFormater = new SimpleDateFormat(prePattern);
        this.lastDateFormatStr1 = prePattern;
      }
      if (this.lastDateFormater2 == null) {
        this.lastDateFormater2 = new SimpleDateFormat(nowPattern);
        this.lastDateFormatStr2 = nowPattern;
      }
      if ((this.lastDateFormater2 != null) && (!nowPattern.equals(this.lastDateFormatStr2))) {
        this.lastDateFormater2 = new SimpleDateFormat(nowPattern);
        this.lastDateFormatStr2 = nowPattern;
      }
      String result = this.lastDateFormater2.format(this.lastDateFormater.parse(date)).toString();

      this.t.set(result);
      return this.t;
    }
    catch (Exception e) {
      this.t.set("");
    }return this.t;
  }

  public static void main(String[] args)
    throws Exception
  {
    UDFDateFormat df = new UDFDateFormat();

    System.out.println(df.evaluate("2012-06-04", "yyyy-MM-dd", "yyyyMMdd"));
    System.out.println(df.evaluate("20120604", "yyyyMMdd", "yyyy-MM-dd"));
  }
}