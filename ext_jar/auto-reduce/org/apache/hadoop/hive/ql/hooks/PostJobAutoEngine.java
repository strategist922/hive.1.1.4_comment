package org.apache.hadoop.hive.ql.hooks;

import java.io.IOException;
import org.apache.hadoop.common.SkyNetException;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.mapred.CounterU;
import org.apache.hadoop.mapred.Counters;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.mapred.RunningJob;

public class PostJobAutoEngine
  implements PostJobHook
{
  public void run(SessionState session, QueryPlan queryPlan, JobConf job, RunningJob runningJob, Integer taskId)
  {
    SessionState.LogHelper console = SessionState.getConsole();
    String className = getClass().getSimpleName();
    try
    {
      if (2 != runningJob.getJobState()) {
        console.printError(className + ": Job is not successful.");
        return;
      }
    } catch (IOException e1) {
      e1.printStackTrace();
      return;
    }

    if (!job.getBoolean("hive.job.hooks.automized.enable", false))
    {
      return;
    }

    int reduceNum = job.getInt("mapred.reduce.tasks", 0);
    if (reduceNum == 0) {
      console.printError(className + ": reduce num = " + reduceNum);
      return;
    }

    String md5 = job.get("hive.job.hooks.autored.md5", "");
    if ("".equals(md5)) {
      console.printError(className + ": Error = Query string md5sum missing!");
      return;
    }

    int stageId = taskId.intValue();

    String jobId = "" + runningJob.getID().getId();
    Counters cs;
    try {
      cs = runningJob.getCounters();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return;
    }
    long reduceBytes = CounterU.getReduceOutputBytes(cs);

    if (reduceBytes < 0L) {
      console.printError(className + ": Info = reduce output bytes [" + reduceBytes + "]");
      return;
    }
    DataUtil ri;
    try {
      ri = new DataUtil(job);
    } catch (SkyNetException sne) {
      return;
    }
    catch (Exception e) {
      console.printError(className + ": Error = " + e.getMessage());
      return;
    }

    if (!ri.updateToDB(stageId, md5, jobId, reduceBytes, CounterU.getCounterInfos(cs))) {
      console.printError(className + ": Error = failed to update reduce info to DB.");
      return;
    }
  }
}