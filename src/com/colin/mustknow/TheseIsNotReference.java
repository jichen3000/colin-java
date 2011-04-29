package com.colin.mustknow;

import java.util.HashMap;
import java.util.Map;
class MyObject{
	public Long id;
	public String name;
	public String toString(){
		return "id:"+id+", name:"+name;
	}
}
public class TheseIsNotReference {
	public static void integerIsNotReference(){
		Map testMap = new HashMap();
		testMap.put("jc",new Integer(1));
		System.out.print("before change:");
		System.out.println(testMap);
		Integer jcInt=(Integer)testMap.get("jc");
		jcInt++;
		System.out.print("change but before re put:");
		System.out.println(testMap);
		testMap.put("jc", jcInt);
		System.out.print("change and after re put:");
		System.out.println(testMap);
	}
	public static void longIsNotReference(){
		Map testMap = new HashMap();
		testMap.put("jc",new Long(1));
		System.out.print("before change:");
		System.out.println(testMap);
		Long jc=(Long)testMap.get("jc");
		jc++;
		System.out.print("change but before re put:");
		System.out.println(testMap);
		testMap.put("jc", jc);
		System.out.print("change and after re put:");
		System.out.println(testMap);
	}
	public static void stringIsNotReference(){
		Map testMap = new HashMap();
		testMap.put("jc","11");
		System.out.print("before change:");
		System.out.println(testMap);
		String jc=(String)testMap.get("jc");
		jc=jc+"1";
		System.out.print("change but before re put:");
		System.out.println(testMap);
		testMap.put("jc", jc);
		System.out.print("change and after re put:");
		System.out.println(testMap);
	}
	public static void objectIsReference(){
		Map testMap = new HashMap();
		MyObject myObject=new MyObject();
		myObject.id = new Long(1);
		myObject.name = "jc";
		testMap.put("jc",myObject);
		System.out.print("before change:");
		System.out.println(testMap);
		MyObject jc=(MyObject)testMap.get("jc");
		jc.id++;
		System.out.print("change but before re put:");
		System.out.println(testMap);
		testMap.put("jc", jc);
		System.out.print("change and after re put:");
		System.out.println(testMap);
	}
	
	public static void main(String[] args){
		System.out.println("objectIsReference");
		objectIsReference();
		System.out.println("integerIsNotReference");
		integerIsNotReference();
		System.out.println("longIsNotReferences");
		longIsNotReference();
		System.out.println("stringIsNotReference");
		stringIsNotReference();
		System.out.println("ok");
	}
}
