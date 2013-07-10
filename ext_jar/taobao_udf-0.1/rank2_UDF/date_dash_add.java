package rank2_UDF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.hadoop.hive.ql.exec.UDF;

public class date_dash_add extends UDF
{
  private static final DateFormat fromDf = new SimpleDateFormat("yyyyMMdd");
  private static final DateFormat toDf = new SimpleDateFormat("yyyy-MM-dd");

  public String evaluate(String fromDate)
  {
    try
    {
      fromDf.setLenient(false);
      return toDf.format(fromDf.parse(fromDate));
    } catch (Throwable t) {
      t.printStackTrace();
    }return "";
  }
}