package org.apache.hadoop.hive.ql.hooks;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.common.Function;
import org.apache.hadoop.common.NullException;
import org.apache.hadoop.common.SkyNetException;
import org.apache.hadoop.conf.Configuration;

public class DataUtil
{
  public static final String TABLE_NAME = "hive_optimize";
  public static final String COL_SKY_ID = "id";
  public static final String COL_ACTION_ID = "actionid";
  public static final String COL_TREE_ID = "treeid";
  public static final String COL_BIZDATE = "bizdate";
  public static final String COL_MD5SUM = "md5sum";
  public static final String COL_STAGE_ID = "stage_id";
  public static final String COL_JOB_ID = "job_id";
  public static final String COL_REDUCE_BYTES = "reduce_bytes";
  public static final String COL_COUNT = "count";
  public static final String EXCLUSIVE_TABLE = "hive_optimize_exclusive";
  public static final String COL_TYPE = "type";
  private static boolean isChecked = false;
  private static boolean isExclusive = false;
  private static String dbDriver;
  private static String dbUrl;
  private static String dbUser;
  private static String dbPassword;
  private Connection connection;
  private Configuration conf;
  private static int skyId;
  private static int actionId;
  private static int treeId;
  private static String bizdate;
  private static boolean initFlag = false;

  public DataUtil(Configuration conf) throws Exception {
    this.conf = conf;
    init(conf);
    connectToDB();
  }

  public DataUtil(String dbDriver, String dbUrl, String dbUser, String dbPassword) throws Exception
  {
    if (!initFlag) {
      dbDriver = dbDriver;
      dbUrl = dbUrl;
      dbUser = dbUser;
      dbPassword = dbPassword;
      Class.forName(dbDriver);
    }
    initFlag = true;
    connectToDB();
  }
  protected void connectToDB() throws Exception {
    this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
  }

  protected static void init(Configuration conf) throws Exception {
    if (initFlag) {
      return;
    }

    dbDriver = conf.get("javax.jdo.option.ConnectionDriverName", "");
    if ("".equals(dbDriver)) {
      throw new Exception("DB driver missing!");
    }

    dbUrl = conf.get("taobao.meta.ConnectionURL");
    if ("".equals(dbUrl)) {
      throw new Exception("DB url missing!");
    }

    dbUser = conf.get("javax.jdo.option.ConnectionUserName");
    if ("".equals(dbUser)) {
      throw new Exception("DB user missing!");
    }

    dbPassword = conf.get("taobao.meta.ConnectionPassword");
    if (dbPassword == null) {
      throw new Exception("DB password missing!");
    }

    skyId = conf.getInt("mapred.job.skynet_id", -1);
    if (skyId < 0) {
      throw new SkyNetException("Skynet id missing!");
    }

    bizdate = conf.get("mapred.job.skynet.bizdate", "");
    if ("".equals(bizdate)) {
      throw new SkyNetException("Skynet bizdate missing!");
    }

    actionId = conf.getInt("mapred.job.skynet.actionid", -1);
    if (actionId < 0) {
      throw new SkyNetException("Skynet action id missing!");
    }

    treeId = conf.getInt("mapred.job.skynet.jobid", -1);
    if (treeId < 0) {
      throw new SkyNetException("Skynet tree id missing!");
    }

    Class.forName(dbDriver);
    initFlag = true;
  }

  public Map<Object, Object> getAverage(int stageId, String md5, int timespan) throws NullException, ParseException
  {
    Date data_bizdate = Function.dateFormatFromStr(bizdate, "yyyyMMdd");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(data_bizdate);
    calendar.add(5, -(timespan + 1));
    Date begin = calendar.getTime();
    String begin_date = Function.dateFormat(begin, "yyyy-MM-dd");

    String[] columns = getColumns();
    int len = columns.length;
    StringBuffer sql_buffer = new StringBuffer();
    StringBuffer sql_count_buffer = new StringBuffer();
    StringBuffer sql_from = new StringBuffer();
    sql_count_buffer.append("select count(*) as count");
    sql_buffer.append("select ");
    for (int i = 0; i < len; i++) {
      if (i == 0) {
        sql_buffer.append("avg(").append(columns[i]).append(") as ").append(columns[i]);
      }
      else {
        sql_buffer.append(",avg(").append(columns[i]).append(") as ").append(columns[i]);
      }
    }
    sql_from.append(" from ").append("hive_optimize").append(" where ").append("bizdate").append(" <= '").append(Function.format(bizdate, "yyyyMMdd", "yyyy-MM-dd")).append("' and ").append("bizdate").append(" > '").append(begin_date).append("' and ").append("md5sum").append("='").append(md5).append("'").append(" and ").append("treeid").append("=1").append(" and ").append("stage_id").append("=").append(stageId).append(" and ").append("id").append("=").append(skyId);

    sql_buffer.append(sql_from);
    sql_count_buffer.append(sql_from);
    if (null == this.connection) {
      throw new NullException("database connection is null");
    }
    long count = 0L;
    Map counterInfos = new HashMap();
    Statement st_count = null;
    ResultSet rs_count = null;
    Statement st_result = null;
    ResultSet rs_result = null;
    try
    {
      st_count = this.connection.createStatement();
      rs_count = st_count.executeQuery(sql_count_buffer.toString());
      while (rs_count.next()) {
        count = rs_count.getInt("count");
      }
      if (count > 0L) {
        st_result = this.connection.createStatement();
        rs_result = st_result.executeQuery(sql_buffer.toString());
        try {
          if (rs_result.next())
          {
            counterInfos.put("combine_input_records", Long.valueOf(Double.valueOf(rs_result.getDouble("combine_input_records")).longValue()));

            counterInfos.put("combine_output_records", Long.valueOf(Double.valueOf(rs_result.getDouble("combine_output_records")).longValue()));

            counterInfos.put("data_local_map_tasks", Long.valueOf(Double.valueOf(rs_result.getDouble("data_local_map_tasks")).longValue()));

            counterInfos.put("hdfs_bytes_read", Long.valueOf(Double.valueOf(rs_result.getDouble("hdfs_bytes_read")).longValue()));

            counterInfos.put("hdfs_bytes_written", Long.valueOf(Double.valueOf(rs_result.getDouble("hdfs_bytes_written")).longValue()));

            counterInfos.put("launched_map_tasks", Long.valueOf(Double.valueOf(rs_result.getDouble("launched_map_tasks")).longValue()));

            counterInfos.put("launched_reduce_tasks", Long.valueOf(Double.valueOf(rs_result.getDouble("launched_reduce_tasks")).longValue()));

            counterInfos.put("local_bytes_read", Long.valueOf(Double.valueOf(rs_result.getDouble("local_bytes_read")).longValue()));

            counterInfos.put("local_bytes_written", Long.valueOf(Double.valueOf(rs_result.getDouble("local_bytes_written")).longValue()));

            counterInfos.put("map_input_bytes", Long.valueOf(Double.valueOf(rs_result.getDouble("map_input_bytes")).longValue()));

            counterInfos.put("map_input_records", Long.valueOf(Double.valueOf(rs_result.getDouble("map_input_records")).longValue()));

            counterInfos.put("map_output_bytes", Long.valueOf(Double.valueOf(rs_result.getDouble("map_output_bytes")).longValue()));

            counterInfos.put("map_output_records", Long.valueOf(Double.valueOf(rs_result.getDouble("map_output_records")).longValue()));

            counterInfos.put("rack_local_map_tasks", Long.valueOf(Double.valueOf(rs_result.getDouble("rack_local_map_tasks")).longValue()));

            counterInfos.put("reduce_input_groups", Long.valueOf(Double.valueOf(rs_result.getDouble("reduce_input_groups")).longValue()));

            counterInfos.put("reduce_input_records", Long.valueOf(Double.valueOf(rs_result.getDouble("reduce_input_records")).longValue()));

            counterInfos.put("reduce_output_records", Long.valueOf(Double.valueOf(rs_result.getDouble("reduce_output_records")).longValue()));
          }

        }
        catch (Exception e)
        {
          System.err.println("database access error");
          counterInfos = null;
        }
      } else {
        counterInfos.put("combine_input_records", Integer.valueOf(0));
        counterInfos.put("combine_output_records", Integer.valueOf(0));
        counterInfos.put("data_local_map_tasks", Integer.valueOf(0));
        counterInfos.put("hdfs_bytes_read", Integer.valueOf(0));
        counterInfos.put("hdfs_bytes_written", Integer.valueOf(0));
        counterInfos.put("launched_map_tasks", Integer.valueOf(0));
        counterInfos.put("launched_reduce_tasks", Integer.valueOf(0));
        counterInfos.put("local_bytes_read", Integer.valueOf(0));
        counterInfos.put("local_bytes_written", Integer.valueOf(0));
        counterInfos.put("map_input_bytes", Integer.valueOf(0));
        counterInfos.put("map_input_records", Integer.valueOf(0));
        counterInfos.put("map_output_bytes", Integer.valueOf(0));
        counterInfos.put("map_output_records", Integer.valueOf(0));
        counterInfos.put("rack_local_map_tasks", Integer.valueOf(0));
        counterInfos.put("reduce_input_groups", Integer.valueOf(0));
        counterInfos.put("reduce_input_records", Integer.valueOf(0));
        counterInfos.put("reduce_output_records", Integer.valueOf(0));
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        rs_count.close();
        st_count.close();
        if (rs_result != null) {
          rs_result.close();
          st_result.close();
        }
      } catch (SQLException e) {
        System.err.println("error while closing the statement or resultset");

        e.printStackTrace();
      }
    }
    return counterInfos;
  }

  public void setExclusive(int stage_id)
  {
    if (!this.conf.getBoolean("hive.job.hooks.autored.exclusive", false))
      return;
    if ((isChecked) && (isExclusive))
      this.conf.setBoolean("hive.job.hooks.autored.exclusive", false);
    try
    {
      Statement st = this.connection.createStatement();
      ResultSet rs = st.executeQuery(getExclusiveSql());
      isChecked = true;
      while (rs.next()) {
        int type = rs.getInt("type");

        switch (type) {
        case 0:
          isExclusive = true;
          break;
        case 1:
          if (!bizdate.equals(rs.getString("bizdate"))) break;
          isExclusive = true;
          break;
        case 2:
          if (stage_id != rs.getInt("stage_id")) break;
          isExclusive = true;
        }

      }

      rs.close();
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    this.conf.setBoolean("hive.job.hooks.autored.exclusive", isExclusive);
  }

  public String getExclusiveSql()
  {
    String condition_1 = new StringBuilder().append("id = ").append(skyId).toString();

    String sql = new StringBuilder().append("select type,date_format(bizdate,'%Y%m%d') as bizdate,stage_id from hive_optimize_exclusive where ").append(condition_1).append(" order by ").append("type").append(";").toString();

    return sql;
  }

  public String getExclusiveSql(int skyId, int type)
  {
    String condition_1 = new StringBuilder().append("id = ").append(skyId).append(" and ").append("type").append(" = ").append(type).toString();

    String sql = new StringBuilder().append("select * reduce_bytes from hive_optimize_exclusive where ").append(condition_1).append(";").toString();

    return sql;
  }

  private String[] getColumns() {
    String[] columns = new String[17];
    columns[0] = "hdfs_bytes_read";
    columns[1] = "hdfs_bytes_written";
    columns[2] = "local_bytes_read";
    columns[3] = "local_bytes_written";
    columns[4] = "launched_reduce_tasks";
    columns[5] = "rack_local_map_tasks";
    columns[6] = "launched_map_tasks";
    columns[7] = "data_local_map_tasks";
    columns[8] = "reduce_input_groups";
    columns[9] = "combine_output_records";
    columns[10] = "map_input_records";
    columns[11] = "reduce_output_records";
    columns[12] = "map_output_bytes";
    columns[13] = "map_input_bytes";
    columns[14] = "combine_input_records";
    columns[15] = "map_output_records";
    columns[16] = "reduce_input_records";
    return columns;
  }

  public boolean updateToDB(int stageId, String md5, String jobId, long reduceBytes, Map<Object, Object> info)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("insert into ").append("hive_optimize").append(" values (");
    sb.append(skyId).append(",");
    sb.append(actionId).append(",");
    sb.append(treeId).append(",'");
    sb.append(bizdate).append("','");
    sb.append(md5).append("',");
    sb.append(stageId).append(",'");
    sb.append(jobId).append("',");
    sb.append(reduceBytes).append(",now(),");
    sb.append(info.get("hdfs_bytes_read")).append(",");
    sb.append(info.get("hdfs_bytes_written")).append(",");
    sb.append(info.get("local_bytes_read")).append(",");
    sb.append(info.get("local_bytes_written")).append(",");
    sb.append(info.get("launched_reduce_tasks")).append(",");
    sb.append(info.get("rack_local_map_tasks")).append(",");
    sb.append(info.get("launched_map_tasks")).append(",");
    sb.append(info.get("data_local_map_tasks")).append(",");
    sb.append(info.get("reduce_input_groups")).append(",");
    sb.append(info.get("combine_output_records")).append(",");
    sb.append(info.get("map_input_records")).append(",");
    sb.append(info.get("reduce_output_records")).append(",");
    sb.append(info.get("map_output_bytes")).append(",");
    sb.append(info.get("map_input_bytes")).append(",");
    sb.append(info.get("combine_input_records")).append(",");
    sb.append(info.get("map_output_records")).append(",");
    sb.append(info.get("reduce_input_records"));
    sb.append(");");
    try {
      Statement st = this.connection.createStatement();
      st.executeUpdate(sb.toString());
      st.close();
      return true;
    } catch (SQLException e) {
      System.err.println(e.toString());
    }return false;
  }

  public void close()
  {
    try {
      if (this.connection != null)
        this.connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}