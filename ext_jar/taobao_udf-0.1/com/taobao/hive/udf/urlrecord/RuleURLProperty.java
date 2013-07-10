package com.taobao.hive.udf.urlrecord;

import java.util.regex.Pattern;

public class RuleURLProperty
{
  private String id;
  private String propertyName;
  private String propertyValue;
  private String status;
  private String bitType;
  private String path;
  private String atPath;
  private String isDefault;
  private String startPath;
  private String endPath;
  private String ascDesc;
  private String urlId;
  private String propertyId;
  private Pattern urlPattern;
  private String startDate;
  private String endDate;

  public String getStartDate()
  {
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
  public Pattern getUrlPattern() {
    return this.urlPattern;
  }
  public void setUrlPattern(Pattern urlPattern) {
    this.urlPattern = urlPattern;
  }
  public String getUrlId() {
    return this.urlId;
  }
  public void setUrlId(String urlId) {
    this.urlId = urlId;
  }
  public String getPropertyId() {
    return this.propertyId;
  }
  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }
  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPropertyName() {
    return this.propertyName;
  }
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }
  public String getPropertyValue() {
    return this.propertyValue;
  }
  public void setPropertyValue(String propertyValue) {
    this.propertyValue = propertyValue;
  }
  public String getStatus() {
    return this.status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getBitType() {
    return this.bitType;
  }
  public void setBitType(String bitType) {
    this.bitType = bitType;
  }
  public String getPath() {
    return this.path;
  }
  public void setPath(String path) {
    this.path = path;
  }
  public String getAtPath() {
    return this.atPath;
  }
  public void setAtPath(String atPath) {
    this.atPath = atPath;
  }
  public String getIsDefault() {
    return this.isDefault;
  }
  public void setIsDefault(String isDefault) {
    this.isDefault = isDefault;
  }
  public String getStartPath() {
    return this.startPath;
  }
  public void setStartPath(String startPath) {
    this.startPath = startPath;
  }
  public String getEndPath() {
    return this.endPath;
  }
  public void setEndPath(String endPath) {
    this.endPath = endPath;
  }
  public String getAscDesc() {
    return this.ascDesc;
  }
  public void setAscDesc(String ascDesc) {
    this.ascDesc = ascDesc;
  }
}