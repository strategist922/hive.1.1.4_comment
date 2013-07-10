package com.taobao.dw.hive.hook.exstore.common;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.parse.HiveSemanticAnalyzerHookContext;

public class HFileSystem
{
  private static Pattern _done = Pattern.compile("\\d{8}\\.done");
  private static DonePathFilter filter = new DonePathFilter();
  private static HiveSemanticAnalyzerHookContext context;
  private static FileSystem fileSystem;
  private Map<String, Path> table2Path;

  public HFileSystem()
  {
    this.table2Path = new HashMap();
  }

  private Path getTablePath(String tableName) throws ExStoreException {
    Path path = (Path)this.table2Path.get(tableName);
    if (path == null) {
      Hive hive = null;
      try {
        hive = context.getHive();
        if (hive == null) {
          throw new ExStoreException("Hive context is null.", -1);
        }
        Table table = hive.getTable(tableName);
        if (table == null) {
          throw new ExStoreException("Table " + tableName + " is not found in HIVE.", -2);
        }

        path = table.getPath();
        this.table2Path.put(tableName, path);
      } catch (HiveException e) {
        throw new ExStoreException(e, 0);
      }
    }
    return path;
  }

  public FileStatus[] listDone(String tableName, String yyyyMM)
    throws ExStoreException
  {
    Path tablePath = getTablePath(tableName);
    Path year = new Path(tablePath, yyyyMM);
    FileStatus[] listStatus = null;
    if (fileSystem == null)
      try {
        fileSystem = tablePath.getFileSystem(context.getConf());
      } catch (IOException e) {
        e.printStackTrace();
      }
    try
    {
      if (fileSystem != null)
        listStatus = fileSystem.listStatus(year, filter);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return listStatus;
  }

  private static List<String> getMonthList(Interval interval)
    throws ParseException
  {
    String start = interval.getStart();
    Date parse = Util.yyyyMM.parse(start.substring(0, 6));
    Util.cal.setTime(parse);
    Date end = Util.yyyyMM.parse(interval.getEnd().substring(0, 6));
    List ml = new ArrayList();
    while (Util.cal.getTimeInMillis() <= end.getTime()) {
      ml.add(Util.yyyyMM.format(Util.cal.getTime()));
      Util.cal.add(2, 1);
    }
    return ml;
  }

  public FileStatus[] listDone(String tableName, Interval interval) throws ExStoreException
  {
    List monthList = null;
    try {
      monthList = getMonthList(interval);
    } catch (ParseException e) {
      e.printStackTrace();
      throw new ExStoreException(e, 1);
    }
    List fl = new ArrayList();

    for (String month : monthList) {
      FileStatus[] fileStatuses = listDone(tableName, month);
      for (FileStatus fs : fileStatuses) {
        fl.add(fs);
      }
    }
    FileStatus[] fs = new FileStatus[fl.size()];
    return (FileStatus[])fl.toArray(fs);
  }

  public boolean isLock(String tableName, String yearAndMonth) throws ExStoreException
  {
    Path tablePath = getTablePath(tableName);

    if (fileSystem == null)
      try {
        fileSystem = tablePath.getFileSystem(context.getConf());
      } catch (IOException e) {
        e.printStackTrace();
      }
    try
    {
      if (fileSystem == null) {
        fileSystem = tablePath.getFileSystem(context.getConf());
      }
      Path newpath = new Path(tablePath, yearAndMonth + "/.lock");
      return fileSystem.exists(newpath);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;
  }

  public Map<String, Path> getTable2Path() {
    return this.table2Path;
  }

  public void setTable2Path(Map<String, Path> table2Path) {
    this.table2Path = table2Path;
  }

  public static HiveSemanticAnalyzerHookContext getContext() {
    return context;
  }

  public static void setContext(HiveSemanticAnalyzerHookContext context) {
    context = context;
  }

  public FileSystem getFileSystem() {
    return fileSystem;
  }

  static class DonePathFilter
    implements PathFilter
  {
    public boolean accept(Path path)
    {
      String name = path.getName();
      Matcher matcher = HFileSystem._done.matcher(name);
      return matcher.matches();
    }
  }
}