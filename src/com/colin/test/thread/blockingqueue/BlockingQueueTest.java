package com.colin.test.thread.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;



class RecordCollection{
	
	private BlockingQueue<String> records;
	private long readedCount;
	private long writedCount;
	public long getReadedCount(){
		return this.readedCount;
	}
	public long getWritedCount(){
		return this.writedCount;
	}
	public RecordCollection(){
		this.records = new LinkedBlockingQueue<String>();
		this.readedCount = 0;
		this.writedCount = 0;
	}
	public String writeRecord(String record){
		System.out.println("  writeRecord{");
		this.records.offer(record);
		this.writedCount++;
		System.out.println("  writeRecord}");
		return record;
	}
	public String readRecord() throws InterruptedException{
		System.out.println("  readRecord{");
		String result = this.records.take();
		this.readedCount++;
		System.out.println("  readRecord}");
		return result;
	}
}

class Reader implements Runnable{
	private RecordCollection rc;
	private long id;
	private String name;
	public Reader(String name,RecordCollection rc){
		this.rc = rc;
		this.name = name;
		this.id = 0;
	}
	private void doRead() throws InterruptedException{
//		Thread.sleep(1);
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println(this.name+" doRead over!");
	}
	public void run(){
		try {
			while(true){
				System.out.println(this.name+" record:"+rc.readRecord());
				doRead();
				this.id++;
			}
		} catch (InterruptedException e) {
			System.out.println(this.name+this.id+" has interrupted!");
		}
			
	}
}

class Writer implements Runnable{
	private RecordCollection rc;
	private long id;
	private String name;
	public Writer(String name,RecordCollection rc){
		this.rc = rc;
		this.name = name;
		this.id = 0;
	}
	private void doWrite() throws InterruptedException{
//		Thread.sleep(3);
		TimeUnit.MICROSECONDS.sleep(300);
		System.out.println(this.name+" doWrite over!");
	}
	public void run(){
		try {
			while(id<100){
				doWrite();
				System.out.println(this.name+" record:"+rc.writeRecord(this.name+this.id));
				this.id++;
			}
		} catch (InterruptedException e) {
//			e.printStackTrace();
			System.out.println(this.name+this.id+" has interrupted!");
		}
	}
}

public class BlockingQueueTest {
	public static void main(String[] args) throws InterruptedException {
		RecordCollection rc = new RecordCollection();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Writer("w1-",rc));
		exec.execute(new Writer("w2-",rc));
		exec.execute(new Reader("r1-",rc));
		exec.execute(new Reader("r2-",rc));
		exec.execute(new Reader("r3-",rc));
//		Thread.sleep(1000);
		TimeUnit.SECONDS.sleep(25);
		exec.shutdownNow();
		System.out.println("writed count:"+rc.getWritedCount());
		System.out.println("readed count:"+rc.getReadedCount());
		System.out.println("ok");
	}
}
