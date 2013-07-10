package com.taobao.data.hive.jobexecinlocalhook;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.hooks.PreJobHook;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapred.JobConf;

public class JobExecInLocalConfSet implements PreJobHook{

  protected Log LOG = LogFactory.getLog(this.getClass().getName());

  @Override
  public void run(SessionState session, QueryPlan queryPlan, JobConf job, Integer taskId)
      throws Exception {
    if ( "local".equals(HiveConf.getVar(job, HiveConf.ConfVars.HADOOPJT)) ) { // only for local tasks
      // jobexecinlocalconf.io.sort.mb = 50; will set io.sort.mb = 50
      Iterator<Entry<String, String>> it = job.iterator();
      while (it.hasNext()) {
        Entry<String, String> e = it.next();
        String key = e.getKey();
        String value = e.getValue();
        if(key.startsWith("jobexecinlocalconf")) {
          String k = key.substring("jobexecinlocalconf.".length());
          job.set(k, value);
          LOG.info(" jobexecinlocalconf set "+k +"="+value);
        }
      }
    }
  }

}
