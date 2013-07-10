package com.taobao.hive.udf.search;

import com.taobao.hive.udf.UDFGetValueFromRefer;
import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class GetValueFromUrl extends UDF
{
  private UDFGetValueFromRefer getValue = new UDFGetValueFromRefer();

  public Text evaluate(Text url, Text index_String) {
    return this.getValue.evaluate(url, CommonData.SPLIT_CHAR, index_String);
  }

  public static void main(String[] args)
  {
    GetValueFromUrl test = new GetValueFromUrl();
    System.out.println(test.evaluate(new Text("http://s.taobao.com/search?q=%C8%FD%B0%CB%C5%AE%C8%CB%BD%DA&keyword=&commend=all&ssid=s5-e&search_type=item&atype=&tracelog=&sourceId=tb.index"), new Text("sourceId=")));
  }
}