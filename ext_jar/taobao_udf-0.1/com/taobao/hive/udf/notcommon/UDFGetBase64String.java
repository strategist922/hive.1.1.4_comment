package com.taobao.hive.udf.notcommon;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetBase64String extends UDF
{
  Text result = new Text();

  public Text evaluate(String str)
  {
    try
    {
      if (str == null) {
        return null;
      }
      String a = str;

      int length = str.length();

      for (int i = 0; i < length; i++) {
        String s = String.valueOf(str.charAt(i));

        if (s.equals("_")) {
          String lowerStr = String.valueOf(str.charAt(i + 1));
          a = a.replace("_" + lowerStr, lowerStr.toUpperCase());
        }
      }

      this.result.set(a);
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFGetBase64String test = new UDFGetBase64String();
    System.out.println(test.evaluate("aa_k_mdddM_kk"));
    System.out.println(test.evaluate("fasfsd"));
  }
}