package org.apache.hadoop.mapred.fingerprint;

import java.io.PrintStream;
import java.util.regex.Pattern;
import org.apache.hadoop.mapred.AutoConstant;

public class RegexStrainer
  implements FilterStrainer
{
  private Pattern pattern;

  public RegexStrainer(Pattern p)
  {
    this.pattern = p;
  }

  public String filter(String input)
  {
    return RegexUtil.denoise(input, this.pattern);
  }

  public static void main(String[] args)
  {
    RegexStrainer ss = new RegexStrainer(AutoConstant.DATATIME_PATTERN);
    System.out.println(ss.filter("ds=20110401,pt=20110228000000"));
  }
}