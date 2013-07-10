package com.taobao.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.io.Text;

public class UDAFTest extends UDAF
{
  public static class Evaluator
    implements UDAFEvaluator
  {
    private int mCount;

    public Evaluator()
    {
      init();
    }

    public void init()
    {
      this.mCount = 0;
    }

    public boolean iterate(Text o) {
      if (o != null) {
        this.mCount += 1;
      }
      return true;
    }

    public Text terminatePartial() {
      return new Text(this.mCount + "");
    }

    public boolean merge(Text o) {
      int a = Integer.parseInt(o.toString());
      this.mCount += a;
      return true;
    }

    public Text terminate() {
      return new Text(this.mCount + "");
    }
  }
}