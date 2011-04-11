package com.colin.test.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskWithResult implements Callable<String>{
	private int id;
	public TaskWithResult(int id){
		this.id = id;
	}
	public String call(){
		return "result of TaskWithResult:" + id;
	}
	
	public void main(String[] args){
		ExecutorService exec = Executors.newCachedThreadPool();
		
	}

}
