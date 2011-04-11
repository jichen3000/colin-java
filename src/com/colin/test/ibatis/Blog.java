package com.colin.test.ibatis;

public class Blog {
	
	public Blog() {
  }
	public Blog(int id, String name) {
	  super();
	  this.id = id;
	  this.name = name;
  }
	public Blog(String name) {
		super();
		this.name = name;
	}
	public int id;
	public String name;
	public String toString(){
		return this.getClass().getName() +
				" id:"+this.id + " name:"+this.name;
	}
	
//	public static Blog selectOne(int id){
//		
//	}
}
