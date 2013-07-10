package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class UDFGetHtmQianN extends UDF
{
  Text result = new Text();

  public Text evaluate(Text urlText, IntWritable nW) {
    if ((urlText == null) || (nW == null))
      return null;
    String url = urlText.toString();
    int n = nW.get();
    int index = url.indexOf(".htm");
    String a = url.substring(0, index);
    index = a.lastIndexOf("/");
    String b = a.substring(index + 1, a.length());
    String[] c = b.split("-");
    int len = c.length;
    if (len < n) {
      this.result.set("-");
      return this.result;
    }
    String d = c[(len - n)];
    this.result.set(d);
    return this.result;
  }

  public static void main(String[] args) {
    UDFGetHtmQianN h = new UDFGetHtmQianN();
    System.out.println(h.evaluate(new Text("http://www.dfasdfsd.com/dfsdfsd/dfas/22dsffsd/22-33-44-55-66-77-88-99-22.htm"), new IntWritable(3)));
  }
}