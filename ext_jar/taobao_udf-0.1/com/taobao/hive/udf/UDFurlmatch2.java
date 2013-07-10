package com.taobao.hive.udf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFurlmatch2 extends UDF
{
  private boolean isInited = false;

  private RegexMap<String> mapUrl = new RegexMap();
  private Text result = new Text();
  private boolean hdfs1_flag = false;

  public void initHdfsData(String loadpath) throws IOException {
    initHdfs1(loadpath);
  }

  public void initHdfs1(String loadpath) throws IOException {
    loadData1(loadpath);
    this.isInited = true;
  }

  public String loadData1(String file) throws IOException {
    BufferedReader reader = null;
    String line = null;
    File dir = new File(file);
    File[] files = null;
    if (dir.isDirectory()) {
      files = dir.listFiles(new HiddenFileFilter());
    } else {
      files = new File[1];
      files[1] = dir;
    }
    if (files == null) {
      throw new IOException(file + "not found.");
    }
    System.out.println("add file(path):" + file);
    for (File lfile : files) {
      try {
        System.out.println("read file fullpath: " + lfile.getAbsolutePath());
        System.out.println("read filename: " + lfile.getName());
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(lfile)));

        while ((line = reader.readLine()) != null)
          try {
            addUrlLine(line);
          }
          catch (Exception e) {
          }
      }
      catch (Exception e) {
        System.out.println(e);
        e.printStackTrace();
      }
    }
    return "success";
  }

  private void addUrlLine(String UrlLine) {
    String[] UrlStr = UrlLine.split("\001");
    Pattern p = Pattern.compile(UrlStr[3].trim(), 8);
    this.mapUrl.put(p, UrlStr[0] + "\001" + UrlStr[2] + "\001" + UrlStr[3]);
  }

  public Text evaluate(String url, String loadpath) throws IOException
  {
    if (url == null) {
      return null;
    }
    try
    {
      if (!this.isInited) {
        initHdfsData(loadpath);
        this.hdfs1_flag = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    String tmp = (String)this.mapUrl.get(url);
    if (tmp != null) {
      this.result.set(tmp.trim());
      return this.result;
    }
    return null;
  }

  public static void main(String[] args)
  {
    UDFurlmatch test = new UDFurlmatch();
    try
    {
      System.out.println(test.evaluate("http://tv.taobao.com/item_list.htm", "D:\\test\\text.txt"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  class HiddenFileFilter
    implements FilenameFilter
  {
    HiddenFileFilter()
    {
    }

    public boolean accept(File arg0, String arg1)
    {
      if ((arg1 == null) || ("".equals(arg1))) return false;
      return !arg1.startsWith(".");
    }
  }

  class RegexMap<V> extends HashMap<Pattern, V>
  {
    private static final long serialVersionUID = 4761214570434685507L;
    protected HashMap<Pattern, V> map = null;

    public RegexMap()
    {
      this.map = new HashMap();
    }

    public RegexMap(int capacity)
    {
      this.map = new HashMap(capacity);
    }

    public RegexMap(int capacity, float factor)
    {
      this.map = new HashMap(capacity, factor);
    }

    public RegexMap()
    {
      this.map = new HashMap(map);
    }

    public V get(Object key) {
      if (key != null) {
        for (Map.Entry entry : this.map.entrySet()) {
          if (((Pattern)entry.getKey()).matcher(key.toString()).find())
            return entry.getValue();
        }
      }
      return null;
    }

    public int size() {
      return this.map.size();
    }

    public boolean isEmpty() {
      return this.map.isEmpty();
    }

    public boolean containsKey(Object key) {
      return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
      return this.map.containsValue(value);
    }

    public V put(Pattern key, V value) {
      return this.map.put(key, value);
    }

    public void putAll(Map<? extends Pattern, ? extends V> in) {
      this.map.putAll(in);
    }

    public V remove(Pattern key) {
      return this.map.remove(key);
    }

    public void clear() {
      this.map.clear();
    }

    public Set<Map.Entry<Pattern, V>> entrySet() {
      return this.map.entrySet();
    }

    public Set<Pattern> keySet() {
      return this.map.keySet();
    }

    public Collection<V> values() {
      return this.map.values();
    }
  }
}