package com.taobao.data.hive.udf;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

/**
 * �����������ڵĲ�ֵ�����жϲ�ֵ�Ƿ���ָ����Χ��
 * 
 * @author ����
 * @created 2010-02-01
 */
public class UDFDateDiff extends UDF {
	private static final int MILLISECS_PER_DAY = 1000 * 60 * 60 * 24;
	private static final DateFormat df = new SimpleDateFormat("yyyyMMdd");

	private static BooleanWritable TRUE_WRITABLE = new BooleanWritable(true);
	private static BooleanWritable FALSE_WRITABLE = new BooleanWritable(false);
	/**
	 * 
	 * @param day1
	 *            �Ƚ�����1
	 * @param day2
	 *            �Ƚ�����2
	 * 
	 * @param diffRange
	 *            ָ���Ĳ�ֵ��Χ
	 * 
	 * @return ���ڲ�ֵ�Ƿ���ָ����ֵ��Χ��
	 */
	public BooleanWritable evaluate(Text day1, Text day2, LongWritable diff) {
		
		if (diff == null) {
			return TRUE_WRITABLE;
		}

		try {
			df.setLenient(false);
			df.setLenient(false);
			
			Date date1 = df.parse(day1.toString());
			Date date2 = df.parse(day2.toString());

			long days = Math.abs((date1.getTime() - date2.getTime()) / MILLISECS_PER_DAY);

			if (days >= 0 && days < diff.get()) {
				return TRUE_WRITABLE;
			} else {
				return FALSE_WRITABLE;
			}

		} catch (Throwable t) {
			System.err.println(t);
			System.err.println(day1);
			System.err.println(day2);
			System.err.println(diff.toString());
			return FALSE_WRITABLE;
		}
	}

}

