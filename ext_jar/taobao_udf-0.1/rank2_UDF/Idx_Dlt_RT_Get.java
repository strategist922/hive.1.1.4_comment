package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Dlt_RT_Get extends UDF
{
  public Double evaluate(Double todayIdx, Double yesterdayIdx, Double N5)
  {
    try
    {
      if ((todayIdx == null) && (yesterdayIdx == null)) {
        return N5;
      }
      if (todayIdx == null) {
        return Double.valueOf((N5.doubleValue() - yesterdayIdx.doubleValue()) / yesterdayIdx.doubleValue());
      }
      if (yesterdayIdx == null) {
        return Double.valueOf((todayIdx.doubleValue() - N5.doubleValue()) / N5.doubleValue());
      }
      if ((yesterdayIdx.equals(IndexConst.ZERO_DOUBLE)) && (todayIdx.equals(IndexConst.ZERO_DOUBLE))) {
        return IndexConst.ZERO_DOUBLE;
      }
      if (yesterdayIdx.equals(IndexConst.ZERO_DOUBLE)) {
        return Double.valueOf((todayIdx.doubleValue() - N5.doubleValue()) / N5.doubleValue());
      }
      if (todayIdx.equals(IndexConst.ZERO_DOUBLE)) {
        return Double.valueOf((todayIdx.doubleValue() - yesterdayIdx.doubleValue()) / yesterdayIdx.doubleValue());
      }
      return Double.valueOf((todayIdx.doubleValue() - yesterdayIdx.doubleValue()) / yesterdayIdx.doubleValue());
    } catch (Throwable t) {
    }
    return N5;
  }
}