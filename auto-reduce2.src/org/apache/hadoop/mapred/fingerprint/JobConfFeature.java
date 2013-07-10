package org.apache.hadoop.mapred.fingerprint;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.hadoop.mapred.AutoConstant;
import org.apache.hadoop.mapred.JobConf;

public class JobConfFeature
  implements Feature
{
  private JobConf jobConf;
  private Filter filter;
  private String featureStr;
  private String jobId;

  public JobConfFeature(String id, JobConf conf)
  {
    this.jobId = id;
    this.jobConf = conf;
    this.filter = new Filter();
    this.featureStr = null;
    initFilter();
  }

  public void addFilterStrainer(FilterStrainer fs) {
    this.filter.addFilterStrainer(fs);
  }

  public String getFeature()
  {
    if (this.featureStr != null) {
      return this.featureStr;
    }
    Iterator it = this.jobConf.iterator();
    StringBuilder sb = new StringBuilder();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      String input = new StringBuilder().append((String)entry.getKey()).append("\001").append((String)entry.getValue()).toString();

      sb.append(this.filter.getResult(input));
    }

    if (sb.length() <= 0) {
      this.featureStr = "";
    }
    else {
      this.featureStr = Md5Util.md5sum(sb.toString().getBytes());
    }
    return this.featureStr;
  }

  protected void initFilter()
  {
    KeyStrainer ks = new KeyStrainer();

    int level = this.jobConf.getInt("mapred.client.hooks.autored.props.level", 0);
    if (level != 0) {
      ks.setLevel(level);
    }
    ks.addKey("dfs.block.size", 2);
    ks.addKey("dfs.replication", 2);
    ks.addKey("dfs.replication.max", 3);
    ks.addKey("dfs.replication.min", 3);
    ks.addKey("dfs.client.block.write.retries", 3);
    ks.addKey("dfs.heartbeat.interval", 3);
    ks.addKey("fs.default.name", 2);
    ks.addKey("ipc.client.connection.maxidletime", 3);
    ks.addKey("ipc.client.tcpnodelay", 3);
    ks.addKey("ipc.client.connect.max.retries", 3);
    ks.addKey("ipc.client.kill.max", 3);
    ks.addKey("ipc.client.idlethreshold", 3);
    ks.addKey("group.name", 3);
    ks.addKey("hadoop.job.ugi", 3);
    ks.addKey("hadoop.native.lib", 2);
    ks.addKey("io.compression.codecs", 2);
    ks.addKey("local.cache.size", 3);
    ks.addKey("mapred.child.java.opts", 2);
    ks.addKey("mapred.compress.map.output", 2);
    ks.addKey("mapred.input.dir", 1);
    ks.addKey("mapred.input.format.class", 1);
    ks.addKey("mapred.job.level", 3);
    ks.addKey("mapred.job.name", 3);
    ks.addKey("mapred.job.skynet.actionid", 3);
    ks.addKey("mapred.job.skynet.bizdate", 3);
    ks.addKey("mapred.job.skynet_id", 3);
    ks.addKey("mapred.job.skynet.jobid", 2);
    ks.addKey("mapred.job.split.file", 2);
    ks.addKey("mapred.job.tracker", 2);
    ks.addKey("mapred.map.output.compression.codec", 2);
    ks.addKey("mapred.mapoutput.key.class", 2);
    ks.addKey("mapred.mapoutput.value.class", 2);
    ks.addKey("mapred.mapper.class", 1);
    ks.addKey("mapred.map.tasks", 2);
    ks.addKey("mapred.map.tasks.speculative.execution", 2);
    ks.addKey("mapred.output.committer.class", 1);
    ks.addKey("mapred.output.compress", 1);
    ks.addKey("mapred.output.compression.codec", 1);
    ks.addKey("mapred.output.compression.type", 1);
    ks.addKey("mapred.output.format.class", 1);
    ks.addKey("mapred.output.key.class", 1);
    ks.addKey("mapred.output.value.class", 1);
    ks.addKey("mapred.partitioner.class", 1);
    ks.addKey("mapred.reducer.class", 1);
    ks.addKey("mapred.reduce.tasks", 2);
    ks.addKey("mapred.reduce.tasks.speculative.execution", 2);
    ks.addKey("map.sort.class", 1);
    ks.addKey("skynet.priority.reduce.speculative.threshold", 3);

    String propsKeys = this.jobConf.get("mapred.client.hooks.autored.props", "");
    if (!"".equals(propsKeys)) {
      for (String props : propsKeys.split(",")) {
        String[] keys = props.split(":");
        if (keys.length == 1)
          ks.addKey(keys[0]);
        else {
          try {
            ks.addKey(keys[0], Integer.valueOf(keys[1]).intValue());
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
        }
      }
    }
    this.filter.addFilterStrainer(ks);

    if (this.jobId != null) {
      this.filter.addFilterStrainer(new StringStrainer(this.jobId));
    }

    this.filter.addFilterStrainer(new RegexStrainer(AutoConstant.DATATIME_PATTERN));
  }

  public static void main(String[] args)
  {
    JobConf conf = new JobConf();
    conf.setInt("mapred.map.tasks", 1059);
    conf.set("mapred.job.split.file", "hdfs://hdpnn:9000/home/hadoop/cluster-data/mapred/system/job_201104091858_61466/job.split");
    conf.set("mapred.cache.files.timestamps", "1302541914847,1302541915362");
    conf.set("mapred.input.dir", "/group/taobao/taobao/hive/s_auction_auctions_used/pt=20110411000000,/group/taobao/taobao/dw/stb/20110411/spu");

    JobConfFeature jcf = new JobConfFeature("job_201104091858_61466", conf);
    System.out.println(jcf.getFeature());
  }
}