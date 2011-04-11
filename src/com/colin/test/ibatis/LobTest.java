package com.colin.test.ibatis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class LobTest {
	public static void selectAll(SqlSession session){
		ArrayList<ClobTable> bl= (ArrayList<ClobTable>)session.selectList(
				"com.colin.test.ibatis.ClobTableMapper.selectBlogList");
		System.out.println(bl.size());
		for (Iterator iterator = bl.iterator(); iterator.hasNext();) {
			ClobTable blog = (ClobTable) iterator.next();
      System.out.println(blog);
    }
	}
	
	public static String getLongString(){
		int count = 300;
		String str = "aaaaaaaaaaaaaaaaaaaa";
		String result = "";
		for (int i = 0; i < count; i++) {
	    result = result+str+Integer.toString(i);
    }
		return result;
	}
	
	public static void main(String[] args) throws IOException{
		String resource = "com/colin/test/ibatis/config.xml"; 
		Reader reader = Resources.getResourceAsReader(resource);
		//
		String ors = "src/com/colin/test/ibatis/ors.conf";
		Properties prop = new Properties();
		prop.load(new FileInputStream(ors));
		// sqlSessionFactory 相当于jdbc中的connection
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,prop); 
		
		// SqlSession相当于jdbc中的Statement
		SqlSession session = sqlSessionFactory.openSession(); 
		try { 
//			ClobTable b = (ClobTable)session.selectOne("com.colin.test.ibatis.ClobTableMapper.selectBlog", 1);
			ClobTable b = (ClobTable)session.selectOne("com.colin.test.ibatis.ClobTableMapper.selectBlog", 1);
			System.out.println(b);
			
//			selectAll(session);
			
//			ClobTable ct1 = new ClobTable("jcxxx","xxxx");
//			session.insert("com.colin.test.ibatis.ClobTableMapper.insertBlog",ct1);
//			session.commit();
//			String longstr = getLongString();
//			System.out.println(longstr.length());
//			ClobTable ct1 = new ClobTable("jcxxx",longstr);
//			session.insert("com.colin.test.ibatis.ClobTableMapper.insertBlog",ct1);
//			
//			b.text = "nnnn"+longstr;
//			session.update("com.colin.test.ibatis.ClobTableMapper.updateBlog",b);
//			
//			session.commit();
//			selectAll(session);
//			
//			
//			session.delete("com.colin.test.ibatis.ClobTableMapper.deleteBlog",ct1);
//			session.commit();
//			selectAll(session);
		  b.name = null;
			ClobTable b1 = (ClobTable)session.selectOne("com.colin.test.ibatis.ClobTableMapperExtend.selectOne", b);
			System.out.println(b1);
			
			b.name = null;
		  b.text = null;
			session.update("com.colin.test.ibatis.ClobTableMapperExtend.update", b);
			session.commit();
			System.out.println(b);
		} finally { 
		  session.close(); 
		} 
		System.out.println("ok");
	}

}
