package com.taobao.hive.udf;

import java.net.URLDecoder;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SearchURLDecode extends UDF
{
  Text dstURL;

  public SearchURLDecode()
  {
    this.dstURL = new Text();
  }

  public Text evaluate(Text urlText)
  {
    if (urlText == null) {
      return null;
    }
    String url = urlText.toString();
    if (url != null) {
      try {
        url = url.trim();
        if (isgbk(url))
          url = URLDecoder.decode(url, "GBK");
        else
          url = URLDecoder.decode(url, "UTF-8");
      }
      catch (Exception e) {
        this.dstURL.set(url);
        return this.dstURL;
      }
    }
    this.dstURL.set(url);
    return this.dstURL;
  }

  public boolean isUtf8Url(String text)
  {
    text = text.toLowerCase();
    int p = text.indexOf("%");
    if ((p != -1) && (text.length() - p > 9)) {
      text = text.substring(p, p + 9);
    }
    return Utf8codeCheck(text);
  }

  private static boolean isgbk(String str)
  {
    String substr = get4str(str);
    if (substr.length() < 4) {
      return false;
    }
    String str1 = substr.trim().substring(0, 2);
    String str2 = substr.trim().substring(2, 4);

    int value1 = Integer.decode("0x" + str1).intValue();
    int value2 = Integer.decode("0x" + str2).intValue();

    return (value1 >= 129) && (value1 <= 254) && 
      (value2 >= 64) && (value2 <= 254);
  }

  private static String get4str(String str)
  {
    String substr = new String();
    String substr1 = new String();
    if (str.length() < 2)
      return "";
    int index = str.indexOf("%");

    substr = get4substr(str.trim(), index);
    if (substr.length() < 5) {
      return "";
    }
    if (substr.substring(2, 3).equals("%") == true) {
      substr1 = substr.substring(0, 2) + substr.substring(3);
    }
    else {
      return "";
    }
    return substr1;
  }

  private static String get4substr(String str, int index) {
    if (index == -1)
      return "";
    if (str.substring(index).length() <= 1) {
      return "";
    }
    String substr = new String();
    int i = 0;
    while (index + 2 < str.length()) {
      substr = str.substring(index);
      if (substr.startsWith("%") == true) {
        substr = str.substring(index + 1);
        index++;
      }
      if (Integer.decode("0x" + substr.substring(0, 2)).intValue() >= 128)
        break;
      i = substr.indexOf("%");
      if (i == -1)
        break;
      index += i;
    }
    return substr;
  }

  private boolean Utf8codeCheck(String text)
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

  public Text evaluate(Text srcURL, Text enc)
  {
    if (srcURL == null) {
      return null;
    }
    String url = srcURL.toString();
    if (url != null) {
      try {
        url = url.trim();
        if (isgbk(url))
          url = URLDecoder.decode(url, "GBK");
        else
          url = URLDecoder.decode(url, "UTF-8");
      }
      catch (Exception e) {
        this.dstURL.set(url);
        return this.dstURL;
      }
    }
    this.dstURL.set(url);
    return this.dstURL;
  }

  private static class Escape {
    private static final String[] hex = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D", "5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E", "7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F", "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };

    private static final byte[] val = { 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 63, 63, 63, 63, 63, 63, 63, 10, 11, 12, 13, 14, 15, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 10, 11, 12, 13, 14, 15, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63 };

    public static String escape(String s)
    {
      StringBuffer sbuf = new StringBuffer();
      int len = s.length();
      for (int i = 0; i < len; i++) {
        int ch = s.charAt(i);
        if ((65 <= ch) && (ch <= 90)) {
          sbuf.append((char)ch);
        } else if ((97 <= ch) && (ch <= 122)) {
          sbuf.append((char)ch);
        } else if ((48 <= ch) && (ch <= 57)) {
          sbuf.append((char)ch);
        } else if ((ch == 45) || (ch == 95) || (ch == 46) || (ch == 33) || (ch == 126) || (ch == 42) || (ch == 39) || (ch == 40) || (ch == 41))
        {
          sbuf.append((char)ch);
        } else if (ch <= 127) {
          sbuf.append('%');
          sbuf.append(hex[ch]);
        } else {
          sbuf.append('%');
          sbuf.append('u');
          sbuf.append(hex[(ch >>> 8)]);
          sbuf.append(hex[(0xFF & ch)]);
        }
      }
      return sbuf.toString();
    }

    public static String unescape(String s) {
      StringBuffer sbuf = new StringBuffer();
      int i = 0;
      int len = s.length() - 1;
      while (i < len) {
        int ch = s.charAt(i);
        if ((65 <= ch) && (ch <= 90)) {
          sbuf.append((char)ch);
        } else if ((97 <= ch) && (ch <= 122)) {
          sbuf.append((char)ch);
        } else if ((48 <= ch) && (ch <= 57)) {
          sbuf.append((char)ch);
        } else if ((ch == 45) || (ch == 95) || (ch == 46) || (ch == 33) || (ch == 126) || (ch == 42) || (ch == 39) || (ch == 40) || (ch == 41))
        {
          sbuf.append((char)ch);
        } else if (ch == 37) {
          int cint = 0;
          if ('u' != s.charAt(i + 1)) {
            cint = cint << 4 | val[s.charAt(i + 1)];
            cint = cint << 4 | val[s.charAt(i + 2)];
            i += 2;
          } else {
            cint = cint << 4 | val[s.charAt(i + 2)];
            cint = cint << 4 | val[s.charAt(i + 3)];
            cint = cint << 4 | val[s.charAt(i + 4)];
            cint = cint << 4 | val[s.charAt(i + 5)];
            i += 5;
          }
          sbuf.append((char)cint);
        } else {
          sbuf.append((char)ch);
        }
        i++;
      }
      return sbuf.toString();
    }
  }
}