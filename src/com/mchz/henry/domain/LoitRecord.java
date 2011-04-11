package com.mchz.henry.domain;

public class LoitRecord {
	private long afn;
//	private int rfn;
	private long blockNumber;
	private long blockCount;

	public LoitRecord(long afn, int rfn, long blockNumber, long blockCount) {
		super();
		this.afn = afn;
//		this.rfn = rfn;
		this.blockNumber = blockNumber;
		this.blockCount = blockCount;
	}

	public long getAfn() {
		return afn;
	}

	public void setAfn(int afn) {
		this.afn = afn;
	}

//	public int getRfn() {
//		return rfn;
//	}
//
//	public void setRfn(int rfn) {
//		this.rfn = rfn;
//	}

	public long getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}

	public long getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}
}
