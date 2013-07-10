package com.taobao.data.hive.hook;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.hadoop.hive.ql.exec.FileSinkOperator;
import org.apache.hadoop.hive.ql.lib.Dispatcher;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.FileSinkDesc;

public class FileSinkDispatcher implements Dispatcher {

  private final List<String> paths;

  public FileSinkDispatcher() {
    super();
    paths = new ArrayList<String>();
  }

  public List<String> getPaths() {
    return paths;
  }

  @Override
  public Object dispatch(Node nd, Stack<Node> ndStack, Object... nodeOutputs)
      throws SemanticException {
    if (nd instanceof FileSinkOperator) {
      FileSinkDesc work = ((FileSinkOperator) nd).getConf();
      String path = work.getDirName();
      paths.add(path);
      return path;
    }
    return null;
  }
}
