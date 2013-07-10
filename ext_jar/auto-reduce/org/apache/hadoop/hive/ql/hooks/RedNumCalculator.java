package org.apache.hadoop.hive.ql.hooks;

public abstract interface RedNumCalculator
{
  public abstract int getReduceNum();
}