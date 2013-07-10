package org.apache.hadoop.hive.ql.exec;

import java.io.IOException;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.session.SessionState;

public interface DefaultTableOwner extends Configurable {

  public void setDefaultTableOwner(SessionState ss,  HiveConf conf) throws IOException;

}
