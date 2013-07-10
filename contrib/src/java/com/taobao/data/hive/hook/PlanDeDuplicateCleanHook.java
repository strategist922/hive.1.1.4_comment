package com.taobao.data.hive.hook;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.hooks.CleanHook;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;

public class PlanDeDuplicateCleanHook implements CleanHook {

  Log LOG = LogFactory.getLog(PlanDeDuplicateCleanHook.class);

  @Override
  public void run(CommandProcessorResponse response, Context context, SessionState ss)
      throws Exception {
    HiveConf conf = ss.getConf();
    // If execute failed, cache nothing.
    if (response.getResponseCode() != 0) {
      return;
    }
    // Do nothing if cache not found.
    List<TaskModel> tms = TaskModelCache.get(context);
    if (tms == null) {
      return;
    }

    PathContext pathctx = new PathContext(context);
    // Move scratchdir to cache dir, prevent Context.clear() to delete them...
    if (!cacheAll(pathctx, conf)) {
      return;
    }

    // Store task models
    StorageHandler<TaskModel> handler =
        StorageHandlerFactory.getRDBMSStorageHandler(conf);
    long now = System.currentTimeMillis();
    SessionState.getConsole().printError("Serializing " + tms.size() + " tasks.");
    for (TaskModel tm : tms) {
      tm.setEndTimeStamp(now);
      handler.put(tm);
      LOG.debug("Task model MD5=" + tm.getMD5() + " serialized.");
    }
    handler.close();
  }

  private boolean cacheAll(PathContext pathctx, HiveConf conf) {
    try {
      if (!cache(pathctx.getScratchdir(), pathctx, conf)) {
        return false;
      }
      for (String e : pathctx.getExternalScratchdirs().values()) {
        URI external = new URI(e);
        URI scratch = new URI(pathctx.getScratchdir());
        URI rel = scratch.relativize(external);
        if (rel.compareTo(external) == 0) {
          if (!cache(e, pathctx, conf)) {
            return false;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private boolean cache(String sourcedir, PathContext pathctx, HiveConf conf) throws IOException {
    String cachedir = pathctx.translateToCache(sourcedir.toString());
    Path cachePath = new Path(cachedir);
    Path cacheRoot = cachePath.getParent();
    FileSystem fs = cachePath.getFileSystem(conf);
    if (!fs.exists(cacheRoot)) {
      if (!fs.mkdirs(cacheRoot)) {
        SessionState.getConsole().printError("mkdir " + cacheRoot + " failed");
        return false;
      }
    }
    if (!fs.rename(new Path(sourcedir), cachePath)) {
      SessionState.getConsole().printError("Caching result from " + sourcedir +
          " to " + cachePath + " failed");
      return false;
    }
    SessionState.getConsole().printError("Caching result from " + sourcedir +
        " to " + cachePath);
    return true;
  }
}
