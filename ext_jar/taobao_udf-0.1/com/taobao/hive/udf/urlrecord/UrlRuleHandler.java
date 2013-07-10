package com.taobao.hive.udf.urlrecord;

import com.taobao.hive.udf.UDFGetValueFromRefer;
import com.taobao.hive.udf.UDFURLDecode;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.io.Text;

public class UrlRuleHandler
{
  private UDFGetValueFromRefer getValueFormRefer = new UDFGetValueFromRefer();
  private UDFURLDecode decode = new UDFURLDecode();
  private boolean initFlag = false;
  private Map<String, RuleURL> ruleURLs = new ConcurrentHashMap();
  private Map<String, RuleRelation> ruleRelation = new ConcurrentHashMap();
  private Map<String, RuleBusiness> ruleBusiness = new ConcurrentHashMap();
  private Map<String, RuleURLProperty> ruleURLProperty = new ConcurrentHashMap();

  public void init() throws IOException {
    if (!this.initFlag) {
      loadData(0, "./rule_type.txt");
      loadData(1, "./rule_url_work.txt");
      loadData(2, "./rule_url.txt");
      loadData(3, "./rule_url_property.txt");
      this.initFlag = true;
    }
  }

  public void loadData(int flag, String file) throws IOException {
    BufferedReader reader = null;
    String line = null;
    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
    while ((line = reader.readLine()) != null)
      try {
        String[] strList = line.split("\"");
        switch (flag) {
        case 2:
          assembleRuleURL(strList);
          break;
        case 0:
          assembleRuleBusiness(strList);
          break;
        case 1:
          assembleRuleRelation(strList);
          break;
        case 3:
          assembleRuleURLProperty(strList);
          break;
        default:
          continue;
        }
      }
      catch (Exception e)
      {
      }
  }

  private void assembleRuleURL(String[] strList) throws Exception {
    if (strList.length < 7) return;
    RuleURL rule = new RuleURL();
    rule.setId(strList[0]);
    rule.setRuleName(strList[1]);
    String ruleValue = strList[2];
    if (!ruleValue.equals(".*")) {
      if (ruleValue.startsWith(".*")) ruleValue = ruleValue.substring(2);
      if (ruleValue.endsWith(".*")) ruleValue = ruleValue.substring(0, ruleValue.length() - 2);
    }
    rule.setRuleValue(ruleValue);
    rule.setPath(strList[3]);
    rule.setStatus(strList[4]);
    rule.setStartDate(strList[5].replace("-", ""));
    rule.setEndDate(strList[6].replace("-", ""));
    rule.setRuleMatcher(Pattern.compile(strList[2]).matcher(""));
    if ("0".equals(rule.getStatus()))
      this.ruleURLs.put(rule.getId(), rule);
  }

  private void assembleRuleRelation(String[] strList) throws Exception
  {
    if (strList.length < 4) return;
    RuleRelation rule = new RuleRelation();
    rule.setId(strList[0]);
    rule.setUrlId(strList[1]);
    rule.setReferId(strList[2]);
    rule.setStatus(strList[3]);
    if ("0".equals(rule.getStatus()))
      this.ruleRelation.put(rule.getId(), rule);
  }

  private void assembleRuleBusiness(String[] strList) throws Exception
  {
    if (strList.length < 7) return;
    RuleBusiness rule = new RuleBusiness();
    rule.setId(strList[0]);
    rule.setStatus(strList[3]);
    rule.setRelationIds(Arrays.asList(strList[6].split(",")));
    if ("0".equals(rule.getStatus()))
      this.ruleBusiness.put(rule.getId(), rule);
  }

  private void assembleRuleURLProperty(String[] strList) throws Exception
  {
    if (strList.length < 13) return;
    RuleURLProperty rule = new RuleURLProperty();
    rule.setId(strList[0]);
    rule.setUrlId(strList[1]);
    rule.setPropertyId(strList[2]);
    rule.setStatus(strList[3]);
    rule.setPropertyName(strList[6]);
    rule.setAtPath(strList[7]);
    rule.setPath(strList[8]);
    rule.setIsDefault(strList[9]);
    rule.setStartPath(strList[10]);
    rule.setEndPath(strList[11]);
    rule.setAscDesc(strList[12]);
    if ("0".equals(rule.getStatus()))
      this.ruleURLProperty.put(strList[1] + "," + strList[2], rule);
  }

  public RuleURL getRuleURL(String ruleId)
  {
    return (RuleURL)this.ruleURLs.get(ruleId);
  }

  public RuleRelation getRuleRelation(String relationId) {
    return (RuleRelation)this.ruleRelation.get(relationId);
  }

  public RuleBusiness getRuleBusiness(String bizId) {
    return (RuleBusiness)this.ruleBusiness.get(bizId);
  }

  public RuleURLProperty getRuleURLProperty(String propertyId) {
    return (RuleURLProperty)this.ruleURLProperty.get(propertyId);
  }

  public Map<String, RuleURL> getRuleURL() {
    return this.ruleURLs;
  }

  public Map<String, RuleRelation> getRuleRelation() {
    return this.ruleRelation;
  }

  public Map<String, RuleBusiness> getRuleBusiness() {
    return this.ruleBusiness;
  }

  public Map<String, RuleURLProperty> getRuleURLProperty() {
    return this.ruleURLProperty;
  }

  public String validate(String ruleId, int type, String time, String url, String prefix)
  {
    RuleURL rule = getRuleURL(ruleId);
    String logTime = time.substring(0, 8);
    if ((rule != null) && (Integer.parseInt(logTime) >= Integer.parseInt(rule.getStartDate())) && (Integer.parseInt(logTime) <= Integer.parseInt(rule.getEndDate())))
    {
      Matcher matcher = rule.getRuleMatcher();
      if (matcher == null) {
        matcher = Pattern.compile(rule.getRuleValue()).matcher(url);
        rule.setRuleMatcher(matcher);
      }
      switch (type) {
      case 0:
        if (((!"0".equals(rule.getPath())) && (!"2".equals(rule.getPath()))) || 
          (matcher == null) || (!matcher.reset(url).find())) break;
        return "true";
      case 1:
        if (("1".equals(rule.getPath())) || ("2".equals(rule.getPath()))) {
          String pre = "";
          if (url != null) {
            pre = this.getValueFormRefer.evaluate(new Text(url), new Text("&"), new Text(prefix)).toString();
            pre = this.decode.evaluate(new Text(pre)).toString();
          }
          if ((matcher != null) && (matcher.reset(url).find())) {
            return "true";
          }
        }
      }
    }
    return "false";
  }

  public boolean validateBiz(String time, String url, String refer, String bizId, String prefix) {
    if ((bizId == null) || ("".equals(bizId))) return false;
    RuleBusiness bizObj = getRuleBusiness(bizId);
    if ((bizObj == null) || (!"0".equals(bizObj.getStatus()))) return false;
    List relationIds = bizObj.getRelationIds();
    if ((relationIds == null) || (relationIds.size() == 0)) return false;
    for (String id : relationIds) {
      RuleRelation obj = getRuleRelation(id);
      if ((obj != null) && ("0".equals(bizObj.getStatus()))) {
        RuleURL urlObj = getRuleURL(obj.getUrlId());
        RuleURL referObj = getRuleURL(obj.getReferId());
        if ((urlObj != null) || (referObj != null))
          if ((urlObj == null) && (referObj != null)) {
            String result = validate(referObj.getId(), 1, time, refer, prefix);
            if ("true".equals(result)) return true; 
          }
          else if ((urlObj != null) && (referObj == null)) {
            String result = validate(urlObj.getId(), 0, time, url, prefix);
            if ("true".equals(result)) return true; 
          }
          else if ((urlObj != null) && (referObj != null)) {
            String result1 = validate(referObj.getId(), 1, time, refer, prefix);
            String result2 = validate(urlObj.getId(), 0, time, url, prefix);
            if (("true".equals(result1)) && ("true".equals(result2))) return true; 
          }
      }
    }
    return false;
  }

  public String getPropValue(String time, String url, String refer, String bizId, String propid, String flag, String prefix) {
    if ((bizId == null) || ("".equals(bizId))) return null;
    RuleBusiness bizObj = getRuleBusiness(bizId);
    if ((bizObj == null) || (!"0".equals(bizObj.getStatus()))) return null;
    List relationIds = bizObj.getRelationIds();
    if ((relationIds == null) || (relationIds.size() == 0)) return null;
    for (String id : relationIds) {
      RuleRelation obj = getRuleRelation(id);
      if ((obj != null) && ("0".equals(bizObj.getStatus()))) {
        RuleURL urlObj = getRuleURL(obj.getUrlId());
        RuleURL referObj = getRuleURL(obj.getReferId());
        if ((urlObj != null) || (referObj != null))
          if ((urlObj == null) && (referObj != null) && ("refer".equals(flag))) {
            String result = validate(referObj.getId(), 1, time, refer, prefix);
            if ("true".equals(result))
              return getValue(referObj.getId(), 1, propid, refer, prefix);
          }
          else if ((urlObj != null) && (referObj == null) && ("url".equals(flag))) {
            String result = validate(urlObj.getId(), 0, time, url, prefix);
            if ("true".equals(result))
              return getValue(urlObj.getId(), 0, propid, refer, prefix);
          }
          else if ((urlObj != null) && (referObj != null)) {
            String result1 = validate(referObj.getId(), 1, time, refer, prefix);
            String result2 = validate(urlObj.getId(), 0, time, url, prefix);
            if (("true".equals(result1)) && ("true".equals(result2))) {
              if ("refer".equals(flag))
                return getValue(referObj.getId(), 1, propid, refer, prefix);
              if ("url".equals(flag))
                return getValue(urlObj.getId(), 0, propid, refer, prefix);
            }
          }
      }
    }
    return null;
  }

  public String getPropValue(String time, String url, String refer, String bizId, String propid, String prefix) {
    if ((bizId == null) || ("".equals(bizId))) return null;
    RuleBusiness bizObj = getRuleBusiness(bizId);
    if ("562".equals(bizId)) {
      System.out.println(1);
    }
    if ((bizObj == null) || (!"0".equals(bizObj.getStatus()))) return null;
    List relationIds = bizObj.getRelationIds();
    if ((relationIds == null) || (relationIds.size() == 0)) return null;
    for (String id : relationIds) {
      RuleRelation obj = getRuleRelation(id);
      if ((obj != null) && ("0".equals(bizObj.getStatus()))) {
        RuleURL urlObj = getRuleURL(obj.getUrlId());
        RuleURL referObj = getRuleURL(obj.getReferId());
        if ((urlObj != null) || (referObj != null))
          if ((urlObj == null) && (referObj != null)) {
            String result = validate(referObj.getId(), 1, time, refer, prefix);
            if ("true".equals(result))
              return getValue(referObj.getId(), 1, propid, refer, prefix);
          }
          else if ((urlObj != null) && (referObj == null)) {
            String result = validate(urlObj.getId(), 0, time, url, prefix);
            if ("true".equals(result))
              return getValue(urlObj.getId(), 0, propid, refer, prefix);
          }
          else if ((urlObj != null) && (referObj != null)) {
            String result1 = validate(referObj.getId(), 1, time, refer, prefix);
            String result2 = validate(urlObj.getId(), 0, time, url, prefix);
            if (("true".equals(result1)) && ("true".equals(result2))) {
              String value1 = getValue(referObj.getId(), 1, propid, refer, prefix);
              String value2 = getValue(urlObj.getId(), 0, propid, refer, prefix);
              value1 = value1 == null ? "" : value1;
              value2 = value2 == null ? "" : value2;
              return value1 + "," + value2;
            }
          }
      }
    }
    return null;
  }

  private String getValueByName(String source, String name)
  {
    if ((source != null) && (source.indexOf("&" + name) < 0) && (source.indexOf("?" + name) < 0)) {
      return null;
    }
    String value = this.getValueFormRefer.evaluate(new Text(source), new Text("&"), new Text(name)).toString();
    return value;
  }

  private String between(String value, String begin, String end) {
    if (("".equals(begin)) && ("".equals(end))) {
      return value;
    }
    int index1 = value.lastIndexOf(begin);
    if ("".equals(begin)) {
      index1 = 0;
    }
    if (!end.equals("end")) {
      int index2 = value.indexOf(end);
      if (("".equals(end)) || (index2 == -1)) {
        index2 = value.length();
      }
      value = value.substring(index1 + begin.length(), index2);
      return value;
    }
    value = value.substring(index1 + begin.length());
    return value;
  }

  private String getLocationDefault(String source, String at_path, String path)
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

  private String getLocationValue(String source, String at_path, String path, String start_path, String end_path, String asc_desc) {
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

  private String getValue(String ruleId, int rule_type, String property_id, String url, String prefix)
  {
    RuleURLProperty prop = getRuleURLProperty(ruleId + "," + property_id);
    if (prop == null) return null;
    switch (rule_type) {
    case 1:
      if ((prop.getPropertyName() != null) && (!prop.getPropertyName().trim().equals(""))) {
        return getValueByName(url, prop.getPropertyName());
      }
      if ("0".equals(prop.getIsDefault())) {
        return getLocationDefault(url, prop.getAtPath(), prop.getPath());
      }
      return getLocationValue(url, prop.getAtPath(), prop.getPath(), prop.getStartPath(), prop.getEndPath(), prop.getAscDesc());
    case 0:
      if ((prop.getPropertyName() != null) && (!prop.getPropertyName().trim().equals(""))) {
        return getValueByName(url, prop.getPropertyName());
      }
      String pre = "";
      if (url != null) {
        pre = this.getValueFormRefer.evaluate(new Text(url), new Text("&"), new Text(prefix)).toString();
        pre = this.decode.evaluate(new Text(pre), "flag1", "flag2").toString();
      }
      if ("0".equals(prop.getIsDefault())) {
        return getLocationDefault(pre, prop.getAtPath(), prop.getPath());
      }
      return getLocationValue(pre, prop.getAtPath(), prop.getPath(), prop.getStartPath(), prop.getEndPath(), prop.getAscDesc());
    }

    return null;
  }

  public String getValidateRel(String time, String url, String refer, String bizId, String prefix) {
    String bizRel = "";
    if ((bizId == null) || ("".equals(bizId))) return "";
    RuleBusiness bizObj = getRuleBusiness(bizId);
    if ((bizObj == null) || (!"0".equals(bizObj.getStatus()))) return "";
    List relationIds = bizObj.getRelationIds();
    if ((relationIds == null) || (relationIds.size() == 0)) return "";
    for (String id : relationIds) {
      RuleRelation obj = getRuleRelation(id);
      if ((obj != null) && ("0".equals(bizObj.getStatus()))) {
        RuleURL urlObj = getRuleURL(obj.getUrlId());
        RuleURL referObj = getRuleURL(obj.getReferId());
        if ((urlObj != null) || (referObj != null))
          if ((urlObj == null) && (referObj != null)) {
            String result = validate(referObj.getId(), 1, time, refer, prefix);
            if ("true".equals(result))
              bizRel = bizRel + bizId + ":" + id + ";";
          }
          else if ((urlObj != null) && (referObj == null)) {
            String result = validate(urlObj.getId(), 0, time, url, prefix);
            if ("true".equals(result))
              bizRel = bizRel + bizId + ":" + id + ";";
          }
          else if ((urlObj != null) && (referObj != null)) {
            String result1 = validate(referObj.getId(), 1, time, refer, prefix);
            String result2 = validate(urlObj.getId(), 0, time, url, prefix);
            if (("true".equals(result1)) && ("true".equals(result2)))
              bizRel = bizRel + bizId + ":" + id + ";";
          }
      }
    }
    return bizRel;
  }

  public String getRelValue(String time, String url, String refer, String bizId, String propid, String prefix) {
    String bizRel = "";
    if ((bizId == null) || ("".equals(bizId))) return "";
    RuleBusiness bizObj = getRuleBusiness(bizId);
    if ((bizObj == null) || (!"0".equals(bizObj.getStatus()))) return "";
    List relationIds = bizObj.getRelationIds();
    if ((relationIds == null) || (relationIds.size() == 0)) return "";
    for (String id : relationIds) {
      RuleRelation obj = getRuleRelation(id);
      if ((obj != null) && ("0".equals(bizObj.getStatus()))) {
        RuleURL urlObj = getRuleURL(obj.getUrlId());
        RuleURL referObj = getRuleURL(obj.getReferId());
        if ((urlObj != null) || (referObj != null))
          if ((urlObj == null) && (referObj != null)) {
            String result = validate(referObj.getId(), 1, time, refer, prefix);
            if ("true".equals(result)) {
              String value = getValue(referObj.getId(), 1, propid, refer, prefix);
              if ((value != null) && (!"".equals(value))) bizRel = bizRel + bizId + ":" + id + ":" + value + ";"; 
            }
          }
          else if ((urlObj != null) && (referObj == null)) {
            String result = validate(urlObj.getId(), 0, time, url, prefix);
            if ("true".equals(result)) {
              String value = getValue(urlObj.getId(), 0, propid, refer, prefix);
              if ((value != null) && (!"".equals(value))) bizRel = bizRel + bizId + ":" + id + ":" + value + ";"; 
            }
          }
          else if ((urlObj != null) && (referObj != null)) {
            String result1 = validate(referObj.getId(), 1, time, refer, prefix);
            String result2 = validate(urlObj.getId(), 0, time, url, prefix);
            if (("true".equals(result1)) && ("true".equals(result2))) {
              String value1 = getValue(referObj.getId(), 1, propid, refer, prefix);
              String value2 = getValue(urlObj.getId(), 0, propid, refer, prefix);
              value1 = value1 == null ? "" : value1;
              value2 = value2 == null ? "" : value2;
              bizRel = bizRel + bizId + ":" + id + ":" + value1 + "," + value2 + ";";
            }
          }
      }
    }
    return bizRel;
  }
}