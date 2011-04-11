package com.colin.test.thread.share;

import java.util.ArrayList;

class ReadThread implements Runnable{
	public void run(){
		MyClass mc = ListShareTest.getMC();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mc.name="mm"+mc.id;
		
	}
}
class WriteThread implements Runnable{
	public void run(){
		
	}
}

class MyClass{
	public int id;
	public String name;
	public MyClass(int id, String name){
		this.id = id;
		this.name = name;
	}
}

public class ListShareTest {
	private static ArrayList<MyClass> cList=new ArrayList<MyClass>();
	public static synchronized void initList(){
		cList.add(new MyClass(1,"jc1"));
		cList.add(new MyClass(2,"jc2"));
		cList.add(new MyClass(3,"jc3"));
	}
	
	public static synchronized MyClass getMC(){
		MyClass result=null;
		for(MyClass mc : cList){
			result = mc;
			break;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args){
		ListShareTest.initList();
		
	}
}
