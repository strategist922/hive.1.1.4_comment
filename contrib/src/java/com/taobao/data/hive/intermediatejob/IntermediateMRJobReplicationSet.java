package com.taobao.data.hive.intermediatejob;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.hooks.PreJobHook;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapred.JobConf;

public class IntermediateMRJobReplicationSet implements PreJobHook{

  protected Log LOG = LogFactory.getLog(this.getClass().getName());

  @Override
  public void run(SessionState session, QueryPlan queryPlan, JobConf job, Integer taskId)
      throws Exception {
    boolean intermediateJob = HiveConf.getBoolVar(job, HiveConf.ConfVars.INTERMEDIATEJOB);
    if (intermediateJob)  {
      int replication = job.getInt("hive.exec.intermediate.job.replication", 2);
      job.setInt("dfs.replication", replication);
      LOG.info(job.get("mapred.job.name")+" set "+ taskId +" dfs.replication =  "+replication);
    }
  }

}
