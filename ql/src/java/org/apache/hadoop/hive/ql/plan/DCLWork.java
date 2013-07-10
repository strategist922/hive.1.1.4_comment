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

public class DCLWork implements Serializable {
  private static final long serialVersionUID = 1L;

  private GrantUserDesc gntUserDesc = null;
  private RevokeUserDesc rvkUserDesc = null;
  private ShowGrantsDesc showGntDesc = null;
  
  public DCLWork(GrantUserDesc grant) {
    this.gntUserDesc = grant;
  }
  
  public DCLWork(RevokeUserDesc revoke) {
    this.rvkUserDesc = revoke;
  }

  public DCLWork(ShowGrantsDesc showGntDesc) {
    this.showGntDesc = showGntDesc;
  }

  public GrantUserDesc getGrantUserDesc() {
    return this.gntUserDesc;
  } 
  
  public void setGrantUserDesc(GrantUserDesc grant) {
    this.gntUserDesc = grant;
  }
  
  public RevokeUserDesc getRevokeUserDesc() {
    return this.rvkUserDesc;
  }
  
  public void setRevokeUserDesc(RevokeUserDesc revoke) {
    this.rvkUserDesc = revoke;
  }

  public ShowGrantsDesc getShowGntDesc() {
    return showGntDesc;
  }

  public void setShowGntDesc(ShowGrantsDesc showGntDesc) {
    this.showGntDesc = showGntDesc;
  }
}
