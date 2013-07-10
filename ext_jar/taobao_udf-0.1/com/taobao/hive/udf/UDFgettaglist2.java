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

public class UDFgettaglist2 extends UDF
{
  Text result = new Text();
  private static final String FILE1 = "./heart_stbTAG-0";
  private static final String FILE2 = "./heart_stbTAG_GROUP-0";
  private Pattern pattern = Pattern.compile("-*[0-9]*(\\.)?[0-9]*");
  Set<Integer> tag_regular = new HashSet();
  private boolean init_flag_tag = false;
  private boolean init_flag_tag_group = false;

  Map<Integer, Integer> m_group2tag_count = new HashMap();
  Map<Integer, HashSet<Integer>> m_tag2group_list = new HashMap();

  public Text evaluate(String user_data, String features, String category_level_one, String property, String spu_features, String cat_features, String category_tag_list, String status, String category, String category_level_two, String category_level_three, String category_level_four, String category_level_five)
  {
    if (property == null)
    {
      property = "";
    }
    if (spu_features == null)
    {
      spu_features = "";
    }
    if (category_level_one == null)
    {
      category_level_one = "";
    }
    if (category_level_two == null)
    {
      category_level_two = "";
    }
    if (category_level_three == null)
    {
      category_level_three = "";
    }
    if (category_level_four == null)
    {
      category_level_four = "";
    }
    if (category_level_five == null)
    {
      category_level_five = "";
    }

    if (user_data == null)
    {
      user_data = "";
    }

    if (features == null)
    {
      features = "";
    }
    if (cat_features == null)
    {
      cat_features = "";
    }
    if (category_tag_list == null)
    {
      category_tag_list = "";
    }
    if (status == null)
    {
      status = "";
    }

    if (category == null)
    {
      category = "";
    }

    HashSet s_pvs = new HashSet();

    if ((!property.toString().isEmpty()) && (property != null))
    {
      String[] token1 = property.toString().split(";");

      for (int k = 0; k < token1.length; k++) {
        if (token1[k].length() == 0) {
          continue;
        }
        String[] token2 = token1[k].split(":");

        if (token2.length == 2) {
          int pid = Integer.parseInt(token2[0]);
          int vid = Integer.parseInt(token2[1]);

          long pid_vid = getlong(vid, pid);

          s_pvs.add(Long.valueOf(pid_vid));
        }

      }

    }

    HashSet spu_tags_list = new HashSet();

    if ((!spu_features.toString().isEmpty()) && (spu_features.toString().contains("tags:"))) {
      parseTaglists(spu_tags_list, spu_features.toString());
    }

    Map m_catTagList = new HashMap();
    if (!category_level_one.toString().isEmpty()) {
      int cat_id = Integer.parseInt(category_level_one.toString());
      String oneLine = cat_features.toString();

      if ((oneLine.length() > 0) && (oneLine.contains("tags:"))) {
        HashSet tag_list = new HashSet();
        parseTaglists(tag_list, oneLine);
        m_catTagList.put(Integer.valueOf(cat_id), tag_list);
      }

    }

    Map m_catTagList2 = new HashMap();
    if (!category.toString().isEmpty()) {
      int cat_id2 = Integer.parseInt(category.toString());
      if (!category_tag_list.toString().isEmpty()) {
        HashSet tag_list = new HashSet();
        parseCaglists(tag_list, category_tag_list.toString());
        m_catTagList2.put(Integer.valueOf(cat_id2), tag_list);
      }

    }

    HashSet tag_result = new HashSet();
    get_valid_tag_lists(tag_result, user_data.toString(), features.toString(), category_level_one.toString(), s_pvs, spu_tags_list, m_catTagList, m_catTagList2, category_tag_list, status, category, category_level_two, category_level_three, category_level_four, category_level_five);

    if (!this.init_flag_tag) {
      BufferedReader reader1 = null;
      String line1 = null;
      String[] strList1 = null;
      try {
        reader1 = new BufferedReader(new InputStreamReader(new FileInputStream("./heart_stbTAG-0")));
        while ((line1 = reader1.readLine()) != null) {
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
      this.init_flag_tag = true;
    }

    if (!this.init_flag_tag_group) {
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
      this.init_flag_tag_group = true;
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
        if (taglists[i].trim().indexOf("-") < 0) {
          tag_list.add(Integer.valueOf(Integer.parseInt(taglists[i].trim())));
        }
        else
        {
          String[] datas = taglists[i].trim().split("-");
          tag_list.add(Integer.valueOf(Integer.parseInt(datas[0].trim())));
        }
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      e.printStackTrace(System.err);
    } catch (NumberFormatException e) {
      System.err.println(e.getMessage());
      System.err.println("NumberFormatException at: " + features);
    }
  }

  private void parseCaglists(HashSet<Integer> tag_list, String features) {
    try {
      String[] taglists = features.split(",");
      for (int i = 0; i < taglists.length; i++) {
        if ((!"".equals(taglists[i].trim())) && (taglists[i].trim().indexOf("-") < 0))
        {
          tag_list.add(Integer.valueOf(Integer.parseInt(taglists[i].trim())));
        }
        else if (taglists[i].trim().indexOf("-") > 0) {
          String[] datas = taglists[i].trim().split("-");
          tag_list.add(Integer.valueOf(Integer.parseInt(datas[0].trim())));
        }
      }
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

  public boolean isNumeric(String str)
  {
    if (str == null) {
      return false;
    }
    if (str.length() == 0) {
      return false;
    }

    boolean regFlag = this.pattern.matcher(str).matches();
    return regFlag;
  }

  public void get_valid_tag_lists(HashSet<Integer> tag_result, String user_data, String features, String cat_id, HashSet<Long> s_pvs, HashSet<Integer> s_sputaglist, Map<Integer, HashSet<Integer>> m_catTagList, Map<Integer, HashSet<Integer>> m_catTagList2, String category_tag_list, String status, String category, String category_level_two, String category_level_three, String category_level_four, String category_level_five)
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
            if (tag_type == 1)
              tag_result.add(Integer.valueOf(tag_id));
            else if (tag_type == 3) {
              if (stags.contains(Integer.valueOf(tag_id))) {
                tag_result.add(Integer.valueOf(tag_id));
              }

            }
            else if ((tag_type == 7) || (tag_type == 8))
            {
              if (m_catTagList2.containsKey(Integer.valueOf(Integer.parseInt(category))))
              {
                if ((tag_type == 7) && (((HashSet)m_catTagList2.get(Integer.valueOf(Integer.parseInt(category)))).contains(Integer.valueOf(tag_id)))) {
                  tag_result.add(Integer.valueOf(tag_id));
                }
                else if ((s_sputaglist.contains(Integer.valueOf(tag_id))) && (((HashSet)m_catTagList2.get(Integer.valueOf(Integer.parseInt(category)))).contains(Integer.valueOf(tag_id))))
                  tag_result.add(Integer.valueOf(tag_id));
              }
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
              if (tag_infos.length == 2) {
                if (tag_type == 4)
                {
                  if ((tag_infos[1].equals(cat_id)) || (tag_infos[1].equals(category_level_two)) || (tag_infos[1].equals(category_level_three)) || (tag_infos[1].equals(category_level_four)) || (tag_infos[1].equals(category_level_five)))
                  {
                    tag_result.add(Integer.valueOf(tag_id));
                  }
                }
                if (tag_type != 9)
                  continue;
                if (!((HashSet)m_catTagList2.get(Integer.valueOf(Integer.parseInt(category)))).contains(Integer.valueOf(tag_id)))
                  continue;
                String parseStatus = "";

                if (!category_tag_list.toString().isEmpty()) {
                  String[] cat_list = category_tag_list.split(",");
                  for (String a : cat_list) {
                    if (a.indexOf("-") > 0) {
                      String[] parses = a.split("-");
                      if (tag_id == Integer.parseInt(parses[0])) {
                        parseStatus = parses[1];
                      }
                    }
                  }
                  if ((parseStatus.equals(status)) || (parseStatus.equals("a")))
                  {
                    if ((tag_infos[1].equals(cat_id)) || (tag_infos[1].equals(category_level_two)) || (tag_infos[1].equals(category_level_three)) || (tag_infos[1].equals(category_level_four)) || (tag_infos[1].equals(category_level_five)))
                    {
                      tag_result.add(Integer.valueOf(tag_id));
                    }
                  }
                }
              }
            }
            else
            {
              if ((2 != index) || 
                (tag_infos.length != 3) || 
                (tag_type != 5))
              {
                continue;
              }

              if ((!tag_infos[1].equals(cat_id)) && (!tag_infos[1].equals(category_level_two)) && (!tag_infos[1].equals(category_level_three)) && (!tag_infos[1].equals(category_level_four)) && (!tag_infos[1].equals(category_level_five)))
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
    UDFgettaglist2 test = new UDFgettaglist2();
  }
}