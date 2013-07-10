package org.apache.hadoop.common;

public class SkynetException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  private String msg;

  public SkynetException(String msg)
  {
    super(msg);
    this.msg = msg;
  }

  public String getExceptionMessage() {
    return this.msg;
  }
}