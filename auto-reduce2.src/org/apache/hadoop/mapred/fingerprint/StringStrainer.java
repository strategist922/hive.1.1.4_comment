package org.apache.hadoop.mapred.fingerprint;

import java.io.PrintStream;

public class StringStrainer
  implements FilterStrainer
{
  private String str;

  public StringStrainer(String str)
  {
    this.str = str;
  }

  public String filter(String input)
  {
    if ((input == null) || ("".equals(input))) {
      return "";
    }
    if ((this.str == null) || ("".equals(this.str))) {
      return input;
    }
    return input.replaceAll(this.str, "");
  }

  public static void main(String[] args)
  {
    StringStrainer ss = new StringStrainer("abc");
    System.out.println(ss.filter("abcdefghabcdefghabdefgh"));
  }
}