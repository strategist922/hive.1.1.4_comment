package com.taobao.hive.udf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

@Description(name="UDFGetIPCity", value="_FUNC_(ip) - Returns the city_id of the ip")
public class UDFGetIPCity extends UDF
{
  private ArrayList<IpCityList> ipCities = new ArrayList();

  IntWritable result = new IntWritable();
  private static final String ip_cityid_files = "./ip_cityid.txt";
  private boolean isInited = false;

  private boolean init() throws Exception {
    BufferedReader reader = null;
    String line = null;
    File ipCityidFile = new File("./ip_cityid.txt");
    reader = new BufferedReader(new FileReader(ipCityidFile));
    while ((line = reader.readLine()) != null) {
      if (line != null) {
        addIpCityList(line);
      }
    }
    return true;
  }

  public IntWritable evaluate(Text s) throws Exception {
    if (s == null) {
      return null;
    }
    if (!this.isInited) {
      this.isInited = init();
      if (!this.isInited)
      {
        return null;
      }
    }
    String str = s.toString();
    int cityID = getCityIdByIp(str);
    if (cityID == -1) {
      return null;
    }
    this.result.set(cityID);
    return this.result;
  }

  private void addIpCityList(String ipCityLine) {
    String[] ipCityStr = ipCityLine.split("\"");
    if (ipCityStr.length < 3)
      return;
    try
    {
      IpCityList ipl = new IpCityList();
      ipl.setStartIP(convertIP(ipCityStr[0]));
      ipl.setEndIP(convertIP(ipCityStr[1]));
      ipl.setCityID(Integer.parseInt(ipCityStr[2]));
      this.ipCities.add(ipl);
    } catch (Exception e) {
      return;
    }
  }

  private long convertIP(String ipStr)
  {
    try {
      long[] ipLongs = new long[4];
      String[] ips = ipStr.split("\\.");
      if (ips.length != 4) {
        return -1L;
      }
      for (int i = 0; i < ips.length; i++) {
        long ipLong = Long.parseLong(ips[i]);
        if (ipLong > 255L) {
          return -1L;
        }
        ipLongs[i] = ipLong;
      }

      return (ipLongs[0] << 24) + (ipLongs[1] << 16) + (ipLongs[2] << 8) + ipLongs[3]; } catch (Exception e) {
    }
    return -1L;
  }

  private int getCityIdByIp(String ip)
  {
    long ipLong = convertIP(ip);
    if ((this.ipCities.size() == 0) || (ipLong == -1L))
      return -1;
    int begin = 0;
    int end = this.ipCities.size();
    while (begin < end) {
      int mid = (end + begin) / 2;
      if (((IpCityList)this.ipCities.get(mid)).getIpRange(ipLong) == 0)
        return ((IpCityList)this.ipCities.get(mid)).getCityID();
      if (((IpCityList)this.ipCities.get(mid)).getIpRange(ipLong) < 0)
        end = mid - 1;
      else {
        begin = mid + 1;
      }
    }
    return -1;
  }

  public static void main(String[] args)
  {
    UDFGetIPCity test = new UDFGetIPCity();
    try {
      System.out.println(test.evaluate(new Text("1.52.0.0")));
      System.out.println(test.evaluate(new Text("1.52.1.254")));
      System.out.println(test.evaluate(new Text("1.52.255.255")));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  static class IpCityList
  {
    private long startIP;
    private long endIP;
    private int CityID;

    public int getIpRange(long ip)
    {
      if ((ip >= this.startIP) && (ip <= this.endIP))
        return 0;
      if (ip > this.endIP) {
        return 1;
      }
      return -1;
    }

    public int getCityID()
    {
      return this.CityID;
    }

    public long getStartIP() {
      return this.startIP;
    }

    public long getEndIP() {
      return this.endIP;
    }

    public void setCityID(int cityID) {
      this.CityID = cityID;
    }

    public void setStartIP(long startIP) {
      this.startIP = startIP;
    }

    public void setEndIP(long endIP) {
      this.endIP = endIP;
    }
  }
}