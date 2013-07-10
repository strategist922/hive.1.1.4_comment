package com.taobao.data.hive.hook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileStorageHandler implements StorageHandler<TaskModel> {

  private final String rootPath;
  private final Log LOG = LogFactory.getLog(FileStorageHandler.class);

  public FileStorageHandler(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public TaskModel get(TaskModel model) {
    File file = new File(rootPath, model.getMD5());
    File[] modelFiles = file.listFiles();
    if (modelFiles == null || modelFiles.length == 0) {
      return null;
    }
    try {
      for (File similar : modelFiles) {
        TaskModel similarModel = TaskModel.fromXml(new BufferedInputStream(
            new FileInputStream(similar)));
        if (similarModel.compareTo(model) == 0) {
          return similarModel;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
    return null;
  }

  @Override
  public boolean put(TaskModel model) {
    File parent = new File(rootPath, model.getMD5());
    File file = new File(parent, UUID.randomUUID().toString() + ".xml");
    parent.mkdirs();
    try {
      TaskModel.toXml(new BufferedOutputStream(new FileOutputStream(file)), model);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    }
    LOG.info("Plan saved to " + file.getAbsolutePath());
    return true;
  }

  @Override
  public boolean remove(TaskModel model) {
    File file = new File(rootPath, model.getMD5());
    File[] modelFiles = file.listFiles();
    if (modelFiles == null || modelFiles.length == 0) {
      return true;
    }
    try {
      for (File similar : modelFiles) {
        TaskModel similarModel = TaskModel.fromXml(new BufferedInputStream(
              new FileInputStream(similar)));
        if (similarModel.compareTo(model) == 0) {
          return similar.delete();
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return true;
    }
    return true;
  }

  @Override
  public void close() {

  }
}
