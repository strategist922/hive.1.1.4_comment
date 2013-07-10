package com.taobao.dw.hive.hook.exstore.common;

import com.taobao.dw.hive.hook.exstore.node.Table;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.ParseDriver;

public class Util
{
  public static Calendar cal = Calendar.getInstance();
  public static ParseDriver parseDriver = new ParseDriver();

  public static SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");
  public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

  private static void ap(ASTNode parse, int i) {
    if (null == parse)
      return;
    ArrayList children = parse.getChildren();
    for (int j = 0; j < i; j++)
      System.err.print("|\t");
    System.err.println(parse);
    if (null != children)
      for (Node n : children)
        ap((ASTNode)n, i + 1);
  }

  public static String getDateString(String dateStr, int date)
  {
    try
    {
      Date time = yyyyMMdd.parse(dateStr);
      cal.setTime(time);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    cal.add(5, date);
    Date time = cal.getTime();
    String dateString = yyyyMMdd.format(time);
    return dateString;
  }

  public static String checkPtValue(String value) {
    String substring = "";
    switch (value.length()) {
    case 16:
      substring = value.substring(1, 9);
      break;
    case 14:
      substring = value.substring(0, 8);
    }
    try
    {
      Integer.parseInt(substring);
    } catch (Exception e) {
      throw new ExStoreException("pt can't involved in the calculation", 3);
    }
    return substring;
  }

  public static void astNoteDumper(ASTNode parse) {
    ap(parse, 0);
  }

  public static <T> String dump(Collection<T> values) {
    if (null == values)
      return "";
    StringBuilder sb = new StringBuilder();
    sb.append(new StringBuilder().append(values.size()).append(" [").toString());
    for (Iterator i$ = values.iterator(); i$.hasNext(); ) { Object e = i$.next();
      sb.append(e).append(", ");
    }
    sb.append("] ");
    return sb.toString();
  }

  public static <K, T> String dump(Map<K, T> m) {
    if (null == m)
      return "";
    Set entrySet = m.entrySet();
    StringBuilder sb = new StringBuilder();
    sb.append(new StringBuilder().append(m.size()).append(" [").toString());
    for (Map.Entry e : entrySet) {
      sb.append(new StringBuilder().append(e.getKey()).append(":").append(e.getValue()).toString()).append(", ");
    }
    sb.append("] ");
    return sb.toString();
  }

  public static void dumpTables(Map<String, Table> tables) {
    Set entrySet = tables.entrySet();
    for (Map.Entry e : entrySet) {
      System.err.println(e.getValue());
      System.err.println(new StringBuilder().append("\tColumns:").append(dump(((Table)e.getValue()).getColumns())).toString());
      System.err.println(new StringBuilder().append("\tSelectColumns:").append(dump(((Table)e.getValue()).getSelectColumns())).toString());
      System.err.println(new StringBuilder().append("\tWhereColumns:").append(dump(((Table)e.getValue()).getWhereColumns())).toString());
      System.err.println(new StringBuilder().append("\tOnColumns:").append(dump(((Table)e.getValue()).getOnColumns())).toString());
      System.err.println(new StringBuilder().append("\tPtColumns:").append(dump(((Table)e.getValue()).getPtColumns())).toString());
      System.err.println();
    }
  }
}