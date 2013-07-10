package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import com.taobao.dw.hive.hook.exstore.node.Column;
import com.taobao.dw.hive.hook.exstore.node.Table;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.tree.Tree;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public class SelectWalker extends AbstractWalker
  implements Walkable
{
  public void walk(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    super.walk(node, context);
    findSelectColumnNode(node);
  }

  private void findSelectColumnNode(ASTNode colNode)
  {
    if (null != colNode) {
      int childCount = colNode.getChildCount();

      for (int i = 0; i < childCount; i++) {
        ASTNode child = (ASTNode)colNode.getChild(i);
        if ((colNode != null) && (colNode.getType() == ExStoreContext.TOK_SELECT))
          handleSelExprNode(child);
      }
    }
  }

  private void handleSelExprNode(ASTNode node)
  {
    List columns = this.context.getColumns();

    int childCount = node.getChildCount();
    for (int i = childCount - 1; i >= 0; i--) {
      ASTNode child = (ASTNode)node.getChild(i);
      int type = child.getType();
      if (ExStoreContext.DOT == type) {
        String columnName = child.getChild(1).getText();
        String ailes = child.getChild(0).getChild(0).getText();
        Table table = (Table)this.context.getTables().get(ailes);
        if (table == null) {
          Collection values = this.context.getTables().values();
          for (Table t : values) {
            if ((ailes.equalsIgnoreCase(t.getAlias())) || (ailes.equalsIgnoreCase(t.getName()))) {
              table = t;
              break;
            }
          }
        }
        if (table == null) {
          continue;
        }
        Column column = table.addNewColumn(columnName, node, 1, ailes);
        columns.add(column);
        if (columnName.equalsIgnoreCase(table.getDiffPt()))
          table.setExistSelectDiffPt(true);
      }
      else if (type == ExStoreContext.TOK_TABLE_OR_COL) {
        String columnName = child.getChild(0).getText();
        Collection extables = this.context.getExtables().values();
        Table table = null;
        Iterator i$ = extables.iterator(); if (i$.hasNext()) { Table t = (Table)i$.next();
          table = t;
        }

        if (table == null) {
          continue;
        }
        Column column = table.addNewColumn(columnName, node, 1, null);
        columns.add(column);
        if (columnName.equalsIgnoreCase(table.getDiffPt()))
          table.setExistSelectDiffPt(true);
      }
      else {
        handleSelExprNode(child);
      }
    }
  }
}