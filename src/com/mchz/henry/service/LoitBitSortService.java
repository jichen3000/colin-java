package com.mchz.henry.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mchz.henry.domain.LoitItem;
import com.mchz.henry.domain.LoitRecord;
import com.mchz.henry.util.ConvertUtil;
import com.mchz.henry.util.InputParameter;

public class LoitBitSortService {

	// fucking afn start with 1 not 0
	public static final int ARRAY_SIZE = 128 + 1;

	// default cell size is 2^22 in loit file
	public static final int CELL_SIZE = 524288;

	private byte bytes[][] = null;

	private SoitSaver soitSaver = null;

	private LoitRecordService loitRecordService = null;

	private InputParameter inputParameter = null;

	private Map afnOffsetMap = new HashMap();

	public LoitBitSortService(int arraySize, int cellSize,
			LoitRecordService loitRecordService, SoitSaver soitSaver) {
		this.bytes = new byte[arraySize][cellSize];
		this.loitRecordService = loitRecordService;
		this.soitSaver = soitSaver;
	}

	public LoitBitSortService(LoitRecordService loitRecordService,
			SoitSaver soitSaver, InputParameter inputParameter) {
		this(ARRAY_SIZE, CELL_SIZE, loitRecordService, soitSaver);
		this.inputParameter = inputParameter;
	}

	public void setInputParameter(InputParameter inputParameter) {
		this.inputParameter = inputParameter;
	}

	public Map getAfnOffsetMap() {
		return afnOffsetMap;
	}

	public void setAfnOffsetMap(Map afnOffsetMap) {
		this.afnOffsetMap = afnOffsetMap;
	}

	public int getArraySize() {
		return this.bytes.length;
	}

	public byte[] getCell(int index) {
		return bytes[index];
	}

	public byte getByte(int i, int j) {
		return this.bytes[i][j];
	}

	public void add(LoitItem item) {
		byte[] cell = bytes[(int) item.getAfn() - inputParameter.getAfnStart()
				+ 1];
		ConvertUtil.blockNumberToBit((int) item.getBlockNumber(), cell);
	}

	public void addAll(Collection items) {
		for (Iterator it = items.iterator(); it.hasNext();) {
			this.add((LoitItem) it.next());
		}
	}

	public void sort() throws IOException {
		LoitRecord loitRecord = null;
		while ((loitRecord = loitRecordService.read()) != null) {
			List loitItems = loitRecordService
					.splitLoitRecordToLoitItem(loitRecord);
			this.addAll(loitItems);
		}
	}

	public void save() throws IOException {
		for (int i = 1; i < bytes.length; i++) {
			for (int j = 0; j < bytes[i].length; j++) {
				this.soitSaver.save(i, j, bytes[i][j]);
			}
		}
	}

	public void saveOptimised() throws IOException {
		SoitSaveStrategy soitSaveStrategy = new SoitSaveStrategy(bytes,
				soitSaver, inputParameter, this.afnOffsetMap);
		this.soitSaver.saveHeaderBlock1();
		this.soitSaver.saveHeaderBlock2();
		long recordCount = soitSaveStrategy.save();
		this.soitSaver.updateHeaderBlock2(afnOffsetMap, recordCount);
	}

	public void close() {
		if (this.soitSaver != null)
			this.soitSaver.close();

		if (this.loitRecordService != null)
			this.loitRecordService.close();
	}

}
