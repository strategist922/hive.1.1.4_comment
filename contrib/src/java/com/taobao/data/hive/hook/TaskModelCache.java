package com.taobao.data.hive.hook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.hadoop.hive.ql.Context;

public class TaskModelCache {
  private static HashMap<Context, List<TaskModel>> TaskModelCache =
      new HashMap<Context, List<TaskModel>>();

  private static long RetireInteval = 6 * 3600 * 1000;
  private static int CheckSize = 100;

  public static void put(Context ctx, TaskModel tm) {
    List<TaskModel> tms = TaskModelCache.get(ctx);
    if (tms == null) {
      tms = new ArrayList<TaskModel>();
      TaskModelCache.put(ctx, tms);
    }
    tms.add(tm);
    if (TaskModelCache.size() >= CheckSize) {
      checkRetire();
    }
  }

  public static void put(Context ctx, List<TaskModel> tms) {
    TaskModelCache.put(ctx, tms);
    if (TaskModelCache.size() >= CheckSize) {
      checkRetire();
    }
  }

  private static void checkRetire() {
    long now = System.currentTimeMillis();
    for (Entry<Context, List<TaskModel>> e : TaskModelCache.entrySet()) {
      if (e.getValue().get(0).getStartTimeStamp() - now > RetireInteval) {
        TaskModelCache.remove(e.getKey());
      }
    }
  }

  public static List<TaskModel> get(Context ctx) {
    return TaskModelCache.get(ctx);
  }
}
