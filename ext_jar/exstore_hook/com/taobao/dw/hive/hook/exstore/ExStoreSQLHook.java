package com.taobao.dw.hive.hook.exstore;

import com.taobao.dw.hive.hook.exstore.common.ExStoreContext;
import com.taobao.dw.hive.hook.exstore.walker.ExStoreASTWalker;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.AbstractSemanticAnalyzerHook;
import org.apache.hadoop.hive.ql.parse.HiveSemanticAnalyzerHookContext;
import org.apache.hadoop.hive.ql.parse.SemanticException;
//at org.apache.hadoop.hive.ql.metadata.HiveUtils.getSemanticAnalyzerHook Èë¿Ú¡£
public class ExStoreSQLHook extends AbstractSemanticAnalyzerHook
{
  public ASTNode preAnalyze(HiveSemanticAnalyzerHookContext hcontext, ASTNode astNode)
    throws SemanticException
  {
    ExStoreASTWalker walker = new ExStoreASTWalker();
    ExStoreContext context = new ExStoreContext(hcontext);
    walker.walk(astNode, context);
    return super.preAnalyze(hcontext, astNode);
  }
}