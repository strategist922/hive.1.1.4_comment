package com.taobao.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.io.Text;

public class UDAFGroupConcat2 extends UDAF
{
  public static class GroupConcatEvaluator
    implements UDAFEvaluator
  {
    private final UDAFGroupConcat2.State state;
    private StringBuilder builder;
    private Text result;

    public GroupConcatEvaluator()
    {
      this.state = new UDAFGroupConcat2.State();
      this.result = new Text();
      this.builder = new StringBuilder();
    }

    public void init() {
      UDAFGroupConcat2.State.access$002(this.state, new Text());
      UDAFGroupConcat2.State.access$102(this.state, new Text());
      this.builder.delete(0, this.builder.length());
    }

    public boolean iterate(Text o, Text splitor) {
      if (UDAFGroupConcat2.State.access$100(this.state) == null) {
        UDAFGroupConcat2.State.access$102(this.state, new Text());
      }
      if ((o != null) && (splitor != null)) {
        String oStr = o.toString();
        String sStr = splitor.toString();
        this.builder.append(oStr);
        this.builder.append(sStr);
        UDAFGroupConcat2.State.access$100(this.state).set(sStr);
      }
      return true;
    }

    public UDAFGroupConcat2.State terminatePartial() {
      if (UDAFGroupConcat2.State.access$000(this.state) == null) {
        UDAFGroupConcat2.State.access$002(this.state, new Text());
      }
      String tmpResult = this.builder.toString();
      UDAFGroupConcat2.State.access$000(this.state).set(tmpResult);
      return this.state;
    }

    public boolean merge(UDAFGroupConcat2.State other) {
      if (UDAFGroupConcat2.State.access$100(this.state) == null) {
        UDAFGroupConcat2.State.access$102(this.state, new Text());
      }
      if ((UDAFGroupConcat2.State.access$000(other) != null) && (UDAFGroupConcat2.State.access$100(other) != null)) {
        this.builder.append(UDAFGroupConcat2.State.access$000(other).toString());
        UDAFGroupConcat2.State.access$102(this.state, UDAFGroupConcat2.State.access$100(other));
      }
      return true;
    }

    public Text terminate()
    {
      if ((this.builder.length() > 0) && (UDAFGroupConcat2.State.access$100(this.state) != null)) {
        String rStr = this.builder.toString();
        String sStr = UDAFGroupConcat2.State.access$100(this.state).toString();
        this.result.set(rStr.substring(0, rStr.length() - sStr.length()));
      } else {
        return null;
      }
      return this.result;
    }
  }

  public static class State
  {
    private Text concat;
    private Text splitor;
  }
}