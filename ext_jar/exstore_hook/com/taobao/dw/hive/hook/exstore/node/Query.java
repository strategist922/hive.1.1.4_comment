package com.taobao.dw.hive.hook.exstore.node;

import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public class Query
{
  private ASTNode node;
  private List<Table> tabs;

  public Query(ASTNode qnode)
  {
    this.node = qnode;
    this.tabs = new ArrayList();
  }

  public ASTNode getNode() {
    return this.node;
  }

  public void setNode(ASTNode node) {
    this.node = node;
  }

  public List<Table> getTabs() {
    return this.tabs;
  }

  public void setTabs(List<Table> tabs) {
    this.tabs = tabs;
  }
}