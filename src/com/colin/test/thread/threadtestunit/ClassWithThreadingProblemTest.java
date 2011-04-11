package com.colin.test.thread.threadtestunit;

import junit.framework.TestCase;


public class ClassWithThreadingProblemTest extends TestCase {
	public void testTwoThreadShouldFailEventually() throws InterruptedException{
		final ClassWithThreadingProblem classWithThreadingProblem = 
			new ClassWithThreadingProblem();
		
		Runnable runnable = new Runnable(){
			public void run(){
				classWithThreadingProblem.takeNextId();
			}
		};
		
		for (int i = 0; i < 500000; i++) {
	    int startingId = classWithThreadingProblem.nextId;
	    int expectedResult = startingId + 2;
	    
	    Thread t1 = new Thread(runnable);
	    Thread t2 = new Thread(runnable);
	    t1.start();
	    t2.start();
	    t1.join();
	    t2.join();
	    
	    int endingId = classWithThreadingProblem.nextId;
	    assertEquals(expectedResult,endingId);
	    
    }
	}
}
