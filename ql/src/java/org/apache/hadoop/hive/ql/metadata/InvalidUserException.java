package org.apache.hadoop.hive.ql.metadata;

public class InvalidUserException extends HiveException {
  String userName;

  public InvalidUserException(String userName) {
    super();
    this.userName = userName;
  }
  
  public InvalidUserException(String message, String userName) {
    super(message);
    this.userName = userName;
  }

  public InvalidUserException(Throwable cause, String userName) {
    super(cause);
    this.userName = userName;
  }

  public InvalidUserException(String message, Throwable cause, String userName) {
    super(message, cause);
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }
}
