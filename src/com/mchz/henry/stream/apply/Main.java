package com.mchz.henry.stream.apply;

import java.util.ArrayList;

import com.mchz.henry.stream.apply.dbdomain.StdbFileMDomain;
import com.mchz.henry.stream.apply.domain.RecordCollection;
import com.mchz.henry.stream.apply.domain.StdbFileCollection;

public class Main {
	public static ArrayList<ArrayList> initStdbFileListList(int count){
		ArrayList<ArrayList> stdbFileListList = new ArrayList<ArrayList>();
		for(int i=0; i<count; i++){
			ArrayList stdbFileList = new ArrayList();
			stdbFileListList.add(stdbFileList);
		}
		return stdbFileListList;
	}
	//�ַ�Ҫ������ļ������ļ��б�
	public static void dispatchStdbFile(ArrayList stdbList,ArrayList stdbFileListList){
		
	}
	public static void main(String[] args){
		try {
			//��ȡ��������
			//�ӹ�����ȡ��������
			int readThreadCount = 3;
			int writeThreadCount = 3;
			
			//��ʼ���ļ��б�
			StdbFileCollection sfCollection = new StdbFileCollection();
			
			//
//			RecordCollection recordBuffer = new RecordCollection();
			
			//�������read�߳�
			ArrayList<Thread> readThreadList = new ArrayList<Thread>();
			
			//�������write�߳�
			ArrayList<Thread> writeThreadList = new ArrayList<Thread>();
			
			boolean stopFlag = true;
			//�ַ�Ҫ������ļ������ļ��б�
			while(stopFlag){
				ArrayList stdbList = StdbFileMDomain.selectNeeds();				
				sfCollection.addStdbFiles(StdbFileCollection.transFromDomain(stdbList));
			}
			
			for(Thread thread : readThreadList)
					thread.join();
			for(Thread thread : writeThreadList)
				thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
