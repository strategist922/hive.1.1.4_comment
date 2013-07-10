package rank2_UDF;

import java.io.PrintStream;

public class Version
{
  public static String getMajor()
  {
    return "1.0.0";
  }

  public static String getMinor() {
    return "b1";
  }

  public static String getValue() {
    return getMajor() + "_" + getMinor();
  }

  public static void main(String[] args) {
    System.out.println(getValue());
  }
}