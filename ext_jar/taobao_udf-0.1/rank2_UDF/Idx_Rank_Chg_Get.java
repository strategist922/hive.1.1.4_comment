package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Rank_Chg_Get extends UDF
{
  public Long evaluate(Long idx_rank_yesterday, Long idx_rank_today, Long N4)
  {
    if ((idx_rank_yesterday == null) && (idx_rank_today == null)) {
      return IndexConst.ERROR_LONG;
    }
    if (idx_rank_today == null) {
      return N4;
    }
    if (idx_rank_yesterday == null) {
      return Long.valueOf(-N4.longValue());
    }
    return Long.valueOf(idx_rank_today.longValue() - idx_rank_yesterday.longValue());
  }
}