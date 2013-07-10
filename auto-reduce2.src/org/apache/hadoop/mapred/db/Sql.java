package org.apache.hadoop.mapred.db;

import java.util.Vector;

public class Sql
{
  protected Vector<Cell> vConditions = new Vector();
  protected Vector<Cell> vTargets = new Vector();
  protected String tableName;
  protected String querySql;
  protected String insertSql;

  public Sql(String tableName)
  {
    this.tableName = tableName;
  }

  public void addCondition(String colName, int colVal) {
    Cell c = new Cell(Cell.Type.NUMBER, colName, String.valueOf(colVal));
    this.vConditions.add(c);
  }

  public void addCondition(String colName, long colVal) {
    Cell c = new Cell(Cell.Type.NUMBER, colName, String.valueOf(colVal));
    this.vConditions.add(c);
  }

  public void addCondition(String colName, String colVal) {
    Cell c = new Cell(colName, colVal);
    this.vConditions.add(c);
  }

  public void addCondition(Cell c) {
    this.vConditions.add(c);
  }

  public void addTarget(String colName) {
    Cell c = new Cell(colName);
    this.vTargets.add(c);
  }

  public void addTarget(String colName, int colVal)
  {
    Cell c = new Cell(colName, colVal);
    this.vTargets.add(c);
  }

  public void addTargets(String colName, long colVal) {
    Cell c = new Cell(colName, colVal);
    this.vTargets.add(c);
  }

  public void addTarget(String colName, String colVal)
  {
    Cell c = new Cell(colName, colVal);
    this.vTargets.add(c);
  }

  public void addTarget(Cell c)
  {
    this.vTargets.add(c);
  }

  public void setQuerySql(String sql) {
    this.querySql = sql;
  }

  public void setInsertSql(String sql) {
    this.insertSql = sql;
  }

  public String getQuerySql() {
    if (this.querySql != null) {
      return this.querySql;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("select ").append(getColNames(this.vTargets, ",", "*"));
    sb.append(" from ").append(this.tableName);
    String conditions = getConditions(this.vConditions, " and ");
    if ((conditions != null) && (!"".equals(conditions))) {
      sb.append(" where ").append(conditions);
    }
    sb.append(";");
    this.querySql = sb.toString();
    return this.querySql;
  }

  public String getInsertSql() {
    if (this.insertSql != null) {
      return this.insertSql;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("insert into ").append(this.tableName);
    sb.append(" set ");
    sb.append(getConditions(this.vTargets, ","));
    sb.append(";");
    this.insertSql = sb.toString();
    return this.insertSql;
  }

  private String getConditions(Vector<Cell> v, String split) {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    for (Cell c : v) {
      if (i > 0) {
        sb.append(split);
      }
      i++;
      switch (1.$SwitchMap$org$apache$hadoop$mapred$db$Cell$Type[c.getType().ordinal()]) {
      case 1:
        sb.append(new StringBuilder().append(c.getName()).append(" = ").append(c.getVal()).toString());
        break;
      case 2:
        sb.append(new StringBuilder().append(c.getName()).append(" = '").append(c.getVal()).append("'").toString());
        break;
      case 3:
        sb.append(new StringBuilder().append(c.getName()).append(" = ").append(c.getVal()).toString());
      }
    }

    return sb.toString();
  }

  private String getColNames(Vector<Cell> v, String split, String defVal) {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    for (Cell c : v) {
      if (i > 0) {
        sb.append(split);
      }
      i++;
      sb.append(c.getName());
    }
    if (sb.length() == 0) {
      return defVal;
    }
    return sb.toString();
  }
}