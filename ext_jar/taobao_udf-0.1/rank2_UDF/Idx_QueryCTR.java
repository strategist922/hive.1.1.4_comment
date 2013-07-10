package rank2_UDF;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_QueryCTR extends UDF
{
  public Double evaluate(Double ipv, Double pv)
  {
    try
    {
      if (pv.equals(IndexConst.ZERO_DOUBLE)) {
        return IndexConst.ERROR_DOUBLE;
      }
      return Double.valueOf(ipv.doubleValue() * 1.0D / pv.doubleValue());
    } catch (Throwable t) {
      System.err.println(t);
    }return IndexConst.ERROR_DOUBLE;
  }
}