package com.taobao.hive.udf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.io.Text;

@Description(name="datediff", value="_FUNC_(date1, date2) - Returns the number of days between date1 and date2", extended="date1 and date2 are strings in the format 'yyyy-MM-dd HH:mm:ss' or 'yyyy-MM-dd'. The time parts are not ignored.If date1 is earlier than date2, the result is negative.\nExample:\n   > SELECT _FUNC_('2009-30-07', '2009-31-07') FROM src LIMIT 1;\n  1")
public class UDFDateDiff extends UDF
{
  private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

  DoubleWritable result = new DoubleWritable();

  public UDFDateDiff() {
    this.formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    this.formatter1.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  public DoubleWritable evaluate(Text dateString1, Text dateString2)
  {
    if ((dateString1 == null) || (dateString2 == null)) {
      return null;
    }

    long dateMS1 = 0L;
    try {
      dateMS1 = this.formatter.parse(dateString1.toString()).getTime();
    } catch (ParseException e) {
      try {
        dateMS1 = this.formatter1.parse(dateString1.toString()).getTime();
      } catch (ParseException ex) {
        return null;
      }
    }

    long dateMS2 = 0L;
    try {
      dateMS2 = this.formatter.parse(dateString2.toString()).getTime();
    } catch (ParseException e) {
      try {
        dateMS2 = this.formatter1.parse(dateString2.toString()).getTime();
      } catch (ParseException ex) {
        return null;
      }
    }

    long diffInMilliSeconds = dateMS1 - dateMS2;

    this.result.set(diffInMilliSeconds / 86400000.0D);
    return this.result;
  }
}