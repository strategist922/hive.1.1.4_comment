package com.taobao.hive.udf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.io.Text;

public class UDFGetCityByIp extends UDF
{
  private ArrayList<IPCityEntry> ipCities;
  Text result;
  private boolean HDFS_INIT_FLAG;

  public UDFGetCityByIp()
  {
    this.ipCities = new ArrayList();
    this.result = new Text("");
    this.HDFS_INIT_FLAG = false;
  }
  public void initHDFS(String filename) throws IOException, HiveException {
    this.HDFS_INIT_FLAG = true;
    Path dimPath = new Path("/group/taobao/taobao/dw/dim/");
    FileSystem fs = dimPath.getFileSystem(new Configuration());
    Path ipCityPath = new Path("/group/taobao/taobao/dw/dim/" + filename);
    if (!fs.exists(ipCityPath))
      throw new IOException("file not found:/group/taobao/taobao/dw/dim/" + filename);
    FSDataInputStream in = fs.open(ipCityPath);
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String line = null;
    this.ipCities.clear();
    while ((line = reader.readLine()) != null)
      try {
        String[] list = line.trim().split(",");
        if (list.length >= 7) {
          IPCityEntry entry = new IPCityEntry();
          entry.setStartIP(Long.parseLong(list[0]));
          entry.setEndIP(Long.parseLong(list[1]));
          entry.setCountry("-".equals(list[2]) ? "" : list[2]);
          entry.setProv("-".equals(list[3]) ? "" : list[3]);
          entry.setCity("-".equals(list[4]) ? "" : list[4]);
          entry.setArea("-".equals(list[5]) ? "" : list[5]);
          entry.setSchool("-".equals(list[6]) ? "" : list[6]);
          if (list.length > 7) entry.setIsp("-".equals(list[7]) ? "" : list[7]);
          this.ipCities.add(entry);
        }
      }
      catch (Exception e)
      {
      }
    System.out.println("read ip entry size: " + this.ipCities.size());
  }

  public void initLocalFS(String fileName) throws IOException {
    this.HDFS_INIT_FLAG = true;
    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\" + fileName)));
    String line = null;
    this.ipCities.clear();
    while ((line = reader.readLine()) != null)
      try {
        String[] list = line.trim().split(",");
        if (list.length >= 7) {
          IPCityEntry entry = new IPCityEntry();
          entry.setStartIP(Long.parseLong(list[0]));
          entry.setEndIP(Long.parseLong(list[1]));
          entry.setCountry("-".equals(list[2]) ? "" : list[2]);
          entry.setProv("-".equals(list[3]) ? "" : list[3]);
          entry.setCity("-".equals(list[4]) ? "" : list[4]);
          entry.setArea("-".equals(list[5]) ? "" : list[5]);
          entry.setSchool("-".equals(list[6]) ? "" : list[6]);
          if (list.length > 7) entry.setIsp("-".equals(list[7]) ? "" : list[7]);
          this.ipCities.add(entry);
        }
      }
      catch (Exception e)
      {
      }
    System.out.println("read ip entry size: " + this.ipCities.size());
  }

  public Text evaluate(String ip, String flag) throws Exception {
    return evaluate(ip, flag, "ipdata.txt");
  }

  public Text evaluate(String ip, String flag, String filename) throws Exception {
    if ((ip == null) || (flag == null) || ("".equals(ip)) || ("".equals(flag)) || (filename == null) || ("".equals(filename))) {
      this.result.set("");
      return this.result;
    }
    if (!this.HDFS_INIT_FLAG) initHDFS(filename);

    IPCityEntry ipEntry = getCityIdByIp(ip);
    if (ipEntry != null) {
      if ("city".equals(flag))
        this.result.set(ipEntry.getCity());
      else if ("area".equals(flag))
        this.result.set(ipEntry.getArea());
      else if ("prov".equals(flag))
        this.result.set(ipEntry.getProv());
      else if ("country".equals(flag))
        this.result.set(ipEntry.getCountry());
      else if ("school".equals(flag))
        this.result.set(ipEntry.getSchool());
      else if ("isp".equals(flag))
        this.result.set(ipEntry.getIsp());
      else
        this.result.set("");
    }
    else {
      this.result.set("");
    }
    return this.result;
  }

  private long convertIP(String ipStr) {
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

  private IPCityEntry getCityIdByIp(String ip)
  {
    long ipLong = convertIP(ip);
    if ((this.ipCities.size() == 0) || (ipLong == -1L))
      return null;
    int begin = 0;
    int end = this.ipCities.size() - 1;
    while (begin <= end) {
      int mid = (end + begin) / 2;
      if (((IPCityEntry)this.ipCities.get(mid)).getIpRange(ipLong) == 0)
        return (IPCityEntry)this.ipCities.get(mid);
      if (((IPCityEntry)this.ipCities.get(mid)).getIpRange(ipLong) < 0)
        end = mid - 1;
      else {
        begin = mid + 1;
      }
    }
    return null;
  }

  public static void main(String[] args)
  {
    try
    {
      System.out.println("======================");
      UDFGetCityByIp test = new UDFGetCityByIp();
      System.out.println(test.evaluate("125.77.170.106", "city"));
      System.out.println(test.evaluate("125.77.170.106", "country"));
      System.out.println(test.evaluate("125.77.170.106", "prov"));
      System.out.println(test.evaluate("125.77.170.106", "area"));
      System.out.println(test.evaluate("125.77.170.106", "isp"));
      System.out.println(test.evaluate("223.245.63.33", "city"));
      System.out.println(test.evaluate("223.245.63.33", "country"));
      System.out.println(test.evaluate("223.245.63.33", "prov"));
      System.out.println(test.evaluate("223.245.63.33", "area"));
      System.out.println(test.evaluate("223.245.63.33", "school"));
      System.out.println(test.evaluate("223.245.63.33", "isp"));
      System.out.println("======================");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  class IPCityEntry
  {
    private long startIP;
    private long endIP;
    private String country;
    private String prov;
    private String city;
    private String area;
    private String school;
    private String isp;

    IPCityEntry()
    {
    }

    public String getSchool()
    {
      return this.school;
    }
    public void setSchool(String school) {
      this.school = school;
    }
    public int getIpRange(long ip) {
      if ((ip >= this.startIP) && (ip <= this.endIP))
        return 0;
      if (ip > this.endIP) {
        return 1;
      }
      return -1;
    }

    public long getStartIP()
    {
      return this.startIP;
    }

    public long getEndIP() {
      return this.endIP;
    }

    public void setStartIP(long startIP) {
      this.startIP = startIP;
    }

    public void setEndIP(long endIP) {
      this.endIP = endIP;
    }

    public String getCountry() {
      return this.country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public String getProv() {
      return this.prov;
    }

    public void setProv(String prov) {
      this.prov = prov;
    }

    public String getCity() {
      return this.city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getArea() {
      return this.area;
    }

    public void setArea(String area) {
      this.area = area;
    }
    public String getIsp() {
      return this.isp;
    }
    public void setIsp(String isp) {
      this.isp = isp;
    }
  }
}