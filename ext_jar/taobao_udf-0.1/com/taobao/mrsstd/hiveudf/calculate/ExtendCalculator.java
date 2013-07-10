package com.taobao.mrsstd.hiveudf.calculate;

import com.taobao.mrsstd.hiveudf.extend.Parent;
import java.util.Map;

public class ExtendCalculator
{
  private String values;
  private String extension;
  private Map<String, String> mapValues;

  public ExtendCalculator(String extension, Map<String, String> mapValues, String values)
  {
    this.values = values;
    this.mapValues = mapValues;
    this.extension = extension;
  }

  public String compute() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    String classname = null;
    String function = null;
    if (this.extension.indexOf(".") != -1) {
      String[] strs = this.extension.split("\\.");
      classname = strs[0];
      function = strs[1];
    } else {
      classname = this.extension;
    }

    if (classname.equalsIgnoreCase("JavaMath"))
      classname = "JavaMath";
    else if (classname.equalsIgnoreCase("JavaString"))
      classname = "JavaString";
    else if (classname.equalsIgnoreCase("JavaMathDirect"))
      classname = "JavaMathDirect";
    else if (classname.equalsIgnoreCase("JavaCHGIdx"))
      classname = "JavaCHGIdx";
    else if (classname.equalsIgnoreCase("JavaCHGRatio")) {
      classname = "JavaCHGRatio";
    }
    Object object = Class.forName("com.taobao.mrsstd.hiveudf.extend." + classname).newInstance();
    Parent parent = (Parent)object;

    String[] arrValue = this.values.split(",");
    for (int i = 0; i < arrValue.length; i++) {
      String value = arrValue[i];

      String field = (String)this.mapValues.get(value.toLowerCase().trim());
      if (field != null)
        arrValue[i] = field;
      else if (this.mapValues.containsKey(value.toLowerCase().trim())) {
        arrValue[i] = null;
      }
    }
    String result = parent.evaluate(function, arrValue);

    return result;
  }
}