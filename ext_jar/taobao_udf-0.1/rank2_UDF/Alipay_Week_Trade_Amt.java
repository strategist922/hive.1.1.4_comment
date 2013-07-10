package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Alipay_Week_Trade_Amt extends UDF
{
  public Long evaluate(Long week_trade_amt)
  {
    if (week_trade_amt == null) {
      return IndexConst.ERROR_LONG;
    }
    return week_trade_amt;
  }
}