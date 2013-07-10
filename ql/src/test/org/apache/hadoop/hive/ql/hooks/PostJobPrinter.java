package org.apache.hadoop.hive.ql.hooks;

import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;

public class PostJobPrinter implements PostJobHook {

  @Override
  public void run(SessionState session, QueryPlan queryPlan, JobConf job,
      RunningJob runningJob, Integer taskId) {
    LogHelper console = SessionState.getConsole();
    if (console == null) {
      return;
    }

    if (session != null) {
      console.printError("POSTJOBHOOK: query: " + session.getCmd().trim());
      console.printError("POSTJOBHOOK: type: " + session.getCommandType());
    }

    if (job != null) {
      console.printError("POSTJOBHOOK: job name: " + job.getJobName());
    }

    if (queryPlan != null) {
      console.printError("POSTJOBHOOK: query id: " + queryPlan.getQueryId());
    }

    if (runningJob != null) {
      console.printError("POSTJOBHOOK: running job: " + runningJob.getJobName());
    }

    if (taskId != null) {
      console.printError("POSTJOBHOOK: task id: " + taskId);
    }
  }

}
