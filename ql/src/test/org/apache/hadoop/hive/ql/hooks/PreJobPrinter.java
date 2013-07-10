package org.apache.hadoop.hive.ql.hooks;

import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.mapred.JobConf;

public class PreJobPrinter implements PreJobHook {

  @Override
  public void run(SessionState session, QueryPlan queryPlan, JobConf job, Integer taskId) {
    LogHelper console = SessionState.getConsole();
    if (console == null) {
      return;
    }

    if (session != null) {
      console.printError("PREJOBHOOK: query: " + session.getCmd().trim());
      console.printError("PREJOBHOOK: type: " + session.getCommandType());
    }

    if (job != null) {
      console.printError("PREJOBHOOK: job name: " + job.getJobName());
    }

    if (queryPlan != null) {
      console.printError("PREJOBHOOK: query id: " + queryPlan.getQueryId());
    }

    if (taskId != null) {
      console.printError("PREJOBHOOK: task id: " + taskId);
    }
  }

}
