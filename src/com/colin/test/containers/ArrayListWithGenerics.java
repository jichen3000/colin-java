package com.colin.test.containers;

import java.util.ArrayList;


public class ArrayListWithGenerics {
	public static void main(String[] args){
		System.out.println("start...");
		ArrayList<Apple> apples = new ArrayList<Apple>();
		for (int i = 0; i < 3; i++) {
			apples.add(new Apple());
		}
//		apples.add(new Orange());
		for (int i=0; i<3; i++){
			System.out.println(apples.get(i).id());
		}
		for(Apple c : apples){
			System.out.println(c.id());
		}
		
	}
}
