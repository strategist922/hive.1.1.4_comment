package com.taobao.hive.udf;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.io.Text;

public class UDAFGroupSpt extends UDAF
{
  public static class UDAFGroupSptEvaluator
    implements UDAFEvaluator
  {
    private PartialResult partial;

    public void init()
    {
      this.partial = null;
    }

    public boolean iterate(Text order, Text value, Text type) {
      if ((value == null) || (order == null)) {
        return true;
      }
      if (this.partial == null) {
        this.partial = new PartialResult();
      }

      this.partial.map.put(order, value);
      this.partial.in_n += 1;
      this.partial.type = type.toString();
      return true;
    }

    public PartialResult terminatePartial() {
      return this.partial;
    }

    public boolean merge(PartialResult other) {
      if (other == null) {
        return true;
      }
      if (this.partial == null) {
        this.partial = new PartialResult();
      }
      this.partial.map.putAll(other.map);

      this.partial.in_n += other.in_n;

      return true;
    }

    public DoubleWritable terminate() {
      if (this.partial == null) {
        return null;
      }
      double[] x = null;
      double[] y = null;

      Iterator titer = this.partial.map.entrySet().iterator();
      int i = 0;
      while (titer.hasNext()) {
        Map.Entry ent = (Map.Entry)titer.next();
        x[i] = Double.valueOf(ent.getKey().toString()).doubleValue();
        y[i] = Double.valueOf(ent.getValue().toString()).doubleValue();
      }

      double[] a = null;
      double[] dt = null;
      int n = this.partial.in_n;
      PartialResult.SPT1(x, y, n, a, dt);
      return new DoubleWritable(a[0]);
    }

    public static class PartialResult
    {
      int in_n;
      TreeMap map;
      String type;

      public static void SPT1(double[] x, double[] y, int n, double[] a, double[] dt)
      {
        double xx = 0.0D;
        double yy = 0.0D;
        for (int i = 0; i <= n - 1; i++) {
          xx += x[i] / n;
          yy += y[i] / n;
        }
        double e = 0.0D;
        double f = 0.0D;
        for (i = 0; i <= n - 1; i++) {
          double q = x[i] - xx;
          e += q * q;
          f += q * (y[i] - yy);
        }
        a[1] = (f / e);
        a[0] = (yy - a[1] * xx);
        double q = 0.0D;
        double u = 0.0D;
        double p = 0.0D;
        double umax = 0.0D;
        double umin = 1.0E+030D;
        for (i = 0; i <= n - 1; i++) {
          double s = a[1] * x[i] + a[0];
          q += (y[i] - s) * (y[i] - s);
          p += (s - yy) * (s - yy);
          e = Math.abs(y[i] - s);
          if (e > umax)
            umax = e;
          if (e < umin)
            umin = e;
          u += e / n;
        }
        dt[1] = Math.sqrt(q / n);
        dt[0] = q;
        dt[2] = p;
        dt[3] = umax;
        dt[4] = umin;
        dt[5] = u;
      }
    }
  }
}