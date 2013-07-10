package org.apache.hadoop.mapred;

import java.util.HashMap;
import java.util.Map;

public class CounterUti
{
  public static long getReduceOutputBytes(Counters cs)
  {
    return cs.getCounter(Task.Counter.MAP_OUTPUT_BYTES);
  }

  public static Map<Object, Object> getCounterInfos(Counters cs) {
    Map counterInfos = new HashMap();
    counterInfos.put("combine_input_records", Long.valueOf(cs.getCounter(Task.Counter.COMBINE_INPUT_RECORDS)));

    counterInfos.put("combine_output_records", Long.valueOf(cs.getCounter(Task.Counter.COMBINE_OUTPUT_RECORDS)));

    counterInfos.put("data_local_map_tasks", Long.valueOf(cs.getCounter(JobInProgress.Counter.DATA_LOCAL_MAPS)));

    counterInfos.put("hdfs_bytes_read", Long.valueOf(cs.getCounter(Task.FileSystemCounter.HDFS_READ)));

    counterInfos.put("hdfs_bytes_written", Long.valueOf(cs.getCounter(Task.FileSystemCounter.HDFS_WRITE)));

    counterInfos.put("launched_map_tasks", Long.valueOf(cs.getCounter(JobInProgress.Counter.TOTAL_LAUNCHED_MAPS)));

    counterInfos.put("launched_reduce_tasks", Long.valueOf(cs.getCounter(JobInProgress.Counter.TOTAL_LAUNCHED_REDUCES)));

    counterInfos.put("local_bytes_read", Long.valueOf(cs.getCounter(Task.FileSystemCounter.LOCAL_READ)));

    counterInfos.put("local_bytes_written", Long.valueOf(cs.getCounter(Task.FileSystemCounter.LOCAL_WRITE)));

    counterInfos.put("map_input_bytes", Long.valueOf(cs.getCounter(Task.Counter.MAP_INPUT_BYTES)));

    counterInfos.put("map_input_records", Long.valueOf(cs.getCounter(Task.Counter.MAP_INPUT_RECORDS)));

    counterInfos.put("map_output_bytes", Long.valueOf(cs.getCounter(Task.Counter.MAP_OUTPUT_BYTES)));

    counterInfos.put("map_output_records", Long.valueOf(cs.getCounter(Task.Counter.MAP_OUTPUT_RECORDS)));

    counterInfos.put("rack_local_map_tasks", Long.valueOf(cs.getCounter(JobInProgress.Counter.RACK_LOCAL_MAPS)));

    counterInfos.put("reduce_input_groups", Long.valueOf(cs.getCounter(Task.Counter.REDUCE_INPUT_GROUPS)));

    counterInfos.put("reduce_input_records", Long.valueOf(cs.getCounter(Task.Counter.REDUCE_INPUT_RECORDS)));

    counterInfos.put("reduce_output_records", Long.valueOf(cs.getCounter(Task.Counter.REDUCE_OUTPUT_RECORDS)));

    return counterInfos;
  }
}