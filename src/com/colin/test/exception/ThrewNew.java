package com.colin.test.exception;


class MyException extends Exception{
	/**
   * 
   */
  private static final long serialVersionUID = 1L;

	public MyException(String mes){
		super(mes);
	}
}
public class ThrewNew {
	public static void main(String[] args) throws MyException{
		try {
	    Class.forName("mm");
    } catch (ClassNotFoundException e) {
    	Exception se = new MyException("mm!");
    	se.initCause(e);
    	throw (MyException)se;
    }
	}
}
