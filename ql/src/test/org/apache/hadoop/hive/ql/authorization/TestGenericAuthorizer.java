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
package org.apache.hadoop.hive.ql.authorization;

import junit.framework.TestCase;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.MetaStoreUtils;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.metadata.User;

public class TestGenericAuthorizer extends TestCase {

  static final private String DEFAULT_DATABASE = "default_db";
  static final private String DEFAULT_TABLE = "default_table";

  private Authorizer authorizer;
  private HiveConf conf;
  private User user;
  private Hive db;
  private Database database;
  private Table tbl;

  @Override
  protected void setUp() throws Exception {
    conf = new HiveConf(this.getClass());
    db = Hive.get(conf);
    authorizer = new GenericAuthorizer();
    authorizer.setConf(conf);
    database = new Database();
    database.setName(DEFAULT_DATABASE);
    org.apache.hadoop.hive.metastore.api.Table tTable =
        new org.apache.hadoop.hive.metastore.api.Table(
            DEFAULT_TABLE,
            DEFAULT_DATABASE,
            MetaStoreUtils.DEFAULT_SUPER_USER_NAME,
            0,
            0,
            0,
            null,
            null,
            null,
            null,
            null,
            "VIRTUAL_VIEW");
    Table tbl1 = new Table(DEFAULT_DATABASE, DEFAULT_TABLE);
    tbl = new Table(tTable);
    db.createDatabase(database, true);
    db.createTable(tbl1, true);
  }

  @Override
  protected void tearDown() throws Exception {

  }

  private void assertAuthorizeFail(Authorizer authorizer, AuthorizeEntry entry) {
    try {
      authorizer.authorize(user.getName(), entry);
      fail();
    } catch (AuthorizeException e) {
    }
  }

  public void testSuperUser() {
    try {
      user = db.getUser(MetaStoreUtils.DEFAULT_SUPER_USER_NAME);;
      // Drop/Create user.getName(), SetPassword
      AuthorizeEntry entry = new AuthorizeEntry(null, null, null,
          Privilege.CREATE_PRIV, PrivilegeLevel.GLOBAL_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Create/Drop Database
      entry = new AuthorizeEntry(null, null, null,
          Privilege.SUPER_PRIV, PrivilegeLevel.GLOBAL_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Create Table
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.CREATE_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Drop Table
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.DROP_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Alter Table
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.ALTER_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Insert
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.INSERT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Select
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.SELECT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);

      // Grant / Revoke
      entry = new AuthorizeEntry(null, null, null,
          Privilege.GRANT_PRIV, PrivilegeLevel.GLOBAL_LEVEL);
      authorizer.authorize(user.getName(), entry);
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.GRANT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  public void testGlobalPrivilege() {
    try {
      // create a new user with selected global privileges
      String userName = "test";
      db.dropUser(userName);
      db.createUser(userName, new byte[] {});
      user = db.getUser(userName);
      AuthorizeEntry entry = null;
      // Drop/Create user.getName(), SetPassword
      entry = new AuthorizeEntry(null, null, null,
            Privilege.CREATE_USER_PRIV, PrivilegeLevel.GLOBAL_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Create/Drop Database
      entry = new AuthorizeEntry(null, null, null,
            Privilege.SUPER_PRIV, PrivilegeLevel.GLOBAL_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Create Table
      entry = new AuthorizeEntry(null, null, null,
            Privilege.CREATE_PRIV, PrivilegeLevel.DATABASE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Drop Table
      entry = new AuthorizeEntry(null, tbl, null,
            Privilege.DROP_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Alter Table
      entry = new AuthorizeEntry(null, tbl, null,
            Privilege.ALTER_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Insert
      entry = new AuthorizeEntry(null, tbl, null,
            Privilege.INSERT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Select
      entry = new AuthorizeEntry(null, tbl, null,
            Privilege.SELECT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Grant / Revoke
      entry = new AuthorizeEntry(null, null, null,
            Privilege.GRANT_PRIV, PrivilegeLevel.GLOBAL_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      entry = new AuthorizeEntry(null, tbl, null,
            Privilege.GRANT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);

      db.addGlobalPriv(userName, PrivilegeLevel.GLOBAL_LEVEL.getPrivileges());
      // Refresh user object
      user = db.getUser(userName);

      // Create Table
      entry = new AuthorizeEntry(database, null, null,
          Privilege.CREATE_PRIV, PrivilegeLevel.DATABASE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Drop Table
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.DROP_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Alter Table
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.ALTER_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Insert
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.INSERT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Select
      entry = new AuthorizeEntry(null, tbl, null,
          Privilege.SELECT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);

      db.dropUser(userName);
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  public void testDatabasePrivilege() {
    try {
      // create a new user with selected table privileges
      String userName = "test";
      db.dropUser(userName);
      db.createUser(userName, new byte[] {});
      user = db.getUser(userName);
      AuthorizeEntry entry = null;

      // Create Table
      entry = new AuthorizeEntry(database, null, null,
          Privilege.CREATE_PRIV, PrivilegeLevel.DATABASE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Drop Table
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.DROP_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Alter Table
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.ALTER_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Insert
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.INSERT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Select
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.SELECT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);

      db.addDatabasePriv(userName, database.getName(),
          PrivilegeLevel.DATABASE_LEVEL.getPrivileges());

      // Create Table
      entry = new AuthorizeEntry(database, null, null,
          Privilege.CREATE_PRIV, PrivilegeLevel.DATABASE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Drop Table
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.DROP_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Alter Table
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.ALTER_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Insert
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.INSERT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Select
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.SELECT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);

      db.dropUser(userName);
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  public void testTablePrivilege() {
    try {
      // create a new user with selected table privileges
      String userName = "test";
      db.dropUser(userName);
      db.createUser(userName, new byte[] {});
      user = db.getUser(userName);
      AuthorizeEntry entry = null;

      // Drop Table
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.DROP_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Alter Table
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.ALTER_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Insert
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.INSERT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);
      // Select
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.SELECT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      assertAuthorizeFail(authorizer, entry);

      tbl = db.getTable(tbl.getDbName(), tbl.getTableName());
      assertNotNull(tbl);
      db.addTablePriv(userName, tbl.getDbName(), tbl.getTableName(), "root",
          PrivilegeLevel.TABLE_LEVEL.getPrivileges());

      // Drop Table
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.DROP_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Alter Table
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.ALTER_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Insert
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.INSERT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);
      // Select
      entry = new AuthorizeEntry(database, tbl, null,
          Privilege.SELECT_PRIV, PrivilegeLevel.TABLE_LEVEL);
      authorizer.authorize(user.getName(), entry);

      db.dropUser(userName);
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }
}
