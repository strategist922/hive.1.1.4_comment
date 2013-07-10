package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFBase32Decoder extends UDF
{
  Text result = new Text();

  public Text evaluate(String value)
  {
    Base32 test = new Base32();
    String returnResult = Base32.decodeString(value);

    this.result.set(returnResult);
    return this.result;
  }

  public static void main(String[] args)
  {
    UDFBase32Decoder test = new UDFBase32Decoder();
    System.out.println(test.evaluate("g,xtg6r46pupd6du5pz3plvw6d64qnbr6juk35w"));
  }
}