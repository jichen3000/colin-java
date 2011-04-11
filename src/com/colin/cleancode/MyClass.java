package com.colin.cleancode;

public class MyClass implements MyInterface {

	@Override
	public void setMyName(){
		System.out.println("myname");
	}
	
	public static void main(String[] args) throws Exception {
	  MyInterface my = new MyClass();
	  my.setMyName();
  }

}
