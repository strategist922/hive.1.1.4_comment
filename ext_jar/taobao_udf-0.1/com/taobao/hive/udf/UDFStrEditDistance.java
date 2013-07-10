package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;

public class UDFStrEditDistance extends UDF
{
  private char[] charArrayX = null;

  private char[] charArrayY = null;

  private IntWritable result = new IntWritable(-1);

  public IntWritable evaluate(String src, String dest)
  {
    if ((src == null) || (dest == null)) {
      this.result.set(-1);
      return this.result;
    }
    src = src.length() > 50 ? src.substring(0, 50) : src;
    dest = dest.length() > 50 ? dest.substring(0, 50) : dest;
    this.charArrayX = src.toCharArray();
    this.charArrayY = dest.toCharArray();
    this.result.set(editDistance(this.charArrayX.length - 1, this.charArrayY.length - 1));
    return this.result;
  }

  private int editDistance(int i, int j)
  {
    if ((i == 0) && (j == 0)) {
      return isModify(i, j);
    }
    if ((i == 0) || (j == 0)) {
      if (j > 0) {
        if (isModify(i, j) == 0) return j;
        return editDistance(i, j - 1) + 1;
      }

      if (isModify(i, j) == 0) return i;
      return editDistance(i - 1, j) + 1;
    }

    int ccc = minDistance(editDistance(i - 1, j) + 1, editDistance(i, j - 1) + 1, editDistance(i - 1, j - 1) + isModify(i, j));
    return ccc;
  }

  private int minDistance(int disa, int disb, int disc)
  {
    int dismin = 2147483647;
    if (dismin > disa) dismin = disa;
    if (dismin > disb) dismin = disb;
    if (dismin > disc) dismin = disc;
    return dismin;
  }

  private int isModify(int i, int j)
  {
    if (this.charArrayX[i] == this.charArrayY[j])
      return 0;
    return 1;
  }

  public static void main(String[] args)
  {
    UDFStrEditDistance udf = new UDFStrEditDistance();
    System.out.println("编辑距离是：" + udf.evaluate("那么", "这么"));
  }
}