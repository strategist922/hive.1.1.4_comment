package com.taobao.ad.data.search.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;

public class GetParams extends UDF
{
  public String evaluate(String url, String key)
  {
    if ((url == null) || (url.equals("")) || (key == null) || (key.equals("")))
    {
      return "";
    }
    String str_key = key + "=";
    char ch_split = '&';

    int begin = url.indexOf("?") + 1;
    int idx_key = url.indexOf(str_key, begin);

    if (idx_key == -1) return "";
    if (idx_key > begin)
    {
      char last_ch = url.charAt(idx_key - 1);
      if (last_ch != ch_split) return "";
    }
    int idx_val = idx_key + str_key.length();
    int idx_val_end = url.indexOf(ch_split, idx_val);
    if (idx_val_end == -1) idx_val_end = url.length();

    return url.substring(idx_val, idx_val_end);
  }

  public static void main(String[] args)
  {
    GetParams p = new GetParams();
    String url = "http://www.taobao.com/get?key1=val1&key2=val2&key1=val6&key6=&key7";
    String key = "key1";
    String[] r = url.split("[?|&]");
    for (int i = 0; i < r.length; i++)
      System.out.println(r[i]);
    System.out.println("ret:" + p.evaluate(url, key));
  }
}