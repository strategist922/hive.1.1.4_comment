package com.taobao.data.hive.hook;

import java.util.Set;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.hooks.PreExecute;
import org.apache.hadoop.hive.ql.hooks.ReadEntity;
import org.apache.hadoop.hive.ql.hooks.WriteEntity;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.mapred.JobConf;

/**
 * Implementation of a pre execute hook that simply prints out its parameters to
 * standard output.
 */
public class PreExecuteParamSetter implements PreExecute {

  public enum ParamEntry {
    SKYNET_PRIORITY("SKYNET_PRIORITY", "mapred.job.level"), 
    SKYNET_ID("SKYNET_ID", "mapred.job.skynet_id"), 
    SKYNET_AUCTIONID("SKYNET_ACTIONID", "mapred.job.skynet.actionid"), 
    SKYNET_JOBID("SKYNET_JOBID", "mapred.job.skynet.jobid"), 
    SKYNET_BIZDATE("SKYNET_BIZDATE", "mapred.job.skynet.bizdate");

    String env;
    String param;

    ParamEntry(String env, String param) {
      this.env = env;
      this.param = param;
    }
  }
  
  private static String SPECULATIVE_REDUCE_PRIORITY_THRESHOLD = "skynet.priority.reduce.speculative.threshold";

  LogHelper console = SessionState.getConsole();

  protected boolean setEnv(SessionState sess, ParamEntry e) {
    String value = System.getenv(e.env);
    if (value == null)
      return false;
    if (sess != null) {
      sess.getConf().set(e.param, value);
    }
    return true;
  }
  
  protected void setSpeculativeExecute(SessionState sess) {
    HiveConf conf = sess.getConf();
    int joblevel = 0;
    try {
      joblevel = Integer.parseInt(System.getenv(ParamEntry.SKYNET_PRIORITY.env));
    } catch (NumberFormatException e) {
      // ignore
    }
    int threashold = conf.getInt(SPECULATIVE_REDUCE_PRIORITY_THRESHOLD, -1);
    if (joblevel <= threashold){
      conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
      conf.setBoolean("hive.mapred.reduce.tasks.speculative.execution", false);
    }
  }

  @Override
  public void run(SessionState sess, Set<ReadEntity> inputs,
      Set<WriteEntity> outputs, UserGroupInformation ugi) throws Exception {
    setSpeculativeExecute(sess);
    for (ParamEntry e : ParamEntry.values() ) {
      setEnv(sess, e);
    }
  }

}
