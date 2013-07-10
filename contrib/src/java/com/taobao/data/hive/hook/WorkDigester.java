package com.taobao.data.hive.hook;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.conf.HiveConf.ConfVars;
import org.apache.hadoop.hive.ql.exec.Operator;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.ql.plan.Explain;
import org.apache.hadoop.hive.ql.plan.MapredWork;
import org.apache.hadoop.hive.ql.session.SessionState;

public class WorkDigester {
  private HiveConf conf;
  private static MessageDigest md = null;
  private boolean isConfigUpdated;

  private final Log LOG = LogFactory.getLog(WorkDigester.class);

  public WorkDigester(HiveConf conf) {
    this.conf = conf;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  private void updateConfig() throws IOException {
    // All dependent jar/files
    // FIXME: parameter HIVEJAR can be empty, so that hive version change
    // will not be awared.
    updateFiles(conf.getVar(ConfVars.HIVEJAR));
    updateFiles(conf.getVar(ConfVars.HIVEAUXJARS));
    updateFiles(getResourceFiles(conf, SessionState.ResourceType.FILE));
    updateFiles(getResourceFiles(conf, SessionState.ResourceType.ARCHIVE));
    updateFiles(getResourceFiles(conf, SessionState.ResourceType.JAR));
    isConfigUpdated = true;
  }

  private void updateFiles(String paths) throws IOException {
    if (paths == null || paths.isEmpty()) {
      return;
    }
    for (String path : paths.split(",")) {
      updateFile(path);
    }
  }

  private void updateFile(String path) throws IOException {
    Path p = new Path(path.trim());
    FileSystem fs = p.getFileSystem(conf);
    if (!fs.exists(p)) {
      LOG.info("File " + p + " do not exist, ignore.");
      return;
    }
    if (fs.isFile(p)) {
      DataInputStream is = fs.open(p);
      BufferedInputStream in = new BufferedInputStream(is);
      byte[] buffer = new byte[8192];
      int length;
      while ((length = in.read(buffer)) != -1) {
        md.update(buffer, 0, length);
      }
      in.close();
    } else {
      FileStatus[] list = fs.listStatus(p);
      if (list != null) {
        for (FileStatus file : list) {
          updateFile(file.getPath().toString());
        }
      }
    }
  }

  /**
   * Copied from ExecDriver
   *
   * @param conf
   * @param t
   * @return
   */
  protected static String getResourceFiles(Configuration conf, SessionState.ResourceType t) {
    // fill in local files to be added to the task environment
    SessionState ss = SessionState.get();
    Set<String> files = (ss == null) ? null : ss.list_resource(t, null);
    if (files != null) {
      List<String> realFiles = new ArrayList<String>(files.size());
      for (String one : files) {
        try {
          realFiles.add(Utilities.realFile(one, conf));
        } catch (IOException e) {
          throw new RuntimeException("Cannot validate file " + one + "due to exception: "
              + e.getMessage(), e);
        }
      }
      return StringUtils.join(realFiles, ",");
    } else {
      return "";
    }
  }

  public String digest(MapredWork work, PathContext pathctx)
      throws IOException {
    if (!isConfigUpdated) {
      updateConfig();
    }

    MessageDigest mdt = null;
    try {
      mdt = (MessageDigest) md.clone();
    } catch (CloneNotSupportedException e) {
      throw new IOException(e);
    }

    // Task explain
    String explainFiltered = getTaskExplain(work)
        .replaceAll(pathctx.getScratchdir().toString() + "[^\\s\\]]*", "")
        .replaceAll("transient_lastDdlTime " + "[^\\s\\]]*", "");
    Map<String, String> externalScratchDirs = pathctx.getExternalScratchdirs();
    if (!externalScratchDirs.isEmpty()) {
      String externalDir = null;
      for (String dir : externalScratchDirs.values()) {
        externalDir = (new Path(dir)).toUri().getPath();
        break;
      }
      explainFiltered = explainFiltered.replaceAll(externalDir + "[^\\s\\]]*", "");
    }
    mdt.update(explainFiltered.getBytes());
    LOG.debug("Digested query plan:\n" + explainFiltered);
    String hex = hexfyBytes(mdt.digest());
    LOG.debug("Digested MD5:" + hex);
    return hex;
  }

  private static String hexfyBytes(byte[] bytes) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < bytes.length; i++) {
      hexString.append(Integer.toHexString(0xFF & bytes[i]));
    }
    return hexString.toString();
  }

  public HiveConf getConf() {
    return conf;
  }

  public void setConf(HiveConf conf) {
    this.conf = conf;
    this.isConfigUpdated = false;
  }

  public String getTaskExplain(MapredWork work) {
    try {
      OutputStream outS = new ByteArrayOutputStream();
      PrintStream out = new PrintStream(outS);
      // Go over all the tasks and dump out the plans
      outputPlan(work, out, 0);
      out.close();

      return outS.toString();
    } catch (Exception e) {
      System.err.println("Failed with exception " + e.getMessage());
      return null;
    }
  }

  private String indentString(int indent) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < indent; ++i) {
      sb.append(" ");
    }

    return sb.toString();
  }

  private void outputMap(Map<?, ?> mp, String header, PrintStream out,
      int indent) throws Exception {

    boolean first_el = true;
    TreeMap<Object, Object> tree = new TreeMap<Object, Object>();
    tree.putAll(mp);
    for (Entry<?, ?> ent : tree.entrySet()) {
      if (first_el) {
        out.println(header);
      }
      first_el = false;

      // Print the key
      out.print(indentString(indent));
      out.printf("%s ", ent.getKey().toString());
      // Print the value
      if (isPrintable(ent.getValue())) {
        out.print(ent.getValue());
        out.println();
      } else if (ent.getValue() instanceof List
          || ent.getValue() instanceof Map) {
        out.print(ent.getValue().toString());
        out.println();
      } else if (ent.getValue() instanceof Serializable) {
        out.println();
        outputPlan((Serializable) ent.getValue(), out, indent + 2);
      } else {
        out.println();
      }
    }
  }

  private void outputList(List<?> l, String header, PrintStream out,
      int indent) throws Exception {

    boolean first_el = true;
    boolean nl = false;
    for (Object o : l) {
      if (first_el) {
        out.print(header);
      }

      if (isPrintable(o)) {
        if (!first_el) {
          out.print(", ");
        } else {
          out.print(" ");
        }

        out.print(o);
        nl = true;
      } else if (o instanceof Serializable) {
        if (first_el) {
          out.println();
        }
        outputPlan((Serializable) o, out, indent + 2);
      }

      first_el = false;
    }

    if (nl) {
      out.println();
    }
  }

  private boolean isPrintable(Object val) {
    if (val instanceof Boolean || val instanceof String
        || val instanceof Integer || val instanceof Byte
        || val instanceof Float || val instanceof Double) {
      return true;
    }

    if (val != null && val.getClass().isPrimitive()) {
      return true;
    }

    return false;
  }

  private void outputPlan(Serializable work, PrintStream out,
      int indent) throws Exception {
    // Check if work has an explain annotation
    Annotation note = work.getClass().getAnnotation(Explain.class);

    if (note instanceof Explain) {
      Explain xpl_note = (Explain) note;
      out.print(indentString(indent));
      out.println(xpl_note.displayName());
    }

    // If this is an operator then we need to call the plan generation on
    // the
    // conf and then
    // the children
    if (work instanceof Operator) {
      Operator<? extends Serializable> operator = (Operator<?>) work;
      if (operator.getConf() != null) {
        outputPlan(operator.getConf(), out, indent);
      }
      if (operator.getChildOperators() != null) {
        for (Operator<? extends Serializable> op : operator
            .getChildOperators()) {
          outputPlan(op, out, indent + 2);
        }
      }
      return;
    }

    // We look at all methods that generate values for explain
    Method[] methods = work.getClass().getMethods();
    Arrays.sort(methods, new MethodComparator());

    for (Method m : methods) {
      int prop_indents = indent + 2;
      note = m.getAnnotation(Explain.class);

      if (note instanceof Explain) {
        Explain xpl_note = (Explain) note;

        Object val = m.invoke(work);

        if (val == null) {
          continue;
        }

        String header = null;
        if (!xpl_note.displayName().equals("")) {
          header = indentString(prop_indents)
              + xpl_note.displayName() + ":";
        } else {
          prop_indents = indent;
          header = indentString(prop_indents);
        }

        if (isPrintable(val)) {

          out.printf("%s ", header);
          out.println(val);
          continue;
        }
        // Try this as a map
        try {
          // Go through the map and print out the stuff
          Map<?, ?> mp = (Map<?, ?>) val;
          outputMap(mp, header, out, prop_indents + 2);
          continue;
        } catch (ClassCastException ce) {
          // Ignore - all this means is that this is not a map
        }

        // Try this as a list
        try {
          List<?> l = (List<?>) val;
          outputList(l, header, out, prop_indents + 2);

          continue;
        } catch (ClassCastException ce) {
          // Ignore
        }

        // Finally check if it is serializable
        try {
          Serializable s = (Serializable) val;
          out.println(header);
          outputPlan(s, out, prop_indents + 2);

          continue;
        } catch (ClassCastException ce) {
          // Ignore
        }
      }
    }
  }

  /**
   * MethodComparator.
   *
   */
  public static class MethodComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      Method m1 = (Method) o1;
      Method m2 = (Method) o2;
      return m1.getName().compareTo(m2.getName());
    }
  }
}
