package com.taobao.hive.udf;

import com.taobao.hive.util.UDFStringUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.io.Text;

public class UDFGetValueFromRefer2
{
  private Pattern pattern = null;

  private String lastIndexString = "";

  private Text result = new Text();

  public Text evaluate(Text refer, Text split_char, Text index_String)
  {
    if ((refer == null) || (split_char == null) || (index_String == null) || ("".equals(index_String.toString().trim()))) return null;
    String indexStr = index_String.toString();
    String referStr = refer.toString();
    if (!indexStr.equals(this.lastIndexString)) {
      this.pattern = Pattern.compile("(" + split_char + "|\\?)" + index_String + "([^" + split_char + "]*)");
    }
    this.lastIndexString = indexStr;
    Matcher m = this.pattern.matcher(referStr);
    if (m.find()) {
      return new Text(m.group(2));
    }
    return new Text("");
  }

  public Text evaluate2(Text refer, Text split_char, Text index_String)
  {
    if ((refer == null) || (split_char == null) || (index_String == null) || ("".equals(index_String.toString().trim()))) return null;
    String indexStr = index_String.toString();
    String referStr = refer.toString();
    String res = UDFStringUtil.getURLValue(referStr, indexStr, split_char.toString());
    res = res == null ? "" : res;
    this.result.set(res);
    return this.result;
  }

  public Text evaluate(Text refer, Text split_char, Text index_String, String split, String index) {
    Text result = evaluate(refer, split_char, index_String);
    if (result == null) return null;
    String returnValue = "";
    String value = result.toString();
    if (value.indexOf(split) >= 0) {
      String[] strList = value.split(split);
      if (strList.length > Integer.parseInt(index)) {
        returnValue = strList[Integer.parseInt(index)];
      }
    }
    result.set(returnValue);
    return result;
  }
}