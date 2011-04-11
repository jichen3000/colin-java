package com.colin.test.thread.wait;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class RecordCollection{
	private LinkedList<String> records;
	private LinkedList<String> readedRecords;
	public LinkedList<String> getReadedRecords() {
		return readedRecords;
	}
	public LinkedList<String> getWriteRecords() {
		return writeRecords;
	}
	private LinkedList<String> writeRecords;
	public RecordCollection(){
		this.records = new LinkedList<String>();
		this.readedRecords = new LinkedList<String>();
		this.writeRecords = new LinkedList<String>();
	}
	public synchronized String writeRecord(String record){
		System.out.println("  writeRecord{");
		this.records.offer(record);
		this.writeRecords.offer(record);
		notifyAll();
		System.out.println("  writeRecord}");
		return record;
	}
	public synchronized String readRecord() throws InterruptedException{
		System.out.println("  readRecord{");
		String record = this.records.poll();
		while(true){
			if(record==null){
				System.out.println("      wait{");
				wait();
				System.out.println("      wait}");
			}else{
				System.out.println("  readRecord}");
				this.readedRecords.offer(record);
				return record;
			}
			record = this.records.poll();
		}
//		return record;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class WaitTest {
	public static void main(String[] args) throws InterruptedException {
		RecordCollection rc = new RecordCollection();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Writer("w1-",rc));
		exec.execute(new Writer("w2-",rc));
		exec.execute(new Reader("r1-",rc));
//		Thread.sleep(1000);
		TimeUnit.SECONDS.sleep(25);
		exec.shutdownNow();
		System.out.println("writed count:"+rc.getWriteRecords().size());
		System.out.println("readed count:"+rc.getWriteRecords().size());
		System.out.println("ok");
	}
}
