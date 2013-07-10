package com.taobao.mrsstd.hiveudf.util;

import java.io.PrintStream;

public class Constants
{
  public static final char SPACE = ' ';
  public static final String SINGLE_QUOTE = "'";
  public static final String DEFAULT = "-99";
  public static final String SEPERATOR = "\t";
  public static final String COMMA = ",";
  public static final String DOT = ".";
  public static final String EMPTY = "";
  public static final String PARENTHESIS_LEFT = "(";
  public static final String PARENTHESIS_RIGHT = ")";
  public static final char SIGN_ADD = '+';
  public static final char SIGN_SUBTRACT = '-';
  public static final char SIGN_MULTIPLICATION = '*';
  public static final char SIGN_DIVIDE = '/';
  public static final char LOGIC_EQUAL = '=';
  public static final char LOGIC_GREAT = '>';
  public static final char LOGIC_LESS = '<';
  public static final char[] LOGIC_GREATEQUAL = { '>', '=' };
  public static final char[] LOGIC_LESSEQUAL = { '<', '=' };
  public static final char[] LOGIC_NOTEQUAL = { '!', '=' };
  public static final char[] LOGIC_OR = { 'o', 'r' };
  public static final char[] LOGIC_AND = { 'a', 'n', 'd' };
  public static final char[] LOGIC_NOT = { 'n', 'o', 't' };
  public static final char STRING = 'C';
  public static final char POS_NUM = '0';
  public static final char NEG_NUM = '1';
  public static final char FIELD = 'F';
  public static final String REGEX_NUMBER = "\\d+\\.{0,1}\\d*";
  public static final String REGEX_STRING = "'([一-龥a-zA-Z0-9\\._-]*)'";
  public static final String REGEX_DELZERO = "\\.0+$";
  public static final String REGEX_DOT = "\\.";
  public static final int MODE_NUMBER = 1;
  public static final int MODE_STRING = 2;
  public static final String TRUE = "1";
  public static final String FALSE = "0";
  public static final char[] CHARS = { ' ', '+', '-', '*', '/', '>', '<', '=', '(', ')' };
  public static final String FUNCTION_DEFAULT = "S";
  public static final String FUNCTION_STRING = "S";
  public static final String FUNCTION_NUMBER = "N";
  public static final String EXTEND_PACKAGE = "com.taobao.mrsstd.hiveudf.extend.";

  public static void main(String[] args)
  {
    if ("'-0.02'".matches("\\d+\\.{0,1}\\d*"))
      System.out.println("true");
  }
}