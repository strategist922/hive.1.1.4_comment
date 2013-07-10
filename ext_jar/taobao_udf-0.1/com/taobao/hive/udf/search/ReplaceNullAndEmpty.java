package com.taobao.hive.udf.search;

import com.taobao.hive.udf.UDFGetValueFromRefer;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class ReplaceNullAndEmpty extends UDF
{
  private UDFGetValueFromRefer getValue = new UDFGetValueFromRefer();
  private GetValueFromPre getPreValue = new GetValueFromPre();
  private GetValueFromSplit getSplitValue = new GetValueFromSplit();

  public Text evaluate(Text refer, Text index_String, Text replace_String)
  {
    Text value = this.getValue.evaluate(refer, CommonData.SPLIT_CHAR, index_String);
    if ((value == null) || (value.toString().trim().equals(""))) {
      return replace_String;
    }

    return new Text(value.toString().trim());
  }

  public Text evaluate(Text refer, Text index_String_1, Text index_String_2, Text replace_String)
  {
    Text value = this.getPreValue.evaluate(refer, index_String_1, index_String_2);
    if ((value == null) || (value.toString().trim().equals(""))) {
      return replace_String;
    }

    return new Text(value.toString().trim());
  }

  public Text evaluate(Text refer, Text index_String, Text split, IntWritable index, Text replace_String)
  {
    Text value = this.getSplitValue.evaluate(refer, index_String, split, index);

    if ((value == null) || (value.toString().trim().equals(""))) {
      return replace_String;
    }

    return new Text(value.toString().trim());
  }
}