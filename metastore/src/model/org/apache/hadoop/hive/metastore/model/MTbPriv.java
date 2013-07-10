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

public class MTbPriv {

  private MDatabase db;
  private MUser user;
  private MTable table;
  private MUser grantor;
  private int timeStamp;
  private boolean select_priv;
  private boolean insert_priv;
  private boolean drop_priv;
  private boolean grant_priv;
  private boolean alter_priv;

  public MTbPriv() {}

  public MTbPriv(MDatabase db, MUser user, MTable table, MUser grantor,
      int timeStamp, boolean select_priv, boolean insert_priv,
      boolean drop_priv, boolean grant_priv, boolean alter_priv) {
    this.db = db;
    this.user = user;
    this.table = table;
    this.grantor = grantor;
    this.timeStamp = timeStamp;
    this.select_priv = select_priv;
    this.insert_priv = insert_priv;
    this.drop_priv = drop_priv;
    this.grant_priv = grant_priv;
    this.alter_priv = alter_priv;
  }

  public MDatabase getDb() {
    return db;
  }

  public void setDb(MDatabase db) {
    this.db = db;
  }

  public MUser getUser() {
    return user;
  }

  public void setUser(MUser user) {
    this.user = user;
  }

  public MTable getTableName() {
    return table;
  }

  public void setTableName(MTable tableName) {
    this.table = tableName;
  }

  public MUser getGrantor() {
    return grantor;
  }

  public void setGrantor(MUser grantor) {
    this.grantor = grantor;
  }

  public int getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(int timeStamp) {
    this.timeStamp = timeStamp;
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
}
