package com.taobao.hive.udf;

import java.util.HashSet;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class QueryNormalization extends UDF
{
  public Text res = new Text();
  private static final String MYAND = "AND";
  private static final String MYOR = "OR";

  public Text evaluate(Text QueryString)
  {
    if ((QueryString == null) || (QueryString.toString() == null))
    {
      return null;
    }
    String temp = QueryString.toString();
    temp = removeSpecialChar(temp);
    temp = Sbc2Dbc(temp);
    temp = removeSpace(temp);
    this.res.set(upper2Lower(temp));
    return this.res;
  }

  public static boolean isRemoveSpecialChar(char test)
  {
    HashSet removeSpecHash = new HashSet();
    removeSpecHash.add("��");
    removeSpecHash.add(";");
    removeSpecHash.add("��");
    removeSpecHash.add("?");
    removeSpecHash.add("��");
    String temp = "" + test;

    return removeSpecHash.contains(temp);
  }

  public static String removeSpecialChar(String input)
  {
    int length = input.length();
    StringBuffer buffer = new StringBuffer(length);
    for (int i = 0; i < length; i++) {
      if (isRemoveSpecialChar(input.charAt(i)))
        buffer.append(' ');
      else {
        buffer.append(input.charAt(i));
      }
    }
    return new String(buffer);
  }

  public static String Sbc2Dbc(String inputStr)
  {
    try {
      byte[] byInput = inputStr.getBytes("GBK");
      byte[] bytemp = new byte[byInput.length];
      int[] intValue = new int[2];
      int count = 0;
      for (int index = 0; index < byInput.length; ) {
        intValue[0] = (byInput[index] & 0xFF);
        if (index + 1 < byInput.length) {
          intValue[1] = (byInput[(index + 1)] & 0xFF);
          if ((intValue[0] == 161) && (intValue[1] == 163)) {
            bytemp[(count++)] = 46;
            index += 2; continue;
          }if ((intValue[0] == 161) && ((intValue[1] == 176) || (intValue[1] == 177)))
          {
            bytemp[(count++)] = 34;
            index += 2; continue;
          }if ((intValue[0] == 161) && ((intValue[1] == 174) || (intValue[1] == 175)))
          {
            bytemp[(count++)] = 39;
            index += 2; continue;
          }if (IsChineseChar(intValue[0], intValue[1])) {
            bytemp[(count++)] = (byte)(intValue[1] - 128);
            index += 2; continue;
          }if (intValue[0] >= 128)
          {
            bytemp[(count++)] = (byte)intValue[0];
            bytemp[(count++)] = (byte)intValue[1];
            index += 2; continue;
          }
          bytemp[(count++)] = (byte)intValue[0];
          index++; continue;
        }

        bytemp[(count++)] = (byte)intValue[0];
        index++;
      }

      return new String(bytemp, 0, count, "GBK");
    } catch (Exception e) {
      e.printStackTrace();
    }return "";
  }

  private static boolean IsChineseChar(int n1, int n2)
  {
    return (n1 == 163) && (n2 > 160) && (n2 < 255);
  }

  public static String removeSpace(String strInput) {
    String input = strInput.trim();
    int length = input.length();
    StringBuffer buffer = new StringBuffer(length);
    char c1 = '\000';
    char c2 = '\000';
    for (int index = 0; index < length; ) {
      int count = 0;
      int first = index;
      c1 = input.charAt(index);
      if (IsSpace(c1)) {
        if (index + 1 >= length) break;
        c2 = input.charAt(index + 1);
        while ((IsSpace(c2)) && (index < length - 1)) {
          index++;
          count++;
          c2 = input.charAt(index);
        }
        if ((first != 0) && (index != length - 1)) {
          buffer.append(' ');
        }
        if (count == 0) {
          index++;
        }

      }
      else
      {
        buffer.append(c1);
        index++;
      }
    }
    return new String(buffer);
  }

  private static boolean IsSpace(char c) {
    return (c == ' ') || (c == '　') || (c == '\t') || (c == '\n');
  }

  public static String upper2Lower(String strInput) {
    String[] strList = strInput.split("\\s+", -1);
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < strList.length; i++)
    {
      if (isSpecail(strList[i])) {
        if (i == 0)
          buffer.append(strList[i]);
        else {
          buffer.append(" " + strList[i]);
        }
      }
      else if (i == 0)
        buffer.append(strList[i].toLowerCase());
      else {
        buffer.append(" " + strList[i].toLowerCase());
      }
    }

    return new String(buffer);
  }

  public static boolean isSpecail(String input)
  {
    return ("AND".equals(input)) || ("OR".equals(input));
  }
}