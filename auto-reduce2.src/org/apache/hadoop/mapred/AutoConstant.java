package org.apache.hadoop.mapred;

import java.util.regex.Pattern;

public class AutoConstant
{
  public static final String AUTORED_ENABLE = "mapred.client.hooks.autored.enabled";
  public static final String AUTORED_PROPS = "mapred.client.hooks.autored.props";
  public static final String AUTORED_PROPS_LEVEL = "mapred.client.hooks.autored.props.level";
  public static final String AUTORED_COLLECT_INFO = "mapred.client.hooks.autored.collectinfo";
  public static final String AUTORED_EXCLUSIVE = "mapred.client.hooks.autored.exclusive";
  public static final String AUTORED_TIMESPAN = "mapred.client.hooks.autored.timespan";
  public static final String AUTORED_COMPTEXT = "mapred.client.hooks.autored.comptext.enabled";
  public static final String AUTORED_OUTPUT_FORMAT = "mapred.output.format.class";
  public static final String AUTORED_OUTPUT_TEXT = "org.apache.hadoop.mapred.TextOutputFormat";
  public static final String AUTORED_COMPRESSED = "mapred.output.compress";
  public static final String AUTORED_MD5 = "mapred.client.hooks.autored.md5";
  public static final String REDUCE_TASK = "mapred.reduce.tasks";
  public static final String META_DB_DRIVER = "taobao.meta.ConnectionDriverName";
  public static final String META_DB_URL = "taobao.meta.ConnectionURL";
  public static final String META_DB_USER = "taobao.meta.ConnectionUserName";
  public static final String META_DB_PASSWORD = "taobao.meta.ConnectionPassword";
  public static final String HIVE_AUTORED_ENABLE = "hive.job.hooks.autored.enable";
  public static final String JOBCONF_SKIP_KEYS = "mapred.client.hooks.autored.jobconf.skipped";
  public static final String MD5_FILE_TYPES = "mapred.client.hooks.autored.md5.types";
  public static final String DB_META_TABLE = "mapred.client.hooks.autored.dbtab";
  public static final String KEY_VALUE_SPLIT = "\001";
  public static final Pattern DATATIME_PATTERN = Pattern.compile("([1-2][0-9]{3}([-|/|.|_|:])?(((0[13578]|1[02])([-|/|.|_|:])?(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)([-|/|.|_|:])?(0[1-9]|[12][0-9]|30))|(02([-|/|.|_|:])?([0-1][0-9]|2[0-9]))))[\\s]?(([0-1][0-9]|2[0-3])([-|/|.|_|:])?([0-5][0-9])([-|/|.|_|:])?([0-5][0-9])){0,1}");
  public static final String SKYNET_ID = "mapred.job.skynet_id";
  public static final String SKYNET_ACTIONID = "mapred.job.skynet.actionid";
  public static final String SKYNET_TREEID = "mapred.job.skynet.jobid";
  public static final String SKYNET_BIZDATE = "mapred.job.skynet.bizdate";
  public static String SCRIPT_NAME = "reduce.script.type";

  public static String SCRIPT_FILE = "reduce.script.file.path";

  public static String SCRIPT_PARAMETER = "reduce.script.parameter.jobconf.name";

  public static String SCRIPT_COUNTER = "reduce.script.parameter.counter.name";

  public static String SCRIPT_DEBUG = "reduce.script.parameter.debug.name";
  public static final String AUTOSHUF_ENABLE = "mapred.client.hooks.autoshuf.enabled";
  public static final String RECORD_PERCENT = "io.sort.record.percent";
  public static final String SORT_MB = "io.sort.mb";
  public static final String JVM_SIZE = "mapred.child.java.opt";
  public static final String MERGE_SORT = "io.sort.factor";
  public static final String MASTER_SWITCH = "mapred.client.hooks.automized.enabled";
  public static final String PRINTMORE = "auto.script.print.more";
}