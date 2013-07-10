package com.taobao.hive.udtf;

import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class UDTFExplodeIP extends GenericUDTF
{
  StringBuilder sb = new StringBuilder("");

  public void close()
    throws HiveException
  {
  }

  public StructObjectInspector initialize(ObjectInspector[] args)
    throws UDFArgumentException
  {
    if (args.length != 2) {
      throw new UDFArgumentLengthException("UDTFExplodeKeyValue takes two arguments");
    }
    if ((args[0].getCategory() != ObjectInspector.Category.PRIMITIVE) || (args[1].getCategory() != ObjectInspector.Category.PRIMITIVE)) {
      throw new UDFArgumentException("UDTFExplodeIP takes string as a parameter");
    }

    ArrayList fieldNames = new ArrayList();
    ArrayList fieldOIs = new ArrayList();
    fieldNames.add("col1");
    fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
  }

  public void process(Object[] args) throws HiveException
  {
    if ((args == null) || (args.length != 2)) {
      forward(new String[1]);
      return;
    }
    long startIp = convertIP2Long(args[0].toString());
    long endIp = convertIP2Long(args[1].toString());
    if ((startIp > 0L) && (endIp > 0L) && (startIp < endIp))
      for (long i = startIp; i <= endIp; i += 1L)
        forward(new String[] { convertIP2Str(i) });
  }

  public long convertIP2Long(String ipStr)
  {
    try
    {
      long[] ipLongs = new long[4];
      String[] ips = ipStr.split("\\.");
      if (ips.length != 4) {
        return -1L;
      }
      for (int i = 0; i < ips.length; i++) {
        long ipLong = Long.parseLong(ips[i]);
        if (ipLong > 255L) {
          return -1L;
        }
        ipLongs[i] = ipLong;
      }

      return (ipLongs[0] << 24) + (ipLongs[1] << 16) + (ipLongs[2] << 8) + ipLongs[3]; } catch (Exception e) {
    }
    return -1L;
  }

  public String convertIP2Str(long ip)
  {
    try {
      this.sb.delete(0, this.sb.length());
      this.sb.append(String.valueOf(ip >>> 24));
      this.sb.append(".");
      this.sb.append(String.valueOf((ip & 0xFFFFFF) >>> 16));
      this.sb.append(".");
      this.sb.append(String.valueOf((ip & 0xFFFF) >>> 8));
      this.sb.append(".");
      this.sb.append(String.valueOf(ip & 0xFF));
      return this.sb.toString(); } catch (Exception e) {
    }
    return "";
  }

  public static void main(String[] args)
  {
    UDTFExplodeIP ip = new UDTFExplodeIP();
    String start_str = "1.9.0.0";
    String end_str = "1.9.1.255";
    long startIp = ip.convertIP2Long(start_str);
    long endIp = ip.convertIP2Long(end_str);
    System.out.println(Integer.valueOf("1090000", 16).toString());
    System.out.println(Long.toHexString(startIp));
    System.out.println(startIp + "--" + endIp);
    System.out.println(ip.convertIP2Str(new Long(Integer.valueOf("1090000", 16).toString()).longValue()));
  }
}