package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFLength extends UDF
{
  Text result = new Text();

  public Text evaluate(String value)
  {
    try
    {
      int valueLength = 0;
      String chinese = "[Α-￥]";

      for (int i = 0; i < value.length(); i++)
      {
        String temp = value.substring(i, i + 1);

        if (temp.matches(chinese))
        {
          valueLength += 2;
        }
        else {
          valueLength++;
        }
      }
      this.result.set(valueLength + "");
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFLength test = new UDFLength();

    System.out.println(test.evaluate("111��33����6-��"));
  }
}