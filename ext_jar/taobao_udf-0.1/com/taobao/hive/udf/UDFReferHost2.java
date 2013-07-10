package com.taobao.hive.udf;

import java.io.PrintStream;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFReferHost2 extends UDF
{
  Text result = new Text();
  static RegexMap<String> mapTest = new RegexMap();

  public UDFReferHost2() {
    String[] zhengze = { "\\.alibaba\\.com", "\\.alimama\\.com", "\\.alipay\\.com", "archive\\.taobao\\.com", "auction1\\.taobao\\.com", "\\.baidu\\.com", "buy\\.taobao\\.com", "click\\.p4p\\.cn\\.yahoo\\.com", "dating\\.taobao\\.com", "edyna\\..*\\.taobao\\.com", "favorite\\.taobao\\.com", "forum\\.taobao\\.com", "\\.google\\.cn", "\\.google\\.com", "itaobao\\.taobao\\.com", "item\\.taobao\\.com", "list\\.taobao\\.com", "member1\\.taobao\\.com", "my\\.taobao\\.com", "\\.qq\\.com", "rate\\.taobao\\.com", "search1\\.taobao\\.com", "search2\\.cn\\.yahoo\\.com", "search8\\.taobao\\.com", "search\\.cn\\.yahoo\\.com", "sell\\.taobao\\.com", "share\\.taobao\\.com", "shop[0-9]+\\.taobao\\.com", "\\.sina\\.com\\.cn", "\\.soso\\.com", "space\\.taobao\\.com", "store\\.taobao\\.com", "tb\\.p4p\\.cn\\.yahoo\\.com", "trade\\.taobao\\.com", "upload\\.taobao\\.com", "wuliu\\.taobao\\.com", "www\\.obuy\\.cn", "www\\.taobao\\.com", "\\.yahoo\\.com", "\\.youdao\\.com", "list\\.mall\\.taobao\\.com", "www\\.mall\\.taobao\\.com", "mservice\\.taobao\\.com", "mall\\.taobao\\.com", "click\\.simba\\.taobao\\.com", "bangpai\\.taobao\\.com", "search\\.taobao\\.com", "jianghu\\.taobao\\.com", "i\\.taobao\\.com", "huoban\\.taobao\\.com", "info\\.taobao\\.com", "bbs\\.taobao\\.com", "caipiao\\.taobao\\.com", "poster\\.taobao\\.com", "huabao\\.taobao\\.com", "service\\.taobao\\.com", "ishop\\.taobao\\.com", "madou\\.taobao\\.com", "list1\\.taobao\\.com", "s\\.taobao\\.com", "tmall\\.taobao\\.com", "list\\.tmall\\.com", "3c\\.tmall\\.com", "list\\.3c\\.tmall\\.com", "xie\\.tmall\\.com", "list\\.xie\\.tmall\\.com", "yundong\\.tmall\\.com", "jia\\.tmall\\.com", "hongkongdg\\.tmall\\.com", "beauty\\.tmall\\.com", "taolets\\.tmall\\.com", "ju\\.taobao\\.com", "www\\.tmall\\.com", "detail\\.tmall\\.com", "item\\.tmall\\.com" };

    String[] zhengze1 = { "\\.alibaba\\.com", "\\.alimama\\.com", "\\.alipay\\.com", "http://archive\\.taobao\\.com", "http://auction1\\.taobao\\.com", "\\.baidu\\.com", "http://buy\\.taobao\\.com", "http://click\\.p4p\\.cn\\.yahoo\\.com", "http://dating\\.taobao\\.com", "http://edyna\\..*\\.taobao\\.com", "http://favorite\\.taobao\\.com", "http://forum\\.taobao\\.com", "\\.google\\.cn", "\\.google\\.com", "http://itaobao\\.taobao\\.com", "http://item\\.taobao\\.com", "http://list\\.taobao\\.com", "http://member1\\.taobao\\.com", "http://my\\.taobao\\.com", "\\.qq\\.com", "http://rate\\.taobao\\.com", "http://search1\\.taobao\\.com", "http://search2\\.cn\\.yahoo\\.com", "http://search8\\.taobao\\.com", "http://search\\.cn\\.yahoo\\.com", "http://sell\\.taobao\\.com", "http://share\\.taobao\\.com", "http://shop[0-9]+\\.taobao\\.com", "\\.sina\\.com\\.cn", "\\.soso\\.com", "http://space\\.taobao\\.com", "http://store\\.taobao\\.com", "http://tb\\.p4p\\.cn\\.yahoo\\.com", "http://trade\\.taobao\\.com", "http://upload\\.taobao\\.com", "http://wuliu\\.taobao\\.com", "http://www\\.obuy\\.cn", "http://www\\.taobao\\.com", "\\.yahoo\\.com", "\\.youdao\\.com", "http://list\\.mall\\.taobao\\.com", "http://www\\.mall\\.taobao\\.com", "http://mservice\\.taobao\\.com", "http://mall\\.taobao\\.com", "http://click\\.simba\\.taobao\\.com", "http://bangpai\\.taobao\\.com", "http://search\\.taobao\\.com", "http://jianghu\\.taobao\\.com", "http://i\\.taobao\\.com", "huoban\\.taobao\\.com", "http://info\\.taobao\\.com", "http://bbs\\.taobao\\.com", "http://caipiao\\.taobao\\.com", "http://poster\\.taobao\\.com", "http://huabao\\.taobao\\.com", "http://service\\.taobao\\.com", "http://ishop\\.taobao\\.com", "http://madou\\.taobao\\.com", "http://list1\\.taobao\\.com", "http://s\\.taobao\\.com", "3c\\.tmall\\.com", "beauty\\.tmall\\.com", "hongkongdg\\.tmall\\.com", "jia\\.tmall\\.com", "ju\\.taobao\\.com", "list\\.3c\\.tmall\\.com", "list\\.tmall\\.com", "list\\.xie\\.tmall\\.com", "taolets\\.tmall\\.com", "tmall\\.taobao\\.com", "www\\.tmall\\.com", "xie\\.tmall\\.com", "yundong\\.tmall\\.com" };

    for (int i = 0; i < zhengze.length; i++) {
      Pattern p = Pattern.compile(zhengze[i].trim(), 8);
      mapTest.put(p, zhengze[i].trim());
    }
  }

  public Text evaluate(String url)
  {
    if (url == null) {
      String referhost = "noReferhost";
      this.result.set(referhost);
      return this.result;
    }
    try {
      String referhost = "noReferhost";
      if (!url.equals("")) {
        referhost = (String)mapTest.get(url);
        if (referhost == null) {
          if (url.equals("")) {
            referhost = "noReferhost";
          }
          else {
            referhost = "otherReferhost";
          }
        }

      }

      if (referhost.startsWith("http://")) {
        referhost = referhost.replace("http://", "");
      }
      referhost = referhost.replaceAll("\\\\", "");
      this.result.set(referhost);
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFReferHost2 test2 = new UDFReferHost2();

    System.out.println(test2.evaluate("detail.tmall.com"));
  }
}