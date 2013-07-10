package com.taobao.hive.udf.urlrecord;

public class RuleRelation
{
  private String id;
  private String relationName;
  private String urlId;
  private String referId;
  private String status;

  public String getId()
  {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getRelationName() {
    return this.relationName;
  }
  public void setRelationName(String relationName) {
    this.relationName = relationName;
  }
  public String getUrlId() {
    return this.urlId;
  }
  public void setUrlId(String urlId) {
    this.urlId = urlId;
  }
  public String getReferId() {
    return this.referId;
  }
  public void setReferId(String referId) {
    this.referId = referId;
  }
  public String getStatus() {
    return this.status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
}