package com.taobao.data.hive.hook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.hadoop.hive.ql.plan.MapredWork;

public class MapredWorkPathReWriter extends WorkPathReWriter<MapredWork> {

  public MapredWorkPathReWriter(List<String> paths, List<String> cachedPaths, PathContext pathctx) {
    super(paths, cachedPaths, pathctx);
  }

  @Override
  public void rewrite(MapredWork work) {
    // pathToAliases
    // replace key and value(ArrayList)
    rewriteMapKeys(work.getPathToAliases());
    for (Entry<String, ArrayList<String>> e : work.getPathToAliases().entrySet()) {
      rewriteList(e.getValue());
    }
    // pathToPartitionInfo
    rewriteMapKeys(work.getPathToPartitionInfo());
    // aliasToWork
    // internal table scan will use data path rather than alias.
    rewriteMapKeys(work.getAliasToWork());
  }
}
