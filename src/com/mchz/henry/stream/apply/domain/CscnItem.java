package com.mchz.henry.stream.apply.domain;

public class CscnItem {
	private long cscn;
	private long lastScn;
	private boolean isFullTrans;
	private RecordItemStatus status;
	public long getCscn() {
		return cscn;
	}
	public long getLastScn() {
		return lastScn;
	}
	public boolean isFullTrans() {
		return isFullTrans;
	}
	public RecordItemStatus getStatus() {
		return status;
	}
	public void setStatus(RecordItemStatus status){
		this.status = status;
	}
	public CscnItem(){
		super();
	}
	public CscnItem(long cscn, long lastScn, boolean isFullTrans, RecordItemStatus status) {
		super();
		this.cscn = cscn;
		this.lastScn = lastScn;
		this.isFullTrans = isFullTrans;
		this.status = status;
	}
	public void copyOtherValue(CscnItem other){
		this.cscn = other.cscn;
		this.lastScn = other.lastScn;
		this.isFullTrans = other.isFullTrans;
		this.status = other.status;
	}
}
