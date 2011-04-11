package com.mchz.henry.service;

import java.io.IOException;
import java.util.Map;

import com.mchz.henry.util.ConvertUtil;
import com.mchz.henry.util.InputParameter;

public class SoitSaveStrategy {

	private int currentBlockCount = 0;
	private int currentBlockNumber = 0;
	private int currentAfn = 0;
	private int currentBit = 0;
	private long totalAfnOffset;
	private long currentAfnOffset;
	private boolean needToPutAfnOffset;
	private Map afnOffsetMap;
	private int maxMassCount;

	private byte[][] bytes;

	private SoitSaver soitSaver;

	private InputParameter inputParameter;

	public SoitSaveStrategy(byte[][] bytes, SoitSaver soitSaver,
			InputParameter inputParameter, Map afnOffsetMap) {
		this.bytes = bytes;
		this.soitSaver = soitSaver;
		this.inputParameter = inputParameter;
		this.afnOffsetMap = afnOffsetMap;
		this.totalAfnOffset = inputParameter.getSoitDataStartOffset();
		this.maxMassCount = inputParameter.getMaxMassCount();
	}

	public long save() throws IOException {
		int maxBlockNumber = bytes[0].length * 8;
		for (currentAfn = 1; currentAfn < bytes.length; currentAfn++) {
			initAfnOffset();
			for (currentBit = 0; currentBit < maxBlockNumber; currentBit++) {
				// optimise for currentBit/8
				int currentByte = currentBit >> 3;
				// optimise for currentBit%8
				int currentOffset = currentBit & 7;
				if (hasBlock(currentAfn, currentByte, currentOffset)) {
					accumulateBlockCount();
				} else {
					saveAccumulatedBlockCount();
					moveCurrentBlockNumber();
				}
				saveIfReachAfnBound();
				saveIfReachMaxMassCount();
			}
			putAfnOffset();
		}
		return (totalAfnOffset - inputParameter.getSoitDataStartOffset())
				/ SoitSaver.SOIT_ITEM_SIZE;
	}

	private void initAfnOffset() {
		currentAfnOffset = totalAfnOffset;
		needToPutAfnOffset = false;
	}

	private void putAfnOffset() {
		if (needToPutAfnOffset) {
			this.afnOffsetMap.put(new Long(currentAfn), new Long(
					currentAfnOffset));
		}
	}

	private void moveCurrentBlockNumber() {
		currentBlockNumber = currentBit;
	}

	private void saveAccumulatedBlockCount() throws IOException {
		if (currentBlockCount != 0) {
			saveCurrentSoit();
			clearCurrentBlockCount();
		}
	}

	private void accumulateBlockCount() {
		if (currentBlockCount == 0) {
			moveCurrentBlockNumber();
		}
		currentBlockCount++;
		this.needToPutAfnOffset = true;
	}

	private void saveIfReachAfnBound() throws IOException {
		if (currentBit == bytes[0].length * 8 - 1) {
			if (currentBlockCount != 0) {
				saveCurrentSoit();
			}
			clearCurrentBlockNumber();
			clearCurrentBlockCount();
		}
	}

	private void saveIfReachMaxMassCount() throws IOException {
		if (currentBlockCount >= this.maxMassCount) {
			saveCurrentSoit();
			clearCurrentBlockCount();
		}
	}

	private void saveCurrentSoit() throws IOException {
		soitSaver.saveSoit(currentAfn + inputParameter.getAfnStart() - 1,
				currentBlockNumber, currentBlockCount);
		this.totalAfnOffset += SoitSaver.SOIT_ITEM_SIZE;
	}

	private void clearCurrentBlockCount() {
		currentBlockCount = 0;
	}

	private void clearCurrentBlockNumber() {
		currentBlockNumber = 0;
	}

	private boolean hasBlock(int currentAfn, int currentByte, int currentOffset) {
		return ConvertUtil.hasBlock(this.bytes[currentAfn][currentByte],
				currentOffset);
	}
}
