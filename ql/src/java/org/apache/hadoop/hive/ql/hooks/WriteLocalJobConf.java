package org.apache.hadoop.hive.ql.hooks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;

public class WriteLocalJobConf implements PostJobHook{

  protected Log LOG = LogFactory.getLog(this.getClass().getName());

  private String getDate(){
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(currentTime);
    return dateString;
  }

  private String getTimeToMinute(){
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
    String dateString = formatter.format(currentTime);
    return dateString;
  }

  @Override
  public void run(SessionState session, QueryPlan queryPlan, JobConf job, RunningJob runningJob,
      Integer taskId) {
    if ( "local".equals(HiveConf.getVar(job, HiveConf.ConfVars.HADOOPJT)) ) { // only for local tasks
      // get conf dir for writing conf to FileSystem
      String localModeConfDir = job.get("hive.exec.mode.local.conf.dir").trim();
      if ("".equals(localModeConfDir)) {
        return ;
      }
      String execPlan = job.get("hive.exec.plan");
      StringBuilder pathSB = new StringBuilder();
      pathSB.append(localModeConfDir);
      pathSB.append(Path.SEPARATOR);
      pathSB.append(getDate());
      pathSB.append(Path.SEPARATOR);
      pathSB.append("hdpgw_job_");
      pathSB.append(getTimeToMinute());
      pathSB.append("_");
      pathSB.append(execPlan.substring(execPlan.lastIndexOf(Path.SEPARATOR)+1));
      pathSB.append("_conf.xml");
      Path submitJobFile = new Path(pathSB.toString());

      FileSystem fs;
      FSDataOutputStream out = null;
      try {
        fs = submitJobFile.getFileSystem(job);
        // Write job file to JobTracker's fs
        out = fs.create(submitJobFile);
        job.writeXml(out);
      } catch (IOException e1) {
        LOG.error(e1.getMessage());
      } finally {
        if (out != null) {
          try {
            out.close();
          } catch (IOException e) {
            LOG.error(e.getMessage());
            return ;
          }
          LOG.info("Write local job conf to "+submitJobFile.toString());
        }
      }
    }
  }

}
