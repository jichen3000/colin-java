package com.colin.test.reflecttest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class Parent{
	private String myName;
	public String myCount;
	public String getMyName(){
		return myName;
	}
	private void getHis(){
		
	}
}

public class Simple extends Parent{
	private String name;
	public int count;
//	public String getName(){
//		return name;
//	}
	private String getPrivateName(){
		return "colin";
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException{
		Class simpleClass = Class.forName("com.colin.test.reflecttest.Simple");
		System.out.printf("class is  %s%n", simpleClass);
		System.out.printf("getFields  %n");
		for(Field field : simpleClass.getFields()){
			System.out.printf("  field  %s%n", field);
		}
		System.out.printf("getDeclaredFields  %n");
		for(Field field : simpleClass.getDeclaredFields()){
			System.out.printf("  field  %s%n", field);
		}
		System.out.printf("getMethods  %n");
		for(Method method : simpleClass.getMethods()){
			System.out.printf("  method  %s%n", method);
		}
		System.out.printf("getDeclaredMethods  %n");
		for(Method method : simpleClass.getDeclaredMethods()){
			System.out.printf("  method  %s%n", method);
		}
		
		Object mySimple = simpleClass.newInstance();
		
		System.out.println("get and set public field:");
		Field countField = simpleClass.getDeclaredField("count");
		countField.set(mySimple, 100);
		System.out.println("  field count value:"+countField.getInt(mySimple));
		
		System.out.println("get and set private field:");
		Field nameField = simpleClass.getDeclaredField("name");
		try {
			nameField.set(mySimple, "jack");
			String name = (String) nameField.get(mySimple);
		} catch (Exception e) {
			System.out.println("  name is private field, please call setAccessible first!");
			e.printStackTrace();
		}
		
//		nameField.set(mySimple, 100);
//		System.out.println("  field count value:"+nameField.getInt(mySimple));
		
		
		System.out.println("ok");
	}
}
