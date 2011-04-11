package com.colin.test.generics;

public class Holder3<T> {
	private T a;
	public Holder3(T a){
		this.a = a;
	}
	public void set(T a){
		this.a = a;
	}
	public T get(){
		return this.a;
	}
	public static void main(String[] args){
		Holder3<Long> h3= new Holder3<Long>(new Long(3));
		Long a = h3.get();
		System.out.println(a);
	}
}
