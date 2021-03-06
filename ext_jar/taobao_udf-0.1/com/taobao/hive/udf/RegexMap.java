package com.taobao.hive.udf;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMap<V> extends HashMap<Pattern, V>
{
  private static final long serialVersionUID = 4761214570434685507L;
  protected HashMap<Pattern, V> map = null;

  public RegexMap()
  {
    this.map = new HashMap();
  }

  public RegexMap(int capacity)
  {
    this.map = new HashMap(capacity);
  }

  public RegexMap(int capacity, float factor)
  {
    this.map = new HashMap(capacity, factor);
  }

  public RegexMap(Map<? extends Pattern, ? extends V> map)
  {
    this.map = new HashMap(map);
  }

  public V get(Object key)
  {
    if (key != null) {
      String tmp = "";
      for (Map.Entry entry : this.map.entrySet()) {
        if (((Pattern)entry.getKey()).matcher(key.toString()).find()) {
          String nowValue = entry.getValue().toString();
          if (nowValue.length() > tmp.length()) {
            tmp = nowValue;
          }
        }
      }
      if (tmp.equals("")) {
        return null;
      }
      return tmp;
    }
    return null;
  }

  public int size() {
    return this.map.size();
  }

  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  public boolean containsKey(Object key) {
    return this.map.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return this.map.containsValue(value);
  }

  public V put(Pattern key, V value) {
    return this.map.put(key, value);
  }

  public void putAll(Map<? extends Pattern, ? extends V> in) {
    this.map.putAll(in);
  }

  public V remove(Pattern key) {
    return this.map.remove(key);
  }

  public void clear() {
    this.map.clear();
  }

  public Set<Map.Entry<Pattern, V>> entrySet() {
    return this.map.entrySet();
  }

  public Set<Pattern> keySet() {
    return this.map.keySet();
  }

  public Collection<V> values() {
    return this.map.values();
  }
}