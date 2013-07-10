package com.taobao.data.hive.hook;

import org.apache.hadoop.conf.Configuration;

public class StorageHandlerFactory {
  public static StorageHandler<TaskModel> getFileStorageHandler(String rootPath) {
    return new FileStorageHandler(rootPath);
  }

  public static StorageHandler<TaskModel> getRDBMSStorageHandler(Configuration conf) {
    return new RDBMSStorageHandler(conf);
  }
}
