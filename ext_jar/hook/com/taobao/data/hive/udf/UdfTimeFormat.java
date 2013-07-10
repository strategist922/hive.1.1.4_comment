package com.taobao.data.hive.udf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.hive.ql.exec.UDF;

public class UdfTimeFormat extends UDF{

	public UdfTimeFormat() {
	}

	public String evaluate(String strTime, String newPattern, String formerPattern, String onError) {
		if (strTime == null) {
			return null;
		}
		SimpleDateFormat newformatter = new SimpleDateFormat(newPattern);
		SimpleDateFormat formatter;
		if (formerPattern == null) {
			formatter = new SimpleDateFormat();
		} else {
			formatter = new SimpleDateFormat(formerPattern);
		}
		formatter.setLenient(false);
		
		try {
			Date d = formatter.parse(strTime);
			return newformatter.format(d);
		} catch (ParseException e) {
			return onError;
		}
	}
	
	public String evaluate(String strTime, String newPattern, String formerPattern) {
		return evaluate(strTime, newPattern, formerPattern, null);
	}

	public String evaluate(String strTime, String newPattern) {
		return evaluate(strTime, newPattern, null, null);
	}
}
