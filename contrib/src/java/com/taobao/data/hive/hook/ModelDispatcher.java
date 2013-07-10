package com.taobao.data.hive.hook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.exec.Task;
import org.apache.hadoop.hive.ql.lib.Dispatcher;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.MapredWork;

public class ModelDispatcher implements Dispatcher {

  private final PathContext pathctx;
  private final HashMap<Task<? extends Serializable>, TaskModel> taskToModel;
  private final HashMap<Task<? extends Serializable>, Integer> taskToMaxRoute;
  private final WorkDigester digester;

  public ModelDispatcher(HiveConf conf, PathContext pathctx) {
    super();
    this.digester = new WorkDigester(conf);
    this.pathctx = pathctx;
    taskToModel = new HashMap<Task<?>, TaskModel>();
    taskToMaxRoute = new HashMap<Task<?>, Integer>();
  }

  @Override
  public Object dispatch(Node nd, Stack<Node> ndStack, Object... nodeOutputs)
      throws SemanticException {
    Task<? extends Serializable> task = (Task<?>) nd;
    if (task.getWork() instanceof MapredWork) {
      TaskModel model = TaskUtils.getTaskModel(task, digester, pathctx);
      taskToModel.put(task, model);
      // Update route map
      List<Task<?>> parents = task.getParentTasks();
      // If task is root task,
      if (parents == null || parents.isEmpty()) {
        updateChildRoute(task, 0);
      } else {
        for (Task<? extends Serializable> parent : parents) {
          TaskModel parentModel = taskToModel.get(parent);
          if (parentModel != null) {
            if (model.getParents() == null) {
              model.setParents(new ArrayList<TaskModel>());
              model.getParents().add(parentModel);
            } else if (!model.getParents().contains(parentModel)) {
              model.getParents().add(parentModel);
            }
          }
        }
      }
      List<Task<?>> children = task.getChildTasks();
      if (children != null && !children.isEmpty()) {
        for (Task<? extends Serializable> child : children) {
          TaskModel childModel = taskToModel.get(child);
          if (childModel != null) {
            if (model.getParents() == null) {
              model.setParents(new ArrayList<TaskModel>());
              model.getParents().add(childModel);
            } else if (!model.getParents().contains(childModel)) {
              model.getParents().add(childModel);
            }
          }
        }
      }
    }
    return null;
  }

  private void updateChildRoute(Task<? extends Serializable> task, int route) {
    Integer currentRoute = taskToMaxRoute.get(task);
    if (currentRoute == null || currentRoute < route) {
      if (task.getWork() instanceof MapredWork) {
        taskToMaxRoute.put(task, route);
      }
    }
    List<Task<? extends Serializable>> children = task.getChildTasks();
    if (children == null || children.isEmpty()) {
      return;
    }
    for (Task<? extends Serializable> child : children) {
      if (task.getWork() instanceof MapredWork) {
        updateChildRoute(child, route + 1);
      }
    }
  }

  public HashMap<Task<? extends Serializable>, TaskModel> getTaskToModel() {
    return taskToModel;
  }

  public HashMap<Task<? extends Serializable>, Integer> getTaskToMaxRoute() {
    return taskToMaxRoute;
  }
}
