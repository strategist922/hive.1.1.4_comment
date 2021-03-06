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

public enum Privilege {
  SELECT_PRIV("Select"),
  INSERT_PRIV("Insert"),
  CREATE_PRIV("Create"),
  DROP_PRIV("Drop"),
  GRANT_PRIV("Grant"),
  ALTER_PRIV("Alter"),
  CREATE_USER_PRIV("CreateUser"),
  SUPER_PRIV("Super"),
  ;

  public final String name;

  private Privilege(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
