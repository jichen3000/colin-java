package com.colin.test.containers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class PriorityQueueDemo {
	public static void main(String[] args){
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		Random rand = new Random(47);
		for(int i=0; i<10; i++){
			pq.offer(rand.nextInt(i+10));
		}
		QueueDemo.printQ(pq);
		
		List<Integer> ints = Arrays.asList(25, 22, 30,4,6,4);
		pq = new PriorityQueue<Integer>(ints);
		QueueDemo.printQ(pq);
		
		String fact = "EDUCATION SHOULD ESCHEW OBFUSCATION";
		List<String> strings = Arrays.asList(fact.split(""));
		PriorityQueue<String> spq = new PriorityQueue<String>(strings);
		QueueDemo.printQ(spq);
		
		spq = new PriorityQueue<String>(strings.size(), Collections.reverseOrder());
		spq.addAll(strings);
		QueueDemo.printQ(spq);
		
		Set<Character> charSet = new HashSet<Character>();
		for(char c : fact.toCharArray())
			charSet.add(c);
		PriorityQueue<Character> cpq = 
			new PriorityQueue<Character>(charSet);
		QueueDemo.printQ(cpq);
	}
}
