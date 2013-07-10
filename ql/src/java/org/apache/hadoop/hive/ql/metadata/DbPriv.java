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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.hadoop.hive.ql.authorization.Privilege;

public class DbPriv {
  private org.apache.hadoop.hive.metastore.api.DbPriv tDbPriv;

  protected DbPriv() throws HiveException {
  }

  public DbPriv(String dbName, String userName) {
    // fill in defaults
    initEmpty();
    getTDbPriv().setDbName(dbName);
    getTDbPriv().setUserName(userName);
  }

  private void initEmpty() {
    this.setDbPriv(new org.apache.hadoop.hive.metastore.api.DbPriv());
  }

  public void setDbPriv(org.apache.hadoop.hive.metastore.api.DbPriv dbPriv) {
    this.tDbPriv = dbPriv;
  }

  public org.apache.hadoop.hive.metastore.api.DbPriv getTDbPriv() {
    return tDbPriv;
  }

  public String getDbName() {
    return tDbPriv.getDbName();
  }

  public void setDb(String dbName) {
    tDbPriv.setDbName(dbName);
  }

  public String getUserName() {
    return tDbPriv.getUserName();
  }

  public void setUser(String userName) {
    tDbPriv.setUserName(userName);
  }

  public boolean hasSelect_priv() {
    return tDbPriv.isSelect_priv();
  }

  public void setSelect_priv(boolean select_priv) {
    tDbPriv.setSelect_priv(select_priv);
  }

  public boolean hasInsert_priv() {
    return tDbPriv.isInsert_priv();
  }

  public void setInsert_priv(boolean insert_priv) {
    tDbPriv.setInsert_priv(insert_priv);
  }

  public boolean hasDrop_priv() {
    return tDbPriv.isDrop_priv();
  }

  public void setDrop_priv(boolean drop_priv) {
    tDbPriv.setDrop_priv(drop_priv);
  }

  public boolean hasGrant_priv() {
    return tDbPriv.isGrant_priv();
  }

  public void setGrant_priv(boolean grant_priv) {
    tDbPriv.setGrant_priv(grant_priv);
  }

  public boolean hasAlter_priv() {
    return tDbPriv.isAlter_priv();
  }

  public void setAlter_priv(boolean alter_priv) {
    tDbPriv.setAlter_priv(alter_priv);
  }

  public boolean hasCreate_priv() {
    return tDbPriv.isCreate_priv();
  }

  public void setCreate_priv(boolean alter_priv) {
    tDbPriv.setCreate_priv(alter_priv);
  }

  public boolean hasPriv(Privilege priv) {
    switch (priv) {
    case ALTER_PRIV:
      return this.hasAlter_priv();
    case DROP_PRIV:
      return this.hasDrop_priv();
    case GRANT_PRIV:
      return this.hasGrant_priv();
    case INSERT_PRIV:
      return this.hasInsert_priv();
    case SELECT_PRIV:
      return this.hasSelect_priv();
    case CREATE_PRIV:
      return this.hasCreate_priv();
    default:
      return false;
    }
  }

  public boolean hasPriv(Collection<Privilege> privs) {
    boolean hasPriv = true;
    for (Privilege p : privs) {
      hasPriv = hasPriv && hasPriv(p);
    }
    return hasPriv;
  }

  public List<Privilege> getAllPriv() {
    List<Privilege> allPriv = new ArrayList<Privilege>();
    if(hasSelect_priv()) {
      allPriv.add(Privilege.SELECT_PRIV);
    }
    if(hasInsert_priv()) {
      allPriv.add(Privilege.INSERT_PRIV);
    }
    if(hasDrop_priv()) {
      allPriv.add(Privilege.DROP_PRIV);
    }
    if(hasGrant_priv()) {
      allPriv.add(Privilege.GRANT_PRIV);
    }
    if(hasAlter_priv()) {
      allPriv.add(Privilege.ALTER_PRIV);
    }
    return allPriv;
  }
}
