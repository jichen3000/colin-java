package com.mchz.henry.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.mchz.henry.domain.LoitItem;
import com.mchz.henry.domain.LoitRecord;
import com.mchz.henry.util.ConvertUtil;

public class LoitRecordServiceImpl implements LoitRecordService {

	private byte[] inputBuffer = new byte[16];

	private InputStream is = null;

	private File[] files;

	private int currentFileIndex;

	public LoitRecordServiceImpl(File[] files) throws IOException {
		this.files = files;
		initNextInputStream();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mchz.henry.service.LoitRecordService#read()
	 */
	public LoitRecord read() throws IOException {
		LoitRecord loitRecord = null;
		int actuallyRead = this.is.read(inputBuffer);
		if (actuallyRead == -1) {
			if (hasNextFile()) {
				closeCurrentInputStream();
				initNextInputStream();
				actuallyRead = this.is.read(inputBuffer);
			} else {
				return null;
			}
		}
		if (actuallyRead != inputBuffer.length)
			return null;
		loitRecord = createLoitRecord(inputBuffer);
		return loitRecord;
	}

	private boolean hasNextFile() {
		return currentFileIndex < files.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mchz.henry.service.LoitRecordService#splitLoitRecordToLoitItem(com
	 * .mchz.henry.domain.LoitRecord)
	 */
	public List splitLoitRecordToLoitItem(LoitRecord loitRecord) {
		List results = new ArrayList();
		for (int i = 0; i < loitRecord.getBlockCount(); i++) {
			LoitItem item = new LoitItem(loitRecord.getAfn(), loitRecord
					.getBlockNumber()
					+ i);
			results.add(item);
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mchz.henry.service.LoitRecordService#close()
	 */
	public void close() {
		closeCurrentInputStream();
	}

	private LoitRecord createLoitRecord(byte[] bytes) {
		LoitRecord loitRecord;
		long afn = ConvertUtil.bytesToLongBigEndian(bytes, 0, 4);
		long blockNumber = ConvertUtil.bytesToBlockNumberBigEndian(bytes, 4);
		long blockCount = ConvertUtil.bytesToLongBigEndian(bytes, 8, 4);
		loitRecord = new LoitRecord(afn, 0, blockNumber, blockCount);
		return loitRecord;
	}

	private void closeCurrentInputStream() {
		if (this.is != null) {
			try {
				this.is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.is = null;
		}
	}

	private void initNextInputStream() throws FileNotFoundException,
			IOException {
		this.is = new BufferedInputStream(new FileInputStream(
				this.files[currentFileIndex++]));
		// skip the header
		this.is.read(new byte[LOIT_HEADER_SIZE]);
	}

}
