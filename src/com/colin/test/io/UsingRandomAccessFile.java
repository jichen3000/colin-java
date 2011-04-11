package com.colin.test.io;

import java.io.IOException;
import java.io.RandomAccessFile;

public class UsingRandomAccessFile {
	static String filename = "D:/tmp/rtest.dat";
	static void display() throws IOException{
		RandomAccessFile rf = new RandomAccessFile(filename,"r");
		for(int i=0; i<7; i++)
			System.out.println("Value "+i+": "+rf.readLong());
		System.out.println(rf.readUTF());
		rf.close();
	}
	
	public static void main(String[] args) throws IOException{
		RandomAccessFile rf = new RandomAccessFile(filename,"rw");
		for(int i=0; i<7; i++)
			rf.writeLong(i*16);
		rf.writeUTF("The end of the file");
		rf.close();
		display();
		System.out.println("ok");
		
		rf = new RandomAccessFile(filename,"rw");
		rf.seek(5*8);
		rf.writeLong(255);
		rf.close();
		display();
	}
	
}
