package com.taobao.mrsstd.hiveudf.calculate;

import com.taobao.mrsstd.hiveudf.util.Constants;
import com.taobao.mrsstd.hiveudf.util.UDFUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator
{
  private Map<String, String> mapFields;
  private String expression = null;

  public StringCalculator(Map<String, String> mapFields, String expression) {
    this.mapFields = mapFields;
    this.expression = expression;
  }

  public String calculate()
  {
    List suffix = createReversePolishExpr(this.expression);
    Stack stack = new Stack();
    for (int i = 0; i < suffix.size(); i++) {
      if (!Operator.isOperator(suffix.get(i), 2)) {
        stack.push(suffix.get(i));
      }
      else if (Operator.getOperatorNum((String)suffix.get(i)) == 2) {
        String current = (String)stack.top();
        stack.pop();
        String previous = null;
        if (stack.count != 0) {
          previous = (String)stack.top();
          stack.pop();
        }
        String result = calculate((String)suffix.get(i), previous, current);
        stack.push(result);
      } else if (Operator.getOperatorNum((String)suffix.get(i)) == 1) {
        String current = (String)stack.top();
        stack.pop();
        String result = calculate((String)suffix.get(i), null, current);
        stack.push(result);
      }

    }

    return ((String)stack.top()).toString();
  }

  public List<String> createReversePolishExpr(String subStr)
  {
    String regexStr = "'([一-龥a-zA-Z0-9\\._-]*)'";
    String[] strings = matcher(regexStr, subStr);
    String changeStr = subStr.replaceAll(regexStr, String.valueOf('C'));

    List listString = new ArrayList();
    changeStr = UDFUtil.replaceFields(this.mapFields, changeStr, listString);
    String[] fields = new String[listString.size()];
    for (int i = 0; i < listString.size(); i++) {
      fields[i] = ((String)listString.get(i));
    }
    char[] chars = changeStr.toCharArray();
    int idxStr = 0;
    int idxField = 0;
    List list = new ArrayList();
    for (int i = 0; i < chars.length; i++) {
      if ('C' == chars[i])
      {
        list.add(strings[(idxStr++)]);
      } else if ('F' == chars[i]) {
        list.add(this.mapFields.get(fields[(idxField++)])); } else {
        if (' ' == chars[i]) {
          continue;
        }
        if (i + 2 < chars.length) if (Operator.isOperator(new char[] { chars[i], chars[(i + 1)], chars[(i + 2)] }, 2))
          {
            list.add(changeStr.substring(i, i + 3));
            i += 2; continue;
          } if (i + 1 < chars.length) if (Operator.isOperator(new char[] { chars[i], chars[(i + 1)] }, 2))
          {
            list.add(changeStr.substring(i, i + 2));
            i += 1; continue;
          } if (Operator.isOperator(Character.valueOf(chars[i]), 2))
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
      if (!Operator.isOperator(element, 2)) {
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
    while (matcher.find())
    {
      String temp = matcher.group();
      temp = temp.substring(1, temp.length() - 1);
      list.add(temp);
    }
    String[] result = new String[list.size()];
    return (String[])list.toArray(result);
  }

  private String calculate(String op, String previous, String current) {
    if (String.valueOf('+').equals(op)) {
      return previous + current;
    }

    if (String.valueOf('=').equals(op)) {
      int result = previous.compareTo(current);
      if (result == 0) {
        return "1";
      }
      return "0";
    }

    if (String.valueOf('>').equals(op)) {
      int result = previous.compareTo(current);
      if (result > 0) {
        return "1";
      }
      return "0";
    }

    if (String.valueOf('<').equals(op)) {
      int result = previous.compareTo(current);
      if (result < 0) {
        return "1";
      }
      return "0";
    }

    if (String.valueOf(Constants.LOGIC_GREATEQUAL).equals(op)) {
      int result = previous.compareTo(current);
      if (result >= 0) {
        return "1";
      }
      return "0";
    }

    if (String.valueOf(Constants.LOGIC_LESSEQUAL).equals(op)) {
      int result = previous.compareTo(current);
      if (result <= 0) {
        return "1";
      }
      return "0";
    }

    if (String.valueOf(Constants.LOGIC_NOTEQUAL).equals(op)) {
      int result = previous.compareTo(current);
      if (result != 0) {
        return "1";
      }
      return "0";
    }

    if (String.valueOf(Constants.LOGIC_OR).equals(op)) {
      if ((previous.equals("0")) && (current.equals("0"))) {
        return "0";
      }
      return "1";
    }

    if (String.valueOf(Constants.LOGIC_AND).equals(op)) {
      if ((previous.equals("0")) || (current.equals("0"))) {
        return "0";
      }
      return "1";
    }

    if (String.valueOf(Constants.LOGIC_NOT).equals(op)) {
      if (current.equals("0")) {
        return "1";
      }
      return "0";
    }

    return null;
  }
}