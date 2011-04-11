package com.mchz.henry.stream.apply.domain;

import java.util.LinkedList;
import java.util.Queue;

public class RecordCollection {
	private final int maxBufferSize;
	private LinkedList<RecordItem> recordList;
	private LinkedList<CscnItem> recordedCscns;
	// 加入的cscn中最大的
	private long maxAddedCscn;
	// 
	// 获取在列表中的最后一个cscn
	public synchronized long getHighCscnIsConfirmed(){
//		for(int i=this.recordedCscns.size()-1; i>=0; i--){
//			if(this.recordedCscns.get(i).getStatus()==RecordItemStatus.CONFIRMED)
//				return this.recordedCscns.get(i).getCscn();
//		}
//		return -1;
		return this.maxAddedCscn;
	}
	public RecordCollection(int maxBufferSize,long startCscn){
		this.maxBufferSize = maxBufferSize;
		this.recordList = new LinkedList<RecordItem>();
		this.recordedCscns = new LinkedList<CscnItem>();
//		this.recordedCscns.add(RecordItem.createCscnItem(startCscn,RecordItemStatus.CONFIRMED));
		this.maxAddedCscn = startCscn;
	}
	// 从列表中获取下一个cscnItem
	private CscnItem getNextCscnItem(CscnItem preCscnItem){
		CscnItem curCscnItem = null;
		if (preCscnItem==null){
			for(CscnItem ci: this.recordedCscns){
				if(ci.getStatus()!=RecordItemStatus.WRITED){
					curCscnItem = ci;
					break;
				}
			}
		}
		// 有一个分开的大事务
		else{
			for(CscnItem ci: this.recordedCscns){
				if(ci.getCscn()==preCscnItem.getCscn() && 
						ci.getLastScn()>preCscnItem.getLastScn()){
					curCscnItem = ci;
					break;
				}
			}
		}
		if(curCscnItem==null){
			// report error
		}
		return curCscnItem;
	}
	public synchronized int getRecordList(Queue<RecordItem> writeRecordList, CscnItem preCscnItem){
		//如果有一个读线程没有提交一个完整的事务，写线程不能超过它来进行
				
		int result = 0;
//		int getCscn = preCscnItem;
		CscnItem curCscnItem =this.getNextCscnItem(preCscnItem); 
		if(curCscnItem==null){
			preCscnItem = null;
			return result;
		}
		for(RecordItem record: this.recordList){
			if(record.getCscn() == curCscnItem.getCscn()){
				writeRecordList.offer(record);
				record.setStatus(RecordItemStatus.WRITED);
				result++;
				if(record.isEndItem())
					break;
			}
		}
		curCscnItem.setStatus(RecordItemStatus.WRITED);
		preCscnItem.copyOtherValue(curCscnItem);
		return result;
	}
	// 检查事务相关性，从上一个cscn开始，从后往前找，一旦找到就设置，然后退出。
	private void checkRelated(RecordItem curRecord, int lastIndex){
		for(int i=lastIndex; i>=0; i--){
			if (curRecord.checkRelated(this.recordList.get(i))){
				curRecord.setRelatedCscn(this.recordList.get(i).getCscn());
				return;
			}
		}
	}
	
	public synchronized int addRecordList(Queue<RecordItem> readedRecordList,long maxCscn){
		int result = 0;
		if(this.recordList.size()>=this.maxBufferSize)
			return result;
		int lastIndex = this.recordList.size()-1;
		while(readedRecordList.peek().getCscn()<=maxCscn){
			RecordItem record = readedRecordList.poll();
			// 检查事务相关性
			checkRelated(record, lastIndex);
			// 将记录加入列表
			this.recordList.offer(record);
			// 如果是需要提交的记录
			if(record.isCommit() ){
				this.recordedCscns.add(new CscnItem(record.getCscn(),record.getScn(),
						true,RecordItemStatus.CONFIRMED));
				lastIndex = this.recordList.size()-1;
				this.maxAddedCscn = record.getCscn();
			// 如果是需要临时提交的记录
			}else if(record.isEndItem()){
				this.recordedCscns.add(new CscnItem(record.getCscn(),record.getScn(), 
						false,RecordItemStatus.CONFIRMED));
			}
			record.setStatus(RecordItemStatus.CONFIRMED);
			
			result++;
		}
		return result;
	}
	
	
	
	
}
