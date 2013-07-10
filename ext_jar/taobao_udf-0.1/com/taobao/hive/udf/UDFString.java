package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFString extends UDF
{
  Text result = new Text();

  public Text evaluate(String methodName, String value)
  {
    return compute(methodName, value, new String[0]);
  }

  public Text evaluate(String methodName, String value, String arg1) {
    return compute(methodName, value, new String[] { arg1 });
  }

  public Text evaluate(String methodName, String value, String arg1, String arg2)
  {
    return compute(methodName, value, new String[] { arg1, arg2 });
  }

  public Text compute(String methodName, String value, String[] args)
  {
    try
    {
      methodName = methodName.toLowerCase();
      if (methodName.equals("tolowercase")) {
        value = value.toLowerCase();
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("touppercase")) {
        value = value.toUpperCase();
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("length")) {
        if (value == null) {
          this.result.set("0");
          return this.result;
        }
        value = String.valueOf(value.length());
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("trim")) {
        value = value.trim();
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("indexof")) {
        int i = value.indexOf(args[0]);
        this.result.set(String.valueOf(i));
        return this.result;
      }
      if (methodName.equals("split_count")) {
        String[] strList = value.split(args[0]);
        int i = strList.length;
        this.result.set(String.valueOf(i));
        return this.result;
      }
      if (methodName.equals("not_null")) {
        if ((value == null) || ("".equals(value.trim()))) {
          this.result.set(String.valueOf(args[0]));
          return this.result;
        }
        this.result.set(value.trim());
        return this.result;
      }
      if (methodName.equals("lastindexof")) {
        int i = value.lastIndexOf(args[0]);
        this.result.set(String.valueOf(i));
        return this.result;
      }
      if (methodName.equals("endswith")) {
        boolean i = value.endsWith(args[0]);
        this.result.set(String.valueOf(i));
        return this.result;
      }
      if (methodName.equals("equals")) {
        boolean i = value.equals(args[0]);
        this.result.set(String.valueOf(i));
        return this.result;
      }
      if (methodName.equals("startswith")) {
        boolean i = value.startsWith(args[0]);
        this.result.set(String.valueOf(i));
        return this.result;
      }
      if (methodName.equals("replaceall")) {
        value = value.replaceAll(args[0], args[1]);
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("replacefirst")) {
        value = value.replaceFirst(args[0], args[1]);
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("replace")) {
        value = value.replace(args[0], args[1]);
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("substring")) {
        int last = Integer.parseInt(args[1]);
        if (String.valueOf(last).equals("-1")) {
          last = value.length();
        }

        value = value.substring(Integer.parseInt(args[0]), last);
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("between")) {
        int index1 = value.indexOf(args[0]);
        if (index1 < 0) {
          return null;
        }

        if (!args[1].equals("end")) {
          value = value.substring(index1 + args[0].length());
          int index2 = value.indexOf(args[1]);
          if (index2 >= 0) {
            value = value.substring(0, index2);
            this.result.set(value);
            return this.result;
          }
          this.result.set(value);
          return this.result;
        }
        value = value.substring(index1 + args[0].length());
        this.result.set(value);
        return this.result;
      }

      if (methodName.equals("cutby")) {
        int index1 = value.indexOf(args[0]);
        if (index1 >= 0) {
          value = value.substring(0, index1);
          this.result.set(value);
          return this.result;
        }
        this.result.set(value);
        return this.result;
      }
      if (methodName.equals("getnumber")) {
        String return_result = "0";
        try {
          return_result = Double.parseDouble(value) + "";
        } catch (Exception e) {
          return_result = "0";
        }
        this.result.set(return_result);
        return this.result;
      }
      value = "err";
      this.result.set(value);
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFString test = new UDFString();

    System.out.println(test.evaluate("between", "http://jie.taobao.com/street-37--1-grid-1-.htm", "a", "|"));
  }
}