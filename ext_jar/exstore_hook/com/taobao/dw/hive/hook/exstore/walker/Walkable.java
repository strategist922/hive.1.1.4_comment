package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public abstract interface Walkable
{
  public abstract void walk(ASTNode paramASTNode, ExStoreContext paramExStoreContext)
    throws ExStoreException;
}