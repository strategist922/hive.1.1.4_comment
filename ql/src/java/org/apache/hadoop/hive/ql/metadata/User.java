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

public class User {
  private org.apache.hadoop.hive.metastore.api.User tUser;

  protected User() throws HiveException {
  }

  public User(String name, byte[] password) {
    // fill in defaults
    initEmpty();
    getTUser().setName(name);
    getTUser().setPassword(password);
  }

  private void initEmpty() {
    this.setTUser(new org.apache.hadoop.hive.metastore.api.User());
  }

  public org.apache.hadoop.hive.metastore.api.User getTUser() {
    return tUser;
  }

  protected void setTUser(org.apache.hadoop.hive.metastore.api.User user) {
    tUser = user;
  }

  public String getName() {
    return tUser.getName();
  }

  public void setName(String userName) {
    tUser.setName(userName);
  }

  public byte[] getPassword() {
    return tUser.getPassword();
  }

  public void setPassword(byte[] password) {
    tUser.setPassword(password);
  }

  public boolean hasSelect_priv() {
    return tUser.isSelect_priv();
  }

  public void setSelect_priv(boolean select_priv) {
    tUser.setSelect_priv(select_priv);
  }

  public boolean hasInsert_priv() {
    return tUser.isInsert_priv();
  }

  public void setInsert_priv(boolean insert_priv) {
    tUser.setInsert_priv(insert_priv);
  }

  public boolean hasCreate_priv() {
    return tUser.isCreate_priv();
  }

  public void setCreate_priv(boolean create_priv) {
    tUser.setCreate_priv(create_priv);
  }

  public boolean isDrop_priv() {
    return tUser.isDrop_priv();
  }

  public void setDrop_priv(boolean drop_priv) {
    tUser.setDrop_priv(drop_priv);
  }

  public boolean hasGrant_priv() {
    return tUser.isGrant_priv();
  }

  public void setGrant_priv(boolean grant_priv) {
    tUser.setGrant_priv(grant_priv);
  }

  public boolean hasAlter_priv() {
    return tUser.isAlter_priv();
  }

  public void setAlter_priv(boolean alter_priv) {
    tUser.setAlter_priv(alter_priv);
  }

  public boolean hasCreate_user_priv() {
    return tUser.isCreate_user_priv();
  }

  public void setCreate_user_priv(boolean create_user_priv) {
    tUser.setCreate_user_priv(create_user_priv);
  }

  public boolean hasSuper_priv() {
    return tUser.isSuper_priv();
  }

  public void setSuper_priv(boolean super_priv) {
    tUser.setSuper_priv(super_priv);
  }

  public boolean hasPriv(Privilege priv) {
    if (this.hasSuper_priv()) {
      return true;
    }
    switch (priv) {
    case ALTER_PRIV:
      return this.hasAlter_priv();
    case DROP_PRIV:
      return this.isDrop_priv();
    case GRANT_PRIV:
      return this.hasGrant_priv();
    case INSERT_PRIV:
      return this.hasInsert_priv();
    case SELECT_PRIV:
      return this.hasSelect_priv();
    case CREATE_PRIV:
      return this.hasCreate_priv();
    case CREATE_USER_PRIV:
      return this.hasCreate_user_priv();
    default:
      return false;
    }
  }

  public boolean hasPriv(Collection<Privilege> privs) {
    if (this.hasSuper_priv()) {
      return true;
    }
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
    if(isDrop_priv()) {
      allPriv.add(Privilege.DROP_PRIV);
    }
    if(hasGrant_priv()) {
      allPriv.add(Privilege.GRANT_PRIV);
    }
    if(hasAlter_priv()) {
      allPriv.add(Privilege.ALTER_PRIV);
    }
    if(hasCreate_priv()) {
      allPriv.add(Privilege.CREATE_PRIV);
    }
    if(hasCreate_user_priv()) {
      allPriv.add(Privilege.CREATE_USER_PRIV);
    }
    if(hasSuper_priv()) {
      allPriv.add(Privilege.SUPER_PRIV);
    }
    return allPriv;
  }
}
