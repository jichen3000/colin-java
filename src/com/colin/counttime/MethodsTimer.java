package com.colin.counttime;

import java.lang.reflect.Method;

public class MethodsTimer {
  private final Method[] methods;
  
  public MethodsTimer(Method[] methods){
    this.methods = methods;
  }
  
  private static final int MAXINUM_SIZE = 1000;
  
  public void report() throws Exception {
    for (Method each : methods){
      System.out.print(each.getName() + "\t");
    }
  }
}