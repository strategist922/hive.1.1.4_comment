package org.apache.hadoop.mapred.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.hooks.AutoConstants;
import org.apache.hadoop.hive.ql.hooks.NumCalc;
import org.apache.hadoop.mapred.JobConf;

public class ScriptInvocate
{
  private JobConf conf;
  private Map<Object, Object> counters;
  private Map<Object, Object> debug;
  private final ScriptEngineManager engineManager;
  private final ScriptEngine engine;
  private FileInputStream scriptFileStream;
  private String scriptType;
  private String debugParameterName;
  private String jobconfParameterName;
  private String counterParameterName;
  private NumCalc calc = new NumCalc();

  public ScriptInvocate(JobConf conf, Map<Object, Object> counters, Map<Object, Object> debug)
    throws FileNotFoundException
  {
    this.conf = conf;
    this.counters = counters;
    this.debug = debug;

    this.engineManager = new ScriptEngineManager();
    this.scriptType = conf.get("reduce.script.type", "velocity");
    if (StringUtils.isBlank(this.scriptType)) {
      this.scriptType = "velocity";
    }

    this.engine = this.engineManager.getEngineByName(this.scriptType);
    if (null == this.engine) {
      throw new ParameterException("don't support this script");
    }

    this.jobconfParameterName = conf.get("reduce.script.parameter.jobconf.name", "jobconf");

    if (StringUtils.isBlank(this.jobconfParameterName)) {
      this.jobconfParameterName = "jobconf";
    }

    this.engine.put(this.jobconfParameterName, this.conf);

    this.counterParameterName = conf.get("reduce.script.parameter.counter.name", "counterInfo");

    if (StringUtils.isBlank(this.counterParameterName)) {
      this.counterParameterName = "counterInfo";
    }

    this.engine.put(this.counterParameterName, this.counters);

    this.debugParameterName = conf.get("reduce.script.parameter.debug.name", "debug");

    if (StringUtils.isBlank(this.debugParameterName)) {
      this.debugParameterName = "debug";
    }

    this.engine.put(this.debugParameterName, this.debug);

    String scriptFile = conf.get("reduce.script.file.path");
    if (StringUtils.isBlank(scriptFile)) {
      throw new ParameterException("script path is empty");
    }
    if (conf.getBoolean(AutoConstants.PRINTMORE, false))
      this.engine.put("out", System.out);
    this.engine.put("ReduceNumCalc", this.calc);

    this.scriptFileStream = new FileInputStream(new File(scriptFile));
  }

  public void run()
    throws ScriptException
  {
    this.engine.eval(new InputStreamReader(this.scriptFileStream));
    if ((this.scriptType.equalsIgnoreCase("jruby")) || (this.scriptType.equalsIgnoreCase("ruby")))
    {
      StringBuffer compute = new StringBuffer();
      compute.append("compute($").append(this.jobconfParameterName).append(",$").append(this.counterParameterName).append(",$").append(this.debugParameterName).append(")");

      this.engine.eval(compute.toString());
    }
  }

  public void close()
    throws IOException
  {
    if (this.scriptFileStream != null)
      this.scriptFileStream.close();
  }
}