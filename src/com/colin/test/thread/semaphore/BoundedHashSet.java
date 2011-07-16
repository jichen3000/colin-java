package com.colin.test.thread.semaphore;


import java.util.concurrent.Semaphore;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

public class BoundedHashSet<T> {
  private final Set<T> set;
  private final Semaphore semaphore;
  
  public BoundedHashSet(int count){
    this.set = Collections.synchronizedSet(new HashSet<T>());
    this.semaphore = new Semaphore(count);
  }
  
  public boolean add(T object) throws InterruptedException {
    this.semaphore.acquire();
    System.out.println("acquire semaphore");
    boolean wasAdded = false;
    try {
      wasAdded = this.set.add(object);
      return wasAdded;
    } finally {
      if (!wasAdded){
        this.semaphore.release();
        System.out.println("release semaphore");
      }
    }
  }
  
  public boolean remove(Object object) {
    boolean wasRemoved = set.remove(object);
    if (wasRemoved){
      this.semaphore.release();
      System.out.println("release semaphore");
    }
    return wasRemoved;
  }
  
  public static void main(String[] args){
    final int boundCount = 3;
    final int threadCount = 4;
    final BoundedHashSet<String> bounded = new BoundedHashSet<String>(boundCount); 
    for ( int i = 0; i < threadCount; i++){
      Thread t = new Thread("thread"+i){
        public void run(){
          final String str1 = getName()+") 1";
          final String str2 = getName()+") 2";
          final String str3 = getName()+") 3";
          try{
            bounded.add(str1);
            bounded.add(str2);
            bounded.add(str3);
          } catch(InterruptedException e){
            e.printStackTrace();
          }
          bounded.remove(str1);
          bounded.remove(str2);
          bounded.remove(str3);
        }
      };
      System.out.println("thread "+i+" started!");
      t.start();
    }
    try{
      Thread.sleep(3000);
    } catch(InterruptedException e){
      e.printStackTrace();
    }
    System.out.println("ok");
  }
}