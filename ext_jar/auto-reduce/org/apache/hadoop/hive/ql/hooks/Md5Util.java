package org.apache.hadoop.hive.ql.hooks;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Md5Util
{
  public static final Pattern DATATIME_PATTERN = Pattern.compile("([1-2][0-9]{3}([-|/|.|_|:])?(((0[13578]|1[02])([-|/|.|_|:])?(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)([-|/|.|_|:])?(0[1-9]|[12][0-9]|30))|(02([-|/|.|_|:])?([0-1][0-9]|2[0-9]))))[\\s]?(([0-1][0-9]|2[0-3])([-|/|.|_|:])?([0-5][0-9])([-|/|.|_|:])?([0-5][0-9])){0,1}");

  public static final char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  public static String denoiseDataTime(String str)
  {
    return denoise(str, DATATIME_PATTERN);
  }

  public static String denoise(String str, Pattern p)
  {
    if ((str == null) || (str.equals(""))) {
      return "";
    }
    Matcher m = p.matcher(str);
    return m.replaceAll("");
  }

  public static String getMd5sum(byte[] b)
  {
    try
    {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(b, 0, b.length);
      return toHexString(md5.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }return "";
  }

  private static String toHexString(byte[] b)
  {
    StringBuilder sb = new StringBuilder(b.length * 2);
    for (int i = 0; i < b.length; i++) {
      sb.append(hexChar[((b[i] & 0xF0) >>> 4)]);
      sb.append(hexChar[(b[i] & 0xF)]);
    }
    return sb.toString();
  }
}