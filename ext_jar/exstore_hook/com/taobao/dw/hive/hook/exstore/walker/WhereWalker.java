package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public class WhereWalker extends ConditionWalker
  implements Walkable
{
  public void walk(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    super.walk(node, context);
    ASTNode child = (ASTNode)node.getChild(0);
    conditionWalker(child, 0, node, context, 2);
  }
}