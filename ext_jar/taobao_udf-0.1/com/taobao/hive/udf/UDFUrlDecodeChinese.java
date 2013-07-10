package com.taobao.hive.udf;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFUrlDecodeChinese extends UDF
{
  private Text res = new Text();
  private Object UnsupportedEncodingException;

  private String urlencode_return(String splitUrlString, String encodeValue)
    throws UnsupportedEncodingException
  {
    return URLDecoder.decode(splitUrlString, encodeValue);
  }

  private String url_decode_code(String url_encode)
    throws UnsupportedEncodingException
  {
    if (url_encode == null)
    {
      return null;
    }

    StringBuffer StrValue = new StringBuffer("");
    StringBuffer StrUrlCode = new StringBuffer("");
    StringBuffer StrCharCode = new StringBuffer("");

    int m = url_encode.length();
    int n = 0;
    while (n < m)
    {
      if (url_encode.charAt(n) == "%".charAt(0))
      {
        if (StrCharCode.length() > 0)
        {
          StrValue.append(toChinese(StrCharCode.toString(), "gb2312"));
          StrCharCode.delete(0, StrCharCode.length());
        }

        StrUrlCode.append(url_encode.substring(n, n + 3));
        n += 3; continue;
      }

      if (url_encode.charAt(n) == "\\".charAt(0))
      {
        if (StrUrlCode.length() > 0)
        {
          StrValue.append(urlencode_return(StrUrlCode.toString(), "gb2312"));
          StrUrlCode.delete(0, StrUrlCode.length());
        }

        if ((m - n >= 2) && (url_encode.substring(n, n + 2).equals("\\x")))
        {
          StrCharCode.append(url_encode.substring(n, n + 4).replace("\\x", ""));
          n += 4; continue;
        }

        StrValue.append(toChinese(StrCharCode.toString(), "gb2312"));
        StrCharCode.delete(0, StrValue.length());
        StrValue.append(url_encode.substring(n, n + 1));
        n += 1; continue;
      }

      if (url_encode.indexOf("\\", n) < url_encode.indexOf("%", n))
      {
        if (StrCharCode.length() > 0)
        {
          StrValue.append(toChinese(StrCharCode.toString(), "gb2312"));
          StrCharCode.delete(0, StrValue.length());
        }
        if (StrUrlCode.length() > 0)
        {
          StrValue.append(urlencode_return(StrUrlCode.toString(), "gb2312"));
          StrUrlCode.delete(0, StrUrlCode.length());
        }
        if (url_encode.indexOf("\\", n) > 0)
        {
          StrValue.append(url_encode.substring(n, url_encode.indexOf("\\", n)));
          n = url_encode.indexOf("\\", n); continue;
        }

        StrValue.append(url_encode.substring(n, url_encode.indexOf("%", n)));
        n = url_encode.indexOf("%", n); continue;
      }

      if (url_encode.indexOf("\\", n) > url_encode.indexOf("%", n))
      {
        if (StrCharCode.length() > 0)
        {
          StrValue.append(toChinese(StrCharCode.toString(), "gb2312"));
          StrCharCode.delete(0, StrValue.length());
        }
        if (StrUrlCode.length() > 0)
        {
          StrValue.append(urlencode_return(StrUrlCode.toString(), "gb2312"));
          StrUrlCode.delete(0, StrUrlCode.length());
        }
        if (url_encode.indexOf("%", n) > 0)
        {
          StrValue.append(url_encode.substring(n, url_encode.indexOf("%", n)));
          n = url_encode.indexOf("%", n); continue;
        }

        StrValue.append(url_encode.substring(n, url_encode.indexOf("\\", n)));
        n = url_encode.indexOf("\\", n); continue;
      }

      if ((url_encode.indexOf("\\", n) >= 0) || (url_encode.indexOf("%", n) >= 0))
        continue;
      StrValue.append(url_encode);
      n = m;
    }

    if (StrCharCode.length() > 0)
    {
      StrValue.append(toChinese(StrCharCode.toString(), "gb2312"));
      StrCharCode.delete(0, StrCharCode.length());
    }
    if (StrUrlCode.length() > 0)
    {
      StrValue.append(urlencode_return(StrUrlCode.toString(), "gb2312"));
      StrUrlCode.delete(0, StrUrlCode.length());
    }

    return StrValue.toString();
  }

  public String url_decode(String url, String encodeStr)
  {
    if (url == null) {
      return new String();
    }
    int len = url.length();
    String str = new String(url);
    String out = new String();
    int i = 0;
    String tmp = new String();
    while (i < len)
    {
      try {
        tmp = str.substring(i, i + 1);
        if (tmp.equals("+")) {
          out = out + " "; } else {
          if ((i < len - 1) && (str.substring(i, i + 2).equals("\\x")))
          {
            String tmp1 = str.substring(i + 2, i + 4);
            if (i + 6 >= len - 1)
              break;
            if (str.substring(i + 4, i + 6).equals("\\x"))
            {
              tmp1 = tmp1 + str.substring(i + 6, i + 8);
            }
            else
            {
              i += 4;
              continue;
            }
            try {
              out = out + toChinese(tmp1, encodeStr);
              i += 8;
            } catch (UnsupportedEncodingException e) {
              System.out.println("UnsupportedEncodingException");
              break label292;
            }
          }
          out = out + tmp;
        }
      } catch (Exception e) {
        label292: e.printStackTrace();
        System.out.println(url);
      }i++;
    }
    return out.trim().replace("%20", " ");
  }

  private StringBuffer toChinese(String asc, String encodingString) throws UnsupportedEncodingException {
    int len = asc.length() / 2;
    byte[] bytes = new byte[len];
    for (int i = 0; i < len; i++)
    {
      String code = asc.substring(i * 2, (i + 1) * 2);
      bytes[i] = Integer.valueOf(code, 16).byteValue();
    }
    String chi = new String(bytes, encodingString);
    StringBuffer StrChi = new StringBuffer(chi);
    return StrChi;
  }

  public Text evaluate(Text StringCode) throws UnsupportedEncodingException
  {
    if (StringCode == null)
    {
      return null;
    }
    try {
      String translate_val = URLDecoder.decode(StringCode.toString().replace("\\x", "%"), "gbk");
      this.res.set(translate_val);
    }
    catch (Exception e)
    {
      return null;
    }

    return this.res;
  }

  public static void main(String[] args)
  {
    UDFUrlDecodeChinese test = new UDFUrlDecodeChinese();
    try {
      System.out.println(test.evaluate(new Text("525ON028r154GmE866033643w658O7")));
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
}