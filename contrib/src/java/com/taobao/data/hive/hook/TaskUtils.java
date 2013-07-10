package com.taobao.data.hive.hook;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.FetchTask;
import org.apache.hadoop.hive.ql.exec.Operator;
import org.apache.hadoop.hive.ql.exec.Task;
import org.apache.hadoop.hive.ql.lib.DefaultGraphWalker;
import org.apache.hadoop.hive.ql.lib.GraphWalker;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.MapredWork;
import org.apache.hadoop.hive.ql.plan.MoveWork;
import org.apache.hadoop.hive.ql.plan.PartitionDesc;

public class TaskUtils {

  public static TaskModel getTaskModel(Task<?> task, WorkDigester digester, PathContext pathctx) {
    try {
      MapredWork work = (MapredWork) task.getWork();
      String md5 = digester.digest(work, pathctx);
      List<String> paths = getOutputPaths(task);
      List<String> inputPaths = getInputPaths(task, pathctx);
      List<String> inputTables = getInputTables(task, pathctx);
      List<String> cachedPaths = new ArrayList<String>();
      for (String path : paths) {
        cachedPaths.add(pathctx.translateToCache(path));
      }
      TaskModel tm = new TaskModel();
      tm.setMD5(md5);
      tm.setPaths(cachedPaths);
      tm.setInputPaths(inputPaths);
      tm.setInputTables(inputTables);

      return tm;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Get all output paths of the given task, new paths created by MoveTasks
   * is considered to replace the scratchdir.
   *
   * @param task
   *          Task to be analysed, should be MR task.
   * @return
   */
  public static List<String> getOutputPaths(Task<? extends Serializable> task) {
    List<String> outputPaths = null;
    // Get FileSink operator paths
    if (task.getWork() instanceof MapredWork) {
      MapredWork work = (MapredWork) task.getWork();
      List<Node> towalk = new ArrayList<Node>();
      for (Map.Entry<String, Operator<? extends Serializable>> e : work
          .getAliasToWork().entrySet()) {
        towalk.add(e.getValue());
      }
      if (work.getReducer() != null) {
        towalk.add(work.getReducer());
      }
      FileSinkDispatcher disp = new FileSinkDispatcher();
      GraphWalker ogw = new DefaultGraphWalker(disp);
      try {
        ogw.startWalking(towalk, null);
      } catch (SemanticException e) {
        e.printStackTrace();
      }
      outputPaths = disp.getPaths();
    }
    assert (outputPaths != null);
    // TODO: NOT SUPPORTED: Replace scratch dir with table location if there is move task.
    /*
    List<Task<? extends Serializable>> children = task.getChildTasks();
    if (children == null) {
      return outputPaths;
    }
    for (Task<? extends Serializable> child : task.getChildTasks()) {
      if (child.getWork() instanceof MoveWork) {
        MoveWork work = (MoveWork) child.getWork();

        LoadFileDesc lfd = work.getLoadFileWork();
        if (lfd != null) {
          int idx = outputPaths.indexOf(lfd.getSourceDir());
          assert (idx >= 0);
          outputPaths.set(idx, lfd.getTargetDir());
        }

        LoadTableDesc ltd = work.getLoadTableWork();
        String targetDir = null;
        if (ltd != null) {
          try {
            Map<String, String> partSpec = ltd.getPartitionSpec();
            DynamicPartitionCtx dpCtx = ltd.getDPCtx();
            Hive db = Hive.get();
            Table tbl = db.getTable(ltd.getTable().getTableName());
            if (partSpec.size() == 0) {
              targetDir = tbl.getDataLocation().toString();
            } else if (dpCtx != null && dpCtx.getNumDPCols() > 0) {
              // Not supported currently
              continue;
            } else {
              Partition part = db.getPartition(tbl, partSpec, false);
              Path partPath;
              // Copied from Hive.java
              if (part == null) {
                // Partition does not exist currently. The partition name is
                // extrapolated from
                // the table's location (even if the table is marked external)
                partPath = new Path(tbl.getDataLocation().getPath(),
                    Warehouse.makePartName(partSpec));
                targetDir = partPath.toString();
              } else {
                // Partition exists already. Get the path from the partition. This will
                // get the default path for Hive created partitions or the external path
                // when directly created by user
                partPath = part.getPath()[0];
              }
            }
          } catch (HiveException e) {
            e.printStackTrace();
          } catch (MetaException e) {
            e.printStackTrace();
          }
          int idx = outputPaths.indexOf(ltd.getSourceDir());
          assert (idx >= 0);
          if (targetDir != null) {
            outputPaths.set(idx, targetDir);
          }
        }
      }
    }
    */
    return outputPaths;
  }

  /**
   * Get all input paths except scratch for the given task
   *
   * @param task
   * @param scratchRoot
   * @return
   */
  private static List<String> getInputPaths(Task<?> task, PathContext pathctx) {
    List<String> inputPaths = new ArrayList<String>();
    List<String> pathsProcessed = new ArrayList<String>();
    if (task.getWork() instanceof MapredWork) {
      MapredWork work = (MapredWork) task.getWork();
      // Copied from ExecDriver.java
      // AliasToWork contains all the aliases
      for (String oneAlias : work.getAliasToWork().keySet()) {
        // The alias may not have any path
        String path = null;
        for (String onefile : work.getPathToAliases().keySet()) {
          List<String> aliases = work.getPathToAliases().get(onefile);
          if (aliases.contains(oneAlias)) {
            path = onefile;
            // Multiple aliases can point to the same path - it should be
            // processed only once
            if (pathsProcessed.contains(path)) {
              continue;
            }
            pathsProcessed.add(path);
            if (path != null && !pathctx.isScratch(path)) {
              inputPaths.add(path);
            }
          }
        }
      }
    }
    return inputPaths;
  }

  /**
   * Get all input paths except scratch for the given task
   *
   * @param task
   * @param scratchRoot
   * @return
   */
  private static List<String> getInputTables(Task<?> task, PathContext pathctx) {
    List<String> inputTables = new ArrayList<String>();
    if (task.getWork() instanceof MapredWork) {
      MapredWork work = (MapredWork) task.getWork();
      if (work.getAliasToPartnInfo() == null || work.getPathToPartitionInfo().size() == 0) {
        return inputTables;
      }
      for (PartitionDesc partDesc : work.getPathToPartitionInfo().values()) {
        String tblName = partDesc.getTableName();
        if (tblName != null && !inputTables.contains(tblName)) {
          inputTables.add(tblName);
        }
      }
    }
    return inputTables;
  }

  public static List<Task<? extends Serializable>> pruneDuplicatedTask(
      List<Task<? extends Serializable>> rootTasks,
      Task<? extends Serializable> task) {
    // Remove child dependency
    // put current task and all its ascendent to pruned tasks
    List<Task<? extends Serializable>> prunedTasks =
        new ArrayList<Task<? extends Serializable>>();
    List<Task<? extends Serializable>> children = task.getChildTasks();
    if (children != null && !children.isEmpty()) {
      for (Task<? extends Serializable> child : children) {
        if (children != null && children.size() != 0) {
          child.getParentTasks().remove(task);
          // Child tasks shall be root task if no other dependencies
          if (child.getParentTasks().isEmpty()) {
            rootTasks.add(child);
          }
        }
      }
    }
    pruneTasks(task, rootTasks, prunedTasks);
    return prunedTasks;
  }

  private static void pruneTasks(
      Task<? extends Serializable> task,
      List<Task<? extends Serializable>> rootTasks,
      List<Task<? extends Serializable>> pruned) {
    pruned.add(task);
    rootTasks.remove(task);
    List<Task<? extends Serializable>> parents = task.getParentTasks();
    if (parents == null || parents.isEmpty()) {
      return;
    }
    for (Task<? extends Serializable> parent : parents) {
      parent.getChildren().remove(task);
      if (parent.getChildren().isEmpty()) {
        pruneTasks(parent, rootTasks, pruned);
      }
    }
  }

  /**
   * Assign new path to all the duplicated task's child, prepare for pruning.
   *
   * @param rootTasks
   * @param fetchTask
   * @param fetch
   *
   * @return Wether the assign process can be successfully done.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static boolean assignNewPaths(Task<? extends Serializable> task,
      List<Task<? extends Serializable>> rootTasks,
      FetchTask fetchTask, List<String> paths, List<String> cachedPaths, PathContext pathctx) {
    List<Task<? extends Serializable>> children = task.getChildTasks();
    boolean noChild = false;
    if (children == null || children.isEmpty()) {
      noChild = true;
    }
    // Move task is not supported currently, the process will fail immediately if
    // move task encoutered
    if (!noChild) {
      for (Task<?> child : children) {
        if (child.getChildTasks() instanceof MoveWork) {
          return false;
        }
      }
    }

    WorkReWriterFactory factory = new WorkReWriterFactory(paths, cachedPaths, pathctx);
    // Change input of FetchTasks
    if (fetchTask != null) {
      Serializable work = fetchTask.getWork();
      WorkPathReWriter rewriter = factory.getPathReWriter(work.getClass());
      if (rewriter != null) {
        rewriter.rewrite(work);
      }
    }

    if (!noChild) {
      for (Task<? extends Serializable> child : children) {
        // Change input to existed files
        // If traversed the same task plan, TaskModel shall get the same
        // path list with the same sequence...
        Serializable work = child.getWork();
        WorkPathReWriter rewriter = factory.getPathReWriter(work.getClass());
        if (rewriter != null) {
          rewriter.rewrite(work);
        }
      }
    }
    return true;
  }

}
