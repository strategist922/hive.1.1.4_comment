package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDF;

public class UDFGetLiveAuctions extends UDF
{
  private static Log LOG = LogFactory.getLog(UDFGetLiveAuctions.class.getName());
  private SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyyMMdd");
  private String spliter = "";

  public Date getAddDate(long deltaDay, Date baseDate)
  {
    long tmpDelta = baseDate.getTime() + deltaDay * 1000L * 3600L * 24L;
    return new Date(tmpDelta);
  }

  public long getDeltaDate(Date endDate, Date startDate) {
    return (endDate.getTime() - startDate.getTime()) / 1000L / 3600L / 24L;
  }

  private String getDeadPt(Date tmpDate, Date expectDate, Date initDate) {
    String tmpPt = new String();
    Date tmpDate1 = new Date();
    long deltaDate = getDeltaDate(expectDate, initDate);
    for (; deltaDate >= 0L; deltaDate -= 1L) {
      tmpPt = tmpPt + " " + this.spliter + " pt='";
      this.spliter = "and";
      tmpDate1 = getAddDate(deltaDate, initDate);
      tmpPt = tmpPt + this.simpleFormatter.format(tmpDate1) + "-" + this.simpleFormatter.format(tmpDate);
      tmpPt = tmpPt + "' ";
    }
    return tmpPt;
  }

  private String getLivePt(Date expectDate, Date initDate) {
    String tmpPt = new String();
    Date tmpDate1 = new Date();
    long deltaDate = getDeltaDate(expectDate, initDate);
    for (; deltaDate >= 0L; deltaDate -= 1L) {
      tmpPt = tmpPt + " " + this.spliter + " pt='";
      this.spliter = "and";
      tmpDate1 = getAddDate(deltaDate, initDate);
      tmpPt = tmpPt + this.simpleFormatter.format(tmpDate1) + "-?";
      tmpPt = tmpPt + "' ";
    }
    return tmpPt;
  }

  public String evaluate(String expectDateString, String initDateString, String lastDateString)
  {
    String liveAuctions = new String();

    Date expectDate = new Date();
    Date lastDate = new Date();
    Date initDate = new Date();
    this.spliter = "";
    try
    {
      expectDate = this.simpleFormatter.parse(expectDateString);
      lastDate = this.simpleFormatter.parse(lastDateString);
      initDate = this.simpleFormatter.parse(initDateString);

      if ((getDeltaDate(lastDate, expectDate) < 0L) || (getDeltaDate(expectDate, initDate) < 0L)) {
        return "";
      }
      long deltaDay = getDeltaDate(lastDate, expectDate);

      Date tmpDate = new Date();
      for (; deltaDay > 0L; deltaDay -= 1L) {
        tmpDate = getAddDate(deltaDay, expectDate);
        liveAuctions = liveAuctions + getDeadPt(tmpDate, expectDate, initDate);
      }
      liveAuctions = liveAuctions + getLivePt(expectDate, initDate);
    } catch (ParseException e) {
      LOG.error(e);
    }
    return liveAuctions;
  }

  public static void main(String[] args) throws Exception {
    UDFGetLiveAuctions df = new UDFGetLiveAuctions();
    if (args.length != 3)
      return;
    System.out.println(df.evaluate(args[0], args[1], args[2]));
  }
}