package com.colin.test.thread.join;

public class Sleeper extends Thread{
	private int duration;
	public Sleeper(String name, int sleepTime){
		super(name);
		duration = sleepTime;
		start();
	}
	public void run() {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			System.out.println(getName() + " was interrupted. "+
					"isInterrupted():"+isInterrupted());
			return;
		}
		System.out.println(getName() + " has awakened");
	}
	
	
}
