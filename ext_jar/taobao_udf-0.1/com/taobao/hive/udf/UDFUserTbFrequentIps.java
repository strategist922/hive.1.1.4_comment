package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.hadoop.hive.ql.exec.UDF;

public class UDFUserTbFrequentIps extends UDF
{
  public static final Object IPCount = null;
  private static String DEFAULTSPILT = ",";
  private static String DEFAULTCON = ".";

  public String evaluate(String logintrace) {
    StringBuffer IP = new StringBuffer("");
    if (logintrace == null)
      return IP.toString();
    try
    {
      String[] login_trace = logintrace.split(DEFAULTSPILT);

      List ipList = new ArrayList();
      for (int i = login_trace.length - 1; i >= 0; i--) {
        if (login_trace[i].length() < 8) {
          continue;
        }
        String ip = login_trace[i].substring(0, 8);

        String ip1 = ip.substring(0, 2);
        String ip2 = ip.substring(2, 4);
        String ip3 = ip.substring(4, 6);
        String ip4 = ip.substring(6, 8);
        String Ip = Integer.valueOf(ip1, 16) + DEFAULTCON + Integer.valueOf(ip2, 16) + DEFAULTCON + Integer.valueOf(ip3, 16) + DEFAULTCON + Integer.valueOf(ip4, 16);
        IPCount count = new IPCount();
        count.setIp(Ip);
        if (ipList.contains(count)) {
          int index = ipList.indexOf(count);
          IPCount count1 = (IPCount)ipList.get(index);
          count1.setCount(Integer.valueOf(count1.getCount().intValue() + 1));
        }
        else {
          count.setCount(Integer.valueOf(1));
          ipList.add(count);
        }

      }

      Collections.sort(ipList, new Comparator()
      {
        public int compare(UDFUserTbFrequentIps.IPCount o1, UDFUserTbFrequentIps.IPCount o2)
        {
          if (o1.getCount().intValue() > o2.getCount().intValue())
            return -1;
          if (o1.getCount().intValue() < o2.getCount().intValue()) {
            return 1;
          }
          return 0;
        }
      });
      if (ipList.size() >= 3) {
        IPCount ip1 = (IPCount)ipList.get(0);
        IPCount ip2 = (IPCount)ipList.get(1);
        IPCount ip3 = (IPCount)ipList.get(2);
        return ip1.getIp() + DEFAULTSPILT + ip2.getIp() + DEFAULTSPILT + ip3.getIp();
      }if ((3 > ipList.size()) && (ipList.size() > 1)) {
        IPCount ip1 = (IPCount)ipList.get(0);
        IPCount ip2 = (IPCount)ipList.get(1);
        return ip1.getIp() + DEFAULTSPILT + ip2.getIp();
      }if ((2 > ipList.size()) && (ipList.size() > 0)) {
        IPCount ip1 = (IPCount)ipList.get(0);
        return ip1.getIp();
      }
      return "";
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }return "";
  }

  public static void main(String[] args)
    throws ParseException
  {
    String ipss = "da02f42e1259159066";
    String ip = "da02f42e";
    UDFUserTbFrequentIps ips = new UDFUserTbFrequentIps();

    System.out.println("Result = >" + ips.evaluate(ipss));

    Long timestamp = Long.valueOf(Long.parseLong("1302533654") * 1000L);
    String str = new SimpleDateFormat("yyyyMMdd").format(new Date(timestamp.longValue()));
    System.out.println("�����Ľ���������" + str);
  }

  class IPCount
  {
    private String ip;
    private Integer count;

    IPCount()
    {
    }

    public String getIp()
    {
      return this.ip;
    }

    public void setIp(String ip) {
      this.ip = ip;
    }

    public Integer getCount() {
      return this.count;
    }

    public void setCount(Integer count) {
      this.count = count;
    }

    public boolean equals(Object obj)
    {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof IPCount)) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      IPCount obj1 = (IPCount)obj;

      return this.ip.equals(obj1.getIp());
    }

    public int hashCode()
    {
      return this.ip.hashCode();
    }
  }
}