package com.taobao.mrsstd.hiveudf.calculate;

import com.taobao.mrsstd.hiveudf.util.Constants;
import com.taobao.mrsstd.hiveudf.util.UDFUtil;
import java.util.Map;

public class Calculator
{
  private Map<String, String> mapValues;
  private String expression;

  public Calculator(Map<String, String> mapValues, String expression)
  {
    this.mapValues = mapValues;
    this.expression = expression;
  }

  public String calculate() throws Exception {
    if ((this.expression == null) || (this.expression.trim().length() == 0)) {
      return "1";
    }
    this.expression = this.expression.toLowerCase();
    char[] chars = this.expression.toCharArray();
    Stack stack = new Stack();
    String function = null;
    StringBuffer bufPrefix = new StringBuffer();
    boolean flagParenthesis = false;

    for (int i = 0; i < chars.length; i++) {
      String stackStr = String.valueOf(chars[i]);
      stack.push(stackStr);

      bufPrefix.append(stackStr);
      if (stackStr.equals("(")) {
        Stack stackFunc = new Stack();
        int index = bufPrefix.length() - 2;
        while (index >= 0) {
          boolean flagFound = false;
          for (int j = 0; j < Constants.CHARS.length; j++) {
            if (bufPrefix.charAt(index) == Constants.CHARS[j]) {
              flagFound = true;
              break;
            }
          }
          if (flagFound) break;
          stackFunc.push(Character.valueOf(bufPrefix.charAt(index)));

          index--;
        }
        if (stackFunc.count > 0) {
          function = stackFunc.toString();
          function = UDFUtil.reverse(function);
          flagParenthesis = true;
        } else {
          flagParenthesis = false;
        }
      }

      if (")".equals(stack.top())) {
        String subStr = null;
        while (!"(".equals(stack.top())) {
          stack.pop();
          if (!"(".equals(stack.top())) {
            subStr = addEnd(subStr, (String)stack.top());
          }
        }

        String pushStr = "";
        if (function == null)
          pushStr = new StringCalculator(this.mapValues, subStr).calculate();
        else if (function.equalsIgnoreCase("S"))
          pushStr = new StringCalculator(this.mapValues, subStr).calculate();
        else if (function.equalsIgnoreCase("N"))
          pushStr = new MathCalculator(this.mapValues, subStr).calculate();
        else {
          pushStr = new ExtendCalculator(function, this.mapValues, subStr).compute();
        }

        stack.pop();
        for (int k = 0; (flagParenthesis) && (k < function.length()); k++)
          stack.pop();
        stack.push("'" + pushStr + "'");

        for (int l = i + 1; l < chars.length; l++) {
          stackStr = String.valueOf(chars[l]);
          stack.push(stackStr);
        }
        bufPrefix = new StringBuffer();
        function = null;
        i = -1;
        chars = stack.toString().toCharArray();
        flagParenthesis = false;
      }
    }
    String resultStr = null;
    while (stack.count != 0) {
      resultStr = new StringCalculator(this.mapValues, stack.toString()).calculate();
    }

    return resultStr;
  }

  public String addEnd(String str, String a) {
    StringBuffer buf = new StringBuffer();
    buf.append(a);
    if (str != null) {
      buf.append(str);
    }
    return buf.toString();
  }

  public static void main(String[] args)
  {
  }
}