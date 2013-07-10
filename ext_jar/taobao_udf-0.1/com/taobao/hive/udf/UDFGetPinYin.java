package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFGetPinYin extends UDF
{
  private Text result = null;

  private static String DEFALT_ENCODE = "UTF-8";
  private int[][] constantsArray;

  public UDFGetPinYin()
  {
    this.result = new Text();
    this.constantsArray = new int[123][2];
    initConstantsArray();
  }

  private void initConstantsArray() {
    this.constantsArray[97] = { 45217, 45252 };
    this.constantsArray[98] = { 45253, 45760 };
    this.constantsArray[99] = { 45761, 46317 };
    this.constantsArray[100] = { 46318, 46825 };
    this.constantsArray[101] = { 46826, 47009 };
    this.constantsArray[102] = { 47010, 47296 };
    this.constantsArray[103] = { 47297, 47613 };
    this.constantsArray[104] = { 47614, 48118 };
    this.constantsArray[105] = { 48118, 48118 };

    this.constantsArray[106] = { 48119, 49061 };
    this.constantsArray[107] = { 49062, 49323 };
    this.constantsArray[108] = { 49324, 49895 };
    this.constantsArray[109] = { 49896, 50370 };
    this.constantsArray[110] = { 50371, 50613 };
    this.constantsArray[111] = { 50614, 50621 };
    this.constantsArray[112] = { 50622, 50905 };
    this.constantsArray[113] = { 50906, 51386 };
    this.constantsArray[114] = { 51387, 51445 };
    this.constantsArray[115] = { 51446, 52217 };
    this.constantsArray[116] = { 52218, 52697 };

    this.constantsArray[117] = { 52697, 52697 };
    this.constantsArray[118] = { 52697, 52697 };
    this.constantsArray[119] = { 52698, 52979 };
    this.constantsArray[120] = { 52980, 53640 };
    this.constantsArray[121] = { 53689, 54480 };
    this.constantsArray[122] = { 54481, 55289 };
  }

  private int getIndex(int b) {
    if (this.constantsArray.length == 0)
      return 0;
    int begin = 97;
    int end = this.constantsArray.length - 1;
    while (begin <= end) {
      int mid = (end + begin) / 2;
      if ((b >= this.constantsArray[mid][0]) && (b <= this.constantsArray[mid][1]))
        return mid;
      if (b < this.constantsArray[mid][0])
        end = mid - 1;
      else {
        begin = mid + 1;
      }
    }
    return 0;
  }

  public Text evaluate(String str) {
    return evaluate(str, "UTF-8");
  }

  public Text evaluate(String str, String encode) {
    if ((str == null) || ("".equals(str))) return null;
    if ((encode == null) || ("".equals(encode))) encode = DEFALT_ENCODE;
    String c = getFirstCharacter(str, encode);
    if ("".equals(c)) return null;
    this.result.set(c);
    return this.result;
  }

  private String getFirstCharacter(String str, String encode) {
    try {
      str = str.toLowerCase();
      String res = new String(str.getBytes(), 0, str.getBytes().length, encode);
      byte[] b = res.getBytes("GBK");
      if (b.length == 1) return str.toUpperCase();
      if (b.length > 1) {
        int idx = 0;
        if ((b[0] >= 97) && (b[0] <= 122)) {
          idx = b[0];
        } else {
          int i1 = b[0] & 0xFF;
          int i2 = b[1] & 0xFF;
          idx = getIndex(i1 * 256 + i2);
        }
        if ((idx >= 97) && (idx <= 122)) {
          return String.valueOf((char)idx).toUpperCase();
        }
      }
      return ""; } catch (Exception e) {
    }
    return "";
  }

  public static void main(String[] args)
  {
    UDFGetPinYin udf = new UDFGetPinYin();

    System.out.println(Integer.valueOf("D4D1", 16).toString());

    System.out.println(udf.evaluate("完美世界", "UTF-8").toString());
    System.out.println(udf.evaluate("W", "GBK").toString());
    System.out.println(udf.evaluate("T-中文", "UTF-8").toString());
    System.out.println(udf.evaluate("linling4325").toString());
    System.out.println(udf.evaluate("狠毒de蝎").toString());
    System.out.println(udf.evaluate("婷婷").toString());
  }
}