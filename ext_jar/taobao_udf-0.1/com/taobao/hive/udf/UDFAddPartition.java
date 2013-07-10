package com.taobao.hive.udf;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDF;

public class UDFAddPartition extends UDF
{
  private static Log LOG = LogFactory.getLog(UDFAddPartition.class.getName());
  private SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyyMMdd");
  private String table = "";
  private String deadPath = "";
  private String livePath = "";
  private String addSql = "ALTER TABLE %s ADD PARTITION (pt='%s') LOCATION '%s';";

  private String addDeadPartitions(long deltaDate, Date tmpDate, Date lastDate)
  {
    String lastDateString = this.simpleFormatter.format(lastDate);
    String tmpDateString = this.simpleFormatter.format(tmpDate);
    String deadPartitions = String.format(this.addSql, new Object[] { this.table, tmpDateString + "-" + lastDateString, this.deadPath + lastDateString + "/" + tmpDateString + "-" + lastDateString });

    return deadPartitions;
  }

  private String addLivePartitions(Date lastDate) {
    String deadPartitions = new String();
    String lastDateString = this.simpleFormatter.format(lastDate);
    deadPartitions = String.format(this.addSql, new Object[] { this.table, lastDateString + "-?", this.livePath + lastDateString + "-?" });

    return deadPartitions;
  }

  public String evaluate(String table, String deadPath, String livePath, String initDateString, String lastDateString) {
    this.table = table;
    this.deadPath = deadPath;
    this.livePath = livePath;

    String addPartitionString = new String();
    Date lastDate = new Date();
    Date initDate = new Date();
    Date tmpDate = new Date();
    UDFGetLiveAuctions df = new UDFGetLiveAuctions();
    try {
      lastDate = this.simpleFormatter.parse(lastDateString);
      initDate = this.simpleFormatter.parse(initDateString);
      long deltaDate = df.getDeltaDate(lastDate, initDate);

      if (deltaDate < 0L) {
        return "";
      }
      for (; deltaDate > 0L; deltaDate -= 1L) {
        tmpDate = df.getAddDate(deltaDate - 1L, initDate);
        addPartitionString = addPartitionString + addDeadPartitions(deltaDate, tmpDate, lastDate);
      }
      addPartitionString = addPartitionString + addLivePartitions(lastDate);
    } catch (ParseException e) {
      LOG.error(e);
    }
    return addPartitionString;
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 5)
      return;
    UDFAddPartition df = new UDFAddPartition();
    System.out.println(df.evaluate(args[0], args[1], args[2], args[3], args[4]));
  }
}