package com.colin.clientserver.nonthreaded;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import com.colin.clientserver.MessageUtils;

class SimpleClient implements Runnable{
	private int port;
	private Map receivedMessageMap;
	private static int taskCount = 0;
	private final int id = taskCount++;
	public SimpleClient(int port, Map receivedMessageMap) {
		this.port = port;
		this.receivedMessageMap = receivedMessageMap;
  }

	@Override
  public void run() {
	  Socket socket;
    try {
    	System.out.println("client start!");
	    socket = new Socket("localhost", port);
	    String message = "send "+id;
			MessageUtils.sendMessage(socket, message);
			String recievedmessage = MessageUtils.getMessage(socket);
			this.receivedMessageMap.put(id, recievedmessage);
			socket.close();
    	System.out.println("client end!");
    } catch (UnknownHostException e) {
	    e.printStackTrace();
    } catch (IOException e) {
	    e.printStackTrace();
    }
  }
	
}
public class ServerTest{
//	public class ServerTest extends TestCase {
	static final int SOCKET_PORT=4321;
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
		int millisecondsTimeout = 5000;
		Map receiveHashMap = new ConcurrentHashMap();
		int connectTimes = 2;
		ExecutorService exec = Executors.newCachedThreadPool();
		Server server = new Server(SOCKET_PORT,millisecondsTimeout);
		exec.execute(server);
		ExecutorService exec1 = Executors.newCachedThreadPool();
		for (int i = 0; i < connectTimes; i++) {
			exec1.execute(new SimpleClient(SOCKET_PORT,receiveHashMap));
    }
		
//		exec.awaitTermination(millisecondsTimeout, TimeUnit.MILLISECONDS);
		exec1.shutdown();
		exec.shutdown();
//		Thread server = new Thread(new Server(SOCKET_PORT,millisecondsTimeout));
//		server.start();
//		for (int i = 0; i < connectTimes; i++) {
//			Thread client = new Thread(new SimpleClient(SOCKET_PORT,receiveHashMap));
//			client.start();
//	  }
//		server.join(millisecondsTimeout);
		System.out.println(receiveHashMap);
//		System.out.println("ok");
//		assertEquals("Processed: it is me!",receiveMessage);
	}
//	private void connectServer(int connectTimes) throws UnknownHostException, IOException {
//		ExecutorService exec = Executors.newCachedThreadPool();
//		for (int i = 0; i < connectTimes; i++) {
//			exec.execute(new SimpleClient(SOCKET_PORT,"send "+i));
//    }
//		exec.shutdown();
//  }
	
	
}
