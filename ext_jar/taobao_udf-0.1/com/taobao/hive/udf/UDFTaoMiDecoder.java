package com.taobao.hive.udf;

import com.alimama.loganalyzer.jobs.daily.taoke.TaomiDecoder;
import java.io.PrintStream;
import java.net.URLDecoder;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFTaoMiDecoder extends UDF
{
  Text result = new Text();

  public Text evaluate(String valueOfTaomi)
  {
    String decrypted = "";
    try
    {
      if (valueOfTaomi.indexOf("%") >= 0) {
        if (HASURLDecoder.isUtf8Url(valueOfTaomi)) {
          valueOfTaomi = URLDecoder.decode(valueOfTaomi, "UTF-8").trim();
        }
        else {
          valueOfTaomi = URLDecoder.decode(valueOfTaomi, "GBK").trim();
        }
      }

      TaomiDecoder taomiDecoder = new TaomiDecoder();
      decrypted = taomiDecoder.decrypt(valueOfTaomi);
      if (decrypted == null)
        decrypted = "";
    }
    catch (Exception e) {
      this.result.set("");
      return this.result;
    }

    this.result.set(decrypted);
    return this.result;
  }

  public static void main(String[] args)
  {
    UDFTaoMiDecoder test = new UDFTaoMiDecoder();
    System.out.println(test.evaluate(null));
  }
}