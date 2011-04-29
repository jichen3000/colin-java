package com.colin.test.junit;

import junit.framework.TestCase;

public class TestForAnt extends TestCase{
	public void setUp(){
		System.out.println("setUp");
	}
	public void tearDown(){
		System.out.println("tearDown");
	}
	public void testForAnt(){
		System.out.println("one");
		assertEquals(true, true);
	}
	public void testFor(){
		System.out.println("two");
		assertEquals(true, true);
	}
}
