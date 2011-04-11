package com.mchz.henry.stream.apply.dbdomain;

import java.util.ArrayList;

public class StdbFileMDomain {
	private long id;
	private String fname;
	public long getId() {
		return id;
	}

	public String getFname() {
		return fname;
	}
	public StdbFileMDomain(){
		
	}
	public StdbFileMDomain(long id, String fname){
		
	}
	
	public static ArrayList<StdbFileMDomain> selectNeeds(){
		ArrayList<StdbFileMDomain> list = new ArrayList<StdbFileMDomain>();
		list.add(new StdbFileMDomain(1,""));
		list.add(new StdbFileMDomain(2,""));
		return list;
	}
}
