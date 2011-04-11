package com.mchz.henry.stream.apply.util;

public class BinUtil {
	public static long bytesToLong(byte[] bytes, int index, int length) {
		switch(length) {
		case 4: return bytesToLongSize4(bytes,index); 
		case 2: return bytesToLongSize2(bytes,index); 
		case 1: return bytesToLongSize1(bytes,index); 
		default: return bytesToLongOthers(bytes,index,length);
		}
	}
	public static long bytesToLongSize4(byte[] bytes, int index) {
		return ((long) (bytes[index ] & 0xff) << 24) |
		((long) (bytes[index + 1] & 0xff) << 16) |
		((long) (bytes[index + 2] & 0xff) << 8) |
		((long) (bytes[index + 3] & 0xff));
	}
	public static int bytesToIntSize4(byte[] bytes, int index) {
		return ((int) (bytes[index ] & 0xff) << 24) |
		((int) (bytes[index + 1] & 0xff) << 16) |
		((int) (bytes[index + 2] & 0xff) << 8) |
		((int) (bytes[index + 3] & 0xff));
	}
	public static long bytesToLongSize2(byte[] bytes, int index) {
		return ((long) (bytes[index ] & 0xff) << 8) |
			((long) (bytes[index + 1] & 0xff));
	}
	public static int bytesToIntSize2(byte[] bytes, int index) {
		return ((int) (bytes[index ] & 0xff) << 8) |
		((int) (bytes[index + 1] & 0xff));
	}
	public static long bytesToLongSize1(byte[] bytes, int index) {
		return ((long) (bytes[index ] & 0xff));
	}
	public static long bytesToLongOthers(byte[] bytes, int index, int length) {
		long result = 0;
		for (int i = 0; i < length; i++) {
			result = result | ((long) (bytes[index + i] & 0xff) << 8 * (length - i - 1));
		}
		return result;
	}
	public static int[] anaRecordSize(byte[] bytes, int recordCount, int recordSize){
		int[] result = new int[recordCount];
		for(int i=0; i < recordCount; i++){
			result[i] = bytesToIntSize2(bytes, i*recordSize);
		}
		return result;
	}
}
