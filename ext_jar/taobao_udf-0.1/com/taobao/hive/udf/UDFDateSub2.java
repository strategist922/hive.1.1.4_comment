package com.taobao.hive.udf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class UDFDateSub2 extends UDF
{
  private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
  private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

  Text result = new Text();

  public Text evaluate(Text dateString1, IntWritable days)
  {
    if ((dateString1 == null) || (days == null)) {
      return null;
    }
    try
    {
      this.calendar.setTime(this.formatter.parse(dateString1.toString()));
      this.calendar.add(5, -days.get());
      Date newDate = this.calendar.getTime();
      this.result.set(this.formatter.format(newDate));
      return this.result;
    } catch (ParseException e) {
      try {
        this.calendar.setTime(this.formatter2.parse(dateString1.toString()));
        this.calendar.add(5, -days.get());
        Date newDate = this.calendar.getTime();
        this.result.set(this.formatter2.format(newDate));
        return this.result; } catch (ParseException ex) {
      }
    }
    return null;
  }
}