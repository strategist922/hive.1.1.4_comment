package com.taobao.hive.udf;

import com.taobao.hive.udf.urlrecord.UrlRuleHandler;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetURLRecord2 extends UDF
{
  static final Log LOG = LogFactory.getLog(UDFGetURLRecord2.class.getName());

  private Text result = new Text();
  private UrlRuleHandler handler = new UrlRuleHandler();
  private static final String DEFAULT_PREFIX = "pre=";
  private static final String DEFAULT_SPLITOR = "-";

  public Text evaluate(String time, String url, String refer, String type_ids)
    throws IOException
  {
    return evaluate(time, url, refer, type_ids, "pre=");
  }

  public Text evaluate(String time, String url, String refer, String type_ids, String property_id, String flag) throws IOException {
    return evaluate(time, url, refer, type_ids, property_id, flag, "pre=");
  }

  public Text evaluate(String time, String url, String refer, String type_ids, String prefix) throws IOException {
    if ((time == null) || (url == null) || (refer == null) || (type_ids == null) || (prefix == null)) {
      this.result.set("false");
      return this.result;
    }
    this.handler.init();
    String[] ruleIds = type_ids.split("-");
    for (int i = 0; i < ruleIds.length; i++) {
      if (this.handler.validateBiz(time, url, refer, ruleIds[i], prefix)) {
        this.result.set("true");
        return this.result;
      }
    }

    this.result.set("false");
    return this.result;
  }

  public Text evaluate(String time, String url, String refer, String type_ids, String property_id, String flag, String prefix) throws IOException {
    if ((time == null) || (url == null) || (refer == null) || (type_ids == null) || (prefix == null) || (property_id == null)) {
      return null;
    }
    this.handler.init();
    String[] ruleIds = type_ids.split("-");
    for (int i = 0; i < ruleIds.length; i++) {
      String value = this.handler.getPropValue(time, url, refer, ruleIds[i], property_id, flag, prefix);
      if (value != null) {
        this.result.set(value);
        return this.result;
      }
    }

    return null;
  }
}