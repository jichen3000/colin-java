package com.colin.test.variable;

public class VariableArity {
  public static int sum(int... args){
    int sum = 0;
    for (int arg : args){
      sum += arg;    
    }
    return sum;
  }
  
  public static void main(String[] args){
    int[] intArr = {3,4,5};
    System.out.printf("sum is: %d %n",VariableArity.sum(intArr));
    System.out.println("ok");
  }
}