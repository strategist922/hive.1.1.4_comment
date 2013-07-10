package com.taobao.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

@Description(name="convert_ip", value="_FUNC_(ip,flag) - convert ip from long/ip to ip/longflag=1 means long to ip,flag=2 means ip to long,flag=3 means string(32) to ip,flag=4 means ip to string(32)")
public class UDFIpConvert extends UDF
{
  private StringBuffer ipInfo = new StringBuffer();
  private long[] mask = { 255L, 65280L, 16711680L, -16777216L };
  private Text result = new Text();

  public Text evaluate(String ip, IntWritable flag)
  {
    if ((ip == null) || (flag == null)) {
      this.result.set("");
      return this.result;
    }
    if (1 == flag.get())
      this.result.set(long2Ip(ip));
    else if (2 == flag.get())
      this.result.set(ip2Long(ip) + "");
    else if (3 == flag.get())
      this.result.set(string2Ip(ip));
    else if (4 == flag.get())
      this.result.set(ip2String(ip));
    else {
      this.result.set("");
    }
    return this.result;
  }

  private String long2Ip(String ip) {
    long num = 0L;
    long ipLong = 0L;
    try {
      ipLong = Long.parseLong(ip);
    } catch (Exception e) {
      return "";
    }
    this.ipInfo.delete(0, this.ipInfo.length());
    for (int i = 0; i < 4; i++) {
      num = (ipLong & this.mask[i]) >> i * 8;
      if (i > 0) this.ipInfo.insert(0, ".");
      this.ipInfo.insert(0, Long.toString(num, 10));
    }
    return this.ipInfo.toString();
  }

  private String ip2Long(String ipStr) {
    try {
      long[] ipLongs = new long[4];
      String[] ips = ipStr.split("\\.");
      if (ips.length != 4) {
        return "";
      }
      for (int i = 0; i < ips.length; i++) {
        long ipLong = Long.parseLong(ips[i]);
        if (ipLong > 255L) {
          return "";
        }
        ipLongs[i] = ipLong;
      }

      long resultL = (ipLongs[0] << 24) + (ipLongs[1] << 16) + (ipLongs[2] << 8) + ipLongs[3];
      return resultL + ""; } catch (Exception e) {
    }
    return "";
  }

  private String ip2String(String ip)
  {
    String[] ips = ip.split("\\.");
    if (ips.length != 4) return ""; try
    {
      this.ipInfo.delete(0, this.ipInfo.length());
      for (int i = 0; i < ips.length; i++) {
        if (Integer.parseInt(ips[i]) < 16)
          this.ipInfo.append("0" + Integer.toHexString(Integer.parseInt(ips[i])));
        else {
          this.ipInfo.append(Integer.toHexString(Integer.parseInt(ips[i])));
        }
      }
      return this.ipInfo.toString(); } catch (Exception e) {
    }
    return "";
  }

  private String string2Ip(String lastIps)
  {
    if ((lastIps == null) || (lastIps.length() < 8))
      return "";
    try
    {
      this.ipInfo.delete(0, this.ipInfo.length());
      String ip1 = lastIps.substring(0, 2);
      String ip2 = lastIps.substring(2, 4);
      String ip3 = lastIps.substring(4, 6);
      String ip4 = lastIps.substring(6, 8);
      this.ipInfo.append(Integer.valueOf(ip1, 16).toString());
      this.ipInfo.append(".");
      this.ipInfo.append(Integer.valueOf(ip2, 16).toString());
      this.ipInfo.append(".");
      this.ipInfo.append(Integer.valueOf(ip3, 16).toString());
      this.ipInfo.append(".");
      this.ipInfo.append(Integer.valueOf(ip4, 16).toString());
      return this.ipInfo.toString(); } catch (Exception e) {
    }
    return "";
  }
}