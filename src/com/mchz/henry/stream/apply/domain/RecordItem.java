package com.mchz.henry.stream.apply.domain;

import java.util.LinkedHashMap;



public class RecordItem{
	private static String spilitFieldStr=",";
	private static String spelitCommStr=";";
	private static String spilitContentStr="~!@";
	private long cscn;
	private String objectOwner;
	private String objectName;
	private String rowId;
	private String contentStr;
	private boolean isDml;
	
	// 流程控制：记录状态
	private RecordItemStatus status;
	
	// 流程控制：相关事务的cscn号
	private long relatedCscn;
	public long getRelatedCscn() {
		return relatedCscn;
	}
	public void setRelatedCscn(long relatedCscn) {
		this.relatedCscn = relatedCscn;
	}
	public RecordItemStatus getStatus() {
		return status;
	}
	public void setStatus(RecordItemStatus status) {
		this.status = status;
	}
	public long getCscn() {
		return cscn;
	}
	public String getObjectOwner() {
		return objectOwner;
	}
	public String getObjectName() {
		return objectName;
	}
	public String getRowId() {
		return rowId;
	}
	public String getContentStr() {
		return contentStr;
	}
	private long scn;
	public long getScn() {
		return scn;
	}
	public void setScn(long scn){
		this.scn = scn;
	}
	public RecordItem(long scn, boolean isEndItem){
		this.scn = scn;
		this.isEndItem = isEndItem;
	}
	public RecordItem(String contentStr){
		this.contentStr = contentStr;
		this.anaSimple();
		this.status = RecordItemStatus.READED;
	}
	// 为了将一个事务拆分而加入的临时的Record
	public static RecordItem createEndItem(long scn){
		RecordItem record = new RecordItem(scn, true);
		return record;
	}
	public String genSql(){
		StringBuffer sql = new StringBuffer();
		sql.append("");
		return sql.toString();
	}
	public boolean checkRelated(RecordItem other){
		return (this.objectName==other.getObjectName() &&
				this.objectOwner==other.getObjectOwner() && 
				this.rowId==other.getRowId());
	}
	
	private void anaSimple(){
//		String[] strs = this.contentStr.split(spilitContentStr);
//		String[] names = strs[0].split(spilitFieldStr);
//		// TODO 还有待改进，要做错误检查
////		for(String str : strs)
////			System.out.println(str);
////		for(String str : names)
////			System.out.println(str);
//		LinkedHashMap<String,String> values = new LinkedHashMap<String,String>(); 
//		for(int i=0; i<names.length; i++){
//			values.put(names[i], strs[i+1]);
//		}
		int pos = this.contentStr.indexOf(spelitCommStr);
		String[] values = this.contentStr.substring(0, pos).split(spilitFieldStr);
		this.contentStr = this.contentStr.substring(pos+1);
		this.isDml = (values[0]=="M") ? true : false ;
		
		
		
//		this.objectName = (String)values.get("OBJECT_NAME");
//		this.objectOwner = (String)values.get("OBJECT_OWNER");
//		this.rowId = (String)values.get("ROWID");
//		this.cscn = Long.parseLong((String)values.get("COMMIT_SCN"));
		
		this.isEndItem = this.isCommit;
		
	}
	private boolean isCommit;
	public boolean isCommit(){
		return false;
	}
	private boolean isEndItem;
	public void setEndItem(boolean isEndItem) {
		this.isEndItem = isEndItem;
	}
	public boolean isEndItem(){
		return false;
	}
	private void anaFull(){
		
	}
	public String toString(){
		return "cscn: " + this.cscn +
				"\nobjectName: " + this.objectName + 
				"\nobjectOwner: " + this.objectOwner + 
				"\nrowId: " + this.rowId;
	}
	
	public static void main(String[] args){
		String contentStr = "SOURCE_DATABASE_NAME,COMMAND_TYPE,OBJECT_OWNER," +
				"OBJECT_NAME,TRANSACTION_ID,COMMIT_SCN,ROWID,SID,SERIAL#," +
				"THREAD,PROD_ID,PROD_NAME,DEPTNO,PROD_DATE" +
				"~!@STREAMS.REGRESS.RDBMS.DEV.US.ORACLE.COM" +
				"~!@DELETE" +
				"~!@COMPANY" +
				"~!@PRODUCT" +
				"~!@3.13.9891~!@40739618" +
				"~!@AAANxWAAGAAAFqoACb" +
				"~!@425" +
				"~!@101" +
				"~!@1" +
				"~!@1436435" +
				"~!@wBjkgeY\\ &v%fTE" +
				"~!@10" +
				"~!@13-AUG-09";
		System.out.println(contentStr);
		RecordItem ri = new RecordItem(contentStr);
		System.out.println(ri);
		System.out.println("ok");
		
	}
}
