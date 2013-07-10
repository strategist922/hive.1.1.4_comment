/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.jdbc;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * The interface that every driver class must implement.
 * <P>The Java SQL framework allows for multiple database drivers.
 *
 * <P>Each driver should supply a class that implements
 * the Driver interface.
 *
 * <P>The DriverManager will try to load as many drivers as it can
 * find and then for any given connection request, it will ask each
 * driver in turn to try to connect to the target URL.
 *
 * <P>It is strongly recommended that each Driver class should be
 * small and standalone so that the Driver class can be loaded and
 * queried without bringing in vast quantities of supporting code.
 *
 * <P>When a Driver class is loaded, it should create an instance of
 * itself and register it with the DriverManager. This means that a
 * user can load and register a driver by calling
 * <pre>
 *   <code>Class.forName("foo.bah.Driver")</code>
 * </pre>
 *
 * @see DriverManager
 * @see Connection 
 */
public class HiveDriver implements java.sql.Driver {
  static {
      try {
      java.sql.DriverManager.registerDriver(new org.apache.hadoop.hive.jdbc.HiveDriver());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  static final String DRIVER_NAME = "Hive JDBC Driver";
  
  /**
   * Major version number of this driver.
   */
  static final int MAJOR_VERSION = 0;

  /**
   * Minor version number of this driver.
   */
  static final int MINOR_VERSION = 0;

  static final String DRIVER_VERSION = Integer.toString(MAJOR_VERSION) + "."
      + Integer.toString(MINOR_VERSION);

  /**
   * Is this driver JDBC compliant?
   */
  static final boolean JDBC_COMPLIANT = false;

  /**
   * The required prefix for the connection url
   */
  static final String URL_PREFIX = "jdbc:hive://";

  /**
   * If host is provided, without a port
   */
  private static final String DEFAULT_PORT = "10000";

  /**
   * Property key for the database name
   */
  private static final String DBNAME_PROPERTY_KEY = "DBNAME";

  /**
   * Property key for the Hive Server host
   */
  private static final String HOST_PROPERTY_KEY = "HOST";

  /**
   * Property key for the Hive Server port
   */
  private static final String PORT_PROPERTY_KEY = "PORT";

  /**
   *
   */
  public HiveDriver() {
    SecurityManager security = System.getSecurityManager();
    if (security != null) {
      security.checkWrite("foobah");
    }
  }

  /**
   * Checks whether a given url is in a valid format.
   *
   * The current uri format is:
   * jdbc:hive://[host[:port]]
   *
   * jdbc:hive://                 - run in embedded mode
   * jdbc:hive://localhost        - connect to localhost default port (10000)
   * jdbc:hive://localhost:5050   - connect to localhost port 5050
   *
   * TODO: - write a better regex.
   *       - decide on uri format
   */
  public boolean acceptsURL(String url) throws SQLException {
    return url.trim().toLowerCase().startsWith(URL_PREFIX);
  }

  /**
   * Attempts to make a database connection to the given URL.
   * The driver should return "null" if it realizes it is the wrong kind
   * of driver to connect to the given URL.  This will be common, as when
   * the JDBC driver manager is asked to connect to a given URL it passes
   * the URL to each loaded driver in turn.
   *
   * <P>The driver should throw an <code>SQLException</code> if it is the right 
   * driver to connect to the given URL but has trouble connecting to
   * the database.
   *
   * <P>The <code>java.util.Properties</code> argument can be used to pass
   * arbitrary string tag/value pairs as connection arguments.
   * Normally at least "user" and "password" properties should be
   * included in the <code>Properties</code> object.
   *
   * @param url the URL of the database to which to connect
   * @param info a list of arbitrary string tag/value pairs as
   * connection arguments. Normally at least a "user" and
   * "password" property should be included.
   * @return a <code>Connection</code> object that represents a
   *         connection to the URL
   * @exception SQLException if a database access error occurs
   */
  public Connection connect(String url, Properties info) throws SQLException {
    return new HiveConnection(url, info);
  }

  /**
   * Returns the major version of this driver.
   */
  public int getMajorVersion() {
    return MAJOR_VERSION;
  }

  /**
   * Returns the minor version of this driver.
   */
  public int getMinorVersion() {
    return MINOR_VERSION;
  }

  /**
   * Gets information about the possible properties for this driver.
   * <P>
   * The <code>getPropertyInfo</code> method is intended to allow a generic 
   * GUI tool to discover what properties it should prompt 
   * a human for in order to get 
   * enough information to connect to a database.  Note that depending on
   * the values the human has supplied so far, additional values may become
   * necessary, so it may be necessary to iterate though several calls
   * to the <code>getPropertyInfo</code> method.
   *
   * @param url the URL of the database to which to connect
   * @param info a proposed list of tag/value pairs that will be sent on
   *          connect open
   * @return an array of <code>DriverPropertyInfo</code> objects describing 
   *          possible properties.  This array may be an empty array if 
   *          no properties are required.
   * @exception SQLException if a database access error occurs
   */
  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
      throws SQLException {
    if (info == null) {
      info = new Properties();
    }

    if ((url != null) && url.startsWith(URL_PREFIX)) {
      info = parseURL(url, info);
    }

    DriverPropertyInfo hostProp = new DriverPropertyInfo(HOST_PROPERTY_KEY,
                                        info.getProperty(HOST_PROPERTY_KEY, ""));
    hostProp.required = false;
    hostProp.description = "Hostname of Hive Server";

    DriverPropertyInfo portProp = new DriverPropertyInfo(PORT_PROPERTY_KEY,
                                        info.getProperty(PORT_PROPERTY_KEY, ""));
    portProp.required = false;
    portProp.description = "Port number of Hive Server";

    DriverPropertyInfo dbProp = new DriverPropertyInfo(DBNAME_PROPERTY_KEY,
                                      info.getProperty(DBNAME_PROPERTY_KEY, "default"));
    dbProp.required = false;
    dbProp.description = "Database name";

    DriverPropertyInfo[] dpi = new DriverPropertyInfo[3];

    dpi[0] = hostProp;
    dpi[1] = portProp;
    dpi[2] = dbProp;

    return dpi;
  }

  /**
   * Reports whether this driver is a genuine JDBC
   * Compliant<sup><font size=-2>TM</font></sup> driver.
   *
   * @return <code>false</code>
   */
  public boolean jdbcCompliant() {
    return JDBC_COMPLIANT;
  }

  /**
   * Takes a url in the form of jdbc:hive://[hostname]:[port]/[db_name] and parses it.
   * Everything after jdbc:hive// is optional.
   *
   * @param url
   * @param defaults
   * @return
   * @throws java.sql.SQLException
   */
  private Properties parseURL(String url, Properties defaults)
      throws java.sql.SQLException {
    Properties urlProps = (defaults != null) ? new Properties(defaults)
        : new Properties();

    if (url == null || !url.startsWith(URL_PREFIX)) {
      throw new SQLException("Invalid connection url: " + url);
    }

    if (url.length() <= URL_PREFIX.length()) return urlProps;

    // [hostname]:[port]/[db_name]
    String connectionInfo = url.substring(URL_PREFIX.length());

    // [hostname]:[port] [db_name]
    String[] hostPortAndDatabase = connectionInfo.split("/", 2);

    // [hostname]:[port]
    if (hostPortAndDatabase[0].length() > 0) {
      String[] hostAndPort = hostPortAndDatabase[0].split(":", 2);
      urlProps.put(HOST_PROPERTY_KEY, hostAndPort[0]);
      if (hostAndPort.length > 1) {
        urlProps.put(PORT_PROPERTY_KEY, hostAndPort[1]);
      }
      else {
        urlProps.put(PORT_PROPERTY_KEY, DEFAULT_PORT);
      }
    }

    // [db_name]
    if (hostPortAndDatabase.length > 1) {
      urlProps.put(DBNAME_PROPERTY_KEY, hostPortAndDatabase[1]);
    }

    return urlProps;
  }
}
