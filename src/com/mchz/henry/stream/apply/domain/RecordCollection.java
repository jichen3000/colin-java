package com.mchz.henry.stream.apply.domain;

import java.util.LinkedList;
import java.util.Queue;

public class RecordCollection {
	private final int maxBufferSize;
	private LinkedList<RecordItem> recordList;
	private LinkedList<CscnItem> recordedCscns;
	// �����cscn������
	private long maxAddedCscn;
	// 
	// ��ȡ���б��е����һ��cscn
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
	// ���б��л�ȡ��һ��cscnItem
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
		// ��һ���ֿ��Ĵ�����
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
		//�����һ�����߳�û���ύһ������������д�̲߳��ܳ�����������
				
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
	// �����������ԣ�����һ��cscn��ʼ���Ӻ���ǰ�ң�һ���ҵ������ã�Ȼ���˳���
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
			// ������������
			checkRelated(record, lastIndex);
			// ����¼�����б�
			this.recordList.offer(record);
			// �������Ҫ�ύ�ļ�¼
			if(record.isCommit() ){
				this.recordedCscns.add(new CscnItem(record.getCscn(),record.getScn(),
						true,RecordItemStatus.CONFIRMED));
				lastIndex = this.recordList.size()-1;
				this.maxAddedCscn = record.getCscn();
			// �������Ҫ��ʱ�ύ�ļ�¼
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
