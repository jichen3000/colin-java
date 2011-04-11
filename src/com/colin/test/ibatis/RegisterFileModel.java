package com.colin.test.ibatis;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class RegisterFileModel {
	private long id;
	private long subId;
	private long bsId;
	private String captureName;
	private String transferName;
	private String applyName;
	private int captureId;
	private String fname;
	private long fid;
	private String tname;
	private Date cftime;
	private Date tftime;
	private Date aftime;
	private int lcrCount;
	private int lcrDdl;
	private int lcrLob;
	private boolean lastEnded;
	private boolean created;
	private boolean transfed;
	private boolean applied;
	private long startScn;
	private long endScn;
	private String txList;
	public long getId() {
  	return id;
  }
	public void setId(long id) {
  	this.id = id;
  }
	public long getSubId() {
  	return subId;
  }
	public void setSubId(long subId) {
  	this.subId = subId;
  }
	public long getBsId() {
  	return bsId;
  }
	public void setBsId(long bsId) {
  	this.bsId = bsId;
  }
	public String getCaptureName() {
  	return captureName;
  }
	public void setCaptureName(String captureName) {
  	this.captureName = captureName;
  }
	public String getTransferName() {
  	return transferName;
  }
	public void setTransferName(String transferName) {
  	this.transferName = transferName;
  }
	public String getApplyName() {
  	return applyName;
  }
	public void setApplyName(String applyName) {
  	this.applyName = applyName;
  }
	public int getCaptureId() {
  	return captureId;
  }
	public void setCaptureId(int captureId) {
  	this.captureId = captureId;
  }
	public String getFname() {
  	return fname;
  }
	public void setFname(String fname) {
  	this.fname = fname;
  }
	public long getFid() {
  	return fid;
  }
	public void setFid(long fid) {
  	this.fid = fid;
  }
	public String getTname() {
  	return tname;
  }
	public void setTname(String tname) {
  	this.tname = tname;
  }
	public Date getCftime() {
  	return cftime;
  }
	public void setCftime(Date cftime) {
  	this.cftime = cftime;
  }
	public Date getTftime() {
  	return tftime;
  }
	public void setTftime(Date tftime) {
  	this.tftime = tftime;
  }
	public Date getAftime() {
  	return aftime;
  }
	public void setAftime(Date aftime) {
  	this.aftime = aftime;
  }
	public int getLcrCount() {
  	return lcrCount;
  }
	public void setLcrCount(int lcrCount) {
  	this.lcrCount = lcrCount;
  }
	public int getLcrDdl() {
  	return lcrDdl;
  }
	public void setLcrDdl(int lcrDdl) {
  	this.lcrDdl = lcrDdl;
  }
	public int getLcrLob() {
  	return lcrLob;
  }
	public void setLcrLob(int lcrLob) {
  	this.lcrLob = lcrLob;
  }
	public boolean isLastEnded() {
  	return lastEnded;
  }
	public void setLastEnded(boolean lastEnded) {
  	this.lastEnded = lastEnded;
  }
	public boolean isCreated() {
  	return created;
  }
	public void setCreated(boolean created) {
  	this.created = created;
  }
	public boolean isTransfed() {
  	return transfed;
  }
	public void setTransfed(boolean transfed) {
  	this.transfed = transfed;
  }
	public boolean isApplied() {
  	return applied;
  }
	public void setApplied(boolean applied) {
  	this.applied = applied;
  }
	public long getStartScn() {
  	return startScn;
  }
	public void setStartScn(long startScn) {
  	this.startScn = startScn;
  }
	public long getEndScn() {
  	return endScn;
  }
	public void setEndScn(long endScn) {
  	this.endScn = endScn;
  }
	public String getTxList() {
  	return txList;
  }
	public void setTxList(String txList) {
  	this.txList = txList;
  }
	private static SqlSessionFactory sqlSessionFactory;
	public static void setSessionFactory(SqlSessionFactory sqlSessionFactory){
		RegisterFileModel.sqlSessionFactory = sqlSessionFactory;
	}
	
	public static void selectNeedMaxs(){
		SqlSession session = sqlSessionFactory.openSession(); 
		try { 
//			Blog b = (Blog)session.selectOne("com.colin.test.ibatis.BlogMapper.selectBlog", 1);
			ArrayList<RegisterFileModel> al = (ArrayList<RegisterFileModel>)session.selectList(
					"com.colin.test.ibatis.RegisterFileMapper.selectNeedMaxs");
		  if(al == null)
		  	System.out.println("is null");
			for (RegisterFileModel r : al) {
	      System.out.println(r);
      }
			
		} finally { 
		  session.close(); 
		} 
	}
}
