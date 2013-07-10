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
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.ql.metadata.Table;
import org.apache.hadoop.hive.ql.plan.DDLDesc;

public class AuthorizeEntry {
  private Database db;
  private Table table;
  private Collection<FieldSchema> fields;
  private Collection<Privilege> requiredPrivs;
  private PrivilegeLevel privLevel;

  private List<DDLDesc> ddlDescs;

  public AuthorizeEntry(Database db, Table table, Collection<FieldSchema> fields,
      Collection<Privilege> requiredPrivs, PrivilegeLevel privLevel) {
    super();
    this.db = db;
    this.table = table;
    this.fields = fields;
    this.setRequiredPrivs(requiredPrivs);
    this.privLevel = privLevel;
  }

  public AuthorizeEntry(Database db, Table table, Collection<FieldSchema> fields,
      Privilege priv, PrivilegeLevel privLevel) {
    this(db, table, fields, Collections.singleton(priv), privLevel);
  }

  public Database getDb() {
    return db;
  }

  public void setDb(Database db) {
    this.db = db;
  }

  public Table getTable() {
    return table;
  }

  public void setTable(Table table) {
    this.table = table;
  }

  public Collection<FieldSchema> getFields() {
    return fields;
  }

  public void setFields(Collection<FieldSchema> fields) {
    this.fields = fields;
  }

  public PrivilegeLevel getPrivLevel() {
    return privLevel;
  }

  public void setPrivLevel(PrivilegeLevel privLevel) {
    this.privLevel = privLevel;
  }

  public List<DDLDesc> getDdlDescs() {
    return ddlDescs;
  }

  public void setDdlDescs(List<DDLDesc> ddlDescs) {
    this.ddlDescs = ddlDescs;
  }

  public void addDdlDesc(DDLDesc ddlDesc) {
    if (ddlDescs == null) {
      ddlDescs = new ArrayList<DDLDesc>();
    }
    ddlDescs.add(ddlDesc);
  }

  public void setRequiredPrivs(Collection<Privilege> requiredPrivs) {
    this.requiredPrivs = requiredPrivs;
  }

  public Collection<Privilege> getRequiredPrivs() {
    return requiredPrivs;
  }

}
