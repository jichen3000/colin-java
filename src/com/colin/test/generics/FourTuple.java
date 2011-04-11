package com.colin.test.generics;

public class FourTuple<A,B,C,D> {
	public final A first;
	public final B second;
	public final C third;
	public final D forth;
	
	public FourTuple(A a, B b, C c, D d){
		this.first = a;
		this.second = b;
		this.third = c;
		this.forth = d;
	}
	
	public String toString(){
		return "first: " + first +
			"\tsecond: " + second +
			"\tthird: " + third +
			"\tforth: " + forth;
	}
	public static void main(String[] args){
		FourTuple<String,Integer,Long,String> ft = new FourTuple<
			String,Integer,Long,String>("a",new Integer(0),new Long(100),"b");
		System.out.println(ft);
	}
}
