/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.exec;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hadoop.hive.ql.CommandNeedRetryException;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.DriverContext;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.api.StageType;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.util.StringUtils;

/**
 * Task implementation.
 **/

public abstract class Task<T extends Serializable> implements Serializable, Node {

  private static final long serialVersionUID = 1L;
  protected transient boolean started;
  protected transient boolean initialized;
  protected transient boolean isdone;
  protected transient boolean queued;
  protected transient HiveConf conf;
  protected transient Hive db;
  protected transient Log LOG;
  protected transient LogHelper console;
  protected transient QueryPlan queryPlan;
  protected transient TaskHandle taskHandle;
  protected transient HashMap<String, Long> taskCounters;
  protected transient DriverContext driverContext;
  protected transient boolean clonedConf = false;

  protected Task<? extends Serializable> backupTask;
  protected List<Task<? extends Serializable>> backupChildrenTasks = new ArrayList<Task<? extends Serializable>>();
  protected int taskTag;

  public static final int NO_TAG = 0;
  public static final int COMMON_JOIN = 1;
  public static final int CONVERTED_MAPJOIN = 2;
  public static final int CONVERTED_LOCAL_MAPJOIN = 3;
  public static final int BACKUP_COMMON_JOIN = 4;
  public static final int LOCAL_MAPJOIN=5;

  private boolean retryCmdWhenFail = false;
  private boolean isLocalMode = false;
  private boolean nonLimitOptimizationLocalModeTry = false;
  private int execCount = 0;
  // Bean methods

  protected List<Task<? extends Serializable>> childTasks;
  protected List<Task<? extends Serializable>> parentTasks;

  protected transient String errorMessage;

  public Task() {
    isdone = false;
    started = false;
    initialized = false;
    queued = false;
    LOG = LogFactory.getLog(this.getClass().getName());
    this.taskCounters = new HashMap<String, Long>();
    taskTag = Task.NO_TAG;
  }

  public void initialize(HiveConf conf, QueryPlan queryPlan, DriverContext driverContext) {
    this.queryPlan = queryPlan;
    isdone = false;
    started = false;
    setInitialized();
    this.conf = conf;
    SessionState ss = SessionState.get();

    try {
      db = (ss == null) ? Hive.get(conf) : ss.getDb();
    } catch (HiveException e) {
      // Bail out ungracefully - we should never hit
      // this here - but would have hit it in SemanticAnalyzer
      LOG.error(StringUtils.stringifyException(e));
      throw new RuntimeException(e);
    }
    this.driverContext = driverContext;

    setIntermediate();
    incExecCount();
    console = new LogHelper(LOG);
  }

  public void setIntermediate() {
    conf.setBoolVar(ConfVars.INTERMEDIATEJOB, false);
    if(isMapRedTask()) {
      if(childTasks != null ) {
        boolean hasMRTask = false, hasMoveTask = false;
        for(Task t: childTasks) {
          if(t.isMapRedTask()) {
            hasMRTask = true;
          }
          if(t.getType() == StageType.MOVE) {
            hasMoveTask = true;
          }
        }
        if(hasMRTask && (!hasMoveTask)) {
          conf.setBoolVar(ConfVars.INTERMEDIATEJOB, true);
        }
      }
    }
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * This method is called in the Driver on every task. It updates counters and calls execute(),
   * which is overridden in each task
   *
   * @return return value of execute()
   */
  public int executeTask() {
    try {
      SessionState ss = SessionState.get();
      this.setStarted();
      if (ss != null && conf.getBoolVar(ConfVars.HIVELOGPLANPROGRESS)) {
        ss.getHiveHistory().logPlanProgress(queryPlan);
      }
      int retval = execute(driverContext);
      this.setDone();
      if (ss != null && conf.getBoolVar(ConfVars.HIVELOGPLANPROGRESS)) {
        ss.getHiveHistory().logPlanProgress(queryPlan);
      }
      return retval;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * This method is overridden in each Task. TODO execute should return a TaskHandle.
   *
   * @return status of executing the task
   */
  protected abstract int execute(DriverContext driverContext);

  // dummy method - FetchTask overwrites this
  public boolean fetch(ArrayList<String> res) throws IOException, CommandNeedRetryException {
    assert false;
    return false;
  }

  public void setChildTasks(List<Task<? extends Serializable>> childTasks) {
    this.childTasks = childTasks;
  }

  public List<? extends Node> getChildren() {
    return getChildTasks();
  }

  public List<Task<? extends Serializable>> getChildTasks() {
    return childTasks;
  }

  public void setParentTasks(List<Task<? extends Serializable>> parentTasks) {
    this.parentTasks = parentTasks;
  }

  public List<Task<? extends Serializable>> getParentTasks() {
    return parentTasks;
  }

  public Task<? extends Serializable> getBackupTask() {
    return backupTask;
  }


  public void setBackupTask(Task<? extends Serializable> backupTask) {
    this.backupTask = backupTask;
  }

  public List<Task<? extends Serializable>> getBackupChildrenTasks() {
    return backupChildrenTasks;
  }

  public void setBackupChildrenTasks(List<Task<? extends Serializable>> backupChildrenTasks) {
    this.backupChildrenTasks = backupChildrenTasks;
  }

  public Task<? extends Serializable> getAndInitBackupTask() {
    if (backupTask != null) {
      // first set back the backup task with its children task.
      if( backupChildrenTasks!= null) {
        for (Task<? extends Serializable> backupChild : backupChildrenTasks) {
          backupChild.getParentTasks().add(backupTask);
        }
      }

      // recursively remove task from its children tasks if this task doesn't have any parent task
      this.removeFromChildrenTasks();
    }
    return backupTask;
  }

  public void removeFromChildrenTasks() {

    List<Task<? extends Serializable>> childrenTasks = this.getChildTasks();
    if (childrenTasks == null) {
      return;
    }

    for (Task<? extends Serializable> childTsk : childrenTasks) {
      // remove this task from its children tasks
      childTsk.getParentTasks().remove(this);

      // recursively remove non-parent task from its children
      List<Task<? extends Serializable>> siblingTasks = childTsk.getParentTasks();
      if (siblingTasks == null || siblingTasks.size() == 0) {
        childTsk.removeFromChildrenTasks();
      }
    }

    return;
  }


  /**
   * The default dependent tasks are just child tasks, but different types could implement their own
   * (e.g. ConditionalTask will use the listTasks as dependents).
   *
   * @return a list of tasks that are dependent on this task.
   */
  public List<Task<? extends Serializable>> getDependentTasks() {
    return getChildTasks();
  }

  /**
   * Add a dependent task on the current task. Return if the dependency already existed or is this a
   * new one
   *
   * @return true if the task got added false if it already existed
   */
  public boolean addDependentTask(Task<? extends Serializable> dependent) {
    boolean ret = false;
    if (getChildTasks() == null) {
      setChildTasks(new ArrayList<Task<? extends Serializable>>());
    }
    if (!getChildTasks().contains(dependent)) {
      ret = true;
      getChildTasks().add(dependent);
      if (dependent.getParentTasks() == null) {
        dependent.setParentTasks(new ArrayList<Task<? extends Serializable>>());
      }
      if (!dependent.getParentTasks().contains(this)) {
        dependent.getParentTasks().add(this);
      }
    }
    return ret;
  }

  /**
   * Remove the dependent task.
   *
   * @param dependent
   *          the task to remove
   */
  public void removeDependentTask(Task<? extends Serializable> dependent) {
    if ((getChildTasks() != null) && (getChildTasks().contains(dependent))) {
      getChildTasks().remove(dependent);
      if ((dependent.getParentTasks() != null) && (dependent.getParentTasks().contains(this))) {
        dependent.getParentTasks().remove(this);
      }
    }
  }

  public void setStarted() {
    this.started = true;
  }

  public boolean started() {
    return started;
  }

  public boolean done() {
    return isdone;
  }

  public void setDone() {
    isdone = true;
  }

  public void setQueued() {
    queued = true;
  }

  public boolean getQueued() {
    return queued;
  }

  public void setInitialized() {
    initialized = true;
  }

  public boolean getInitialized() {
    return initialized;
  }

  public boolean isRunnable() {
    boolean isrunnable = true;
    if (parentTasks != null) {
      for (Task<? extends Serializable> parent : parentTasks) {
        if (!parent.done()) {
          isrunnable = false;
          break;
        }
      }
    }
    return isrunnable;
  }

  protected String id;
  protected T work;

  public void setWork(T work) {
    this.work = work;
  }

  public T getWork() {
    return work;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public boolean isMapRedTask() {
    return false;
  }

  public boolean isMapRedLocalTask() {
    return false;
  }

  public boolean hasReduce() {
    return false;
  }

  public HashMap<String, Long> getCounters() {
    return taskCounters;
  }

  /**
   * Should be overridden to return the type of the specific task among the types in TaskType.
   *
   * @return TaskTypeType.* or -1 if not overridden
   */
  public int getType() {
    assert false;
    return -1;
  }

  /**
   * If this task uses any map-reduce intermediate data (either for reading
   * or for writing), localize them (using the supplied Context). Map-Reduce
   * intermediate directories are allocated using Context.getMRTmpFileURI()
   * and can be localized using localizeMRTmpFileURI().
   *
   * This method is declared abstract to force any task code to explicitly
   * deal with this aspect of execution.
   *
   * @param ctx context object with which to localize
   */
  abstract protected void localizeMRTmpFilesImpl(Context ctx);

  /**
   * Localize a task tree
   * @param ctx context object with which to localize
   */
  public final void localizeMRTmpFiles(Context ctx) {
    localizeMRTmpFilesImpl(ctx);

    if (childTasks == null) {
      return;
    }

    for (Task<? extends Serializable> t: childTasks) {
      t.localizeMRTmpFiles(ctx);
    }
  }

  protected void cloneConf () {
    if (!clonedConf) {
      clonedConf = true;
      conf = new HiveConf(conf);
    }
  }

  public boolean isLocalMode() {
    return isLocalMode;
  }

  public void setLocalMode(boolean isLocalMode) {
    this.isLocalMode = isLocalMode;
  }

  public boolean getNonLimitOptimizationLocalModeTry() {
    return nonLimitOptimizationLocalModeTry;
  }

  public void setNonLimitOptimizationLocalModeTry(boolean nonLimitOptimizationLocalModeTry) {
    this.nonLimitOptimizationLocalModeTry = nonLimitOptimizationLocalModeTry;
  }

  public int getTaskTag() {
    return taskTag;
  }

  public void setTaskTag(int taskTag) {
    this.taskTag = taskTag;
  }

  public int getExecCount() {
    return execCount;
  }

  public void incExecCount() {
    execCount = execCount+1;
  }

  public boolean ifRetryCmdWhenFail() {
    return retryCmdWhenFail;
  }

  public void setRetryCmdWhenFail(boolean retryCmdWhenFail) {
    this.retryCmdWhenFail = retryCmdWhenFail;
  }
}
