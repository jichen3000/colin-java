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

public class Simple {
	public static void selectAll(SqlSession session){
		ArrayList<Blog> bl= (ArrayList<Blog>)session.selectList("com.colin.test.ibatis.BlogMapper.selectBlogList");
		System.out.println(bl.size());
		for (Iterator iterator = bl.iterator(); iterator.hasNext();) {
      Blog blog = (Blog) iterator.next();
      System.out.println(blog);
    }
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
			Blog b = (Blog)session.selectOne("com.colin.test.ibatis.BlogMapper.selectBlog", 1);
			System.out.println(b);
			Blog bi = (Blog)session.selectOne("com.colin.test.ibatis.BlogMapper.selectBlogInteger", new Integer(1));
			System.out.println(bi);
			
			Blog b1 = new Blog("jc1");
			session.insert("com.colin.test.ibatis.BlogMapper.insertBlog",b1);
			Blog b2 = new Blog(1000,"jc1000");
			session.insert("com.colin.test.ibatis.BlogMapper.insertBlogNoSeq",b2);
			b.name = "update!!";
			session.update("com.colin.test.ibatis.BlogMapper.updateBlog",b);
			session.commit();
			selectAll(session);
			
			session.delete("com.colin.test.ibatis.BlogMapper.deleteBlog",b1);
			session.delete("com.colin.test.ibatis.BlogMapper.deleteBlogById",b2.id);
			session.commit();
			selectAll(session);
			
			
		} finally { 
		  session.close(); 
		} 
		System.out.println("ok");
	}
}
