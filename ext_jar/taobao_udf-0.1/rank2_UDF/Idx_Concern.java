package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Concern extends UDF
{
  public Double evaluate(Double uv, Double pv)
  {
    if ((uv == null) || (pv == null)) {
      return IndexConst.ERROR_DOUBLE;
    }
    if (uv.equals(IndexConst.ZERO_DOUBLE)) {
      return IndexConst.ERROR_DOUBLE;
    }

    return Double.valueOf(Math.log(uv.doubleValue()) / Math.log(10.0D) * uv.doubleValue() * (1.0D + 1.0D * pv.doubleValue() / uv.doubleValue() / 1000.0D) * 0.28D);
  }
}