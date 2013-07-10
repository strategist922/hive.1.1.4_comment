package com.taobao.hive.udf.search;

import com.taobao.hive.udf.UDFGetValueFromRefer;
import java.io.PrintStream;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class GetValueFromSplit extends UDF
{
  private UDFGetValueFromRefer getValue = new UDFGetValueFromRefer();

  public Text evaluate(Text refer, Text index_String, Text split, IntWritable index)
  {
    if ((refer == null) || (index_String == null) || (split == null) || (index == null) || (CommonData.SPLIT_CHAR == null))
    {
      return null;
    }

    if (index.get() < 0) {
      return null;
    }

    String returnValue = "";

    Text value = this.getValue.evaluate(refer, CommonData.SPLIT_CHAR, new Text(index_String));

    if (value == null) {
      return null;
    }

    String valueToString = value.toString().trim();

    String[] strList = valueToString.split(split.toString());

    if (strList.length > index.get())
      returnValue = strList[index.get()];
    else {
      return null;
    }

    return new Text(returnValue);
  }

  public static void main(String[] args)
  {
    GetValueFromSplit test = new GetValueFromSplit();

    System.out.println(test.evaluate(new Text("http://8.etao.com/thread_detail.htm?bar_id=90077&thread_id=1.2.31&author_id=0&page=3"), new Text("thread_id="), new Text("--"), new IntWritable(0)));
  }
}