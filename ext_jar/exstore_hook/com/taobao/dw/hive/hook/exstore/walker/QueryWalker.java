package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import com.taobao.dw.hive.hook.exstore.common.HFileSystem;
import com.taobao.dw.hive.hook.exstore.common.Interval;
import com.taobao.dw.hive.hook.exstore.common.Util;
import com.taobao.dw.hive.hook.exstore.node.Table;
import com.taobao.dw.hive.hook.exstore.transfor.Transformer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.runtime.tree.Tree;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.HiveSemanticAnalyzerHookContext;

public class QueryWalker extends AbstractWalker
  implements Walkable
{
  private List<QueryWalker> subQueryList;
  private ASTNode node;
  private ExStoreContext context;

  public void walk(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    this.node = node;
    this.context = context;
    super.walk(node, context);

    if (null == node) {
      return;
    }
    interWalker(node, context);
    validate(context);
  }

  public void transform() {
    if (this.subQueryList != null) {
      for (QueryWalker qw : this.subQueryList) {
        qw.transform();
        if (qw.getContext().isChange()) {
          this.context.setChange(true);
        }
      }
    }
    Transformer tf = new Transformer();
    tf.transform(this.node, this.context);
  }

  private Collection<Table> validate(ExStoreContext context) {
    Map tables = context.getExtables();
    Collection<Table> extables = tables.values();
    for (Table table : extables) {
      if (!tableValidate(table)) {
        continue;
      }
      table.setChange(true);
    }
    return extables;
  }

  private boolean tableValidate(Table table) {
    if (table.getPtInterval().getStart().equals("20030101")) {
      throw new ExStoreException("Pt no lower bound", 3);
    }
    HFileSystem.setContext(ExStoreContext.hiveContext);
    HFileSystem hFileSystem = new HFileSystem();
    boolean result = true;
    result = (result) && (!table.isExistSelectDiffPt());
    result = (result) && (existPtBefore31days(table));
    try {
      result = (result) && (allDone(table, hFileSystem));
      result = (result) && (!existLock(table, hFileSystem));
    } catch (ParseException e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }

  private boolean existPtBefore31days(Table table) {
    return table.getPtInterval().getStart().compareTo(Interval._31DAYS_AGO) < 0;
  }

  private boolean existLock(Table table, HFileSystem hFileSystem) throws ParseException {
    Interval interval = table.getPtInterval();
    String start = interval.getStart();
    Date parse = Util.yyyyMMdd.parse(start);
    Util.cal.setTime(parse);
    Date end = Util.yyyyMMdd.parse(interval.getEnd());
    while (Util.cal.getTimeInMillis() < end.getTime()) {
      String month = Util.yyyyMM.format(Util.cal.getTime());
      if (hFileSystem.isLock(table.getExName(), month)) {
        return true;
      }
      Util.cal.add(2, 1);
    }
    return false;
  }

  private boolean allDone(Table table, HFileSystem hFileSystem) throws ParseException {
    Interval interval = table.getPtInterval();
    FileStatus[] listDone = hFileSystem.listDone(table.getExName(), interval);
    HashMap hashmap = new HashMap();
    for (FileStatus fs : listDone) {
      hashmap.put(fs.getPath().getName(), null);
    }
    String start = interval.getStart();
    Date parse = Util.yyyyMMdd.parse(start);
    Util.cal.setTime(parse);
    Date end = Util.yyyyMMdd.parse(interval.getEnd());
    while (Util.cal.getTimeInMillis() <= end.getTime())
    {
      String fileName = Util.yyyyMMdd.format(Util.cal.getTime()) + ".done";
      if (!hashmap.containsKey(fileName)) {
        return false;
      }
      Util.cal.add(5, 1);
    }
    return true;
  }

  private void interWalker(ASTNode nf, ExStoreContext context) throws ExStoreException {
    ArrayList<Node> children = nf.getChildren();
    if (children == null) {
      return;
    }
    for (Node n : children) {
      ASTNode node = (ASTNode)n;
      int type = node.getType();
      if (type == ExStoreContext.TOK_DESTINATION)
        continue;
      if (type == ExStoreContext.TOK_SUBQUERY) {
        if (this.subQueryList == null) {
          this.subQueryList = new ArrayList();
        }
        QueryWalker qp = new QueryWalker();
        this.subQueryList.add(qp);
        HiveSemanticAnalyzerHookContext hive = ExStoreContext.getHiveContext();
        ExStoreContext subContext = new ExStoreContext(hive);
        ASTNode subquery = (ASTNode)node.getChild(0);
        qp.walk(subquery, subContext);
      } else if (type == ExStoreContext.TOK_TABREF) {
        TableWalker tp = new TableWalker();
        tp.walk(node, context);
      } else if (type == ExStoreContext.TOK_WHERE) {
        if (context.getExtables().isEmpty()) {
          return;
        }
        WhereWalker wp = new WhereWalker();
        wp.walk(node, getContext());
      } else if (type == ExStoreContext.TOK_SELECT) {
        if (context.getExtables().isEmpty()) {
          return;
        }
        SelectWalker sp = new SelectWalker();
        sp.walk(node, getContext());
      } else if ((type == ExStoreContext.TOK_GROUPBY) || (type == ExStoreContext.TOK_ORDERBY) || (type == ExStoreContext.TOK_SORTBY)) {
        if (context.getExtables().isEmpty()) {
          return;
        }
        groupByOrderByETCwalker(node, context);
      } else {
        interWalker(node, context);
        if ((type == ExStoreContext.TOK_JOIN) || (type == ExStoreContext.TOK_FULLOUTERJOIN) || (type == ExStoreContext.TOK_RIGHTOUTERJOIN) || (type == ExStoreContext.TOK_LEFTOUTERJOIN) || (type == ExStoreContext.TOK_UNIQUEJOIN) || (type == ExStoreContext.TOK_LEFTSEMIJOIN))
        {
          if (context.getExtables().isEmpty()) {
            return;
          }
          ASTNode child = (ASTNode)node.getChild(2);
          if ((node.getChildCount() > 2) && (child != null)) {
            OnWalker onWalker = new OnWalker();
            onWalker.walk(child, context);
          }
        }
      }
    }
  }

  private void groupByOrderByETCwalker(ASTNode nf, ExStoreContext context)
  {
    ArrayList<Node> children = nf.getChildren();
    if (children == null) {
      return;
    }
    for (Node n : children) {
      ASTNode node = (ASTNode)n;
      String ailas = "";
      String columnName = null;
      Tree lchild = node.getChild(0);
      if (lchild == null) {
        return;
      }
      int type = node.getType();
      if ((type == ExStoreContext.TOK_TABLE_OR_COL) || (type == ExStoreContext.DOT)) {
        Table table = null;
        if (type == ExStoreContext.DOT) {
          ailas = lchild.getChild(0).getText();
          columnName = node.getChild(1).getText();
          table = (Table)context.getTables().get(ailas);
          if (table == null) {
            Collection<Table> values = context.getTables().values();
            for (Table t : values)
              if ((ailas.equalsIgnoreCase(t.getAlias())) || (ailas.equalsIgnoreCase(t.getName()))) {
                table = t;
                break;
              }
          }
        }
        else if (ExStoreContext.TOK_TABLE_OR_COL == type) {
          columnName = lchild.getText();
          Collection extables = context.getExtables().values();
          Iterator i$ = extables.iterator(); if (i$.hasNext()) { Table t = (Table)i$.next();
            table = t;
          }
        }

        if (table == null) {
          return;
        }

        if ((columnName.equalsIgnoreCase("pt")) || (columnName.equalsIgnoreCase(table.getDiffPt())))
          throw new ExStoreException("Pt or Diff_pt should no at group order sort by clause!", 3);
      }
      else {
        groupByOrderByETCwalker(node, context);
      }
    }
  }
}