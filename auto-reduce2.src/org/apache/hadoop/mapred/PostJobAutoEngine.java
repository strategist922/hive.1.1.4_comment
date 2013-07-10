package org.apache.hadoop.mapred;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.hadoop.common.SkynetException;
import org.apache.hadoop.mapred.fingerprint.Fingerprint;
import org.apache.hadoop.mapred.fingerprint.JobConfFeature;

public class PostJobAutoEngine
  implements PostJobHook
{
  boolean isDebug;

  public void run(JobID id, JobConf conf, RunningJob runningJob)
    throws Exception
  {
    String className = getClass().getSimpleName();
    this.isDebug = conf.getBoolean("auto.script.print.more", false);

    if (conf.get("hive.job.hooks.autored.enable") != null) {
      print2Console(this.isDebug, className + "Hive AutoReduce is Already run out.");

      return;
    }

    if (!conf.getBoolean("mapred.client.hooks.automized.enabled", false)) {
      print2Console(this.isDebug, className + ": Auto Switch is Disabled.");
      return;
    }

    if (2 != runningJob.getJobState()) {
      print2Console(this.isDebug, className + ": Job is not successful.");
      return;
    }

    int reduceNum = conf.getInt("mapred.reduce.tasks", 0);
    if (reduceNum == 0) {
      print2Console(this.isDebug, className + ": reduce num = " + reduceNum);
      return;
    }

    String md5 = conf.get("mapred.client.hooks.autored.md5", "");
    if ("".equals(md5)) {
      Fingerprint fingerprint = new Fingerprint();
      JobConfFeature jcf = new JobConfFeature(id.toString(), conf);
      fingerprint.addFeature(jcf);
      md5 = fingerprint.toString();
      conf.set("mapred.client.hooks.autored.md5", md5);
    }

    String jobId = id.toString();
    Counters cs;
    try
    {
      cs = runningJob.getCounters();
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    long reduceBytes = CounterUti.getReduceOutputBytes(cs);

    if (reduceBytes < 0L) {
      print2Console(this.isDebug, className + ": Info = reduce output bytes [" + reduceBytes + "]");

      return;
    }
    DataUtil ri;
    try {
      ri = new DataUtil(conf);
    } catch (SkynetException sne) {
      return;
    }
    catch (Exception e) {
      print2Console(this.isDebug, className + ": Error = " + e.getMessage());
      return;
    }

    if (!ri.updateToDB(-1, md5, jobId, reduceBytes, CounterUti.getCounterInfos(cs)))
    {
      print2Console(this.isDebug, className + ": Error = failed to update reduce info to DB.");

      return;
    }

    ri.close();
  }

  public static void print2Console(boolean debug, String info) {
    if (debug)
      System.err.println(info);
  }
}