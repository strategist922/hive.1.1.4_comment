package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Dlt_RT_Rank_Get extends UDF
{
  public Long evaluate(Long idx_chg_rate_rank, Long today_idx_rank, Long yesterday_idx_rank, Long N6, Long N7)
  {
    try
    {
      if (today_idx_rank == null) {
        return N7;
      }

      if (yesterday_idx_rank == null) {
        if ((today_idx_rank.longValue() <= N6.longValue()) && (today_idx_rank.longValue() >= 0L)) {
          return idx_chg_rate_rank;
        }
        return N7;
      }

      if ((today_idx_rank.longValue() < 0L) || (yesterday_idx_rank.longValue() < 0L)) {
        return N7;
      }
      Long min = today_idx_rank.longValue() >= yesterday_idx_rank.longValue() ? yesterday_idx_rank : today_idx_rank;

      if (min.longValue() <= N6.longValue()) {
        return idx_chg_rate_rank;
      }
      return N7;
    } catch (Throwable e) {
    }
    return N7;
  }
}