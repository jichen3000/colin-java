package com.colin.test.ibatis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class StreamTest {
	public static void main(String[] args) throws IOException{
		String resource = "com/colin/test/ibatis/config.xml"; 
		Reader reader = Resources.getResourceAsReader(resource);
		//
		String ors = "src/com/colin/test/ibatis/ors.conf";
		Properties prop = new Properties();
		prop.load(new FileInputStream(ors));
		// sqlSessionFactory 相当于jdbc中的connection
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,prop); 
		
		RegisterFileModel.setSessionFactory(sqlSessionFactory);
		RegisterFileModel.selectNeedMaxs();
		
		System.out.println("ok");
	}

}
