package com.taobao.hive.udf.logutil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Description(name="get_json_object", value="_FUNC_(json_txt, path) - Extract a json object from path ", extended="Extract json object from a json string based on json path specified, and return json string of the extracted json object. It will return null if the input json string is invalid.\nA limited version of JSONPath supported:\n  $   : Root object\n  .   : Child operator\n  []  : Subscript operator for array\n  *   : Wildcard for []\nSyntax not supported that's worth noticing:\n  ''  : Zero length string as key\n  ..  : Recursive descent\n  &amp;#064;   : Current object/element\n  ()  : Script expression\n  ?() : Filter (script) expression.\n  [,] : Union operator\n  [start:end:step] : array slice operator\n")
public class UDFGetValue extends UDF
{
  private final Pattern patternKey = Pattern.compile("^([a-zA-Z0-9_\\-]+).*");
  private final Pattern patternIndex = Pattern.compile("\\[([0-9]+|\\*)\\]");

  static Map<String, Object> extractObjectCache = new HashCache();
  static Map<String, String[]> pathExprCache = new HashCache();
  static Map<String, ArrayList<String>> indexListCache = new HashCache();
  static Map<String, String> mKeyGroup1Cache = new HashCache();
  static Map<String, Boolean> mKeyMatchesCache = new HashCache();

  Text result = new Text();

  ArrayList<Object> jsonList = new ArrayList();

  public Text evaluate(String jsonStr, String path)
  {
    if ((jsonStr == null) || (jsonStr == "") || (path == null) || (path == ""))
    {
      return null;
    }
    String jResult = evaluateJson(jsonStr, path);
    if ((jResult == null) || ("".equals(jResult))) return null;
    this.result.set(jResult);
    return this.result;
  }

  public Text evaluate(String jsonStr, String path, String value)
  {
    if ((jsonStr == null) || (jsonStr == "") || (path == null) || (path == "") || (value == null) || ("".equals(value)))
    {
      this.result.set("false");
      return this.result;
    }
    String jResult = evaluateJson(jsonStr, path);
    if ((jResult == null) || ("".equals(jResult))) {
      this.result.set("false");
      return this.result;
    }
    String[] sV = value.split(",");
    String[] dV = jResult.split(",");
    int sameCount = 0;
    for (int i = 0; i < sV.length; i++) {
      for (int j = 0; j < dV.length; j++) {
        if (sV[i].equals(dV[j])) {
          sameCount++;
          break;
        }
      }
    }
    if (sameCount == sV.length)
      this.result.set("true");
    else {
      this.result.set("false");
    }
    return this.result;
  }

  public Text evaluate(String jsonStr, String path, String value, String flag)
  {
    if ("&".equals(flag)) return evaluate(jsonStr, path, value);
    if ((jsonStr == null) || (jsonStr == "") || (path == null) || (path == "") || (value == null) || ("".equals(value)) || (flag == null) || (!"|".equals(flag)))
    {
      this.result.set("false");
      return this.result;
    }
    String jResult = evaluateJson(jsonStr, path);
    if ((jResult == null) || ("".equals(jResult))) {
      this.result.set("false");
      return this.result;
    }
    String[] sV = value.split(",");
    String[] dV = jResult.split(",");
    for (int i = 0; i < sV.length; i++) {
      for (int j = 0; j < dV.length; j++) {
        if (sV[i].equals(dV[j])) {
          this.result.set("true");
          return this.result;
        }
      }
    }
    this.result.set("false");
    return this.result;
  }

  private String evaluateJson(String jsonString, String pathString)
  {
    try
    {
      pathString = "$." + pathString;

      String[] pathExpr = (String[])pathExprCache.get(pathString);
      if (pathExpr == null) {
        pathExpr = pathString.split("\\.", -1);
        pathExprCache.put(pathString, pathExpr);
      }

      if (!pathExpr[0].equalsIgnoreCase("$")) {
        return null;
      }

      Object extractObject = extractObjectCache.get(jsonString);
      if (extractObject == null) {
        extractObject = new JSONObject(jsonString);
        extractObjectCache.put(jsonString, extractObject);
      }
      for (int i = 1; i < pathExpr.length; i++) {
        extractObject = extract(extractObject, pathExpr[i]);
      }
      return extractObject.toString(); } catch (Exception e) {
    }
    return null;
  }

  private Object extract(Object json, String path)
    throws JSONException
  {
    Matcher mKey = null;
    Boolean mKeyMatches = (Boolean)mKeyMatchesCache.get(path);
    if (mKeyMatches == null) {
      mKey = this.patternKey.matcher(path);
      mKeyMatches = mKey.matches() ? Boolean.TRUE : Boolean.FALSE;
      mKeyMatchesCache.put(path, mKeyMatches);
    }
    if (!mKeyMatches.booleanValue()) {
      return null;
    }

    String mKeyGroup1 = (String)mKeyGroup1Cache.get(path);
    if (mKeyGroup1 == null) {
      if (mKey == null) {
        mKey = this.patternKey.matcher(path);
      }
      mKeyGroup1 = mKey.group(1);
      mKeyGroup1Cache.put(path, mKeyGroup1);
    }
    json = extract_json_withkey(json, mKeyGroup1);

    ArrayList indexList = (ArrayList)indexListCache.get(path);
    if (indexList == null) {
      Matcher mIndex = this.patternIndex.matcher(path);
      indexList = new ArrayList();
      while (mIndex.find()) {
        indexList.add(mIndex.group(1));
      }
      indexListCache.put(path, indexList);
    }

    if (indexList.size() > 0) {
      json = extract_json_withindex(json, indexList);
    }

    return json;
  }

  private Object extract_json_withindex(Object json, ArrayList<String> indexList)
    throws JSONException
  {
    this.jsonList.clear();
    this.jsonList.add(json);
    Iterator itr = indexList.iterator();
    while (itr.hasNext()) {
      String index = (String)itr.next();
      ArrayList tmp_jsonList = new ArrayList();
      if (index.equalsIgnoreCase("*")) {
        for (int i = 0; i < this.jsonList.size(); i++) {
          try {
            JSONArray array = (JSONArray)this.jsonList.get(i);
            for (int j = 0; j < array.length(); j++)
              tmp_jsonList.add(array.get(j));
          }
          catch (Exception e)
          {
          }
        }
        this.jsonList = tmp_jsonList;
      } else {
        for (int i = 0; i < this.jsonList.size(); i++) {
          try {
            tmp_jsonList.add(((JSONArray)this.jsonList.get(i)).get(Integer.parseInt(index)));
          }
          catch (ClassCastException e) {
            continue;
          } catch (JSONException e) {
            return null;
          }
          this.jsonList = tmp_jsonList;
        }
      }
    }
    return this.jsonList.size() > 1 ? new JSONArray(this.jsonList) : this.jsonList.get(0);
  }

  private Object extract_json_withkey(Object json, String path) throws JSONException
  {
    if (json.getClass() == JSONArray.class) {
      JSONArray jsonArray = new JSONArray();
      for (int i = 0; i < ((JSONArray)json).length(); i++) {
        Object josn_elem = ((JSONArray)json).get(i);
        try {
          Object json_obj = ((JSONObject)josn_elem).get(path);
          if (json_obj.getClass() == JSONArray.class) {
            for (int j = 0; j < ((JSONArray)json_obj).length(); j++)
              jsonArray.put(((JSONArray)json_obj).get(j));
          }
          else
            jsonArray.put(json_obj);
        }
        catch (Exception e)
        {
        }
      }
      return jsonArray.length() == 0 ? null : jsonArray;
    }
    return ((JSONObject)json).get(path);
  }

  static class HashCache<K, V> extends LinkedHashMap<K, V>
  {
    private static final int CACHE_SIZE = 16;
    private static final int INIT_SIZE = 32;
    private static final float LOAD_FACTOR = 0.6F;
    private static final long serialVersionUID = 1L;

    HashCache()
    {
      super(0.6F);
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
    {
      return size() > 16;
    }
  }
}