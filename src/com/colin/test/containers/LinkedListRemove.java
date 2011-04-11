package com.colin.test.containers;

import java.util.LinkedList;

public class LinkedListRemove {
	private int id;
	public LinkedListRemove(int id){
		this.id = id;
	}
	
	public String toString(){
		return "LinkedListRemove id: "+id;
	}
	public static void main(String[] args){
		LinkedList<LinkedListRemove> ll = new LinkedList<LinkedListRemove>();
		for(int i = 0; i<10; i++){
			ll.add(new LinkedListRemove(i));
		}
		
		ll.remove(3);
		
		System.out.println("first:");
		for(LinkedListRemove item: ll)
			System.out.println(item);
		
		System.out.println("second:");
		for(int i=0; i<ll.size(); i++){
			System.out.println("index£º"+i+"\t"+ll.get(i));
		}
		
	}
}
