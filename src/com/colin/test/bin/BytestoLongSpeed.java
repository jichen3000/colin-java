package com.colin.test.bin;

public class BytestoLongSpeed {
	public static long bytesToLong(byte[] bytes, int index, int length) {
		long result = 0;
		for (int i = 0; i < length; i++) {
			result = result | ((long) (bytes[index + i] & 0xff) << 8 * (length - i - 1));
		}
		return result;
	}
	public static long bytesToLong2(byte[] bytes, int index, int length) {
		switch(length) {
		case 4: return bytesToLongSize4(bytes,index); 
		case 2: return bytesToLongSize2(bytes,index); 
		case 1: return bytesToLongSize1(bytes,index); 
		default: return bytesToLong(bytes,index,length);
		}
	}
	public static long bytesToLongSize4(byte[] bytes, int index) {
		return ((long) (bytes[index ] & 0xff) << 24) |
		((long) (bytes[index + 1] & 0xff) << 16) |
		((long) (bytes[index + 2] & 0xff) << 8) |
		((long) (bytes[index + 3] & 0xff));
	}
	public static long bytesToLongSize2(byte[] bytes, int index) {
		return ((long) (bytes[index ] & 0xff) << 8) |
			((long) (bytes[index + 1] & 0xff));
	}
	public static long bytesToLongSize1(byte[] bytes, int index) {
		return ((long) (bytes[index ] & 0xff));
	}
	public static long bytesToLongSize42(byte[] bytes, int index) {
		return ((long) (bytes[index + 0] ) << 24) |
		((long) (bytes[index + 1] ) << 16) |
		((long) (bytes[index + 2] ) << 8) |
		((long) (bytes[index + 3] )) & 0xffffffff;
	}
	public static void test1(byte[] bytes){
		long index = 0;
		while (index<10000000){
			bytesToLong(bytes,0,4);
			index++;
		}
	}
	public static void test2(byte[] bytes){
		long index = 0;
		while (index<10000000){
			bytesToLong2(bytes,0,4);
			index++;
		}
	}
	public static void printBytes(byte[] bytes){
		for(byte b : bytes)
			System.out.print(b+"\t");
	}
	public static void main(String[] args) throws InterruptedException{
//		Thread.sleep(1000);
		byte[] bytes = new byte[4];
		bytes[3] = -122;
		printBytes(bytes);
		System.out.println(bytesToLong(bytes,0,4));
		System.out.println(bytesToLong2(bytes,0,4));
		long startTime = System.currentTimeMillis();
		test1(bytes);
		long endTime = System.currentTimeMillis();
		System.out.println();
		System.out.println(endTime - startTime);
		startTime = System.currentTimeMillis();
		test2(bytes);
		endTime = System.currentTimeMillis();
		System.out.println();
		System.out.println(endTime - startTime);
		
	}
}
