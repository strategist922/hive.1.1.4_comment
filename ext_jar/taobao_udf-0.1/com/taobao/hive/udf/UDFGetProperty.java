package com.taobao.hive.udf;

import com.alibaba.common.lang.StringUtil;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(name="get_prop", value="_FUNC_(property,keys) - return the values if keys is in property", extended="Example:\n    > SELECT _FUNC_(property,'20000') as value_id FROM src LIMIT 1;\n")
public class UDFGetProperty extends UDF
{
  private String[] keys = null;
  private Text result;
  private StringBuilder builder;
  private boolean keyAndValue = false;
  private String lastKey = null;

  public UDFGetProperty() {
    this.result = new Text();
    this.keyAndValue = false;
    this.builder = new StringBuilder();
  }

  public Text evaluate(String property, String key) {
    return evaluate(property, key, ";", ":");
  }

  public Text evaluate(String property, String key, String pairSplitor, String keySplitor) {
    if ((StringUtil.isBlank(property)) || (StringUtil.isBlank(key)) || (StringUtil.isBlank(pairSplitor)) || (StringUtil.isBlank(keySplitor))) {
      return null;
    }
    if ((this.keys == null) || (!key.equals(this.lastKey))) {
      this.keys = getKeys(key);
      this.lastKey = key;
      if ((this.keys == null) || (this.keys.length == 0)) return null;
      if (this.keys[0].indexOf(":") > 0) this.keyAndValue = true; else
        this.keyAndValue = false;
    }
    String[] keyValuePairs = property.split(pairSplitor);
    this.builder.delete(0, this.builder.length());
    for (int i = 0; i < this.keys.length; i++) {
      String r = checkKey(this.keys[i], keyValuePairs, keySplitor);
      if (r == null) return null;
      if (this.keyAndValue) continue; this.builder.append(r).append(",");
    }
    if (this.keyAndValue) {
      this.result.set("true");
      return this.result;
    }
    if (this.builder.length() > 0) this.builder.delete(this.builder.length() - 1, this.builder.length());
    this.result.set(this.builder.toString());
    return this.result;
  }

  private String[] getKeys(String key) {
    if (key.indexOf(",") > 0) {
      String[] keys = key.split(",");
      return keys;
    }if (key.indexOf(";") > 0) {
      String[] keys = key.split(";");
      return keys;
    }
    return new String[] { key };
  }

  private String checkKey(String key, String[] keyValuePairs, String keySplitor) {
    for (int i = 0; i < keyValuePairs.length; i++) {
      if (this.keyAndValue) {
        key = key.replace(":", keySplitor);
        if (keyValuePairs[i].trim().equals(key)) return "true"; 
      }
      else {
        String[] values = keyValuePairs[i].split(keySplitor);
        if (key.equals(values[0].trim())) {
          if (values.length == 2) return values[1].trim();
          if (keyValuePairs[i].equals(values[0])) return null;
          return "";
        }
      }
    }
    return null;
  }
}