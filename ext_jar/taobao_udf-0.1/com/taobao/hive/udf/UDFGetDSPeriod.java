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

public class UDFGetDSPeriod extends UDF
{
  private Map<String, String[]> periods;
  private boolean initFlag;
  private String[] columns;

  public UDFGetDSPeriod()
  {
    this.periods = new HashMap();
    this.initFlag = false;
    this.columns = new String[] { "period_name", "period_type_id", "start_date", "end_date", "period_label_id", "period_label_name" };
  }

  public String evaluate(String periodId, String propName) throws IOException
  {
    if ((periodId == null) || (propName == null) || ("".equals(periodId)) || ("".equals(propName))) return null;

    if (!this.initFlag) init();
    if (this.periods.get(periodId) == null) return null;
    String[] values = (String[])this.periods.get(periodId);
    for (int i = 0; i < this.columns.length; i++) {
      if (propName.equals(this.columns[i])) return values[i];
    }
    return null;
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
          p[0] = items[2];
          p[1] = items[3];
          p[2] = items[5];
          p[3] = items[6];
          p[4] = items[9];
          p[5] = items[11];
          this.periods.put(items[0], p);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      reader.close();
    }
  }

  public static void main(String[] args) {
    UDFGetDSPeriod udf = new UDFGetDSPeriod();
    try {
      System.out.println(udf.evaluate("20162391", "period_name"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}