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

package org.apache.hadoop.hive.ql.exec;

import static org.apache.hadoop.util.StringUtils.stringifyException;

import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.InvalidOperationException;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.DriverContext;
import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.authorization.AuthorizeEntry;
import org.apache.hadoop.hive.ql.authorization.AuthorizeException;
import org.apache.hadoop.hive.ql.authorization.Privilege;
import org.apache.hadoop.hive.ql.authorization.PrivilegeLevel;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.metadata.InvalidUserException;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.metadata.User;
import org.apache.hadoop.hive.ql.plan.DCLWork;
import org.apache.hadoop.hive.ql.plan.GrantUserDesc;
import org.apache.hadoop.hive.ql.plan.RevokeUserDesc;
import org.apache.hadoop.hive.ql.plan.ShowGrantsDesc;
import org.apache.hadoop.hive.ql.plan.api.StageType;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.util.StringUtils;

public class DCLTask extends Task<DCLWork> implements Serializable {
  private static final long serialVersionUID = 1L;
  static final private Log LOG = LogFactory.getLog("hive.ql.exec.DCLTask");

  static final private int terminator = Utilities.newLineCode;

  @Override
  public void initialize(HiveConf conf, QueryPlan queryPlan,
      DriverContext driverContext) {
    super.initialize(conf, queryPlan, driverContext);
    this.conf = conf;
  }

  @Override
  public int execute(DriverContext driverContext) {
    try {

      GrantUserDesc grantDesc = work.getGrantUserDesc();
      if (grantDesc != null) {
        grantUser(grantDesc);
      }

      RevokeUserDesc rvkDesc = work.getRevokeUserDesc();
      if (rvkDesc != null) {
        revokeUser(rvkDesc);
      }

      ShowGrantsDesc showGntDesc = work.getShowGntDesc();
      if (showGntDesc != null) {
        showGrants(showGntDesc);
      }
    } catch (AuthorizeException e) {
      console.printError("FAILED: Error in privilege authorization: "
          + e.getMessage(), "\n"
          + stringifyException(e));
      LOG.debug(stringifyException(e));
      errorMessage = "FAILED: Error in privilege authorization: "
          + e.getMessage()+ "\n" + stringifyException(e);
      return 1;
    } catch (InvalidUserException e) {
      console.printError("User " + e.getUserName() + " does not exist");
      LOG.debug(StringUtils.stringifyException(e));
      errorMessage = "User " + e.getUserName() + " does not exist";
      return 1;
    } catch (HiveException e) {
      console.printError("FAILED: Error in metadata: " + e.getMessage(),
          "\n" + StringUtils.stringifyException(e));
      LOG.debug(StringUtils.stringifyException(e));
      errorMessage = "FAILED: Error in metadata: " + e.getMessage()
          + "\n" + StringUtils.stringifyException(e);
      return 1;
    } catch (Exception e) {
      console.printError("Failed with exception " + e.getMessage(),
          "\n" + StringUtils.stringifyException(e));
      errorMessage = "Failed with exception " + e.getMessage()
          + "\n" + StringUtils.stringifyException(e);
      return 1;
    }

    return 0;
  }

  /**
   * do grant works
   *
   * @param grantDesc
   * @throws HiveException
   * @throws InvalidOperationException
   * @throws AuthorizeException
   */
  private void grantUser(final GrantUserDesc grantDesc) throws HiveException,
      InvalidOperationException, AuthorizeException {
    Map<String, String> users = grantDesc.getUsers();
    createUnexistUsers(users);

    AuthorizeEntry entry = null;
    String grantor = SessionState.get().getUserName();
    // do actual grant work here
    for (String name : users.keySet()) {
      if (grantDesc.getGrantLevel() == PrivilegeLevel.GLOBAL_LEVEL) {
        entry = new AuthorizeEntry(null, null, null, grantDesc.getPrivileges(),
            PrivilegeLevel.GLOBAL_LEVEL);
      } else if (grantDesc.getGrantLevel() == PrivilegeLevel.DATABASE_LEVEL) {
        Database database = db.getDatabase(grantDesc.getDbName());
        entry = new AuthorizeEntry(database, null, null, grantDesc.getPrivileges(),
            PrivilegeLevel.DATABASE_LEVEL);
      } else if (grantDesc.getGrantLevel() == PrivilegeLevel.TABLE_LEVEL) {
        String dbName = grantDesc.getDbName();
        if (dbName == null) {
          dbName = db.getCurrentDatabase();
        }
        Table tbl = db.getTable(dbName, grantDesc.getTableName());
        if (tbl != null) {
          Database database = db.getDatabase(dbName);
          entry = new AuthorizeEntry(database, tbl, null, grantDesc.getPrivileges(),
              PrivilegeLevel.TABLE_LEVEL);
        }
      }
      SessionState.get().getAuthorizer().grant(name, grantor, entry);
    }
  }

  private void revokeUser(final RevokeUserDesc rvkDesc)
      throws InvalidOperationException, HiveException, AuthorizeException {
    // do revoking
    AuthorizeEntry entry = null;
    String grantor = SessionState.get().getUserName();
    for (String name : rvkDesc.getUsers()) {
      if (rvkDesc.getRevokeLevel() == PrivilegeLevel.GLOBAL_LEVEL) {
        entry = new AuthorizeEntry(null, null, null, rvkDesc.getPrivileges(),
            PrivilegeLevel.GLOBAL_LEVEL);
      } else if (rvkDesc.getRevokeLevel() == PrivilegeLevel.DATABASE_LEVEL) {
        Database database = db.getDatabase(rvkDesc.getDbName());
        entry = new AuthorizeEntry(database, null, null, rvkDesc.getPrivileges(),
            PrivilegeLevel.DATABASE_LEVEL);
      } else if (rvkDesc.getRevokeLevel() == PrivilegeLevel.TABLE_LEVEL) {
        String dbName = rvkDesc.getDbName();
        if (dbName == null) {
          dbName = db.getCurrentDatabase();
        }
        Table tbl = db.getTable(dbName, rvkDesc.getTableName());
        if (tbl != null) {
          Database database = db.getDatabase(dbName);
          entry = new AuthorizeEntry(database, tbl, null, rvkDesc.getPrivileges(),
              PrivilegeLevel.TABLE_LEVEL);
        }
      }
      SessionState.get().getAuthorizer().revoke(name, grantor, entry);
    }
  }

  private int showGrants(ShowGrantsDesc showGntsDesc)
      throws HiveException, AuthorizeException {
    String userName = showGntsDesc.getUser();
    if (userName == null) {
      userName = SessionState.get().getUserName();
    }
    User user = db.getUser(userName);
    try {
      if (user == null) {
        FileSystem fs = showGntsDesc.getResFile().getFileSystem(conf);
        DataOutput outStream = (DataOutput) fs.open(showGntsDesc.getResFile());
        String errMsg = "User " + userName + " does not exist";
        outStream.write(errMsg.getBytes("UTF-8"));
        ((FSDataOutputStream) outStream).close();
        return 0;
      }
    } catch (FileNotFoundException e) {
      LOG.info("show grants: " + StringUtils.stringifyException(e));
      return 1;
    } catch (IOException e) {
      LOG.info("show grants: " + StringUtils.stringifyException(e));
      return 1;
    }

    try {

      LOG.info("DCLTask: got grant privilege for " + user.getName());

      FileSystem fs = showGntsDesc.getResFile().getFileSystem(conf);
      DataOutput outStream = (DataOutput) fs.create(showGntsDesc.getResFile());

      List<AuthorizeEntry> entries =
        SessionState.get().getAuthorizer().getAllPrivileges(userName);
      if (entries == null || entries.isEmpty()) {
        return 0;
      }
      for (AuthorizeEntry e: entries) {
        switch (e.getPrivLevel()) {
        case GLOBAL_LEVEL:
          outStream.writeBytes("Global grants: ");
          break;
        case DATABASE_LEVEL:
          outStream.writeBytes(
              String.format("Grants on database %s:", e.getDb().getName()));
          break;
        case TABLE_LEVEL:
          outStream.writeBytes(
              String.format("Grants on table %s.%s:",
                  e.getTable().getDbName(),
                  e.getTable().getTableName()));
          break;
        case COLUMN_LEVEL:
          String fields = "";
          if (e.getFields() != null && !e.getFields().isEmpty()) {
            for (FieldSchema f : e.getFields()) {
              fields += f.getName() + ",";
            }
          } else {
            fields = "<null>";
          }
          outStream.writeBytes(
              String.format("Grants on column %s.%s.[%s]:",
                  e.getTable().getDbName(),
                  e.getTable().getTableName(),
                  fields));
          break;
        default:
        }
        for (Privilege p : e.getRequiredPrivs()) {
          outStream.writeBytes(p.toString() + " ");
        }
        outStream.write(terminator);
      }
      LOG.info("DCLTask: written data for " + user.getName());
      ((FSDataOutputStream) outStream).close();
    } catch (FileNotFoundException e) {
      LOG.info("show grants: " + StringUtils.stringifyException(e));
      return 1;
    } catch (IOException e) {
      LOG.info("show grants: " + StringUtils.stringifyException(e));
      return 1;
    } catch (Exception e) {
      throw new HiveException(e.toString());
    }
    return 0;
  }


  /**
   * Check if each user to be granted exists:
   * <p>
   * 1. If it doesn't exist, create it. Password will empty if it is not specified. Or, set
   * passwords as specified.
   * <p>
   * 2. If user does exist and the exist password does not match the newly-specified one, change the
   * old one to the new.
   *
   * @param users
   * @throws HiveException
   * @throws InvalidOperationException
   */
  private void createUnexistUsers(final Map<String, String> users) throws HiveException,
      InvalidOperationException {
    MessageDigest sha1 = null;
    try {
      sha1 = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e1) {
      throw new HiveException(e1);
    }

    byte[] pwdHash = null;
    boolean isEmptyPwd = false;
    for (Entry<String, String> ent : users.entrySet()) {
      String name = ent.getKey();
      if (("".equals(ent.getValue())) || (ent.getValue() == null)) {
        isEmptyPwd = true;
        pwdHash = sha1.digest(new byte[] {});
      } else {
        pwdHash = sha1.digest(ent.getValue().getBytes());
      }
      User user = db.getUser(name);
      if (user == null) {
        db.createUser(name, pwdHash);
      } else if (!isEmptyPwd) {
        db.alterPassword(name, pwdHash);
      }
    }
  }

  @Override
  public String getName() {
    return "DCL";
  }

  @Override
  public int getType() {
    return StageType.DCL;
  }

  @Override
  protected void localizeMRTmpFilesImpl(Context ctx) {
    // no-op
  }
}
