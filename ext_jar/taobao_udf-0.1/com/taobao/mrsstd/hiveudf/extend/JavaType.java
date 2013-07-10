package com.taobao.mrsstd.hiveudf.extend;

import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JavaType
{
  public static boolean addTypeValue(Type type, List<Integer> listTypes)
  {
    boolean flagFound = false;

    if ("class java.lang.String".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(9));
      flagFound = true;
    } else if ("class [C".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(8));
      flagFound = true;
    } else if ("class java.util.Locale".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(7));
      flagFound = true;
    } else if ("double".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(6));
      flagFound = true;
    } else if ("float".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(5));
      flagFound = true;
    } else if ("long".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(4));
      flagFound = true;
    } else if ("int".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(3));
      flagFound = true;
    } else if ("char".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(2));
      flagFound = true;
    } else if ("boolean".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(1));
      flagFound = true;
    } else if ("void".equalsIgnoreCase(type.toString())) {
      listTypes.add(Integer.valueOf(0));
      flagFound = true;
    }

    return flagFound;
  }

  public static boolean putTypeValue(Type type, Map<String, Integer> map, String key) {
    boolean flagFound = false;

    if ("class java.lang.String".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(9));
      flagFound = true;
    } else if ("class [C".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(8));
      flagFound = true;
    } else if ("class java.util.Locale".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(7));
      flagFound = true;
    } else if ("double".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(6));
      flagFound = true;
    } else if ("float".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(5));
      flagFound = true;
    } else if ("long".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(4));
      flagFound = true;
    } else if ("int".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(3));
      flagFound = true;
    } else if ("char".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(2));
      flagFound = true;
    } else if ("boolean".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(1));
      flagFound = true;
    } else if ("void".equalsIgnoreCase(type.toString())) {
      map.put(key, Integer.valueOf(0));
      flagFound = true;
    }

    return flagFound;
  }

  public static Object[] generateObjParams(List<Integer> listTypes, int index, String[] values)
  {
    List listParams = new ArrayList();
    for (Integer type : listTypes) {
      switch (type.intValue()) {
      case 9:
        listParams.add(values[(index++)]);
        break;
      case 8:
        listParams.add(values[(index++)].toCharArray());
        break;
      case 7:
        listParams.add(new Locale(values[(index++)]));
        break;
      case 6:
        listParams.add(Double.valueOf(values[(index++)]));
        break;
      case 5:
        listParams.add(Float.valueOf(values[(index++)]));
        break;
      case 4:
        listParams.add(Long.valueOf(values[(index++)]));
        break;
      case 3:
        listParams.add(Integer.valueOf(values[(index++)]));
        break;
      case 2:
        listParams.add(Character.valueOf(values[(index++)].charAt(0)));
        break;
      case 1:
        listParams.add(Boolean.valueOf(Boolean.parseBoolean(values[(index++)])));
        break;
      case 0:
        break;
      default:
        System.out.println("not supported param type value: " + type);
      }

    }

    Object[] objParams = new Object[listParams.size()];
    index = 0;
    for (Iterator i$ = listParams.iterator(); i$.hasNext(); ) { Object param = i$.next();
      objParams[(index++)] = param;
    }
    return objParams;
  }

  public static void main(String[] args)
  {
  }
}