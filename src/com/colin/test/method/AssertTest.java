package com.colin.test.method;

public class AssertTest{
  public static void test(int count, Integer next){
    assert count>0;
    assert next!=null;
    System.out.printf("count: %d, next %d%n",count,next);
  }
  
  public static void main(String[] args){
    AssertTest.test(0,13);
    AssertTest.test(1,null);
    AssertTest.test(0,null);
    AssertTest.test(1,null);
    System.out.println("ok");
  }
}