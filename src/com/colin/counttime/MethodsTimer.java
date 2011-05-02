package com.colin.counttime;

public class MethodsTimer {
  private final Method[] methods;
  
  public Methodstimer(Method[] methods){
    this.methods = methods;
  }
  
  pirvate static final int MAXINUM_SIZE = 1000;
  
  public void report() throws Exception {
    for (Method each : methods){
      System.out.print(each.getName() + "\t");
    }
  }
}