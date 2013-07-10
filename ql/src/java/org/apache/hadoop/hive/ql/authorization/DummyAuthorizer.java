package org.apache.hadoop.hive.ql.authorization;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.ql.session.SessionState;

public class DummyAuthorizer implements Authorizer {

  private Configuration conf;

  private void printInfo(String userName, AuthorizeEntry entry, String message) {
    StringBuilder sb = new StringBuilder(message);
    sb.append("Request privilege " + entry.getRequiredPrivs());
    String db = entry.getDb() == null ? "<null>" : entry.getDb().getName();
    String tbl = entry.getTable() == null ? "<null>" : entry.getTable().getTableName();
    String fields = "";
    if (entry.getFields() != null && !entry.getFields().isEmpty()) {
      for (FieldSchema f : entry.getFields()) {
        fields += f.getName() + ",";
      }
    } else {
      fields = "<null>";
    }
    sb.append(String.format(" on (db=%s, tbl=%s, columns=[%s])", db, tbl, fields));
    sb.append(" with " + entry.getPrivLevel().name());
    sb.append(" for user ");
    sb.append(userName);
    SessionState.getConsole().printError(sb.toString());
  }

  @Override
  public void authorize(String userName, AuthorizeEntry entry) throws AuthorizeException {
    printInfo(userName, entry, "=Authorize=: ");
  }

  @Override
  public Configuration getConf() {
    return conf;
  }

  @Override
  public void setConf(Configuration conf) {
    this.conf = conf;
  }

  @Override
  public void grant(String userName, String grantorName, AuthorizeEntry entry)
      throws AuthorizeException {
    printInfo(userName, entry, "=Grant=: ");
  }

  @Override
  public void revoke(String userName, String revokerName, AuthorizeEntry entry)
      throws AuthorizeException {
    printInfo(userName, entry, "=Revoke=: ");
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
