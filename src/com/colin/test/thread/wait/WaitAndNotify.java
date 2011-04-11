package com.colin.test.thread.wait;

import java.util.LinkedList;

class Source{
	int MAXCOUNT=2;
	private LinkedList<Long> list;
	public Source(){
		this.list = new LinkedList<Long>();
	}
	public synchronized void notifyThenWait() throws InterruptedException{
		System.out.println("s:notifyThenWait will notify(");
		notify();
		System.out.println("s:notifyThenWait will wait(");
		wait();
		System.out.println("s:notifyThenWait end wait(");
	}
	public synchronized void justWait() throws InterruptedException{
		System.out.println("s:justWait will wait(");
		wait();
		System.out.println("s:justWait end wait(");
	}
	public synchronized Long getElseWait() throws InterruptedException{
		Long result = this.list.poll();
		while(result==null){
			System.out.println("s:get will wait("+this.list.size());
			wait();
			result = this.list.poll();
		}
		System.out.println("s:get will notify("+this.list.size());
		notify();
		return result;
	}
	public synchronized void addElseWait(Long l) throws InterruptedException{
		while(this.list.size()>=MAXCOUNT){
			System.out.println("s:add will wait("+this.list.size());
			wait();
		}
		System.out.println("s:add will notify("+this.list.size());
		this.list.offer(l);
		notify();
	}
}
class ReadSource implements Runnable{
	private Source s;
	public ReadSource(Source source){
		this.s = source;
	}
	public void run(){
		try {
			s.justWait();
//			for (int i = 0; i < 20; i++) {
//	      Long item = s.getElseWait();
//	      System.out.println("r::"+item);
//	    }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
	}
}
class WriteSource implements Runnable{
	private Source s;
	public WriteSource(Source source){
		this.s = source;
	}
	public void run(){
		try {
			s.notifyThenWait();
//			for (int i = 0; i < 10; i++) {
//	      s.addElseWait(new Long(i));
//				System.out.println("w:"+i);
//	    }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
		
	}
}
public class WaitAndNotify {
	public static void main(String[] args) throws InterruptedException {
	  Source s = new Source();
	  ReadSource read = new ReadSource(s);
	  WriteSource write = new WriteSource(s);
	  
	  Thread tr = new Thread(read);
	  Thread tw = new Thread(write);


	  
	  tr.start();
	  tw.start();
	  
	  tr.join();
	  tw.join();
	  
	  System.out.println("ok");
	  
  }
}
