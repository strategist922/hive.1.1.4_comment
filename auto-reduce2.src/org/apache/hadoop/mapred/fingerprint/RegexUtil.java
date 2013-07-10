package org.apache.hadoop.mapred.fingerprint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil
{
  public static String denoise(String str, Pattern p)
  {
    if ((str == null) || (str.equals(""))) {
      return "";
    }
    if (p == null) {
      return str;
    }
    Matcher m = p.matcher(str);
    return m.replaceAll("");
  }
}