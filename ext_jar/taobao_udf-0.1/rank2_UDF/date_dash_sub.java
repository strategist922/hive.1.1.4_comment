package rank2_UDF;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;

public class date_dash_sub extends UDF
{
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

  public String evaluate(String initDateStr, Long days)
  {
    try
    {
      if (days == null) {
        return initDateStr;
      }
      dateFormat.setLenient(false);
      Date initDate = dateFormat.parse(initDateStr);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(initDate);
      calendar.add(5, (int)(-days.longValue()));
      Date resultDate = calendar.getTime();
      String result = dateFormat.format(resultDate);
      return result;
    } catch (Throwable t) {
      t.printStackTrace();
    }return "";
  }
}