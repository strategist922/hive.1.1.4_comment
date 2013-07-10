package org.apache.hadoop.hive.ql.hooks;

import java.io.PrintStream;

public class Query
{
  private String orgQuery;
  private String denoisedQuery;

  public Query(String query)
  {
    this.orgQuery = query;
  }

  public String getMd5sum() {
    this.denoisedQuery = Md5Util.denoiseDataTime(this.orgQuery);
    return Md5Util.getMd5sum(this.denoisedQuery.getBytes());
  }

  public String getQuery() {
    return this.orgQuery;
  }

  public void setQuery(String query) {
    this.orgQuery = query;
  }

  public String getDenoisedQuery() {
    return this.denoisedQuery;
  }

  public static void main(String[] args)
  {
    String query = "select '2011-03-18' as abc,\n * from dim_category where\n ds=20110228\n and pt = 20100301000000\n";
    Query q = new Query(query);
    System.out.println("md5:" + q.getMd5sum());
    System.out.println("denoisedQuery:" + q.getDenoisedQuery());

    query = "select '2010-12-31' as abc,\n * from dim_category where\n ds=20101231\n and pt = 20101231000000\n";
    q.setQuery(query);

    System.out.println("md5:" + q.getMd5sum());
    System.out.println("denoisedQuery:" + q.getDenoisedQuery());
  }
}