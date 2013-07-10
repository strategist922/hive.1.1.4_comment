package rank2_UDF;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;

public class Idx_Sale extends UDF
{
  public Double evaluate(Long saleNum)
  {
    if (saleNum == null) {
      return IndexConst.ERROR_DOUBLE;
    }
    try
    {
      return Double.valueOf(29.0D * Math.sqrt(6.283185307179586D * saleNum.longValue()) * Math.exp(1.0D / (2L * saleNum.longValue() + 1L)));
    } catch (Throwable t) {
      System.err.println(t);
    }return IndexConst.ERROR_DOUBLE;
  }
}