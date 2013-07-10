package com.taobao.dw.hive.hook.exstore.common;

import com.taobao.dw.hive.hook.exstore.node.Column;
import com.taobao.dw.hive.hook.exstore.node.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.ql.parse.HiveParser;
import org.apache.hadoop.hive.ql.parse.HiveSemanticAnalyzerHookContext;

public class ExStoreContext
{
  private static final String EXST_HOOK_PASSWORD = "taobao.meta.ConnectionPassword";
  private static final String EXST_HOOK_URL = "taobao.meta.ConnectionURL";
  private static final String EXST_HOOK_USER_NAME = "taobao.meta.ConnectionUserName";
  private static Map<String, String[]> exTableMap;
  public static HiveSemanticAnalyzerHookContext hiveContext;
  static final Class<HiveParser> hiveParserClass = HiveParser.class;
  private static String pswd;
  public static final int DOT = getInt("DOT");
  public static final int EQUAL = getInt("EQUAL");
  public static final int GREATERTHAN = getInt("GREATERTHAN");
  public static final int GREATERTHANOREQUALTO = getInt("GREATERTHANOREQUALTO");
  public static final int KW_AND = getInt("KW_AND");
  public static final int KW_JOIN = getInt("KW_JOIN");
  public static final int KW_MAPJOIN = getInt("KW_MAPJOIN");
  public static final int KW_OR = getInt("KW_OR");
  public static final int KW_UNIQUEJOIN = getInt("KW_UNIQUEJOIN");
  public static final int LESSTHAN = getInt("LESSTHAN");
  public static final int LESSTHANOREQUALTO = getInt("LESSTHANOREQUALTO");
  public static final int NOTEQUAL = getInt("NOTEQUAL");
  public static final int TOK_DESTINATION = getInt("TOK_DESTINATION");
  public static final int TOK_FROM = getInt("TOK_FROM");
  public static final int TOK_FULLOUTERJOIN = getInt("TOK_FULLOUTERJOIN");
  public static final int TOK_GROUPBY = getInt("TOK_GROUPBY");
  public static final int TOK_ORDERBY = getInt("TOK_ORDERBY");
  public static final int TOK_SORTBY = getInt("TOK_SORTBY");
  public static final int TOK_JOIN = getInt("TOK_JOIN");
  public static final int TOK_LEFTOUTERJOIN = getInt("TOK_LEFTOUTERJOIN");
  public static final int TOK_LEFTSEMIJOIN = getInt("TOK_LEFTSEMIJOIN");
  public static final int TOK_MAPJOIN = getInt("TOK_MAPJOIN");
  public static final int TOK_QUERY = getInt("TOK_QUERY");
  public static final int TOK_RIGHTOUTERJOIN = getInt("TOK_RIGHTOUTERJOIN");
  public static final int TOK_SELECT = getInt("TOK_SELECT");
  public static final int TOK_SELEXPR = getInt("TOK_SELEXPR");
  public static final int TOK_SUBQUERY = getInt("TOK_SUBQUERY");
  public static final int TOK_TABLE_OR_COL = getInt("TOK_TABLE_OR_COL");
  public static final int TOK_TABREF = getInt("TOK_TABREF");
  public static final int TOK_UNIQUEJOIN = getInt("TOK_UNIQUEJOIN");
  public static final int TOK_WHERE = getInt("TOK_WHERE");
  private static String url;
  private static String usrn;
  private boolean change = false;
  private List<Column> columns = new ArrayList();

  private boolean debug = false;
  private Map<String, Table> extables = new HashMap();
  private Map<String, Table> tables = new HashMap();
  private List<Column> whereColumns = new ArrayList();

  public static Map<String, String[]> getExTableMap()
  {
    return exTableMap;
  }

  public static HiveSemanticAnalyzerHookContext getHiveContext() {
    return hiveContext;
  }

  static int getInt(String filedname) {
    try {
      return hiveParserClass.getField(filedname).getInt(hiveParserClass);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public static void setExTableMap(Map<String, String[]> exTable)
  {
    exTableMap = exTable;
  }

  public static void setHiveContext(HiveSemanticAnalyzerHookContext hiveContext) {
    hiveContext = hiveContext;
  }

  public ExStoreContext()
  {
  }

  public ExStoreContext(HiveSemanticAnalyzerHookContext hiveContext)
    throws ExStoreException
  {
    hiveContext = hiveContext;
    if (null == exTableMap)
      initExDB(hiveContext);
  }

  public List<Column> getColumns()
  {
    return this.columns;
  }

  public Map<String, Table> getExtables() {
    return this.extables;
  }

  public Table getTable(String name) {
    return (Table)this.tables.get(name);
  }

  public Map<String, Table> getTables() {
    return this.tables;
  }

  public List<Column> getWhereColumns() {
    return this.whereColumns;
  }

  private void initExDB(HiveSemanticAnalyzerHookContext context) throws ExStoreException {
    Configuration conf = context.getConf();
    if (StringUtils.isEmpty(url)) {
      url = conf.get("taobao.meta.ConnectionURL");
      pswd = conf.get("taobao.meta.ConnectionPassword", "hive");
      usrn = conf.get("taobao.meta.ConnectionUserName", "hive");
      Connection conn = null;
      exTableMap = new HashMap();
      try {
        conn = DriverManager.getConnection(url, usrn, pswd);
        Statement stmt = conn.createStatement();
        stmt.execute("select * from exstored_table");
        ResultSet resultSet = stmt.getResultSet();
        while (resultSet.next()) {
          String[] values = { resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5) };

          exTableMap.put(resultSet.getString(2), values);
        }
        resultSet.close();
        stmt.close();
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new ExStoreException(e, -3);
      } finally {
        if (conn != null)
          try {
            conn.close();
          } catch (SQLException e1) {
            e1.printStackTrace();
          }
      }
    }
  }

  public boolean isChange()
  {
    return this.change;
  }

  public boolean isDebug() {
    return this.debug;
  }

  public void putColumn(String name, Column column) {
    this.columns.add(column);
  }

  public void putTable(String name, Table table) {
    this.tables.put(name, table);
  }

  public void putWhereColumn(String name, Column column) {
    this.whereColumns.add(column);
  }

  public void setChange(boolean change) {
    this.change = change;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public void setExtables(Map<String, Table> extables) {
    this.extables = extables;
  }

  static
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}