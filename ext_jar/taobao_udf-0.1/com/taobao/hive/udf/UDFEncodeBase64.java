package com.taobao.hive.udf;

import com.taobao.base64.Base64;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFEncodeBase64 extends UDF
{
  Text result = new Text();

  public Text evaluate(String str, String encode)
  {
    String base64str = "";
    if (str == null) {
      return null;
    }

    try
    {
      base64str = new String(Base64.encodeBase64(str.getBytes()), encode);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    this.result.set(base64str);
    return this.result;
  }

  public static void main(String[] args)
  {
    UDFEncodeBase64 test2 = new UDFEncodeBase64();

    System.out.println(test2.evaluate("aaaa", "gbk"));
  }
}