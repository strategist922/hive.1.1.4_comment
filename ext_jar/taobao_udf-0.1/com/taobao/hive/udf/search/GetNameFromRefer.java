package com.taobao.hive.udf.search;

import com.taobao.hive.udf.SearchURLDecode;
import com.taobao.hive.udf.UDFGetValueFromRefer;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class GetNameFromRefer extends UDF
{
  private UDFGetValueFromRefer getValue = new UDFGetValueFromRefer();
  private SearchURLDecode decoder = new SearchURLDecode();

  public Text evaluate(Text refer, Text index_String_1, Text index_String_2)
  {
    return getName(refer, index_String_1, index_String_2);
  }

  public Text evaluate(Text refer, Text index_String_1)
  {
    return getName(refer, index_String_1, CommonData.QUERY_CHAR);
  }

  private Text getName(Text refer, Text index_String_1, Text index_String_2)
  {
    if ((refer == null) || (index_String_1 == null) || (index_String_2 == null) || (CommonData.SPLIT_CHAR == null) || (CommonData.REPLACED_CHAR == null) || (CommonData.REPLACE_CHAR == null))
    {
      return null;
    }

    Text value = this.getValue.evaluate(this.decoder.evaluate(this.getValue.evaluate(refer, CommonData.SPLIT_CHAR, index_String_1)), CommonData.SPLIT_CHAR, index_String_2);

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