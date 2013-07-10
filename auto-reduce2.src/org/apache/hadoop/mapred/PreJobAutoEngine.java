package org.apache.hadoop.mapred;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.script.ScriptException;
import org.apache.hadoop.common.SkynetException;
import org.apache.hadoop.mapred.fingerprint.FileMd5Feature;
import org.apache.hadoop.mapred.fingerprint.Fingerprint;
import org.apache.hadoop.mapred.fingerprint.JobConfFeature;
import org.apache.hadoop.mapred.script.ScriptInvocation;

public class PreJobAutoEngine
  implements PreJobHook
{
  static boolean isDebug;
  String className = getClass().getSimpleName();

  public void run(JobID jobId, JobConf jobConf, List<PreJobHook.JobFileMD5> md5s)
    throws Exception
  {
    isDebug = jobConf.getBoolean("auto.script.print.more", false);

    if (jobConf.get("hive.job.hooks.autored.enable") != null) {//如果hive那边带开了，mr这边关闭
      print2Console(isDebug, this.className + ": Info = Hive AutoReduce is Already run out.");

      jobConf.setBoolean("mapred.client.hooks.autored.enabled", true);
      return;
    }
    initJobConf(jobConf);

    int timespan = jobConf.getInt("mapred.client.hooks.autored.timespan", 7);

    Fingerprint fingerprint = new Fingerprint();
    JobConfFeature jcf = new JobConfFeature(jobId.toString(), jobConf);
    print2Console(isDebug, this.className + ": JobConf md5 = " + jcf.getFeature());

    fingerprint.addFeature(jcf);

    String fileTypesStr = jobConf.get("mapred.client.hooks.autored.md5.types", "");
    if (!"".equals(fileTypesStr)) {
      for (String type : fileTypesStr.split(",")) {
        try {
          PreJobHook.JobFileType fileType = (PreJobHook.JobFileType)Enum.valueOf(PreJobHook.JobFileType.class, type.toUpperCase());

          fingerprint.addFeature(new FileMd5Feature(fileType, md5s));
        } catch (Exception e) {
          print2Console(isDebug, this.className + ": Unkown " + "mapred.client.hooks.autored.md5.types" + " = " + type);
        }
      }

    }

    String key = fingerprint.toString();

    jobConf.set("mapred.client.hooks.autored.md5", key);

    String md5 = jobConf.get("mapred.client.hooks.autored.md5", "");
    if ("".equals(md5)) {
      print2Console(isDebug, this.className + ":md5sum is missing!");
      return;
    }

    executeScript(jobConf, md5, timespan);
  }

  private static void executeScript(JobConf conf, String md5, int timespan)
  {
    try
    {
      DataUtil util = new DataUtil(conf);

      util.setExclusive();

      Map debug = new HashMap();

      Map counterInfos = util.getAverage(md5, timespan);

      ScriptInvocation invocation = new ScriptInvocation(conf, counterInfos, debug);

      long begin = Calendar.getInstance().getTimeInMillis();

      invocation.run();

      long end = Calendar.getInstance().getTimeInMillis();

      print2Console(isDebug, "PreJobAutoReduce: counters compute + script execute time(ms)=" + (end - begin));

      Set debug_keys = debug.keySet();

      print2Console(true, "PreJobAutoReduce: optimize rule:");
      for (Iterator it = debug_keys.iterator(); it.hasNext(); ) {
        String debug_key = (String)it.next();
        System.err.println(debug_key + "=>" + debug.get(debug_key));
      }

      invocation.close();

      util.close();
    }
    catch (ScriptException se) {
      print2Console(isDebug, "PreJobAutoReduce:script execute error " + se.getMessage());

      se.printStackTrace();
    } catch (SkynetException sne) {
      System.out.println("Warning:Can't find Skynet info:" + sne.getMessage());
    }
    catch (FileNotFoundException ffe)
    {
      System.err.println("Error锛歵he script file is not found");
    } catch (Exception e) {
      System.err.println("Error:script optimize dosn't execute:" + e.getMessage());
    }
  }

  private void initJobConf(JobConf jobConf)
  {
    if ((jobConf.get("mapred.output.format.class", "").equals("org.apache.hadoop.mapred.TextOutputFormat")) && (jobConf.getBoolean("mapred.output.compress", false)) && (!jobConf.getBoolean("mapred.client.hooks.autored.comptext.enabled", false)))
    {
      print2Console(isDebug, this.className + ":Output is compressed TextFile,Disabled!");

      jobConf.setBoolean("mapred.client.hooks.autored.enabled", false);
      return;
    }
  }

  public static void print2Console(boolean debug, String info)
  {
    System.err.println(info);
  }

  public static void main(String[] args) {
    JobConf conf = new JobConf();

    conf.setBoolean("mapred.client.hooks.automized.enabled", true);
    conf.setBoolean("mapred.client.hooks.autoshuf.enabled", true);
    conf.setBoolean("mapred.client.hooks.autored.enabled", true);

    conf.setBoolean("auto.script.print.more", false);

    conf.setInt("skynet.priority.reduce.speculative.threshold", 2);
    conf.setInt("mapred.job.level", 1);

    conf.set("taobao.meta.ConnectionDriverName", "com.mysql.jdbc.Driver");

    conf.set("taobao.meta.ConnectionURL", "jdbc:mysql://10.232.128.68/new_tbdev_hive");

    conf.set("taobao.meta.ConnectionUserName", "hive");

    conf.set("taobao.meta.ConnectionPassword", "hive");

    conf.set(AutoConstant.SCRIPT_FILE, "lib/velocity/HadoopAuto.vm");
    conf.setInt("mapred.job.skynet_id", 10320);

    executeScript(conf, "ab2d5ab474f409641273ff928c4d8c6c", 7);
  }
}