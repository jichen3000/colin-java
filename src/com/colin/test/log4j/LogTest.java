package com.colin.test.log4j;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogTest {
//	static Logger logger = Logger.getLogger("lt");
//	static Logger logger = Logger.getLogger(LogTest.class);
	static Logger logger = Logger.getLogger(LogTest.class.getName());
	public static void main(String[] args){
		
		PropertyConfigurator.configure("src/com/colin/test/log4j/log4j.prop");
//		PropertyConfigurator.configure("src/com/colin/test/log4j/log4j.prop4");
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
		logger.info("exit");
		
		A a = new A();
		a.doSomething();
		System.out.println("exit");
	}
}
