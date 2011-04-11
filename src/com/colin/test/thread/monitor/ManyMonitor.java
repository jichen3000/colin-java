package com.colin.test.thread.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



class MonitorOne{
	public synchronized void doSomething(int threadId,int waitSeconds){
		try {
			System.out.println("thread("+threadId+")will sleep:"+10000);
			Thread.sleep(5000);
			System.out.println("thread("+threadId+")end sleep:"+10000);
			System.out.println("thread("+threadId+")will wait:"+waitSeconds);
	    this.wait();
			System.out.println("thread("+threadId+")wake up:"+waitSeconds);
    } catch (InterruptedException e) {
	    e.printStackTrace();
    }
	}
	public synchronized void doSomething2(int threadId,int waitSeconds){
		try {
			System.out.println("222thread("+threadId+")will sleep:"+10000);
			Thread.sleep(5000);
			System.out.println("222thread("+threadId+")end sleep:"+10000);
			System.out.println("222thread("+threadId+")will wait:"+waitSeconds);
	    this.wait();
			System.out.println("222thread("+threadId+")wake up:"+waitSeconds);
    } catch (InterruptedException e) {
	    e.printStackTrace();
    }
	}
	public synchronized void getUp(){
		this.notify();
		System.out.println("get up");
	}
	public synchronized void getUp2(){
		this.notify();
		System.out.println("222get up");
	}
	
}
class WaitThread implements Runnable{
	private static int taskCount = 0;
	private final int id = taskCount++;
	private MonitorOne one;
	public WaitThread(MonitorOne one){
		this.one=one;
	}
	public void run(){
		
		one.doSomething(id,100);
	}
}
class WaitThreadTwo implements Runnable{
	private static int taskCount = 0;
	private final int id = taskCount++;
	private MonitorOne one;
	public WaitThreadTwo(MonitorOne one){
		this.one=one;
	}
	public void run(){
		
		one.doSomething2(id,100);
	}
}
public class ManyMonitor {
	public static void main(String[] args) throws InterruptedException {
		MonitorOne one = new MonitorOne();
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0; i<2; i++){
			exec.execute(new WaitThread(one));
		}
		for(int i=0; i<2; i++){
			exec.execute(new WaitThreadTwo(one));
		}
		exec.shutdown();
		Thread.sleep(6000);
		for(int i=0; i<2; i++){
			one.getUp();
		}
		for(int i=0; i<2; i++){
			one.getUp2();
		}
		
		System.out.println("ok");
  }
}
