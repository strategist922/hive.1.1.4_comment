package com.taobao.data.hive.hook;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.Constants;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.session.SessionState;

public class TaskModel implements Comparable<TaskModel> {
  private String MD5;
  private List<TaskModel> parents;
  private List<String> paths;
  private long startTimeStamp;
  private long endTimeStamp;
  private List<String> inputPaths;
  private List<String> inputTables;

  public TaskModel() {
    super();
  }

  public String getMD5() {
    return MD5;
  }

  public void setMD5(String MD5) {
    this.MD5 = MD5;
  }

  public List<String> getPaths() {
    return paths;
  }

  public void setPaths(List<String> paths) {
    this.paths = paths;
  }

  public List<TaskModel> getParents() {
    return parents;
  }

  public void setParents(List<TaskModel> parents) {
    this.parents = parents;
  }

  public long getStartTimeStamp() {
    return startTimeStamp;
  }

  public void setStartTimeStamp(long startTimeStamp) {
    this.startTimeStamp = startTimeStamp;
  }

  public long getEndTimeStamp() {
    return endTimeStamp;
  }

  public void setEndTimeStamp(long endTimeStamp) {
    this.endTimeStamp = endTimeStamp;
  }

  public List<String> getInputPaths() {
    return inputPaths;
  }

  public void setInputPaths(List<String> inputPaths) {
    this.inputPaths = inputPaths;
  }

  public List<String> getInputTables() {
    return inputTables;
  }

  public void setInputTables(List<String> inputTables) {
    this.inputTables = inputTables;
  }

  @Override
  public int compareTo(TaskModel other) {
    int rt = 0;
    rt = this.getMD5().compareTo(other.getMD5());
    if (rt != 0) {
      return rt;
    }
    if (parents == null || parents.isEmpty()) {
      return 0;
    }
    for (TaskModel parent : parents) {
      boolean matched = false;
      for (TaskModel otherParent : other.getParents()) {
        if (parent.getMD5().equals(otherParent.getMD5())) {
          matched = true;
          rt = parent.compareTo(otherParent);
          if (rt != 0) {
            return rt;
          }
        }
      }
      if (!matched) {
        return 1;
      }
    }
    return 0;
  }

  /**
   * Check wether task model is valid
   * A task model is valid all following requirements matched:
   * * Cache path exists
   * * Cache data is not modified since generated
   * * All source data read by the cache is not modified since the last time
   * cache is generated
   * * All input tables' metainfo is not modified.
   *
   * @param conf
   * @return true if valid
   */
  public boolean isValid(HiveConf conf) {
    try {
      if (paths != null && !paths.isEmpty()) {
        for (String path : paths) {
          Path p = new Path(path);
          FileSystem fs = p.getFileSystem(conf);
          if (!fs.exists(p)) {
            SessionState.getConsole().printError("Path " + path + " is not exists, cache is not valid any more");
            return false;
          }
          FileStatus status = fs.getFileStatus(p);
          if (status.getModificationTime() > endTimeStamp) {
            SessionState.getConsole().printError("Data under " + path
                + " is modified after last generate, cache is not valid any more");
            return false;
          }
        }
      }

      if (inputPaths != null && !inputPaths.isEmpty()) {
        for (String path : inputPaths) {
          Path p = new Path(path);
          FileSystem fs = p.getFileSystem(conf);
          FileStatus status = fs.getFileStatus(p);
          if (status.getModificationTime() > startTimeStamp) {
            SessionState.getConsole().printError("Data under " + path
                + " is modified after last generate, cache is not valid any more");
            return false;
          }
        }
      }

      if (inputTables != null && !inputTables.isEmpty()) {
        Hive db = Hive.get(conf);
        for (String tableName : inputTables) {
          Table table = db.getTable(tableName);
          long lastDDLTime = Long.parseLong(table.getProperty(Constants.DDL_TIME));
          if (lastDDLTime > startTimeStamp) {
            SessionState.getConsole().printError("Meta data of " + tableName
                + " is modified after last generate, cache is not valid any more");
            return false;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } catch (HiveException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static void toXml(OutputStream out, TaskModel tm) {
    XMLEncoder encoder = new XMLEncoder(out);
    encoder.writeObject(tm);
    encoder.close();
  }

  public static TaskModel fromXml(InputStream in) {
    XMLDecoder decoder = new XMLDecoder(in);
    TaskModel tm = null;
    try {
      tm = (TaskModel) decoder.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    }
    decoder.close();
    return tm;
  }
}
