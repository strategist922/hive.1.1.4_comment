package com.taobao.mrsstd.hiveudf.extend;

import com.taobao.mrsstd.hiveudf.util.UDFUtil;
import java.io.PrintStream;

public class JavaMathDirect extends Parent
{
  public String evaluate(String function, String[] values)
  {
    if ("abs".equalsIgnoreCase(function)) {
      Double result = Double.valueOf(Math.abs(Double.valueOf(values[0]).doubleValue()));
      return UDFUtil.delZero(String.valueOf(result));
    }if ("log".equalsIgnoreCase(function)) {
      Double result = Double.valueOf(Math.log(Double.valueOf(values[0]).doubleValue()));
      return UDFUtil.delZero(String.valueOf(result));
    }if ("log10".equalsIgnoreCase(function)) {
      Double result = Double.valueOf(Math.log10(Double.valueOf(values[0]).doubleValue()));
      return UDFUtil.delZero(String.valueOf(result));
    }if ("sin".equalsIgnoreCase(function)) {
      Double result = Double.valueOf(Math.sin(Double.valueOf(values[0]).doubleValue()));
      return UDFUtil.delZero(String.valueOf(result));
    }if ("round".equalsIgnoreCase(function)) {
      Long result = Long.valueOf(Math.round(Double.valueOf(values[0]).doubleValue()));
      return UDFUtil.delZero(String.valueOf(result));
    }if ("sqrt".equalsIgnoreCase(function)) {
      Double result = Double.valueOf(Math.sqrt(Double.valueOf(values[0]).doubleValue()));
      return UDFUtil.delZero(String.valueOf(result));
    }if ("pow".equalsIgnoreCase(function)) {
      Double result = Double.valueOf(Math.pow(Double.valueOf(values[0]).doubleValue(), Double.valueOf(values[1]).doubleValue()));

      return UDFUtil.delZero(String.valueOf(result));
    }
    throw new RuntimeException("not supported function: " + function);
  }

  public static void main(String[] args)
  {
    JavaMathDirect math = new JavaMathDirect();
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++)
      math.evaluate("sin", new String[] { "0.897" });
    long time2 = System.currentTimeMillis();
    System.out.println("cost time: " + (time2 - time1));
  }
}