package com.taobao.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFDistributeBy extends UDF
{
  private Text result = new Text();

  public Text evaluate(String key, int number) {
    if ((key == null) || ("".equals(key))) {
      this.result.set("0");
      return this.result;
    }
    if (number == 0) number = 2;
    int ret = (key.hashCode() & 0x7FFFFFFF) % number;
    this.result.set(ret + "");
    return this.result;
  }

  public Text evaluate(String key) {
    return evaluate(key, 2);
  }
}