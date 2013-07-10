package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Rank_Get extends UDF
{
  public Double evaluate(Double idx_rank_last, Double N3)
  {
    if (idx_rank_last == null) {
      return N3;
    }
    return idx_rank_last;
  }
}