package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import com.taobao.dw.hive.hook.exstore.common.Interval;
import com.taobao.dw.hive.hook.exstore.common.Util;
import com.taobao.dw.hive.hook.exstore.node.Column;
import com.taobao.dw.hive.hook.exstore.node.Table;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.antlr.runtime.tree.Tree;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public abstract class ConditionWalker extends AbstractWalker
  implements Walkable
{
  public void walk(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    super.walk(node, context);
  }

  protected void conditionWalker(ASTNode node, int childSequence, ASTNode daddy, ExStoreContext context, int columnType) {
    int type = node.getType();
    boolean isOperator = (type == ExStoreContext.EQUAL) || (type == ExStoreContext.GREATERTHAN) || (type == ExStoreContext.GREATERTHANOREQUALTO) || (type == ExStoreContext.LESSTHAN) || (type == ExStoreContext.LESSTHANOREQUALTO) || (type == ExStoreContext.NOTEQUAL);

    if (isOperator) {
      Tree lchild = node.getChild(0);
      String ailas = "";
      String columnName = null;
      type = lchild.getType();
      Table table = null;
      if (type == ExStoreContext.DOT) {
        ailas = lchild.getChild(0).getChild(0).getText();
        columnName = lchild.getChild(1).getText();
        table = (Table)context.getTables().get(ailas);
        if (table == null) {
          Collection values = context.getTables().values();
          for (Table t : values)
            if ((ailas.equalsIgnoreCase(t.getAlias())) || (ailas.equalsIgnoreCase(t.getName()))) {
              table = t;
              break;
            }
        }
      }
      else if (ExStoreContext.TOK_TABLE_OR_COL == type) {
        columnName = lchild.getChild(0).getText();
        Collection extables = context.getExtables().values();
        Iterator i$ = extables.iterator(); if (i$.hasNext()) { Table t = (Table)i$.next();
          table = t;
        }
      }

      if (table == null) {
        return;
      }
      Column column = table.addNewColumn(columnName, node, daddy, childSequence, columnType, ailas);
      if ((column.getType() & 0x8) == 8) {
        String value = node.getChild(1).getText();
        String dateStr = Util.checkPtValue(value);
        if (dateStr != null) {
          type = node.getType();
          String start = null;
          String end = null;
          if (type == ExStoreContext.LESSTHAN) {
            start = "20030101";
            end = Util.getDateString(dateStr, -1);
          } else if (type == ExStoreContext.EQUAL) {
            start = dateStr;
            end = dateStr;
          } else if (type == ExStoreContext.GREATERTHAN) {
            start = Util.getDateString(dateStr, 1);
            end = Interval.MAX;
          } else if (type == ExStoreContext.LESSTHANOREQUALTO) {
            start = "20030101";
            end = Util.getDateString(dateStr, 0);
          } else if (type == ExStoreContext.GREATERTHANOREQUALTO) {
            start = Util.getDateString(dateStr, 0);
            end = Interval.MAX;
          } else {
            System.err.println("========= require yuquan solve !");
          }
          Interval interval = new Interval(start, end);
          column.setInterval(interval);
          Interval ptInterval = table.getPtInterval();
          table.setPtInterval(merge(interval, ptInterval));
        }
      }
    } else {
      int childCount = node.getChildCount();
      for (int i = 0; i < childCount; i++) {
        Tree child = node.getChild(i);
        conditionWalker((ASTNode)child, i, node, context, columnType);
      }
    }
  }

  private Interval merge(Interval interval1, Interval interval2) {
    Interval interval = null;
    if (null == interval1) {
      interval = new Interval(interval2);
    } else if (null == interval2) {
      interval = new Interval(interval1);
    } else {
      String s1 = interval1.getStart();
      String s2 = interval2.getStart();
      String e1 = interval1.getEnd();
      String e2 = interval2.getEnd();
      String start = null;
      String end = null;
      if (s1.compareTo(s2) > 0)
        start = s2.equals("20030101") ? s1 : s2;
      else {
        start = s1.equals("20030101") ? s2 : s1;
      }
      if (e1.compareTo(e2) < 0)
        end = e2.equals(Interval.MAX) ? e1 : e2;
      else {
        end = e1.equals(Interval.MAX) ? e2 : e1;
      }
      if (e1.compareTo(s2) < 0)
        start = s1;
      if (e2.compareTo(s1) < 0)
        start = s2;
      interval = new Interval();
      interval.setStart(start);
      interval.setEnd(end);
    }
    return interval;
  }
}