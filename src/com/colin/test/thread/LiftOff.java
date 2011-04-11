package com.colin.test.thread;

public class LiftOff implements Runnable{
	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;
	public LiftOff(){
		
	}
	public LiftOff(int countDown){
		this.countDown = countDown;
	}
	public String status(){
		return "#" + id + "(" + (countDown > 0 ? countDown:"Lift Off!") +"),";
	}
	public void run(){
		while(countDown-- > 0){
			System.out.print(status());
			Thread.yield();
		}
		System.out.println("");
	}
	
	public static void main(String[] args){
		LiftOff lf = new LiftOff();
		lf.run();
	}
	
}
