package com.taobao.hive.udf.search;

import org.apache.hadoop.io.Text;

public class CommonData
{
  public static final Text SPLIT_CHAR = new Text("\\?|&");

  public static final Text REPLACED_CHAR = new Text("\\x");

  public static final Text REPLACE_CHAR = new Text("%");

  public static final Text QUERY_CHAR = new Text("q=");
}