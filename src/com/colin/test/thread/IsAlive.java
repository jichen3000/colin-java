package com.colin.test.thread;

import java.util.ArrayList;
class MyThread implements Runnable{
	private static int taskCount = 0;
	private final int id = taskCount++;

	@Override
  public synchronized void run() {
		System.out.println(id+" run start");
		try {
	    wait();
    } catch (InterruptedException e) {
	    e.printStackTrace();
    }
		System.out.println(id+" run end");
  }
}

public class IsAlive {
	public static void main(String[] args) throws InterruptedException{
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		for(int i=0; i<2; i++){
			Thread t = new Thread(new MyThread());
			threadList.add(t);
			t.start();
		}
		System.out.println("join!");
		for(Thread thread : threadList){
			
			System.out.println(thread.isAlive());
			thread.join();
		}
		System.out.println("ok!");
	}

}
