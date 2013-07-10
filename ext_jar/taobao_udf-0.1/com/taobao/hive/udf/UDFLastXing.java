package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFLastXing extends UDF
{
  Text result = new Text();

  public Text evaluate(String str, String i)
  {
    try
    {
      if (str.length() >= Integer.parseInt(i)) {
        String str1 = str.substring(0, str.length() - Integer.parseInt(i));

        String str2 = "";
        for (int j = 0; j < Integer.parseInt(i); j++) {
          str2 = str2 + "*";
        }

        this.result.set(str1 + str2);
        return this.result;
      }

    }
    catch (Exception e)
    {
      this.result.set("err");
      return this.result;
    }
    return this.result;
  }

  public static void main(String[] args)
  {
    UDFLastXing test = new UDFLastXing();
    System.out.println(test.evaluate("12345557890", "3"));
  }
}