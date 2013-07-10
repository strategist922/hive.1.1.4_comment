package com.taobao.hive.udf;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFBingUrlDecode extends UDF
{
  Text result = new Text();

  public Text evaluate(Text urlText) {
    if (urlText == null) {
      return null;
    }
    String urlReturn = null;
    String url = urlText.toString();
    try {
      urlReturn = URLDecoder.decode(url, "UTF-8");
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    this.result.set(urlReturn);
    return this.result;
  }
}