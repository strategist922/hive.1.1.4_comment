package com.taobao.data.hive.hook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.exec.FetchTask;
import org.apache.hadoop.hive.ql.exec.Task;
import org.apache.hadoop.hive.ql.lib.DefaultGraphWalker;
import org.apache.hadoop.hive.ql.lib.GraphWalker;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.AbstractSemanticAnalyzerHook;
import org.apache.hadoop.hive.ql.parse.HiveSemanticAnalyzerHookContext;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.ExplainWork;
import org.apache.hadoop.hive.ql.session.SessionState;

public class PlanDeDuplicateSemanticAnalyzerHook extends
    AbstractSemanticAnalyzerHook {

  private final Log LOG = LogFactory.getLog(PlanDeDuplicateSemanticAnalyzerHook.class);

  @Override
  public void postAnalyze(HiveSemanticAnalyzerHookContext context,
      List<Task<? extends Serializable>> rootTasks, FetchTask fetchTask)
      throws SemanticException {
    if (rootTasks.isEmpty()) {
      // Incase a select-star statement
      return;
    }
    HiveConf conf = (HiveConf) context.getConf();
    // Deal with explain task
    Serializable rootWork0 = rootTasks.get(0).getWork();
    boolean isExplain;
    List<Task<? extends Serializable>> towalk;
    if (rootWork0 instanceof ExplainWork) {
      isExplain = true;
      towalk = ((ExplainWork) rootWork0).getRootTasks();
    } else {
      isExplain = false;
      towalk = rootTasks;
    }
    analyze(conf, context.getContext(), towalk, fetchTask, isExplain);
  }

  private void analyze(HiveConf conf, Context context,
      List<Task<? extends Serializable>> rootTasks,
      FetchTask fetchTask, boolean isExplain) throws SemanticException {

    PathContext pathctx = new PathContext(context);
    // Generate task models
    ModelDispatcher disp = new ModelDispatcher(conf, pathctx);
    GraphWalker tgw = new DefaultGraphWalker(disp);
    List<Node> towalk = new ArrayList<Node>();
    towalk.addAll(rootTasks);
    tgw.startWalking(towalk, null);

    HashMap<Task<? extends Serializable>, Integer> taskToMaxRoute = disp
        .getTaskToMaxRoute();
    HashMap<Task<? extends Serializable>, TaskModel> taskToModel = disp
        .getTaskToModel();
    if (taskToModel.size() == 0) {
      return;
    }

    // Get task models' ancestor paths(tables).
    // TODO: FIXME:

    // Sort tasks by max route to root, so we can prune the plan from the
    // deepest task
    List<Entry<Task<? extends Serializable>, Integer>> tasks =
        new LinkedList<Entry<Task<? extends Serializable>, Integer>>(
            taskToMaxRoute.entrySet());
    Collections.sort(tasks,
        new Comparator<Entry<Task<? extends Serializable>, Integer>>() {
          @Override
          public int compare(
              Entry<Task<? extends Serializable>, Integer> e0,
              Entry<Task<? extends Serializable>, Integer> e1) {
            return e1.getValue() - e0.getValue();
          }
        });

    List<Task<? extends Serializable>> toPrune = new ArrayList<Task<? extends Serializable>>();
    for (Entry<Task<? extends Serializable>, Integer> e : tasks) {
      toPrune.add(e.getKey());
    }

    long now = System.currentTimeMillis();
    StorageHandler<TaskModel> handler =
        StorageHandlerFactory.getRDBMSStorageHandler(conf);
    int numPrunedTasks = 0;
    while (!toPrune.isEmpty()) {
      Task<? extends Serializable> task = toPrune.remove(0);
      TaskModel tm = taskToModel.get(task);
      assert (tm != null);
      TaskModel duptm = handler.get(tm);
      if (duptm == null || !duptm.isValid(conf)) {
        if (duptm != null) {
          handler.remove(duptm);
        }
        if (!isExplain) {
          tm.setStartTimeStamp(now);
          TaskModelCache.put(context, tm);
        }
        continue;
      }
      LOG.debug("Task " + task.getId() + " found duplicate, MD5=" + tm.getMD5());
      boolean succeeded = TaskUtils.assignNewPaths(task, rootTasks, fetchTask, tm.getPaths(),
          duptm.getPaths(), pathctx);
      if (succeeded) {
        List<Task<? extends Serializable>> prunedTasks = TaskUtils
            .pruneDuplicatedTask(rootTasks, task);
        numPrunedTasks += prunedTasks.size();
        toPrune.removeAll(prunedTasks);
      }
    }
    handler.close();
    if (numPrunedTasks > 0) {
      LOG.info("Pruned " + Integer.toString(numPrunedTasks) + " tasks");
      SessionState.getConsole().printInfo(
          "Pruned " + Integer.toString(numPrunedTasks) + " tasks");
    }
  }

}
