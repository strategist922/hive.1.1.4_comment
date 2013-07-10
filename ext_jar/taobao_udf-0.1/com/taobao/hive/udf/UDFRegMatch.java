package com.taobao.hive.udf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFRegMatch extends UDF
{
  Text result = new Text();

  public Text evaluate(String url, String pattern)
  {
    String returnResult = "0";
    Pattern p = Pattern.compile(pattern, 8);
    if (p.matcher(url).find()) {
      returnResult = "1";
    }

    this.result.set(returnResult);
    return this.result;
  }
}