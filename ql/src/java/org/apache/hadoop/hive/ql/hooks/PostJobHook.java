package org.apache.hadoop.hive.ql.hooks;

import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;

public interface PostJobHook {
  /**
   * Run the Post-Job Hook
   *
   * @param session
   *          The current session state
   * @param queryPlan
   *          The current query plan
   * @param job
   *          The current configuration of the plan
   * @param runningJob
   *          The running job, just finished job
   * @param taskId
   *          The current task id (stage id)
   */
  public void run(SessionState session, QueryPlan queryPlan, JobConf job,
      RunningJob runningJob, Integer taskId);
}
