package com.mchz.henry.util;

public class ConvertUtil {
	private ConvertUtil() {

	}

	private static byte[] COMPARE_TABLE = new byte[] { (byte) 0x80, 0x40, 0x20,
			0x10, 0x8, 0x4, 0x2, 0x1 };

	public static long bytesToLong(byte[] bytes) {
		return bytesToLong(bytes, 0, bytes.length);
	}

	public static long bytesToLong(byte[] bytes, int index, int length) {
		long result = 0;
		for (int i = 0; i < length; i++) {
			result = result | ((long) (bytes[index + i] & 0xff) << 8 * i);
		}
		return result;
	}

	public static long bytesToLongBigEndian(byte[] bytes, int index, int length) {
		long result = 0;
		for (int i = 0; i < length; i++) {
			result = result
					| ((long) (bytes[index + i] & 0xff) << 8 * (length - i - 1));
		}
		return result;
	}

	public static long bytesToBlockNumber(final byte[] bytes, int index) {
		byte[] tmp = new byte[4];
		System.arraycopy(bytes, index, tmp, 0, 4);
		tmp[3] = 0;
		tmp[2] = (byte) (tmp[2] & 0x3f);
		return bytesToLong(tmp, 0, 4);
	}

	public static long bytesToBlockNumberBigEndian(final byte[] bytes, int index) {
		byte[] tmp = new byte[4];
		System.arraycopy(bytes, index, tmp, 0, 4);
		tmp[0] = 0;
		tmp[1] = (byte) (tmp[1] & 0x3f);
		return bytesToLongBigEndian(tmp, 0, 4);
	}

	public static byte[] longToBytes(long l) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (l >> 0);
		bytes[1] = (byte) (l >> 8);
		bytes[2] = (byte) (l >> 16);
		bytes[3] = (byte) (l >> 24);
		return bytes;
	}

	public static byte[] longToBytesBigEndian(long l) {
		byte[] bytes = new byte[4];
		bytes[3] = (byte) (l >> 0);
		bytes[2] = (byte) (l >> 8);
		bytes[1] = (byte) (l >> 16);
		bytes[0] = (byte) (l >> 24);
		return bytes;
	}

	public static void longToBytes(long l, byte[] longBytes) {
		longBytes[0] = (byte) (l >> 0);
		longBytes[1] = (byte) (l >> 8);
		longBytes[2] = (byte) (l >> 16);
		longBytes[3] = (byte) (l >> 24);
		return;
	}

	public static void blockNumberToBit(int blockNumber, byte[] bytes) {
		// divide by 8
		int index = blockNumber / 8;
		int offset = blockNumber % 8;
		byte tmp = (byte) (bytes[index] | (byte) (0x01 << (8 - offset - 1)));
		bytes[index] = tmp;
	}

	public static boolean hasBlock(byte b, int index) {
		if ((b & COMPARE_TABLE[index]) == 0x00)
			return false;
		else
			return true;
		// b = (byte) ((byte) b << index & 0x80);
		// if (b == 0x00)
		// return false;
		// return true;
	}

}
