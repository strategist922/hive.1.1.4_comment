package com.taobao.hive.udf.urlrecord;

import java.util.regex.Matcher;

public class RuleURL
{
  private String id;
  private String ruleName;
  private String ruleValue;
  private String propertyId;
  private String startDate;
  private String endDate;
  private String status;
  private String path;
  private Matcher ruleMatcher;

  public String getPath()
  {
    return this.path;
  }
  public void setPath(String path) {
    this.path = path;
  }
  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getRuleName() {
    return this.ruleName;
  }
  public void setRuleName(String ruleName) {
    this.ruleName = ruleName;
  }
  public String getRuleValue() {
    return this.ruleValue;
  }
  public void setRuleValue(String ruleValue) {
    this.ruleValue = ruleValue;
  }
  public String getPropertyId() {
    return this.propertyId;
  }
  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }
  public String getStartDate() {
    return this.startDate;
  }
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  public String getEndDate() {
    return this.endDate;
  }
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  public String getStatus() {
    return this.status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public Matcher getRuleMatcher() {
    return this.ruleMatcher;
  }
  public void setRuleMatcher(Matcher ruleMatcher) {
    this.ruleMatcher = ruleMatcher;
  }
}