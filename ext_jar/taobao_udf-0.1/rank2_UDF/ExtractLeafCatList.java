package rank2_UDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class ExtractLeafCatList extends UDF
{
  public String evaluate(String catID)
  {
    String result = "";
    catID = catID.trim();
    if ((!catID.equals("")) && (!catID.startsWith("TR")))
    {
      if (catID.indexOf("_") >= 0)
      {
        result = catID.substring(0, catID.indexOf("_")).trim();
      }
    }
    return result;
  }
}