package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

@Description(name="Trunc", value="_FUNC_([inDateString,inDatePattern  [, outDatePattern, days, isDebugOn]]) - Returns the date corresponding to the pattern", extended="Converts the first string the date corresponding to the pattern ")
public class UDFTrunc extends UDF
{
  private static Log LOG = LogFactory.getLog(UDFTrunc.class.getName());

  Text result = new Text();

  public Text evaluate(Text inDateString)
  {
    return evaluate(inDateString, new Text("yyyyMMdd"), new Text("yyyy-MM-dd"), new IntWritable(0), new Text("N"));
  }

  public Text evaluate(Text inDateString, IntWritable days)
  {
    return evaluate(inDateString, new Text("yyyyMMdd"), new Text("yyyy-MM-dd"), days, new Text("N"));
  }

  public Text evaluate(IntWritable days)
  {
    Date dateNow = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateNowStr = dateFormat.format(dateNow);

    return evaluate(new Text(dateNowStr), new Text("yyyy-MM-dd HH:mm:ss"), new Text("yyyy-MM-dd"), days, new Text("N"));
  }

  public Text evaluate(Text inDateString, Text inDatePattern)
  {
    return evaluate(inDateString, inDatePattern, new Text("yyyy-MM-dd HH:mm:ss"), new IntWritable(0), new Text("N"));
  }

  public Text evaluate(Text inDateString, Text inDatePattern, Text outDatePattern)
  {
    return evaluate(inDateString, inDatePattern, outDatePattern, new IntWritable(0), new Text("N"));
  }

  public Text evaluate(Text inDateString, Text inDatePattern, Text outDatePattern, IntWritable days)
  {
    return evaluate(inDateString, inDatePattern, outDatePattern, days, new Text("N"));
  }

  public Text evaluate(Text inDateString, Text inDatePattern, Text outDatePattern, IntWritable days, Text isDebugOn)
  {
    if ((inDateString == null) || (inDatePattern == null) || (inDateString.toString() == "NULL")) {
      return null;
    }

    if (outDatePattern == null) {
      outDatePattern.set("yyyy-MM-dd HH:mm:ss");
    }

    if (days == null) {
      days.set(0);
    }

    if (isDebugOn == null) {
      isDebugOn.set("N");
    }

    SimpleDateFormat inDateFormat = new SimpleDateFormat(inDatePattern.toString(), Locale.ENGLISH);

    SimpleDateFormat outDateFormat = new SimpleDateFormat(outDatePattern.toString(), Locale.ENGLISH);
    try
    {
      Date inDate = inDateFormat.parse(inDateString.toString());
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(inDate);
      calendar.add(5, days.get());
      this.result.set(outDateFormat.format(calendar.getTime()));
    } catch (Exception e) {
      if (isDebugOn.toString() == "Y") {
        e.printStackTrace();
      }
      return null;
    }

    return this.result;
  }

  public static void main(String[] args) throws Exception {
    UDFTrunc trunc = new UDFTrunc();
    System.out.println(trunc.evaluate(new Text("20100611"), new IntWritable(1)));
  }
}