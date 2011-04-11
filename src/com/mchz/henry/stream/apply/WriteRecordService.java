package com.mchz.henry.stream.apply;

import java.util.LinkedList;

import com.mchz.henry.stream.apply.domain.CscnItem;
import com.mchz.henry.stream.apply.domain.RecordCollection;
import com.mchz.henry.stream.apply.domain.RecordItem;

public class WriteRecordService implements Runnable{
	private int id;
	private RecordCollection recordCollection;
	private LinkedList<RecordItem> recordList;
	public WriteRecordService(int id, RecordCollection recordCollection){
		this.id = id;
		this.recordCollection = recordCollection;
		
		this.recordList = new LinkedList<RecordItem>();
	}
	
	private void genSql(){
		for(RecordItem record : this.recordList){
//			record.genSql();
		}
	}
	private void execSql(){
		
	}
	public void run(){
		CscnItem preCscnItem = new CscnItem();
		while(true){
			//
			int count = this.recordCollection.getRecordList(this.recordList, preCscnItem);
			
			// 可以在这里才进行记录的完整分解
			
			// 对于记录的map和filter操作都可以在这里
			genSql();
			
			execSql();
			
		}
	}
}
