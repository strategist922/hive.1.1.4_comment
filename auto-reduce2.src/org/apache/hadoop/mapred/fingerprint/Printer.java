package org.apache.hadoop.mapred.fingerprint;

import java.io.PrintStream;

public class Printer
  implements FilterStrainer
{
  private String printerName;

  public Printer(String name)
  {
    this.printerName = name;
  }

  public String filter(String input)
  {
    System.out.println(this.printerName + ":" + input);
    return input;
  }
}