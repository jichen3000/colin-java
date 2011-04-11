package com.colin.test.bin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class BinTest {
//  public static String saveBinFile(String filename,oracle.sql.ARRAY p_in) {
//	  String[] values = null;
//	  try {
//		  values = (String[])p_in.getArray();
//	  }catch (java.sql.SQLException e){
//		  e.printStackTrace();
//	  }
//	    
//	      FileOutputStream out=null;
//	    try {
//	      out = new FileOutputStream(filename);
//	    } catch (FileNotFoundException e) {
//	      e.printStackTrace();
//	    }
//	      for (int i=0; i<values.length; i++){
//	        try {
//	        out.write(values[i].getBytes());
//	      } catch (IOException e) {
//	        e.printStackTrace();
//	      }
//	      }
//	      try {
//	      out.close();
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    }
//	    
//	    return '0';
//	  }
	public static int saveBinFile(String filename,String values[]){
	    FileOutputStream out=null;
		try {
			out = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}
	    for (int i=0; i<values.length; i++){
	    	try {
				out.write(values[i].getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				return 1;
			}
	    }
	    try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}
	public static int saveBinFile(String filename,String value, int count){
		FileOutputStream out=null;
		try {
			out = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		}
		for (int i=0; i<count; i++){
			try {
				out.write(value.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				return 1;
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;
	}
	public static void main(String args[]) throws IOException{
		String filename = "D:/tmp/log/jbin";
//		String values[]={"abcdefg 12 hijk\n","abcdefg 12 hijk\n"};
//		BinTest.saveBinFile(filename, values);
		String value = "EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO"+"\n";
		Date start_time = new Date();
		System.out.println(start_time);
		BinTest.saveBinFile(filename, value,1000000);
		Date end_time = new Date();
		System.out.println(end_time);
		System.out.println(end_time.getTime()-start_time.getTime());
		System.out.println("filename:"+filename);
		System.out.println("ok");
	}
}
