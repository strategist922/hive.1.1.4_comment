package com.taobao.ad.data.search.udf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

class Gbk2GbkSimplified
{
  public int[] gbkTable;
  private static Gbk2GbkSimplified instance = null;

  private Gbk2GbkSimplified()
  {
    this.gbkTable = new int[48132];
    BufferedReader buf = null;
    try {
      buf = new BufferedReader(new FileReader(new File(Gbk2GbkSimplified.class.getResource("").getPath() + "/gbk.txt")));
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (buf == null)
      return;
    String line = null;
    try {
      int count = 0;
      while ((line = buf.readLine()) != null) {
        String tmp = line.substring(11, 20);
        String[] strlist = tmp.split(",");
        this.gbkTable[(count++)] = Integer.valueOf(strlist[0].substring(2), 16).intValue();
        this.gbkTable[(count++)] = Integer.valueOf(strlist[1].substring(2), 16).intValue();
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  public static synchronized Gbk2GbkSimplified getInstance()
  {
    if (instance == null) {
      instance = new Gbk2GbkSimplified();
    }
    return instance;
  }
}