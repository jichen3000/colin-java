package com.colin.test.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.driver.OracleResultSet;
import oracle.sql.BLOB;

class OraConn{
	private static Connection conn;
	public static Connection getConn() {
		return conn;
	}
	public static void initConn(String url, String user, String pwd){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,user,pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException sqle){
			System.err.println(sqle);
		}
	}
	public static void closeConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

class TestOra{
	public static void testConn(){
		try {
			Statement stat = OraConn.getConn().createStatement();
			ResultSet rs = stat.executeQuery("select * from tab where rownum < 10");
			int rowCount = 0;
			while(rs.next()){
				System.out.println(rs.getString("TNAME")+"\t"+rs.getString(1));
				rowCount++;
			}
			System.out.println("共有"+rowCount+"行数据");
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void testCreateTable(){
		try {
			Statement stat = OraConn.getConn().createStatement();
			stat.execute("create table jc_test(id number, "+
					"name varchar2(30), memo varchar2(30))");
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void testInsertRecord(){
		try {
			String sql = "insert into jc_test (id,name) values (:id,\':name\')";
			OraConn.getConn().setAutoCommit(false);
			Statement stat = OraConn.getConn().createStatement();
			for(int i=0; i<3; i++){
				stat.executeUpdate(sql.replace(":id", ""+i).replace(":name", "jc"+i));
			}
			OraConn.getConn().commit();
			OraConn.getConn().setAutoCommit(true);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// 注意batch时，只能使用同类型的语句，比如都是insert的，或都是update的。
	public static void testBatchInsert(){
		try {
			String sql = "insert into jc_test (id,name) values (:id,\':name\')";
			OraConn.getConn().setAutoCommit(false);
			Statement stat = OraConn.getConn().createStatement();
			for(int i=0; i<100; i++){
				stat.addBatch(sql.replace(":id", ""+i).replace(":name", "jc"+i));
			}
			sql = "insert into jc_test1 (id,name) values (1,'jc')";
			stat.addBatch(sql);
			stat.executeBatch();
			OraConn.getConn().commit();
			OraConn.getConn().setAutoCommit(true);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void testSelectNull(){
		try {
			String sql = "select * from tab where rownum<2";
			Statement stat = OraConn.getConn().createStatement();
			ResultSet rs = stat.executeQuery(sql);
			int index = 0;
			while(rs.next()){
				System.out.println(index+":"+rs.getString(1));
				index++;
			}
			if(index==0){
				System.out.println("no value!");
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void testBatchInsert2(){
		try {
			String sql = "insert into jc_test (id,name) values (0,'jc')";
			OraConn.getConn().setAutoCommit(false);
			Statement stat = OraConn.getConn().createStatement();
			for(int i=0; i<10000; i++){
				stat.addBatch(sql);
			}
			stat.executeBatch();
			OraConn.getConn().commit();
			OraConn.getConn().setAutoCommit(true);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void readBlob(String filename){
		try {
			Statement stat = OraConn.getConn().createStatement();
			
			String sql = "select * from jc_test where id = 0";
			ResultSet rs = stat.executeQuery(sql);
			
			rs.next();
			BLOB blob=((OracleResultSet)rs).getBLOB("memo");
			InputStream ins = blob.getBinaryStream();
			
			OutputStream outs = new FileOutputStream(filename);
			
			byte[] buffer = new byte[32528];
			
			int len;
			while((len=ins.read(buffer))!=-1){
				outs.write(buffer,0,len);
			}
			
			outs.close();
			ins.close();
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeBlob(String filename){
		try {
			// 取消Connection对象的auto commit属性
			OraConn.getConn().setAutoCommit(false);
			Statement stat = OraConn.getConn().createStatement();
			// 必须先设置一下，否则blob.getBinaryOutputStream()
			stat.executeUpdate("update jc_test set memo=EMPTY_BLOB() where id=0");
			
			
			String sql = "select * from jc_test where id=0";
			ResultSet rs = stat.executeQuery(sql);
			rs.next();
			BLOB blob = ((OracleResultSet)rs).getBLOB("memo");
			OutputStream outs = blob.getBinaryOutputStream();
			//要先从文件中读进来再写。
			InputStream ins = new FileInputStream(filename);
			
			byte[] buffer = new byte[blob.getBufferSize()];
			System.out.println("buffersize:"+blob.getBufferSize());
			
			int len;
			while((len=ins.read(buffer))!= -1){
				outs.write(buffer,0,len);
			}
			
			outs.close();
			ins.close();
			
			OraConn.getConn().commit();
			OraConn.getConn().setAutoCommit(true);
			
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
public class Simple {
	public static void main(String[] args){
		String url = "jdbc:oracle:thin:@172.16.4.28:1555:drb";
		OraConn.initConn(url, "wzdbra", "wzdbra");
		long startTime = System.currentTimeMillis();
//		TestOra.testConn();
//		TestOra.testCreateTable();
//		TestOra.testInsertRecord();
		TestOra.testSelectNull(); 
//		TestOra.testBatchInsert(); //15828
//		TestOra.testBatchInsert2(); //4078
//		TestOra.writeBlob("D:/tmp/log/S_1_0.stdb");
//		TestOra.readBlob("D:/tmp/log/S_1_0.stdb_out");
		long endTime = System.currentTimeMillis();
		System.out.println("spend seconds:"+(endTime-startTime));
		OraConn.closeConn();
	}
}
