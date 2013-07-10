package com.taobao.data.hive.test;

import com.taobao.data.storage.udf.UDFRange;

import junit.framework.TestCase;

public class TestUDFRange extends TestCase {
	

	@Override
	public void setUp() {

	}

	@Override
	public void tearDown() {

	}

	public void testEvaluate(){
		//		range_start	range_end	pt_start	pt_end	accept (Y/N)
		//		20100728	20100928	20100727	20100729	Y
		//		20100728	20100928	20100728	20100729	Y
		//		20100728	20100928	20100729	20100729	Y
		//		20100728	20100928	20100901	20100928	Y
		//		20100728	20100928	20100901	20100929	Y
		//		20100728	20100928	20100929	20100930	N
		//		20100728	20100728	20100728	20100929	Y
		//		20100728	20100928	20100728	201007_INFINITY	N
		//		20100728	20100928	20100828	201008_INFINITY	N
		//		20100728	20100928	20100928	201009_INFINITY	Y
		//		20100728	20100729	20100728	201007_INFINITY	Y
		//		20100728	20110928	20100728	201109_INFINITY	Y

		UDFRange evaluator = new UDFRange();
		assertTrue(evaluator.evaluate("20100727", "20100729", "20100728", "20100928").get());
		assertTrue(evaluator.evaluate("20100728", "20100729", "20100728", "20100928").get());
		assertTrue(evaluator.evaluate("20100729", "20100729", "20100728", "20100928").get());
		assertTrue(evaluator.evaluate("20100901", "20100928", "20100728", "20100928").get());
		assertTrue(evaluator.evaluate("20100901", "20100929", "20100728", "20100928").get());
		assertFalse(evaluator.evaluate("20100929", "20100930", "20100728", "20100928").get());
		assertTrue(evaluator.evaluate("20100728", "20100929", "20100728", "20100728").get());
		assertFalse(evaluator.evaluate("20100728", "201007_INFINITY", "20100728", "20100928").get());
		assertFalse(evaluator.evaluate("20100828", "201008_INFINITY", "20100728", "20100928").get());
		assertTrue(evaluator.evaluate("20100928", "201009_INFINITY", "20100728", "20100928").get());
		assertTrue(evaluator.evaluate("20100728", "201007_INFINITY", "20100728", "20100729").get());
		assertTrue(evaluator.evaluate("20100728", "201109_INFINITY", "20100728", "20110928").get());

	}
}
