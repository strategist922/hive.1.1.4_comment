package com.taobao.data.hive.hook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class WorkPathReWriter<T> {
  protected final List<String> paths;
  protected final List<String> cachedPaths;
  protected final PathContext pathctx;

  public WorkPathReWriter(List<String> paths, List<String> cachedPaths,
      PathContext pathctx) {
    this.paths = paths;
    this.cachedPaths = cachedPaths;
    this.pathctx = pathctx;
  }

  abstract public void rewrite(T work);

  protected void rewriteList(List<String> arr) {
    if (arr == null || arr.isEmpty()) {
      return;
    }
    String replacement;
    //TODO: Optimize it for LinkedList
    for (int i=0 ;i< arr.size(); i++) {
      replacement = getReplacement(arr.get(i));
      arr.set(i, replacement);
    }
  }

  protected <V> void rewriteMapKeys(Map<String, V> map) {
    // This map is to prevent ConcurrentModificationException
    Map<String, String> replaceMap = new HashMap<String, String>();
    // Collect all keys to replace
    for (String e : map.keySet()) {
      String replaceInput = getReplacement(e);
      if (replaceInput != null) {
        replaceMap.put(e, replaceInput);
      }
    }
    // Replace keys
    for (Entry<String, String> entry : replaceMap.entrySet()) {
      String formerKey = entry.getKey();
      String replaceInput = entry.getValue();
      V value = map.get(formerKey);
      map.remove(formerKey);
      map.put(replaceInput, value);
    }
  }

  protected String getReplacement(String path) {
    String cachedPath = pathctx.translateToCache(path);
    int idx = paths.indexOf(cachedPath);
    if (idx < 0) {
      return path;
    }
    return cachedPaths.get(idx);
  }
}
