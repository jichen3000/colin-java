package com.colin.test.generics;

public class Pair<T> {
	private T first;
	private T second;
	public Pair(T first, T second) {
	  super();
	  this.first = first;
	  this.second = second;
  }
	public Pair(){
		this.first = null;
		this.second = null;
	}
	public T getFirst() {
  	return first;
  }
	public void setFirst(T first) {
  	this.first = first;
  }
	public T getSecond() {
  	return second;
  }
	public void setSecond(T second) {
  	this.second = second;
  }
	
	public static Pair<String> minmax(String[] strArr){
		if(strArr == null || strArr.length==0)
			return null;
		String min = strArr[0];
		String max = strArr[0];
		for (String element : strArr) {
	    if (min.compareTo(element)>0)
	    	min = element;
	    if (max.compareTo(element)<0)
	    	max = element;
    }
		return new Pair<String>(min,max);
	}
	
	public static void main(String[] args) {
	  String[] words = {"aa","zz","11","99"};
	  Pair<String> mm = Pair.minmax(words);
	  System.out.println("min="+mm.getFirst());
	  System.out.println("max="+mm.getSecond());
  }
}
