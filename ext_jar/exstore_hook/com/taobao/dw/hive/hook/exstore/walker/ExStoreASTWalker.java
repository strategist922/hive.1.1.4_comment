package com.taobao.dw.hive.hook.exstore.walker;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.common.ExStoreException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Stack;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.ASTNode;

public class ExStoreASTWalker
  implements Walkable
{
  public void walk(ASTNode node, ExStoreContext context)
    throws ExStoreException
  {
    QueryWalker qryp = new QueryWalker();
    try {
      node.dump();
    } catch (ClassCastException e) {
      return;
    }
    ASTNode qastn = findQuery(node);
    try {
      qryp.walk(qastn, context);
      qryp.transform();
      if (context.isChange())
        System.err.println("The SQL is rewritten by Exstore Hook.");
    }
    catch (ExStoreException e) {
      System.err.println(e.getMessage());
      switch (e.getStateCode()) {
      case 3:
    	  throw e;
      }
      
    }
    
  }
//找出第一个TOK_QUERY节点
  private ASTNode findQuery(ASTNode fq)
  {
    Stack st = new Stack();
    st.push(fq);
    ASTNode result = null;
  outter:
    while (!st.isEmpty()) {
      fq = (ASTNode)st.pop();
      if ((fq.getToken() != null) && (ExStoreContext.TOK_QUERY == fq.getType())) {
        return fq;
      }
      ArrayList<Node> children = fq.getChildren();
      if (children != null) {
        for (Node n : children) {
          ASTNode astNode = (ASTNode)n;
          st.push(astNode);
          if (ExStoreContext.TOK_QUERY == astNode.getType()) {
            result = astNode;
            break outter;
          }
        }
      }
    }
   return result;
  }
}