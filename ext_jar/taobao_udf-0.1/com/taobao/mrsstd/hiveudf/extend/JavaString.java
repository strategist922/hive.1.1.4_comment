package com.taobao.mrsstd.hiveudf.extend;

import com.taobao.mrsstd.hiveudf.util.UDFUtil;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JavaString extends Parent
{
  private static Map<String, List<Integer>> mapTypes = new HashMap();
  private static Map<String, Method> mapMethods = new HashMap();
  private static Map<String, Integer> mapReturns = new HashMap();

  public String evaluate(String function, String[] values)
  {
    try
    {
      String functionkey = function + "_" + (values.length - 1);
      List listTypes = (List)mapTypes.get(functionkey);
      if (listTypes == null) {
        System.out.println("function key not found: " + functionkey);
        return null;
      }
      int index = 1;
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

          return null;
        }
      }

      Object[] objParams = new Object[listParams.size()];
      index = 0;
      for (Iterator i$ = listParams.iterator(); i$.hasNext(); ) { Object param = i$.next();
        objParams[(index++)] = param;
      }
      Method method = (Method)mapMethods.get(functionkey);
      Object result = method.invoke(values[0], objParams);
      return UDFUtil.delZero(String.valueOf(result));
    }
    catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static void main(String[] args) {
    JavaString reflect = new JavaString();
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++)
    {
      reflect.evaluate("indexOf", new String[] { "123", "23", "0" });
    }

    long time2 = System.currentTimeMillis();
    System.out.println("cost time: " + (time2 - time1));
  }

  static
  {
    try
    {
      Class string = Class.forName(String.class.getName());
      Method[] methods = string.getDeclaredMethods();
      for (Method method : methods) {
        String name = method.getName();
        List listTypes = new ArrayList();

        boolean flagSupport = true;
        Type[] types = method.getGenericParameterTypes();
        for (Type type : types) {
          flagSupport = JavaType.addTypeValue(type, listTypes);
          if (!flagSupport) {
            System.out.println("method: " + name + " missing param type: " + type.toString());
            flagSupport = false;
            break;
          }
        }

        if (!flagSupport)
        {
          continue;
        }
        String namekey = name + "_" + listTypes.size();
        namekey = namekey.toLowerCase();
        if (mapTypes.get(namekey) != null) {
          List listTypesold = (List)mapTypes.get(namekey);
          int valueComplexity = 0;
          for (Integer complexity : listTypesold)
            valueComplexity += complexity.intValue();
          for (Integer complexity : listTypes)
            valueComplexity -= complexity.intValue();
          if (valueComplexity >= 0) {
            continue;
          }
        }
        Type type = method.getGenericReturnType();
        flagSupport = JavaType.putTypeValue(type, mapReturns, namekey);
        if (!flagSupport) {
          System.out.println("method: " + name + " missing return type: " + type.toString());
        }
        else
        {
          mapTypes.put(namekey, listTypes);
          mapMethods.put(namekey, method);
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
  }
}