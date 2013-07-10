package com.taobao.hive.udf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetTmpRecord extends UDF
{
  private static HashMap rules1 = new HashMap();
  private static HashMap rules2 = new HashMap();
  private static HashMap rules3 = new HashMap();
  private static HashMap rules4 = new HashMap();
  private static String hdfs1_flag = null;
  private static String hdfs2_flag = null;
  private static String hdfs3_flag = null;
  private static String hdfs4_flag = null;
  private static final String FILE1 = "./rule_type.txt";
  private static final String FILE2 = "./rule_url_work.txt";
  private static final String FILE3 = "./rule_url.txt";
  private static final String FILE4 = "./rule_url_property.txt";
  private static UDFGetValueFromRefer getValueFormRefer = new UDFGetValueFromRefer();
  private static UDFURLDecode decode = new UDFURLDecode();

  Text result = new Text();

  public UDFGetTmpRecord()
    throws IOException, ParseException
  {
  }

  public static String validate(String rule_id, String rule_type, String time, String url, String refer)
  {
    String line = (String)rules3.get(rule_id);
    String[] strList = line.split("\"", -1);
    String RULE_VALUE = strList[0];
    String PATH = strList[1];
    String STARTS = strList[2];
    String ENDS = strList[3];
    if ((rule_id != null) && (rule_type.equals("url_id"))) {
      if ((Integer.parseInt(time) >= Integer.parseInt(STARTS)) && (Integer.parseInt(time) <= Integer.parseInt(ENDS))) {
        Pattern p = Pattern.compile(RULE_VALUE, 8);
        if ((PATH.equals("0")) || (PATH.equals("2"))) {
          if (p.matcher(url).find()) {
            return "true";
          }
          return "url not match";
        }

        return "rule effect on url,but path is 1";
      }

      return "time is not in range";
    }

    if ((rule_id != null) && (rule_type.equals("refer_id"))) {
      if ((Integer.parseInt(time) >= Integer.parseInt(STARTS)) && (Integer.parseInt(time) <= Integer.parseInt(ENDS))) {
        Pattern p = Pattern.compile(RULE_VALUE, 8);
        if ((PATH.equals("1")) || (PATH.equals("2"))) {
          String pre = "";
          if (refer != null) {
            pre = getValueFormRefer.evaluate(new Text(refer), new Text("&"), new Text("pre=")).toString();
            pre = decode.evaluate(new Text(pre), "flag1", "flag2").toString();
          }
          if (p.matcher(pre).find()) {
            return "true";
          }
          return "pre not match";
        }

        return "rule effect on refer,but path is 0";
      }

      return "time is not in range";
    }

    return "false";
  }

  public static String getValue(String rule_id, String rule_type, String property_id, String url, String refer) {
    String line = (String)rules4.get(rule_id + "\"" + property_id);
    String[] strList = line.split("\"", -1);
    String name = strList[0];
    String at_path = strList[1];
    String path = strList[2];
    String is_default = strList[3];
    String start_path = strList[4];
    String end_path = strList[5];
    String asc_desc = strList[6];
    if ("url".equals(rule_type)) {
      if ((name != null) && (!name.trim().equals(""))) {
        return getValueByName(url, name, rule_type);
      }
      if ("0".equals(is_default)) {
        return getLocationDefault(url, at_path, path);
      }
      return getLocationValue(url, at_path, path, start_path, end_path, asc_desc);
    }

    if ("refer".equals(rule_type)) {
      if ((name != null) && (!name.trim().equals(""))) {
        return getValueByName(refer, name, rule_type);
      }
      String pre = "";
      if (refer != null) {
        pre = getValueFormRefer.evaluate(new Text(refer), new Text("&"), new Text("pre=")).toString();
        pre = decode.evaluate(new Text(pre), "flag1", "flag2").toString();
      }
      if ("0".equals(is_default)) {
        return getLocationDefault(pre, at_path, path);
      }
      return getLocationValue(pre, at_path, path, start_path, end_path, asc_desc);
    }

    return null;
  }

  public static String getValueByName(String source, String name, String rule_type)
  {
    if ((source != null) && (source.indexOf("&" + name) < 0) && (source.indexOf("?" + name) < 0)) {
      return null;
    }
    String value = getValueFormRefer.evaluate(new Text(source), new Text("&"), new Text(name)).toString();
    return value;
  }

  public static String getLocationDefault(String source, String at_path, String path)
  {
    String urlC = between(source, "/", ".htm");
    if ("|X|X|".equals(at_path)) {
      return urlC;
    }
    if (urlC.indexOf(at_path) < 0) {
      return null;
    }
    if ("".equals(at_path)) {
      return null;
    }
    String[] strList = urlC.split(at_path);
    String value = strList[(strList.length - Integer.parseInt(path))];
    return value;
  }

  public static String getLocationValue(String source, String at_path, String path, String start_path, String end_path, String asc_desc)
  {
    String urlC = between(source, start_path, end_path);
    if ("|X|X|".equals(at_path)) {
      return urlC;
    }
    if (urlC.indexOf(at_path) < 0) {
      return null;
    }
    if ("".equals(at_path)) {
      return null;
    }
    String[] strList = urlC.split(at_path);
    if ("0".equals(asc_desc)) {
      String value = strList[(strList.length - Integer.parseInt(path))];
      return value;
    }
    String value = strList[(Integer.parseInt(path) - 1)];
    return value;
  }

  public Text evaluate(String time, String url, String refer, String work_ids)
    throws IOException
  {
    if ((time == null) || (url == null) || (refer == null) || (work_ids == null)) {
      this.result.set("args is null:" + time + "\"" + url + "\"" + refer + "\"" + work_ids);
      return this.result;
    }
    try {
      initHdfsData();
    } catch (Exception e) {
      e.printStackTrace();
    }

    String yes = "false";
    String yes1 = "false";
    String yes2 = "false";
    try {
      time = time.substring(0, 8);
      String[] workidList = work_ids.split("-");
      for (int i = 0; i < workidList.length; i++)
        try {
          String workid = workidList[i];
          if ((workid != null) && (workid.trim().equals(""))) {
            continue;
          }
          String line = (String)rules2.get(workid);
          String[] strList = line.split("\"", -1);
          String url_id = strList[0];
          String refer_id = strList[1];
          if ((url_id != null) && (url_id.trim().equals(""))) {
            url_id = null;
          }
          if ((refer_id != null) && (refer_id.trim().equals(""))) {
            refer_id = null;
          }

          if ((url_id != null) && (refer_id == null)) {
            yes = validate(url_id, "url_id", time, url, refer);
          }
          if ((url_id == null) && (refer_id != null)) {
            yes = validate(refer_id, "refer_id", time, url, refer);
          }
          if ((url_id != null) && (refer_id != null)) {
            yes1 = validate(url_id, "url_id", time, url, refer);
            yes2 = validate(refer_id, "refer_id", time, url, refer);
            if (("true".equals(yes1)) && ("true".equals(yes2))) {
              yes = "true";
            }
          }
          if (((url_id != null) || (refer_id != null)) || 
            ("true".equals(yes))) {
            this.result.set(yes);
            return this.result;
          }
        }
        catch (Exception e) {
        }
    }
    catch (Exception e) {
      this.result.set("err_message:" + time + "\"" + url + "\"" + refer + "\"" + work_ids);
      return this.result;
    }
    this.result.set("result:" + yes + "\"" + "rule_id:" + yes1 + "\"" + "refer_id:" + yes2);
    return this.result;
  }

  public Text evaluate(String time, String url, String refer, String work_ids, String property_id, String flag)
    throws IOException
  {
    if ((time == null) || (url == null) || (refer == null) || (work_ids == null) || (property_id == null)) {
      return null;
    }
    if ((!"url".equals(flag)) && (!"refer".equals(flag))) {
      return null;
    }
    try
    {
      initHdfsData();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try
    {
      time = time.substring(0, 8);
      String[] workidList = work_ids.split("-");
      for (int i = 0; i < workidList.length; i++)
        try {
          String workid = workidList[i];
          if ((workid != null) && (workid.trim().equals(""))) {
            continue;
          }
          String line = (String)rules2.get(workid);
          String[] strList = line.split("\"", -1);
          String url_id = strList[0];
          String refer_id = strList[1];
          if ((url_id != null) && (url_id.equals(""))) {
            url_id = null;
          }
          if ((refer_id != null) && (refer_id.equals(""))) {
            refer_id = null;
          }

          if ((url_id != null) && (refer_id == null)) {
            if (flag.equals("refer")) {
              continue;
            }
            String yes = validate(url_id, "url_id", time, url, refer);
            if ("true".equals(yes)) {
              String property_value = getValue(url_id, flag, property_id, url, refer);
              if (property_value == null) {
                continue;
              }
              this.result.set(property_value);
              return this.result;
            }
          }
          if ((url_id == null) && (refer_id != null)) {
            if (flag.equals("url")) {
              continue;
            }
            String yes = validate(refer_id, "refer_id", time, url, refer);
            if ("true".equals(yes)) {
              String property_value = getValue(refer_id, flag, property_id, url, refer);
              if (property_value == null) {
                continue;
              }
              this.result.set(property_value);
              return this.result;
            }
          }
          if ((url_id != null) && (refer_id != null)) {
            String yes = "false";
            String yes1 = validate(url_id, "url_id", time, url, refer);
            String yes2 = validate(refer_id, "refer_id", time, url, refer);
            if (("true".equals(yes1)) && ("true".equals(yes2))) {
              yes = "true";
            }
            if ("true".equals(yes)) {
              if (flag.equals("url")) {
                String property_value = getValue(url_id, flag, property_id, url, refer);
                if (property_value == null) {
                  continue;
                }
                this.result.set(property_value);
                return this.result;
              }
              if (flag.equals("refer")) {
                String property_value = getValue(refer_id, flag, property_id, url, refer);
                if (property_value == null) {
                  continue;
                }
                this.result.set(property_value);
                return this.result;
              }
            }
          }
        }
        catch (Exception e) {
        }
    }
    catch (Exception e) {
      return null;
    }
    return null;
  }

  public static String between(String value, String begin, String end)
  {
    int index1 = value.lastIndexOf(begin);
    if (("".equals(begin)) && ("".equals(end))) {
      return value;
    }
    if ("".equals(begin)) {
      index1 = 0;
    }
    if (!end.equals("end")) {
      int index2 = value.indexOf(end);
      if ("".equals(end)) {
        index2 = value.length();
      }
      value = value.substring(index1 + begin.length(), index2);
      return value;
    }
    value = value.substring(index1 + begin.length());
    return value;
  }

  public static void initHdfsData() throws IOException, ParseException
  {
    initHdfs1();
    initHdfs2();
    initHdfs3();
    initHdfs4();
  }

  public static void initHdfs1() throws IOException {
    if (hdfs1_flag == null)
      hdfs1_flag = loadData1("./rule_type.txt");
  }

  public static void initHdfs2() throws IOException
  {
    if (hdfs2_flag == null)
      hdfs2_flag = loadData2("./rule_url_work.txt");
  }

  public static void initHdfs3() throws IOException, ParseException
  {
    if (hdfs3_flag == null)
      hdfs3_flag = loadData3("./rule_url.txt");
  }

  public static void initHdfs4() throws IOException
  {
    if (hdfs4_flag == null)
      hdfs4_flag = loadData4("./rule_url_property.txt");
  }

  public static String loadData1(String file)
  {
    BufferedReader reader = null;
    String line = null;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
      while ((line = reader.readLine()) != null)
        try {
          String[] strList = line.split("\"", -1);
          String type_id = strList[0];
          String STATUS = strList[3];
          String WORK_ID = strList[6];
          if ("0".equals(STATUS))
            rules1.put(type_id, WORK_ID);
        }
        catch (Exception e)
        {
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return "success";
  }

  public static String loadData2(String file) {
    BufferedReader reader = null;
    String line = null;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
      while ((line = reader.readLine()) != null)
        try {
          String[] strList = line.split("\"", -1);
          String work_id = strList[0];
          String URL_ID = strList[1];
          String REFER_ID = strList[2];
          String STATUS = strList[3];
          if ("0".equals(STATUS))
            rules2.put(work_id, URL_ID + "\"" + REFER_ID);
        }
        catch (Exception e)
        {
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return "success";
  }

  public static String loadData3(String file)
  {
    BufferedReader reader = null;
    String line = null;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
      while ((line = reader.readLine()) != null)
        try {
          String[] strList = line.split("\"", -1);
          String rule_id = strList[0];
          String RULE_VALUE = strList[2];
          String PATH = strList[3];
          String STATUS = strList[4];
          String STARTS = strList[5].replace("-", "");
          String ENDS = strList[6].replace("-", "");
          if ("0".equals(STATUS))
            rules3.put(rule_id, RULE_VALUE + "\"" + PATH + "\"" + STARTS + "\"" + ENDS);
        }
        catch (Exception e)
        {
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return "success";
  }

  public static String loadData4(String file)
  {
    BufferedReader reader = null;
    String line = null;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
      while ((line = reader.readLine()) != null)
        try {
          String[] strList = line.split("\"", -1);
          String URL_ID = strList[1];
          String P_ID = strList[2];
          String STATUS = strList[3];
          String NAME = strList[6];
          String AT_PATH = strList[7];
          String PATH = strList[8];
          String is_default = strList[9];
          String start_path = strList[10];
          String end_path = strList[11];
          String asc_desc = strList[12];
          if ("0".equals(STATUS))
            rules4.put(URL_ID + "\"" + P_ID, NAME + "\"" + AT_PATH + "\"" + PATH + "\"" + is_default + "\"" + start_path + "\"" + end_path + "\"" + asc_desc);
        }
        catch (Exception e)
        {
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return "success";
  }
}