package com.taobao.data.hive.qa;

import static org.apache.hadoop.util.StringUtils.stringifyException;

import java.net.URI;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreHook;
import org.apache.hadoop.hive.metastore.api.InvalidOperationException;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.HiveMetaDataHook;
import org.apache.hadoop.hive.ql.metadata.Partition;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.session.SessionState;

public class QAReplaceDir implements HiveMetaDataHook, HiveMetaStoreHook {

  static final private Log LOG = LogFactory.getLog("com.taobao.data.hive.qa.QAReplaceDir");

  @Override
  public String run(Hive hive, Table table, Partition part, Map<String, String> partSpec,
      boolean replace, String pathLocation) throws Exception {
//  if(replace) {

    String message = "before replace Location is "+pathLocation;
    String rd = replaceDir(pathLocation);
    if( rd != null ) {
      pathLocation = rd;
      if (!table.isPartitioned()) { // table
        try {
          table.setDataLocation( new URI(pathLocation) );
          hive.alterTable(table.getTableName(), table);
          message = message + " after replace Location is "+pathLocation;
          LOG.info("QA.REPLACE table dir happen "+message);
        } catch (Exception e) {
          LOG.error("alter Table: " + stringifyException(e));
          throw new HiveException(e);
        }
      } else { // partition
        if(part==null) { // partition does not exist, create it
          part = hive.getPartition(table, partSpec, true);
        }
        part.setLocation(pathLocation);
        try {
          hive.alterPartition(table.getTableName(), part);
          message = message + " after replace Location is "+pathLocation;
          LOG.info("QA.REPLACE partition dir happen "+message);
        } catch (InvalidOperationException e) {
          LOG.error("alter Partition: " + stringifyException(e));
          throw new HiveException(e.getMessage());
        }
      }
      LOG.debug(" pathLocation finally is "+pathLocation);
      return pathLocation;
    } // pathLocation.startsWith(srcPrefix)
//  } // replace

    return null;
  }

  @Override
  public String replaceDir(String pathLocation) {
    LOG.debug(" pathLocation is "+pathLocation+" . This location if proper will be replace!");
    SessionState ss = SessionState.get();
    HiveConf conf = ss.getConf();

    String srcPrefixArray = conf.get("qa.source.prefix");
    srcPrefixArray = srcPrefixArray.trim();
    if (srcPrefixArray.equals("")) {
      return null;
    }
    String[] srcPrefixs = srcPrefixArray.split(",");
    LOG.debug(" qa.source.prefix is "+srcPrefixArray);
    String destPrefix = conf.get("qa.destination.prefix");
    if (destPrefix.equals("")) {
      return null;
    }
    try {
      FileSystem fs = FileSystem.get(conf);
      pathLocation = new Path(pathLocation).makeQualified(fs).toString();
      destPrefix = new Path(destPrefix).makeQualified(fs).toString();

      String message = "before replace Location is "+pathLocation;
      // iterator the qa.source.prefix
      for(String srcPrefix : srcPrefixs) {
        srcPrefix = new Path(srcPrefix).makeQualified(fs).toString();
        LOG.debug(" srcPrefix "+srcPrefix);
        if(pathLocation.startsWith(srcPrefix)) {
          LOG.debug(" pathLocation.startsWith(srcPrefix) is true. ");
          pathLocation = pathLocation.replaceFirst(srcPrefix, destPrefix);
          LOG.debug(" pathLocation finally is "+pathLocation);
          return pathLocation;
        } // pathLocation.startsWith(srcPrefix)
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }
}