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
	//分发要处理的文件放入文件列表
	public static void dispatchStdbFile(ArrayList stdbList,ArrayList stdbFileListList){
		
	}
	public static void main(String[] args){
		try {
			//读取管理库参数
			//从管理库读取其它参数
			int readThreadCount = 3;
			int writeThreadCount = 3;
			
			//初始化文件列表
			StdbFileCollection sfCollection = new StdbFileCollection();
			
			//
//			RecordCollection recordBuffer = new RecordCollection();
			
			//启动多个read线程
			ArrayList<Thread> readThreadList = new ArrayList<Thread>();
			
			//启动多个write线程
			ArrayList<Thread> writeThreadList = new ArrayList<Thread>();
			
			boolean stopFlag = true;
			//分发要处理的文件放入文件列表
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
