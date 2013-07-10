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

package org.apache.hadoop.hive.metastore.model;

/**
 * Storage user's global privileges in a rdbms
 *
 */
public class MUser {
  
  private String userName;
  private byte[] password;
  private boolean select_priv;
  private boolean insert_priv;
  private boolean create_priv;
  private boolean drop_priv;
  private boolean grant_priv;
  private boolean alter_priv;
  private boolean create_user_priv;
  private boolean super_priv;
  
  public MUser() {}

  public MUser(String userName, byte[] password, boolean select_priv, boolean insert_priv,
      boolean create_priv, boolean drop_priv, boolean grant_priv, boolean alter_priv,
      boolean create_user_priv, boolean super_priv) {
    this.userName = userName;
    this.password = password;
    this.select_priv = select_priv;
    this.insert_priv = insert_priv;
    this.create_priv = create_priv;
    this.drop_priv = drop_priv;
    this.grant_priv = grant_priv;
    this.alter_priv = alter_priv;
    this.create_user_priv = create_user_priv;
    this.super_priv = super_priv;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public byte[] getPassword() {
    return password;
  }

  public void setPassword(byte[] password) {
    this.password = password;
  }

  public boolean isSelect_priv() {
    return select_priv;
  }

  public void setSelect_priv(boolean select_priv) {
    this.select_priv = select_priv;
  }

  public boolean isInsert_priv() {
    return insert_priv;
  }

  public void setInsert_priv(boolean insert_priv) {
    this.insert_priv = insert_priv;
  }

  public boolean isCreate_priv() {
    return create_priv;
  }

  public void setCreate_priv(boolean create_priv) {
    this.create_priv = create_priv;
  }

  public boolean isDrop_priv() {
    return drop_priv;
  }

  public void setDrop_priv(boolean drop_priv) {
    this.drop_priv = drop_priv;
  }

  public boolean isGrant_priv() {
    return grant_priv;
  }

  public void setGrant_priv(boolean grant_priv) {
    this.grant_priv = grant_priv;
  }

  public boolean isAlter_priv() {
    return alter_priv;
  }

  public void setAlter_priv(boolean alter_priv) {
    this.alter_priv = alter_priv;
  }

  public boolean isCreate_user_priv() {
    return create_user_priv;
  }

  public void setCreate_user_priv(boolean create_user_priv) {
    this.create_user_priv = create_user_priv;
  }

  public boolean isSuper_priv() {
    return super_priv;
  }

  public void setSuper_priv(boolean super_priv) {
    this.super_priv = super_priv;
  }
}
