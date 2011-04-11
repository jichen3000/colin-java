package com.mchz.henry.domain;

public class LoitItem implements Comparable {
	// data file number
	private long afn;
	// block number in data file
	private long blockNumber;

	private long blockCount = 1;

	public long getAfn() {
		return afn;
	}

	public long getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(long blockCount) {
		this.blockCount = blockCount;
	}

	public LoitItem(long afn, long blockNumber) {
		super();
		this.afn = afn;
		this.blockNumber = blockNumber;
	}

	public LoitItem(long afn, long blockNumber, long blockCount) {
		super();
		this.afn = afn;
		this.blockNumber = blockNumber;
		this.blockCount = blockCount;
	}

	public void setAfn(long afn) {
		this.afn = afn;
	}

	public long getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(long blockNumber) {
		this.blockNumber = blockNumber;
	}

	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;
		LoitItem other = (LoitItem) obj;
		if (other.getAfn() == this.afn
				&& other.getBlockNumber() == this.getBlockNumber()) {
			return true;
		}
		return false;
	}

	public int compareTo(Object obj) {
		LoitItem item = (LoitItem) obj;
		if (this.afn > item.afn) {
			return 1;
		} else if (this.afn == item.afn) {
			if (this.blockNumber > item.blockNumber)
				return 1;
			else if (this.blockNumber < item.blockNumber)
				return -1;
			else
				return 0;
		} else {
			return -1;
		}
	}

}
