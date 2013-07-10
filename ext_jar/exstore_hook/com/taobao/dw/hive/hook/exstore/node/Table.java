package com.taobao.dw.hive.hook.exstore.node;

import com.taobao.dw.hive.hook.exstore.common.Interval;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public class Table
{
  private String alias;
  private boolean change;
  private boolean existSelectDiffPt;
  private List<Column> columns = new ArrayList();
  private List<Column> selectColumns = new ArrayList();
  private List<Column> ptColumns = new ArrayList();//·ÖÇøÁÐ
  private List<Column> onColumns = new ArrayList();
  private List<Column> whereColumns = new ArrayList();
  private List<Column> diffPtColumns = new ArrayList();
  private String diffPt;
  private String exName;
  private String name;
  private Interval ptInterval = new Interval("20030101", Interval.MAX);
  private ASTNode node;

  public Interval getPtInterval()
  {
    return this.ptInterval;
  }

  public void setPtInterval(Interval ptInterval) {
    this.ptInterval = ptInterval;
  }

  public List<Column> getDiffPtColumns() {
    return this.diffPtColumns;
  }

  public void setDiffPtColumns(List<Column> diffPtColumns) {
    this.diffPtColumns = diffPtColumns;
  }

  public Table(ASTNode tnode) {
    this.node = tnode;
  }

  public void addColumn(Column c) {
    this.columns.add(c);
  }

  public boolean isExistSelectDiffPt() {
    return this.existSelectDiffPt;
  }

  public List<Column> getPtColumns() {
    return this.ptColumns;
  }

  public void setPtColumns(List<Column> ptColumns) {
    this.ptColumns = ptColumns;
  }

  public void setExistSelectDiffPt(boolean existSelectDiffPt) {
    this.existSelectDiffPt = existSelectDiffPt;
  }

  public String getAlias() {
    return this.alias;
  }

  public List<Column> getColumns() {
    return this.columns;
  }

  public String getDiffPt() {
    return this.diffPt;
  }

  public String getExName() {
    return this.exName;
  }

  public String getName() {
    return this.name;
  }

  public ASTNode getNode() {
    return this.node;
  }

  public boolean isChange() {
    return this.change;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public void setChange(boolean change) {
    this.change = change;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }

  public void setDiffPt(String diffPt) {
    this.diffPt = diffPt;
  }

  public void setExName(String exName) {
    this.exName = exName;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNode(ASTNode node) {
    this.node = node;
  }

  public List<Column> getSelectColumns() {
    return this.selectColumns;
  }

  public void setSelectColumns(List<Column> selectColumns) {
    this.selectColumns = selectColumns;
  }

  public List<Column> getOnColumns() {
    return this.onColumns;
  }

  public void setOnColumns(List<Column> onColumns) {
    this.onColumns = onColumns;
  }

  public List<Column> getWhereColumns() {
    return this.whereColumns;
  }

  public void setWhereColumns(List<Column> whereColumns) {
    this.whereColumns = whereColumns;
  }

  public Column addNewColumn(String columnName, ASTNode node, int columnType, String alias) {
    Column column = new Column();
    column.setType(columnName.equalsIgnoreCase("pt") ? columnType | 0x8 : columnType);
    column.setName(columnName);
    column.setNode(node);
    column.setTable(this);
    column.setAlias(alias);
    return column;
  }

  public Column addNewColumn(String columnName, ASTNode node, ASTNode daddy, int childSequence, int columnType, String alias)
  {
    Column column = addNewColumn(columnName, node, columnType, alias);
    column.setChildSequence(childSequence);
    column.setDaddy(daddy);
    return column;
  }

  public String toString()
  {
    return "Table [alias=" + this.alias + ", change=" + this.change + ", existSelectPt=" + this.existSelectDiffPt + ", columns=" + this.columns.size() + ", diffPt=" + this.diffPt + ", exName=" + this.exName + ", name=" + this.name + ", node=" + this.node + "]";
  }
}