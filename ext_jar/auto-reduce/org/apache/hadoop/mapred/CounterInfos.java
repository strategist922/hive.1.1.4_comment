package org.apache.hadoop.mapred;

public class CounterInfos
{
  private long hdfsBytesRead = 0L;
  private long hdfsBytesWritten = 0L;
  private long localBytesRead = 0L;
  private long localBytesWritten = 0L;
  private long launchedReduceTasks = 0L;
  private long rackLocalMapTasks = 0L;
  private long launchedMapTasks = 0L;
  private long dataLocalMapTasks = 0L;
  private long reduceInputGroups = 0L;
  private long combineOutputRecords = 0L;
  private long mapInputRecords = 0L;
  private long reduceOutputRecords = 0L;
  private long mapOutputBytes = 0L;
  private long mapInputBytes = 0L;
  private long combineInputRecords = 0L;
  private long mapOutputRecords = 0L;
  private long reduceInputRecords = 0L;

  public CounterInfos() {
    this.hdfsBytesRead = 0L;
    this.hdfsBytesWritten = 0L;
    this.localBytesRead = 0L;
    this.localBytesWritten = 0L;
    this.launchedReduceTasks = 0L;
    this.rackLocalMapTasks = 0L;
    this.launchedMapTasks = 0L;
    this.dataLocalMapTasks = 0L;
    this.reduceInputGroups = 0L;
    this.combineOutputRecords = 0L;
    this.mapInputRecords = 0L;
    this.reduceOutputRecords = 0L;
    this.mapOutputBytes = 0L;
    this.mapInputBytes = 0L;
    this.combineInputRecords = 0L;
    this.mapOutputRecords = 0L;
    this.reduceInputRecords = 0L;
  }

  public CounterInfos(Counters cs) {
    this.hdfsBytesRead = cs.getCounter(Task.FileSystemCounter.HDFS_READ);
    this.hdfsBytesWritten = cs.getCounter(Task.FileSystemCounter.HDFS_WRITE);
    this.localBytesRead = cs.getCounter(Task.FileSystemCounter.LOCAL_READ);
    this.localBytesWritten = cs.getCounter(Task.FileSystemCounter.LOCAL_WRITE);
    this.launchedReduceTasks = cs.getCounter(JobInProgress.Counter.TOTAL_LAUNCHED_REDUCES);
    this.rackLocalMapTasks = cs.getCounter(JobInProgress.Counter.RACK_LOCAL_MAPS);
    this.launchedMapTasks = cs.getCounter(JobInProgress.Counter.TOTAL_LAUNCHED_MAPS);
    this.dataLocalMapTasks = cs.getCounter(JobInProgress.Counter.DATA_LOCAL_MAPS);
    this.reduceInputGroups = cs.getCounter(Task.Counter.REDUCE_INPUT_GROUPS);
    this.combineOutputRecords = cs.getCounter(Task.Counter.COMBINE_OUTPUT_RECORDS);
    this.mapInputRecords = cs.getCounter(Task.Counter.MAP_INPUT_RECORDS);
    this.reduceOutputRecords = cs.getCounter(Task.Counter.REDUCE_OUTPUT_RECORDS);
    this.mapOutputBytes = cs.getCounter(Task.Counter.MAP_OUTPUT_BYTES);
    this.mapInputBytes = cs.getCounter(Task.Counter.MAP_INPUT_BYTES);
    this.combineInputRecords = cs.getCounter(Task.Counter.COMBINE_INPUT_RECORDS);
    this.mapOutputRecords = cs.getCounter(Task.Counter.MAP_OUTPUT_RECORDS);
    this.reduceInputRecords = cs.getCounter(Task.Counter.REDUCE_INPUT_RECORDS);
  }

  public void setHdfsBytesRead(long hdfsBytesRead)
  {
    this.hdfsBytesRead = hdfsBytesRead;
  }

  public long getHdfsBytesRead() {
    return this.hdfsBytesRead;
  }

  public void setHdfsBytesWritten(long hdfsBytesWritten) {
    this.hdfsBytesWritten = hdfsBytesWritten;
  }

  public long getHdfsBytesWritten() {
    return this.hdfsBytesWritten;
  }

  public void setLocalBytesRead(long localBytesRead) {
    this.localBytesRead = localBytesRead;
  }

  public long getLocalBytesRead() {
    return this.localBytesRead;
  }

  public void setLocalBytesWritten(long localBytesWritten) {
    this.localBytesWritten = localBytesWritten;
  }

  public long getLocalBytesWritten() {
    return this.localBytesWritten;
  }

  public void setLaunchedReduceTasks(long launchedReduceTasks) {
    this.launchedReduceTasks = launchedReduceTasks;
  }

  public long getLaunchedReduceTasks() {
    return this.launchedReduceTasks;
  }

  public void setLaunchedMapTasks(long launchedMapTasks) {
    this.launchedMapTasks = launchedMapTasks;
  }

  public long getLaunchedMapTasks() {
    return this.launchedMapTasks;
  }

  public void setReduceInputGroups(long reduceInputGroups) {
    this.reduceInputGroups = reduceInputGroups;
  }

  public long getReduceInputGroups() {
    return this.reduceInputGroups;
  }

  public void setCombineOutputRecords(long combineOutputRecords) {
    this.combineOutputRecords = combineOutputRecords;
  }

  public long getCombineOutputRecords() {
    return this.combineOutputRecords;
  }

  public void setMapInputRecords(long mapInputRecords) {
    this.mapInputRecords = mapInputRecords;
  }

  public long getMapInputRecords() {
    return this.mapInputRecords;
  }

  public void setReduceOutputRecords(long reduceOutputRecords) {
    this.reduceOutputRecords = reduceOutputRecords;
  }

  public long getReduceOutputRecords() {
    return this.reduceOutputRecords;
  }

  public void setMapOutputBytes(long mapOutputBytes) {
    this.mapOutputBytes = mapOutputBytes;
  }

  public long getMapOutputBytes() {
    return this.mapOutputBytes;
  }

  public void setCombineInputRecords(long combineInputRecords) {
    this.combineInputRecords = combineInputRecords;
  }

  public long getCombineInputRecords() {
    return this.combineInputRecords;
  }

  public void setMapInputBytes(long mapInputBytes) {
    this.mapInputBytes = mapInputBytes;
  }

  public long getMapInputBytes() {
    return this.mapInputBytes;
  }

  public void setMapOutputRecords(long mapOutputRecords) {
    this.mapOutputRecords = mapOutputRecords;
  }

  public long getMapOutputRecords() {
    return this.mapOutputRecords;
  }

  public void setReduceInputRecords(long reduceInputRecords) {
    this.reduceInputRecords = reduceInputRecords;
  }

  public long getReduceInputRecords() {
    return this.reduceInputRecords;
  }

  public void setRackLocalMapTasks(long rackLocalMapTasks) {
    this.rackLocalMapTasks = rackLocalMapTasks;
  }

  public long getRackLocalMapTasks() {
    return this.rackLocalMapTasks;
  }

  public void setDataLocalMapTasks(long dataLocalMapTasks) {
    this.dataLocalMapTasks = dataLocalMapTasks;
  }

  public long getDataLocalMapTasks() {
    return this.dataLocalMapTasks;
  }
}