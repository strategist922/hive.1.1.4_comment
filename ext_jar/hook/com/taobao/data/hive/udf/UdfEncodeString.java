package com.taobao.data.hive.udf;

import java.io.UnsupportedEncodingException;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UdfEncodeString extends UDF {

	public UdfEncodeString() {
	}

	public String evaluate(Text src, String enc){
		if (src == null) {
			return null;
		}

		String res = null;
		try {
			res = new String(src.getBytes(), 0, src.getLength(), enc);
			return new String(res.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
 
	public String evaluate(Text src){
		return evaluate(src, "GBK");
	}

}
