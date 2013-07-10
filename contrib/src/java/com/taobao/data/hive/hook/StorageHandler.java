package com.taobao.data.hive.hook;

public interface StorageHandler<T> {
  public T get(T model);

  public boolean put(T model);

  public boolean remove(T model);

  void close();
}
