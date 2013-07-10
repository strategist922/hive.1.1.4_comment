package com.taobao.hive.udf.urlrecord;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.io.Text;

@Description(name="get_url_allrel", value="_FUNC_(time,url,refer[,property_id]) - Returns the all the bizid and workid with the style 'bizid:workid[:value]'")
public class UDFGetUrlRel
{
  private Text result = new Text();
  private UrlRuleHandler handler = new UrlRuleHandler();

  public Text evaluate(String time, String url, String refer) throws IOException {
    this.handler.init();
    String bizIDs = "";
    for (String bizId : this.handler.getRuleBusiness().keySet()) {
      String bizRel = this.handler.getValidateRel(time, url, refer, bizId, "pre=");
      if (!"".equals(bizRel)) bizIDs = bizIDs + bizRel;
    }
    if (!"".equals(bizIDs)) bizIDs = bizIDs.substring(0, bizIDs.length() - 1);
    this.result.set(bizIDs);
    return this.result;
  }

  public Text evaluate(String time, String url, String refer, String prop) throws IOException {
    String bizIDs = "";
    for (String bizId : this.handler.getRuleBusiness().keySet()) {
      String value = this.handler.getRelValue(time, url, refer, bizId, prop, "pre=");
      if (!"".equals(value)) bizIDs = bizIDs + value;
    }
    if (!"".equals(bizIDs)) bizIDs = bizIDs.substring(0, bizIDs.length() - 1);
    this.result.set(bizIDs);
    return this.result;
  }

  public static void main(String[] args)
  {
    try
    {
      UDFGetUrlRel biz = new UDFGetUrlRel();
      String refer = "/1.gif?cache=4124541&pre=http%3A//s.taobao.com/search%3Fq%3D%25BB%25E1%25BC%25C6%25B0%25EC%25B9%25AB%25D3%25C3%25C6%25B7%26sort%3Dcredit-desc&scr=1024x768&category=item_50012708&userid=&tid=6cef52db816997800b6a46238327ff86&channel=112&at_isb=0&at_autype=5_33517315&ad_id= 5451729810408116229";
      String url = "http://item.taobao.com/item.htm?id=8455365";
      System.out.println(biz.evaluate("20110327000000", url, refer));
      System.out.println(biz.evaluate("20110327000000", url, refer, "121"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}