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

import java.util.List;

import org.apache.hadoop.conf.Configuration;

/**
 * Authorizer passes all checks for any hive account.
 */
public class FakeAuthorizer implements Authorizer {

  @Override
  public void authorize(String userName, AuthorizeEntry entry) throws AuthorizeException {
  }

  @Override
  public Configuration getConf() {
    return null;
  }

  @Override
  public void setConf(Configuration arg0) {
  }

  @Override
  public void grant(String userName, String grantorName, AuthorizeEntry entry)
      throws AuthorizeException {
  }

  @Override
  public void revoke(String userName, String revokerName, AuthorizeEntry entry)
      throws AuthorizeException {
  }

  @Override
  public List<AuthorizeEntry> getAllPrivileges(String userName) throws AuthorizeException {
    return null;
  }

  @Override
  public void authorize(String userName, List<AuthorizeEntry> entrys) throws AuthorizeException {
    for(AuthorizeEntry entry : entrys) {
      authorize(userName, entry);
    }
  }
}

