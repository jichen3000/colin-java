package com.mchz.henry.stream.apply.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;

import com.mchz.henry.stream.apply.util.BinUtil;

public class StdbFile implements IDbraFile{
	private static final int MAXREADSIZE = 1048576;
	private String filename;
	private RandomAccessFile file;
	private int recordIndex;
	private FileUseStatus status;
	private Queue<String> recordBuffer;
	private boolean isEof;
	public StdbFile(String filename){
		this.filename = filename;
		try {
			this.file = new RandomAccessFile(filename,"r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.status = FileUseStatus.NOT;
		this.recordIndex = 0;
		this.recordBuffer = new LinkedList<String>();
	}
//	0-3 file_type(见下面)
//	4-7 record_count(如果是数据文件指的是多少个block)
//	8-11 record_size
//	12-13 endian_info 大小头信息（大头为00 11(数值17)，小头为ff 00(数值255)）
//	14-15 check_sum（现在不填）
//	16-19 second_header_size 第二个头块的长度
//	20-23 db_id
//	24-27 db_version(只放9，10就可以了)
//	48-51 LOW_SCN
//	52-55 HIGH_SCN
//	56-59 file_id
	private long fileType;
	public static int getMaxreadsize() {
		return MAXREADSIZE;
	}
	public String getFilename() {
		return filename;
	}
	public RandomAccessFile getFile() {
		return file;
	}
	public int getRecordIndex() {
		return recordIndex;
	}
	public FileUseStatus getStatus() {
		return status;
	}
	public Queue<String> getRecordBuffer() {
		return recordBuffer;
	}
	public long getFileType() {
		return fileType;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public long getRecordSize() {
		return recordSize;
	}
	public long getEndianInfo() {
		return endianInfo;
	}
	public long getCheckSum() {
		return checkSum;
	}
	public long getSecondHeaderSize() {
		return secondHeaderSize;
	}
	public long getDbId() {
		return dbId;
	}
	public long getDbVersion() {
		return dbVersion;
	}
	public long getLowScn() {
		return lowScn;
	}
	public long getHighScn() {
		return highScn;
	}
	public long getFileId() {
		return fileId;
	}
	public int[] getRecordLengths() {
		return recordLengths;
	}
	public int getRecordSizeLength() {
		return recordSizeLength;
	}
	private long recordCount;
	private long recordSize;
	private long endianInfo;
	private long checkSum;
	private long secondHeaderSize;
	private long dbId;
	private long dbVersion;
	private long lowScn;
	private long highScn;
	private long fileId;
	private int[] recordLengths;
	private int recordSizeLength;
	private void anaFirstHeader(byte[] bytes){
		this.fileType = BinUtil.bytesToLongSize4(bytes, 0);
		this.recordCount = BinUtil.bytesToLongSize4(bytes, 4);
		this.recordSize = BinUtil.bytesToLongSize4(bytes, 8);
		this.endianInfo = BinUtil.bytesToLongSize2(bytes, 12);
		this.checkSum = BinUtil.bytesToLongSize2(bytes, 14);
		this.secondHeaderSize = BinUtil.bytesToLongSize4(bytes, 16);
		this.dbId = BinUtil.bytesToLongSize4(bytes, 20);
		this.dbVersion = BinUtil.bytesToLongSize4(bytes, 24);
		this.lowScn = BinUtil.bytesToLongSize4(bytes, 48);
		this.highScn = BinUtil.bytesToLongSize4(bytes, 52);
		this.fileId = BinUtil.bytesToLongSize4(bytes, 56);
		
		this.recordSizeLength = (int)(this.secondHeaderSize/this.recordCount);
	}
	public String getFisrtHeaderInfo(){
		return "fileType: " + this.fileType + "\t" +
			"recordCount: " + this.recordCount + "\t" +
			"recordSize: " + this.recordSize + "\t" +
			"endianInfo: " + this.endianInfo + "\t" +
			"checkSum: " + this.checkSum + "\t" +
			"secondHeaderSize: " + this.secondHeaderSize + "\t" +
			"dbId: " + this.dbId + "\t" +
			"dbVersion: " + this.dbVersion + "\t" +
			"lowScn: " + this.lowScn + "\t" +
			"highScn: " + this.highScn + "\t" +
			"fileId: " + this.fileId;
	}
	public void readFirstHeader(){
		try {
			file.seek(file.length()-firstHeaderSize);
			byte[] b = new byte[firstHeaderSize];
			file.read(b);
			anaFirstHeader(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readSecondHeader(){
		try {
			file.seek(file.length()-firstHeaderSize-this.secondHeaderSize);
			byte[] b = new byte[(int)this.secondHeaderSize];
			file.read(b);
			this.recordLengths = BinUtil.anaRecordSize(b, 
					(int)this.recordCount, (int)this.recordSizeLength);
			file.seek(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 填充recordBuffer
	// 可以避免一次读很少的字节，减少IO
	public void fullRecordBuffer(){
		// 如果文件结束直接退出
		if ((this.recordIndex+1)==this.recordCount){
			this.isEof = true;
			return;
		}
		
		int sumSize = 0;
		int readCount = 0;
		while (sumSize < MAXREADSIZE && (this.recordIndex+readCount)<this.recordCount){
			sumSize = sumSize + (this.recordLengths[readCount+this.recordIndex]);
			readCount++;
		}
		byte[] b = new byte[sumSize];
		try {
			file.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int offset = 0;
		for(int i=0; i<readCount; i++){
			this.recordBuffer.offer(
					new String(b,offset,this.recordLengths[i+this.recordIndex]-1));
			offset = this.recordLengths[i+this.recordIndex];
		}
		// reset recordindex
		this.recordIndex = readCount;
	}
	
	public boolean isEof(){
		return this.isEof;
	}
	public RecordItem getRecordItem(){
		String contentStr = this.recordBuffer.poll();
		// 为空表示recordBuffer中没有数据。
		if (contentStr == null){
			this.fullRecordBuffer();
			// 文件结束
			if (this.isEof)
				return null;
			contentStr = this.recordBuffer.poll();
		}
		return (new RecordItem(contentStr));
	}
	
	public String[] getRecords(int readCount){
		String[] s = new String[readCount];
		int sumSize = 0;
		for(int i=0; i < readCount; i++){
			sumSize = sumSize + (this.recordLengths[i+this.recordIndex]);
		}
		byte[] b = new byte[sumSize];
		try {
			file.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int offset = 0;
		for(int i=0; i<readCount; i++){
			s[i] = new String(b,offset,this.recordLengths[i+this.recordIndex]-1);
			offset = this.recordLengths[i+this.recordIndex];
		}
		// reset recordindex
		this.recordIndex = readCount;
		return s;
	}
	public static void main(String[] args){
		String filename = "D:/tmp/log/S_1_0.stdb";
		StdbFile df = new StdbFile(filename);
		df.readFirstHeader();
		System.out.println(df.getFisrtHeaderInfo());
		df.readSecondHeader();
		String[] records = df.getRecords(5);
		for(String s : records){
			System.out.println(s);
		}
		System.out.println("ok");
	}
}
