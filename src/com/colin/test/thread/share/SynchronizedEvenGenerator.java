package com.colin.test.thread.share;

public class SynchronizedEvenGenerator extends IntGenerator{
	private int currentEvenValue = 0;
	public synchronized int next() {
		++currentEvenValue;
		Thread.yield();
		++currentEvenValue;
		return currentEvenValue;
	}
	public static void main(String[] args){
		EvenChecker.test(new SynchronizedEvenGenerator(),100);
		System.out.println("ok");
	}

}
