package com.mchz.henry.stream.apply.domain;

import java.util.ArrayList;
import java.util.LinkedList;

import com.mchz.henry.stream.apply.dbdomain.StdbFileMDomain;

public class StdbFileCollection {
	private ArrayList<StdbFile> fileList;
	
	public StdbFileCollection(){
		this.fileList = new ArrayList<StdbFile>();
	}
	public synchronized long getContinueNextCscn(long curCscn){
		// TODO continueCscnList
		ArrayList<Long> continueCscnList = new ArrayList<Long>();
		
		for(int i = 0; i < continueCscnList.size(); i++){
			if (continueCscnList.get(i)==curCscn)
				return continueCscnList.get(i+1);
		}
		return -1;
	}
	public synchronized long getMaxContinueCscn(
			long curCscn,LinkedList<Long> cscnList){
		// TODO continueCscnList
		ArrayList<Long> continueCscnList = new ArrayList<Long>();
		long result = -1;
		// 获取当前cscn在continueCscnList的位置
		int firstIndex = continueCscnList.indexOf(curCscn);
		
		if(firstIndex >= 0){
			firstIndex++;
			for(int i=0,j=firstIndex; i<cscnList.size()&&j<continueCscnList.size();i++,j++){
				// 依次比较，只有连续的才可以被加入s
				if(cscnList.get(i)==continueCscnList.get(j)){
					result=cscnList.get(i);
				}else{
					break;
				}
			}
		}
		
		return result;
	}
	
	public synchronized void addStdbFiles(ArrayList<StdbFile> files){
		// TODO 要判断是否是有文件是相关的。
		this.fileList.addAll(files);
	}
	private void addStdbFile(StdbFile  file){
		
		
	}
	public synchronized StdbFile getStdbFile(){
		StdbFile result = null;
		for(StdbFile sf : this.fileList){
			// TODO 要判断是否是有文件是相关的。
			result = sf;
			break;
		}
		return result;
	}
	public static ArrayList<StdbFile> transFromDomain(ArrayList<StdbFileMDomain> domains){
		ArrayList<StdbFile> result = new ArrayList<StdbFile>();
		for(StdbFileMDomain domain : domains){
			StdbFile sf = new StdbFile(domain.getFname());
			result.add(sf);
		}
		return result;
	}
	
}
