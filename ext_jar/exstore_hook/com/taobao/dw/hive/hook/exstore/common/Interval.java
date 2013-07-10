package com.taobao.dw.hive.hook.exstore.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Interval
{
  public static final String MIN = "20030101";
  public static final String MAX;
  public static final String _31DAYS_AGO;
  private String tab;
  private String start;
  private String end;

  public String getTab()
  {
    return this.tab;
  }

  public void setTab(String tab) {
    this.tab = tab;
  }

  public String toString() {
    return "Interval [start=" + this.start + ", end=" + this.end + "]";
  }

  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.end == null ? 0 : this.end.hashCode());
    result = 31 * result + (this.start == null ? 0 : this.start.hashCode());
    return result;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Interval other = (Interval)obj;
    if (this.end == null) {
      if (other.end != null)
        return false;
    } else if (!this.end.equals(other.end))
      return false;
    if (this.start == null) {
      if (other.start != null)
        return false;
    } else if (!this.start.equals(other.start))
      return false;
    return true;
  }

  public String getStart() {
    return this.start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return this.end;
  }

  public void setEnd(String end) {
    this.end = end;
  }
/**
 * 合并区间
 * @param a
 * @param b
 * @return
 */
  public static Interval and(Interval a, Interval b) {
    if ((a.start.compareTo(b.end) > 0) || (b.start.compareTo(a.end) > 0))//无交集
      return null;
    String _start = a.start.compareTo(b.start) > 0 ? a.start : b.start;
    String _end = a.end.compareTo(b.end) > 0 ? b.end : a.end;
    return new Interval(_start, _end);
  }

  public Interval(String start, String end) {
    this.start = start;
    this.end = end;
  }

  public Interval() {
  }

  public Interval(Interval interval) {
    this.tab = interval.tab;
    this.start = interval.start;
    this.end = interval.end;
  }

  static
  {
    int i = 86400000;
    MAX = Util.yyyyMMdd.format(new Date(System.currentTimeMillis() - 86400000L));
    Util.cal.setTimeInMillis(System.currentTimeMillis());
    Util.cal.add(5, -31);
    _31DAYS_AGO = Util.yyyyMMdd.format(Util.cal.getTime());
  }
}