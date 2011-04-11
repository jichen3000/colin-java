package com.colin.test.integer;

public class IntTest {
	public static int getNextNo(int current){
		int result = current+1;
		if(result<=0)
			result = 1;
		return result;
	}
	public static long getNextNo(long current){
		long result = current+1;
		if(result<=0)
			result = 1;
		return result;
	}
//	public static <S> getNextNo(<S> current){
//		<S> result = current+1;
//		if(result<=0)
//			result = 1;
//		return result;
//	}
	
	public static void main(String[] args){
//		int i = 2147483646;
		long i = 1;
		long result= 0;
		while(i>0){
			result = IntTest.getNextNo(i);
			System.out.println(result-1);
			i = i * 2;
		}
	}
}
