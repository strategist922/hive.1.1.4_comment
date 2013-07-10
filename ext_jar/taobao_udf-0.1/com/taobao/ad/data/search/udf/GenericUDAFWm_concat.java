package com.taobao.ad.data.search.udf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.AggregationBuffer;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.Mode;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils.ObjectInspectorCopyOption;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;

@Description(name="wm_concat", value="_FUNC_(expr) - Returns the concat value of expr")
public class GenericUDAFWm_concat extends AbstractGenericUDAFResolver
{
  static final Log LOG = LogFactory.getLog(GenericUDAFWm_concat.class.getName());

  public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters)
    throws SemanticException
  {
    if (parameters.length != 1) {
      throw new UDFArgumentTypeException(parameters.length - 1, "Exactly one argument is expected.");
    }

    ObjectInspector oi = TypeInfoUtils.getStandardJavaObjectInspectorFromTypeInfo(parameters[0]);
    if (!ObjectInspectorUtils.compareSupported(oi)) {
      throw new UDFArgumentTypeException(parameters.length - 1, "Cannot support comparison of map<> type or complex type containing map<>.");
    }

    return new GenericUDAFWm_concatEvaluator();
  }

  public static class GenericUDAFWm_concatEvaluator extends GenericUDAFEvaluator
  {
    ObjectInspector inputOI;
    ObjectInspector outputOI;
    boolean warned = false;

    public ObjectInspector init(GenericUDAFEvaluator.Mode m, ObjectInspector[] parameters)
      throws HiveException
    {
      assert (parameters.length == 1);
      super.init(m, parameters);
      this.inputOI = parameters[0];

      this.outputOI = ObjectInspectorUtils.getStandardObjectInspector(this.inputOI, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);

      return this.outputOI;
    }

    public GenericUDAFEvaluator.AggregationBuffer getNewAggregationBuffer()
      throws HiveException
    {
      Wm_concatAgg result = new Wm_concatAgg();
      return result;
    }

    public void reset(GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException
    {
      Wm_concatAgg myagg = (Wm_concatAgg)agg;
      myagg.o = null;
      myagg.mcount = 0;
    }

    public void iterate(GenericUDAFEvaluator.AggregationBuffer agg, Object[] parameters)
      throws HiveException
    {
      assert (parameters.length == 1);
      merge(agg, parameters[0]);
    }

    public Object terminatePartial(GenericUDAFEvaluator.AggregationBuffer agg) throws HiveException
    {
      return terminate(agg);
    }

    public void merge(GenericUDAFEvaluator.AggregationBuffer agg, Object partial)
      throws HiveException
    {
      if (partial != null) {
        Wm_concatAgg myagg = (Wm_concatAgg)agg;

        if (myagg.mcount == 0) {
          myagg.o = ObjectInspectorUtils.copyToStandardObject(partial, this.inputOI, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
        }
        else {
          myagg.size = (myagg.o.toString().length() >= 4000 ? 4000 : myagg.o.toString().length());
          if ((partial.toString().length() > 0) && (myagg.size == 0))
            myagg.o = ObjectInspectorUtils.copyToStandardObject(partial, this.inputOI, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
          else if ((myagg.size < 4000) && (partial.toString().length() > 0)) {
            myagg.o = (myagg.o.toString() + ',' + ObjectInspectorUtils.copyToStandardObject(partial, this.inputOI, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA));
          }
        }
        myagg.mcount += 1;
      }
    }

    public Object terminate(GenericUDAFEvaluator.AggregationBuffer agg)
      throws HiveException
    {
      Wm_concatAgg myagg = (Wm_concatAgg)agg;
      return myagg.o.toString().length() >= 4000 ? myagg.o.toString().substring(0, 4000) : myagg.o;
    }

    static class Wm_concatAgg
      implements GenericUDAFEvaluator.AggregationBuffer
    {
      Object o;
      int mcount = 0;
      int size = 4000;
    }
  }
}