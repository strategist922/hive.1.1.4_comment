package rank2_UDF;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;

public class SPU extends UDF
{
  public static final String PAIR_SPLIT = ";";
  public static final String KEY_VALUE_SPLIT = ":";
  public static final Pattern KEY_VALUE_PATTERN = Pattern.compile("(\\w+):*(\\w+)*;??");
  public static final byte AND_MODE = 0;
  public static final byte OR_MODE = 1;

  public Boolean evaluate(String subSet, String universalSet)
  {
    try
    {
      if (subSet == null) return Boolean.valueOf(true);
      if (universalSet == null) return Boolean.valueOf(false);

      subSet = subSet.trim();
      universalSet = universalSet.trim();

      if ((subSet.equals("")) || (subSet.equalsIgnoreCase("NULL"))) {
        return Boolean.valueOf(true);
      }

      if ((universalSet.equals("")) || (universalSet.equalsIgnoreCase("NULL"))) {
        return Boolean.valueOf(false);
      }

      int logicMode = subSet.startsWith("(|)") ? 1 : 0;

      Set subKeys = extractKeys(subSet);
      Set univeralKeys = extractKeys(universalSet);

      if (logicMode == 1)
      {
        for (String subKey : subKeys) {
          if (univeralKeys.contains(subKey)) {
            return Boolean.valueOf(true);
          }
        }
        return Boolean.valueOf(false);
      }

      if (univeralKeys.containsAll(subKeys)) {
        return Boolean.valueOf(true);
      }
      return Boolean.valueOf(false);
    }
    catch (Throwable t)
    {
      System.err.println(t);
    }return Boolean.valueOf(false);
  }

  public Set<String> extractKeys(String keyValuePair)
  {
    Matcher m = KEY_VALUE_PATTERN.matcher(keyValuePair);
    Set resultSet = new HashSet();
    while (m.find()) {
      resultSet.add(m.group(0).replaceAll(" ", ""));
    }
    return resultSet;
  }
}