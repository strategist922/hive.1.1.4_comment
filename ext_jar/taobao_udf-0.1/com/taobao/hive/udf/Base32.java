package com.taobao.hive.udf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Base32
{
  private static final Map ENCODE_MAP = new HashMap();
  private static final Map DECODE_MAP = new HashMap();
  private static final String base32Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
  private static final int[] base32Lookup = { 255, 255, 26, 27, 28, 29, 30, 31, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 255, 255, 255, 255, 255, 255, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 255, 255, 255, 255, 255 };

  public static final String encode(byte[] bytes)
  {
    int i = 0;
    int index = 0;
    int digit = 0;

    StringBuffer base32 = new StringBuffer((bytes.length + 7) * 8 / 5);

    while (i < bytes.length) {
      int currByte = bytes[i] >= 0 ? bytes[i] : bytes[i] + 256;

      if (index > 3)
      {
        int nextByte;
        int nextByte;
        if (i + 1 < bytes.length) {
          nextByte = bytes[(i + 1)] >= 0 ? bytes[(i + 1)] : bytes[(i + 1)] + 256;
        }
        else {
          nextByte = 0;
        }

        digit = currByte & 255 >> index;
        index = (index + 5) % 8;
        digit <<= index;
        digit |= nextByte >> 8 - index;
        i++;
      } else {
        digit = currByte >> 8 - (index + 5) & 0x1F;
        index = (index + 5) % 8;

        if (index == 0) {
          i++;
        }
      }

      base32.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".charAt(digit));
    }

    return base32.toString();
  }

  public static final byte[] decode(String base32)
  {
    byte[] bytes = new byte[base32.length() * 5 / 8];

    int i = 0; int index = 0; for (int offset = 0; i < base32.length(); i++) {
      int lookup = base32.charAt(i) - '0';

      if ((lookup < 0) || (lookup >= base32Lookup.length))
      {
        continue;
      }
      int digit = base32Lookup[lookup];

      if (digit == 255)
      {
        continue;
      }
      if (index <= 3) {
        index = (index + 5) % 8;

        if (index == 0)
        {
          int tmp90_88 = offset;
          byte[] tmp90_86 = bytes; tmp90_86[tmp90_88] = (byte)(tmp90_86[tmp90_88] | digit);
          offset++;

          if (offset >= bytes.length)
            break;
        }
        else
        {
          int tmp115_113 = offset;
          byte[] tmp115_111 = bytes; tmp115_111[tmp115_113] = (byte)(tmp115_111[tmp115_113] | digit << 8 - index);
        }
      } else {
        index = (index + 5) % 8;
        int tmp141_139 = offset;
        byte[] tmp141_137 = bytes; tmp141_137[tmp141_139] = (byte)(tmp141_137[tmp141_139] | digit >>> index);
        offset++;

        if (offset >= bytes.length)
          break;
        int tmp168_166 = offset;
        byte[] tmp168_164 = bytes; tmp168_164[tmp168_166] = (byte)(tmp168_164[tmp168_166] | digit << 8 - index);
      }
    }

    return bytes;
  }

  public static final String encodeString(String str)
  {
    if (null == str) {
      return null;
    }
    try
    {
      StringBuffer sb = new StringBuffer();
      String encode = "GBK";
      byte[] bs = str.getBytes(encode);

      sb.append(DECODE_MAP.get(encode)).append(",");
      sb.append(encode(bs));
      return sb.toString().toLowerCase(); } catch (Exception e) {
    }
    return "";
  }

  public static final String decodeString(String str)
  {
    if ((null == str) || (str.length() < 3)) {
      return str;
    }

    str = str.toUpperCase();
    try {
      String encodeKey = str.substring(0, 1);
      String encode = (String)ENCODE_MAP.get(encodeKey);

      if (null == encode) {
        encode = "GBK";
      }

      byte[] bs = decode(str.substring(2));

      return new String(bs, encode); } catch (Exception e) {
    }
    return "";
  }

  static
  {
    ENCODE_MAP.put("G", "GBK");
    ENCODE_MAP.put("U", "UTF-8");

    for (Iterator it = ENCODE_MAP.entrySet().iterator(); it.hasNext(); ) {
      Map.Entry e = (Map.Entry)it.next();

      DECODE_MAP.put(e.getValue(), e.getKey());
    }
  }
}