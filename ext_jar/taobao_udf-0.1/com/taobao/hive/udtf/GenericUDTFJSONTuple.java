package com.taobao.hive.udtf;

import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.io.Text;
import org.json.JSONException;
import org.json.JSONObject;

@Description(name="json_tuple", value="_FUNC_(jsonStr, p1, p2, ..., pn) - like get_json_object, but it takes multiple names and return a tuple. All the input parameters and output column types are string.")
public class GenericUDTFJSONTuple extends GenericUDTF
{
  private static Log LOG = LogFactory.getLog(GenericUDTFJSONTuple.class.getName());
  int numCols;
  String[] paths;
  Text[] retCols;
  Text[] cols;
  Object[] nullCols;
  ObjectInspector[] inputOIs;
  boolean pathParsed = false;
  boolean seenErrors = false;

  public void close()
    throws HiveException
  {
  }

  public StructObjectInspector initialize(ObjectInspector[] args)
    throws UDFArgumentException
  {
    this.inputOIs = args;
    this.numCols = (args.length - 1);

    if (this.numCols < 1) {
      throw new UDFArgumentException("json_tuple() takes at least two arguments: the json string and a path expression");
    }

    for (int i = 0; i < args.length; i++) {
      if ((args[i].getCategory() == ObjectInspector.Category.PRIMITIVE) && (args[i].getTypeName().equals("string")))
        continue;
      throw new UDFArgumentException("json_tuple()'s arguments have to be string type");
    }

    this.seenErrors = false;
    this.pathParsed = false;
    this.paths = new String[this.numCols];
    this.cols = new Text[this.numCols];
    this.retCols = new Text[this.numCols];
    this.nullCols = new Object[this.numCols];

    for (int i = 0; i < this.numCols; i++) {
      this.cols[i] = new Text();
      this.retCols[i] = this.cols[i];
      this.nullCols[i] = null;
    }

    ArrayList fieldNames = new ArrayList(this.numCols);
    ArrayList fieldOIs = new ArrayList(this.numCols);
    for (int i = 0; i < this.numCols; i++)
    {
      fieldNames.add("c" + i);

      fieldOIs.add(PrimitiveObjectInspectorFactory.writableStringObjectInspector);
    }
    return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
  }

  public void process(Object[] o)
    throws HiveException
  {
    if (o[0] == null) {
      forward(this.nullCols);
      return;
    }

    if (!this.pathParsed) {
      for (int i = 0; i < this.numCols; i++) {
        this.paths[i] = ((StringObjectInspector)this.inputOIs[(i + 1)]).getPrimitiveJavaObject(o[(i + 1)]);
      }
      this.pathParsed = true;
    }

    String jsonStr = ((StringObjectInspector)this.inputOIs[0]).getPrimitiveJavaObject(o[0]);
    if (jsonStr == null) {
      forward(this.nullCols);
      return;
    }
    try {
      JSONObject jsonObj = new JSONObject(jsonStr);

      for (int i = 0; i < this.numCols; i++) {
        if (jsonObj.isNull(this.paths[i])) {
          this.retCols[i] = null;
        } else {
          if (this.retCols[i] == null) {
            this.retCols[i] = this.cols[i];
          }
          this.retCols[i].set(jsonObj.getString(this.paths[i]));
        }
      }
      forward(this.retCols);
      return;
    }
    catch (JSONException e) {
      if (!this.seenErrors) {
        LOG.error("The input is not a valid JSON string: " + jsonStr + ". Skipping such error messages in the future.");
        this.seenErrors = true;
      }
      forward(this.nullCols);
      return;
    } catch (Throwable e) {
      LOG.error("JSON parsing/evaluation exception" + e);
      forward(this.nullCols);
    }
  }

  public String toString()
  {
    return "json_tuple";
  }
}