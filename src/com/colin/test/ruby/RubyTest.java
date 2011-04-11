package com.colin.test.ruby;

public class RubyTest {
	public void check(){
		System.out.println("check!");
	}
	
	private void privateCheck(){
		System.out.println("private check");
	}
	
	private int getInt(){
		System.out.println("get int");
		return 1;
	}
	private int add(int a1, int a2){
		return a1+a2;
	}
	private Colin addColin(Colin colin){
		colin.setName(colin.getName()+" test");
		return colin;
	}
}