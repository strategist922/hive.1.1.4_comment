package com.taobao.hive.udf.search;

import com.taobao.hive.udf.SearchURLDecode;
import com.taobao.hive.udf.UDFGetValueFromRefer;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class GetNameFromUrl extends UDF
{
  private UDFGetValueFromRefer getValue = new UDFGetValueFromRefer();
  private SearchURLDecode decoder = new SearchURLDecode();

  public Text evaluate(Text url, Text index_String)
  {
    return getName(url, index_String);
  }

  public Text evaluate(Text url)
  {
    return getName(url, CommonData.QUERY_CHAR);
  }

  private Text getName(Text url, Text index_String) {
    if ((url == null) || (index_String == null) || (CommonData.SPLIT_CHAR == null) || (CommonData.REPLACED_CHAR == null) || (CommonData.REPLACE_CHAR == null))
    {
      return null;
    }

    Text value = this.getValue.evaluate(url, CommonData.SPLIT_CHAR, index_String);

    if (value == null) {
      return null;
    }

    if (value.toString().equals("")) {
      return new Text("");
    }

    String value_replaced = value.toString().replace(CommonData.REPLACED_CHAR.toString(), CommonData.REPLACE_CHAR.toString());

    return this.decoder.evaluate(new Text(value_replaced));
  }
}