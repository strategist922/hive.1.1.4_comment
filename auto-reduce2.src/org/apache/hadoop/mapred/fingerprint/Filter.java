package org.apache.hadoop.mapred.fingerprint;

import java.util.Vector;

public class Filter
{
  private Vector<FilterStrainer> vStrainers = new Vector();

  public synchronized void addFilterStrainer(FilterStrainer fs) {
    this.vStrainers.add(fs);
  }

  public synchronized String getResult(String input) {
    if ((null == this.vStrainers) || (this.vStrainers.size() == 0)) {
      return input;
    }
    String reslut = input;
    for (FilterStrainer fs : this.vStrainers) {
      reslut = fs.filter(reslut);
      if ((reslut == null) || ("".equals(reslut))) {
        return "";
      }
    }
    return reslut;
  }
}