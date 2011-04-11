package com.colin.test.other;
enum OracleColumnType {DATE, TIMESTAMP, NUMBER, VARCHAR2, CLOB, BLOB, ROW, UNKNOW}

public class EnumTest {
	private String name;
	private OracleColumnType myType;
	@Override
  public String toString() {
	  return "EnumTest [myType=" + myType + ", name=" + name + "]";
  }
	public EnumTest(String name) {
	  super();
	  this.name = name;
	  try {
		  this.myType = (OracleColumnType.valueOf(this.name));
    } catch (IllegalArgumentException e) {
    	this.myType = OracleColumnType.UNKNOW; 
    }
  }
	public void perform(){
		switch(this.myType){
		case DATE:
			System.out.println("dateeee");
			break;
		case NUMBER:
			System.out.println("numberrrr");
		case TIMESTAMP:
			System.out.println("222");
		default:
			System.out.println("tttt");
		}
	}
	public static void main(String[] argv){
		EnumTest et = new EnumTest("TIMESTAMP");
		System.out.println(et);
		et.perform();
		EnumTest et2 = new EnumTest("2");
		System.out.println(et2);
		et2.perform();
		System.out.println("ok");
	}
}
