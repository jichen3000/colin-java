package com.colin.test.generics;

public class GenericMethods {
	public <T> void f(T x){
		System.out.println(x.getClass().getName());
	}
	public <T extends Comparable> T min(T[] tArr){
		if (tArr==null || tArr.length==0)
			return null;
		T min = tArr[0];
		for(T element : tArr){
			if (min.compareTo(element)>0)
				min = element;
		}
		return min;
	}
//	public <T,U> T ff(U x){
//		if (x instanceof String){
//			return new T(1);
//		}
//		return null;
//	}
	public static void main(String[] args){
		GenericMethods gm = new GenericMethods();
		gm.f("");
		gm.f(1);
		gm.f(1.0);
		gm.f(1.0F);
		gm.f('c');
		gm.f(gm);
	}
}
