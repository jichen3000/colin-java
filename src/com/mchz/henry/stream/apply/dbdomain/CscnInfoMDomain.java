package com.mchz.henry.stream.apply.dbdomain;

import java.util.ArrayList;

public class CscnInfoMDomain {
	public static ArrayList<Long> getContinueCscn(long startScn){
		ArrayList<Long> list = new ArrayList<Long>();
		list.add(startScn);
		list.add(startScn+1);
		return list;
	}
}
