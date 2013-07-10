package com.taobao.hive.udf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFgetsputaglist extends UDF
{
  Text result = new Text();
  private static final String FILE1 = "./heart_stbTAG-0";
  private static final String FILE2 = "./heart_stbTAG_GROUP-0";
  Set<Integer> tag_regular = new HashSet();

  Map<Integer, Integer> m_group2tag_count = new HashMap();
  Map<Integer, HashSet<Integer>> m_tag2group_list = new HashMap();

  public Text evaluate(String spu_features)
  {
    if (spu_features == null)
    {
      spu_features = "";
    }

    HashSet tag_result = new HashSet();

    if ((!spu_features.toString().isEmpty()) && (spu_features.toString().contains("tags:"))) {
      parseTaglists(tag_result, spu_features.toString());
    }

    System.out.println(tag_result);

    BufferedReader reader1 = null;
    String line1 = null;
    String[] strList1 = null;
    try
    {
      reader1 = new BufferedReader(new InputStreamReader(new FileInputStream("./heart_stbTAG-0")));
      while ((line1 = reader1.readLine()) != null)
      {
        strList1 = line1.split("\t");
        if (isNumeric(strList1[0].toString())) {
          int tagid = Integer.parseInt(strList1[0].toString());

          this.tag_regular.add(Integer.valueOf(tagid));
        }
      }

    }
    catch (FileNotFoundException e)
    {
      System.err.println("file1: not exist");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println(": read line1 error");
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.err.println(strList1[0] + " parse string1 to integer error.");
      e.printStackTrace();
    }

    BufferedReader reader2 = null;
    String line2 = null;
    String[] strList2 = null;
    try {
      reader2 = new BufferedReader(new InputStreamReader(new FileInputStream("./heart_stbTAG_GROUP-0")));
      while ((line2 = reader2.readLine()) != null)
      {
        strList2 = line2.split("\t");

        if (!isNumeric(strList2[0].toString()))
        {
          continue;
        }
        int tag_groupid = Integer.parseInt(strList2[0].toString());
        String expressions = strList2[3].toString();

        if ((expressions.contains("&")) && (!expressions.contains("|"))) {
          String[] tags_id = expressions.split("&");
          this.m_group2tag_count.put(Integer.valueOf(tag_groupid), Integer.valueOf(tags_id.length));
          for (String tagid : tags_id) {
            if (!isNumeric(tagid)) {
              break;
            }
            int tid = Integer.parseInt(tagid);
            HashSet ts = (HashSet)this.m_tag2group_list.get(Integer.valueOf(tid));
            if (ts != null) {
              ts.add(Integer.valueOf(tag_groupid));
            } else {
              ts = new HashSet();
              ts.add(Integer.valueOf(tag_groupid));
              this.m_tag2group_list.put(Integer.valueOf(tid), ts);
            }
          }
        } else if ((!expressions.contains("&")) && (expressions.contains("|"))) {
          String[] tags_id = expressions.split("\\|");
          this.m_group2tag_count.put(Integer.valueOf(tag_groupid), Integer.valueOf(1));

          for (String tagid : tags_id) {
            if (!isNumeric(tagid)) {
              break;
            }
            int tid = Integer.parseInt(tagid);
            HashSet ts = (HashSet)this.m_tag2group_list.get(Integer.valueOf(tid));
            if (ts != null) {
              ts.add(Integer.valueOf(tag_groupid));
            } else {
              ts = new HashSet();
              ts.add(Integer.valueOf(tag_groupid));
              this.m_tag2group_list.put(Integer.valueOf(tid), ts);
            }
          }
        }
      }
    }
    catch (FileNotFoundException e)
    {
      System.err.println("file2: not exist");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println(": read line2 error");
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.err.println(strList2[0] + " parse string2 to integer error.");
      e.printStackTrace();
    }

    ArrayList tag_field_show = new ArrayList();

    Map m_group_tags = new HashMap();
    if (tag_result.size() > 0)
    {
      for (Iterator i$ = tag_result.iterator(); i$.hasNext(); ) { int tag_id = ((Integer)i$.next()).intValue();
        if (this.tag_regular.contains(Integer.valueOf(tag_id)))
        {
          tag_field_show.add(Integer.valueOf(tag_id));

          if (this.m_tag2group_list.containsKey(Integer.valueOf(tag_id))) {
            HashSet ts = (HashSet)this.m_tag2group_list.get(Integer.valueOf(tag_id));
            for (i$ = ts.iterator(); i$.hasNext(); ) { int group_id = ((Integer)i$.next()).intValue();
              if (m_group_tags.containsKey(Integer.valueOf(group_id))) {
                int count = ((Integer)m_group_tags.get(Integer.valueOf(group_id))).intValue();
                count += 1;

                m_group_tags.put(Integer.valueOf(group_id), Integer.valueOf(count));
              } else {
                m_group_tags.put(Integer.valueOf(group_id), Integer.valueOf(1));
              }
            }
          }
        }
      }
      Iterator i$;
      for (Map.Entry group_count : m_group_tags.entrySet()) {
        int key = ((Integer)group_count.getKey()).intValue();
        if ((this.m_group2tag_count.containsKey(Integer.valueOf(key))) && 
          (((Integer)group_count.getValue()).intValue() >= ((Integer)this.m_group2tag_count.get(Integer.valueOf(key))).intValue()))
        {
          tag_field_show.add(Integer.valueOf(key));
        }
      }

    }

    String results = "";
    for (Integer i : tag_field_show) {
      if (results == "") {
        results = i.toString();
      }
      else {
        results = results.concat(",").concat(i.toString());
      }

    }

    this.result.set(results);
    return this.result;
  }

  private void parseTaglists(HashSet<Integer> tag_list, String features)
  {
    try {
      int start_index = features.indexOf("tags:");
      int end_index = features.indexOf(";", start_index);
      if (-1 == end_index) {
        end_index = features.length();
      }

      String[] taglists = features.substring(start_index + "tags:".length(), end_index).split(",");
      for (int i = 0; i < taglists.length; i++)
        tag_list.add(Integer.valueOf(Integer.parseInt(taglists[i].trim())));
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      e.printStackTrace(System.err);
    } catch (NumberFormatException e) {
      System.err.println(e.getMessage());
      System.err.println("NumberFormatException at: " + features);
    }
  }

  public long getlong(int vid, int pid)
  {
    long l = vid;
    l <<= 32;
    l += pid;

    return l;
  }

  public String get_tags(String features) {
    String result = "";
    String token = "tags:";
    String[] items = features.split(";");
    for (int i = 0; i < items.length; i++) {
      if (items[i].startsWith(token)) {
        result = items[i].substring(items[i].indexOf(":") + 1);
      }
    }
    return result;
  }

  public int get_tag_type(int tagId) {
    return tagId & 0x3F;
  }

  public static boolean isNumeric(String str)
  {
    if (str == null) {
      return false;
    }
    if (str.length() == 0) {
      return false;
    }

    Pattern pattern = Pattern.compile("-*[0-9]*(\\.)?[0-9]*");
    boolean regFlag = pattern.matcher(str).matches();
    return regFlag;
  }

  public static void main(String[] args)
  {
    UDFgetsputaglist test = new UDFgetsputaglist();
    System.out.println(test.evaluate(""));
  }
}