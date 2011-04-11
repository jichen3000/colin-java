package com.colin.test.jvm;

public class NewClass implements Cloneable{
	NewClass(){
		System.out.println("Created by invoking newInstance()!");
	}
	NewClass(String msg){
		System.out.println(msg);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, 
			InstantiationException, IllegalAccessException, CloneNotSupportedException{
		NewClass nc1= new NewClass("created with new.");
		
		Class myClass=Class.forName("com.colin.test.jvm.NewClass");
		NewClass nc2 = (NewClass) myClass.newInstance();
		
		NewClass nc3 = (NewClass) nc2.clone();
	}
}
