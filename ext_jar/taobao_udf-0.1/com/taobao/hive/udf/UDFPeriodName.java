package com.taobao.hive.udf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFPeriodName extends UDF
{
  private boolean initFlag = false;
  private Map<String, String[]> periods;
  private Text result;

  public UDFPeriodName()
  {
    this.periods = new HashMap();
    this.initFlag = false;
    this.result = new Text();
  }

  public Text evaluate(String endDate, String periodLabelId) throws IOException {
    if ((endDate == null) || (periodLabelId == null)) return null;
    if (!this.initFlag) {
      init();
      this.initFlag = true;
    }
    String key = formatEndDate(endDate) + "," + periodLabelId;

    String[] period = (String[])this.periods.get(key);
    if ((period == null) || (period.length != 6)) return null;
    this.result.set(period[2]);
    return this.result;
  }

  private void init() throws IOException {
    this.initFlag = true;
    Path dimPath = new Path("/group/taobao/taobao/dw/fact/");
    FileSystem fs = dimPath.getFileSystem(new Configuration());

    String ruleFile = null;
    Path inputPath = null;

    ruleFile = "/group/taobao/taobao/dw/dim/ds_ddf_period/ds_ddf_period";
    inputPath = new Path(ruleFile);
    if (!fs.exists(inputPath))
      throw new IOException("/group/taobao/taobao/dw/dim/ds_ddf_period/ds_ddf_period not found");
    if (!fs.isFile(inputPath))
      throw new IOException("Input should be a file (period.txt)");
    FSDataInputStream in = fs.open(inputPath);
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    try
    {
      String line = null;
      while ((line = reader.readLine()) != null) {
        String[] items = line.split("\t");
        String[] p = new String[6];
        if (items.length >= 12) {
          p[0] = items[0];
          p[1] = items[1];
          p[2] = items[2];
          p[3] = items[6];
          p[4] = items[9];
          p[5] = items[11];
          this.periods.put(items[6] + "," + items[9], p);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      reader.close();
    }
  }

  public String formatEndDate(String endDate) {
    if (endDate.length() <= 8) return endDate;

    endDate = endDate.replace("-", "");
    if (endDate.length() > 8) endDate = endDate.substring(0, 8);

    return endDate;
  }

  public static void main(String[] args) {
    UDFPeriodName udf = new UDFPeriodName();
    try
    {
      System.out.println(udf.formatEndDate("20120328"));
      System.out.println(udf.formatEndDate("2012-03-28"));
      System.out.println(udf.formatEndDate("20120328000"));
      System.out.println(udf.evaluate("20120329", "day").toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}