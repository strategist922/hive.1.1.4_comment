package com.taobao.hive.udf;

import java.io.PrintStream;
import java.net.URLDecoder;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

@Description(name="get_auctionid", value="_FUNC_(url,level) - get domain from url(level can be 1 or 2) \n_FUNC_(url,1) - get first level domain from url \n_FUNC_(url,2) - get second level domain from url \n")
public class UDFGetDomainFromUrl extends UDF
{
  private Text result = new Text();
  private Pattern shopPattern = null;
  private Pattern wwPattern = null;
  private Pattern ipPattern = null;
  private Pattern topDomains = null;
  private UDFURLDecode decoder = null;

  public UDFGetDomainFromUrl() {
    this.decoder = new UDFURLDecode();
    this.shopPattern = Pattern.compile("^shop[0-9]+.taobao.com$", 8);
    this.wwPattern = Pattern.compile("^http://127.0.0.1|^http://[^.]*?/auth(/|[\\d]+/)");
    this.ipPattern = Pattern.compile("^([\\d]{1,3}\\.){3,3}[\\d]{1,3}($|:)");
    this.topDomains = Pattern.compile("\\.(com|net|org|edu|gov|mobi|asia|aero|int|mil|biz|info|pro|name|museum|coop|aero|idv|cat|jobs|tel|travel|me)(\\.|$)");
  }

  public Text evaluate(String url, int level)
  {
    if (url == null) {
      this.result.set("");
      return this.result;
    }
    if ((level != 1) && (level != 2)) {
      this.result.set("");
      return this.result;
    }

    if ((url.indexOf("%3A") >= 0) || (url.indexOf("%2F") >= 0)) {
      Text dUrl = this.decoder.evaluate(new Text(url));
      if (dUrl != null) {
        url = dUrl.toString();
      }
    }
    String sUrl = url;

    if (url.startsWith("http://"))
      url = url.substring(7);
    else if (url.startsWith("https://")) {
      url = url.substring(8);
    }

    int i = url.indexOf("/");
    if (i < 0) {
      i = url.length();
    }
    url = url.substring(0, i);

    int k = url.indexOf("?");
    if (k < 0) {
      k = url.length();
    }
    url = url.substring(0, k);

    int j = url.indexOf(":");
    if (j > 0) {
      url = url.substring(0, j);
    }

    if (this.ipPattern.matcher(url).find()) {
      if ("127.0.0.1".equals(url)) {
        this.result.set("wangwang");
        return this.result;
      }
      this.result.set(url);
      return this.result;
    }

    String domain = getDomainFromHost(url, level);
    if (domain == null) {
      if (this.wwPattern.matcher(sUrl).find()) {
        this.result.set("wangwang");
        return this.result;
      }
      this.result.set("");
      return this.result;
    }
    if (this.shopPattern.matcher(domain).find()) domain = "shopXX.taobao.com";
    this.result.set(domain);
    return this.result;
  }

  public String getDomainFromHost(String host, int level) {
    Matcher m = this.topDomains.matcher(host);
    if (m.find()) {
      String dh = m.toMatchResult().group();
      String[] d = host.split("\\" + dh);
      if ((d == null) || (d.length == 0)) return null;
      String pre = d[0];
      String post = null;
      if (d.length == 1) post = dh; else
        post = dh + d[1];
      d = pre.split("\\.");
      if (d.length >= level) {
        if (level == 1) return d[(d.length - 1)] + post;
        if (level == 2) return d[(d.length - 2)] + "." + d[(d.length - 1)] + post; 
      }
    }
    else {
      String[] d = host.split("\\.");
      if (d.length >= level + 1) {
        String post = "." + d[(d.length - 1)];
        if (level == 1) return d[(d.length - 2)] + post;
        if (level == 2) return d[(d.length - 3)] + "." + d[(d.length - 2)] + post;
      }
    }
    return null;
  }

  public static void main(String[] args) {
    UDFGetDomainFromUrl test = new UDFGetDomainFromUrl();
    System.out.println(test.evaluate("http://1234.www.taobao.com:908/2222.html", 3));
    System.out.println(URLDecoder.decode("https%3A%2F%2F"));
  }
}