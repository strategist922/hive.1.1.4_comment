package com.taobao.data.hive.hook;

import java.util.List;

import org.apache.hadoop.hive.ql.plan.FetchWork;

public class FetchWorkPathReWriter extends WorkPathReWriter<FetchWork> {

  public FetchWorkPathReWriter(List<String> paths, List<String> cachedPaths, PathContext pathctx) {
    super(paths, cachedPaths, pathctx);
  }

  @Override
  public void rewrite(FetchWork work) {
    String tableDir = work.getTblDir();
    if (tableDir != null && !tableDir.isEmpty()) {
      String replaceInput = getReplacement(tableDir);
      assert (replaceInput != null);
      work.setTblDir(replaceInput);
    }
    rewriteList(work.getPartDir());
  }
}
