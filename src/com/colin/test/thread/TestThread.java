package com.colin.test.thread;

//public class TestThread extends Thread{
public class TestThread implements Runnable{
	long minPrime;
	private int countDown = 10;
	TestThread(long minPrime){
		this.minPrime = minPrime;
	}
	public String status(){
//		return "#"+id
		return "";
	}
	public void run(){
		while(countDown-- > 0){
			System.out.println(countDown);
		}
	}
	
	public static void main(String[] args){
		TestThread tt = new TestThread(3);
		tt.run();
		System.out.println("ok");
	}
}
