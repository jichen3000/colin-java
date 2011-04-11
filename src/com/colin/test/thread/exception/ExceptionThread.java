package com.colin.test.thread.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExceptionThread implements Runnable{
	public void run(){
		throw new RuntimeException();
	}
	public static void main(String[] args){
		try{
			ExecutorService exec = Executors.newCachedThreadPool();
			exec.execute(new ExceptionThread());
		}catch(RuntimeException re){
			System.out.println("Exception has been hanled!");
		}
	}
}
