package com.taobao.data.hive.hook;

import java.io.Serializable;
import java.util.List;

import org.apache.hadoop.hive.ql.plan.FetchWork;
import org.apache.hadoop.hive.ql.plan.MapredWork;

public class WorkReWriterFactory {

  protected final List<String> paths;
  protected final List<String> cachedPaths;
  protected final PathContext pathctx;

  public WorkReWriterFactory(List<String> paths, List<String> cachedPaths, PathContext pathctx) {
    this.paths = paths;
    this.cachedPaths = cachedPaths;
    this.pathctx = pathctx;
  }

  public WorkPathReWriter<?> getPathReWriter(Class<? extends Serializable> clz) {
    if (clz.equals(MapredWork.class)) {
      return new MapredWorkPathReWriter(paths, cachedPaths, pathctx);
    } else if (clz.equals(FetchWork.class)) {
      return new FetchWorkPathReWriter(paths, cachedPaths, pathctx);
    }
    // FIXME: support move work
    return null;
  }
}
