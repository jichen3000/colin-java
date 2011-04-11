package com.colin.test.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.PropertyConfigurator;


// java -cp ../lib
public class LogTest {
	private static Logger logger = LoggerFactory.getLogger(LogTest.class);
	public static void main(String[] args) {
		// 可以修改log4j的配置文件，但是在slf4j中没有对应的地方可以修改配置文件。
		PropertyConfigurator.configure("conf/log4j.properties");
	  logger.info("mm starting ...");
	  System.out.println("ok");
	}
}
