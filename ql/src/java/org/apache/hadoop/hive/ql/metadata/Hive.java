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

package org.apache.hadoop.hive.ql.metadata;

import static org.apache.hadoop.hive.metastore.MetaStoreUtils.DEFAULT_DATABASE_NAME;
import static org.apache.hadoop.hive.metastore.api.Constants.META_TABLE_STORAGE;
import static org.apache.hadoop.hive.serde.Constants.SERIALIZATION_FORMAT;
import static org.apache.hadoop.hive.serde.Constants.STRING_TYPE_NAME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.common.JavaUtils;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaException;
import org.apache.hadoop.hive.metastore.HiveMetaHook;
import org.apache.hadoop.hive.metastore.HiveMetaHookLoader;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.MetaStoreUtils;
import org.apache.hadoop.hive.metastore.TableType;
import org.apache.hadoop.hive.metastore.Warehouse;
import org.apache.hadoop.hive.metastore.api.AlreadyExistsException;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.InvalidObjectException;
import org.apache.hadoop.hive.metastore.api.InvalidOperationException;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.NoSuchObjectException;
import org.apache.hadoop.hive.ql.authorization.Privilege;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.serde2.Deserializer;
import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.util.StringUtils;
import org.apache.thrift.TException;

/**
 * The Hive class contains information about this instance of Hive. An instance
 * of Hive represents a set of data in a file system (usually HDFS) organized
 * for easy query processing
 *
 */

public class Hive {

  static final private Log LOG = LogFactory.getLog("hive.ql.metadata.Hive");

  private HiveConf conf = null;
  private IMetaStoreClient metaStoreClient;
  private String currentDatabase;

  private static ThreadLocal<Hive> hiveDB = new ThreadLocal() {
    @Override
    protected synchronized Object initialValue() {
      return null;
    }

    @Override
    public synchronized void remove() {
      if (this.get() != null) {
        ((Hive) this.get()).close();
      }
      super.remove();
    }
  };

  /**
   * Gets hive object for the current thread. If one is not initialized then a
   * new one is created If the new configuration is different in metadata conf
   * vars then a new one is created.
   *
   * @param c
   *          new Hive Configuration
   * @return Hive object for current thread
   * @throws HiveException
   *
   */
  public static Hive get(HiveConf c) throws HiveException {
    boolean needsRefresh = false;
    Hive db = hiveDB.get();
    if (db != null) {
      for (HiveConf.ConfVars oneVar : HiveConf.metaVars) {
        // Since metaVars are all of different types, use string for comparison
        String oldVar = db.getConf().get(oneVar.varname, "");
        String newVar = c.get(oneVar.varname, "");
        if (oldVar.compareToIgnoreCase(newVar) != 0) {
          needsRefresh = true;
          break;
        }
      }
    }
    return get(c, needsRefresh);
  }

  /**
   * get a connection to metastore. see get(HiveConf) function for comments
   *
   * @param c
   *          new conf
   * @param needsRefresh
   *          if true then creates a new one
   * @return The connection to the metastore
   * @throws HiveException
   */
  public static Hive get(HiveConf c, boolean needsRefresh) throws HiveException {
    Hive db = hiveDB.get();
    if (db == null || needsRefresh) {
      closeCurrent();
      c.set("fs.scheme.class", "dfs");
      db = new Hive(c);
      hiveDB.set(db);
    }
    return db;
  }

  public static Hive get() throws HiveException {
    Hive db = hiveDB.get();
    if (db == null) {
      db = new Hive(new HiveConf(Hive.class));
      hiveDB.set(db);
    }
    return db;
  }

  public static void closeCurrent() {
    hiveDB.remove();
  }

  /**
   * Hive
   *
   * @param argFsRoot
   * @param c
   *
   */
  private Hive(HiveConf c) throws HiveException {
    conf = c;
  }

  /**
   * closes the connection to metastore for the calling thread
   */
  private void close() {
    LOG.info("Closing current thread's connection to Hive Metastore.");
    if (metaStoreClient != null) {
      metaStoreClient.close();
    }
  }

  /**
   * Create a database
   * @param db
   * @param ifNotExist if true, will ignore AlreadyExistsException exception
   * @throws AlreadyExistsException
   * @throws HiveException
   */
  public void createDatabase(Database db, boolean ifNotExist)
      throws AlreadyExistsException, HiveException {
    try {
      getMSC().createDatabase(db);
    } catch (AlreadyExistsException e) {
      if (!ifNotExist) {
        throw e;
      }
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Create a Database. Raise an error if a database with the same name already exists.
   * @param db
   * @throws AlreadyExistsException
   * @throws HiveException
   */
  public void createDatabase(Database db) throws AlreadyExistsException, HiveException {
    createDatabase(db, false);
  }

  /**
   * Drop a database.
   * @param name
   * @throws NoSuchObjectException
   * @throws HiveException
   * @see org.apache.hadoop.hive.metastore.HiveMetaStoreClient#dropDatabase(java.lang.String)
   */
  public void dropDatabase(String name) throws HiveException, NoSuchObjectException {
    dropDatabase(name, true, false);
  }


  /**
   * Drop a database
   * @param name
   * @param deleteData
   * @param ignoreUnknownDb if true, will ignore NoSuchObjectException
   * @return
   * @throws HiveException
   * @throws NoSuchObjectException
   */
  public void dropDatabase(String name, boolean deleteData, boolean ignoreUnknownDb)
      throws HiveException, NoSuchObjectException {
    try {
      getMSC().dropDatabase(name, deleteData, ignoreUnknownDb);
    } catch (NoSuchObjectException e) {
      throw e;
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Creates a table metdata and the directory for the table data
   *
   * @param tableName
   *          name of the table
   * @param columns
   *          list of fields of the table
   * @param partCols
   *          partition keys of the table
   * @param fileInputFormat
   *          Class of the input format of the table data file
   * @param fileOutputFormat
   *          Class of the output format of the table data file
   * @throws HiveException
   *           thrown if the args are invalid or if the metadata or the data
   *           directory couldn't be created
   */
  public void createTable(String tableName, List<String> columns,
      List<String> partCols, Class<? extends InputFormat> fileInputFormat,
      Class<?> fileOutputFormat) throws HiveException {
    this.createTable(tableName, columns, partCols, fileInputFormat,
        fileOutputFormat, -1, null);
  }

  /**
   * Creates a table metdata and the directory for the table data
   *
   * @param tableName
   *          name of the table
   * @param columns
   *          list of fields of the table
   * @param partCols
   *          partition keys of the table
   * @param fileInputFormat
   *          Class of the input format of the table data file
   * @param fileOutputFormat
   *          Class of the output format of the table data file
   * @param bucketCount
   *          number of buckets that each partition (or the table itself) should
   *          be divided into
   * @throws HiveException
   *           thrown if the args are invalid or if the metadata or the data
   *           directory couldn't be created
   */
  public void createTable(String tableName, Collection<String> columns,
      Collection<String> partCols, Class<? extends InputFormat> fileInputFormat,
      Class<?> fileOutputFormat, int bucketCount, List<String> bucketCols)
      throws HiveException {
    if (columns == null) {
      throw new HiveException("columns not specified for table " + tableName);
    }

    Table tbl = new Table(getCurrentDatabase(), tableName);
    tbl.setInputFormatClass(fileInputFormat.getName());
    tbl.setOutputFormatClass(fileOutputFormat.getName());

    for (String col : columns) {
      FieldSchema field = new FieldSchema(col, STRING_TYPE_NAME, "default");
      tbl.getCols().add(field);
    }

    if (partCols != null) {
      for (String partCol : partCols) {
        FieldSchema part = new FieldSchema();
        part.setName(partCol);
        part.setType(STRING_TYPE_NAME); // default
                                                                               // partition
                                                                               // key
        tbl.getPartCols().add(part);
      }
    }
    tbl.setSerializationLib(LazySimpleSerDe.class.getName());
    tbl.setNumBuckets(bucketCount);
    tbl.setBucketCols(bucketCols);
    createTable(tbl);
  }

  /**
   * Updates the existing table metadata with the new metadata.
   *
   * @param tblName
   *          name of the existing table
   * @param newTbl
   *          new name of the table. could be the old name
   * @throws InvalidOperationException
   *           if the changes in metadata is not acceptable
   * @throws TException
   */
  public void alterTable(String tblName, Table newTbl)
      throws InvalidOperationException, HiveException {
    try {
      getMSC().alter_table(getCurrentDatabase(), tblName,
          newTbl.getTTable());
    } catch (MetaException e) {
      throw new HiveException("Unable to alter table.", e);
    } catch (TException e) {
      throw new HiveException("Unable to alter table.", e);
    }
  }

  /**
   * Updates the existing table metadata with the new metadata.
   *
   * @param tblName
   *          name of the existing table
   * @param newTbl
   *          new name of the table. could be the old name
   * @throws InvalidOperationException
   *           if the changes in metadata is not acceptable
   * @throws TException
   */
  public void alterPartition(String tblName, Partition newPart)
      throws InvalidOperationException, HiveException {
    try {
      getMSC().alter_partition(getCurrentDatabase(), tblName,
          newPart.getTPartition());

    } catch (MetaException e) {
      throw new HiveException("Unable to alter partition.", e);
    } catch (TException e) {
      throw new HiveException("Unable to alter partition.", e);
    }
  }

  /**
   * Creates the table with the give objects
   *
   * @param tbl
   *          a table object
   * @throws HiveException
   */
  public void createTable(Table tbl) throws HiveException {
    createTable(tbl, false);
  }

  /**
   * Creates the table with the give objects
   *
   * @param tbl
   *          a table object
   * @param ifNotExists
   *          if true, ignore AlreadyExistsException
   * @throws HiveException
   */
  public void createTable(Table tbl, boolean ifNotExists) throws HiveException {
    try {
      if (tbl.getDbName() == null || "".equals(tbl.getDbName().trim())) {
        tbl.setDbName(getCurrentDatabase());
      }
      if (tbl.getCols().size() == 0) {
        tbl.setFields(MetaStoreUtils.getFieldsFromDeserializer(tbl.getTableName(),
            tbl.getDeserializer()));
      }
      tbl.checkValidity();
      getMSC().createTable(tbl.getTTable());
    } catch (AlreadyExistsException e) {
      if (!ifNotExists) {
        throw new HiveException(e);
      }
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Drops table along with the data in it. If the table doesn't exist then it
   * is a no-op
   *
   * @param dbName
   *          database where the table lives
   * @param tableName
   *          table to drop
   * @throws HiveException
   *           thrown if the drop fails
   */
  public void dropTable(String tableName) throws HiveException {
    dropTable(getCurrentDatabase(), tableName, true, true);
  }

  /**
   * Drops table along with the data in it. If the table doesn't exist
   * then it is a no-op
   * @param dbName database where the table lives
   * @param tableName table to drop
   * @throws HiveException thrown if the drop fails
   * Drops table along with the data in it. If the table doesn't exist then it
   * is a no-op
   *
   * @param dbName
   *          database where the table lives
   * @param tableName
   *          table to drop
   * @throws HiveException
   *           thrown if the drop fails
   */
  public void dropTable(String dbName, String tableName) throws HiveException {
    dropTable(dbName, tableName, true, true);
  }

  /**
   * Drops the table.
   *
   * @param tableName
   * @param deleteData
   *          deletes the underlying data along with metadata
   * @param ignoreUnknownTab
   *          an exception if thrown if this is falser and table doesn't exist
   * @throws HiveException
   */
  public void dropTable(String dbName, String tableName, boolean deleteData,
      boolean ignoreUnknownTab) throws HiveException {

    try {
      getMSC().dropTable(dbName, tableName, deleteData, ignoreUnknownTab);
    } catch (NoSuchObjectException e) {
      if (!ignoreUnknownTab) {
        throw new HiveException(e);
      }
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  public HiveConf getConf() {
    return (conf);
  }

  /**
   * Create a user with a give user name.
   * @param userName
   * @throws HiveException
   */
  public void createUser(String userName, byte[] password) throws HiveException {
    User user = new User(userName, password);
    createUser(user);
  }

  /**
   * Create a user with a give user object.
   * @param user
   * @throws HiveException
   */
  public void createUser(User user) throws HiveException {
    try {
      getMSC().createUser(user.getTUser());
    } catch (AlreadyExistsException e) {
      throw new HiveException(e);
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Return a user with the user name.
   * @param userName
   * @return User
   * @throws HiveException if there's an internal error or if the user doesn't exist
   */
  public User getUser(final String userName) throws HiveException {

    return getUser(userName, false);
  }

  /**
   * Return a user by user name
   * @param userName
   * @param throwException
   * @return user
   * @throws HiveException if there's an internal error or if the user doesn't exist
   */
  public User getUser(final String userName, boolean throwException) throws HiveException {

    if(userName == null || userName.equals("")) {
      throw new HiveException("empty user creation??");
    }
    User user = new User();
    org.apache.hadoop.hive.metastore.api.User tUser = null;
    try {
      tUser = getMSC().getUser(userName);
      if(tUser == null) {
        return null;
      }
    } catch (NoSuchObjectException e) {
      if(throwException) {
        LOG.error(StringUtils.stringifyException(e));
        throw new InvalidUserException("User not found ", userName);
      } else {
        return null;
      }
    } catch (Exception e) {
      throw new HiveException("Unable to fetch user " + userName, e);
    }
    user.setTUser(tUser);
    return user;
  }

  public boolean checkGlobalPriv(final String userName, Collection<Privilege> privs)
  throws HiveException {
    User user = getUser(userName);
    return user.hasPriv(privs);
  }

  /**
   * Change user's password.
   * @param userName
   * @param newPassword
   * @throws InvalidOperationException
   * @throws HiveException
   */
  public void alterPassword(String userName, byte[] newPassword) throws InvalidOperationException, HiveException {
    User user = this.getUser(userName);
    if(user == null) {
      throw new HiveException("The user " + userName + " does't exist.");
    }
    user.setPassword(newPassword);
    try {
      getMSC().alterUser(userName, user.getTUser());
    } catch (MetaException e) {
      throw new HiveException("Unable to alter user " + userName, e);
    } catch (TException e) {
      throw new HiveException("Unable to alter user " + userName, e);
    } catch (NoSuchObjectException e) {
      throw new InvalidUserException("User " + userName + "not exists");
    }
  }

  /**
   * Drop the user with giving user name.
   * @param userName
   * @throws HiveException
   */
  public void dropUser(String userName) throws HiveException {
    dropUser(userName, true);
  }

  /**
   * Drop the user with giving user name.
   * @param userName
   * @param ignoreUnknownTab
   * @throws HiveException
   */
  public void dropUser(String userName, boolean ignoreUnknownTab) throws HiveException {

    try {
      getMSC().dropUser(userName);
    } catch (NoSuchObjectException e) {
      if (!ignoreUnknownTab) {
        throw new HiveException(e);
      }
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Grant user with database privilege.
   * @param dbPriv
   * @return true if succeed
   * @throws HiveException
   */
  public boolean grantDbPriv(DbPriv dbPriv) throws HiveException {
    try {
      return getMSC().grantDbPriv(dbPriv.getTDbPriv());
    } catch (AlreadyExistsException e) {
      throw new HiveException(e);
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Get user's database privilege.
   * @param userName
   * @param dbName
   * @param throwException
   * @return user's database privilege
   * @throws HiveException
   */
  public DbPriv getDbPriv(final String userName, final String dbName) throws HiveException {

    if(userName == null || userName.equals("")) {
      throw new HiveException("You must specify a user name");
    }
    DbPriv dbPriv = new DbPriv();
    org.apache.hadoop.hive.metastore.api.DbPriv tDbPriv = null;
    try {
      tDbPriv = getMSC().getDbPriv(userName, dbName);
    } catch (NoSuchObjectException e) {
      return null;
    } catch (Exception e) {
      throw new HiveException("Unable to fetch database privilege ", e);
    }
    dbPriv.setDbPriv(tDbPriv);
    return dbPriv;
  }

  /**
   * Get database privilege.
   * @param dbName
   * @param throwException
   * @return user's database privilege
   * @throws HiveException
   */
  public List<DbPriv> getDbPrivs(final String dbName) throws HiveException {
    List<DbPriv> dbPrivs = new ArrayList<DbPriv>();
    List<org.apache.hadoop.hive.metastore.api.DbPriv> tDbPrivs = null;
    try {
      tDbPrivs = getMSC().getDbPrivs(dbName);
    } catch (NoSuchObjectException e) {
      return null;
    } catch (Exception e) {
      throw new HiveException("Unable to fetch database privilege ", e);
    }
    for (org.apache.hadoop.hive.metastore.api.DbPriv tDbPriv : tDbPrivs) {
      DbPriv dbPriv = new DbPriv();
      dbPriv.setDbPriv(tDbPriv);
      dbPrivs.add(dbPriv);
    }
    return dbPrivs;
  }

  public boolean checkDbPriv(final String userName, final String dbName,
      Collection<Privilege> privs) throws HiveException {
    if(userName == null || userName.equals("")) {
      throw new HiveException("You must specify a user name");
    }
    DbPriv dbPriv = new DbPriv();
    org.apache.hadoop.hive.metastore.api.DbPriv tDbPriv = null;
    try {
      tDbPriv = getMSC().getDbPriv(userName, dbName);
    } catch (NoSuchObjectException e) {
      return false;
    } catch (Exception e) {
      throw new HiveException("Unable to fetch database privilege ", e);
    }
    dbPriv.setDbPriv(tDbPriv);
    return dbPriv.hasPriv(privs);
  }

  /**
   * Grant user with table privilege.
   * @param tbPriv
   * @return true if succeed
   * @throws HiveException
   */
  public boolean grantTbPriv(TbPriv tbPriv) throws HiveException {
    try {
      if (tbPriv.getGrantor() == null) {
        throw new HiveException("not specify grantor");
      }
      return getMSC().grantTbPriv(tbPriv.getTTbPriv());
    } catch (AlreadyExistsException e) {
      throw new HiveException(e);
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Get user's table privilege.
   * @param userName
   * @param dbName
   * @param tbName
   * @param throwException
   * @return user's table privilege
   * @throws HiveException
   */
  public TbPriv getTbPriv(final String userName, final String dbName, final String tbName)
      throws HiveException {

    TbPriv tbPriv = new TbPriv();
    org.apache.hadoop.hive.metastore.api.TbPriv tTbPriv = null;
    try {
      tTbPriv = getMSC().getTbPriv(userName, dbName, tbName);
    } catch (NoSuchObjectException e) {
      return null;
    } catch (Exception e) {
      throw new HiveException(e);
    }
    tbPriv.setTbPriv(tTbPriv);
    return tbPriv;
  }


  public List<TbPriv> getTbPrivs(final String dbName, final String tbName)
      throws HiveException {

    List<TbPriv> tbPrivs = new ArrayList<TbPriv>();
    List<org.apache.hadoop.hive.metastore.api.TbPriv> tTbPrivs = null;
    try {
      tTbPrivs = getMSC().getTbPrivs(dbName, tbName);
    } catch (NoSuchObjectException e) {
      return null;
    } catch (Exception e) {
      throw new HiveException(e);
    }
    for (org.apache.hadoop.hive.metastore.api.TbPriv tTbPriv : tTbPrivs) {
      TbPriv tbPriv = new TbPriv();
      tbPriv.setTbPriv(tTbPriv);
      tbPrivs.add(tbPriv);
    }
    return tbPrivs;
  }

  public boolean checkTbPriv(final String userName, final String dbName,
      final String tbName, Collection<Privilege> privs) throws HiveException {
    TbPriv tbPriv = new TbPriv();
    org.apache.hadoop.hive.metastore.api.TbPriv tTbPriv = null;
    try {
      tTbPriv = getMSC().getTbPriv(userName, dbName, tbName);
    } catch (NoSuchObjectException e) {
      return false;
    } catch (Exception e) {
      throw new HiveException(e);
    }
    tbPriv.setTbPriv(tTbPriv);
    return tbPriv.hasPriv(privs);
  }

  /**
   * Add global privilege to user.
   * @param username
   * @param privs
   * @throws HiveException
   * @throws InvalidOperationException
   */
  public void addGlobalPriv(String username, Collection<Privilege> privs) throws HiveException, InvalidOperationException {
    User usr = this.getUser(username);
    if(usr == null) {
      throw new HiveException("The user: " + username + " doesn't exist");
    }
    for(Privilege priv : privs) {
      switch(priv) {
        case ALTER_PRIV :
          usr.setAlter_priv(true);
          break;
        case CREATE_PRIV :
          usr.setCreate_priv(true);
          break;
        case CREATE_USER_PRIV :
          usr.setCreate_user_priv(true);
          break;
        case DROP_PRIV :
          usr.setDrop_priv(true);
          break;
        case GRANT_PRIV :
          usr.setGrant_priv(true);
          break;
        case INSERT_PRIV :
          usr.setInsert_priv(true);
          break;
        case SELECT_PRIV :
          usr.setSelect_priv(true);
          break;
        case SUPER_PRIV :
          usr.setSuper_priv(true);
          break;
        default:
          throw new HiveException("Globe privilege don't support " + priv);
      }
    }
    try {
      getMSC().alterUser(username, usr.getTUser());
    } catch (MetaException e) {
      throw new HiveException("Unable to alter user.", e);
    } catch (TException e) {
      throw new HiveException("Unable to alter user.", e);
    } catch (NoSuchObjectException e) {
      throw new HiveException("User not exists.", e);
    }
  }

  /**
   * Add database privilege to user.
   * @param username
   * @param dbname
   * @param privs
   * @throws HiveException
   * @throws InvalidOperationException
   */
  public void addDatabasePriv(String username, String dbname, Collection<Privilege> privs) throws HiveException, InvalidOperationException {
    List<DbPriv> dbPrivs = null;
    if (username != null) {
      dbPrivs = new ArrayList<DbPriv>();
      dbPrivs.add(getDbPriv(username, dbname));
    } else {
      dbPrivs = getDbPrivs(dbname);
    }

    for (DbPriv dbpriv : dbPrivs) {
      if(dbpriv == null) {
        this.grantDbPriv(new DbPriv(dbname, username));
      }
      dbpriv = this.getDbPriv(username, dbname);

      for(Privilege priv : privs) {
        switch(priv) {
          case ALTER_PRIV :
            dbpriv.setAlter_priv(true);
            break;
          case DROP_PRIV :
            dbpriv.setDrop_priv(true);
            break;
          case GRANT_PRIV :
            dbpriv.setGrant_priv(true);
            break;
          case INSERT_PRIV :
            dbpriv.setInsert_priv(true);
            break;
          case SELECT_PRIV :
            dbpriv.setSelect_priv(true);
            break;
          case CREATE_PRIV :
            dbpriv.setCreate_priv(true);
            break;
          default:
            throw new HiveException("Database privilege don't support " + priv);
        }
      }

      try {
        getMSC().alterDbPriv(dbpriv.getUserName(), dbname, dbpriv.getTDbPriv());
      } catch (InvalidObjectException e) {
        throw new HiveException(e.getMessage());
      } catch (NoSuchObjectException e) {
        throw new HiveException(e.getMessage());
      } catch (TException e) {
        throw new HiveException(e.getMessage());
      } catch (MetaException e) {
        throw new HiveException(e.getMessage());
      }
    }
  }

  /**
   * Add table privilege to user.
   * @param username
   * @param dbname
   * @param tbname
   * @param grantor current user
   * @param privs
   * @throws HiveException
   * @throws InvalidOperationException
   */
  public void addTablePriv(String username, String dbname, String tbname,
      String grantor, Collection<Privilege> privs)
      throws HiveException, InvalidOperationException {
    List<TbPriv> tbPrivs = null;
    if (username != null) {
      tbPrivs = new ArrayList<TbPriv>();
      tbPrivs.add(getTbPriv(username, dbname, tbname));
    } else {
      tbPrivs = getTbPrivs(dbname, tbname);
    }

    for (TbPriv tbpriv : tbPrivs) {
      if (tbpriv == null) {
        this.grantTbPriv(new TbPriv(dbname, username, grantor, tbname));
      }
      tbpriv = this.getTbPriv(username, dbname, tbname);
      for(Privilege priv : privs) {
        switch(priv) {
          case ALTER_PRIV :
            tbpriv.setAlter_priv(true);
            break;
          case DROP_PRIV :
            tbpriv.setDrop_priv(true);
            break;
          case GRANT_PRIV :
            tbpriv.setGrant_priv(true);
            break;
          case INSERT_PRIV :
            tbpriv.setInsert_priv(true);
            break;
          case SELECT_PRIV :
            tbpriv.setSelect_priv(true);
            break;
          default:
            throw new HiveException("Table privilege don't support " + priv);
        }
      }
      tbpriv.setTimeStamp( (int) (System.currentTimeMillis() / 1000) );
      try {
        getMSC().alterTbPriv(tbpriv.getUserName(), dbname, tbname, tbpriv.getTTbPriv());
      } catch (InvalidObjectException e) {
        throw new HiveException(e.getMessage());
      } catch (NoSuchObjectException e) {
        throw new HiveException(e.getMessage());
      } catch (TException e) {
        throw new HiveException(e.getMessage());
      } catch (MetaException e) {
        throw new HiveException(e.getMessage());
      }
    }
  }

  /**
   * Revoke privilege from user.
   * @param username
   * @param privs
   * @throws HiveException
   * @throws InvalidOperationException
   */
  public void revokeGlobalPriv(String username, Collection<Privilege> privs) throws HiveException, InvalidOperationException {
    User usr = this.getUser(username);
    if(usr == null) {
      throw new HiveException("The user: " + username + " doesn't exist");
    }
    for(Privilege priv : privs) {
      switch(priv) {
        case ALTER_PRIV :
          usr.setAlter_priv(false);
          break;
        case CREATE_PRIV :
          usr.setCreate_priv(false);
          break;
        case CREATE_USER_PRIV :
          usr.setCreate_user_priv(false);
          break;
        case DROP_PRIV :
          usr.setDrop_priv(false);
          break;
        case GRANT_PRIV :
          usr.setGrant_priv(false);
          break;
        case INSERT_PRIV :
          usr.setInsert_priv(false);
          break;
        case SELECT_PRIV :
          usr.setSelect_priv(false);
          break;
        case SUPER_PRIV :
          usr.setSuper_priv(false);
          break;
        default:
          throw new HiveException("Global privilege don't support " + priv);
      }
    }
    try {
      getMSC().alterUser(username, usr.getTUser());
    } catch (MetaException e) {
      throw new HiveException("Unable to alter user.", e);
    } catch (TException e) {
      throw new HiveException("Unable to alter user.", e);
    } catch (NoSuchObjectException e) {
      throw new HiveException("User not exists.", e);
    }
  }

  /**
   * Revoke database privilege from user.
   * @param username
   * @param dbname
   * @param privs
   * @throws HiveException
   * @throws InvalidOperationException
   */
  public void revokeDatabasePriv(String username, String dbname, Collection<Privilege> privs)
      throws HiveException, InvalidOperationException {
    List<DbPriv> dbPrivs = null;
    if (username != null) {
      dbPrivs = new ArrayList<DbPriv>();
      dbPrivs.add(getDbPriv(username, dbname));
    } else {
      dbPrivs = getDbPrivs(dbname);
    }

    for (DbPriv dbpriv : dbPrivs) {
      if(dbpriv == null) {
        return;
      }
      for(Privilege priv : privs) {
        switch(priv) {
          case ALTER_PRIV :
            dbpriv.setAlter_priv(false);
            break;
          case DROP_PRIV :
            dbpriv.setDrop_priv(false);
            break;
          case GRANT_PRIV :
            dbpriv.setGrant_priv(false);
            break;
          case INSERT_PRIV :
            dbpriv.setInsert_priv(false);
            break;
          case SELECT_PRIV :
            dbpriv.setSelect_priv(false);
            break;
          default:
            throw new HiveException("Database privilege don't support " + priv);
        }
      }

      try {
        getMSC().alterDbPriv(dbpriv.getUserName(), dbname, dbpriv.getTDbPriv());
      } catch (InvalidObjectException e) {
        throw new HiveException(e.getMessage());
      } catch (NoSuchObjectException e) {
        throw new HiveException(e.getMessage());
      } catch (TException e) {
        throw new HiveException(e.getMessage());
      } catch (MetaException e) {
        throw new HiveException(e.getMessage());
      }
    }
  }

  /**
   * @param username
   * @param dbname
   * @param tbname
   * @param privs
   * @throws HiveException
   * @throws InvalidOperationException
   */
  public void revokeTablePriv(String username, String dbname, String tbname,
      Collection<Privilege> privs) throws HiveException, InvalidOperationException {
    List<TbPriv> tbPrivs = null;
    if (username != null) {
      tbPrivs = new ArrayList<TbPriv>();
      tbPrivs.add(getTbPriv(username, dbname, tbname));
    } else {
      tbPrivs = getTbPrivs(dbname, tbname);
    }

    for (TbPriv tbpriv : tbPrivs) {
      if(tbpriv == null) {
        return;
      }
      for(Privilege priv : privs) {
        switch(priv) {
          case ALTER_PRIV :
            tbpriv.setAlter_priv(false);
            break;
          case DROP_PRIV :
            tbpriv.setDrop_priv(false);
            break;
          case GRANT_PRIV :
            tbpriv.setGrant_priv(false);
            break;
          case INSERT_PRIV :
            tbpriv.setInsert_priv(false);
            break;
          case SELECT_PRIV :
            tbpriv.setSelect_priv(false);
            break;
          default:
            throw new HiveException("Table privilege don't support " + priv);
        }
      }

      try {
        getMSC().alterTbPriv(tbpriv.getUserName(), dbname, tbname, tbpriv.getTTbPriv());
      } catch (InvalidObjectException e) {
        throw new HiveException(e.getMessage());
      } catch (NoSuchObjectException e) {
        throw new HiveException(e.getMessage());
      } catch (TException e) {
        throw new HiveException(e.getMessage());
      } catch (MetaException e) {
        throw new HiveException(e.getMessage());
      }
    }
  }

  /**
   * Returns metadata for the table named tableName in the current database.
   * @param tableName the name of the table
   * @return
   * @throws HiveException if there's an internal error or if the
   * table doesn't exist
   */
  public Table getTable(final String tableName) throws HiveException {
    return this.getTable(getCurrentDatabase(), tableName, true);
  }

  /**
   * Returns metadata of the table
   *
   * @param dbName
   *          the name of the database
   * @param tableName
   *          the name of the table
   * @return the table
   * @exception HiveException
   *              if there's an internal error or if the table doesn't exist
   */
  public Table getTable(final String dbName, final String tableName) throws HiveException {
    return this.getTable(dbName, tableName, true);
  }

  /**
   * Returns metadata of the table
   *
   * @param dbName
   *          the name of the database
   * @param tableName
   *          the name of the table
   * @param throwException
   *          controls whether an exception is thrown or a returns a null
   * @return the table or if throwException is false a null value.
   * @throws HiveException
   */
  public Table getTable(final String dbName, final String tableName,
      boolean throwException) throws HiveException {

    if (tableName == null || tableName.equals("")) {
      throw new HiveException("empty table creation??");
    }

    // Get the table from metastore
    org.apache.hadoop.hive.metastore.api.Table tTable = null;
    try {
      tTable = getMSC().getTable(dbName, tableName);
    } catch (NoSuchObjectException e) {
      if (throwException) {
        LOG.error(StringUtils.stringifyException(e));
        throw new InvalidTableException("Table not found ", tableName);
      }
      return null;
    } catch (Exception e) {
      throw new HiveException("Unable to fetch table " + tableName, e);
    }

    // For non-views, we need to do some extra fixes
    if (!TableType.VIRTUAL_VIEW.toString().equals(tTable.getTableType())) {
      // Fix the non-printable chars
      Map<String, String> parameters = tTable.getSd().getParameters();
      String sf = parameters.get(SERIALIZATION_FORMAT);
      if (sf != null) {
        char[] b = sf.toCharArray();
        if ((b.length == 1) && (b[0] < 10)) { // ^A, ^B, ^C, ^D, \t
          parameters.put(SERIALIZATION_FORMAT,
              Integer.toString(b[0]));
        }
      }

      // Use LazySimpleSerDe for MetadataTypedColumnsetSerDe.
      // NOTE: LazySimpleSerDe does not support tables with a single column of
      // col
      // of type "array<string>". This happens when the table is created using
      // an
      // earlier version of Hive.
      if (org.apache.hadoop.hive.serde2.MetadataTypedColumnsetSerDe.class
          .getName().equals(
            tTable.getSd().getSerdeInfo().getSerializationLib())
          && tTable.getSd().getColsSize() > 0
          && tTable.getSd().getCols().get(0).getType().indexOf('<') == -1) {
        tTable.getSd().getSerdeInfo().setSerializationLib(
            org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe.class.getName());
      }
    }

    Table table = new Table(tTable);

    table.checkValidity();
    return table;
  }

  /**
   * Get all table names for the current database.
   * @return List of table names
   * @throws HiveException
   */
  public List<String> getAllTables() throws HiveException {
    return getAllTables(getCurrentDatabase());
  }

  /**
   * Get all table names for the specified database.
   * @param dbName
   * @return List of table names
   * @throws HiveException
   */
  public List<String> getAllTables(String dbName) throws HiveException {
    return getTablesByPattern(dbName, ".*");
  }

  public List<String> getAllTablesForUser(String userName) throws HiveException {
    return getTablesForUser(userName, getCurrentDatabase(), ".*");
  }

  public List<String> getTablesForUser(String userName, String tablePattern) throws HiveException {
    return getTablesForUser(userName, getCurrentDatabase(), tablePattern);
  }

  public List<String> getTablesForUser(String userName, String database, String tablePattern) throws HiveException {
    List<String> tables = new ArrayList<String>();
    try {
      tables = getMSC().getTables(database, tablePattern);
    } catch(Exception e) {
      throw new HiveException(e);
    }
    List<Privilege> showsPriv = new ArrayList<Privilege>();
    showsPriv.add(Privilege.SELECT_PRIV);
    showsPriv.add(Privilege.DROP_PRIV);
    showsPriv.add(Privilege.ALTER_PRIV);
    showsPriv.add(Privilege.INSERT_PRIV);

    User user = this.getUser(userName);
    if(user != null &&
        (user.getAllPriv().contains(showsPriv) || user.hasSuper_priv())) {
      return tables;
    }
    DbPriv dbpriv = this.getDbPriv(userName, database);
    if(dbpriv != null && dbpriv.getAllPriv().contains(showsPriv)) {
      return tables;
    }

    List<String> resTables = new ArrayList<String>();
    for(String tb : tables) {
      TbPriv tbpriv = this.getTbPriv(userName, database, tb);
      List<Privilege> tbPrivs = new ArrayList<Privilege>();
      if(tbpriv != null) {
        tbPrivs = tbpriv.getAllPriv();
      }
      for(Privilege priv : showsPriv) {
        if(tbPrivs.contains(priv)) {
          resTables.add(tb);
          break;
        }
      }
    }
    return resTables;
  }

  /**
   * Returns all existing tables from default database which match the given
   * pattern. The matching occurs as per Java regular expressions
   *
   * @param tablePattern
   *          java re pattern
   * @return list of table names
   * @throws HiveException
   */
  public List<String> getTablesByPattern(String tablePattern) throws HiveException {
    return getTablesByPattern(getCurrentDatabase(), tablePattern);
  }

  /**
   * Returns all existing tables from the specified database which match the given
   * pattern. The matching occurs as per Java regular expressions.
   * @param dbName
   * @param tablePattern
   * @return list of table names
   * @throws HiveException
   */
  public List<String> getTablesByPattern(String dbName, String tablePattern) throws HiveException {
    try {
      return getMSC().getTables(dbName, tablePattern);
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Returns all existing tables from the given database which match the given
   * pattern. The matching occurs as per Java regular expressions
   *
   * @param database
   *          the database name
   * @param tablePattern
   *          java re pattern
   * @return list of table names
   * @throws HiveException
   */
  public List<String> getTablesForDb(String database, String tablePattern)
      throws HiveException {
    try {
      return getMSC().getTables(database, tablePattern);
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   *
   * @param dbName
   * @return
   * @throws HiveException
   */
  public Database getDatabase(String dbName) throws HiveException {
    return getDatabase(dbName, true);
  }

  /**
   * Get database by name
   *
   * @return
   * @throws HiveException
   */
  public Database getDatabase(String dbName, boolean throwException) throws HiveException {
    try {
      return getMSC().getDatabase(dbName);
    } catch (NoSuchObjectException e) {
      if (throwException) {
        LOG.error(StringUtils.stringifyException(e));
        throw new InvalidTableException("Database not found ", dbName);
      }
      return null;
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Get all existing database names.
   *
   * @return List of database names.
   * @throws HiveException
   */
  public List<String> getAllDatabases() throws HiveException {
    try {
      return getMSC().getAllDatabases();
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Get all existing databases that match the given
   * pattern. The matching occurs as per Java regular expressions
   *
   * @param databasePattern
   *          java re pattern
   * @return list of database names
   * @throws HiveException
   */
  public List<String> getDatabasesByPattern(String databasePattern) throws HiveException {
    try {
      return getMSC().getDatabases(databasePattern);
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Query metadata to see if a database with the given name already exists.
   *
   * @param dbName
   * @return true if a database with the given name already exists, false if
   *         does not exist.
   * @throws HiveException
   */
  public boolean databaseExists(String dbName) throws HiveException {
    try {
      if (null != getMSC().getDatabase(dbName)) {
        return true;
      }
      return false;
    } catch (NoSuchObjectException e) {
      return false;
    } catch (Exception e) {
      throw new HiveException(e);
    }
  }

  /**
   * Load a directory into a Hive Table Partition - Alters existing content of
   * the partition with the contents of loadPath. - If he partition does not
   * exist - one is created - files in loadPath are moved into Hive. But the
   * directory itself is not removed.
   *
   * @param loadPath
   *          Directory containing files to load into Table
   * @param tableName
   *          name of table to be loaded.
   * @param partSpec
   *          defines which partition needs to be loaded
   * @param replace
   *          if true - replace files in the partition, otherwise add files to
   *          the partition
   * @param tmpDirPath
   *          The temporary directory.
   */
  public void loadPartition(Path loadPath, String tableName,
      Map<String, String> partSpec, boolean replace, Path tmpDirPath)
      throws HiveException {
    Table tbl = getTable(tableName);
    try {
      /**
       * Move files before creating the partition since down stream processes
       * check for existence of partition in metadata before accessing the data.
       * If partition is created before data is moved, downstream waiting
       * processes might move forward with partial data
       */

      FileSystem fs;
      Path partPath;

      // check if partition exists without creating it
      Partition part = getPartition(tbl, partSpec, false);
      if (part == null) {
        // Partition does not exist currently. The partition name is
        // extrapolated from
        // the table's location (even if the table is marked external)
        fs = FileSystem.get(tbl.getDataLocation(), getConf());
        partPath = new Path(tbl.getDataLocation().getPath(),
            Warehouse.makePartName(partSpec));
      } else {
        // Partition exists already. Get the path from the partition. This will
        // get the default path for Hive created partitions or the external path
        // when directly created by user
        partPath = part.getPath()[0];
        fs = partPath.getFileSystem(getConf());
      }

      // this is for qa, not hive must have
      try {
        // Get all the metadata hooks and execute them.
        for (HiveMetaDataHook hiveMetaDataHook : getHiveMetaDataHooks()) {
          LOG.debug("hiveMetaDataHook is "+hiveMetaDataHook.getClass().getName());
          try {
              String partDfsLocation = partPath.makeQualified(fs).toString();
              partDfsLocation = hiveMetaDataHook.run(this, tbl, part, partSpec,
                  replace, partDfsLocation);
              if(partDfsLocation != null){
                partPath = new Path(partDfsLocation);
              }
          } catch (Exception e) {
            LOG.error(e.getMessage());
          }
        }
      } catch (Exception e) {
        LOG.error( e.getMessage() );
      }

      if (replace) {
        Hive.replaceFiles(loadPath, partPath, fs, tmpDirPath);
      } else {
        Hive.copyFiles(loadPath, partPath, fs);
      }

      // recreate the partition if it existed before
      part = getPartition(tbl, partSpec, true);
    } catch (IOException e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new HiveException(e);
    } catch (MetaException e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new HiveException(e);
    }

  }

  /**
   * Given a source directory name of the load path, load all dynamically generated partitions
   * into the specified table and return a list of strings that represent the dynamic partition
   * paths.
   * @param loadPath
   * @param tableName
   * @param partSpec
   * @param replace
   * @param tmpDirPath
   * @param numSp: number of static partitions in the partition spec
   * @return
   * @throws HiveException
   */
  public ArrayList<LinkedHashMap<String, String>> loadDynamicPartitions(Path loadPath,
      String tableName, Map<String, String> partSpec, boolean replace,
      Path tmpDirPath, int numDP)
      throws HiveException {

    try {
      ArrayList<LinkedHashMap<String, String>> fullPartSpecs =
        new ArrayList<LinkedHashMap<String, String>>();

      FileSystem fs = loadPath.getFileSystem(conf);
      FileStatus[] status = Utilities.getFileStatusRecurse(loadPath, numDP, fs);
      if (status.length == 0) {
        LOG.warn("No partition is genereated by dynamic partitioning");
      }

      if (status.length > conf.getIntVar(HiveConf.ConfVars.DYNAMICPARTITIONMAXPARTS)) {
        throw new HiveException("Number of dynamic partitions created is " + status.length
            + ", which is more than "
            + conf.getIntVar(HiveConf.ConfVars.DYNAMICPARTITIONMAXPARTS)
            +". To solve this try to set " + HiveConf.ConfVars.DYNAMICPARTITIONMAXPARTS.varname
            + " to at least " + status.length + '.');
      }

      // for each dynamically created DP directory, construct a full partition spec
      // and load the partition based on that
      for (int i= 0; i < status.length; ++i) {
        // get the dynamically created directory
        Path partPath = status[i].getPath();
        assert fs.getFileStatus(partPath).isDir():
          "partitions " + partPath + " is not a directory !";

        // generate a full partition specification
        LinkedHashMap<String, String> fullPartSpec = new LinkedHashMap<String, String>(partSpec);
        Warehouse.makeSpecFromName(fullPartSpec, partPath);
      	fullPartSpecs.add(fullPartSpec);

        // finally load the partition -- move the file to the final table address
      	loadPartition(partPath, tableName, fullPartSpec, replace, tmpDirPath);
      	LOG.info("New loading path = " + partPath + " with partSpec " + fullPartSpec);
    	}
      return fullPartSpecs;
    } catch (IOException e) {
      throw new HiveException(e);
    }
  }

  /**
   * Load a directory into a Hive Table. - Alters existing content of table with
   * the contents of loadPath. - If table does not exist - an exception is
   * thrown - files in loadPath are moved into Hive. But the directory itself is
   * not removed.
   *
   * @param loadPath
   *          Directory containing files to load into Table
   * @param tableName
   *          name of table to be loaded.
   * @param replace
   *          if true - replace files in the table, otherwise add files to table
   * @param tmpDirPath
   *          The temporary directory.
   */
  public void loadTable(Path loadPath, String tableName, boolean replace,
      Path tmpDirPath) throws HiveException {
    Table tbl = getTable(tableName);

    // this is for qa, not hive must have
    try {
      // Get all the metadata hooks and execute them.
      for (HiveMetaDataHook hiveMetaDataHook : getHiveMetaDataHooks()) {
        LOG.debug("hiveMetaDataHook is "+hiveMetaDataHook.getClass().getName());
        try {
          hiveMetaDataHook.run(this, tbl, null, null ,
              replace, tbl.getDataLocation().toString());
        } catch (Exception e) {
          LOG.error(e.getMessage());
        }
      }
    } catch (Exception e) {
      LOG.error( e.getMessage() );
    }

    if (replace) {
      tbl.replaceFiles(loadPath, tmpDirPath);
    } else {
      tbl.copyFiles(loadPath);
    }
  }

  // this is for qa, not hive must have
  private List<HiveMetaDataHook> getHiveMetaDataHooks() throws Exception {
    ArrayList<HiveMetaDataHook> metaDataHooks = new ArrayList<HiveMetaDataHook>();
    String classString = conf.get("hive.metadata.hooks");
    LOG.debug(" HiveMetaDataHook is "+classString);
    classString = classString.trim();
    if (classString.equals("")) {
      return metaDataHooks;
    }

    String[] classesArray = classString.split(",");

    for (String oneClass : classesArray) {
      try {
        metaDataHooks.add((HiveMetaDataHook) Class.forName(oneClass.trim(), true, JavaUtils.getClassLoader())
            .newInstance());
      } catch (ClassNotFoundException e) {
        LOG.error("Hive MetaData Hook Class not found:" + e.getMessage());
        throw e;
      }
    }

    return metaDataHooks;
  }

  /**
   * Creates a partition.
   *
   * @param tbl
   *          table for which partition needs to be created
   * @param partSpec
   *          partition keys and their values
   * @return created partition object
   * @throws HiveException
   *           if table doesn't exist or partition already exists
   */
  public Partition createPartition(Table tbl, Map<String, String> partSpec)
      throws HiveException {
    return createPartition(tbl, partSpec, null);
  }

  /**
   * Creates a partition
   *
   * @param tbl
   *          table for which partition needs to be created
   * @param partSpec
   *          partition keys and their values
   * @param location
   *          location of this partition
   * @return created partition object
   * @throws HiveException
   *           if table doesn't exist or partition already exists
   */
  public Partition createPartition(Table tbl, Map<String, String> partSpec,
      Path location) throws HiveException {

    org.apache.hadoop.hive.metastore.api.Partition partition = null;

    for (FieldSchema field : tbl.getPartCols()) {
      String val = partSpec.get(field.getName());
      if (val == null || val.length() == 0) {
        throw new HiveException("add partition: Value for key "
            + field.getName() + " is null or empty");
      }
    }

    try {
      Partition tmpPart = new Partition(tbl, partSpec, location);
      partition = getMSC().add_partition(tmpPart.getTPartition());
    } catch (Exception e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new HiveException(e);
    }

    return new Partition(tbl, partition);
  }

  /**
   * Returns partition metadata
   *
   * @param tbl
   *          the partition's table
   * @param partSpec
   *          partition keys and values
   * @param forceCreate
   *          if this is true and partition doesn't exist then a partition is
   *          created
   * @return result partition object or null if there is no partition
   * @throws HiveException
   */
  public Partition getPartition(Table tbl, Map<String, String> partSpec,
      boolean forceCreate) throws HiveException {
    if (!tbl.isValidSpec(partSpec)) {
      throw new HiveException("Invalid partition: " + partSpec);
    }
    List<String> pvals = new ArrayList<String>();
    for (FieldSchema field : tbl.getPartCols()) {
      String val = partSpec.get(field.getName());
      // enable dynamic partitioning
      if (val == null && !HiveConf.getBoolVar(conf, HiveConf.ConfVars.DYNAMICPARTITIONING)
          || val.length() == 0) {
        throw new HiveException("get partition: Value for key "
            + field.getName() + " is null or empty");
      } else if (val != null){
        pvals.add(val);
      }
    }
    org.apache.hadoop.hive.metastore.api.Partition tpart = null;
    try {
      tpart = getMSC().getPartition(tbl.getDbName(), tbl.getTableName(), pvals);
    } catch (NoSuchObjectException nsoe) {
      // this means no partition exists for the given partition
      // key value pairs - thrift cannot handle null return values, hence
      // getPartition() throws NoSuchObjectException to indicate null partition
      tpart = null;
    } catch (Exception e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new HiveException(e);
    }
    try {
      if (forceCreate) {
        if (tpart == null) {
          LOG.debug("creating partition for table " + tbl.getTableName()
                    + " with partition spec : " + partSpec);
          tpart = getMSC().appendPartition(tbl.getDbName(), tbl.getTableName(), pvals);
        }
        else {
          LOG.debug("altering partition for table " + tbl.getTableName()
                    + " with partition spec : " + partSpec);

          tpart.getSd().setOutputFormat(tbl.getTTable().getSd().getOutputFormat());
          tpart.getSd().setInputFormat(tbl.getTTable().getSd().getInputFormat());
          tpart.getSd().getSerdeInfo().setSerializationLib(tbl.getSerializationLib());
          alterPartition(tbl.getTableName(), new Partition(tbl, tpart));
        }
      }
      if (tpart == null) {
        return null;
      }
    } catch (Exception e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new HiveException(e);
    }
    return new Partition(tbl, tpart);
  }

  public boolean dropPartition(String db_name, String tbl_name,
      List<String> part_vals, boolean deleteData) throws HiveException {
    try {
      return getMSC().dropPartition(db_name, tbl_name, part_vals, deleteData);
    } catch (NoSuchObjectException e) {
      throw new HiveException("Partition or table doesn't exist.", e);
    } catch (Exception e) {
      throw new HiveException("Unknow error. Please check logs.", e);
    }
  }

  public List<String> getPartitionNames(String dbName, String tblName, short max)
      throws HiveException {
    List<String> names = null;
    try {
      names = getMSC().listPartitionNames(dbName, tblName, max);
    } catch (Exception e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new HiveException(e);
    }
    return names;
  }

  public List<String> getPartitionNames(String dbName, String tblName,
      Map<String, String> partSpec, short max) throws HiveException {
    List<String> names = null;
    Table t = getTable(dbName, tblName);

    List<String> pvals = getPvals(t.getPartCols(), partSpec);

    try {
      names = getMSC().listPartitionNames(dbName, tblName, pvals, max);
    } catch (Exception e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new HiveException(e);
    }
    return names;
  }

  /**
   * get all the partitions that the table has
   *
   * @param tbl
   *          object for which partition is needed
   * @return list of partition objects
   * @throws HiveException
   */
  public List<Partition> getPartitions(Table tbl) throws HiveException {
    if (tbl.isPartitioned()) {
      List<org.apache.hadoop.hive.metastore.api.Partition> tParts;
      try {
        tParts = getMSC().listPartitions(tbl.getDbName(), tbl.getTableName(),
            (short) -1);
      } catch (Exception e) {
        LOG.error(StringUtils.stringifyException(e));
        throw new HiveException(e);
      }
      List<Partition> parts = new ArrayList<Partition>(tParts.size());
      for (org.apache.hadoop.hive.metastore.api.Partition tpart : tParts) {
        parts.add(new Partition(tbl, tpart));
      }
      return parts;
    } else {
      Partition part = new Partition(tbl);
      ArrayList<Partition> parts = new ArrayList<Partition>(1);
      parts.add(part);
      return parts;
    }
  }

  private static List<String> getPvals(List<FieldSchema> partCols,
      Map<String, String> partSpec) {
    List<String> pvals = new ArrayList<String>();
    for (FieldSchema field : partCols) {
      String val = partSpec.get(field.getName());
      if (val == null) {
        val = "";
      }
      pvals.add(val);
    }
    return pvals;
  }

  /**
   * get all the partitions of the table that matches the given partial
   * specification. partition columns whose value is can be anything should be
   * an empty string.
   *
   * @param tbl
   *          object for which partition is needed. Must be partitioned.
   * @return list of partition objects
   * @throws HiveException
   */
  public List<Partition> getPartitions(Table tbl, Map<String, String> partialPartSpec)
  throws HiveException {
    if (!tbl.isPartitioned()) {
      throw new HiveException("Partition spec should only be supplied for a " +
      		"partitioned table");
    }

    List<String> partialPvals = getPvals(tbl.getPartCols(), partialPartSpec);

    List<org.apache.hadoop.hive.metastore.api.Partition> partitions = null;
    try {
      partitions = getMSC().listPartitions(tbl.getDbName(), tbl.getTableName(),
          partialPvals, (short) -1);
    } catch (Exception e) {
      throw new HiveException(e);
    }

    List<Partition> qlPartitions = new ArrayList<Partition>();
    for (org.apache.hadoop.hive.metastore.api.Partition p : partitions) {
      qlPartitions.add( new Partition(tbl, p));
    }

    return qlPartitions;
  }

  /**
   * Get the name of the current database
   * @return
   */
  public String getCurrentDatabase() {
    if (null == currentDatabase) {
      currentDatabase = DEFAULT_DATABASE_NAME;
    }
    return currentDatabase;
  }

  /**
   * Set the name of the current database
   * @param currentDatabase
   */
  public void setCurrentDatabase(String currentDatabase) {
    this.currentDatabase = currentDatabase;
  }

  static private void checkPaths(FileSystem fs, FileStatus[] srcs, Path destf,
      boolean replace) throws HiveException {
    try {
      for (FileStatus src : srcs) {
        FileStatus[] items = fs.listStatus(src.getPath());
        for (FileStatus item : items) {

          if (Utilities.isTempPath(item)) {
            // This check is redundant because temp files are removed by
            // execution layer before
            // calling loadTable/Partition. But leaving it in just in case.
            fs.delete(item.getPath(), true);
            continue;
          }
          if (item.isDir()) {
            throw new HiveException("checkPaths: " + src.getPath()
                + " has nested directory" + item.getPath());
          }
          Path tmpDest = new Path(destf, item.getPath().getName());
          if (!replace && fs.exists(tmpDest)) {
            throw new HiveException("checkPaths: " + tmpDest
                + " already exists");
          }
        }
      }
    } catch (IOException e) {
      throw new HiveException("checkPaths: filesystem error in check phase", e);
    }
  }

  static protected void copyFiles(Path srcf, Path destf, FileSystem fs)
      throws HiveException {
    try {
      // create the destination if it does not exist
      if (!fs.exists(destf)) {
        fs.mkdirs(destf);
      }
    } catch (IOException e) {
      throw new HiveException(
          "copyFiles: error while checking/creating destination directory!!!",
          e);
    }

    FileStatus[] srcs;
    try {
      srcs = fs.globStatus(srcf);
    } catch (IOException e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new HiveException("addFiles: filesystem error in check phase", e);
    }
    if (srcs == null) {
      LOG.info("No sources specified to move: " + srcf);
      return;
      // srcs = new FileStatus[0]; Why is this needed?
    }
    // check that source and target paths exist
    checkPaths(fs, srcs, destf, false);

    // move it, move it
    try {
      for (FileStatus src : srcs) {
        FileStatus[] items = fs.listStatus(src.getPath());
        for (FileStatus item : items) {
          Path source = item.getPath();
          Path target = new Path(destf, item.getPath().getName());
          if (!fs.rename(source, target)) {
            throw new IOException("Cannot move " + source + " to " + target);
          }
        }
      }
    } catch (IOException e) {
      throw new HiveException("copyFiles: error while moving files!!!", e);
    }
  }

  /**
   * Replaces files in the partition with new data set specifed by srcf. Works
   * by moving files
   *
   * @param srcf
   *          Files to be moved. Leaf Directories or Globbed File Paths
   * @param destf
   *          The directory where the final data needs to go
   * @param fs
   *          The filesystem handle
   * @param tmppath
   *          Temporary directory
   */
  static protected void replaceFiles(Path srcf, Path destf, FileSystem fs,
      Path tmppath) throws HiveException {
    FileStatus[] srcs;
    try {
      srcs = fs.globStatus(srcf);
    } catch (IOException e) {
      throw new HiveException("addFiles: filesystem error in check phase", e);
    }
    if (srcs == null) {
      LOG.info("No sources specified to move: " + srcf);
      return;
      // srcs = new FileStatus[0]; Why is this needed?
    }
    checkPaths(fs, srcs, destf, true);

    try {
      fs.mkdirs(tmppath);
      for (FileStatus src : srcs) {
        FileStatus[] items = fs.listStatus(src.getPath());
        for (int j = 0; j < items.length; j++) {
          if (!fs.rename(items[j].getPath(), new Path(tmppath, items[j]
              .getPath().getName()))) {
            throw new HiveException("Error moving: " + items[j].getPath()
                + " into: " + tmppath);
          }
        }
      }

      // point of no return
      boolean b = fs.delete(destf, true);
      LOG.debug("Deleting:" + destf.toString() + ",Status:" + b);

      // create the parent directory otherwise rename can fail if the parent
      // doesn't exist
      if (!fs.mkdirs(destf.getParent())) {
        throw new HiveException("Unable to create destination directory: "
            + destf.getParent().toString());
      }

      b = fs.rename(tmppath, destf);
      if (!b) {
        throw new HiveException("Unable to move results from " + tmppath
            + " to destination directory: " + destf.getParent().toString());
      }
      LOG.debug("Renaming:" + tmppath.toString() + ",Status:" + b);

    } catch (IOException e) {
      throw new HiveException("replaceFiles: error while moving files from "
          + tmppath + " to " + destf + "!!!", e);
    }
    // In case of error, we should leave the temporary data there, so
    // that user can recover the data if necessary.
  }

  /**
   * Creates a metastore client. Currently it creates only JDBC based client as
   * File based store support is removed
   *
   * @returns a Meta Store Client
   * @throws HiveMetaException
   *           if a working client can't be created
   */
  private IMetaStoreClient createMetaStoreClient() throws MetaException {

    HiveMetaHookLoader hookLoader = new HiveMetaHookLoader() {
        public HiveMetaHook getHook(
          org.apache.hadoop.hive.metastore.api.Table tbl)
          throws MetaException {

          try {
            if (tbl == null) {
              return null;
            }
            HiveStorageHandler storageHandler =
              HiveUtils.getStorageHandler(
                conf,
                tbl.getParameters().get(META_TABLE_STORAGE));
            if (storageHandler == null) {
              return null;
            }
            return storageHandler.getMetaHook();
          } catch (HiveException ex) {
            LOG.error(StringUtils.stringifyException(ex));
            throw new MetaException(
              "Failed to load storage handler:  " + ex.getMessage());
          }
        }
      };
    return new HiveMetaStoreClient(conf, hookLoader);
  }

  /**
   *
   * @return the metastore client for the current thread
   * @throws MetaException
   */
  private IMetaStoreClient getMSC() throws MetaException {
    if (metaStoreClient == null) {
      metaStoreClient = createMetaStoreClient();
    }
    return metaStoreClient;
  }

  public static List<FieldSchema> getFieldsFromDeserializer(String name,
      Deserializer serde) throws HiveException {
    try {
      return MetaStoreUtils.getFieldsFromDeserializer(name, serde);
    } catch (SerDeException e) {
      throw new HiveException("Error in getting fields from serde. "
          + e.getMessage(), e);
    } catch (MetaException e) {
      throw new HiveException("Error in getting fields from serde."
          + e.getMessage(), e);
    }
  }
};
