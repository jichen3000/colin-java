package com.colin.test.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerFromFile {
  public static void main(String[] args){
  	String fileName="src/com/colin/test/io/BasicFileOutput.java";
  	Scanner in;
    try {
	    in = new Scanner(new File(fileName));
	  	for (int i=0; i<5; i++){
	  		System.out.printf("No %d: %s%n",i,in.nextLine());
	  	}
    } catch (FileNotFoundException e) {
	    e.printStackTrace();
    }
  	System.out.println("ok");
  }
  
}
