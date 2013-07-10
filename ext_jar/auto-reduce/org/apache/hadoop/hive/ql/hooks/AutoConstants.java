package org.apache.hadoop.hive.ql.hooks;

public class AutoConstants
{
  public static final String HOOK_REDNUM_ENABLE_KEY = "hive.job.hooks.autored.enable";
  public static final String HADOOP_AUTORED = "mapred.client.pre.hook.enabled";
  public static final String HOOK_REDNUM_EXCLUSIVE_KEY = "hive.job.hooks.autored.exclusive";
  public static final String HOOK_REDNUM_UPPERLIMIT_KEY = "hive.job.hooks.autored.upperlimit";
  public static final String HOOK_REDNUM_TIMESPAN_KEY = "hive.job.hooks.autored.timespan";
  public static final String HOOK_REDNUM_COMPTEXT_KEY = "hive.job.hooks.autored.comptext.enabled";
  public static final String HOOK_REDNUM_MD5_KEY = "hive.job.hooks.autored.md5";
  public static final String HOOK_REDNUM_TEXT_COMPRESS_KEY = "hive.job.hooks.autored.text_compress";
  public static final String SKYNET_ID_KEY = "mapred.job.skynet_id";
  public static final String SKYNET_ACTIONID_KEY = "mapred.job.skynet.actionid";
  public static final String SKYNET_TREEID_KEY = "mapred.job.skynet.jobid";
  public static final String SKYNET_BIZDATE_KEY = "mapred.job.skynet.bizdate";
  public static final String COMPRESS_OUTPUT_KEY = "hive.exec.compress.output";
  public static final String REDUCE_TASK_KEY = "mapred.reduce.tasks";
  public static final String META_DB_DRIVER_KEY = "javax.jdo.option.ConnectionDriverName";
  public static final String META_DB_URL_KEY = "taobao.meta.ConnectionURL";
  public static final String META_DB_USER_KEY = "javax.jdo.option.ConnectionUserName";
  public static final String META_DB_PASSWORD_KEY = "taobao.meta.ConnectionPassword";
  public static final String TEXT_FORMAT = "org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat";
  public static final String DFS_REPLICATION = "dfs.replication";
  public static final String DFS_INTERMEDIATE_REPLICATION = "dfs.intermediate.replication";
  public static final String DYNAMIC_PARTITION = "hive.exec.dynamic.partition";
  public static final String SCRIPT_NAME = "reduce.script.type";
  public static final String SCRIPT_FILE = "reduce.script.file.path";
  public static final String SCRIPT_PARAMETER = "reduce.script.parameter.jobconf.name";
  public static final String SCRIPT_COUNTER = "reduce.script.parameter.counter.name";
  public static final String SCRIPT_DEBUG = "reduce.script.parameter.debug.name";
  public static final String HOOK_SHUFFLE_ENABLE_KEY = "hive.job.hooks.autoshuf.enable";
  public static final String HADOOP_AUTOSHUF = "mapred.job.hooks.autoshuf.enable";
  public static final String RECORD_PERCENT = "io.sort.record.percent";
  public static final String SORT_MB = "io.sort.mb";
  public static final String JVM_SIZE = "mapred.child.java.opt";
  public static final String MERGE_SORT = "io.sort.factor";
  public static final String MASTER_SWITCH = "hive.job.hooks.automized.enable";
  public static String PRINTMORE = "auto.script.print.more";
}