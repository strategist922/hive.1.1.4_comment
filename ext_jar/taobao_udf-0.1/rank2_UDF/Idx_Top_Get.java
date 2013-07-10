package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Top_Get extends UDF
{
  public Double evaluate(Double idx, Double N1)
  {
    if (idx == null) {
      return N1;
    }
    return idx;
  }
}