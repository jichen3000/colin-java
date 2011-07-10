package com.colin.test.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class TestHarness {
  public static long timeTasks(int threadCount, final Runnable task)
    throws InterruptedException{
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(threadCount);
    
    for( int i = 0; i < threadCount; i++){
      Thread t = new Thread(){
        public void run(){
          try{
            startGate.await();
            try{
              task.run();
            } finally {
              endGate.countDown();
            }
          } catch (InterruptedException e){
            e.printStackTrace();
          }
        }
      };
      System.out.println("thread will start!");
      t.start();
    }
    long start = System.nanoTime();
    startGate.countDown();
    endGate.await();
    long end = System.nanoTime();
    return end-start;
  }
  
  public static void main(String[] args){
    final int taskIndex = 1;
    try {
      TestHarness.timeTasks(5,new Runnable(){
        public void run(){
          System.out.println(taskIndex);
        }
      });
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("ok");
  }
}