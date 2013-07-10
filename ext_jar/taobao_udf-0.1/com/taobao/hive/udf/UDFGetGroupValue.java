package com.taobao.hive.udf;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetGroupValue extends UDF
{
  Text result = new Text();

  public UDFGetGroupValue()
    throws IOException
  {
  }

  public Text evaluate(String str, String flag)
    throws IOException
  {
    try
    {
      String[] strList = str.split("}");
      String returnValue = "";
      for (int i = 0; i < strList.length; i++) {
        String duan = strList[i];

        int index = duan.indexOf(flag);

        if (index < 0) {
          continue;
        }
        String value = duan.substring(index + flag.length()).trim();
        value = value.replace("'", "");
        if (value.indexOf("key_code:229") < 0) {
          returnValue = returnValue + value + ",";
        }
      }
      returnValue = returnValue.substring(0, returnValue.length() - 1);
      this.result.set(returnValue);
      return this.result;
    } catch (Exception e) {
      e.printStackTrace();

      this.result.set("");
    }return this.result;
  }

  public static void main(String[] args)
    throws IOException
  {
    UDFGetGroupValue test = new UDFGetGroupValue();
    System.out.println(test.evaluate("1", "2"));
  }
}