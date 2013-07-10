package com.taobao.hive.udf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(name="str_to_date", value="_FUNC_([str[, pattern]]) - Returns the date corresponding to the pattern", extended="Converts the first string the date corresponding to the pattern ")
public class UDFStrToDate extends UDF
{
  private static Log LOG = LogFactory.getLog(UDFStrToDate.class.getName());

  private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private SimpleDateFormat internalFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  Text t = new Text();

  Text lastPatternText = new Text();

  public Text evaluate(Text dateText)
  {
    if (dateText == null) {
      return null;
    }
    try
    {
      Date date = this.formatter.parse(dateText.toString());
      this.t.set(this.internalFormatter.format(date));
      return this.t; } catch (ParseException e) {
    }
    return null;
  }

  public Text evaluate(Text dateText, Text patternText)
  {
    if ((dateText == null) || (patternText == null))
      return null;
    try
    {
      if (!patternText.equals(this.lastPatternText)) {
        this.formatter.applyPattern(patternText.toString());
        this.lastPatternText.set(patternText);
      }
    } catch (Exception e) {
      return null;
    }

    return evaluate(dateText);
  }
}