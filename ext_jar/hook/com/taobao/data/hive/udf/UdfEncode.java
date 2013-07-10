package com.taobao.data.hive.udf;

import java.io.UnsupportedEncodingException;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UdfEncode extends UDF {

	public UdfEncode() {
	}

	public Text evaluate(Text src, String enc){
		if (src == null) {
			return null;
		}

		String res = null;
		try {
			res = new String(src.getBytes(), 0, src.getLength(), enc);
			return new Text(new String(res.getBytes(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
 
	public Text evaluate(Text src){
		return evaluate(src, "GBK");
	}

}
