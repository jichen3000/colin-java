package com.colin.test.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropTest {
	public static void main(String[] args){
		String fileName = "src/com/colin/test/properties/props";
		try {
	    InputStream in = new BufferedInputStream(new FileInputStream(fileName));
	    Properties props = new Properties();
	    props.load(in);
//	    Enumeration en = props.propertyNames();
	    Enumeration<?> en = props.propertyNames();
	    while (en.hasMoreElements()) {
	      String key = (String) en.nextElement();
	      String property = props.getProperty(key);
	      System.out.println(key+":"+property);
      }
	    
	    int readStep = Integer.parseInt(props.getProperty("readStep"));
	    System.out.println("readStep:"+readStep);
    } catch (FileNotFoundException e) {
	    e.printStackTrace();
    } catch (IOException e) {
	    e.printStackTrace();
    }
		
	}
}
