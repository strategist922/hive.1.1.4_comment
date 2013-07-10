package com.taobao.dw.hive.hook.exstore.common;

public class ExStoreException extends RuntimeException
{
  public static final int HIVE_CONTEXT_IS_NULL_STATECODE = -1;
  public static final int TABLE_IS_NOT_FOUND_IN_HIVE = -2;
  public static final int MYSQL_ERROR = -3;
  public static final int HIVE_INTERNAL_EXCEPTION = 0;
  public static final int INTERVAL_PARSE_ERROR = 1;
  public static final int COLUMN_TYPE_NOT_FOUND = 2;
  public static final int PT_EXCEPTION = 3;
  public static final String HIVE_CONTEXT_IS_NULL_MSG = "Hive context is null.";
  private int stateCode;
  private static final long serialVersionUID = -3317849913996968049L;

  public ExStoreException()
  {
  }

  public ExStoreException(String exmsg)
  {
    super(exmsg);
  }

  public ExStoreException(String exmsg, int stateCode) {
    super(exmsg);
    this.stateCode = stateCode;
  }

  public ExStoreException(Throwable e, int stateCode) {
    super(e);
    this.stateCode = stateCode;
  }

  public int getStateCode() {
    return this.stateCode;
  }

  public void setStateCode(int stateCode) {
    this.stateCode = stateCode;
  }
}