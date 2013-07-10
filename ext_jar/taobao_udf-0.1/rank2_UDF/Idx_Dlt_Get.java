package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Dlt_Get extends UDF
{
  public Double evaluate(Double todayIdx, Double yesterdayIdx, Double N2)
  {
    try
    {
      if ((yesterdayIdx == null) && (todayIdx == null))
        return N2;
      if (yesterdayIdx == null)
      {
        return IndexConst.ERROR_DOUBLE;
      }if (todayIdx == null) {
        return Double.valueOf(N2.doubleValue() - yesterdayIdx.doubleValue());
      }
      return Double.valueOf(todayIdx.doubleValue() - yesterdayIdx.doubleValue()); } catch (Throwable t) {
    }
    return N2;
  }
}