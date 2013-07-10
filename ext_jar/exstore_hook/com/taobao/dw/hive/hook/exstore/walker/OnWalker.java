package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public class OnWalker extends ConditionWalker
  implements Walkable
{
  public void walk(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    super.walk(node, context);
    conditionWalker(node, 0, null, context, 4);
  }
}