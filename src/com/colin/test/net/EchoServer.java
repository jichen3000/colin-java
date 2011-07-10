package com.colin.test.net;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServer{
    public static void main(String[] args){
	try{
	    ServerSocket server = new ServerSocket(8888);
	    Socket inSocket = server.accept();
	    try{
		InputStream inStream = inSocket.getInputStream();
		OutputStream outStream = inSocket.getOutputStream();

		Scanner in = new Scanner(inStream);
		PrintWriter out = new PrintWriter(outStream,true);

		out.println("Hello! Enter bye to exit.");

		boolean done = false;
		while(!done && in.hasNextLine()){
		    String line = in.nextLine();
		    out.println("Echo: " + line);
		    if(line.trim().equals("bye")){
			done = true;
		    }
		}
	    }finally{
		inSocket.close();
	    }
	}catch(IOException e){
	    e.printStackTrace();
	}
    }
}