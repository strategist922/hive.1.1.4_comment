package org.apache.hadoop.mapred.fingerprint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util
{
  public static final char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  public static String md5sum(byte[] b)
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