package org.apache.hadoop.mapred.fingerprint;

import java.io.PrintStream;
import java.util.List;
import org.apache.hadoop.mapred.PreJobHook.JobFileMD5;
import org.apache.hadoop.mapred.PreJobHook.JobFileType;

public class FileMd5Feature
  implements Feature
{
  private PreJobHook.JobFileType type;
  private StringBuilder sb = new StringBuilder();
  private int count = 0;

  public FileMd5Feature(PreJobHook.JobFileType type, List<PreJobHook.JobFileMD5> md5s) { this.type = type;
    for (PreJobHook.JobFileMD5 j : md5s)
      if (j.getType() == this.type) {
        System.err.println("PreJobAutoReduce: " + type.name() + " md5 = " + j.getMd5());
        this.sb.append(j.getMd5());
        this.count += 1;
      }
  }

  public String getFeature()
  {
    switch (this.count) {
    case 0:
      return "";
    case 1:
      return this.sb.toString();
    }
    return Md5Util.md5sum(this.sb.toString().getBytes());
  }
}