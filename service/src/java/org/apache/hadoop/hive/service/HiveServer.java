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

package org.apache.hadoop.hive.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hadoop.hive.metastore.HiveMetaStore;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.Schema;
import org.apache.hadoop.hive.ql.CommandNeedRetryException;
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.history.HiveHistory.QueryInfo;
import org.apache.hadoop.hive.ql.history.HiveHistory.TaskInfo;
import org.apache.hadoop.hive.ql.plan.api.QueryPlan;
import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.processors.CommandProcessorFactory;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapred.ClusterStatus;
import org.apache.hadoop.mapred.JobTracker;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportFactory;

/**
 * Thrift Hive Server Implementation.
 */
public class HiveServer extends ThriftHive {
  private static final String VERSION = "1";

  /**
   * Handler which implements the Hive Interface This class can be used in lieu
   * of the HiveClient class to get an embedded server.
   */
  public static class HiveServerHandler extends HiveMetaStore.HMSHandler
      implements HiveInterface {
    /**
     * Hive server uses org.apache.hadoop.hive.ql.Driver for run() and
     * getResults() methods.
     */
    private final Driver driver;

    private boolean isQueryRunning;

    /**
     * Flag that indicates whether the last executed command was a Hive query.
     */
    private boolean isHiveQuery;

    public static final Log LOG = LogFactory.getLog(HiveServer.class.getName());

    /**
     * A constructor.
     */
    public HiveServerHandler() throws MetaException {
      super(HiveServer.class.getName());

      isHiveQuery = false;
      HiveConf conf = new HiveConf(SessionState.class);
      SessionState session = new SessionState(conf);
      SessionState.start(session);
      session.in = null;
      session.out = null;
      session.err = null;
      driver = new Driver();
      driver.setAuthorizeEnabled(conf.getBoolVar(ConfVars.HIVE_AUTHORIZATION_ENABLED));
    }

    public HiveServerHandler(TTransport trans) throws MetaException, TException {
      super(HiveServer.class.getName());

      isHiveQuery = false;
      HiveConf conf = new HiveConf(SessionState.class);
      SessionState session = new SessionState(conf);
      SessionState.start(session);
      session.in = null;
      session.out = null;
      session.err = null;

      if (!session.getAuthenticator().validate(session, new TBinaryProtocol(trans))) {
        throw new TException("Verification failed...");
      }
      driver = new Driver();
      driver.setAuthorizeEnabled(conf.getBoolVar(ConfVars.HIVE_AUTHORIZATION_ENABLED));
    }

    /**
     * Executes a query.
     *
     * @param cmd
     *          HiveQL query to execute
     */
    public void execute(String cmd) throws HiveServerException, TException {
      HiveServerHandler.LOG.info("Running the query: " + cmd);
      SessionState.get();

      String cmd_trimmed = cmd.trim();
      String[] tokens = cmd_trimmed.split("\\s");
      String cmd_1 = cmd_trimmed.substring(tokens[0].length()).trim();

      int ret = 0;
      String errorMessage = "";
      String SQLState = null;

      try {
        CommandProcessor proc = CommandProcessorFactory.get(tokens[0]);
        CommandProcessorResponse response = null;
        if (proc != null) {
          if (proc instanceof Driver) {
            isHiveQuery = true;
            // In Hive server mode, we are not able to retry in the FetchTask
            // case, when calling fetch queries since execute() has returned.
            // For now, we disable the test attempts.
            driver.setTryCount(Integer.MAX_VALUE);
            response = driver.run(cmd);
          } else {
            isHiveQuery = false;
            response = proc.run(cmd_1);
          }

          ret = response.getResponseCode();
          SQLState = response.getSQLState();
          errorMessage = response.getErrorMessage();
        }
      } catch (Exception e) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage("Error running query: " + e.toString());
        throw ex;
      }

      if (ret != 0) {
        throw new HiveServerException("Query returned non-zero code: " + ret
            + ", cause: " + errorMessage, ret, SQLState);
      }
    }

    /**
     * Return the status information about the Map-Reduce cluster.
     */
    public HiveClusterStatus getClusterStatus() throws HiveServerException,
        TException {
      HiveClusterStatus hcs;
      try {
        ClusterStatus cs = driver.getClusterStatus();
        JobTracker.State jbs = cs.getJobTrackerState();

        // Convert the ClusterStatus to its Thrift equivalent: HiveClusterStatus
        int state;
        switch (jbs) {
        case INITIALIZING:
          state = JobTrackerState.INITIALIZING;
          break;
        case RUNNING:
          state = JobTrackerState.RUNNING;
          break;
        default:
          String errorMsg = "Unrecognized JobTracker state: " + jbs.toString();
          throw new Exception(errorMsg);
        }

        hcs = new HiveClusterStatus(cs.getTaskTrackers(), cs.getMapTasks(), cs
            .getReduceTasks(), cs.getMaxMapTasks(), cs.getMaxReduceTasks(),
            state);
      } catch (Exception e) {
        LOG.error(e.toString());
        e.printStackTrace();
        HiveServerException ex = new HiveServerException();
        ex.setMessage("Unable to get cluster status: " + e.toString());
        throw ex;
      }
      return hcs;
    }

    /**
     * Return the Hive schema of the query result.
     */
    public Schema getSchema() throws HiveServerException, TException {
      if (!isHiveQuery) {
        // Return empty schema if the last command was not a Hive query
        return new Schema();
      }

      try {
        Schema schema = driver.getSchema();
        if (schema == null) {
          schema = new Schema();
        }
        LOG.info("Returning schema: " + schema);
        return schema;
      } catch (Exception e) {
        LOG.error(e.toString());
        e.printStackTrace();
        HiveServerException ex = new HiveServerException();
        ex.setMessage("Unable to get schema: " + e.toString());
        throw ex;
      }
    }

    /**
     * Return the Thrift schema of the query result.
     */
    public Schema getThriftSchema() throws HiveServerException, TException {
      if (!isHiveQuery) {
        // Return empty schema if the last command was not a Hive query
        return new Schema();
      }

      try {
        Schema schema = driver.getThriftSchema();
        if (schema == null) {
          schema = new Schema();
        }
        LOG.info("Returning schema: " + schema);
        return schema;
      } catch (Exception e) {
        LOG.error(e.toString());
        e.printStackTrace();
        HiveServerException ex = new HiveServerException();
        ex.setMessage("Unable to get schema: " + e.toString());
        throw ex;
      }
    }

    /**
     * Fetches the next row in a query result set.
     *
     * @return the next row in a query result set. null if there is no more row
     *         to fetch.
     */
    public String fetchOne() throws HiveServerException, TException {
      if (!isHiveQuery) {
        // Return no results if the last command was not a Hive query
        return "";
      }

      ArrayList<String> result = new ArrayList<String>();
      driver.setMaxRows(1);
      try {
        if (driver.getResults(result)) {
          return result.get(0);
        }
        // TODO: Cannot return null here because thrift cannot handle nulls
        // TODO: Returning empty string for now. Need to figure out how to
        // TODO: return null in some other way
        return "";
      } catch (CommandNeedRetryException e) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage(e.getMessage());
        throw ex;
      } catch (IOException e) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage(e.getMessage());
        throw ex;
      }
    }

    /**
     * Fetches numRows rows.
     *
     * @param numRows
     *          Number of rows to fetch.
     * @return A list of rows. The size of the list is numRows if there are at
     *         least numRows rows available to return. The size is smaller than
     *         numRows if there aren't enough rows. The list will be empty if
     *         there is no more row to fetch or numRows == 0.
     * @throws HiveServerException
     *           Invalid value for numRows (numRows < 0)
     */
    public List<String> fetchN(int numRows) throws HiveServerException,
        TException {
      if (numRows < 0) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage("Invalid argument for number of rows: " + numRows);
        throw ex;
      }
      if (!isHiveQuery) {
        // Return no results if the last command was not a Hive query
        return new ArrayList<String>();
      }

      ArrayList<String> result = new ArrayList<String>();
      driver.setMaxRows(numRows);
      try {
        driver.getResults(result);
      } catch (CommandNeedRetryException e) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage(e.getMessage());
        throw ex;
      } catch (IOException e) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage(e.getMessage());
        throw ex;
      }
      return result;
    }

    /**
     * Fetches all the rows in a result set.
     *
     * @return All the rows in a result set of a query executed using execute
     *         method.
     *
     *         TODO: Currently the server buffers all the rows before returning
     *         them to the client. Decide whether the buffering should be done
     *         in the client.
     */
    public List<String> fetchAll() throws HiveServerException, TException {
      if (!isHiveQuery) {
        // Return no results if the last command was not a Hive query
        return new ArrayList<String>();
      }

      ArrayList<String> rows = new ArrayList<String>();
      ArrayList<String> result = new ArrayList<String>();
      try {
        while (driver.getResults(result)) {
          rows.addAll(result);
          result.clear();
        }
      } catch (CommandNeedRetryException e) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage(e.getMessage());
        throw ex;
      } catch (IOException e) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage(e.getMessage());
        throw ex;
      }
      return rows;
    }

    /**
     * Return the status of the server.
     */
    @Override
    public int getStatus() {
      return 0;
    }

    /**
     * Return the version of the server software.
     */
    @Override
    public String getVersion() {
      return VERSION;
    }

    @Override
    public QueryPlan getQueryPlan() throws HiveServerException, TException {
      QueryPlan qp = new QueryPlan();
      // TODO for now only return one query at a time
      // going forward, all queries associated with a single statement
      // will be returned in a single QueryPlan
      try {
        qp.addToQueries(driver.getQueryPlan());
      } catch (Exception e) {
        HiveServerException ex = new HiveServerException();
        ex.setMessage(e.toString());
        throw ex;
      }
      return qp;
    }

    /**
     * Should be called by the client at the end of a session.
     */
    @Override
    public void clean() throws TException {
      if (driver != null) {
        driver.close();
      }
    }

    @Override
    public void killJob() throws HiveServerException, TException {
      try {
        if (driver != null) {
          driver.killRunningJobs();
        }
      } catch (Exception e) {
        throw new HiveServerException(e.getMessage(), -1, "INTERNAL");
      }

    }

    @Override
    public TQueryInfo getHiveQueryInfo() throws HiveServerException, TException {
      TQueryInfo tQueryInfo = new TQueryInfo();
      String queryId = SessionState.get().getQueryId();
      QueryInfo queryInfo = SessionState.get().getHiveHistory().getQueryInfo(queryId);
      if( queryInfo != null ) {
        for (Map.Entry<String, String> ent : queryInfo.hm.entrySet()) { // queryInfo
          String key = ent.getKey();
          String val = ent.getValue();
          tQueryInfo.putToQueryInfos(key, val);
        }
        HashMap<String, TaskInfo> taskInfos = SessionState.get().getHiveHistory().getTaskInfos(queryId);
        if( taskInfos != null )  {
          for (Map.Entry<String, TaskInfo> ent : taskInfos.entrySet()) { // taskInfos
            String taskId = ent.getKey();
            TaskInfo taskInfo = ent.getValue();
            TTaskInfo tTaskInfo = new TTaskInfo();
            for(Map.Entry<String, String> entT : taskInfo.hm.entrySet()){
              tTaskInfo.putToInfoMap(entT.getKey(), entT.getValue());
            }
            tQueryInfo.putToTaskInfos(taskId, tTaskInfo);
          }
        }
      }
      return tQueryInfo;
    }

    @Override
    public void start(String query) throws HiveServerException, TException {
      if (isQueryRunning) {
        throw new HiveServerException(
            "Another query is running on this session", -1, "");
      }

      HiveServerHandler.LOG.info("Running the query: " + query);
      SessionState.get();

      String cmd_trimmed = query.trim();
      String[] tokens = cmd_trimmed.split("\\s");
      String cmd_1 = cmd_trimmed.substring(tokens[0].length()).trim();

      CommandProcessor proc = CommandProcessorFactory.get(tokens[0]);
      if (proc != null) {
        if (proc instanceof Driver) {
          isHiveQuery = true;
          driver.start(query);
          isQueryRunning = true;
        } else {
          isHiveQuery = false;
          try {
            proc.run(cmd_1);
          } catch (CommandNeedRetryException e) {
            // not Driver, may not happen
            e.printStackTrace();
          }
        }
      }
    }

    private static final TCommandProcessorResponse notReady =
      new TCommandProcessorResponse("", 0, "", false);
    private static final TCommandProcessorResponse dummyDone =
      new TCommandProcessorResponse("", 0, "", true);

    @Override
    public TCommandProcessorResponse getResponse() throws TException {
      if (!isQueryRunning) {
        return dummyDone;
      }
      CommandProcessorResponse response = driver.getResponse();
      if (response == null) {
        return notReady;
      } else {
        isQueryRunning = false;
        return new TCommandProcessorResponse(
            response.getErrorMessage(),
            response.getResponseCode(),
            response.getSQLState(), true);
      }
    }
  }

  /**
   * ThriftHiveProcessorFactory.
   *
   */
  public static class ThriftHiveProcessorFactory extends TProcessorFactory {
    public ThriftHiveProcessorFactory(TProcessor processor) {
      super(processor);
    }

    @Override
    public TProcessor getProcessor(TTransport trans) {
      try {
        Iface handler = new HiveServerHandler(trans);
        return new ThriftHive.Processor(handler);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static void main(String[] args) {
    SessionState.initHiveLog4j();
    try {
      int port = 10000;
      int minWorkerThreads = 100; // default number of threads serving the Hive server
      if (args.length >= 1) {
        port = Integer.parseInt(args[0]);
      }
      if (args.length >= 2) {
        minWorkerThreads = Integer.parseInt(args[1]);
      }
      TServerTransport serverTransport = new TServerSocket(port);
      ThriftHiveProcessorFactory hfactory = new ThriftHiveProcessorFactory(null);
      TThreadPoolServer.Options options = new TThreadPoolServer.Options();
      options.minWorkerThreads = minWorkerThreads;
      TServer server = new TThreadPoolServer(hfactory, serverTransport,
          new TTransportFactory(), new TTransportFactory(),
          new TBinaryProtocol.Factory(), new TBinaryProtocol.Factory(), options);
      HiveServerHandler.LOG.info("Starting hive server on port " + port);
      server.serve();
    } catch (Exception x) {
      x.printStackTrace();
    }
  }
}
