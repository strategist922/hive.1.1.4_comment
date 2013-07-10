package rank2_UDF;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;

public class date_in_prd extends UDF
{
  private static final int MILLISECS_PER_DAY = 86400000;
  private static final DateFormat df = new SimpleDateFormat("yyyyMMdd");

  public Boolean evaluate(String day1, String day2, Long diffRange)
  {
    if ((diffRange == null) || (diffRange.longValue() <= 0L)) {
      return Boolean.valueOf(false);
    }
    try
    {
      df.setLenient(false);
      df.setLenient(false);

      Date date1 = df.parse(day1);
      Date date2 = df.parse(day2);

      long diff = date1.getTime() - date2.getTime();
      long days = Math.abs(diff / 86400000L);

      if ((days >= 0L) && (days < diffRange.longValue())) {
        return Boolean.valueOf(true);
      }
      return Boolean.valueOf(false);
    }
    catch (Throwable t)
    {
      System.err.println(t);
    }return Boolean.valueOf(false);
  }
}