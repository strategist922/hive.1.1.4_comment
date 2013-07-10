package com.taobao.hive.udf;

import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFKeyValue extends UDF
{
  static Map<String, String> mKeyValueache = new ConcurrentHashMap();

  Text result = new Text();

  public Text evaluate(String str, String keyname)
  {
    return evaluate(str, ";", ":", keyname);
  }

  public Text evaluate(String str, String split1, String split2, String keyname) {
    try {
      if ((str == null) || ("".equals(str))) return null;
      String[] values1 = str.split(split1);
      mKeyValueache.clear();
      int i = 0;
      while (i < values1.length) {
        storeKeyValue(values1[i], split2);
        i++;
      }
      String resultValue = getKeyValue(keyname);
      if (resultValue == null) return null;
      this.result.set(new Text(resultValue));
      return this.result; } catch (Exception e) {
    }
    return null;
  }

  private boolean storeKeyValue(String keyValues, String split)
  {
    if ((keyValues == null) || ("".equals(keyValues))) return false;
    if (mKeyValueache == null) mKeyValueache = new ConcurrentHashMap();
    String[] keyValueArr = keyValues.split(split);
    if (keyValueArr.length == 2) {
      mKeyValueache.put(keyValueArr[0], keyValueArr[1]);
      return true;
    }
    return false;
  }

  private String getKeyValue(String keyName) {
    if ((keyName == null) || ("".equals(keyName)) || (mKeyValueache == null) || (mKeyValueache.size() == 0)) return null;
    return (String)mKeyValueache.get(keyName);
  }

  public static void main(String[] args)
  {
    UDFKeyValue test = new UDFKeyValue();
    System.out.println(test.evaluate(";decreaseStore:1;xcard:1;isB2C:1;tf:21910;cart:1;shipping:2;pf:0;market:shoes;instPayAmount:0;", ";", ":", "tf"));
  }
}