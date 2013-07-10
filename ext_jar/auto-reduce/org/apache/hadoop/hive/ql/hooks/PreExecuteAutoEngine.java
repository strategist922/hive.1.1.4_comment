package org.apache.hadoop.hive.ql.hooks;

import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.hooks.PreExecute;
import org.apache.hadoop.hive.ql.hooks.ReadEntity;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.ql.session.SessionState.LogHelper;
import org.apache.hadoop.security.UserGroupInformation;

public class PreExecuteAutoEngine
  implements PreExecute
{
  public void run(SessionState arg0, Set<ReadEntity> arg1, Set<WriteEntity> arg2, UserGroupInformation arg3)
    throws Exception
  {
    SessionState.LogHelper console = SessionState.getConsole();
    String className = getClass().getSimpleName();
    HiveConf conf = arg0.getConf();

    if (!conf.getBoolean("hive.job.hooks.automized.enable", false)) {
      console.printError(className + ": Disabled");
      return;
    }

    boolean isCompress = conf.getBoolean("hive.exec.compress.output", false);
    if (isCompress) {
      Iterator it = arg2.iterator();
      while (it.hasNext()) {
        Table t = ((WriteEntity)it.next()).getTable();
        if (t == null) {
          continue;
        }
        String outputFormat = t.getOutputFormatClass().getName();
        if ("org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat".equals(outputFormat))
        {
          conf.setBoolean("hive.job.hooks.autored.text_compress", true);
          break;
        }
      }
    }

    String query = arg0.getCmd().trim();
    Query q = new Query(query);
    String md5 = q.getMd5sum();
    if ("".equals(md5)) {
      return;
    }

    conf.set("hive.job.hooks.autored.md5", md5);
  }
}