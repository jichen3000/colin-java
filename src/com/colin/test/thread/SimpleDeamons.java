package com.colin.test.thread;

public class SimpleDeamons implements Runnable{
	public void run(){
		try {
			Thread.sleep(100);
			System.out.println(Thread.currentThread() + " " + this);
		} catch (InterruptedException e) {
			System.out.println("Sleep() interrupted!");
		}
	}
	public static void main(String[] args) throws Exception {
		for(int i =0; i <10; i++){
			Thread daemon = new Thread(new SimpleDeamons());
			daemon.setDaemon(true);
			daemon.start();
		}
		System.out.println("All daemons started!");
		Thread.sleep(172);
	}
}
