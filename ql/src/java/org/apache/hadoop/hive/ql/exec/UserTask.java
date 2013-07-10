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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.metastore.api.InvalidOperationException;
import org.apache.hadoop.hive.ql.Context;
import org.apache.hadoop.hive.ql.DriverContext;
import org.apache.hadoop.hive.ql.authorization.AuthorizeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.plan.CreateUserDesc;
import org.apache.hadoop.hive.ql.plan.DropUserDesc;
import org.apache.hadoop.hive.ql.plan.SetPasswordDesc;
import org.apache.hadoop.hive.ql.plan.UserWork;
import org.apache.hadoop.hive.ql.plan.api.StageType;
import org.apache.hadoop.util.StringUtils;

public class UserTask extends Task<UserWork> {
  private static final long serialVersionUID = 1L;
  private static final Log LOG = LogFactory.getLog("hive.ql.exec.UserTask");

  @Override
  public int execute(DriverContext driverContext) {
    try {
      CreateUserDesc createUserDesc = work.getCreateUserDesc();
      if (createUserDesc != null) {
        return createUser(createUserDesc);
      }

      DropUserDesc dropUserDesc = work.getDropUserDesc();
      if (dropUserDesc != null) {
        return dropUser(dropUserDesc);
      }

      SetPasswordDesc setPasswordDesc = work.getSetPasswordDesc();
      if (setPasswordDesc != null) {
        return setPassword(setPasswordDesc);
      }
    } catch (AuthorizeException e) {
      console.printError("FAILED: Error in privilege authorization: "
          + e.getMessage(), "\n"
          + stringifyException(e));
      LOG.debug(stringifyException(e));
      errorMessage = "FAILED: Error in privilege authorization: "
          + e.getMessage() + "\n" + stringifyException(e);
      return 1;
    }
    return 0;
  }

  private int createUser(CreateUserDesc createUserDesc) throws AuthorizeException {
    MessageDigest sha1 = null;
    try {
      sha1 = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException nsae) {
      LOG.info("create user: " + StringUtils.stringifyException(nsae));
      errorMessage = "create user: " + StringUtils.stringifyException(nsae);
      return 2;
    }
    // FIXME: it isn't an atom operation
    List<String> names = createUserDesc.getUserNames();
    List<String> passwords = createUserDesc.getPasswords();
    int len = names.size();
    for(int i = 0; i < len; i++) {
      String name = names.get(i);
      String password = passwords.get(i);
      try {
        sha1.reset();
        byte[] buff = sha1.digest(password.getBytes());
        db.createUser(name,buff);
      } catch (HiveException he) {
        LOG.info("create user: " + StringUtils.stringifyException(he));
        errorMessage = "create user: " + StringUtils.stringifyException(he);
        return 1;
      }
    }
    return 0;
  }

  private int dropUser(DropUserDesc dropUserDesc) throws AuthorizeException {
    List<String> names = dropUserDesc.getUserNames();
    for(String name :  names) {
      try {
        db.dropUser(name);
      } catch (HiveException he) {
        LOG.info("drop user: " + StringUtils.stringifyException(he));
        errorMessage = "drop user: " + StringUtils.stringifyException(he);
        return 1;
      }
    }
    return 0;
  }

  private int setPassword(SetPasswordDesc setPasswordDesc) throws AuthorizeException {
    MessageDigest sha1 = null;
    try {
      sha1 = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException nsae) {
      LOG.info("create user: " + StringUtils.stringifyException(nsae));
      errorMessage = "create user: " + StringUtils.stringifyException(nsae);
      return 2;
    }
    String userName = setPasswordDesc.getUserName();
    String password = setPasswordDesc.getPassword();
    try {
      db.alterPassword(userName, sha1.digest(password.getBytes()));
    } catch (InvalidOperationException e) {
      console.printError("Invalid alter operation: " + e.getMessage());
      LOG.info("set password: " + StringUtils.stringifyException(e));
      errorMessage = "Invalid alter operation: " + e.getMessage();
      return 1;
    } catch (HiveException he) {
      LOG.info("set password: " + StringUtils.stringifyException(he));
      errorMessage = "set password: " + StringUtils.stringifyException(he) ;
      return 1;
    }
    return 0;
  }

  @Override
  public String getName() {
    return "USER";
  }

  @Override
  public int getType() {
    return StageType.USER;
  }

  @Override
  protected void localizeMRTmpFilesImpl(Context ctx) {
    // no-op
  }
}
