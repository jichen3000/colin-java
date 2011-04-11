package com.mchz.henry.service;

import java.io.IOException;
import java.util.Map;

public interface SoitSaver {

	public static final int SOIT_HEADER1_SIZE = 512;

	public static final int SOIT_HEADER2_ITEM_SIZE = 4;

	public static final int SOIT_ITEM_SIZE = 16;

	public static final int BIG_ENDIAN = 0x0011;

	public static final byte[] BIG_ENDIAN_BYTES = new byte[] { 0x00, 0x11 };

	public static int LITTLE_ENDIAN = 0xff00;

	public static final byte[] LITTLE_ENDIAN_BYTES = new byte[] { (byte) 0xff,
			0x00 };

	public static final byte[] FILE_TYPE = new byte[] { 0x00, 0x00, 0x00, 0x0c };

	public void saveHeaderBlock1() throws IOException;

	public void saveHeaderBlock2() throws IOException;

	public void updateHeaderBlock2(Map offsetMap, long recordCount)
			throws IOException;

	public void saveSoit(long afn, long blockNumber, long blockCount)
			throws IOException;

	public void save(long afn, long blockNumberIndex, byte b)
			throws IOException;

	public void close();

}
