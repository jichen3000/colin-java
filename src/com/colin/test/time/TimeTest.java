package com.colin.test.time;

import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTest {
	public static void main(String[] args) throws InterruptedException {
	  Date start = new Date(); 
	  System.out.println(start);
	  
	  Date end = new Date();
	  System.out.println(end);
	  // milliseconds 
	  System.out.println(end.getTime()-start.getTime());
	  
	  System.out.println((new GregorianCalendar()).getTime());
	  
	  long startMillisec = System.currentTimeMillis();
	  System.out.println(startMillisec);
	  Thread.sleep(100);
	  long endMillisec = System.currentTimeMillis();
	  System.out.println(endMillisec);
	  System.out.println(endMillisec-startMillisec);
	  
  }
}
