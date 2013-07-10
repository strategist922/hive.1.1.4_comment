package com.taobao.hive.udf;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetPatternGroup extends UDF
{
  Text result = new Text();

  public Text evaluate(String s, String pattern, int group)
  {
    if (s == null) {
      return null;
    }
    if (pattern == null) {
      return null;
    }

    Pattern p = null;
    try {
      p = Pattern.compile(pattern);
    } catch (Exception e) {
      return null;
    }
    Matcher m = p.matcher(s);
    String resultReturn = null;
    while (m.find())
      try {
        resultReturn = m.group(group);
      }
      catch (Exception e)
      {
      }
    if (resultReturn == null) {
      return null;
    }
    this.result.set(resultReturn);
    return this.result;
  }

  public static void main(String[] args) {
    UDFGetPatternGroup test = new UDFGetPatternGroup();
    System.out.println(test.evaluate("123,456", "(\\d+,)(\\d+)", 2));
  }
}