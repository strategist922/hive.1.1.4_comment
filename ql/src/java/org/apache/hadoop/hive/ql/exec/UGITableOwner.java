package org.apache.hadoop.hive.ql.exec;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.session.SessionState;

public class UGITableOwner implements DefaultTableOwner{

  private static final Log LOG = LogFactory.getLog("hive.ql.exec.UGITableOwner");

  @Override
  public void setDefaultTableOwner(SessionState ss, HiveConf conf) throws IOException {
    if(ss != null && conf!=null) {
      LOG.info("use ugi '"+conf.getUser()+"' as table owner");
      ss.setUgi(conf.getUser());
    }
  }

  @Override
  public Configuration getConf() {
    return null;
  }

  @Override
  public void setConf(Configuration conf) {
  }

}
