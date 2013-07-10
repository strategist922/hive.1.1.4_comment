package com.taobao.hive.udf;

import com.taobao.hive.util.UDFStringUtil;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(name="get_auctionid", value="_FUNC_(url) - get auction_id from a URL \n_FUNC_(url,refer,flag) - get auction_id from a URL (flag=1,means need to validate url by item rule,flag=0 means no)", extended="URL examples: \n http://item.taobao.com/item.htm?id=1234 \nhttp://item.tmall.com/item.htm?id=1234 \nhttp://item.lp.taobao.com/item.htm?id=1234 \nhttp://item.wmlp.com/item.htm?id=1234 \nhttp://spu.tmall.com/spu-[]")
public class UDFGetAuctionID2 extends UDF
{
  private URL url = null;
  private Pattern pattern = null;
  private String lastUrl = null;
  private Text result = null;
  private Pattern spuPattern = null;
  private UDFGetValueFromRefer2 getvaluefromrefer = null;
  private UDFGetValueFromUrl getValueFromURL = null;
  private Text SPLITOR = null;
  private Text SPU_KEY1 = null;
  private Text SPU_KEY2 = null;
  private Text SPU_KEY_AT_ITEMID = null;
  private Pattern itemPattern = null;

  public UDFGetAuctionID2() {
    this.result = new Text();
    this.getvaluefromrefer = new UDFGetValueFromRefer2();
    this.getValueFromURL = new UDFGetValueFromUrl();
    this.SPLITOR = new Text("&");
    this.SPU_KEY1 = new Text("b2c_auction=");
    this.SPU_KEY2 = new Text("at_vtcspu=");
    this.SPU_KEY_AT_ITEMID = new Text("at_itemid=");
    initPattern();

    if (this.spuPattern == null) {
      this.spuPattern = Pattern.compile("^http://(detail\\.tmall\\.com/venus/spu_detail\\.htm|list\\.3c\\.tmall\\.com/spu|spu\\.tmall\\.com)", 2);
    }
    if (this.itemPattern == null)
      this.itemPattern = Pattern.compile("^http://(item\\.tmall\\.com/|spu\\.tmall\\.com/|item\\.taobao\\.com/|item\\.lp\\.taobao\\.com/|list\\.xie\\.tmall\\.com/spu_detail\\.htm|chaoshi\\.tmall\\.com/detail/view_detail\\.htm|list\\.3c\\.tmall\\.com/spu|item\\.wmlp\\.com|detail\\.tmall\\.com|item\\.beta\\.taobao\\.com|mall\\.hitao\\.com/item_detail\\.htm|baoxian\\.taobao\\.com/item\\.html|item\\.hitao\\.com/item|ju\\.taobao\\.com/tg/home\\.htm|ju\\.taobao\\.com/tg/life_home\\.htm)", 2);
  }

  private void initPattern()
  {
    try
    {
      Path dimPath = new Path("/group/taobao/taobao/dw/dim/");
      FileSystem fs = dimPath.getFileSystem(new Configuration());
      Path regFile = new Path("/group/taobao/taobao/dw/dim/get_auction_id_rule.txt");
      if (!fs.exists(regFile))
        throw new IOException("file not found:/group/taobao/taobao/dw/dim/get_auction_id_rule.txt");
      FSDataInputStream in = fs.open(regFile);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line = null;
      while ((line = reader.readLine()) != null) {
        String[] patterns = line.split("<--->");
        if (patterns.length != 2) {
          continue;
        }
        if ("item".equals(patterns[0])) {
          this.itemPattern = Pattern.compile(patterns[1], 2);
          System.out.println("init item pattern: " + patterns[1]);
        }
        if ("spu".equals(patterns[0])) {
          this.spuPattern = Pattern.compile(patterns[1], 2);
          System.out.println("init spu pattern: " + patterns[1]);
        }
      }
    } catch (Exception e) {
      this.itemPattern = null;
      this.spuPattern = null;
      e.printStackTrace();
    }
  }

  private void initPatternLocal() {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\reg_files\\get_auction_id_rule.txt")));
      String line = null;
      while ((line = reader.readLine()) != null) {
        String[] patterns = line.split("<--->");
        if (patterns.length != 2) {
          continue;
        }
        if ("item".equals(patterns[0])) this.itemPattern = Pattern.compile(patterns[1], 2);
        if ("spu".equals(patterns[0])) this.spuPattern = Pattern.compile(patterns[1], 2); 
      }
    }
    catch (Exception e) {
      this.itemPattern = null;
      this.spuPattern = null;
      e.printStackTrace();
    }
  }

  public Text evaluate(Text sUrl) {
    return evaluate(sUrl, null, "1");
  }

  public Text evaluate(Text sUrl, Text refer, String flag)
  {
    if ((sUrl == null) || ("".equals(sUrl.toString()))) {
      this.result.set("");
      return this.result;
    }
    String urlstr = sUrl.toString().trim().toLowerCase();
    Text returnValue = null;
    if ("1".equals(flag)) {
      Matcher m = this.itemPattern.matcher(urlstr);
      if (m.find())
        returnValue = getAuctionId(urlstr, refer);
    }
    else {
      returnValue = getAuctionId(urlstr, refer);
    }
    if (returnValue == null) {
      this.result.set("");
      return this.result;
    }
    return returnValue;
  }

  private Text getAuctionId(String urlstr, Text refer) {
    Matcher m = this.spuPattern.matcher(urlstr);
    if (m.find()) {
      return getSPUAcutionId(urlstr, refer);
    }
    Text value = getURLAuctionId(urlstr);
    if (value == null) {
      if (urlstr.indexOf("http://item.taobao.com/item_detail-") >= 0) {
        String url = urlstr.replace("http://item.taobao.com/item_detail-", "");
        int dot = url.indexOf(".");
        url = url.substring(0, dot);
        try {
          String aucid = url.split("-")[1];
          value = new Text(aucid);
          return value;
        } catch (Exception e) {
          return null;
        }
      }
      if (UDFStringUtil.indexOf(urlstr, "http://item.hitao.com/item-") >= 0) {
        int flag = UDFStringUtil.indexOf(urlstr, "http://item.hitao.com/item-");
        int index = urlstr.indexOf(".htm", flag);
        int end = index == -1 ? urlstr.length() : index;
        value = new Text();
        value.set(urlstr.substring(flag + "http://item.hitao.com/item-".length(), end));
      }
    }
    return value;
  }

  private Text getSPUAcutionId(String urlstr, Text refer)
  {
    if ((refer == null) || ("".equals(refer.toString()))) return null;
    if (UDFStringUtil.indexOf(urlstr, "http://detail.tmall.com/venus/spu_detail.htm") == 0) {
      Text spuValue = this.getvaluefromrefer.evaluate(refer, this.SPLITOR, this.SPU_KEY_AT_ITEMID);
      if ((spuValue != null) && (!"".equals(spuValue.toString()))) {
        return spuValue;
      }
      spuValue = this.getValueFromURL.evaluate(urlstr, "default_item_id");
      if ((spuValue != null) && (!"".equals(spuValue.toString()))) {
        return spuValue;
      }
      spuValue = this.getValueFromURL.evaluate(urlstr, "mallstitemid");
      return spuValue;
    }

    if (UDFStringUtil.indexOf(urlstr, "http://spu.tmall.com/") == 0)
      return this.getvaluefromrefer.evaluate(refer, this.SPLITOR, this.SPU_KEY1);
    if (UDFStringUtil.indexOf(urlstr, "http://list.3c.tmall.com/spu") == 0) {
      Text value = this.getvaluefromrefer.evaluate(refer, this.SPLITOR, this.SPU_KEY2);
      if (value == null) return null;
      String valueStr = value.toString();
      String[] values = valueStr.split("_");
      if (values.length == 4) {
        this.result.set(values[3]);
        return this.result;
      }
    }
    return null;
  }

  private Text getURLAuctionId(String urlstr) {
    if (this.pattern == null) {
      this.pattern = Pattern.compile("(&|^)(id|item_id|item_num_id|item_num)=([^&]*)");
    }
    if (!urlstr.equals(this.lastUrl)) {
      try {
        this.url = new URL(urlstr);
      } catch (Exception e) {
        return null;
      }
      this.lastUrl = urlstr;
    }
    String query = this.url.getQuery();
    if (query == null) return null;
    Matcher mather = this.pattern.matcher(query);
    if (mather.find()) {
      this.result.set(mather.group(3));
      return this.result;
    }
    return null;
  }

  public static void main(String[] args) {
    try {
      UDFGetAuctionID2 udf = new UDFGetAuctionID2();
      Text url = new Text("http://item.hitao.com/item-6934145496.htm");
      System.out.println(udf.evaluate(url).toString());
      url = new Text("http://ju.taobao.com/tg/home.htm?spm=608.1000562.0.1&item_id=15147840808");
      System.out.println(udf.evaluate(url).toString());
      url = new Text("http://ju.taobao.com/tg/life_home.htm?spm=608.1000526.10.1&item_id=17414516441");
      System.out.println(udf.evaluate(url).toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}