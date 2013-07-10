package com.taobao.ad.data.search.udf;

public class GBKConvertor
{
  public static String Gbk2GbkSimple(String inputStr)
  {
    try
    {
      byte[] byInput = inputStr.getBytes("GBK");
      byte[] byoutput = new byte[byInput.length];
      int[] intValue = new int[2];
      int offset = 0;
      int count = 0;
      Gbk2GbkSimplified gbk2Gbk = Gbk2GbkSimplified.getInstance();
      for (int index = 0; index < byInput.length; ) {
        intValue[0] = (byInput[index] & 0xFF);
        if ((intValue[0] >= 129) && (intValue[0] <= 254)) {
          if (index + 1 < byInput.length) {
            intValue[1] = (byInput[(index + 1)] & 0xFF);
            if ((intValue[1] >= 64) && (intValue[1] <= 254) && (intValue[1] != 127))
            {
              offset = (intValue[0] - 129) * 191 + (intValue[1] - 64);

              byoutput[(count++)] = (byte)gbk2Gbk.gbkTable[(offset * 2)];
              byoutput[(count++)] = (byte)gbk2Gbk.gbkTable[(offset * 2 + 1)];
              index += 2; continue;
            }
            byoutput[(count++)] = byInput[(index++)]; continue;
          }

          byoutput[(count++)] = byInput[(index++)]; continue;
        }

        byoutput[(count++)] = byInput[(index++)];
      }

      String output = new String(byoutput, 0, count, "GBK");
      return output;
    } catch (Exception e) {
      e.printStackTrace();
    }return "";
  }

  public static String Sbc2Dbc(String inputStr)
  {
    try {
      byte[] byInput = inputStr.getBytes("GBK");
      byte[] bytemp = new byte[byInput.length];
      int[] intValue = new int[2];
      int count = 0;
      for (int index = 0; index < byInput.length; ) {
        intValue[0] = (byInput[index] & 0xFF);
        if (index + 1 < byInput.length) {
          intValue[1] = (byInput[(index + 1)] & 0xFF);
          if ((intValue[0] == 161) && (intValue[1] == 163)) {
            bytemp[(count++)] = 46;
            index += 2; continue;
          }if ((intValue[0] == 161) && ((intValue[1] == 176) || (intValue[1] == 177)))
          {
            bytemp[(count++)] = 34;
            index += 2; continue;
          }if ((intValue[0] == 161) && ((intValue[1] == 174) || (intValue[1] == 175)))
          {
            bytemp[(count++)] = 39;
            index += 2; continue;
          }if (IsChineseChar(intValue[0], intValue[1])) {
            bytemp[(count++)] = (byte)(intValue[1] - 128);
            index += 2; continue;
          }if (intValue[0] >= 128)
          {
            bytemp[(count++)] = (byte)intValue[0];
            bytemp[(count++)] = (byte)intValue[1];
            index += 2; continue;
          }
          bytemp[(count++)] = (byte)intValue[0];
          index++; continue;
        }

        bytemp[(count++)] = (byte)intValue[0];
        index++;
      }

      return new String(bytemp, 0, count, "GBK");
    } catch (Exception e) {
      e.printStackTrace();
    }return "";
  }

  private static boolean IsChineseChar(int n1, int n2)
  {
    return (n1 == 163) && (n2 > 160) && (n2 < 255);
  }
}