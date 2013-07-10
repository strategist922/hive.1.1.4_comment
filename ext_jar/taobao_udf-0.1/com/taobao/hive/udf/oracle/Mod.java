package com.taobao.hive.udf.oracle;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Mod extends UDF
{
  Text result = new Text();

  public Text evaluate(String n1, String n2)
  {
    if ((n1 == null) || (n2 == null))
      return null;
    try
    {
      int a1 = Integer.parseInt(n1);
      int a2 = Integer.parseInt(n2);
      if (a2 == 0) {
        this.result.set(a1 + "");
        return this.result;
      }
      int result_value = a1 % a2;
      this.result.set(result_value + "");
      return this.result; } catch (Exception e) {
    }
    return null;
  }

  public static void main(String[] args)
  {
    Mod test = new Mod();
    System.out.println(test.evaluate("25", "0"));
    System.out.println(test.evaluate("25", "3"));
    System.out.println(test.evaluate("25", "5"));
    System.out.println(test.evaluate("0", "25"));
    System.out.println(test.evaluate(null, "25"));
    System.out.println(test.evaluate(null, null));
    System.out.println(test.evaluate("a", "25"));
  }
}