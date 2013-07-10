package org.apache.hadoop.hive.ql.optimizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.hadoop.hive.ql.exec.AbstractMapJoinOperator;
import org.apache.hadoop.hive.ql.exec.JoinOperator;
import org.apache.hadoop.hive.ql.exec.Operator;
import org.apache.hadoop.hive.ql.exec.ReduceSinkOperator;
import org.apache.hadoop.hive.ql.exec.Task;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.lib.NodeProcessor;
import org.apache.hadoop.hive.ql.lib.NodeProcessorCtx;
import org.apache.hadoop.hive.ql.optimizer.GenMRProcContext.GenMapRedCtx;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.plan.ExprNodeColumnDesc;
import org.apache.hadoop.hive.ql.plan.ExprNodeDesc;
import org.apache.hadoop.hive.ql.plan.MapJoinDesc;
import org.apache.hadoop.hive.ql.plan.MapredWork;
import org.apache.hadoop.hive.ql.plan.ReduceSinkDesc;

public class GenMRRedSink5 implements NodeProcessor {

  @Override
  public Object
      process(Node nd, Stack<Node> stack, NodeProcessorCtx opProcCtx, Object... nodeOutputs)
          throws SemanticException {
    ReduceSinkOperator op = (ReduceSinkOperator) nd;
    GenMRProcContext ctx = (GenMRProcContext) opProcCtx;
    ctx.getParseCtx();
    Operator<? extends Serializable> reducer = op.getChildOperators().get(0);
    Map<Operator<? extends Serializable>, GenMapRedCtx> mapCurrCtx = ctx
        .getMapCurrCtx();
    GenMapRedCtx mapredCtx = mapCurrCtx.get(op.getParentOperators().get(0));
    Task<? extends Serializable> currTask = mapredCtx.getCurrTask();
    MapredWork plan = (MapredWork) currTask.getWork();
    HashMap<Operator<? extends Serializable>, Task<? extends Serializable>> opTaskMap = ctx
        .getOpTaskMap();
    Task<? extends Serializable> opMapTask = opTaskMap.get(reducer);
    if (opMapTask == null) {
      if (plan.getReducer() == null) {
        //Set the current task to the parent task so that FileSinkOperator which is used in the
        //move task can do its work correctly.
        ctx.setCurrTask(currTask.getParentTasks().get(0));
        // Remove dependency
        ArrayList<Task<? extends Serializable>> parentTasks = null;
        if (currTask.getParentTasks() != null) {
          parentTasks = new ArrayList<Task<? extends Serializable>>();
          parentTasks.addAll(currTask.getParentTasks());
          for (Task<? extends Serializable> par : parentTasks) {
            par.removeDependentTask(currTask);
          }
        }
        // Get the parent Mapjoin Operator
        AbstractMapJoinOperator<? extends MapJoinDesc> currMapJ = ctx.getCurrMapJoinOp();
        Map<String, ExprNodeDesc> mapJoincolumnExprMap = currMapJ.getColumnExprMap();
        // Walk up the current reduce sink operator tree to select after the map join operator.
        if (parentTasks != null && parentTasks.size() >= 1) {
          Operator<? extends Serializable> tempOp = op;
          while (tempOp.getParentOperators() != null) {
            tempOp = tempOp.getParentOperators().get(0);
          }
          ReduceSinkDesc desc = (ReduceSinkDesc) op.getConf();
          if(tempOp instanceof ReduceSinkOperator) {
            ArrayList<ExprNodeDesc> valueCols = desc.getValueCols();
            ArrayList<ExprNodeDesc> keyCols = desc.getKeyCols();
            Operator<? extends Serializable> parentSelOperator =
              tempOp.getParentOperators().get(0);
            Map<String, ExprNodeDesc> columnMapping = parentSelOperator.getColumnExprMap();
            for(Map.Entry<String, ExprNodeDesc> entry : columnMapping.entrySet()) {
              ExprNodeDesc nodeDesc = entry.getValue();
            }
            ArrayList<ExprNodeDesc> newValueCols = new ArrayList<ExprNodeDesc>();
            ArrayList<ExprNodeDesc> newKeyCols = new ArrayList<ExprNodeDesc>();

            for(ExprNodeDesc keyDesc : keyCols) {
              String colName = keyDesc.getCols().get(0);
              ExprNodeColumnDesc parentNode = (ExprNodeColumnDesc)columnMapping.get(colName);
              newKeyCols.add(parentNode);
            }
            for(ExprNodeDesc keyDesc : valueCols) {
              String colName = keyDesc.getCols().get(0);
              ExprNodeColumnDesc parentNode = (ExprNodeColumnDesc)columnMapping.get(colName);
              newValueCols.add(parentNode);
            }
            op.setColumnExprMap(columnMapping);
            desc.setKeyCols(newKeyCols);
            desc.setValueCols(newValueCols);
          }
          Task<? extends Serializable> parent = parentTasks.get(0);
          ArrayList<Operator<? extends Serializable>> children =
              new ArrayList<Operator<? extends Serializable>>();
          // Set the child of the MapJoin to the child operator of the select operator, removing
          // FileSinkOperator which is currently map join operators child.
          children.add(tempOp);
          currMapJ.setChildOperators(children);
          // Set the parent of the operator to mapjoin.
          ArrayList<Operator<? extends Serializable>> pop = new ArrayList<Operator<? extends Serializable>>();
          pop.add(currMapJ);
          tempOp.setParentOperators(pop);
          MapredWork parentsPlan = (MapredWork) parent.getWork();
          if (reducer.getClass() == JoinOperator.class) {
            parentsPlan.setNeedsTagging(true);
          }
          // configure the reducer
          parentsPlan.setReducer(reducer);
          parentsPlan.setNumReduceTasks(desc.getNumReducers());
        }
        ctx.setCurrTopOp(null);
        ctx.setCurrAliasId(null);
        ctx.setCurrMapJoinOp(null);
      }
    } else {
      ctx.setCurrTask(currTask);
      // There is a join after mapjoin. One of the branches of mapjoin has already
      // been initialized.
      // Initialize the current branch, and join with the original plan.
      assert plan.getReducer() != reducer;
      GenMapRedUtils.joinPlan(op, currTask, opMapTask, ctx, -1, false, true,
          false);
    }
    mapCurrCtx.put(op, new GenMapRedCtx(ctx.getCurrTask(), ctx.getCurrTopOp(),
        ctx.getCurrAliasId()));
    // the mapjoin operator has been processed
    ctx.setCurrMapJoinOp(null);
    return null;
  }

}

