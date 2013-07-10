package com.taobao.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.io.Text;

public class UDAFGroupConcat extends UDAF
{
  public static class UDAFGroupConcatEvaluator
    implements UDAFEvaluator
  {
    private StringBuilder sb = new StringBuilder();
    private static String __DELIMITER = ",";

    public UDAFGroupConcatEvaluator()
    {
      init();
    }

    public void init()
    {
      if (this.sb.length() > 0)
        this.sb.delete(0, this.sb.length());
    }

    public boolean iterate(Text o, String delimeter)
    {
      if (o != null) {
        if (this.sb.length() > 0) {
          this.sb.append(delimeter);
        }
        this.sb.append(o.toString());
      }
      return true;
    }

    public boolean iterate(Text o) {
      return iterate(o, __DELIMITER);
    }

    public Text terminatePartial()
    {
      return new Text(this.sb.toString());
    }

    public boolean merge(Text o, String delimeter)
    {
      if (o != null) {
        if (this.sb.length() > 0) {
          this.sb.append(delimeter);
        }
        this.sb.append(o.toString());
      }
      return true;
    }

    public boolean merge(Text o) {
      return merge(o, __DELIMITER);
    }

    public Text terminate()
    {
      return new Text(this.sb.toString().trim());
    }
  }
}