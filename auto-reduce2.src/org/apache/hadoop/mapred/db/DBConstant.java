package org.apache.hadoop.mapred.db;

public class DBConstant
{
  public static final String TABLE_NAME = "hive_optimize";
  public static final String EXCLUSIVE_TABLE = "hive_optimize_exclusive";
  public static final String COL_SKY_ID = "id";
  public static final String COL_ACTION_ID = "actionid";
  public static final String COL_TREE_ID = "treeid";
  public static final String COL_BIZDATE = "bizdate";
  public static final String COL_MD5SUM = "md5sum";
  public static final String COL_STAGE_ID = "stage_id";
  public static final String COL_JOB_ID = "job_id";
  public static final String COL_REDUCE_BYTES = "reduce_bytes";
  public static final String COL_TIMESTAMP = "timestamp";
  public static final String COL_HDFS_BYTES_READ = "hdfs_bytes_read";
  public static final String COL_HDFS_BYTES_WRITTEN = "hdfs_bytes_written";
  public static final String COL_LOCAL_BYTES_READ = "local_bytes_read";
  public static final String COL_LOCAL_BYTES_WRITTEN = "local_bytes_written";
  public static final String COL_LAUNCHED_REDUCE_TASKS = "launched_reduce_tasks";
  public static final String COL_RACK_LOCAL_MAP_TASKS = "rack_local_map_tasks";
  public static final String COL_LAUNCHED_MAP_TASKS = "launched_map_tasks";
  public static final String COL_DATA_LOCAL_MAP_TASKS = "data_local_map_tasks";
  public static final String COL_REDUCE_INPUT_GROUPS = "reduce_input_groups";
  public static final String COL_COMBINE_OUTPUT_RECORDS = "combine_output_records";
  public static final String COL_MAP_INPUT_RECORDS = "map_input_records";
  public static final String COL_REDUCE_OUTPUT_RECORDS = "reduce_output_records";
  public static final String COL_MAP_OUTPUT_BYTES = "map_output_bytes";
  public static final String COL_MAP_INPUT_BYTES = "map_input_bytes";
  public static final String COL_COMBINE_INPUT_RECORDS = "combine_input_records";
  public static final String COL_MAP_OUTPUT_RECORDS = "map_output_records";
  public static final String COL_REDUCE_INPUT_RECORDS = "reduce_input_records";
  public static final String COL_COUNT = "count";
  public static final String COL_TYPE = "type";
  public static final String COL_PER = "mb_per_reduce";
  public static final String COL_NUM = "reduce_num";
}