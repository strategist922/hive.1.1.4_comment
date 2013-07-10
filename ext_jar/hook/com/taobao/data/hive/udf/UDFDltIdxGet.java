package com.taobao.data.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;

public class UDFDltIdxGet extends UDF {

	DoubleWritable r = new DoubleWritable(-1);
	/**
	 * 
	 * @param todayIdx
	 *            ����ָ��
	 * @param yesterdayIdx
	 *            ����ָ��
	 * @param N2
	 * 
	 * @return ָ��仯ֵ
	 */
	public DoubleWritable evaluate(DoubleWritable todayIdx, DoubleWritable yesterdayIdx) {
			try {
				if (yesterdayIdx == null && todayIdx == null)
					return r;
					//return IndexConst.N2;
				if (yesterdayIdx == null)
					return r;
					//return todayIdx - N2;
					//return IndexConst.ERROR_DOUBLE;
				if (todayIdx == null)
					return r;
					//return IndexConst.N2 - yesterdayIdx;
				else
					return r;
					//return todayIdx - yesterdayIdx;
			} catch (Throwable t) {
				//return IndexConst.N2;
			}
			return r;
		}
}
