package com.colin.test.containers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class MyClass1{
	public long id;
	public long scn;
	MyClass1(long id, long scn){
		this.id = id;
		this.scn = scn;
	}
	public String toString(){
		return "id: " + this.id +
		"\tscn: " + this.scn;
	}
}
public class LinkedListTest {
	public static void printList(List list){
		System.out.println("List: ");
		for(Iterator iter = list.iterator();iter.hasNext();){
			Object o = (Object)iter.next();
			System.out.println(o);
		}
	}
	public static void main(String[] args){
		LinkedList<MyClass1> ll = new LinkedList<MyClass1>();
		for(int i=10; i>0; i--)
			ll.add(new MyClass1(i,i+10000));
		printList(ll);
		for(int i=100; i<105; i++)
			ll.add(5+i-100, new MyClass1(i,i+10000));
		printList(ll);
		
	}
}
