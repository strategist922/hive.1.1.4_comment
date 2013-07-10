package com.taobao.hive.udf;

import java.net.URLEncoder;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(name="url_encode", value="_FUNC_(expr[,codec]) - encode url by java.net.URLEncoder.", extended="Example:\n   > SELECT _FUNC_('http://taobao.com','utf-8') FROM dual;\n")
public class UDFUrlEncode extends UDF
{
  private Text result = new Text();

  public Text evaluate(String url) {
    if ((url == null) || ("".equals(url))) return null;
    return evaluate(url, "utf-8");
  }

  public Text evaluate(String url, String encode) {
    if ((encode == null) || ("".equals(encode))) encode = "utf-8"; try
    {
      this.result.set(URLEncoder.encode(url, encode.toLowerCase()));
      return this.result; } catch (Exception e) {
    }
    return null;
  }
}