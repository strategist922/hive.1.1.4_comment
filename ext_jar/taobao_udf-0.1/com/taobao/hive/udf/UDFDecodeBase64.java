package com.taobao.hive.udf;

import com.taobao.base64.Base64;
import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFDecodeBase64 extends UDF
{
  Text result = new Text();

  public Text evaluate(String str, String encode)
  {
    String base64str = "";
    try
    {
      if (str == null) {
        return null;
      }
      base64str = new String(Base64.decodeBase64(str.getBytes()), encode);
    } catch (Exception e) {
      this.result.set("err");
      return this.result;
    }
    this.result.set(base64str);
    return this.result;
  }

  public static void main(String[] args)
  {
    UDFDecodeBase64 test2 = new UDFDecodeBase64();

    System.out.println(test2.evaluate("YWFhYQ==", "gbk"));
  }
}