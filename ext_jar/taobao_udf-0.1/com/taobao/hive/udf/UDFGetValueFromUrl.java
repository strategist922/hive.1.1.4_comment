package com.taobao.hive.udf;

import com.taobao.hive.udf.search.GetValueFromUrl;
import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetValueFromUrl extends UDF
{
  private GetValueFromUrl search = new GetValueFromUrl();
  Text result = new Text();

  public Text evaluate(String url, String param)
  {
    if ((url == null) || (param == null) || ("".equals(url)) || ("".equals(param))) return null;
    if (param.charAt(param.length() - 1) == '=') {
      return this.search.evaluate(new Text(url), new Text(param));
    }
    return evaluateOrg(url, param);
  }

  private Text evaluateOrg(String url, String param)
  {
    try {
      String[] strList = url.split("&");
      for (int i = 0; i < strList.length; i++) {
        String canshu = strList[i];
        if (canshu.indexOf("?" + param + "=") >= 0) {
          int k = canshu.indexOf("?" + param + "=");
          String taomiValue = canshu.substring(k + param.length() + 2);
          this.result.set(taomiValue);
          return this.result;
        }
        if (canshu.indexOf(param + "=") == 0) {
          String taomiValue = canshu.substring(param.length() + 1);
          this.result.set(taomiValue);
          return this.result;
        }
      }
      this.result.set("");
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFGetValueFromUrl test = new UDFGetValueFromUrl();
    System.out.println(test.evaluate("http://item.daily.taobao.net/auction/item_detail-1-408a3e2d05ef2d261036776ad132c1dc.jhtml?ecrmPromotionId=111111&ecrmSellerId=222222&ecrmBuyerId=333333", "ecrmBuyerId"));
    System.out.println(test.evaluate(null, null));

    String param = "ecrmBuyerId=";
    System.out.println(param.charAt(param.length() - 1));
  }
}