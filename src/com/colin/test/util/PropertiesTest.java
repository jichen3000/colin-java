package com.colin.test.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
	public static void main(String[] args) throws IOException{
		FileInputStream fis = new FileInputStream("src/com/colin/test/util/ors.conf");
		Properties prop = new Properties();
		prop.load(fis);
		prop.list(System.out);
		System.out.println(prop.getProperty("username"));
		System.out.println("ok");
	}
}
