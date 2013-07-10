package com.taobao.hive.udf.oracle;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Nvl2 extends UDF
{
  Text result = new Text();

  public Text evaluate(String c1, String c2, String c3)
  {
    try
    {
      if (c1 == null) {
        this.result.set(c3 + "");
        return this.result;
      }
      this.result.set(c2 + "");
      return this.result;
    } catch (Exception e) {
    }
    return null;
  }

  public static void main(String[] args)
  {
    Nvl2 test = new Nvl2();
    System.out.println(test.evaluate("222", "333", "444"));
    System.out.println(test.evaluate(null, "333", "444"));
    System.out.println(test.evaluate(null, null, "444"));
    System.out.println(test.evaluate(null, "333", null));
  }
}