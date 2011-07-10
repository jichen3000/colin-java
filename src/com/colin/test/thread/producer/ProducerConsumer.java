package com.colin.test.thread.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.lang.Runnable;
import java.lang.InterruptedException;


class Producer implements Runnable{
  private static final int COUNT = 100;
  private final BlockingQueue<String> queue;
  private final String seed;
  private int id;
  
  Producer(BlockingQueue<String> queue, String seed){
    this.queue = queue;
    this.seed = seed;
    this.id = 0;
  }
  
  public void run(){
    try{
      for(int i = 0; i<COUNT; i++){
        queue.put(produce());
      }
      
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  private String produce(){
    id++;
    return seed+"id:"+id;
  }    
}

class Consumer implements Runnable {
  private final BlockingQueue<String> queue;
  
  Consumer(BlockingQueue<String> queue){
    this.queue = queue;
  }
  
  public void run(){
    try{
      while(true){
        consume(queue.take());
      }
    } catch (InterruptedException e){
      e.printStackTrace();
    }
      
  }
  
  private void consume(String seed){
    System.out.println("conssume : " + seed);
  }
}

public class ProducerConsumer{
  public static void main(String[] args){
    BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
    Producer p1 = new Producer(queue,"p1 ");
    Producer p2 = new Producer(queue,"p2 ");
    Consumer c1 = new Consumer(queue);
    Consumer c2 = new Consumer(queue);
    new Thread(p1).start();
    new Thread(p2).start();
    new Thread(c1).start();
    new Thread(c2).start();
    System.out.println("OK");
  }
}