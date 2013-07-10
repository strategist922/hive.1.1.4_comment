package com.taobao.hive.udf;

import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFP4Pkeywords extends UDF
{
  Text result = new Text();

  public Text evaluate(String url)
  {
    try
    {
      UDFGetValueFromUrl test = new UDFGetValueFromUrl();
      String ali_refid = test.evaluate(url, "ali_refid").toString();

      String[] strList = ali_refid.split(":");
      if (strList.length < 4) {
        this.result.set("");
        return this.result;
      }
      String returnResult = strList[3];

      this.result.set(returnResult);
      return this.result;
    } catch (Exception e) {
      this.result.set("err");
    }return this.result;
  }

  public static void main(String[] args)
  {
    UDFP4Pkeywords test = new UDFP4Pkeywords();
    System.out.println(test.evaluate("http://item.taobao.com/auction/item_detail.jhtml?item_id=718c30acbc47125dffc27229a4426fa1&ali_refid=a3_419375_1007:1102543830:7:525ON028r154GmE866033643w658O7:83d5eab4b155acb8bf56738a78d2bf11&ali_trackid=1_83d5eab4b155acb8bf56738a78d2bf11"));
    System.out.println(test.evaluate(null));
  }
}