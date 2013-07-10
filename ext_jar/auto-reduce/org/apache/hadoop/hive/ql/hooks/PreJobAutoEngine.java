package org.apache.hadoop.hive.ql.hooks;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javax.script.ScriptException;
import org.apache.hadoop.common.SkyNetException;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.exec.ConditionalTask;
import org.apache.hadoop.hive.ql.exec.Task;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.script.ScriptInvocate;

public class PreJobAutoEngine
  implements PreJobHook
{
  private Set<String> visitedSet;
  private Queue<Task<? extends Serializable>> q;
  static boolean isDebug;
  String className;

  public PreJobAutoEngine()
  {
    this.visitedSet = new HashSet();
    this.q = new LinkedList();

    this.className = getClass().getSimpleName();
  }

  public void run(SessionState session, QueryPlan queryPlan, JobConf jobConf, Integer taskId)
    throws Exception
  {
    isDebug = jobConf.getBoolean(AutoConstants.PRINTMORE, false);

    if (queryPlan == null) {
      print2Console(isDebug, this.className + ": Info = map only job, QueryPlan is null");

      jobConf.setBoolean("hive.job.hooks.automized.enable", false);
      return;
    }
    initJobConf(queryPlan, jobConf, taskId);

    int timespan = jobConf.getInt("hive.job.hooks.autored.timespan", 7);

    String md5 = jobConf.get("hive.job.hooks.autored.md5", "");
    if ("".equals(md5)) {
      print2Console(isDebug, this.className + ":md5sum is missing!");
      return;
    }

    executeScript(jobConf, taskId.intValue(), md5, timespan);
  }

  public static void executeScript(JobConf conf, int stageId, String md5, int timespan)
  {
    try
    {
      DataUtil util = new DataUtil(conf);

      util.setExclusive(stageId);

      Map debug = new HashMap();

      Map counterInfos = util.getAverage(stageId, md5, timespan);

      ScriptInvocate invocation = new ScriptInvocate(conf, counterInfos, debug);

      long begin = Calendar.getInstance().getTimeInMillis();

      invocation.run();

      long end = Calendar.getInstance().getTimeInMillis();

      print2Console(isDebug, "Velocity Script Engine: counters compute + script execute time(ms)=" + (end - begin));

      Set debug_keys = debug.keySet();

      print2Console(true, "Velocity Script Engine: optimize rule result:");
      for (Iterator it = debug_keys.iterator(); it.hasNext(); ) {
        String debug_key = (String)it.next();
        System.err.println(debug_key + "=>" + debug.get(debug_key));
      }

      invocation.close();
      util.close();
    }
    catch (ScriptException se) {
      print2Console(isDebug, "PreJobAutoEngine:script execute error " + se.getMessage());
    }
    catch (SkyNetException sne) {
      System.out.println("Warning:Can't find SkyNet info:" + sne.getMessage());
    }
    catch (FileNotFoundException ffe)
    {
      System.out.println("Error：the script file is not found");
    } catch (Exception e) {
      System.err.println("Error:script optimize dosn't execute and the error:" + e.getMessage());
    }
  }

  private void initJobConf(QueryPlan queryPlan, JobConf jobConf, Integer taskId)
  {
    if ("local".equalsIgnoreCase(jobConf.get("mapred.job.tracker", ""))) {
      print2Console(isDebug, this.className + ": Info = a local map/reduce job");

      jobConf.setBoolean("hive.job.hooks.automized.enable", false);
      return;
    }
    Task rt = getTask(queryPlan.getRootTasks(), new TaskIdFinder("Stage-" + taskId, null));

    if (rt == null) {
      print2Console(isDebug, this.className + ": Task not found = Stage-" + taskId);

      jobConf.setBoolean("hive.job.hooks.automized.enable", false);
      return;
    }

    if (rt.getType() != 3) {
      print2Console(isDebug, this.className + ": Info = not a Map/Reduce Task");
      jobConf.setBoolean("hive.job.hooks.automized.enable", false);
      return;
    }
    boolean isFinalTask = isFinalTask(rt);

    if ((isFinalTask) && (jobConf.getBoolean("hive.job.hooks.autored.text_compress", false)) && (!jobConf.getBoolean("hive.job.hooks.autored.comptext.enabled", false)))
    {
      print2Console(isDebug, this.className + ": Info = Output format is Compressed TextFile");

      jobConf.setBoolean("hive.job.hooks.autored.enable", false);
      return;
    }
    if (jobConf.getBoolean("hive.exec.dynamic.partition", false)) {
      jobConf.setBoolean("hive.job.hooks.autored.enable", false);
      print2Console(isDebug, this.className + ": Info = dynamic partition task");

      return;
    }

    int reduceNum = jobConf.getInt("mapred.reduce.tasks", -1);
    if ((reduceNum == 0) || (reduceNum == 1)) {
      jobConf.setBoolean("hive.job.hooks.autored.enable", false);
      print2Console(isDebug, this.className + ": reduce num = " + reduceNum);
      return;
    }

    int replication = jobConf.getInt("dfs.intermediate.replication", 0);

    if ((!isFinalTask) && (replication > 0))
      jobConf.setInt("dfs.replication", replication);
  }

  private boolean isVisited(Task<? extends Serializable> task)
  {
    return this.visitedSet.contains(task.getId());
  }

  private void setVisited(Task<? extends Serializable> task)
  {
    this.visitedSet.add(task.getId());
    this.q.add(task);
  }

  private Task<? extends Serializable> getTask(Task<? extends Serializable> task, TaskChecker checker)
  {
    if (task == null) {
      return null;
    }

    List ct = task.getChildTasks();
    if (ct != null) {
      for (Task t : ct)
      {
        if (checker.check(t)) {
          return t;
        }
        if (!isVisited(t)) {
          setVisited(t);
        }
      }
    }
    if ((task instanceof ConditionalTask)) {
      List lt = ((ConditionalTask)task).getListTasks();

      if (lt != null) {
        for (Task t : lt)
        {
          if (checker.check(t)) {
            return t;
          }
          if (!isVisited(t)) {
            setVisited(t);
          }
        }
      }
    }

    return null;
  }

  private Task<? extends Serializable> getTask(List<Task<? extends Serializable>> lt, TaskChecker checker)
  {
    if (lt == null) {
      return null;
    }
    if (lt.size() <= 0) {
      return null;
    }
    this.visitedSet.clear();
    this.q.clear();

    for (Task t : lt) {
      if (checker.check(t)) {
        return t;
      }
      if (!isVisited(t)) {
        setVisited(t);
      }
    }
    while (!this.q.isEmpty()) {
      Task t = (Task)this.q.remove();
      t = getTask(t, checker);
      if (t != null) {
        return t;
      }
    }
    return null;
  }

  private boolean isFinalTask(Task<? extends Serializable> task) {
    if (task.getChildTasks() == null) {
      return true;
    }
    if (task.getChildTasks().size() == 0) {
      return true;
    }

    return getTask(task.getChildTasks(), new MapredTaskFinder(null)) == null;
  }

  public static void print2Console(boolean debug, String info)
  {
    if (debug)
      System.err.println(info);
  }

  public static void main(String[] args) {
    JobConf conf = new JobConf();

    conf.setBoolean("hive.job.hooks.automized.enable", true);
    conf.setBoolean("hive.job.hooks.autoshuf.enable", true);
    conf.setBoolean("hive.job.hooks.autored.enable", true);

    conf.set("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");

    conf.set("taobao.meta.ConnectionURL", "jdbc:mysql://10.232.128.68/new_tbdev_hive");

    conf.set("javax.jdo.option.ConnectionUserName", "hive");

    conf.set("taobao.meta.ConnectionPassword", "hive");

    conf.set("mapred.job.skynet_id", "12345");

    conf.set("mapred.job.skynet.bizdate", "20111121");

    conf.set("reduce.script.file.path", "lib/velocity/HiveAuto.vm");

    conf.setBoolean("hive.job.hooks.autored.advanced", true);

    executeScript(conf, 1, "6b7e148fa9819f1eb8aaea81c3aa725f", 7);
    System.out.println(conf.get("io.sort.record.percent"));
  }

  private class MapredTaskFinder extends PreJobAutoEngine.TaskChecker
  {
    private MapredTaskFinder()
    {
      super(null);
    }

    boolean check(Task<? extends Serializable> task)
    {
      return task.getType() == 3;
    }
  }

  private class TaskIdFinder extends PreJobAutoEngine.TaskChecker
  {
    private String taskId;

    private TaskIdFinder(String taskId)
    {
      super(null);
      this.taskId = taskId;
    }

    boolean check(Task<? extends Serializable> task)
    {
      return this.taskId.equalsIgnoreCase(task.getId());
    }
  }

  private abstract class TaskChecker
  {
    private TaskChecker()
    {
    }

    abstract boolean check(Task<? extends Serializable> paramTask);
  }
}