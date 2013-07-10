package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import com.taobao.dw.hive.hook.exstore.node.Table;
import java.util.Map;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public class TableWalker extends AbstractWalker
  implements Walkable
{
  public void walk(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    super.walk(node, context);
    String tableName = ((ASTNode)node.getChild(0)).getText();
    Table t = new Table(node);
    t.setName(tableName);
    int childCount = node.getChildCount();

    String tabAlias = null;
    if (childCount == 2)
      tabAlias = ((ASTNode)node.getChild(1)).getText();
    else {
      tabAlias = null;
    }
    t.setAlias(tabAlias);
    String key = tabAlias == null ? tableName : tabAlias;
    context.putTable(key, t);
    String[] row = (String[])ExStoreContext.getExTableMap().get(tableName);
    if (row != null) {
      t.setExName(row[1]);
      t.setDiffPt(row[3]);
      context.getExtables().put(key, t);
    }
  }
}