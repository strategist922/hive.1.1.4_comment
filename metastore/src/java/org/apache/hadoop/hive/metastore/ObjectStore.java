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

package org.apache.hadoop.hive.metastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.datastore.DataStoreCache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.DbPriv;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.InvalidObjectException;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.NoSuchObjectException;
import org.apache.hadoop.hive.metastore.api.Order;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.metastore.api.SerDeInfo;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.metastore.api.TbPriv;
import org.apache.hadoop.hive.metastore.api.Type;
import org.apache.hadoop.hive.metastore.api.User;
import org.apache.hadoop.hive.metastore.model.MDatabase;
import org.apache.hadoop.hive.metastore.model.MDbPriv;
import org.apache.hadoop.hive.metastore.model.MFieldSchema;
import org.apache.hadoop.hive.metastore.model.MOrder;
import org.apache.hadoop.hive.metastore.model.MPartition;
import org.apache.hadoop.hive.metastore.model.MSerDeInfo;
import org.apache.hadoop.hive.metastore.model.MStorageDescriptor;
import org.apache.hadoop.hive.metastore.model.MTable;
import org.apache.hadoop.hive.metastore.model.MTbPriv;
import org.apache.hadoop.hive.metastore.model.MType;
import org.apache.hadoop.hive.metastore.model.MUser;
import org.apache.hadoop.util.StringUtils;

/**
 * This class is the interface between the application logic and the database
 * store that contains the objects. Refrain putting any logic in mode.M* objects
 * or in this file as former could be auto generated and this class would need
 * to be made into a interface that can read both from a database and a
 * filestore.
 */
public class ObjectStore implements RawStore, Configurable {
  private static Properties prop = null;
  private static PersistenceManagerFactory pmf = null;

  private static Lock pmfPropLock = new ReentrantLock();
  private static final Log LOG = LogFactory.getLog(ObjectStore.class.getName());

  private static enum TXN_STATUS {
    NO_STATE, OPEN, COMMITED, ROLLBACK
  }

  private boolean isInitialized = false;
  private PersistenceManager pm = null;
  private Configuration hiveConf;
  private int openTrasactionCalls = 0;
  private Transaction currentTransaction = null;
  private TXN_STATUS transactionStatus = TXN_STATUS.NO_STATE;

  public ObjectStore() {
  }

  public Configuration getConf() {
    return hiveConf;
  }

  /**
   * Called whenever this object is instantiated using ReflectionUils, and also
   * on connection retries. In cases of connection retries, conf will usually
   * contain modified values.
   */
  @SuppressWarnings("nls")
  public void setConf(Configuration conf) {
    // Although an instance of ObjectStore is accessed by one thread, there may
    // be many threads with ObjectStore instances. So the static variables
    // pmf and prop need to be protected with locks.
    pmfPropLock.lock();
    try {
      isInitialized = false;
      hiveConf = conf;
      Properties propsFromConf = getDataSourceProps(conf);
      boolean propsChanged = !propsFromConf.equals(prop);

      if (propsChanged) {
        pmf = null;
        prop = null;
      }

      assert(!isActiveTransaction());
      shutdown();
      // Always want to re-create pm as we don't know if it were created by the
      // most recent instance of the pmf
      pm = null;
      openTrasactionCalls = 0;
      currentTransaction = null;
      transactionStatus = TXN_STATUS.NO_STATE;

      initialize(propsFromConf);

      if (!isInitialized) {
        throw new RuntimeException(
        "Unable to create persistence manager. Check dss.log for details");
      } else {
        LOG.info("Initialized ObjectStore");
      }
    } finally {
      pmfPropLock.unlock();
    }
  }

  private ClassLoader classLoader;
  {
    classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = ObjectStore.class.getClassLoader();
    }
  }

  @SuppressWarnings("nls")
  private void initialize(Properties dsProps) {
    LOG.info("ObjectStore, initialize called");
    prop = dsProps;
    pm = getPersistenceManager();
    isInitialized = pm != null;
    return;
  }

  /**
   * Properties specified in hive-default.xml override the properties specified
   * in jpox.properties.
   */
  @SuppressWarnings("nls")
  private static Properties getDataSourceProps(Configuration conf) {
    Properties prop = new Properties();

    Iterator<Map.Entry<String, String>> iter = conf.iterator();
    while (iter.hasNext()) {
      Map.Entry<String, String> e = iter.next();
      if (e.getKey().contains("datanucleus") || e.getKey().contains("jdo")) {
        Object prevVal = prop.setProperty(e.getKey(), e.getValue());
        if (LOG.isDebugEnabled()
            && !e.getKey().equals(HiveConf.ConfVars.METASTOREPWD.varname)) {
          LOG.debug("Overriding " + e.getKey() + " value " + prevVal
              + " from  jpox.properties with " + e.getValue());
        }
      }
    }

    if (LOG.isDebugEnabled()) {
      for (Entry<Object, Object> e : prop.entrySet()) {
        if (!e.getKey().equals(HiveConf.ConfVars.METASTOREPWD.varname)) {
          LOG.debug(e.getKey() + " = " + e.getValue());
        }
      }
    }
    return prop;
  }

  private static PersistenceManagerFactory getPMF() {
    if (pmf == null) {
      pmf = JDOHelper.getPersistenceManagerFactory(prop);
      DataStoreCache dsc = pmf.getDataStoreCache();
      if (dsc != null) {
        dsc.pinAll(true, MTable.class);
        dsc.pinAll(true, MStorageDescriptor.class);
        dsc.pinAll(true, MSerDeInfo.class);
        dsc.pinAll(true, MPartition.class);
        dsc.pinAll(true, MDatabase.class);
        dsc.pinAll(true, MType.class);
        dsc.pinAll(true, MFieldSchema.class);
        dsc.pinAll(true, MOrder.class);
      }
    }
    return pmf;
  }

  private PersistenceManager getPersistenceManager() {
    return getPMF().getPersistenceManager();
  }

  public void shutdown() {
    if (pm != null) {
      pm.close();
    }
  }

  /**
   * Opens a new one or the one already created Every call of this function must
   * have corresponding commit or rollback function call
   *
   * @return an active transaction
   */

  public boolean openTransaction() {
    openTrasactionCalls++;
    if (openTrasactionCalls == 1) {
      currentTransaction = pm.currentTransaction();
      currentTransaction.begin();
      transactionStatus = TXN_STATUS.OPEN;
    } else {
      // something is wrong since openTransactionCalls is greater than 1 but
      // currentTransaction is not active
      assert ((currentTransaction != null) && (currentTransaction.isActive()));
    }
    return currentTransaction.isActive();
  }

  /**
   * if this is the commit of the first open call then an actual commit is
   * called.
   *
   * @return Always returns true
   */
  @SuppressWarnings("nls")
  public boolean commitTransaction() {
    if (TXN_STATUS.ROLLBACK == transactionStatus) {
      return false;
    }
    if (openTrasactionCalls <= 0) {
      throw new RuntimeException("commitTransaction was called but openTransactionCalls = "
          + openTrasactionCalls + ". This probably indicates that there are unbalanced " +
              "calls to openTransaction/commitTransaction");
    }
    if (!currentTransaction.isActive()) {
      throw new RuntimeException(
          "Commit is called, but transaction is not active. Either there are"
              + "mismatching open and close calls or rollback was called in the same trasaction");
    }
    openTrasactionCalls--;
    if ((openTrasactionCalls == 0) && currentTransaction.isActive()) {
      transactionStatus = TXN_STATUS.COMMITED;
      currentTransaction.commit();
    }
    return true;
  }

  /**
   * @return true if there is an active transaction. If the current transaction
   *         is either committed or rolled back it returns false
   */
  public boolean isActiveTransaction() {
    if (currentTransaction == null) {
      return false;
    }
    return currentTransaction.isActive();
  }

  /**
   * Rolls back the current transaction if it is active
   */
  public void rollbackTransaction() {
    if (openTrasactionCalls < 1) {
      return;
    }
    openTrasactionCalls = 0;
    if (currentTransaction.isActive()
        && transactionStatus != TXN_STATUS.ROLLBACK) {
      transactionStatus = TXN_STATUS.ROLLBACK;
      // could already be rolled back
      currentTransaction.rollback();
    }
  }


  public void createDatabase(Database db) throws InvalidObjectException, MetaException {
    boolean commited = false;
    MDatabase mdb = new MDatabase();
    mdb.setName(db.getName().toLowerCase());
    mdb.setLocationUri(db.getLocationUri());
    mdb.setDescription(db.getDescription());
    try {
      openTransaction();
      pm.makePersistent(mdb);
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
  }

  @SuppressWarnings("nls")
  private MDatabase getMDatabase(String name) throws NoSuchObjectException {
    MDatabase mdb = null;
    boolean commited = false;
    try {
      openTransaction();
      name = name.toLowerCase().trim();
      Query query = pm.newQuery(MDatabase.class, "name == dbname");
      query.declareParameters("java.lang.String dbname");
      query.setUnique(true);
      mdb = (MDatabase) query.execute(name);
      pm.retrieve(mdb);
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    if (mdb == null) {
      throw new NoSuchObjectException("There is no database named " + name);
    }
    return mdb;
  }

  public Database getDatabase(String name) throws NoSuchObjectException {
    MDatabase mdb = null;
    boolean commited = false;
    try {
      openTransaction();
      mdb = getMDatabase(name);
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    Database db = new Database();
    db.setName(mdb.getName());
    db.setDescription(mdb.getDescription());
    db.setLocationUri(mdb.getLocationUri());
    return db;
  }

  public boolean dropDatabase(String dbname) throws NoSuchObjectException, MetaException {
    boolean success = false;
    LOG.info("Dropping database " + dbname + " along with all tables");
    dbname = dbname.toLowerCase();
    try {
      openTransaction();

      // first drop tables
      for (String tableName : getAllTables(dbname)) {
        dropTable(dbname, tableName);
      }
      // then drop the database
      MDatabase db = getMDatabase(dbname);
      pm.retrieve(db);
      if (db != null) {
        pm.deletePersistent(db);
      }
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return success;
  }

  @SuppressWarnings("rawtypes")
  public List<String> getDatabases(String pattern) throws MetaException {
    boolean commited = false;
    List<String> databases = null;
    try {
      openTransaction();
      // Take the pattern and split it on the | to get all the composing
      // patterns
      String[] subpatterns = pattern.trim().split("\\|");
      String query = "select name from org.apache.hadoop.hive.metastore.model.MDatabase where (";
      boolean first = true;
      for (String subpattern : subpatterns) {
        subpattern = "(?i)" + subpattern.replaceAll("\\*", ".*");
        if (!first) {
          query = query + " || ";
        }
        query = query + " name.matches(\"" + subpattern + "\")";
        first = false;
      }
      query = query + ")";

      Query q = pm.newQuery(query);
      q.setResult("name");
      q.setOrdering("name ascending");
      Collection names = (Collection) q.execute();
      databases = new ArrayList<String>();
      for (Iterator i = names.iterator(); i.hasNext();) {
        databases.add((String) i.next());
      }
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    return databases;
  }

  public List<String> getAllDatabases() throws MetaException {
    return getDatabases(".*");
  }

  private MType getMType(Type type) {
    List<MFieldSchema> fields = new ArrayList<MFieldSchema>();
    if (type.getFields() != null) {
      for (FieldSchema field : type.getFields()) {
        fields.add(new MFieldSchema(field.getName(), field.getType(), field
            .getComment()));
      }
    }
    return new MType(type.getName(), type.getType1(), type.getType2(), fields);
  }

  private Type getType(MType mtype) {
    List<FieldSchema> fields = new ArrayList<FieldSchema>();
    if (mtype.getFields() != null) {
      for (MFieldSchema field : mtype.getFields()) {
        fields.add(new FieldSchema(field.getName(), field.getType(), field
            .getComment()));
      }
    }
    return new Type(mtype.getName(), mtype.getType1(), mtype.getType2(), fields);
  }

  public boolean createType(Type type) {
    boolean success = false;
    MType mtype = getMType(type);
    boolean commited = false;
    try {
      openTransaction();
      pm.makePersistent(mtype);
      commited = commitTransaction();
      success = true;
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    return success;
  }

  public Type getType(String typeName) {
    Type type = null;
    boolean commited = false;
    try {
      openTransaction();
      Query query = pm.newQuery(MType.class, "name == typeName");
      query.declareParameters("java.lang.String typeName");
      query.setUnique(true);
      MType mtype = (MType) query.execute(typeName.trim());
      pm.retrieve(type);
      if (mtype != null) {
        type = getType(mtype);
      }
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    return type;
  }

  public boolean dropType(String typeName) {
    boolean success = false;
    try {
      openTransaction();
      Query query = pm.newQuery(MType.class, "name == typeName");
      query.declareParameters("java.lang.String typeName");
      query.setUnique(true);
      MType type = (MType) query.execute(typeName.trim());
      pm.retrieve(type);
      if (type != null) {
        pm.deletePersistent(type);
      }
      success = commitTransaction();
    } catch (JDOObjectNotFoundException e) {
      success = commitTransaction();
      LOG.debug("type not found " + typeName, e);
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return success;
  }

  public void createTable(Table tbl) throws InvalidObjectException,
      MetaException {
    boolean commited = false;
    try {
      openTransaction();
      MTable mtbl = convertToMTable(tbl);
      pm.makePersistent(mtbl);
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
  }

  public boolean dropTable(String dbName, String tableName) throws MetaException {

    boolean success = false;
    try {
      openTransaction();
      MTable tbl = getMTable(dbName, tableName);
      pm.retrieve(tbl);
      if (tbl != null) {
        // first remove all the partitions
        pm.deletePersistentAll(listMPartitions(dbName, tableName, -1));
        // then remove the table
        pm.deletePersistent(tbl);
      }
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return success;
  }

  public Table getTable(String dbName, String tableName) throws MetaException {
    boolean commited = false;
    Table tbl = null;
    try {
      openTransaction();
      tbl = convertToTable(getMTable(dbName, tableName));
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    return tbl;
  }

  @SuppressWarnings("rawtypes")
  public List<String> getTables(String dbName, String pattern)
      throws MetaException {
    boolean commited = false;
    List<String> tbls = null;
    try {
      openTransaction();
      dbName = dbName.toLowerCase().trim();
      // Take the pattern and split it on the | to get all the composing
      // patterns
      String[] subpatterns = pattern.trim().split("\\|");
      String query =
        "select tableName from org.apache.hadoop.hive.metastore.model.MTable "
        + "where database.name == dbName && (";
      boolean first = true;
      for (String subpattern : subpatterns) {
        subpattern = "(?i)" + subpattern.replaceAll("\\*", ".*");
        if (!first) {
          query = query + " || ";
        }
        query = query + " tableName.matches(\"" + subpattern + "\")";
        first = false;
      }
      query = query + ")";

      Query q = pm.newQuery(query);
      q.declareParameters("java.lang.String dbName");
      q.setResult("tableName");
      q.setOrdering("tableName ascending");
      Collection names = (Collection) q.execute(dbName);
      tbls = new ArrayList<String>();
      for (Iterator i = names.iterator(); i.hasNext();) {
        tbls.add((String) i.next());
      }
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    return tbls;
  }

  public List<String> getAllTables(String dbName) throws MetaException {
    return getTables(dbName, ".*");
  }

  private MTable getMTable(String db, String table) {
    MTable mtbl = null;
    boolean commited = false;
    try {
      openTransaction();
      db = db.toLowerCase().trim();
      table = table.toLowerCase().trim();
      Query query = pm.newQuery(MTable.class, "tableName == table && database.name == db");
      query.declareParameters("java.lang.String table, java.lang.String db");
      query.setUnique(true);
      mtbl = (MTable) query.execute(table, db);
      pm.retrieve(mtbl);
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    return mtbl;
  }

  private Table convertToTable(MTable mtbl) throws MetaException {
    if (mtbl == null) {
      return null;
    }
    String tableType = mtbl.getTableType();
    if (tableType == null) {
      // for backwards compatibility with old metastore persistence
      if (mtbl.getViewOriginalText() != null) {
        tableType = TableType.VIRTUAL_VIEW.toString();
      } else if ("TRUE".equals(mtbl.getParameters().get("EXTERNAL"))) {
        tableType = TableType.EXTERNAL_TABLE.toString();
      } else {
        tableType = TableType.MANAGED_TABLE.toString();
      }
    }
    return new Table(mtbl.getTableName(), mtbl.getDatabase().getName(), mtbl
        .getOwner(), mtbl.getCreateTime(), mtbl.getLastAccessTime(), mtbl
        .getRetention(), convertToStorageDescriptor(mtbl.getSd()),
        convertToFieldSchemas(mtbl.getPartitionKeys()), mtbl.getParameters(),
        mtbl.getViewOriginalText(), mtbl.getViewExpandedText(),
        tableType);
  }

  private MTable convertToMTable(Table tbl) throws InvalidObjectException,
      MetaException {
    if (tbl == null) {
      return null;
    }
    MDatabase mdb = null;
    try {
      mdb = getMDatabase(tbl.getDbName());
    } catch (NoSuchObjectException e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new InvalidObjectException("Database " + tbl.getDbName()
          + " doesn't exist.");
    }

    // If the table has property EXTERNAL set, update table type
    // accordingly
    String tableType = tbl.getTableType();
    boolean isExternal = "TRUE".equals(tbl.getParameters().get("EXTERNAL"));
    if (TableType.MANAGED_TABLE.toString().equals(tableType)) {
      if (isExternal) {
        tableType = TableType.EXTERNAL_TABLE.toString();
      }
    }
    if (TableType.EXTERNAL_TABLE.toString().equals(tableType)) {
      if (!isExternal) {
        tableType = TableType.MANAGED_TABLE.toString();
      }
    }

    return new MTable(tbl.getTableName().toLowerCase(), mdb,
        convertToMStorageDescriptor(tbl.getSd()), tbl.getOwner(), tbl
            .getCreateTime(), tbl.getLastAccessTime(), tbl.getRetention(),
        convertToMFieldSchemas(tbl.getPartitionKeys()), tbl.getParameters(),
        tbl.getViewOriginalText(), tbl.getViewExpandedText(),
        tableType);
  }

  private List<MFieldSchema> convertToMFieldSchemas(List<FieldSchema> keys) {
    List<MFieldSchema> mkeys = null;
    if (keys != null) {
      mkeys = new ArrayList<MFieldSchema>(keys.size());
      for (FieldSchema part : keys) {
        mkeys.add(new MFieldSchema(part.getName().toLowerCase(),
            part.getType(), part.getComment()));
      }
    }
    return mkeys;
  }

  private List<FieldSchema> convertToFieldSchemas(List<MFieldSchema> mkeys) {
    List<FieldSchema> keys = null;
    if (mkeys != null) {
      keys = new ArrayList<FieldSchema>(mkeys.size());
      for (MFieldSchema part : mkeys) {
        keys.add(new FieldSchema(part.getName(), part.getType(), part
            .getComment()));
      }
    }
    return keys;
  }

  private List<MOrder> convertToMOrders(List<Order> keys) {
    List<MOrder> mkeys = null;
    if (keys != null) {
      mkeys = new ArrayList<MOrder>(keys.size());
      for (Order part : keys) {
        mkeys.add(new MOrder(part.getCol().toLowerCase(), part.getOrder()));
      }
    }
    return mkeys;
  }

  private List<Order> convertToOrders(List<MOrder> mkeys) {
    List<Order> keys = null;
    if (mkeys != null) {
      keys = new ArrayList<Order>();
      for (MOrder part : mkeys) {
        keys.add(new Order(part.getCol(), part.getOrder()));
      }
    }
    return keys;
  }

  private SerDeInfo converToSerDeInfo(MSerDeInfo ms) throws MetaException {
    if (ms == null) {
      throw new MetaException("Invalid SerDeInfo object");
    }
    return new SerDeInfo(ms.getName(), ms.getSerializationLib(), ms
        .getParameters());
  }

  private MSerDeInfo converToMSerDeInfo(SerDeInfo ms) throws MetaException {
    if (ms == null) {
      throw new MetaException("Invalid SerDeInfo object");
    }
    return new MSerDeInfo(ms.getName(), ms.getSerializationLib(), ms
        .getParameters());
  }

  // MSD and SD should be same objects. Not sure how to make then same right now
  // MSerdeInfo *& SerdeInfo should be same as well
  private StorageDescriptor convertToStorageDescriptor(MStorageDescriptor msd)
      throws MetaException {
    if (msd == null) {
      return null;
    }
    return new StorageDescriptor(convertToFieldSchemas(msd.getCols()), msd
        .getLocation(), msd.getInputFormat(), msd.getOutputFormat(), msd
        .isCompressed(), msd.getNumBuckets(), converToSerDeInfo(msd
        .getSerDeInfo()), msd.getBucketCols(), convertToOrders(msd
        .getSortCols()), msd.getParameters());
  }

  private MStorageDescriptor convertToMStorageDescriptor(StorageDescriptor sd)
      throws MetaException {
    if (sd == null) {
      return null;
    }
    return new MStorageDescriptor(convertToMFieldSchemas(sd.getCols()), sd
        .getLocation(), sd.getInputFormat(), sd.getOutputFormat(), sd
        .isCompressed(), sd.getNumBuckets(), converToMSerDeInfo(sd
        .getSerdeInfo()), sd.getBucketCols(),
        convertToMOrders(sd.getSortCols()), sd.getParameters());
  }

  public boolean addPartition(Partition part) throws InvalidObjectException,
      MetaException {
    boolean success = false;
    boolean commited = false;
    try {
      openTransaction();
      MPartition mpart = convertToMPart(part);
      pm.makePersistent(mpart);
      commited = commitTransaction();
      success = true;
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    return success;
  }

  public Partition getPartition(String dbName, String tableName,
      List<String> part_vals) throws NoSuchObjectException, MetaException {
    openTransaction();
    Partition part = convertToPart(getMPartition(dbName, tableName, part_vals));
    commitTransaction();
    if(part == null) {
        throw new NoSuchObjectException();
    }
    part.setValues(part_vals);
    return part;
  }

  private MPartition getMPartition(String dbName, String tableName,
      List<String> part_vals) throws MetaException {
    MPartition mpart = null;
    boolean commited = false;
    try {
      openTransaction();
      dbName = dbName.toLowerCase().trim();
      tableName = tableName.toLowerCase().trim();
      MTable mtbl = getMTable(dbName, tableName);
      if (mtbl == null) {
        commited = commitTransaction();
        return null;
      }
      // Change the query to use part_vals instead of the name which is
      // redundant
      String name = Warehouse.makePartName(convertToFieldSchemas(mtbl
          .getPartitionKeys()), part_vals);
      Query query = pm.newQuery(MPartition.class,
          "table.tableName == t1 && table.database.name == t2 && partitionName == t3");
      query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3");
      query.setUnique(true);
      mpart = (MPartition) query.execute(tableName, dbName, name);
      pm.retrieve(mpart);
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    return mpart;
  }

  private MPartition convertToMPart(Partition part)
      throws InvalidObjectException, MetaException {
    if (part == null) {
      return null;
    }
    MTable mt = getMTable(part.getDbName(), part.getTableName());
    if (mt == null) {
      throw new InvalidObjectException(
          "Partition doesn't have a valid table or database name");
    }
    return new MPartition(Warehouse.makePartName(convertToFieldSchemas(mt
        .getPartitionKeys()), part.getValues()), mt, part.getValues(), part
        .getCreateTime(), part.getLastAccessTime(),
        convertToMStorageDescriptor(part.getSd()), part.getParameters());
  }

  private Partition convertToPart(MPartition mpart) throws MetaException {
    if (mpart == null) {
      return null;
    }
    return new Partition(mpart.getValues(), mpart.getTable().getDatabase()
        .getName(), mpart.getTable().getTableName(), mpart.getCreateTime(),
        mpart.getLastAccessTime(), convertToStorageDescriptor(mpart.getSd()),
        mpart.getParameters());
  }

  public boolean dropPartition(String dbName, String tableName,
      List<String> part_vals) throws MetaException {
    boolean success = false;
    try {
      openTransaction();
      MPartition part = getMPartition(dbName, tableName, part_vals);
      if (part != null) {
        pm.deletePersistent(part);
      }
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return success;
  }

  public List<Partition> getPartitions(String dbName, String tableName, int max)
      throws MetaException {
    openTransaction();
    List<Partition> parts = convertToParts(listMPartitions(dbName, tableName,
        max));
    commitTransaction();
    return parts;
  }

  private List<Partition> convertToParts(List<MPartition> mparts)
      throws MetaException {
    List<Partition> parts = new ArrayList<Partition>(mparts.size());
    for (MPartition mp : mparts) {
      parts.add(convertToPart(mp));
    }
    return parts;
  }

  // TODO:pc implement max
  @SuppressWarnings("rawtypes")
  public List<String> listPartitionNames(String dbName, String tableName,
      short max) throws MetaException {
    List<String> pns = new ArrayList<String>();
    boolean success = false;
    try {
      openTransaction();
      LOG.debug("Executing getPartitionNames");
      dbName = dbName.toLowerCase().trim();
      tableName = tableName.toLowerCase().trim();
      Query q = pm.newQuery(
          "select partitionName from org.apache.hadoop.hive.metastore.model.MPartition "
          + "where table.database.name == t1 && table.tableName == t2 "
          + "order by partitionName asc");
      q.declareParameters("java.lang.String t1, java.lang.String t2");
      q.setResult("partitionName");
      Collection names = (Collection) q.execute(dbName, tableName);
      pns = new ArrayList<String>();
      for (Iterator i = names.iterator(); i.hasNext();) {
        pns.add((String) i.next());
      }
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return pns;
  }

  // TODO:pc implement max
  @SuppressWarnings("unchecked")
  private List<MPartition> listMPartitions(String dbName, String tableName,
      int max) {
    boolean success = false;
    List<MPartition> mparts = null;
    try {
      openTransaction();
      LOG.debug("Executing listMPartitions");
      dbName = dbName.toLowerCase().trim();
      tableName = tableName.toLowerCase().trim();
      Query query = pm.newQuery(MPartition.class,
          "table.tableName == t1 && table.database.name == t2");
      query.declareParameters("java.lang.String t1, java.lang.String t2");
      mparts = (List<MPartition>) query.execute(tableName, dbName);
      LOG.debug("Done executing query for listMPartitions");
      pm.retrieveAll(mparts);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listMPartitions");
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return mparts;
  }

  public void alterTable(String dbname, String name, Table newTable)
      throws InvalidObjectException, MetaException {
    boolean success = false;
    try {
      openTransaction();
      name = name.toLowerCase();
      dbname = dbname.toLowerCase();
      MTable newt = convertToMTable(newTable);
      if (newt == null) {
        throw new InvalidObjectException("new table is invalid");
      }

      MTable oldt = getMTable(dbname, name);
      if (oldt == null) {
        throw new MetaException("table " + name + " doesn't exist");
      }

      // For now only alter name, owner, paramters, cols, bucketcols are allowed
      oldt.setTableName(newt.getTableName().toLowerCase());
      oldt.setParameters(newt.getParameters());
      oldt.setOwner(newt.getOwner());
      oldt.setSd(newt.getSd());
      oldt.setDatabase(newt.getDatabase());
      oldt.setRetention(newt.getRetention());
      oldt.setPartitionKeys(newt.getPartitionKeys());
      oldt.setTableType(newt.getTableType());

      // commit the changes
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
  }

  public void alterPartition(String dbname, String name, Partition newPart)
      throws InvalidObjectException, MetaException {
    boolean success = false;
    try {
      openTransaction();
      name = name.toLowerCase();
      dbname = dbname.toLowerCase();
      MPartition oldp = getMPartition(dbname, name, newPart.getValues());
      MPartition newp = convertToMPart(newPart);
      if (oldp == null || newp == null) {
        throw new InvalidObjectException("partition does not exist.");
      }
      oldp.setParameters(newPart.getParameters());
      copyMSD(newp.getSd(), oldp.getSd());
      if (newp.getCreateTime() != oldp.getCreateTime()) {
        oldp.setCreateTime(newp.getCreateTime());
      }
      if (newp.getLastAccessTime() != oldp.getLastAccessTime()) {
        oldp.setLastAccessTime(newp.getLastAccessTime());
      }
      // commit the changes
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
  }

  private void copyMSD(MStorageDescriptor newSd, MStorageDescriptor oldSd) {
    oldSd.setLocation(newSd.getLocation());
    oldSd.setCols(newSd.getCols());
    oldSd.setBucketCols(newSd.getBucketCols());
    oldSd.setCompressed(newSd.isCompressed());
    oldSd.setInputFormat(newSd.getInputFormat());
    oldSd.setOutputFormat(newSd.getOutputFormat());
    oldSd.setNumBuckets(newSd.getNumBuckets());
    oldSd.getSerDeInfo().setName(newSd.getSerDeInfo().getName());
    oldSd.getSerDeInfo().setSerializationLib(
        newSd.getSerDeInfo().getSerializationLib());
    oldSd.getSerDeInfo().setParameters(newSd.getSerDeInfo().getParameters());
  }

  public User getUser(String userName) throws NoSuchObjectException {
    boolean commited = false;
    User user = null;
    try {
      openTransaction();
      user = convertToUser(getMUser(userName));
      commited = commitTransaction();
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    return user;
  }

  private MUser getMUser(String name) throws NoSuchObjectException {
    if(name == null) {
      return null;
    }
    MUser mUser = null;
    boolean commited = false;
    try {
      openTransaction();
      name = name.toLowerCase();
      Query query = pm.newQuery(MUser.class, "userName == name");
      query.declareParameters("java.lang.String name");
      query.setUnique(true);
      mUser = (MUser) query.execute(name.trim());
      pm.retrieve(mUser);
      commited = commitTransaction();
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    if(mUser == null) {
      throw new NoSuchObjectException("There is no user named " + name);
    }
    return mUser;
  }

  public boolean dropUser(String userName) throws NoSuchObjectException {

    boolean success = false;
    try {
      openTransaction();
      MUser mUser = getMUser(userName);
      pm.retrieve(mUser);
      if(mUser != null) {
        // first remove all the DB privilege
        pm.deletePersistentAll(listMDbPrivByUser(userName));
        // then remove table's privilege
        pm.deletePersistentAll(listMTbPrivByUser(userName));
        // then remove the user
        pm.deletePersistent(mUser);
      }
      success = commitTransaction();
    } finally {
      if(!success) {
        rollbackTransaction();
      }
    }
    return success;
  }

  public void alterUser(String userName, User newUser) throws InvalidObjectException, NoSuchObjectException, MetaException {
    boolean success = false;
    try {
      openTransaction();
      userName = userName.toLowerCase();
      MUser newu = convertToMUser(newUser);
      if(newu == null) {
        throw new InvalidObjectException("new user is invalid");
      }

      MUser oldu = this.getMUser(userName);
      if(oldu == null) {
        throw new MetaException("user " + userName + " doesn't exist");
      }

      oldu.setUserName(newu.getUserName().toLowerCase());
      oldu.setPassword(newu.getPassword());
      oldu.setSelect_priv(newu.isSelect_priv());
      oldu.setInsert_priv(newu.isInsert_priv());
      oldu.setDrop_priv(newu.isDrop_priv());
      oldu.setGrant_priv(newu.isGrant_priv());
      oldu.setAlter_priv(newu.isAlter_priv());
      oldu.setCreate_priv(newu.isCreate_priv());
      oldu.setCreate_user_priv(newu.isCreate_user_priv());
      oldu.setSuper_priv(newu.isSuper_priv());

      success = commitTransaction();
    } finally {
      if(!success) {
        rollbackTransaction();
      }
    }
  }

  private MUser convertToMUser(User user) {
    if(user == null) {
      return null;
    }
    return new MUser(user.getName(),
        user.getPassword(),
        user.isSelect_priv(),
        user.isCreate_priv(),
        user.isInsert_priv(),
        user.isDrop_priv(),
        user.isGrant_priv(),
        user.isAlter_priv(),
        user.isCreate_user_priv(),
        user.isSuper_priv());
  }

  private User convertToUser(MUser mUser) {
    if(mUser == null) {
      return null;
    }
    return new User(mUser.getUserName(),
        mUser.getPassword(),
        mUser.isSelect_priv(),
        mUser.isCreate_priv(),
        mUser.isInsert_priv(),
        mUser.isDrop_priv(),
        mUser.isGrant_priv(),
        mUser.isAlter_priv(),
        mUser.isCreate_user_priv(),
        mUser.isSuper_priv());
  }

  public boolean createUser(User user) {
    boolean success = false;
    boolean commited = false;
    MUser muser = new MUser(user.getName().toLowerCase(), user.getPassword(), user.isSelect_priv(),
        user.isInsert_priv(), user.isCreate_priv(), user.isDrop_priv(), user.isGrant_priv(),
        user.isAlter_priv(), user.isCreate_user_priv(), user.isSuper_priv());
    try {
      openTransaction();
      pm.makePersistent(muser);
      commited = commitTransaction();
      success = true;
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    return success;
  }

  private MDbPriv getMDbPriv(String name, String db) {
    MDbPriv mDbPriv = null;
    boolean commited = false;
    try {
      openTransaction();
      name = name.toLowerCase();
      db = db.toLowerCase();
      Query query = pm.newQuery(MDbPriv.class, "user.userName == t1 && db.name == t2");
      query.declareParameters("java.lang.String t1, java.lang.String t2");
      query.setUnique(true);
      mDbPriv = (MDbPriv) query.execute(name.trim(), db.trim());
      pm.retrieve(mDbPriv);
      commited = commitTransaction();
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    return mDbPriv;
  }

  public DbPriv getDbPriv(String name, String db) throws MetaException, NoSuchObjectException {
    boolean commited = false;
    DbPriv dbPriv = null;
    try {
      openTransaction();
      dbPriv = convertToDbPriv(getMDbPriv(name, db));
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    if (dbPriv == null) {
      throw new NoSuchObjectException(String.format("User %s has no privilege on database %s",
          name, db));
    }
    return dbPriv;
  }

  public List<DbPriv> getDbPrivs(String db) throws MetaException, NoSuchObjectException {
    boolean commited = false;
    List<DbPriv> dbPrivs = new ArrayList<DbPriv>();
    List<MDbPriv> mDbPrivs = null;
    try {
      openTransaction();
      mDbPrivs = listMDbPrivByDB(db);
      commited = commitTransaction();
    } finally {
      if (!commited) {
        rollbackTransaction();
      }
    }
    if (mDbPrivs == null) {
      throw new NoSuchObjectException(String.format("No privilege record on database %s", db));
    }
    for (MDbPriv mDbPriv : mDbPrivs) {
      dbPrivs.add(convertToDbPriv(mDbPriv));
    }
    return dbPrivs;
  }

  public boolean grantDbPriv(DbPriv dbPriv) throws MetaException, InvalidObjectException {
    boolean commited = false;
    try {
      openTransaction();
      MDbPriv dbp = convertToMDbPriv(dbPriv);
      pm.makePersistent(dbp);
      commited = commitTransaction();
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    return commited;
  }

  public void alterDbPriv(String userName, String db, DbPriv newDbPriv)
      throws InvalidObjectException, NoSuchObjectException, MetaException {
    boolean success = false;
    try {
      openTransaction();
      userName = userName.toLowerCase();
      MDbPriv newu = convertToMDbPriv(newDbPriv);
      if (this.getUser(userName) == null) {
        throw new NoSuchObjectException("User " + userName + " doesn't exist");
      } else if (this.getDatabase(db) == null) {
        throw new NoSuchObjectException("Database " + db + " doesn't exist");
      }
      if (newu == null) {
        throw new InvalidObjectException("new database privilege is invalid");
      }
      MDbPriv oldDbPriv = this.getMDbPriv(userName, db);
      if (oldDbPriv != null) {
        pm.deletePersistent(oldDbPriv);
      }

      if (newu.isCreate_priv() ||
          newu.isAlter_priv() ||
          newu.isDrop_priv() ||
          newu.isGrant_priv() ||
          newu.isInsert_priv() ||
          newu.isSelect_priv()) {
        pm.makePersistent(newu);
      }

      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
  }

  public boolean revokeDbPriv(String userName, String db) throws NoSuchObjectException {
    boolean success = false;
    try {
      openTransaction();
      MDbPriv mdbpriv = getMDbPriv(userName, db);
      pm.retrieve(mdbpriv);
      if (mdbpriv == null) {
        throw new NoSuchObjectException(String.format("User %s has no privilege on database %s",
              userName, db));
      }
      pm.deletePersistent(mdbpriv);
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return success;
  }

  private DbPriv convertToDbPriv(MDbPriv mdbp) throws MetaException {
    if(mdbp == null) {
      return null;
    }
    return new DbPriv(mdbp.getUser().getUserName(),
        mdbp.getDb().getName(),
        mdbp.isSelect_priv(),
        mdbp.isInsert_priv(),
        mdbp.isDrop_priv(),
        mdbp.isGrant_priv(),
        mdbp.isAlter_priv(),
        mdbp.isCreate_priv());
  }

  private MDbPriv convertToMDbPriv(DbPriv dbp) throws InvalidObjectException, MetaException {
    if(dbp == null) {
      return null;
    }
    MDatabase mdb = null;
    MUser user = null;
    try {
      mdb = this.getMDatabase(dbp.getDbName().toLowerCase());
      user = this.getMUser(dbp.getUserName().toLowerCase());
    } catch (NoSuchObjectException e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new InvalidObjectException("Database " + dbp.getDbName() + " or user " + dbp.getUserName() + " doesn't exsit.");
    }
    return new MDbPriv(mdb,
        user,
        dbp.isSelect_priv(),
        dbp.isInsert_priv(),
        dbp.isDrop_priv(),
        dbp.isGrant_priv(),
        dbp.isAlter_priv(),
        dbp.isCreate_priv());
  }

  private MTbPriv getMTbPriv(String name, String db, String tb) {
    MTbPriv mTbPriv = null;
    boolean commited = false;
    try {
      openTransaction();
      name = name.toLowerCase();
      db = db.toLowerCase();
      tb = tb.toLowerCase();
      Query query = pm.newQuery(MTbPriv.class, "user.userName == t1 && db.name == t2 && table.tableName == t3");
      query.declareParameters("java.lang.String t1, java.lang.String t2, java.lang.String t3");
      query.setUnique(true);
      mTbPriv = (MTbPriv) query.execute(name.trim(), db.trim(), tb.trim());
      pm.retrieve(mTbPriv);
      commited = commitTransaction();
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    return mTbPriv;
  }

  public TbPriv getTbPriv(String userName, String db, String tb) throws MetaException, NoSuchObjectException {
    boolean commited = false;
    TbPriv tbPriv = null;
    try {
      openTransaction();
      tbPriv = convertToTbPriv(getMTbPriv(userName, db, tb));
      commited = commitTransaction();
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    if (tbPriv == null) {
      throw new NoSuchObjectException(String.format("User %s has no privilege on %s.%s", userName, db, tb));
    }
    return tbPriv;
  }

  public List<TbPriv> getTbPrivs(String db, String tb) throws MetaException, NoSuchObjectException {
    boolean commited = false;
    List<TbPriv> tbPrivs = new ArrayList<TbPriv>();
    List<MTbPriv> mTbPrivs = null;
    try {
      openTransaction();
      mTbPrivs = listMTbPrivByTable(db, tb);
      commited = commitTransaction();
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    if (mTbPrivs == null) {
      throw new NoSuchObjectException(String.format("No privilege on %s.%s", db, tb));
    }
    for (MTbPriv mTbPriv : mTbPrivs) {
      tbPrivs.add(convertToTbPriv(mTbPriv));
    }
    return tbPrivs;
  }

  public boolean grantTbPriv(TbPriv tbPriv) throws InvalidObjectException, MetaException {
    boolean commited = false;
    try {
      openTransaction();
      MTbPriv tbp = convertToMTbPriv(tbPriv);
      pm.makePersistent(tbp);
      commited = commitTransaction();
    } finally {
      if(!commited) {
        rollbackTransaction();
      }
    }
    return commited;
  }

  public void alterTbPriv(String userName, String db, String tbName, TbPriv newTbPriv)
      throws InvalidObjectException, NoSuchObjectException, MetaException {
    boolean success = false;
    try {
      openTransaction();
      userName = userName.toLowerCase();
      db = db.toLowerCase();
      tbName = tbName.toLowerCase();
      if (this.getUser(userName) == null) {
        throw new NoSuchObjectException("User " + userName + " doesn't exist");
      } else if (this.getDatabase(db) == null) {
        throw new NoSuchObjectException("Database " + db + " doesn't exist");
      } else if (this.getTable(db, tbName) == null) {
        throw new NoSuchObjectException("Table " + tbName + " doesn't exist");
      }

      MTbPriv newt = convertToMTbPriv(newTbPriv);
      if (newt == null) {
        throw new InvalidObjectException("new database privilege is invalid");
      }
      MTbPriv oldTbPriv = this.getMTbPriv(userName, db, tbName);
      if (oldTbPriv != null) {
        pm.deletePersistent(oldTbPriv);
      }

      if (newt.isAlter_priv() ||
          newt.isDrop_priv() ||
          newt.isGrant_priv() ||
          newt.isInsert_priv() ||
          newt.isSelect_priv()) {
        pm.makePersistent(newt);
      }
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
  }

  public boolean revokeTbPriv(String userName, String db, String tbName)
      throws NoSuchObjectException {
    boolean success = false;
    try {
      openTransaction();
      MTbPriv mtbpriv = getMTbPriv(userName, db, tbName);
      pm.retrieve(mtbpriv);
      if (mtbpriv == null) {
        throw new NoSuchObjectException(String.format("User %s has no privilege on table %s.%s",
            userName, db, tbName));
      }
      pm.deletePersistent(mtbpriv);
      success = commitTransaction();
    } finally {
      if (!success) {
        rollbackTransaction();
      }
    }
    return success;
  }

  @SuppressWarnings("unchecked")
  private List<MDbPriv> listMDbPrivByUser(String userName) {
    boolean success = false;
    List<MDbPriv> mDbPrivs = null;
    try {
      openTransaction();
      LOG.debug("Executing listMDbPrivByUser");
      userName = userName.toLowerCase();
      Query query = pm.newQuery(MDbPriv.class, "user.userName == t1");
      query.declareParameters("java.lang.String t1");
      mDbPrivs = (List<MDbPriv>) query.execute(userName.trim());
      LOG.debug("Done executing query for listMDbPrivByUser");
      pm.retrieveAll(mDbPrivs);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listMDbPrivByUser");
    } finally {
      if(!success) {
        rollbackTransaction();
      }
    }
    return mDbPrivs;
  }

  @SuppressWarnings("unchecked")
  private List<MTbPriv> listMTbPrivByUser(String userName) {
    boolean success = false;
    List<MTbPriv> mTbPrivs = null;
    try {
      openTransaction();
      LOG.debug("Executing listMTbPrivByUser");
      userName = userName.toLowerCase();
      Query query = pm.newQuery(MTbPriv.class, "user.userName == t1");
      query.declareParameters("java.lang.String t1");
      mTbPrivs = (List<MTbPriv>) query.execute(userName.trim());
      LOG.debug("Done executing query for listMTbPrivByUser");
      pm.retrieveAll(mTbPrivs);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listMTbPrivByUser");
    } finally {
      if(!success) {
        rollbackTransaction();
      }
    }
    return mTbPrivs;
  }

  @SuppressWarnings("unchecked")
  private List<MTbPriv> listMTbPrivByTable(String db, String tableName) {
  boolean success = false;
    List<MTbPriv> mTbPrivs = null;
    try {
      openTransaction();
      LOG.debug("Executing listMTbPrivByUser");
      db = db.toLowerCase();
      tableName = tableName.toLowerCase();
      Query query = pm.newQuery(MTbPriv.class, "table.tableName == t1 && db.name == t2");
      query.declareParameters("java.lang.String t1, java.lang.String t2");
      mTbPrivs = (List<MTbPriv>) query.execute(tableName.trim(), db.trim());
      LOG.debug("Done executing query for listMTbPrivByTable");
      pm.retrieveAll(mTbPrivs);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listMTbPrivByTable");
    } finally {
      if(!success) {
        rollbackTransaction();
      }
    }
    return mTbPrivs;
  }

  @SuppressWarnings("unchecked")
  private List<MDbPriv> listMDbPrivByDB(String dbName) {
        boolean success = false;
    List<MDbPriv> mDbPrivs = null;
    try {
      openTransaction();
      LOG.debug("Executing listMTbPrivByUser");
      dbName = dbName.toLowerCase();
      Query query = pm.newQuery(MDbPriv.class, "db.name == t1");
      query.declareParameters("java.lang.String t1");
      mDbPrivs = (List<MDbPriv>) query.execute(dbName.trim());
      LOG.debug("Done executing query for listMDbPrivByDB");
      pm.retrieveAll(mDbPrivs);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listMDbPrivByDB");
    } finally {
      if(!success) {
        rollbackTransaction();
      }
    }
    return mDbPrivs;
  }

  @SuppressWarnings("unchecked")
  private List<MTbPriv> listMTbPrivByDB(String dbName) {
  boolean success = false;
    List<MTbPriv> mTbPrivs = null;
    try {
      openTransaction();
      LOG.debug("Executing listMTbPrivByUser");
      dbName = dbName.toLowerCase();
      Query query = pm.newQuery(MTbPriv.class, "db.name == t1");
      query.declareParameters("java.lang.String t1");
      mTbPrivs = (List<MTbPriv>) query.execute(dbName.trim());
      LOG.debug("Done executing query for listMPartitions");
      pm.retrieveAll(mTbPrivs);
      success = commitTransaction();
      LOG.debug("Done retrieving all objects for listMPartitions");
    } finally {
      if(!success) {
        rollbackTransaction();
      }
    }
    return mTbPrivs;
  }

  private TbPriv convertToTbPriv(MTbPriv mtbp) throws MetaException {
    if(mtbp == null) {
      return null;
    }
    return new TbPriv(mtbp.getUser().getUserName().toLowerCase(),
        mtbp.getDb().getName().toLowerCase(),
        mtbp.getTableName().getTableName().toLowerCase(),
        mtbp.getGrantor().getUserName().toLowerCase(),
        mtbp.getTimeStamp(),
        mtbp.isSelect_priv(),
        mtbp.isInsert_priv(),
        mtbp.isDrop_priv(),
        mtbp.isGrant_priv(),
        mtbp.isAlter_priv());
  }

  private MTbPriv convertToMTbPriv(TbPriv tbp) throws InvalidObjectException, MetaException {
    if(tbp == null) {
      return null;
    }
    MDatabase mdb = null;
    MUser user = null;
    MUser grantor = null;
    MTable tb = null;
    try {
      mdb = this.getMDatabase(tbp.getDbName());
      user = this.getMUser(tbp.getUserName());
      grantor = this.getMUser(tbp.getGrantor());
    } catch (NoSuchObjectException e) {
      LOG.error(StringUtils.stringifyException(e));
      throw new InvalidObjectException(e.getMessage());
    }
    tb = this.getMTable(mdb.getName(), tbp.getTbName());
    return new MTbPriv(mdb, user, tb, grantor,
        tbp.getTimeStamp(),
        tbp.isSelect_priv(),
        tbp.isInsert_priv(),
        tbp.isDrop_priv(),
        tbp.isGrant_priv(),
        tbp.isAlter_priv());
  }
}
