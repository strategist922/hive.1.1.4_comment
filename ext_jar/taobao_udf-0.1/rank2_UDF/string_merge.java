package rank2_UDF;

import java.util.Iterator;
import java.util.TreeSet;
import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.io.Text;

public class string_merge extends UDAF
{
  public static class stringMergeEvaluator
    implements UDAFEvaluator
  {
    private String strResult;
    private boolean mEmpty;

    public stringMergeEvaluator()
    {
      init();
    }

    public void init() {
      this.mEmpty = true;
      this.strResult = "";
    }

    public boolean iterate(DoubleWritable o)
    {
      if (o != null) {
        if (this.strResult.equals(""))
          this.strResult = o.toString();
        else {
          this.strResult = (this.strResult + ";" + o.toString());
        }
        this.mEmpty = false;
      }
      return true;
    }

    public String terminatePartial() {
      return this.mEmpty ? null : this.strResult;
    }

    public boolean merge(Text o) {
      if (o != null) {
        if (this.strResult.equals(""))
          this.strResult = o.toString();
        else {
          this.strResult = (this.strResult + ";" + o.toString());
        }
        this.mEmpty = false;
      }
      return true;
    }

    public String terminate()
    {
      if (this.mEmpty) {
        return null;
      }
      String strLastResult = "";
      String[] strDAlist = this.strResult.split(";");
      TreeSet hsTemp = new TreeSet();
      for (int i = 0; i < strDAlist.length; i++) {
        hsTemp.add(Double.valueOf(strDAlist[i]));
      }

      Iterator it = hsTemp.iterator();

      while (it.hasNext()) {
        if (strLastResult.equals("")) {
          strLastResult = ((Double)it.next()).toString(); continue;
        }
        strLastResult = strLastResult + ";" + ((Double)it.next()).toString();
      }

      return strLastResult;
    }
  }
}