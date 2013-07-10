package com.taobao.hive.udf;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HASURLDecoder
{
  static int[] base32Lookup = { 255, 255, 26, 27, 28, 29, 30, 31, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 255, 255, 255, 255, 255, 255, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 255, 255, 255, 255, 255 };

  public static String ISO2GB(String text)
  {
    String result = "";
    try {
      result = new String(text.getBytes("ISO-8859-1"), "GB2312");
    }
    catch (UnsupportedEncodingException ex) {
      result = ex.toString();
    }
    return result;
  }

  public static String GB2ISO(String text)
  {
    String result = "";
    try {
      result = new String(text.getBytes("GB2312"), "ISO-8859-1");
    }
    catch (UnsupportedEncodingException ex) {
      ex.printStackTrace();
    }
    return result;
  }

  public static String Utf8URLencode(String text)
  {
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if ((c >= 0) && (c <= 'ÿ')) {
        result.append(c);
      }
      else {
        byte[] b = new byte[0];
        try {
          b = Character.toString(c).getBytes("UTF-8");
        } catch (Exception ex) {
        }
        for (int j = 0; j < b.length; j++) {
          int k = b[j];
          if (k < 0) k += 256;
          result.append("%" + Integer.toHexString(k).toUpperCase());
        }
      }
    }
    return result.toString();
  }

  public static String Utf8URLdecode(String text)
  {
    String result = "";
    int p = 0;
    if ((text != null) && (text.length() > 0)) {
      text = text.toLowerCase();
      p = text.indexOf("%e");
      if (p == -1)
        return text;
      while (p != -1) {
        result = result + text.substring(0, p);
        text = text.substring(p, text.length());
        if ((text == "") || (text.length() < 9))
          return result;
        result = result + CodeToWord(text.substring(0, 9));
        text = text.substring(9, text.length());
        p = text.indexOf("%e");
      }
    }
    return result + text;
  }

  private static String CodeToWord(String text)
  {
    String result;
    if (Utf8codeCheck(text)) {
      byte[] code = new byte[3];
      code[0] = (byte)(Integer.parseInt(text.substring(1, 3), 16) - 256);
      code[1] = (byte)(Integer.parseInt(text.substring(4, 6), 16) - 256);
      code[2] = (byte)(Integer.parseInt(text.substring(7, 9), 16) - 256);
      try {
        result = new String(code, "UTF-8");
      } catch (UnsupportedEncodingException ex) {
        String result = null;
      }
    }
    else {
      result = text;
    }
    return result;
  }

  private static boolean Utf8codeCheck(String text)
  {
    String sign = "";
    if (text.startsWith("%e")) {
      int i = 0; for (int p = 0; p != -1; i++) {
        p = text.indexOf("%", p);
        if (p != -1)
          p++;
        sign = sign + p;
      }
    }
    return sign.equals("147-1");
  }

  public static boolean isUtf8Url(String text)
  {
    text = text.toLowerCase();
    int p = text.indexOf("%");
    if ((p != -1) && (text.length() - p > 9)) {
      text = text.substring(p, p + 9);
    }
    return Utf8codeCheck(text);
  }

  public static String decode1(String url) {
    int i = 0;
    int index = 0;
    int len = url.getBytes().length * 5 / 8 + 1;
    int offset = 0;
    byte[] b = new byte[len];
    i = 0; index = 0; for (offset = 0; i < url.length(); i++) {
      int k = url.charAt(i) - '0';
      if ((k < 0) || (k >= base32Lookup.length)) {
        continue;
      }
      int digit = base32Lookup[k];
      if (digit == 255)
      {
        continue;
      }
      if (index <= 3) {
        index = (index + 5) % 8;
        if (index == 0)
        {
          int tmp106_104 = offset;
          byte[] tmp106_102 = b; tmp106_102[tmp106_104] = (byte)(tmp106_102[tmp106_104] | digit);
          offset++;
          if (offset >= len)
            break;
        }
        else
        {
          int tmp129_127 = offset;
          byte[] tmp129_125 = b; tmp129_125[tmp129_127] = (byte)(tmp129_125[tmp129_127] | digit << 8 - index);
        }
      }
      else {
        index = (index + 5) % 8;
        int tmp155_153 = offset;
        byte[] tmp155_151 = b; tmp155_151[tmp155_153] = (byte)(tmp155_151[tmp155_153] | digit >> index);
        offset++;
        if (offset >= len)
          break;
        int tmp180_178 = offset;
        byte[] tmp180_176 = b; tmp180_176[tmp180_178] = (byte)(tmp180_176[tmp180_178] | digit << 8 - index);
      }
    }
    String str = new String();
    try {
      str = new String(b, "gbk"); } catch (UnsupportedEncodingException e) {
    }
    return str;
  }

  public static void main(String[] args)
  {
    try
    {
      String strUrl = "http%3A//www.google.com/search%3Fhl%3Dzh-CN%26q%3D%25E6%25B3%2595%25E5%259B%25BD%25E5%25AE%25A2%25E9%2582%25AE%25E7%25A5%25A8%26btnG%3DGoogle+%25E6%2590%259C%25E7%25B4%25A2%26lr%3D";
      strUrl = URLDecoder.decode(strUrl, "UTF-8");

      if (isUtf8Url(strUrl)) {
        System.out.println(Utf8URLdecode(strUrl));
      }
      else {
        System.out.println(URLDecoder.decode(strUrl, "UTF-8"));
      }

      strUrl = "http%3A//www.baidu.com/s%3Fwd%3D%25CC%25D4%25B1%25A6%26lm%3D0%26si%3D%26rn%3D10%26tn%3Dindex88_pg%26ie%3Dgb2312%26ct%3D0%26cl%3D3%26f%3D12%26oq%3D%25CC%25CD%25B1%25A6";
      strUrl = URLDecoder.decode(strUrl, "UTF-8");
      if (isUtf8Url(strUrl)) {
        System.out.println(Utf8URLdecode(strUrl));
      }
      else {
        System.out.println(URLDecoder.decode(strUrl, "UTF-8"));
      }

      strUrl = "http%3A//www.soso.com/q%3Fw%3D%u6DD8%u5B9D%26cid%3Dt.s";
      strUrl = URLDecoder.decode(strUrl, "UTF-8");
      if (isUtf8Url(strUrl)) {
        System.out.println(Utf8URLdecode(strUrl));
      }
      else
        System.out.println(URLDecoder.decode(strUrl, "UTF-8"));
    }
    catch (UnsupportedEncodingException e)
    {
      System.out.println("UnsupportedEncodingException");
    }
    catch (Exception e) {
      System.out.println("error");
    }
  }
}