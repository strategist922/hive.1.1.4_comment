package com.taobao.dw.hive.hook.exstore.node;

import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import com.taobao.dw.hive.hook.exstore.common.Interval;
import java.io.PrintStream;
import java.util.List;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public class Column
{
  public static final int SELECT_COLUMN = 1;
  public static final int WHERE_COLUMN = 2;
  public static final int ON_COLUMN = 4;
  public static final int PT_COLUMN = 8;
  private String alias;
  private String name;
  private ASTNode node;
  private ASTNode daddy;
  private Table table;
  private int childSequence;
  private int type;
  private Interval interval;

  public Interval getInterval()
  {
    return this.interval;
  }

  public void setInterval(Interval interval) {
    this.interval = interval;
  }

  public String getAlias() {
    return this.alias;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public int getChildSequence() {
    return this.childSequence;
  }

  public void setChildSequence(int childSequence) {
    this.childSequence = childSequence;
  }

  public ASTNode getDaddy() {
    return this.daddy;
  }

  public void setDaddy(ASTNode daddy) {
    this.daddy = daddy;
  }

  public String getName() {
    return this.name;
  }

  public ASTNode getNode() {
    return this.node;
  }

  public Table getTable() {
    return this.table;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNode(ASTNode node) {
    this.node = node;
  }

  public void setTable(Table table) {
    if (table == null) {
      System.err.println("table is null");
      return;
    }
    if ((this.type & 0xE) > 8)
      table.getPtColumns().add(this);
    else if (this.type == 9) {
      throw new ExStoreException("'PT' not allow in select clause.", 3);
    }
    this.table = table;
    switch (this.type & 0x7) {
    case 1:
      table.getSelectColumns().add(this);
      break;
    case 2:
      table.getWhereColumns().add(this);
      if (!this.name.equalsIgnoreCase(table.getDiffPt())) break;
      table.getDiffPtColumns().add(this); break;
    case 4:
      table.getOnColumns().add(this);
      if (!this.name.equalsIgnoreCase(table.getDiffPt())) break;
      table.getDiffPtColumns().add(this); break;
    case 3:
    default:
      throw new ExStoreException("column type not found", 2);
    }
  }

  public String toString()
  {
    return "Column [ tabAlias=" + this.alias + ", name=" + this.name + ", node=" + this.node + ", daddy=" + this.daddy + ", table=" + this.table.getName() + ", childSequence=" + this.childSequence + ", type=" + this.type + "]";
  }
}