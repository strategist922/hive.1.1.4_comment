package com.taobao.mrsstd.hiveudf.extend;

import com.taobao.mrsstd.hiveudf.util.UDFUtil;
import java.math.BigDecimal;

public class JavaCHGRatio extends Parent
{
  public String evaluate(String function, String[] values)
  {
    String strIdx = values[0];
    String strIdxLast = values[1];

    Double result = null;
    if ((strIdx == null) || (strIdx.trim().length() == 0)) {
      throw new RuntimeException("IDX is null");
    }
    if ((strIdxLast == null) || (strIdxLast.trim().length() == 0)) {
      if (values.length > 2)
        result = Double.valueOf(values[2]);
      else
        result = Double.valueOf(0.0D);
    }
    else {
      Double doubleIdx = Double.valueOf(strIdx);
      Double doubleIdxLast = Double.valueOf(strIdxLast);
      if (doubleIdxLast.doubleValue() == 0.0D) {
        if (values.length > 2)
          result = Double.valueOf(values[3]);
        else
          result = Double.valueOf(0.0D);
      }
      else {
        result = Double.valueOf((doubleIdx.doubleValue() - doubleIdxLast.doubleValue()) / doubleIdxLast.doubleValue());
      }

    }

    BigDecimal formatResult = new BigDecimal(String.valueOf(result));
    formatResult = formatResult.setScale(4, 4);

    return UDFUtil.delZero(formatResult.toPlainString());
  }
}