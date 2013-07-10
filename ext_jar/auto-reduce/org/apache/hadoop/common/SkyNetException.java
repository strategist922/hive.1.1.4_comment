package org.apache.hadoop.common;

public class SkyNetException extends Exception
{
  private static final long serialVersionUID = 1L;
  private String msg;

  public SkyNetException(String msg)
  {
    super(msg);
    this.msg = msg;
  }

  public String getExceptionMessage() {
    return this.msg;
  }
}