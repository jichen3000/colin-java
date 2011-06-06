package com.colin.test.thread;

public class InsideLock {
	
	public synchronized void putsA(){
		System.out.println("A start...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("A end...");
		
	}
	public synchronized void putsB(){
		System.out.println("B start...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("B end...");
		
	}
	
	public static void main(String[] args) throws InterruptedException{
		final InsideLock insideLock = new InsideLock();
		Thread threadA = new Thread(){
			public void run(){
				insideLock.putsA();
			}
		};
		Thread threadB = new Thread(){
			public void run(){
				insideLock.putsB();
			}
		};
		threadA.start();
		threadB.start();
		threadA.join();
		threadB.join();
		System.out.println("ok");
	}

}
