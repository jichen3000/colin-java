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
	// ������������
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
	//���ļ��б��л�ȡ�ļ������ȡ�����͵ȴ�
	private StdbFile getStdbFileOrWait() throws InterruptedException{
		StdbFile sf = this.stdbFileCollection.getStdbFile();
		while (sf==null){
			sf = this.stdbFileCollection.getStdbFile();
			Thread.sleep(this.fileWaitStep);
		}
		return sf;
	}
	// �����ػ���д����д���塱
	private int toWriteBuffer(){
		int result = 0;
		long highCscn = this.recordCollection.getHighCscnIsConfirmed();
		// ��������Լ��뵽��д���塱������cscn��
		long maxCscn = this.stdbFileCollection.getMaxContinueCscn(
				highCscn, (LinkedList<Long>)this.readedCscnList);
		if (maxCscn>=0)
			result = this.recordCollection.addRecordList(this.recordList, maxCscn);
		// delete cscn
		while(this.readedCscnList.peek()<=maxCscn)
			this.readedCscnList.poll();
		return result;
	}
	// ����ǰ��cscn�����б��������Ѿ��ظ�����ֵ���롣
	private int addReadedCscn(long cscn){
		int result = 0;
		if(((LinkedList<Long>)this.readedCscnList).getLast()!=cscn){
			this.readedCscnList.offer(cscn);
			result = 1;
		}
		return result;
		
	}
	// ǿ�ƽ����ػ���д����д���塱
	private int forceToWriteBufferOrWait(long cscn, long preScn) throws InterruptedException{
		int result = 0;
		do{
			// ���ֻ��һ��Cscn�ͳ����˻��壬�ͱ�������ʱ�ύ
			if(this.readedCscnList.size()==0){
				// ����һ����Ϊ�����ļ�¼
				this.recordList.offer(RecordItem.createEndItem(preScn));
				addReadedCscn(cscn);
			}
			// ���û�п����ύ�����ݣ�ֻ�ܵȴ���
			result = toWriteBuffer(); 
			if(result<=0){
				Thread.sleep(this.addStep);
			}
		}while(this.recordList.size()>=this.maxRecordCount);
		return result;
	}
	public void run(){
		try {
			// ��¼��һ��scn�š�
			long preScn = -1;
			while(true){
				//���ļ��б��л�ȡ�ļ������ȡ�����͵ȴ�
				StdbFile sf = getStdbFileOrWait();
				sf.readSecondHeader();
				while(!sf.isEof()){
					
					RecordItem record = sf.getRecordItem();
					// ����ļ���������ʱҲ��Ҫ�ύ�ˣ��������Ҫ����List�Ĵ�С��
					
					this.recordList.offer(record);
					//sf.isEof()
					if(record.isCommit() ){
						// ֻ�иտ�ʼ��һ���ļ��ĵ�һ���ſ�������
						if(preScn>-1){
							record.setScn(preScn);
							addReadedCscn(record.getCscn());
							toWriteBuffer();
						}
						//add log debug
					    // ���û�п����ύ�����ݣ�����Ҫ�ȴ���	
					}
					// ��������Ѿ���������Ҫ�ύ�ˡ�
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
