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

import java.util.EnumSet;

public enum PrivilegeLevel {
  GLOBAL_LEVEL(1,
      EnumSet.of(Privilege.ALTER_PRIV, Privilege.CREATE_PRIV, Privilege.CREATE_USER_PRIV,
          Privilege.INSERT_PRIV, Privilege.SELECT_PRIV, Privilege.DROP_PRIV)),
  DATABASE_LEVEL(2,
      EnumSet.of(Privilege.ALTER_PRIV, Privilege.CREATE_PRIV, Privilege.INSERT_PRIV, Privilege.SELECT_PRIV,
          Privilege.DROP_PRIV, Privilege.GRANT_PRIV)),
  TABLE_LEVEL(3,
      EnumSet.of(Privilege.ALTER_PRIV, Privilege.INSERT_PRIV, Privilege.SELECT_PRIV,
          Privilege.DROP_PRIV, Privilege.GRANT_PRIV)),
  COLUMN_LEVEL(4,
      EnumSet.of(Privilege.ALTER_PRIV, Privilege.INSERT_PRIV, Privilege.SELECT_PRIV, Privilege.GRANT_PRIV)),
  ;

  private final EnumSet<Privilege> privs;
  private final int level;

  private PrivilegeLevel(int level, EnumSet<Privilege> privs) {
    this.privs = privs;
    this.level = level;
  }

  public EnumSet<Privilege> getPrivileges() {
    return privs;
  }

  public int getLevel() {
    return level;
  }
}
