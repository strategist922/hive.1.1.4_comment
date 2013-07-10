package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Dlt_Rank_Get extends UDF
{
  public Long evaluate(Long idx_dlt_rank)
  {
    return idx_dlt_rank;
  }
}