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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.InvalidOperationException;
import org.apache.hadoop.hive.ql.metadata.DbPriv;
import org.apache.hadoop.hive.ql.metadata.Hive;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.metadata.TbPriv;
import org.apache.hadoop.hive.ql.metadata.User;

/**
 * Used to check privileges of an hive account.
 */
public class GenericAuthorizer implements Authorizer {
  public static final Log LOG = LogFactory.getLog(GenericAuthorizer.class.getName());

  private Configuration conf;
  private Hive hive;

  public GenericAuthorizer() {
  }

  @Override
  public void authorize(String userName, AuthorizeEntry ctx) throws AuthorizeException {
    // Super user first
    // global level
    if (ctx == null) {
      return;
    }
    Collection<Privilege> privs = ctx.getRequiredPrivs();
    int privLevel = ctx.getPrivLevel().getLevel();
    try {
      if (hive.checkGlobalPriv(userName, privs)) {
        return;
      }
    } catch (HiveException e) {
      LOG.info("Error when checking global privilege", e);
      throw new AuthorizeException("Error when checking global privilege", e);
    }
    if (privLevel <= PrivilegeLevel.GLOBAL_LEVEL.getLevel()) {
      throw new AuthorizeException("Required access " + StringUtils.join(privs, ',') + " denied.");
    }

    // db level
    Database db = ctx.getDb();
    String dbName = db == null ? hive.getCurrentDatabase() : db.getName();
    try {
      if (hive.checkDbPriv(userName, dbName, privs)) {
        return;
      }
    } catch (HiveException e) {
      LOG.info("Error when checking privilege on database " + dbName, e);
      throw new AuthorizeException("Error when checking privilege on database " + dbName, e);
    }
    if (privLevel <= PrivilegeLevel.DATABASE_LEVEL.getLevel()) {
      throw new AuthorizeException("Required access " + StringUtils.join(privs, ',')
          + " denied for database "
          + dbName);
    }

    // table/partition level
    Table tbl = ctx.getTable();
    // In case a drop table operation, while table not exists.
    if (tbl == null) {
      return;
    }
    try {
      if (hive.checkTbPriv(userName, dbName, tbl.getTableName(), privs)) {
        return;
      }
    } catch (HiveException e) {
      LOG.info("Error when checking privilege on table " + dbName + "." + tbl.getTableName(),
          e);
      throw new AuthorizeException("Error when checking privilege on table " + dbName
          + "." + tbl.getTableName(), e);
    }
    if (privLevel <= PrivilegeLevel.TABLE_LEVEL.getLevel()) {
      throw new AuthorizeException("Required access " + StringUtils.join(privs, ',')
          + " denied for table "
          + dbName + "." + tbl.getTableName());
    }

    // column level
    // Not supported yet...
    throw new AuthorizeException("Required access " + StringUtils.join(privs, ',')
        + " denied for table "
        + dbName + "." + tbl.getTableName());
  }

  @Override
  public Configuration getConf() {
    return conf;
  }

  @Override
  public void setConf(Configuration conf) {
    this.conf = conf;
    try {
      this.hive = Hive.get((HiveConf) conf);
    } catch (HiveException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void grant(String userName, String grantorName, AuthorizeEntry ctx)
      throws AuthorizeException {
    Collection<Privilege> privs = ctx.getRequiredPrivs();

    try {
      switch (ctx.getPrivLevel()) {
      case GLOBAL_LEVEL:
        hive.addGlobalPriv(userName, privs);
        break;
      case DATABASE_LEVEL:
        if (!hive.checkGlobalPriv(userName, privs)) {
          hive.addDatabasePriv(userName, ctx.getDb().getName(), privs);
        }
        break;
      case TABLE_LEVEL:
        String dbName = ctx.getTable().getDbName();
        if (dbName == null) {
          dbName = hive.getCurrentDatabase();
        }
        if (!hive.checkGlobalPriv(userName, privs) &&
            !hive.checkDbPriv(userName, dbName, privs)) {
          hive.addTablePriv(userName, dbName,
              ctx.getTable().getTableName(), grantorName, privs);
        }
        break;
      case COLUMN_LEVEL:
        // Not supported...
        break;
      default:

      }
    } catch (InvalidOperationException e) {
      e.printStackTrace();
    } catch (HiveException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new AuthorizeException(e);
    }
  }

  @Override
  public void revoke(String userName, String revokerName, AuthorizeEntry ctx)
      throws AuthorizeException {
    if (revokerName == null) {
      return;
    }

    Collection<Privilege> privs = ctx.getRequiredPrivs();

    try {
      switch (ctx.getPrivLevel()) {
      case GLOBAL_LEVEL:
        hive.revokeGlobalPriv(userName, privs);
        break;
      case DATABASE_LEVEL:
        hive.revokeDatabasePriv(userName, ctx.getDb().getName(), privs);
        break;
      case TABLE_LEVEL:
        String dbName = ctx.getTable().getDbName();
        if (dbName == null) {
          dbName = hive.getCurrentDatabase();
        }
        hive.revokeTablePriv(userName, dbName,
            ctx.getTable().getTableName(), privs);
        break;
      case COLUMN_LEVEL:
        // Not supported...
        break;
      default:

      }
    } catch (InvalidOperationException e) {
      e.printStackTrace();
    } catch (HiveException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new AuthorizeException(e);
    }
  }

  @Override
  public List<AuthorizeEntry> getAllPrivileges(String userName) throws AuthorizeException {
    List<AuthorizeEntry> entries = new ArrayList<AuthorizeEntry>();
    try {
      // Global privilege
      User user = hive.getUser(userName);
      entries.add(new AuthorizeEntry(null, null, null, user.getAllPriv(), PrivilegeLevel.GLOBAL_LEVEL));

      // Database privilege
      List<String> dbs = hive.getAllDatabases();
      for (String dbName : dbs) {
        Database db = hive.getDatabase(dbName);
        DbPriv dbPriv = hive.getDbPriv(userName, dbName);
        if (dbPriv != null) {
          entries.add(new AuthorizeEntry(db, null, null,
              dbPriv.getAllPriv(), PrivilegeLevel.DATABASE_LEVEL));
        }
        // Table privilege
        List<String> tbls = hive.getTablesForUser(userName, dbName);
        for (String tbName : tbls) {
          Table tbl = hive.getTable(dbName, tbName);
          TbPriv tbPriv = hive.getTbPriv(userName, dbName, tbName);
          if (tbPriv != null) {
            entries.add(new AuthorizeEntry(db, tbl, null, tbPriv.getAllPriv(),
                PrivilegeLevel.TABLE_LEVEL));
          }
        }
      }
    } catch (HiveException e) {
      e.printStackTrace();
    }
    return entries;
  }

  @Override
  public void authorize(String userName, List<AuthorizeEntry> entrys) throws AuthorizeException {
    for(AuthorizeEntry entry : entrys) {
      authorize(userName, entry);
    }
  }
}
