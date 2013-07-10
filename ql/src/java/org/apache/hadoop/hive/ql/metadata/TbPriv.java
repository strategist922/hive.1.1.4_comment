package org.apache.hadoop.hive.ql.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.hadoop.hive.ql.authorization.Privilege;


public class TbPriv {
  private org.apache.hadoop.hive.metastore.api.TbPriv tTbPriv;

  protected TbPriv() throws HiveException {
  }

  public TbPriv(String dbName, String userName, String grantor, String tbName) {
    // fill in defaults
    initEmpty();
    getTTbPriv().setDbName(dbName);
    getTTbPriv().setUserName(userName);
    getTTbPriv().setGrantor(grantor);
    getTTbPriv().setTbName(tbName);
  }

  private void initEmpty() {
    this.setTbPriv(new org.apache.hadoop.hive.metastore.api.TbPriv());
  }

  public void setTbPriv(org.apache.hadoop.hive.metastore.api.TbPriv tbPriv) {
    tTbPriv = tbPriv;
  }

  public org.apache.hadoop.hive.metastore.api.TbPriv getTTbPriv() {
    return tTbPriv;
  }

  public String getDbName() {
    return tTbPriv.getDbName();
  }

  public void setDbName(String dbName) {
    tTbPriv.setDbName(dbName);
  }

  public String getUserName() {
    return tTbPriv.getUserName();
  }

  public void setUserName(String userName) {
    tTbPriv.setUserName(userName);
  }

  public String getTableName() {
    return tTbPriv.getTbName();
  }

  public void setTableName(String tableName) {
    tTbPriv.setTbName(tableName);
  }

  public String getGrantor() {
    return tTbPriv.getGrantor();
  }

  public void setGrantor(String grantor) {
    tTbPriv.setGrantor(grantor);
  }

  public int getTimeStamp() {
    return tTbPriv.getTimeStamp();
  }

  public void setTimeStamp(int timeStamp) {
    tTbPriv.setTimeStamp(timeStamp);
  }

  public boolean hasSelect_priv() {
    return tTbPriv.isSelect_priv();
  }

  public void setSelect_priv(boolean select_priv) {
    tTbPriv.setSelect_priv(select_priv);
  }

  public boolean hasInsert_priv() {
    return tTbPriv.isInsert_priv();
  }

  public void setInsert_priv(boolean insert_priv) {
    tTbPriv.setInsert_priv(insert_priv);
  }

  public boolean hasDrop_priv() {
    return tTbPriv.isDrop_priv();
  }

  public void setDrop_priv(boolean drop_priv) {
    tTbPriv.setDrop_priv(drop_priv);
  }

  public boolean hasGrant_priv() {
    return tTbPriv.isGrant_priv();
  }

  public void setGrant_priv(boolean grant_priv) {
    tTbPriv.setGrant_priv(grant_priv);
  }

  public boolean hasAlter_priv() {
    return tTbPriv.isAlter_priv();
  }

  public void setAlter_priv(boolean alter_priv) {
    tTbPriv.setAlter_priv(alter_priv);
  }

  public boolean hasPriv(Privilege priv) {
    switch(priv) {
      case ALTER_PRIV :
        return this.hasAlter_priv();
      case DROP_PRIV :
        return this.hasDrop_priv();
      case GRANT_PRIV :
        return this.hasGrant_priv();
      case INSERT_PRIV :
        return this.hasInsert_priv();
      case SELECT_PRIV :
        return this.hasSelect_priv();
      default :
          return false;
    }
  }

  public boolean hasPriv(Collection<Privilege> privs) {
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
    if(hasDrop_priv()) {
      allPriv.add(Privilege.DROP_PRIV);
    }
    if(hasGrant_priv()) {
      allPriv.add(Privilege.GRANT_PRIV);
    }
    if(hasAlter_priv()) {
      allPriv.add(Privilege.ALTER_PRIV);
    }
    return allPriv;
  }
}
