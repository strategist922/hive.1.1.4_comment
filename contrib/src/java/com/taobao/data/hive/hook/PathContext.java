package com.taobao.data.hive.hook;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.Context;

public class PathContext {
  public static final String CACHEDIR_PREFIX = "cache";

  String scratchdir;
  Map<String, String> externalScratchdirs;

  public String getScratchdir() {
    return scratchdir;
  }

  public void setScratchdir(String scratchdir) {
    this.scratchdir = scratchdir;
  }

  public Map<String, String> getExternalScratchdirs() {
    return externalScratchdirs;
  }

  public void setExternalScratchdirs(Map<String, String> externalScratchdirs) {
    this.externalScratchdirs = externalScratchdirs;
  }

  public PathContext(String scratchdir, Map<String, String> externalScratchdirs) {
    super();
    this.scratchdir = scratchdir;
    this.externalScratchdirs = externalScratchdirs;
  }

  public PathContext(Context ctx) {
    super();
    this.scratchdir = ctx.getMRScratchDir();
    this.externalScratchdirs = ctx.getFsScratchDirs();
  }

  public String translateToCache(String src) {
    if (src.contains(scratchdir.toString())) {
      return getCachePath(src, scratchdir.toString(), CACHEDIR_PREFIX);
    }
    for (Entry<String, String> e : externalScratchdirs.entrySet()) {
      String externalScratch = e.getValue().toString();
      if (src.contains(externalScratch)) {
        return getCachePath(src, externalScratch, CACHEDIR_PREFIX);
      }
    }

    assert false : String.format(
          "Path to process %s is neither under scratchdir %s nor externalScratchdirs",
          src, scratchdir);
    return null;
  }

  public String translateToSource(Path cachePath) {
    return null;
  }

  private String getCachePath(String src, String scratch, String cacheString) {
    String root = (new Path(scratch)).getParent().toString();
    int idx = src.indexOf(root) + root.length();
    String postfix = src.substring(idx);
    String prefix = src.substring(0, idx);
    return prefix + Path.SEPARATOR + cacheString + postfix;
  }

  public boolean isScratch(String dir) {
    if (dir.contains(scratchdir.toString())) {
      return true;
    }
    for (Entry<String, String> e : externalScratchdirs.entrySet()) {
      String externalScratch = e.getValue();
      if (dir.contains(externalScratch)) {
        return true;
      }
    }
    return false;
  }

}
