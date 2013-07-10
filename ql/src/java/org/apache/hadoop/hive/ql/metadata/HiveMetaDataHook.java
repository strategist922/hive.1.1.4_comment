package org.apache.hadoop.hive.ql.metadata;

import java.util.Map;

public interface HiveMetaDataHook {

  public String run(Hive hive, Table table, Partition partition, Map<String, String> partSpec,
      boolean replace, String pathLocation) throws Exception;

}
