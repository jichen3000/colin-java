package com.colin.test.thread.futuretask;

import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Preloader {
  private final FutureTask<String> future = 
    new FutureTask<String>( new Callable<String>() {
      public String call() throws InterruptedException{
        Thread.sleep(10000);
        return "info";
      }      
    }
  );
  
  private final Thread thread = new Thread(future);
  public void start(){
    thread.start();
  }
  public String get() throws InterruptedException {
    try{ 
      return future.get();
    } catch (ExecutionException e) {
      e.printStackTrace();
      //throw launderThrowable(e.getCause());
    }    
    return null;
  }
  
  public static void main(String[] args) throws InterruptedException{
    Preloader preloader = new Preloader();
    preloader.start();
    System.out.println(preloader.get());
  }
}
