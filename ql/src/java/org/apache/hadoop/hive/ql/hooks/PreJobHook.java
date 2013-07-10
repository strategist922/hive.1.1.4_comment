package org.apache.hadoop.hive.ql.hooks;

import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapred.JobConf;

/**
 * PreJobHook.is a hook which is called before job submitted.
 */
public interface PreJobHook {
  /**
   * Run the Pre-Job Hook
   *
   * @param session
   *          The current session state
   * @param queryPlan
   *          The current query plan
   * @param job
   *          The current configuration of the job
   * @param taskId
   *          The current task id (stage id)
   */
  public void run(SessionState session, QueryPlan queryPlan, JobConf job, Integer taskId)
      throws Exception;
}
