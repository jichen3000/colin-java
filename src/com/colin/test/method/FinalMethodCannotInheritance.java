package com.colin.test.method;

class Parent{
  public final void putsName(){
    System.out.println("parent");
  }
}

public class FinalMethodCannotInheritance extends Parent{
  // this will report error
  //public void putsName(){
  //}
  
  public static void main(String[] args){
    System.out.println("ok");
  }
}