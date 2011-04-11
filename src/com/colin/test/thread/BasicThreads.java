package com.colin.test.thread;

import com.colin.test.thread.LiftOff;

public class BasicThreads {
//	public static void main(String[] args){
//		Thread t = new Thread(new LiftOff());
//		t.start();
//		System.out.println("Waiting for Lift Off!");
//	}
	public static void main(String[] args){
		for(int i=0; i<5; i++){
			Thread t = new Thread(new LiftOff());
			t.start();
		}
		System.out.println("Waiting for Lift Off!");
	}
}
