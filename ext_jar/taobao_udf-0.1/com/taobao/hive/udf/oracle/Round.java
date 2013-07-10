package com.taobao.hive.udf.oracle;

import java.io.PrintStream;
import java.math.BigDecimal;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Round extends UDF
{
  Text result = new Text();

  public Text evaluate(String n1)
  {
    if (n1 == null)
      return null;
    try
    {
      double a = Double.parseDouble(n1);
      int result_value = (int)Math.round(a);
      this.result.set(result_value + "");
      return this.result; } catch (Exception e) {
    }
    return null;
  }

  public Text evaluate(String n1, String n2)
  {
    if ((n1 == null) || (n2 == null))
      return null;
    try
    {
      BigDecimal b = new BigDecimal(n1);
      BigDecimal one = new BigDecimal("1");
      double result_value = b.divide(one, Integer.parseInt(n2), 4).doubleValue();
      this.result.set(result_value + "");
      return this.result; } catch (Exception e) {
    }
    return null;
  }

  public static void main(String[] args)
  {
    Round test = new Round();
    System.out.println(test.evaluate("25"));
    System.out.println(test.evaluate("25.01"));
    System.out.println(test.evaluate("25.0a1"));
    System.out.println(test.evaluate("25.06"));
    System.out.println(test.evaluate("25.46"));
    System.out.println(test.evaluate("25.56"));
    System.out.println(test.evaluate(null));

    System.out.println("------------------------------------------------");
    System.out.println(test.evaluate("25.01", "0"));
    System.out.println(test.evaluate("23.01", "-1"));
    System.out.println(test.evaluate("26.01", "-1"));
    System.out.println(test.evaluate("25.01", "1"));
    System.out.println(test.evaluate("25.01", "2"));
    System.out.println(test.evaluate("25.31", "1"));
    System.out.println(test.evaluate("25.61", "1"));
    System.out.println(test.evaluate("25.65", "2"));
    System.out.println(test.evaluate("25.66", "2"));
    System.out.println(test.evaluate("25.667", "2"));
    System.out.println(test.evaluate("25.667", "2.8"));
    System.out.println(test.evaluate("26.01", "-2"));
    System.out.println(test.evaluate("76.01", "-2"));
  }
}