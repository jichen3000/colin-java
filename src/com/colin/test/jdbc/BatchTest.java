package com.colin.test.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

public class BatchTest {
	public static void testBatchInsert2(){
		try {
			String sql = "insert into jc (id,name) values (0,'jc')";
			OraConn.getConn().setAutoCommit(false);
			Statement stat = OraConn.getConn().createStatement();
			for(int i=0; i<10; i++){
				stat.addBatch(sql);
			}
			stat.addBatch("update jc set name='mm' where id = 1");
			stat.executeBatch();
			OraConn.getConn().commit();
			OraConn.getConn().setAutoCommit(true);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		String url = "jdbc:oracle:thin:@172.16.4.98:1521:xe";
		OraConn.initConn(url, "colin_test", "colin_test");
		long startTime = System.currentTimeMillis();
		BatchTest.testBatchInsert2();
		long endTime = System.currentTimeMillis();
		System.out.println("spend seconds:"+(endTime-startTime));
		OraConn.closeConn();
	}
}
