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

public class UDFgetcattaglist extends UDF
{
  Text result = new Text();
  private static final String FILE1 = "./heart_stbTAG-0";
  private static final String FILE2 = "./heart_stbTAG_GROUP-0";
  private static final String File0 = "./dwdbCAT_TAG-0";
  Set<Integer> tag_regular = new HashSet();

  Map<Integer, Integer> m_group2tag_count = new HashMap();
  Map<Integer, HashSet<Integer>> m_tag2group_list = new HashMap();

  public Text evaluate(String category_level_one, String cat_features)
  {
    if (category_level_one == null)
    {
      category_level_one = "";
    }

    if (cat_features == null)
    {
      cat_features = "";
    }

    Map m_catTagList = new HashMap();
    HashSet tag_result = new HashSet();
    int cat_id = Integer.parseInt(category_level_one.toString());
    if (!category_level_one.toString().isEmpty())
    {
      String oneLine = cat_features.toString();

      if ((oneLine.length() > 0) && (oneLine.contains("tags:")))
      {
        parseTaglists(tag_result, oneLine);
        m_catTagList.put(Integer.valueOf(cat_id), tag_result);
      }

    }

    BufferedReader reader0 = null;
    String line0 = null;
    String[] strList0 = null;
    try
    {
      reader0 = new BufferedReader(new InputStreamReader(new FileInputStream("./dwdbCAT_TAG-0")));
      while ((line0 = reader0.readLine()) != null)
      {
        strList0 = line0.split("\t");
        if ((isNumeric(strList0[0].toString())) && (isNumeric(strList0[1].toString()))) {
          int tagid = Integer.parseInt(strList0[0].toString());

          if ((!tag_result.contains(Integer.valueOf(tagid))) && (cat_id == Integer.parseInt(strList0[1]))) {
            tag_result.add(Integer.valueOf(tagid));
          }
        }
      }

    }
    catch (FileNotFoundException e)
    {
      System.err.println("file0: not exist");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println(": read line0 error");
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.err.println(strList0[0] + " parse string1 to integer error.");
      e.printStackTrace();
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
    System.out.println(tag_field_show);
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

  public void get_valid_tag_lists(HashSet<Integer> tag_result, String user_data, String features, String cat_id, HashSet<Long> s_pvs, HashSet<Integer> s_sputaglist, Map<Integer, HashSet<Integer>> m_catTagList)
  {
    try
    {
      String[] user_datas = user_data.split("~");
      String[] feature_tags = get_tags(features).split(",");

      Set stags = new HashSet();

      if (feature_tags.length > 0) {
        for (String f_tag : feature_tags) {
          if (isNumeric(f_tag)) {
            int tag = Integer.parseInt(f_tag);
            stags.add(Integer.valueOf(tag));
            if (get_tag_type(tag) == 2) {
              tag_result.add(Integer.valueOf(tag));
            }
          }
        }
      }
      if ((user_data == null) || (user_data.length() == 0)) {
        return;
      }

      for (int index = 0; index < user_datas.length; index++) {
        String[] datas = user_datas[index].split(",");

        for (String s : datas) {
          if (0 == index) {
            if (!isNumeric(s)) {
              continue;
            }
            int tag_id = Integer.parseInt(s);
            int tag_type = get_tag_type(tag_id);
            if (tag_type == 1) {
              tag_result.add(Integer.valueOf(tag_id));
            } else if (tag_type == 3) {
              if (stags.contains(Integer.valueOf(tag_id))) {
                tag_result.add(Integer.valueOf(tag_id));
              }

            }
            else if ((tag_type == 7) || (tag_type == 8)) {
              if ((!m_catTagList.containsKey(Integer.valueOf(Integer.parseInt(cat_id)))) || 
                (!((HashSet)m_catTagList.get(Integer.valueOf(Integer.parseInt(cat_id)))).contains(Integer.valueOf(tag_id))))
              {
                continue;
              }

              if (tag_type == 7) {
                tag_result.add(Integer.valueOf(tag_id));
              }
              else if (s_sputaglist.contains(Integer.valueOf(tag_id)))
                tag_result.add(Integer.valueOf(tag_id));
            }
          }
          else
          {
            String[] tag_infos = s.split("-");
            if ((tag_infos.length < 2) || (!isNumeric(tag_infos[0]))) {
              continue;
            }
            int tag_id = Integer.parseInt(tag_infos[0]);
            int tag_type = get_tag_type(tag_id);
            if (1 == index) {
              if ((tag_infos.length != 2) || 
                (tag_type != 4) || 
                (!tag_infos[1].equals(cat_id))) continue;
              tag_result.add(Integer.valueOf(tag_id));
            }
            else
            {
              if ((2 != index) || 
                (tag_infos.length != 3) || 
                (tag_type != 5))
                continue;
              if (!tag_infos[1].equals(cat_id))
              {
                continue;
              }
              String[] pidvids = tag_infos[2].split(";");
              if (pidvids.length > 3) {
                continue;
              }
              boolean pv_check = true;
              for (String pv : pidvids) {
                String[] item = pv.split(":");
                if (item.length != 2) {
                  pv_check = false;
                  break;
                }
                long l_pv = getlong(Integer.parseInt(item[1]), Integer.parseInt(item[0]));
                if (!s_pvs.contains(Long.valueOf(l_pv))) {
                  pv_check = false;
                  break;
                }
              }
              if (true == pv_check) {
                tag_result.add(Integer.valueOf(tag_id));
              }
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      System.err.println("Exception:" + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    UDFgetcattaglist test = new UDFgetcattaglist();
    System.out.println(test.evaluate("50010388", ";*tags:72;*alimallEnable:1;*vm:3;*xcard:1;*h_assCatLimit:1;*site_3c_lady:��ױƷ;charityChannel:household;*b_point_lmt:1;*cod:1;*enable_tabbed_spu_module:rate,comment,price;*pcs_new_old:1,4,32;*pcs_new:1,4,16,32;h_auctionNum:0-3X800&4-250X800&251-10000X800&10001-100000000X800;*h_videoshow:1;"));
  }
}