package com.colin.test.method;

class Parent{
  public static void putsName(){
    System.out.println("parent");
  }
}

public class StaticMethodInheritance extends Parent{
  
  public static void putsName(){
    System.out.println("sub");
  }
  
  public static void main(String[] args){
    StaticMethodInheritance.putsName();
    System.out.println("ok");
  }
}