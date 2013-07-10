package com.taobao.data.storage.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BooleanWritable;

public class UDFRange extends UDF {
	static final BooleanWritable falseWritable = new BooleanWritable(false);
	static final BooleanWritable trueWritable = new BooleanWritable(true);
	
	public UDFRange() {
		
	}

	public BooleanWritable evaluate(String pt_start, String pt_end,
			String start, String end) {
		// Deal with _INFINITY ...
		if (pt_end.charAt(6) == '_') {
			if ( !pt_end.substring(0, 6).equals(end.substring(0, 6)))
				return falseWritable;
		}
		// pt_start <= end
		if (pt_start.compareTo(end) > 0) {
			return falseWritable;
		}
		// pt_end > start
		if (pt_end.compareTo(start) <= 0) {
			return falseWritable;
		}
		return trueWritable;
	}
}
