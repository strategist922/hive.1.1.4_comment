package com.taobao.mrsstd.hiveudf.extend;

import com.taobao.mrsstd.hiveudf.util.UDFUtil;
import java.math.BigDecimal;

public class JavaCHGIdx extends Parent
{
  public String evaluate(String function, String[] values)
  {
    String strIdx = values[0];
    String strIdxLast = values[1];

    if ((strIdx == null) || (strIdx.trim().length() == 0)) {
      throw new RuntimeException("IDX is null");
    }
    Double idx = Double.valueOf(strIdx);
    Double result;
    Double result;
    if ((strIdxLast == null) || (strIdxLast.trim().length() == 0)) {
      result = idx;
    } else {
      Double idxLast = Double.valueOf(strIdxLast);
      Double result;
      if (idxLast.doubleValue() < 0.0D)
        result = idx;
      else {
        result = Double.valueOf(idx.doubleValue() - idxLast.doubleValue());
      }
    }

    BigDecimal formatResult = new BigDecimal(String.valueOf(result));
    formatResult = formatResult.setScale(4, 4);

    return UDFUtil.delZero(formatResult.toPlainString());
  }
}