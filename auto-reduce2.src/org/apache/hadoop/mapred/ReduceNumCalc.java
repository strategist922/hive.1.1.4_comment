package org.apache.hadoop.mapred;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReduceNumCalc
  implements RedNumCalculator
{
  private int datasize = 0;

  public ReduceNumCalc(int datasize)
  {
    this.datasize = datasize;
  }
  public ReduceNumCalc() {
  }

  public int getReduceNumber(String datasize) {
    this.datasize = Integer.valueOf(datasize).intValue();
    return getReduceNum();
  }

  public int getReduceNum()
  {
    if (this.datasize == 0) {
      return 1;
    }

    int rednum = 0;
    rednum += calc(0, 30720, 128);
    rednum += calc(30720, 102400, 512);
    rednum += calc(102400, 512000, 1024);
    rednum += calc(512000, 2147483647, 2048);
    return rednum;
  }

  private int calc(int lower, int upper, int div) {
    if (this.datasize <= lower)
      return 0;
    if ((this.datasize > lower) && (this.datasize <= upper)) {
      if ((this.datasize - lower) % div == 0) {
        return (this.datasize - lower) / div;
      }
      return (this.datasize - lower) / div + 1;
    }

    return (upper - lower) / div;
  }

  public int jvmFormat(String jvmSize)
  {
    if (jvmSize.length() > 9) {
      jvmSize = jvmSize.split(" ")[0];
    }
    Pattern pt = Pattern.compile("[0-9]+");
    Matcher m = pt.matcher(jvmSize);
    m.find();
    return (int)(Integer.valueOf(m.group()).intValue() * 0.4D);
  }

  public static void main(String[] args) {
    ReduceNumCalc c = new ReduceNumCalc(0);
    System.out.println(c.getReduceNum());
    c = new ReduceNumCalc(10240);
    System.out.println(c.getReduceNum());

    c = new ReduceNumCalc(30720);
    System.out.println(c.getReduceNum());

    c = new ReduceNumCalc(51200);
    System.out.println(c.getReduceNum());

    c = new ReduceNumCalc(102400);
    System.out.println(c.getReduceNum());

    c = new ReduceNumCalc(512000);
    System.out.println(c.getReduceNum());

    c = new ReduceNumCalc(50176);
    System.out.println(c.getReduceNum());

    c = new ReduceNumCalc(2252800);
    System.out.println(c.getReduceNum());
  }
}