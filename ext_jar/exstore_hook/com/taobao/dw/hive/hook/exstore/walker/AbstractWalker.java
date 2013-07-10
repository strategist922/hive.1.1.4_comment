package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public abstract class AbstractWalker
  implements Walkable
{
  protected ExStoreContext context;

  public void walk(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    this.context = context;
  }

  public ExStoreContext getContext() {
    return this.context;
  }

  public void setContext(ExStoreContext context) {
    this.context = context;
  }
}