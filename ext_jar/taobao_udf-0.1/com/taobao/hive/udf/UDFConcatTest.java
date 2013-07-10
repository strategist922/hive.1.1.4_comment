package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFConcatTest extends UDF
{
  Text result = new Text();

  public Text evaluate(String[] args)
  {
    return compute(args);
  }

  public Text compute(String[] args)
  {
    try
    {
      if (args == null) {
        this.result.set("");
        return this.result;
      }

      String last = args[(args.length - 1)];
      String split = "";
      if ((last != null) && (last.startsWith("splitby"))) {
        split = last.split("_")[1];
      }
      StringBuffer return_result = new StringBuffer();
      for (int i = 0; i < args.length; i++) {
        String arg = args[i];
        if (arg == null)
        {
          continue;
        }
        if ((last != null) && (last.startsWith("splitby"))) {
          if (i != args.length - 1)
            return_result.append(arg).append(split);
        }
        else {
          return_result.append(arg);
        }
      }
      if ((last != null) && (last.startsWith("splitby"))) {
        this.result.set(return_result.toString().substring(0, return_result.toString().length() - split.length()));
        return this.result;
      }

      this.result.set(return_result.toString());
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFConcatTest test = new UDFConcatTest();
    System.out.println(test.evaluate(new String[] { "aa", "bb", "cc", null }));
    System.out.println(test.evaluate(new String[] { "aa", "bb" }));
    System.out.println(test.evaluate(new String[] { "aa", "bb", "cc", null, "dd", "splitby_|" }));
    System.out.println(test.evaluate(new String[] { "null", "bb", ":", null, "dd", "splitby_," }));
    System.out.println(test.evaluate(null));
  }
}