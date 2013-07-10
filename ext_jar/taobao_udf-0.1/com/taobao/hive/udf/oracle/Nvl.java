package com.taobao.hive.udf.oracle;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Nvl extends UDF
{
  Text result = new Text();

  public Text evaluate(String n1, String n2)
  {
    try
    {
      if (n1 == null) {
        this.result.set(n2 + "");
        return this.result;
      }
      this.result.set(n1 + "");
      return this.result;
    } catch (Exception e) {
    }
    return null;
  }

  public static void main(String[] args)
  {
    Nvl test = new Nvl();
    System.out.println(test.evaluate("222", "333"));
    System.out.println(test.evaluate(null, "333"));
    System.out.println(test.evaluate("222", null));
    System.out.println(test.evaluate(null, null));
  }
}