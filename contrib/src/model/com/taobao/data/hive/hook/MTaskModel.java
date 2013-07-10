package com.taobao.data.hive.hook;

public class MTaskModel {
  private String MD5;
  private String XMLString;

  public MTaskModel() {
  }

  public MTaskModel(String MD5, String XMLString) {
    this.MD5 = MD5;
    this.XMLString = XMLString;
  }

  public String getMD5() {
    return MD5;
  }

  public void setMD5(String MD5) {
    this.MD5 = MD5;
  }

  public String getXMLString() {
    return XMLString;
  }

  public void setXMLString(String XMLString) {
    this.XMLString = XMLString;
  }
}
