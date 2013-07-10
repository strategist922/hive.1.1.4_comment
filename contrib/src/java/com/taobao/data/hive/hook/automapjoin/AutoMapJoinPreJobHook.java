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

package com.taobao.data.hive.hook.automapjoin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.exec.ConditionalTask;
import org.apache.hadoop.hive.ql.exec.Task;
import org.apache.hadoop.hive.ql.hooks.PreJobHook;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapred.JobConf;

/**
 * AutoMapJoinPreJobHook is a job level hook which tracks which join tasks are
 * converted into map joins.
 */
public class AutoMapJoinPreJobHook implements PreJobHook {

  // Each session shares the same lock
  private static Object lock = new Object();

  private boolean found = false;  // dfs found task

  private int taskTag = Task.NO_TAG;  // dfs result

  private static final String AUTO_JOIN_STATICS_DIR = "hive.auto.join.statics.dir";

  /**
   * A depth-first search method which searches taskTag of the specific task in
   * the tasks' graph
   *
   * @param rootTasks A tasks' graph
   * @param taskId A "Stage-1" like string
   */
  private void dfsTaskTag(
      List<Task<? extends Serializable>> rootTasks,
      String taskId) {
    if (found || null == rootTasks) {
      return;
    }

    for (Task<? extends Serializable> task : rootTasks) {
      if (taskId.equals(task.getId())) {
        found = true;
        taskTag = task.getTaskTag();
        return;
      }
      if (task instanceof ConditionalTask) {
        dfsTaskTag(((ConditionalTask) task).getListTasks(), taskId);
      }
      dfsTaskTag(task.getChildTasks(), taskId);
    }
  }

  @Override
  public void run(SessionState session,
      QueryPlan queryPlan,
      JobConf job,
      Integer taskId) {
    if (!job.getBoolean(HiveConf.ConfVars.HIVECONVERTJOIN.varname, false) ||
        null == job.get(AUTO_JOIN_STATICS_DIR) ||
        null == queryPlan) {
      return;
    }
    List<Task<? extends Serializable>> rootTasks = queryPlan.getRootTasks();
    String taskIdStr = "Stage-" + taskId.toString();
    dfsTaskTag(rootTasks, taskIdStr);  // dfs, fill the member variable 'taskTag'

    StringBuilder sb = new StringBuilder();
    sb.append(queryPlan.getQueryId());
    sb.append('\u0001');
    sb.append(taskIdStr);
    sb.append('\u0001');
    switch (taskTag) {
    case Task.NO_TAG:
      return;
    case Task.BACKUP_COMMON_JOIN:
      sb.append("Backup Common Join");
      break;
    case Task.COMMON_JOIN:
      sb.append("Common Join");
      break;
    case Task.CONVERTED_LOCAL_MAPJOIN:
      sb.append("Converted Local Map Join");
      break;
    case Task.CONVERTED_MAPJOIN:
      sb.append("Converted Map Join");
      break;
    case Task.LOCAL_MAPJOIN:
      sb.append("Local Map Join");
      break;
    }

    String sessionId = session.getSessionId();
    File dir = new File(job.get(AUTO_JOIN_STATICS_DIR));
    if (!dir.exists()) {
      dir.mkdir();
    }
    File file = new File(dir, sessionId + ".stat");
    synchronized(lock) {
      FileOutputStream fout = null;
      PrintStream pout = null;
      try {
        if (!file.exists()) {
          file.createNewFile();
        }
        fout = new FileOutputStream(file, true);
        pout = new PrintStream(fout);
        pout.println(sb.toString());
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (null != pout) {
          pout.close();
        }
      }
    }

    // reset dfs results
    taskTag = Task.NO_TAG;
    found = false;
  }

}
