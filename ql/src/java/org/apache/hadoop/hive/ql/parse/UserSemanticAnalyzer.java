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

package org.apache.hadoop.hive.ql.parse;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.ql.authorization.Privilege;
import org.apache.hadoop.hive.ql.exec.TaskFactory;
import org.apache.hadoop.hive.ql.plan.CreateUserDesc;
import org.apache.hadoop.hive.ql.plan.DropUserDesc;
import org.apache.hadoop.hive.ql.plan.SetPasswordDesc;
import org.apache.hadoop.hive.ql.plan.UserWork;

public class UserSemanticAnalyzer extends BaseSemanticAnalyzer {
  private static final Log LOG =
    LogFactory.getLog("hive.ql.parse.UserSemanticAnalyzer");

  public UserSemanticAnalyzer(HiveConf conf) throws SemanticException {
    super(conf);
  }

  @Override
  public void analyzeInternal(ASTNode ast) throws SemanticException {
    if (ast.getToken().getType() == HiveParser.TOK_CREATEUSER) {
      analyzeCreateUser(ast);
    }
    if (ast.getToken().getType() == HiveParser.TOK_DROPUSER) {
      analyzeDropUser(ast);
    }
    if (ast.getToken().getType() == HiveParser.TOK_SETPASSWORD) {
      analyzeSetPassword(ast);
    }
    genAuthorizeEntry(null, null, null, Privilege.CREATE_USER_PRIV);
    LOG.info("analyze done");
  }

  private void analyzeCreateUser(ASTNode ast)
      throws SemanticException {
    List<String> userNames = new ArrayList<String>();
    List<String> passwords = new ArrayList<String>();
    for (int i = 0; i < ast.getChildCount(); ++i) {
      ASTNode child = (ASTNode) ast.getChild(i);
      userNames.add(child.getChild(0).getText());
      // FIXME: deal with empty password
      if(child.getChild(1) != null) {
        passwords.add(unescapeSQLString(child.getChild(1).getText()));
      } else {
        passwords.add("");
      }
    }
    CreateUserDesc desc = new CreateUserDesc(userNames, passwords);
    rootTasks.add(TaskFactory.get(new UserWork(desc), conf));
  }

  private void analyzeDropUser(ASTNode ast)
      throws SemanticException {
    List<String> userNames = new ArrayList<String>();
    for (int i = 0; i < ast.getChildCount(); ++i) {
      ASTNode child = (ASTNode) ast.getChild(i);
      userNames.add(child.getText());
    }
    DropUserDesc desc = new DropUserDesc(userNames);
    rootTasks.add(TaskFactory.get(new UserWork(desc), conf));
  }

  private void analyzeSetPassword(ASTNode ast)
      throws SemanticException {
    String userName = ast.getChild(0).getText();
    String password = unescapeSQLString(ast.getChild(1).getText());
    SetPasswordDesc desc = new SetPasswordDesc(userName, password);
    rootTasks.add(TaskFactory.get(new UserWork(desc), conf));
  }
}
