package com.colin.test.io;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

public class BasicFileOutput{
	static String file = "D:/tmp/basicfile.txt";
	public static void main(String[] args) throws IOException{
		String sfile = "src/com/colin/test/io/BasicFileOutput.java";
		BufferedReader in = new BufferedReader(
				new StringReader(BufferedInputFile.read(sfile)));
		PrintWriter out = new PrintWriter(new FileWriter(file));
		int lineCount =1;
		String s;
		while((s = in.readLine())!=null)
			out.println(lineCount++ +": "+s);
		out.close();
		System.out.println(BufferedInputFile.read(file));
		in.close();
	}
}
