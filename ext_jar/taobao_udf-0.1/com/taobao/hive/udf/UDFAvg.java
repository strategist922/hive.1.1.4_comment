package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFAvg extends UDF
{
  Text result = new Text();

  public Text evaluate(String arg1)
  {
    return compute(new String[] { arg1 });
  }
  public Text evaluate(String arg1, String arg2) {
    return compute(new String[] { arg1, arg2 });
  }
  public Text evaluate(String arg1, String arg2, String arg3) {
    return compute(new String[] { arg1, arg2, arg3 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4) {
    return compute(new String[] { arg1, arg2, arg3, arg4 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, String arg16) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, String arg16, String arg17) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, String arg16, String arg17, String arg18) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, String arg16, String arg17, String arg18, String arg19) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, String arg16, String arg17, String arg18, String arg19, String arg20) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20 });
  }
  public Text evaluate(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12, String arg13, String arg14, String arg15, String arg16, String arg17, String arg18, String arg19, String arg20, String arg21) {
    return compute(new String[] { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17, arg18, arg19, arg20, arg21 });
  }
  public Text compute(String[] args) {
    try {
      double total = 0.0D;
      int n = 0;
      for (int i = 0; i < args.length; i++) {
        String arg = args[i];
        try {
          Double a = Double.valueOf(Double.parseDouble(arg));
          total += a.doubleValue();
          if (a.doubleValue() - 0.0D != 0.0D)
          {
            n++;
          }
        }
        catch (Exception e) {
        }
      }
      if (n != 0) {
        double avg_value = total / n;
        this.result.set(avg_value + "");
        return this.result;
      }
      this.result.set("0");
      return this.result;
    }
    catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFAvg test = new UDFAvg();
    System.out.println(test.evaluate("0", "2", "3", "1"));
  }
}