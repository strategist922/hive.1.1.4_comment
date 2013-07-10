package com.taobao.data.hive.udf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 计算两个日期的差值，并判断差值是否在指定范围内
 * 
 * @author 常龙
 * @created 2010-02-01
 */
public class BadUDFDateDiff extends UDF {
	private static final int MILLISECS_PER_DAY = 1000 * 60 * 60 * 24;
	private static final DateFormat df = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 
	 * @param day1
	 *            比较日期1
	 * @param day2
	 *            比较日期2
	 * 
	 * @param diffRange
	 *            指定的差值范围
	 * 
	 * @return 日期差值是否在指定差值范围内
	 */
	public Boolean evaluate(String day1, String day2, Long diffRange) {
		if (diffRange == null || diffRange <= 0) {
			return false;
		}

		try {
			df.setLenient(false);
			df.setLenient(false);
			
			Date date1 = df.parse(day1);
			Date date2 = df.parse(day2);

			long diff = date1.getTime() - date2.getTime();
			long days = Math.abs(diff / MILLISECS_PER_DAY);

			if (days >= 0 && days < diffRange) {
				return true;
			} else {
				return false;
			}

		} catch (Throwable t) {
			System.err.println(t);
			System.err.println(day1);
			System.err.println(day2);
			System.err.println(diffRange.toString());
			return false;
		}
	}

}

