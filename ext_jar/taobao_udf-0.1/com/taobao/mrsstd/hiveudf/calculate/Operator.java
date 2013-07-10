package com.taobao.mrsstd.hiveudf.calculate;

import com.taobao.mrsstd.hiveudf.util.Constants;

public class Operator
{
  public static boolean isOperator(Object object, int mode)
  {
    if ((object instanceof String)) {
      object = ((String)object).toCharArray();
      if (((char[])(char[])object).length == 1) {
        object = Character.valueOf(((char[])(char[])object)[0]);
      }
    }

    if ((object instanceof char[])) {
      char[] operator = (char[])(char[])object;
      if (operator.length == 2) {
        if (((operator[0] == Constants.LOGIC_GREATEQUAL[0]) && (operator[1] == Constants.LOGIC_GREATEQUAL[1])) || ((operator[0] == Constants.LOGIC_LESSEQUAL[0]) && (operator[1] == Constants.LOGIC_LESSEQUAL[1])) || ((operator[0] == Constants.LOGIC_NOTEQUAL[0]) && (operator[1] == Constants.LOGIC_NOTEQUAL[1])) || ((operator[0] == Constants.LOGIC_OR[0]) && (operator[1] == Constants.LOGIC_OR[1])))
        {
          return true;
        }
      } else if ((operator.length == 3) && (
        ((operator[0] == Constants.LOGIC_AND[0]) && (operator[1] == Constants.LOGIC_AND[1]) && (operator[2] == Constants.LOGIC_AND[2])) || ((operator[0] == Constants.LOGIC_NOT[0]) && (operator[1] == Constants.LOGIC_NOT[1]) && (operator[2] == Constants.LOGIC_NOT[2]))))
      {
        return true;
      }
    }
    else if ((object instanceof Character)) {
      Character operator = (Character)object;
      if ((operator.charValue() == '+') || (operator.charValue() == '=') || (operator.charValue() == '>') || (operator.charValue() == '<'))
      {
        return true;
      }

      if ((mode == 1) && ((operator.charValue() == '-') || (operator.charValue() == '*') || (operator.charValue() == '/')))
      {
        return true;
      }
    }

    return false;
  }

  public static int getPriority(Object object)
  {
    if ((object instanceof String)) {
      object = ((String)object).toCharArray();
      if (((char[])(char[])object).length == 1) {
        object = Character.valueOf(((char[])(char[])object)[0]);
      }
    }

    if ((object instanceof char[])) {
      char[] operator = (char[])(char[])object;
      if (operator.length == 2) {
        if (((operator[0] == Constants.LOGIC_GREATEQUAL[0]) && (operator[1] == Constants.LOGIC_GREATEQUAL[1])) || ((operator[0] == Constants.LOGIC_LESSEQUAL[0]) && (operator[1] == Constants.LOGIC_LESSEQUAL[1])) || ((operator[0] == Constants.LOGIC_NOTEQUAL[0]) && (operator[1] == Constants.LOGIC_NOTEQUAL[1])))
        {
          return 6;
        }
        if ((operator[0] == Constants.LOGIC_OR[0]) && (operator[1] == Constants.LOGIC_OR[1]))
        {
          return 4;
        }
      } else if (operator.length == 3) {
        if ((operator[0] == Constants.LOGIC_AND[0]) && (operator[1] == Constants.LOGIC_AND[1]) && (operator[2] == Constants.LOGIC_AND[2]))
        {
          return 5;
        }
        if ((operator[0] == Constants.LOGIC_NOT[0]) && (operator[1] == Constants.LOGIC_NOT[1]) && (operator[2] == Constants.LOGIC_NOT[2]))
        {
          return 3;
        }
      }
    } else if ((object instanceof Character)) {
      Character operator = (Character)object;
      if ((operator.charValue() == '+') || (operator.charValue() == '-'))
      {
        return 7;
      }

      if ((operator.charValue() == '*') || (operator.charValue() == '/'))
      {
        return 8;
      }

      if ((operator.charValue() == '=') || (operator.charValue() == '>') || (operator.charValue() == '<'))
      {
        return 6;
      }
    }

    return -1;
  }

  public static int compare(Object op1, Object op2)
  {
    int iop1 = getPriority(op1);
    int iop2 = getPriority(op2);
    return iop1 - iop2;
  }

  public static int getOperatorNum(String ops)
  {
    if (ops.equals(String.valueOf(Constants.LOGIC_NOT))) {
      return 1;
    }

    return 2;
  }
}