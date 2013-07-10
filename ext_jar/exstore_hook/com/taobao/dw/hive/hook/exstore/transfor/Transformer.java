package com.taobao.dw.hive.hook.exstore.transfor;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import com.taobao.dw.hive.hook.exstore.common.Interval;
import com.taobao.dw.hive.hook.exstore.common.Util;
import com.taobao.dw.hive.hook.exstore.node.Column;
import com.taobao.dw.hive.hook.exstore.node.Table;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.BaseTree;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.ParseDriver;

public class Transformer
{
  public void transform(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    if (context.getExtables().isEmpty()) {
      return;
    }
    Collection<Table> extables = context.getExtables().values();
    for (Table table : extables) {
      if (!table.isChange()) {
        continue;
      }
      ASTNode child = (ASTNode)table.getNode().getChild(0);

      child.getToken().setText(table.getExName());//修改为ex表名
      context.setChange(true);
      tableTransform(node, table);
      for (Column column : table.getPtColumns())// 针对分区列
        try {
          ptTransform(table, column);
        }
        catch (NullPointerException e) {
        }
      for (Column column : table.getDiffPtColumns())
        mvDiffPt(column);
    }
  }
/**
 *  查找node树下为原始表名的所有节点，修改为极限表名
 */
  private void tableTransform(ASTNode node, Table table)
  {
    if ((node == null) || (node.getType() == ExStoreContext.TOK_SUBQUERY) || (node.getType() == ExStoreContext.TOK_DESTINATION))
      return;
    ArrayList<Node> children = node.getChildren();
    if (children == null)
      return;
    for (Node child : children) {
      ASTNode child2 = (ASTNode)child;

      if (child2.getText().equalsIgnoreCase(table.getName())) {
        child2.getToken().setText(table.getExName());
      }
      tableTransform(child2, table);
    }
  }
/**
 * 
 * @param table
 * @param column 分区列
 */
  private void ptTransform(Table table, Column column) {
    Interval ptInterval = table.getPtInterval();
    Interval interval = column.getInterval();
    String alias = column.getAlias();
    if (alias.equalsIgnoreCase(table.getName())) {
      alias = table.getExName();
    }

    Interval and = Interval.and(ptInterval, interval);

    ASTNode newNode = genPtNode(and, alias);
    ASTNode cn = column.getNode();
    cn.getToken().setText(newNode.getText());
    int childCount = cn.getChildCount();
    cn.getToken().setType(newNode.getType());
    childCount = newNode.getChildCount();
    for (int i = 0; i < childCount; i++) {
      Tree child = newNode.getChild(i);
      cn.setChild(i, (BaseTree)child);
    }
  }

  private void mvDiffPt(Column column) {
    int childSequence = 1 - column.getChildSequence();
    ASTNode daddy = column.getDaddy();
    ASTNode child = (ASTNode)daddy.getChild(childSequence);
    daddy.getToken().setText(child.getText());
    daddy.getToken().setType(child.getType());
    int childCount2 = child.getChildCount();
    for (int i = 0; i < childCount2; i++)
      daddy.setChild(i, (BaseTree)child.getChild(i));
  }

  private ASTNode genPtNode(Interval interval, String tabOrAlise)
  {
    Date end = null;
    try {
      Util.cal.setTime(Util.yyyyMMdd.parse(interval.getStart()));
      end = Util.yyyyMMdd.parse(interval.getEnd());
    } catch (java.text.ParseException e) {
      e.printStackTrace();
      return null;
    }
    boolean flag = true;

    StringBuilder sb = new StringBuilder();
    sb.append("(");
    boolean notEmpty = StringUtils.isNotEmpty(tabOrAlise);
    while (flag)
    {
      String startStr = Util.yyyyMMdd.format(Util.cal.getTime());
      Util.cal.add(2, 1);
      Util.cal.set(5, 1);
      Util.cal.add(5, -1);
      flag = Util.cal.getTimeInMillis() < end.getTime();
      String endStr;
      if (flag)
        endStr = Util.yyyyMMdd.format(Util.cal.getTime());
      else {
        endStr = interval.getEnd();
      }
      Util.cal.add(5, 1);
      if (notEmpty) {
        sb.append("( ");
        sb.append(new StringBuilder().append(tabOrAlise).append(".pt_start <= '").append(endStr).toString());
        sb.append("' and ");
        sb.append(new StringBuilder().append(tabOrAlise).append(".pt_end > '").append(startStr).toString());
        sb.append("' ) or");
      } else {
        sb.append(new StringBuilder().append("( pt_start <= '").append(endStr).append("' and pt_end > '").append(startStr).append("' ) or").toString());
      }
    }
    int length = sb.length();
    sb.delete(length - 2, length).append(")");//删除多余的最后一个'or'

    String ptstr = sb.insert(0, "select * from a where ").toString();
    ASTNode parse = null;
    try {
      parse = (ASTNode)Util.parseDriver.parse(ptstr).getChild(0).getChild(1).getChild(2).getChild(0);
    } catch (org.apache.hadoop.hive.ql.parse.ParseException e) {
      e.printStackTrace();
    }
    return parse;
  }
}