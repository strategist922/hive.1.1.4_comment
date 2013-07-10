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

package org.apache.hadoop.hive.ql.plan;

import java.io.Serializable;
import java.util.List;

import org.apache.hadoop.hive.ql.authorization.Privilege;
import org.apache.hadoop.hive.ql.authorization.PrivilegeLevel;

@Explain(displayName="revoke")
public class RevokeUserDesc extends DCLDesc implements Serializable {
  private static final long serialVersionUID = 1L;

  List<Privilege> privileges = null;
  PrivilegeLevel revokeLevel = null;
  String dbName = null;
  String tableName = null;
  List<String> users = null;

  public RevokeUserDesc(List<Privilege> privs, PrivilegeLevel revokeLevel, List<String> users) {
    this.privileges = privs;
    this.revokeLevel = revokeLevel;
    this.users = users;
  }

  public RevokeUserDesc(List<Privilege> privs, PrivilegeLevel revokeLevel, String dbName, List<String> users) {
    this.privileges = privs;
    this.revokeLevel = revokeLevel;
    this.dbName = dbName;
    this.users = users;
  }

  public RevokeUserDesc(List<Privilege> privs, PrivilegeLevel revokeLevel, String dbName, String tbName, List<String> users) {
    this.privileges = privs;
    this.revokeLevel = revokeLevel;
    this.dbName = dbName;
    this.tableName = tbName;
    this.users = users;
  }

  public List<Privilege> getPrivileges() {
    return this.privileges;
  }

  public void setPrivileges(List<Privilege> privs) {
    this.privileges = privs;
  }

  public PrivilegeLevel getRevokeLevel() {
    return revokeLevel;
  }

  public void setRevokeLevel(PrivilegeLevel revokeLevel) {
    this.revokeLevel = revokeLevel;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tblName) {
    this.tableName = tblName;
  }

  public List<String> getUsers() {
    return this.users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }
}
