package com.colin.test.containers;

public class ArrayAsign {
	public int readThreadCount;
	public int oracleApplyCount;
	public int[][] asignOracleApply(){
		int[][] result = new int[readThreadCount][];
		int quotient = oracleApplyCount/readThreadCount;
		int remainder = oracleApplyCount%readThreadCount;
		for (int i = 0; i < result.length; i++) {
	    int size = quotient;
	    if(remainder >= i+1)
	    	size++;
	    result[i] = new int[size];
	    for (int j = 0; j < result[i].length; j++) {
	      result[i][j] = j*readThreadCount+i+1;
      }
    }
		return result;
	}
	public static void main(String[] args){
		ArrayAsign aa = new ArrayAsign();
		aa.readThreadCount = 3;
		aa.oracleApplyCount = 16;
		int[][] result = aa.asignOracleApply();
		for (int i = 0; i < result.length; i++) {
	    for (int j = 0; j < result[i].length; j++) {
	      System.out.println("value["+i+"]["+j+"]:"+result[i][j]);
      }
    }
	}
}
