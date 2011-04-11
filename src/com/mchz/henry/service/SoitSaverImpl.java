package com.mchz.henry.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Map;

import com.mchz.henry.util.ConvertUtil;
import com.mchz.henry.util.InputParameter;

public class SoitSaverImpl implements SoitSaver {

	private OutputStream os = null;

	private InputParameter inputParameter;

	private byte[] emptyBytes = new byte[4];

	private byte[] longBytes = new byte[4];

	private RandomAccessFile file;

	public static long bytesToLong(byte[] bytes, int index, int length) {
		long result = 0;
		for (int i = 0; i < length; i++) {
			result = result | ((long) (bytes[index + i] & 0xff) << 8 * i);
		}
		return result;
	}
	
	public SoitSaverImpl(File file, InputParameter inputParameter)
			throws FileNotFoundException {
		this.file = new RandomAccessFile(file, "rw");
		this.os = new BufferedOutputStream(new FileOutputStream(file));
		this.inputParameter = inputParameter;
	}

	public void saveSoit(long afn, long blockNumber, long blockCount)
			throws IOException {
		os.write(ConvertUtil.longToBytesBigEndian(afn));
		os.write(ConvertUtil.longToBytesBigEndian(blockNumber));
		os.write(ConvertUtil.longToBytesBigEndian(blockCount));
		os.write(emptyBytes);
	}

	public void save(long afn, long blockNumberIndex, byte b)
			throws IOException {
		ConvertUtil.longToBytes(afn, this.longBytes);
		for (int i = 0; i < 8; i++) {
			if (ConvertUtil.hasBlock(b, i)) {
				os.write(this.longBytes);
				os.write(ConvertUtil.longToBytes(blockNumberIndex * 8 + i));
				os.write(ConvertUtil.longToBytes(0x01));
				os.write(emptyBytes);
			}
		}
	}

	public void close() {
		if (os != null)
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (this.file != null)
			try {
				this.file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void saveHeaderBlock1()
			throws IOException {
		os.write(FILE_TYPE);
		os.write(ConvertUtil.longToBytesBigEndian(0));
		os.write(ConvertUtil.longToBytesBigEndian(SoitSaver.SOIT_ITEM_SIZE));
		os.write(SoitSaver.BIG_ENDIAN_BYTES);
		os.write(new byte[2]);
		os.write(ConvertUtil.longToBytesBigEndian(this.inputParameter
				.getAfnCount() * 4));
		os.write(ConvertUtil
				.longToBytesBigEndian(this.inputParameter.getDbId()));
		os.write(ConvertUtil.longToBytesBigEndian(this.inputParameter
				.getDbVersion()));
		os.write(new byte[20]);
		os.write(ConvertUtil.longToBytesBigEndian(this.inputParameter
				.getAfnStart()));
		os.write(ConvertUtil.longToBytesBigEndian(this.inputParameter
				.getAfnCount()));
		os.write(new byte[SOIT_HEADER1_SIZE - 56]);
	}

	public void saveHeaderBlock2() throws IOException {
		os.write(new byte[this.inputParameter.getAfnCount()
				* SoitSaver.SOIT_HEADER2_ITEM_SIZE]);
	}

	public void updateHeaderBlock2(Map offsetMap, long recordCount)
			throws IOException {
		// must flush os first ,or header block 2 will be over write when close
		this.os.flush();
		// skip header
		this.file.seek(4);
		this.file.write(ConvertUtil.longToBytesBigEndian(recordCount));
		this.file.seek(SoitSaver.SOIT_HEADER1_SIZE);
		int afnStart = this.inputParameter.getAfnStart();
		int afnEnd = afnStart + this.inputParameter.getAfnCount();
		for (int i = afnStart; i < afnEnd; i++) {
			Object offset = offsetMap.get(new Long(i));
			if (offset != null) {
				this.file.write(ConvertUtil
						.longToBytesBigEndian(((Long) offset).longValue()));
			} else {
				this.file.write(emptyBytes);
			}
		}
	}
}
