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
import java.util.Map;

import org.apache.hadoop.hive.ql.authorization.Privilege;
import org.apache.hadoop.hive.ql.authorization.PrivilegeLevel;

@Explain(displayName="Grant privilege")
public class GrantUserDesc extends DCLDesc implements Serializable {
  private static final long serialVersionUID = 1L;

  List<Privilege> privileges = null;
  PrivilegeLevel grantLevel = null;
  String dbName = null;
  String tableName = null;
  Map<String, String> users = null;

  public GrantUserDesc(List<Privilege> privs, PrivilegeLevel grantLevel, Map<String, String> users) {
    this.privileges = privs;
    this.grantLevel = grantLevel;
    this.users = users;
  }

  public GrantUserDesc(List<Privilege> privs, PrivilegeLevel grantLevel, String dbName, Map<String, String> users) {
    this.privileges = privs;
    this.grantLevel = grantLevel;
    this.dbName = dbName;
    this.users = users;
  }

  public GrantUserDesc(List<Privilege> privs, PrivilegeLevel grantLevel, String dbName, String tbName, Map<String, String> users) {
    this.privileges = privs;
    this.grantLevel = grantLevel;
    this.dbName = dbName;
    this.tableName = tbName;
    this.users = users;
  }

  @Explain(displayName="list of privileges")
  public List<Privilege> getPrivileges() {
    return this.privileges;
  }

  public void setPrivileges(List<Privilege> privs) {
    this.privileges = privs;
  }

  @Explain(displayName="list of privilege level")
  public PrivilegeLevel getGrantLevel() {
    return grantLevel;
  }

  public void setGrantLevel(PrivilegeLevel grantLevel) {
    this.grantLevel = grantLevel;
  }

  @Explain(displayName="list of database name")
  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  @Explain(displayName="list of table name")
  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tblName) {
    this.tableName = tblName;
  }

  @Explain(displayName="list of user name")
  public Map<String, String> getUsers() {
    return this.users;
  }

  public void setUsers(Map<String, String> users) {
    this.users = users;
  }
}
