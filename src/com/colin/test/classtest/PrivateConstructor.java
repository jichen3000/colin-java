package com.colin.test.classtest;

class PConstructor{
  // this will report error
  private PConstructor(){
    System.out.println("pc");
  }
}

public class PrivateConstructor extends PConstructor{
  
}
