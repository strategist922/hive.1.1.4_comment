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
package org.apache.hadoop.hive.shims;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobContext;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapred.MultiFileInputFormat;
import org.apache.hadoop.mapred.MultiFileSplit;
import org.apache.hadoop.mapred.OutputCommitter;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TaskAttemptContext;
import org.apache.hadoop.mapred.TaskCompletionEvent;
import org.apache.hadoop.mapred.TaskID;
import org.apache.hadoop.mapred.lib.NullOutputFormat;

/**
 * Implemention of shims against Hadoop 0.19.0.
 */
public class Hadoop19Shims implements HadoopShims {
  public boolean usesJobShell() {
    return true;
  }

  public boolean fileSystemDeleteOnExit(FileSystem fs, Path path)
    throws IOException {

    return fs.deleteOnExit(path);
  }

  public void inputFormatValidateInput(InputFormat fmt, JobConf conf)
    throws IOException {
    // gone in 0.18+
  }

  public boolean isJobPreparing(RunningJob job) throws IOException {
    return job.getJobState() == JobStatus.PREP;
  }

  /**
   * workaround for hadoop-17 - jobclient only looks at commandlineconfig.
   */
  public void setTmpFiles(String prop, String files) {
    Configuration conf = JobClient.getCommandLineConfig();
    if (conf != null) {
      conf.set(prop, files);
    }
  }

  public HadoopShims.MiniDFSShim getMiniDfs(Configuration conf,
                                int numDataNodes,
                                boolean format,
                                String[] racks) throws IOException {
    return new MiniDFSShim(new MiniDFSCluster(conf, numDataNodes, format, racks));
  }

  public class MiniDFSShim implements HadoopShims.MiniDFSShim {
    private final MiniDFSCluster cluster;
    public MiniDFSShim(MiniDFSCluster cluster) {
      this.cluster = cluster;
    }

    public FileSystem getFileSystem() throws IOException {
      return cluster.getFileSystem();
    }

    public void shutdown() {
      cluster.shutdown();
    }
  }

  /**
   * We define this function here to make the code compatible between
   * hadoop 0.17 and hadoop 0.20.
   *
   * Hive binary that compiled Text.compareTo(Text) with hadoop 0.20 won't
   * work with hadoop 0.17 because in hadoop 0.20, Text.compareTo(Text) is
   * implemented in org.apache.hadoop.io.BinaryComparable, and Java compiler
   * references that class, which is not available in hadoop 0.17.
   */
  public int compareText(Text a, Text b) {
    return a.compareTo(b);
  }

  public HadoopShims.CombineFileInputFormatShim getCombineFileInputFormat() {
    return new CombineFileInputFormatShim() {

      @Override
      public RecordReader getRecordReader(InputSplit split, JobConf arg1,
          Reporter arg2) throws IOException {
        throw new IOException(
            "CombineFileInputFormat.getRecordReader not needed.");
      }
    };
  }

  public String getInputFormatClassName() {
    return "org.apache.hadoop.hive.ql.io.HiveInputFormat";
  }

  /**
   * MultiFileShim code here
   *
   *
   */
  public abstract static class CombineFileInputFormatShim<K, V> extends
      MultiFileInputFormat<K, V> implements
      HadoopShims.CombineFileInputFormatShim<K, V> {

    /**
     * gets the input paths from static method in parent class. Same code in the
     * hadoop20shim, adapted for @link{MultiFileInputFormat}
     *
     * @param conf
     * @return Path[] of all files to be processed.
     */
    public Path[] getInputPathsShim(JobConf conf) {
      try {
        return FileInputFormat.getInputPaths(conf);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Not supported by MultiFileInputFormat so it doesn't do anything
     *
     * @param conf
     * @param filters
     */
    public void createPool(JobConf conf, PathFilter... filters) {
      LOG.debug("createPool called.");
    }

    @Override
    public InputSplitShim[] getSplits(JobConf job, int numSplits)
        throws IOException {
      long minSize = job.getLong("mapred.min.split.size", 0);

      // For backward compatibility, let the above parameter be used
      if (job.getLong("mapred.min.split.size.per.node", 0) == 0) {
        super.setMinSplitSize(minSize);
      }

      if (job.getLong("mapred.min.split.size.per.rack", 0) == 0) {
        super.setMinSplitSize(minSize);
      }

      ArrayList<InputSplitShim> splits = new ArrayList<InputSplitShim>();
      for (Path p : getInputPathsShim(job)) {
        List<MultiFileSplit> split = getSplitsForPath(job, p, 1);
        if (split != null) {
          for (MultiFileSplit s : split) {
            splits.add(new InputSplitShim(job, s));
          }
        }
      }
      LOG.debug("MultiFileSplit returned " + splits.size() + " splits.");
      return splits.toArray(new InputSplitShim[splits.size()]);
    }

    public InputSplitShim getInputSplitShim() throws IOException {
      return new InputSplitShim();
    }

    public RecordReader<K, V> getRecordReader(JobConf job,
        HadoopShims.InputSplitShim split, Reporter reporter,
        Class<RecordReader<K, V>> rrClass) throws IOException {
      return new MultiFileRecordReader<K, V>(job, (InputSplitShim) split,
          reporter, rrClass);
    }

    private List<MultiFileSplit> getSplitsForPath(JobConf job, Path path, int numSplits)
        throws IOException {
      FileSystem fs = path.getFileSystem(job);
      Path[] paths = FileUtil.stat2Paths(fs.listStatus(path));
      List<MultiFileSplit> splits = new ArrayList<MultiFileSplit>(Math.min(numSplits, paths.length));
      if (paths.length != 0) {
        // HADOOP-1818: Manage splits only if there are paths
        long[] lengths = new long[paths.length];
        long totLength = 0;
        for(int i=0; i<paths.length; i++) {
          lengths[i] = fs.getContentSummary(paths[i]).getLength();
          totLength += lengths[i];
        }
        long sizePerTask = job.getLong("hive.merge.size.per.task", 67108864);
        long filesPerTask = job.getInt("hive.merge.files.per.task", 100);
        numSplits = (int) Math.max(numSplits, Math.ceil(totLength / sizePerTask));
        if (numSplits < 1) {
          numSplits = 1;
        }
        double avgLengthPerSplit = ((double)totLength) / numSplits;
        long cumulativeLength = 0;

        int startIndex = 0;

        int totalSize = 0;
        int splitIndex = 0;
        while (totalSize < lengths.length) {
          int splitSize = findSize(splitIndex, avgLengthPerSplit, cumulativeLength
              , startIndex, lengths, filesPerTask);
          splitIndex ++;
          totalSize += splitSize;
          if (splitSize != 0) {
            // HADOOP-1818: Manage split only if split size is not equals to 0
            Path[] splitPaths = new Path[splitSize];
            long[] splitLengths = new long[splitSize];
            System.arraycopy(paths, startIndex, splitPaths , 0, splitSize);
            System.arraycopy(lengths, startIndex, splitLengths , 0, splitSize);
            splits.add(new MultiFileSplit(job, splitPaths, splitLengths));
            startIndex += splitSize;
            for(long l: splitLengths) {
              cumulativeLength += l;
            }
          }
        }
      }
      return splits;
    }

    private int findSize(int splitIndex, double avgLengthPerSplit
        , long cumulativeLength , int startIndex, long[] lengths, long maxSize) {

      if(splitIndex == lengths.length - 1) {
        return lengths.length - startIndex;
      }

      long goalLength = (long)((splitIndex + 1) * avgLengthPerSplit);
      long partialLength = 0;
      // accumulate till just above the goal length;
      for(int i = startIndex; i < lengths.length; i++) {
        partialLength += lengths[i];
        if(partialLength + cumulativeLength >= goalLength || i - startIndex + 1 >= maxSize) {
          return i - startIndex + 1;
        }
      }
      return lengths.length - startIndex;
    }
  }

  /**
   * We need to supply a RecordReader.
   */
  public static class MultiFileRecordReader<K, V> implements RecordReader<K, V> {

    protected InputSplitShim split;
    protected JobConf jc;
    protected Reporter reporter;
    protected Class<RecordReader<K, V>> rrClass;
    protected Constructor<RecordReader<K, V>> rrConstructor;
    protected FileSystem fs;
    protected int idx;
    protected long progress;
    protected RecordReader<K, V> curReader;

    public boolean next(K key, V value) throws IOException {

      while ((curReader == null) || !curReader.next(key, value)) {
        if (!initNextRecordReader()) {
          return false;
        }
      }
      return true;
    }

    public K createKey() {
      return curReader.createKey();
    }

    public V createValue() {
      return curReader.createValue();
    }

    /**
     * return the amount of data processed
     */
    public long getPos() throws IOException {
      return progress;
    }

    public void close() throws IOException {
      if (curReader != null) {
        curReader.close();
        curReader = null;
      }
    }

    /**
     * return progress based on the amount of data processed so far.
     */
    public float getProgress() throws IOException {
      return Math.min(1.0f, progress / (float) (split.getLength()));
    }

    static final Class[] constructorSignature = new Class[] { InputSplit.class,
        Configuration.class, Reporter.class, Integer.class };

    /**
     * A generic RecordReader that can hand out different recordReaders for each
     * chunk in the CombineFileSplit.
     */
    public MultiFileRecordReader(JobConf job, InputSplitShim split,
        Reporter reporter, Class<RecordReader<K, V>> rrClass)
        throws IOException {
      this.split = split;
      this.jc = job;
      this.rrClass = rrClass;
      this.reporter = reporter;
      this.idx = 0;
      this.curReader = null;
      this.progress = 0;

      try {
        rrConstructor = rrClass.getDeclaredConstructor(constructorSignature);
        rrConstructor.setAccessible(true);
      } catch (Exception e) {
        throw new RuntimeException(rrClass.getName()
            + " does not have valid constructor", e);
      }
      initNextRecordReader();
    }

    /**
     * Get the record reader for the next chunk in this CombineFileSplit.
     */
    protected boolean initNextRecordReader() throws IOException {
      if (curReader != null) {
        curReader.close();
        curReader = null;
        if (idx > 0) {
          progress += split.getLength(idx - 1); // done processing so far
        }
      }

      // if all chunks have been processed, nothing more to do.
      if (idx == split.getNumPaths()) {
        return false;
      }

      // get a record reader for the idx-th chunk
      try {
        /*
         * in mapper task, the split is constructed with the default
         * constructor, so it won't have the jc, resulting in
         * NullPointerException
         */
        InputSplitShim newSplit = new InputSplitShim(jc, split.delegate);

        curReader = rrConstructor.newInstance(new Object[] {
            (InputSplit) newSplit, jc, reporter, Integer.valueOf(idx) });

        // setup some helper config variables.
        jc.set("map.input.file", split.getPath(idx).toString());
        jc.setLong("map.input.start", 0); // split.getOffset(idx) instead of 0
                                          // in CombineFileSplit
        jc.setLong("map.input.length", split.getLength(idx));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      idx++;
      return true;
    }
  }

  /**
   * InputSplitShim
   *
   */
  public static class InputSplitShim // extends MultiFileSplit
      implements HadoopShims.InputSplitShim {

    MultiFileSplit delegate;
    long[] offsets;
    JobConf job;
    String constructor = "none";

    public InputSplitShim() {
      delegate = new MultiFileSplit(null, new Path[0], new long[0]);
      onDelegateChange(delegate);
      constructor = "default";
    }

    /**
     * It encapsulate a set of files
     *
     * @param job
     * @param old
     * @throws IOException
     */
    public InputSplitShim(JobConf job, MultiFileSplit old) throws IOException {
      delegate = new MultiFileSplit(job, old.getPaths(), old.getLengths());
      offsets = new long[old.getPaths().length];
      this.job = job;
      constructor = "copy";
    }

    public long[] getStartOffsets() {
      return offsets;
    }

    public long getOffset(int i) {
      return 0;
    }

    public JobConf getJob() {
      return job;
    }

    public long getLength() {
      return delegate.getLength();
    }

    public long[] getLengths() {
      return delegate.getLengths();
    }

    public long getLength(int i) {
      return delegate.getLength(i);
    }

    public int getNumPaths() {
      return delegate.getNumPaths();
    }

    public Path getPath(int i) {
      return delegate.getPath(i);
    }

    public Path[] getPaths() {
      return delegate.getPaths();
    }

    public String[] getLocations() throws IOException {
      return delegate.getLocations();
    }

    public void readFields(DataInput in) throws IOException {
      delegate.readFields(in);
      onDelegateChange(delegate);
    }

    public void write(DataOutput out) throws IOException {
      delegate.write(out);
    }

    private void onDelegateChange(MultiFileSplit delegate) {
      if (delegate != null) {
        offsets = new long[delegate.getNumPaths()];
      }
    }

    private void setJob(JobConf jc) {
      job = jc;
    }
  }

  @Override
  public long getAccessTime(FileStatus file) {
    return file.getAccessTime();
  }

  String [] ret = new String[2];

  @Override
  public String [] getTaskJobIDs(TaskCompletionEvent t) {
    TaskID tid = t.getTaskAttemptId().getTaskID();
    ret[0] = tid.toString();
    ret[1] = tid.getJobID().toString();
    return ret;
  }

  public void setFloatConf(Configuration conf, String varName, float val) {
    conf.set(varName, Float.toString(val));
  }

  @Override
  public int createHadoopArchive(Configuration conf, Path parentDir, Path destDir,
      String archiveName) throws Exception {
    throw new RuntimeException("Not implemented in this Hadoop version");
  }

  public static class NullOutputCommitter extends OutputCommitter {
    @Override
    public void setupJob(JobContext jobContext) { }
    @Override
    public void cleanupJob(JobContext jobContext) { }

    @Override
    public void setupTask(TaskAttemptContext taskContext) { }
    @Override
    public boolean needsTaskCommit(TaskAttemptContext taskContext) {
      return false;
    }
    @Override
    public void commitTask(TaskAttemptContext taskContext) { }
    @Override
    public void abortTask(TaskAttemptContext taskContext) { }
  }

  public void setNullOutputFormat(JobConf conf) {
    conf.setOutputFormat(NullOutputFormat.class);
    conf.setOutputCommitter(Hadoop19Shims.NullOutputCommitter.class);


    // option to bypass job setup and cleanup was introduced in hadoop-21 (MAPREDUCE-463)
    // but can be backported. So we disable setup/cleanup in all versions >= 0.19
    conf.setBoolean("mapred.committer.job.setup.cleanup.needed", false);
  }
}
