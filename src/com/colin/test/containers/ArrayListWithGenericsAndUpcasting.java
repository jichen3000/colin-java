package com.colin.test.containers;

import java.util.ArrayList;

class GrannySmith extends Apple {}
class Gala extends Apple {}
class Fuji extends Apple {}

public class ArrayListWithGenericsAndUpcasting {
	public static void main(String[] args){
		ArrayList<Apple> apples = new ArrayList<Apple>();
		apples.add(new GrannySmith());
		apples.add(new Gala());
		apples.add(new Fuji());
		for(Apple c : apples)
			System.out.println(c);
	}
}
