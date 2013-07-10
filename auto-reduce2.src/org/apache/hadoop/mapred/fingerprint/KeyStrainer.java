package org.apache.hadoop.mapred.fingerprint;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class KeyStrainer
  implements FilterStrainer
{
  private Map<String, Integer> keyMap = new HashMap();
  private int level = 1;

  public KeyStrainer() {
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public KeyStrainer(String key) {
    addKey(key);
  }

  public void addKey(String key) {
    this.keyMap.put(key, Integer.valueOf(1));
  }

  public void addKey(String key, int level) {
    this.keyMap.put(key, Integer.valueOf(level));
  }

  public String filter(String input)
  {
    if ((input == null) || ("".equals(input))) {
      return "";
    }
    String[] subs = input.split("\001");
    if ((this.keyMap.containsKey(subs[0])) && (((Integer)this.keyMap.get(subs[0])).intValue() <= this.level)) {
      return input;
    }
    return "";
  }

  public static void main(String[] args)
  {
    KeyStrainer ss = new KeyStrainer("mapred.map.tasks");
    System.out.println(ss.filter("mapred.map.tasks\0011056"));
    System.out.println(ss.filter("mapred.map\0011056"));
  }
}