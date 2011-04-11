package com.colin.test.thread;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.colin.clientserver.MessageUtils;

class SimpleClient implements Runnable{
//	private int port;
//	private Map receivedMessageMap;
	private static int taskCount = 0;
	private final int id = taskCount++;
	public SimpleClient() {
  }

	@Override
  public void run() {
		System.out.println(id+" start"+System.currentTimeMillis());
		try {
	    Thread.sleep(5000);
    } catch (InterruptedException e) {
	    e.printStackTrace();
    }
		System.out.println(id+" end  "+System.currentTimeMillis());
  }
	
}
public class CachedThreadPoolExec {
	public static void main(String[] args) {
	  ExecutorService exec = Executors.newCachedThreadPool();
	  for (int i = 0; i < 2; i++) {
		  exec.execute(new SimpleClient());
    }
	  exec.shutdown();
  }
}
