package org.apache.hadoop.mapred.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector
{
  private String dbUrl;
  private String dbUser;
  private String dbPassword;

  public DBConnector(String dbDriver, String dbUrl, String dbUser, String dbPassword)
    throws Exception
  {
    this.dbUrl = dbUrl;
    this.dbUser = dbUser;
    this.dbPassword = dbPassword;
    Class.forName(dbDriver);
  }

  public Connection getConnection() throws Exception {
    return DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
  }
}