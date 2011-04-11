package com.colin.test.ibatis;

public class ClobTable {
	public int id;
	public String name;
	public String text;
	public ClobTable() {
  }
	public ClobTable(String name, String text) {
	  super();
	  this.name = name;
	  this.text = text;
  }
	public String toString(){
		return this.getClass().getName() +
				" id:"+this.id + " name:"+this.name+
				" text:"+this.text;
	}

}
