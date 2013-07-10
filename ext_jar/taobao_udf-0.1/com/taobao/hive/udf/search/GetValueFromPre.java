package com.taobao.hive.udf.search;

import com.taobao.hive.udf.SearchURLDecode;
import com.taobao.hive.udf.UDFGetValueFromRefer;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class GetValueFromPre extends UDF
{
  private UDFGetValueFromRefer getValue = new UDFGetValueFromRefer();
  private SearchURLDecode decoder = new SearchURLDecode();

  public Text evaluate(Text refer, Text index_String_1, Text index_String_2)
  {
    if ((refer == null) || (index_String_1 == null) || (index_String_2 == null) || (CommonData.SPLIT_CHAR == null) || (CommonData.REPLACED_CHAR == null) || (CommonData.REPLACE_CHAR == null))
    {
      return null;
    }

    Text value = this.getValue.evaluate(this.decoder.evaluate(this.getValue.evaluate(refer, CommonData.SPLIT_CHAR, index_String_1)), CommonData.SPLIT_CHAR, index_String_2);

    if (value == null) {
      return null;
    }

    return new Text(value.toString());
  }
}