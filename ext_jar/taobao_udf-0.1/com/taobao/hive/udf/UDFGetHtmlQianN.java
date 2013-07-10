package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetHtmlQianN extends UDF
{
  Text result = new Text();

  public Text evaluate(String url, int n)
  {
    try
    {
      int index = url.indexOf(".htm");
      if (index < 0) {
        this.result.set("");
        return this.result;
      }
      String a = url.substring(0, index);
      index = a.lastIndexOf("/");
      String b = a.substring(index + 1, a.length());
      String[] c = b.split("-");
      if (c.length < n) {
        this.result.set("");
        return this.result;
      }
      if (n == 0) {
        this.result.set("");
        return this.result;
      }
      String d = c[(c.length - n)];

      this.result.set(d);
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFGetHtmlQianN test = new UDFGetHtmlQianN();
    System.out.println(test.evaluate("www.dfasdfsd.com22-33-44-55-66-77-88-99-22.htm", 0));
    System.out.println(test.evaluate("http://www.dfasdfsd.com/dfsdfsd/dfas/22dsffsd/22-33-44-55-66-77-88-99-22.htm", 99));
  }
}