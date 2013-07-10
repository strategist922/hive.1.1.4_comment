package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFIsInIp extends UDF
{
  Text result = new Text();

  public Text evaluate(String ip, String beginIp, String endIp)
  {
    long ip1 = ipToLong(ip);
    long ip2 = ipToLong(beginIp);
    long ip3 = ipToLong(endIp);

    if ((ip1 >= ip2) && (ip1 <= ip3)) {
      this.result.set("true");
      return this.result;
    }

    this.result.set("false");
    return this.result;
  }

  public static long ipToLong(String strIP)
  {
    long[] ip = new long[4];

    int position1 = strIP.indexOf(".");
    int position2 = strIP.indexOf(".", position1 + 1);
    int position3 = strIP.indexOf(".", position2 + 1);

    ip[0] = Long.parseLong(strIP.substring(0, position1));
    ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
    ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
    ip[3] = Long.parseLong(strIP.substring(position3 + 1));

    return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
  }

  public static String long2IP(long longIP)
  {
    StringBuffer sb = new StringBuffer("");

    sb.append(String.valueOf(longIP >>> 24));
    sb.append(".");

    sb.append(String.valueOf((longIP & 0xFFFFFF) >>> 16));
    sb.append(".");
    sb.append(String.valueOf((longIP & 0xFFFF) >>> 8));
    sb.append(".");
    sb.append(String.valueOf(longIP & 0xFF));
    return sb.toString();
  }

  public static void main(String[] args)
  {
    UDFIsInIp test = new UDFIsInIp();

    System.out.println(ipToLong("192.168.0.2"));
    System.out.println(long2IP(Long.valueOf("3232235521").longValue()));
    System.out.println(test.evaluate("202.13.45.3", "202.12.45.3", "202.13.45.255"));
    System.out.println(test.evaluate("202.13.45.3", "202.13.45.3", "202.13.42.255"));
  }
}