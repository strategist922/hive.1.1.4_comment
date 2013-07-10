package com.taobao.hive.udtf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class UDTFExplodeDSPeriod extends GenericUDTF
{
  private Map<String, List<Period>> periods;
  private boolean init;
  private SimpleDateFormat dateFormatter;

  public UDTFExplodeDSPeriod()
  {
    this.init = false;
    this.dateFormatter = new SimpleDateFormat("yyyyMMdd");
  }

  public StructObjectInspector initialize(ObjectInspector[] args)
    throws UDFArgumentException
  {
    if ((args.length != 1) && (args.length != 3) && (args.length != 4)) {
      throw new UDFArgumentLengthException("UDTFExplodeDSPeriod takes one(three) argument(s)");
    }

    if ((args.length == 1) && (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE)) {
      throw new UDFArgumentException("UDTFExplodeDSPeriod takes string as a parameter");
    }

    if ((args.length >= 3) && (
      (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) || (args[1].getCategory() != ObjectInspector.Category.PRIMITIVE) || (args[2].getCategory() != ObjectInspector.Category.PRIMITIVE)))
    {
      throw new UDFArgumentException("UDTFExplodeDSPeriod takes string as a parameter");
    }

    ArrayList fieldNames = new ArrayList();
    ArrayList fieldOIs = new ArrayList();
    fieldNames.add("col1");
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    fieldNames.add("col2");
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    fieldNames.add("col3");
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

    return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
  }

  public void process(Object[] args)
    throws HiveException
  {
    if ((args == null) || ((args.length != 1) && (args.length != 3) && (args.length != 4))) return;

    if (!this.init) {
      this.init = true;
      try {
        if (args.length == 4)
          buildPeriod(args[1].toString(), args[2].toString(), true);
        else
          buildPeriod(null, null, false);
      }
      catch (Exception e) {
        throw new HiveException("/group/taobao/taobao/dw/dim/ds_ddf_period/ds_ddf_period not found.");
      }
    }

    String bizDate = args[0].toString();
    List days = (List)this.periods.get(bizDate);
    if ((days != null) && (days.size() > 0)) {
      for (Period p : days) {
        if (args.length == 3) {
          if ((p.getEndDate().equals(args[1].toString())) && (p.getPeriodLabel().equals(args[2].toString())))
          {
            forward(new String[] { p.getPeriodID(), p.getPeriodCode(), p.getPeriodName() });
          }
        }
        else {
          forward(new String[] { p.getPeriodID(), p.getPeriodCode(), p.getPeriodName() });
        }
      }
    }
    else
      forward(new String[3]);
  }

  public void close()
    throws HiveException
  {
  }

  public static void main(String[] args)
  {
    UDTFExplodeDSPeriod udtf = new UDTFExplodeDSPeriod();
    try {
      udtf.process(new String[] { "20111209" });
      System.out.println("---------------------------------------");
      udtf.buildPeriod("20111209", "day", false);
      udtf.process(new String[] { "20111209", "20111209", "day" });
      System.out.println("---------------------------------------");
      udtf.buildPeriod("20111209", "day", true);
      udtf.process(new String[] { "20111209", "20111209", "day" });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void buildPeriod(String endDate, String periodLabel, boolean endFlag) throws Exception {
    this.periods = new HashMap();

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
      String[] items;
      while ((line = reader.readLine()) != null) {
        items = line.split("\t");
        if (items.length >= 10) {
          if ((endFlag) && (
            ((endDate != null) && (!"".equals(endDate)) && 
            (!endDate.equals(items[6]))) || (
            (periodLabel != null) && (!"".equals(periodLabel)) && 
            (!periodLabel.equals(items[9]))))) {
            continue;
          }
          List days = explodeDate(items[5], items[6]);
          if ((days != null) && (days.size() > 0))
            for (String date : days)
              if (this.periods.get(date) == null) {
                List pd = new ArrayList();
                Period p = new Period();
                p.setPeriodCode(items[1]);
                p.setPeriodID(items[0]);
                p.setPeriodName(items[2]);
                p.setEndDate(items[6]);
                p.setPeriodLabel(items[9]);
                pd.add(p);
                this.periods.put(date, pd);
              } else {
                Period p = new Period();
                p.setPeriodCode(items[1]);
                p.setPeriodID(items[0]);
                p.setPeriodName(items[2]);
                p.setEndDate(items[6]);
                p.setPeriodLabel(items[9]);
                ((List)this.periods.get(date)).add(p);
              }
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      reader.close();
    }
  }
  private List<String> explodeDate(String startDate, String endDate) {
    Date start;
    Date end;
    try {
      start = this.dateFormatter.parse(startDate);
      end = this.dateFormatter.parse(endDate);
    } catch (Exception e) {
      return null;
    }
    List lDate = new ArrayList();
    lDate.add(this.dateFormatter.format(start));
    if (!startDate.equals(endDate)) {
      lDate.add(this.dateFormatter.format(end));
    }
    Calendar cal = Calendar.getInstance();

    cal.setTime(start);

    boolean bContinue = true;

    while (bContinue)
    {
      cal.add(5, 1);

      if (!end.after(cal.getTime())) break;
      lDate.add(this.dateFormatter.format(cal.getTime()));
    }

    return lDate; } 
  class Period { private String periodID;
    private String periodName;
    private String periodCode;
    private String endDate;
    private String periodLabel;

    Period() {  } 
    public String getPeriodID() { return this.periodID; }

    public void setPeriodID(String periodID) {
      this.periodID = periodID;
    }
    public String getPeriodName() {
      return this.periodName;
    }
    public void setPeriodName(String periodName) {
      this.periodName = periodName;
    }
    public String getPeriodCode() {
      return this.periodCode;
    }
    public void setPeriodCode(String periodCode) {
      this.periodCode = periodCode;
    }
    public String getEndDate() {
      return this.endDate;
    }
    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }
    public String getPeriodLabel() {
      return this.periodLabel;
    }
    public void setPeriodLabel(String periodLabel) {
      this.periodLabel = periodLabel;
    }
  }
}