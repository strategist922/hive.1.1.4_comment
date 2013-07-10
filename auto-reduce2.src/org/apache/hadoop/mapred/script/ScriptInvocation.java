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
import org.apache.hadoop.mapred.AutoConstant;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.ReduceNumCalc;

public class ScriptInvocation
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
  private ReduceNumCalc calc = new ReduceNumCalc();

  public ScriptInvocation(JobConf conf, Map<Object, Object> counters, Map<Object, Object> debug)
    throws FileNotFoundException
  {
    this.conf = conf;
    this.counters = counters;
    this.debug = debug;

    this.engineManager = new ScriptEngineManager();
    this.scriptType = conf.get(AutoConstant.SCRIPT_NAME);
    if (StringUtils.isBlank(this.scriptType))
    {
      this.scriptType = "velocity";
    }

    this.engine = this.engineManager.getEngineByName(this.scriptType);
    if (null == this.engine) {
      throw new ParameterException("don't support this script");
    }

    this.jobconfParameterName = conf.get(AutoConstant.SCRIPT_PARAMETER);

    if (StringUtils.isBlank(this.jobconfParameterName))
    {
      this.jobconfParameterName = "jobconf";
    }

    this.engine.put(this.jobconfParameterName, this.conf);

    this.counterParameterName = conf.get(AutoConstant.SCRIPT_COUNTER);
    if (StringUtils.isBlank(this.counterParameterName))
    {
      this.counterParameterName = "counterInfo";
    }

    this.engine.put(this.counterParameterName, this.counters);

    this.debugParameterName = conf.get(AutoConstant.SCRIPT_DEBUG);
    if (StringUtils.isBlank(this.debugParameterName))
    {
      this.debugParameterName = "debug";
    }

    this.engine.put(this.debugParameterName, this.debug);

    String scriptFile = conf.get(AutoConstant.SCRIPT_FILE);
    if (StringUtils.isBlank(scriptFile)) {
      throw new ParameterException("script path is empty");
    }
    if (conf.getBoolean("auto.script.print.more", false))
      this.engine.put("out", System.out);
    this.engine.put("String", String.class);
    this.engine.put("ReduceNumCalc", this.calc);

    this.scriptFileStream = new FileInputStream(new File(scriptFile));
  }

  public void run()
    throws ScriptException
  {
    this.engine.eval(new InputStreamReader(this.scriptFileStream));
    if ((this.scriptType.equalsIgnoreCase("jruby")) || (this.scriptType.equalsIgnoreCase("ruby"))) {
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