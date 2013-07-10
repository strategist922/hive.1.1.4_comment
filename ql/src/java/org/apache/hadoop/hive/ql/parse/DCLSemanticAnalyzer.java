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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.MetaStoreUtils;
import org.apache.hadoop.hive.ql.authorization.Privilege;
import org.apache.hadoop.hive.ql.authorization.PrivilegeLevel;
import org.apache.hadoop.hive.ql.exec.FetchTask;
import org.apache.hadoop.hive.ql.exec.TaskFactory;
import org.apache.hadoop.hive.ql.io.IgnoreKeyTextOutputFormat;
import org.apache.hadoop.hive.ql.plan.DCLWork;
import org.apache.hadoop.hive.ql.plan.FetchWork;
import org.apache.hadoop.hive.ql.plan.GrantUserDesc;
import org.apache.hadoop.hive.ql.plan.RevokeUserDesc;
import org.apache.hadoop.hive.ql.plan.ShowGrantsDesc;
import org.apache.hadoop.hive.ql.plan.TableDesc;
import org.apache.hadoop.hive.serde.Constants;
import org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe;
import org.apache.hadoop.mapred.TextInputFormat;

public class DCLSemanticAnalyzer extends BaseSemanticAnalyzer {
  private static final Log LOG = LogFactory
      .getLog("hive.ql.parse.DCLSemanticAnalyzer");

  Privilege privilege;

  private static final Map<Integer, Privilege> tokenToPrivilege = new HashMap<Integer, Privilege>();
  static {
    tokenToPrivilege.put(HiveParser.TOK_PRIVSEL, Privilege.SELECT_PRIV);
    tokenToPrivilege.put(HiveParser.TOK_PRIVINS, Privilege.INSERT_PRIV);
    tokenToPrivilege.put(HiveParser.TOK_PRIVCRT, Privilege.CREATE_PRIV);
    tokenToPrivilege.put(HiveParser.TOK_PRIVALT, Privilege.ALTER_PRIV);
    tokenToPrivilege.put(HiveParser.TOK_PRIVDROP, Privilege.DROP_PRIV);
    tokenToPrivilege.put(HiveParser.TOK_PRIVCREATEUSER, Privilege.CREATE_USER_PRIV);
    tokenToPrivilege.put(HiveParser.TOK_PRIVGRANT, Privilege.GRANT_PRIV);
    tokenToPrivilege.put(HiveParser.TOK_PRIVSUPER, Privilege.SUPER_PRIV);
  }

  public DCLSemanticAnalyzer(HiveConf conf)
      throws SemanticException {
    super(conf);
    privilege = null;
  }

  @Override
  public void analyzeInternal(ASTNode ast) throws SemanticException {
    if (ast.getToken().getType() == HiveParser.TOK_GRANT) {
      analyzeGrant(ast);
    } else if (ast.getToken().getType() == HiveParser.TOK_REVOKE) {
      analyzeRevoke(ast);
    } else if (ast.getToken().getType() == HiveParser.TOK_SHOWGRANTS) {
      ctx.setResFile(new Path(ctx.getLocalTmpFileURI()));
      analyzeShowGrants(ast);
    }

    LOG.info("analyze done");
  }

  private void analyzeGrant(final ASTNode ast) throws SemanticException {
    List<Privilege> privileges = new ArrayList<Privilege>();
    Map<String, String> users = new TreeMap<String, String>();
    PrivilegeLevel grantLevel = null;

    ASTNode privsNode = (ASTNode) ast.getChild(0);
    boolean isAll = false;
    for (int i = 0; i < privsNode.getChildCount(); ++i) {
      ASTNode child = (ASTNode) privsNode.getChild(i);
      int privType = child.getToken().getType();
      if (privType != HiveParser.TOK_PRIVALL) {
        privileges.add(tokenToPrivilege.get(privType));
      } else {
        isAll = true;
      }
    }

    ASTNode targetNode = (ASTNode) ast.getChild(1);
    int targetLevel = targetNode.getToken().getType();
    String dbName = null;
    String tbName = null;
    if (targetLevel == HiveParser.TOK_PRIV_GLOBAL) {
      // global level
      grantLevel = PrivilegeLevel.GLOBAL_LEVEL;
      if (isAll == true) {
        privileges.addAll(PrivilegeLevel.GLOBAL_LEVEL.getPrivileges());
      }
      for (Privilege p : privileges) {
        checkPrivsValid(grantLevel, p);
      }
    } else if (targetLevel == HiveParser.TOK_PRIV_DB) {
      // db level
      grantLevel = PrivilegeLevel.DATABASE_LEVEL;
      if (isAll == true) {
        privileges.addAll(PrivilegeLevel.DATABASE_LEVEL.getPrivileges());
      }
      if (targetNode.getChildCount() == 1) {
        dbName = stripQuotes(targetNode.getChild(0).getText());
      }
      for (Privilege p : privileges) {
        checkPrivsValid(grantLevel, p);
      }
    } else if (targetLevel == HiveParser.TOK_PRIV_TABLE) {
      // table level
      grantLevel = PrivilegeLevel.TABLE_LEVEL;
      if (isAll == true) {
        privileges.addAll(PrivilegeLevel.TABLE_LEVEL.getPrivileges());
      }
      if (targetNode.getChildCount() == 2) {
        dbName = targetNode.getChild(0).getText();
        tbName = targetNode.getChild(1).getText();
      } else {
        tbName = targetNode.getChild(0).getText();
      }
      for (Privilege p : privileges) {
        checkPrivsValid(grantLevel, p);
      }
    }

    ASTNode usersNode = (ASTNode) ast.getChild(2);
    for (int i = 0; i < usersNode.getChildCount(); ++i) {
      ASTNode child = (ASTNode) usersNode.getChild(i);
      String userName = unescapeIdentifier(child.getChild(0).getText());
      if (child.getChild(1) != null) {
        users.put(userName, unescapeIdentifier(child.getChild(1).getText()));
      } else {
        users.put(userName, "");
      }
    }

    ASTNode optionsNode = (ASTNode) ast.getChild(3);
    if (optionsNode != null) {
      for (int i = 0; i < optionsNode.getChildCount(); ++i) {
        ASTNode child = (ASTNode) optionsNode.getChild(i);
        if (child.getToken().getType() == HiveParser.TOK_PRIVGRANT) {
          privileges.add(tokenToPrivilege.get(HiveParser.TOK_PRIVGRANT));
        }
      }
    }

    GrantUserDesc gntUserDesc = null;
    if (grantLevel == PrivilegeLevel.GLOBAL_LEVEL) {
      gntUserDesc = new GrantUserDesc(privileges, grantLevel, users);
      genAuthorizeEntry(null, null, null, Privilege.GRANT_PRIV);
    } else if (grantLevel == PrivilegeLevel.DATABASE_LEVEL) {
      gntUserDesc = new GrantUserDesc(privileges, grantLevel, dbName, users);
      genAuthorizeEntry(dbName, null, null, Privilege.GRANT_PRIV);
    } else if (grantLevel == PrivilegeLevel.TABLE_LEVEL) {
      gntUserDesc = new GrantUserDesc(privileges, grantLevel, dbName, tbName, users);
      genAuthorizeEntry(dbName, tbName, null, Privilege.GRANT_PRIV);
    } else {
      throw new SemanticException("Unsupport privilege level.");
    }
    rootTasks.add(TaskFactory.get(new DCLWork(gntUserDesc), conf));
  }

  private void analyzeRevoke(final ASTNode ast) throws SemanticException {
    List<Privilege> privileges = new ArrayList<Privilege>();
    List<String> users = new ArrayList<String>();
    PrivilegeLevel revokeLevel = null;

    ASTNode privsNode = (ASTNode) ast.getChild(0);
    boolean isAll = false;
    for (int i = 0; i < privsNode.getChildCount(); ++i) {
      ASTNode child = (ASTNode) privsNode.getChild(i);
      int privType = child.getToken().getType();
      if (privType != HiveParser.TOK_PRIVALL) {
        privileges.add(tokenToPrivilege.get(privType));
      } else {
        isAll = true;
      }
    }

    ASTNode targetNode = (ASTNode) ast.getChild(1);
    int targetLevel = targetNode.getToken().getType();
    String dbName = MetaStoreUtils.DEFAULT_DATABASE_NAME;
    String tbName = null;
    if (targetLevel == HiveParser.TOK_PRIV_GLOBAL) {
      // global level
      revokeLevel = PrivilegeLevel.GLOBAL_LEVEL;
      if (isAll == true) {
        privileges.addAll(PrivilegeLevel.GLOBAL_LEVEL.getPrivileges());
      }
    } else if (targetLevel == HiveParser.TOK_PRIV_DB) {
      // db level
      revokeLevel = PrivilegeLevel.DATABASE_LEVEL;
      if (isAll == true) {
        privileges.addAll(PrivilegeLevel.DATABASE_LEVEL.getPrivileges());
      }
      if (targetNode.getChildCount() == 1) {
        dbName = targetNode.getChild(0).getText();
      }
    } else if (targetLevel == HiveParser.TOK_PRIV_TABLE) {
      // table level
      revokeLevel = PrivilegeLevel.TABLE_LEVEL;
      if (isAll == true) {
        privileges.addAll(PrivilegeLevel.TABLE_LEVEL.getPrivileges());
      }
      if (targetNode.getChildCount() == 2) {
        dbName = targetNode.getChild(0).getText();
        tbName = targetNode.getChild(1).getText();
      } else {
        tbName = targetNode.getChild(0).getText();
      }
    }

    ASTNode usersNode = (ASTNode) ast.getChild(2);
    for (int i = 0; i < usersNode.getChildCount(); ++i) {
      ASTNode child = (ASTNode) usersNode.getChild(i);
      users.add(unescapeIdentifier(child.getChild(0).getText()));
    }

    RevokeUserDesc revokeUserDesc = null;
    if (revokeLevel == PrivilegeLevel.GLOBAL_LEVEL) {
      revokeUserDesc = new RevokeUserDesc(privileges, revokeLevel, users);
      genAuthorizeEntry(null, null, null, Privilege.GRANT_PRIV);
    } else if (revokeLevel == PrivilegeLevel.DATABASE_LEVEL) {
      revokeUserDesc = new RevokeUserDesc(privileges, revokeLevel, dbName, users);
      genAuthorizeEntry(dbName, null, null, Privilege.GRANT_PRIV);
    } else if (revokeLevel == PrivilegeLevel.TABLE_LEVEL) {
      revokeUserDesc = new RevokeUserDesc(privileges, revokeLevel, dbName, tbName, users);
      genAuthorizeEntry(dbName, tbName, null, Privilege.GRANT_PRIV);
    } else {
      throw new SemanticException("Unsupport privilege level.");
    }
    rootTasks.add(TaskFactory.get(new DCLWork(revokeUserDesc), conf));
  }

  private void analyzeShowGrants(ASTNode ast)
      throws SemanticException {
    ShowGrantsDesc showGntsDesc;
    if (ast.getChildCount() == 1) {
      String userName = ast.getChild(0).getText();
      showGntsDesc = new ShowGrantsDesc(ctx.getResFile(), userName);
    } else {
      showGntsDesc = new ShowGrantsDesc(ctx.getResFile());
    }
    rootTasks.add(TaskFactory.get(new DCLWork(showGntsDesc), conf));
    privilege = Privilege.SUPER_PRIV;
    setFetchTask(createFetchTask(showGntsDesc.getSchema()));
  }

  private FetchTask createFetchTask(String schema) {
    Properties prop = new Properties();

    prop.setProperty(Constants.SERIALIZATION_FORMAT, "9");
    prop.setProperty(Constants.SERIALIZATION_NULL_FORMAT, " ");
    String[] colTypes = schema.split("#");
    prop.setProperty("columns", colTypes[0]);
    prop.setProperty("columns.types", colTypes[1]);

    FetchWork fetch = new FetchWork(
        ctx.getResFile().toString(),
        new TableDesc(LazySimpleSerDe.class, TextInputFormat.class,
            IgnoreKeyTextOutputFormat.class, prop),
        -1
        );
    fetch.setSerializationNullFormat(" ");
    return (FetchTask) TaskFactory.get(fetch, this.conf);
  }

  private void checkPrivsValid(PrivilegeLevel level, Privilege priv)
      throws SemanticException {
    if (!level.getPrivileges().contains(priv) && (priv != Privilege.GRANT_PRIV)) {
      throw new SemanticException(ErrorMsg.ILLEGE_GRANTREVOKE_COMMAND.getMsg());
    }
  }
}
