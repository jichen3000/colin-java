package com.mchz.henry.stream.apply;

import java.util.LinkedList;
import java.util.Queue;

import com.mchz.henry.stream.apply.domain.RecordCollection;
import com.mchz.henry.stream.apply.domain.RecordItem;
import com.mchz.henry.stream.apply.domain.StdbFile;
import com.mchz.henry.stream.apply.domain.StdbFileCollection;

public class ReadRecordService implements Runnable{
	private final int id;
	private final int fileWaitStep;
	private final int addStep;  
	// 缓冲的最大数量
	private final int maxRecordCount;
	
	private RecordCollection recordCollection;
	private StdbFileCollection stdbFileCollection;
	private Queue<RecordItem> recordList;
	private Queue<Long> readedCscnList;
	public ReadRecordService(int id, 
			RecordCollection recordCollection, StdbFileCollection stdbFileCollection){
		this.id = id;
		this.stdbFileCollection = stdbFileCollection;
		this.recordCollection = recordCollection;
		this.recordList = new LinkedList<RecordItem>();
		this.readedCscnList = new LinkedList<Long>();
		this.fileWaitStep = 1000;
		this.addStep = 1000;
		this.maxRecordCount = 10000;
	}
	//从文件列表中获取文件，如果取不到就等待
	private StdbFile getStdbFileOrWait() throws InterruptedException{
		StdbFile sf = this.stdbFileCollection.getStdbFile();
		while (sf==null){
			sf = this.stdbFileCollection.getStdbFile();
			Thread.sleep(this.fileWaitStep);
		}
		return sf;
	}
	// 将本地缓冲写到“写缓冲”
	private int toWriteBuffer(){
		int result = 0;
		long highCscn = this.recordCollection.getHighCscnIsConfirmed();
		// 计算出可以加入到“写缓冲”中最大的cscn号
		long maxCscn = this.stdbFileCollection.getMaxContinueCscn(
				highCscn, (LinkedList<Long>)this.readedCscnList);
		if (maxCscn>=0)
			result = this.recordCollection.addRecordList(this.recordList, maxCscn);
		// delete cscn
		while(this.readedCscnList.peek()<=maxCscn)
			this.readedCscnList.poll();
		return result;
	}
	// 将当前的cscn加入列表，会避免加已经重复的数值加入。
	private int addReadedCscn(long cscn){
		int result = 0;
		if(((LinkedList<Long>)this.readedCscnList).getLast()!=cscn){
			this.readedCscnList.offer(cscn);
			result = 1;
		}
		return result;
		
	}
	// 强制将本地缓冲写到“写缓冲”
	private int forceToWriteBufferOrWait(long cscn, long preScn) throws InterruptedException{
		int result = 0;
		do{
			// 如果只有一个Cscn就充满了缓冲，就必须先临时提交
			if(this.readedCscnList.size()==0){
				// 插入一条作为结束的记录
				this.recordList.offer(RecordItem.createEndItem(preScn));
				addReadedCscn(cscn);
			}
			// 如果没有可以提交的内容，只能等待。
			result = toWriteBuffer(); 
			if(result<=0){
				Thread.sleep(this.addStep);
			}
		}while(this.recordList.size()>=this.maxRecordCount);
		return result;
	}
	public void run(){
		try {
			// 记录上一个scn号。
			long preScn = -1;
			while(true){
				//从文件列表中获取文件，如果取不到就等待
				StdbFile sf = getStdbFileOrWait();
				sf.readSecondHeader();
				while(!sf.isEof()){
					
					RecordItem record = sf.getRecordItem();
					// 如果文件结束，这时也需要提交了，否则就需要控制List的大小。
					
					this.recordList.offer(record);
					//sf.isEof()
					if(record.isCommit() ){
						// 只有刚开始第一个文件的第一条才可能这样
						if(preScn>-1){
							record.setScn(preScn);
							addReadedCscn(record.getCscn());
							toWriteBuffer();
						}
						//add log debug
					    // 如果没有可以提交的内容，不需要等待。	
					}
					// 如果缓冲已经充满就需要提交了。
					if(this.recordList.size()>=this.maxRecordCount){
						forceToWriteBufferOrWait(record.getCscn(),preScn);
					}
					
					preScn = record.getScn();
									
				}
				
			}
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
