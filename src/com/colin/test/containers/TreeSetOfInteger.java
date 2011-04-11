package com.colin.test.containers;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetOfInteger {
	public static void main(String[] args){
		Random rand = new Random(47);
//		SortedSet<Integer> intset = new TreeSet<Integer>();
		TreeSet<Integer> intset = new TreeSet<Integer>();
		for (int i=0; i<10; i++){
			intset.add(rand.nextInt(10000));
		}
		System.out.println(intset);
		Integer longtmp = intset.lower(-1);
		System.out.println(longtmp);
	}
}
