package com.taobao.hive.udf;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFReferHost3 extends UDF
{
  Text result = new Text();
  static RegexMap<String> mapTest = new RegexMap();

  public Text evaluate(String url)
  {
    if (url == null)
      return null;
    try
    {
      if (!url.equals("")) {
        String realurl = getRealUrl(url);
        if ((realurl != null) && (realurl.indexOf("tmall.com") >= 0)) {
          realurl.replace("tmall.com", "mall.taobao.com");
        }
        this.result.set(realurl);
      }

      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static String getRealUrl(String s)
  {
    if (s == null) {
      return null;
    }

    String sourceUrl = s.toString().toLowerCase();

    if (sourceUrl.startsWith("http://")) {
      sourceUrl = sourceUrl.substring(7);
    }

    if (sourceUrl.startsWith("https://")) {
      sourceUrl = sourceUrl.substring(8);
    }

    if (sourceUrl.startsWith("http%3a%2f%2f")) {
      sourceUrl = sourceUrl.substring(13);
    }

    if (sourceUrl.startsWith("https%3a%2f%2f")) {
      sourceUrl = sourceUrl.substring(14);
    }

    if (sourceUrl.startsWith("http%3a//")) {
      sourceUrl = sourceUrl.substring(9);
    }

    if (sourceUrl.startsWith("https%3a//")) {
      sourceUrl = sourceUrl.substring(10);
    }

    if (sourceUrl.startsWith("http:%2f%2f")) {
      sourceUrl = sourceUrl.substring(11);
    }
    if (sourceUrl.startsWith("https:%2f%2f")) {
      sourceUrl = sourceUrl.substring(12);
    }

    int i = sourceUrl.indexOf("/");

    if (i < 0) {
      i = sourceUrl.indexOf("%2f");
      if (i < 0) {
        i = sourceUrl.length();
      }
    }

    sourceUrl = sourceUrl.substring(0, i);

    Pattern p = Pattern.compile("shop[0-9]+.taobao.com", 8);
    if (p.matcher(sourceUrl).find()) {
      sourceUrl = "shopXX.taobao.com";
    }
    if (sourceUrl.startsWith("127.0.0.1")) {
      sourceUrl = "wangwang";
    }
    return sourceUrl;
  }

  public static void main(String[] args)
  {
    UDFReferHost3 test2 = new UDFReferHost3();

    System.out.println(test2.evaluate(null));
  }
}