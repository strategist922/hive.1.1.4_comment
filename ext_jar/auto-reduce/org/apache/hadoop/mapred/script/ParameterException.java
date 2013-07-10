package org.apache.hadoop.mapred.script;

public class ParameterException extends RuntimeException
{
  private static final long serialVersionUID = 4601025553790744862L;
  private String msg;

  public ParameterException(String msg)
  {
    super(msg);
    this.msg = msg;
  }

  public String getExceptionMessage() {
    return this.msg;
  }
}