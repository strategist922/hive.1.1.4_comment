package com.taobao.mrsstd.hiveudf.calculate;

import com.taobao.mrsstd.hiveudf.util.Constants;
import com.taobao.mrsstd.hiveudf.util.UDFUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathCalculator
{
  private Map<String, String> mapFields;
  private String expression = null;

  public MathCalculator(Map<String, String> mapFields, String expression) {
    this.mapFields = mapFields;
    this.expression = expression;
  }

  public String calculate()
  {
    List suffix = createReversePolishExpr(this.expression);
    Stack stack = new Stack();
    for (int i = 0; i < suffix.size(); i++) {
      if (!Operator.isOperator(suffix.get(i), 1)) {
        stack.push(Double.valueOf((String)suffix.get(i)));
      }
      else if (Operator.getOperatorNum((String)suffix.get(i)) == 2) {
        Double current = (Double)stack.top();
        stack.pop();
        Double previous = null;
        if (stack.count != 0) {
          previous = (Double)stack.top();
          stack.pop();
        } else {
          previous = new Double(0.0D);
        }
        Double result = calculate((String)suffix.get(i), previous, current);
        stack.push(result);
      } else if (Operator.getOperatorNum((String)suffix.get(i)) == 1) {
        Double current = (Double)stack.top();
        stack.pop();
        Double result = calculate((String)suffix.get(i), null, current);
        stack.push(result);
      }

    }

    return UDFUtil.delZero(((Double)stack.top()).toString());
  }

  public List<String> createReversePolishExpr(String subStr)
  {
    List listString = new ArrayList();
    String changeStr = UDFUtil.replaceFields(this.mapFields, subStr, listString);
    String[] fields = new String[listString.size()];
    for (int i = 0; i < listString.size(); i++) {
      fields[i] = ((String)listString.get(i));
    }

    String regexDigit = "\\d+\\.{0,1}\\d*";
    changeStr = changeStr.replace('\'', ' ');
    String[] numbers = matcher(regexDigit, changeStr);
    changeStr = changeStr.replaceAll(regexDigit, "0").replaceAll("\\-\\s*\\-0", "-1").replaceAll("\\+\\s*\\-0", "+1").replaceAll("\\*\\s*\\-0", "*1").replaceAll("\\/\\s*\\-0", "/1");

    char[] chars = changeStr.toCharArray();
    int idxNum = 0;
    int idxField = 0;
    List list = new ArrayList();
    for (int i = 0; i < chars.length; i++) {
      if ('0' == chars[i])
      {
        list.add(numbers[(idxNum++)]);
      } else if ('1' == chars[i])
      {
        list.add("-" + numbers[(idxNum++)]);
      } else if ('F' == chars[i]) {
        list.add(this.mapFields.get(fields[(idxField++)])); } else {
        if (' ' == chars[i]) {
          continue;
        }
        if (i + 2 < chars.length) if (Operator.isOperator(new char[] { chars[i], chars[(i + 1)], chars[(i + 2)] }, 1))
          {
            list.add(changeStr.substring(i, i + 3));
            i += 2; continue;
          } if (i + 1 < chars.length) if (Operator.isOperator(new char[] { chars[i], chars[(i + 1)] }, 1))
          {
            list.add(changeStr.substring(i, i + 2));
            i += 1; continue;
          } if (Operator.isOperator(Character.valueOf(chars[i]), 1))
        {
          list.add(String.valueOf(chars[i]));
        }
        else throw new RuntimeException("not supported format: " + changeStr.substring(i, changeStr.length()));
      }
    }

    List suffix = new ArrayList();
    Stack operator = new Stack();
    for (int i = 0; i < list.size(); i++) {
      String element = (String)list.get(i);
      if (!Operator.isOperator(element, 1)) {
        suffix.add(element);
      }
      else if (operator.count == 0) {
        operator.push(element);
      }
      else {
        while ((operator.count != 0) && (Operator.compare(operator.top(), element) >= 0)) {
          String top = (String)operator.top();
          operator.pop();
          suffix.add(top);
        }
        operator.push(element);
      }

    }

    while (operator.count != 0) {
      suffix.add(operator.top());
      operator.pop();
    }

    return suffix;
  }

  private String[] matcher(String regex, String str) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(str);
    List list = new ArrayList();
    while (matcher.find()) {
      String tmp = matcher.group();
      if (tmp.startsWith("'")) {
        tmp = tmp.substring(1, tmp.length());
      }
      if (tmp.endsWith("'")) {
        tmp = tmp.substring(0, tmp.length() - 1);
      }
      list.add(tmp);
    }
    String[] result = new String[list.size()];
    return (String[])list.toArray(result);
  }

  private Double calculate(String op, Double previous, Double current) {
    if (String.valueOf('+').equals(op)) {
      return Double.valueOf(previous.doubleValue() + current.doubleValue());
    }

    if (String.valueOf('-').equals(op)) {
      return Double.valueOf(previous.doubleValue() - current.doubleValue());
    }

    if (String.valueOf('*').equals(op)) {
      return Double.valueOf(previous.doubleValue() * current.doubleValue());
    }

    if (String.valueOf('/').equals(op)) {
      return Double.valueOf(previous.doubleValue() / current.doubleValue());
    }

    if (String.valueOf('=').equals(op)) {
      int result = previous.compareTo(current);
      if (result == 0) {
        return Double.valueOf(1.0D);
      }
      return Double.valueOf(0.0D);
    }

    if (String.valueOf('>').equals(op)) {
      int result = previous.compareTo(current);
      if (result > 0) {
        return Double.valueOf(1.0D);
      }
      return Double.valueOf(0.0D);
    }

    if (String.valueOf('<').equals(op)) {
      int result = previous.compareTo(current);
      if (result < 0) {
        return Double.valueOf(1.0D);
      }
      return Double.valueOf(0.0D);
    }

    if (String.valueOf(Constants.LOGIC_GREATEQUAL).equals(op)) {
      int result = previous.compareTo(current);
      if (result >= 0) {
        return Double.valueOf(1.0D);
      }
      return Double.valueOf(0.0D);
    }

    if (String.valueOf(Constants.LOGIC_LESSEQUAL).equals(op)) {
      int result = previous.compareTo(current);
      if (result <= 0) {
        return Double.valueOf(1.0D);
      }
      return Double.valueOf(0.0D);
    }

    if (String.valueOf(Constants.LOGIC_NOTEQUAL).equals(op)) {
      int result = previous.compareTo(current);
      if (result != 0) {
        return Double.valueOf(1.0D);
      }
      return Double.valueOf(0.0D);
    }

    if (String.valueOf(Constants.LOGIC_OR).equals(op)) {
      if ((previous.doubleValue() != 0.0D) || (current.doubleValue() != 0.0D)) {
        return Double.valueOf(1.0D);
      }
      return Double.valueOf(0.0D);
    }

    if (String.valueOf(Constants.LOGIC_AND).equals(op)) {
      if ((previous.doubleValue() != 0.0D) && (current.doubleValue() != 0.0D)) {
        return Double.valueOf(1.0D);
      }
      return Double.valueOf(0.0D);
    }

    if (String.valueOf(Constants.LOGIC_NOT).equals(op)) {
      if (current.doubleValue() != 0.0D) {
        return Double.valueOf(0.0D);
      }
      return Double.valueOf(1.0D);
    }

    return null;
  }
}