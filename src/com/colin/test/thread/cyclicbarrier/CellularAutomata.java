package com.colin.test.thread.cyclicbarrier;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;


class Board {
  private final List<int[]> items;
  private int step = 1;
  private int[] middleResults;
  private int finalResult = 0;
  public Board(int itemCount){
    items = new ArrayList<int[]>(itemCount);
    for(int i=0; i<itemCount; i++){
      int[] item=new int[]{10*i+1,10*i+2,10*i+3};
      items.add(item);
    }
    middleResults=new int[]{0,0,0};  
  }
  public int getItemCount(){
    System.out.println(items.size());
    return items.size();
  }
  public void commitResult(){
    for(int i = 0; i < middleResults.length; i++){
      this.finalResult = this.finalResult + this.middleResults[i];
    }
    step++;
    System.out.println("commit result:"+this.finalResult);
  }
  
  public void setMiddleResult(int index){
    int[] item = this.items.get(index);
    middleResults[index] = item[step-1]+item[step];
    System.out.println("middle("+index+") value:"+middleResults[index]);    
  }
  
  public void waitFinalResult() throws InterruptedException{
    System.out.println("step:"+step);
    while(!hasDone()){
      System.out.println("will sleep 3");
      Thread.sleep(3000);
    }
  }
  
  public boolean hasDone(){
    return (step >= 2);
  }
}

public class CellularAutomata {
  private class Worker implements Runnable{
    private final Board board;
    private final int index;
    public Worker (Board board,int index){
      System.out.println("worker init index:"+index);
      this.board = board;
      this.index = index;
    }
    public void run(){
      System.out.println("start running");
      while (!board.hasDone()){
        System.out.println("runing: index="+index);
        board.setMiddleResult(index);
        try{
          barrier.await();
        }catch(InterruptedException e){
          e.printStackTrace();
          return;
        }catch(BrokenBarrierException e){
          e.printStackTrace();
          return;
        }
      }
    }
  }
  private final Board mainBoard;
  private final CyclicBarrier barrier;
  private final Worker[] workers;
  public CellularAutomata(Board board){
    this.mainBoard = board;
    int threadCount = this.mainBoard.getItemCount();
    this.barrier = new CyclicBarrier(threadCount,
      new Runnable(){
        public void run(){
          mainBoard.commitResult();
        }
      }
    );
    this.workers = new Worker[threadCount];
    for (int i=0; i<threadCount; i++){
      this.workers[i] = new Worker(this.mainBoard,i);
    }
  }
  
  public void start() throws InterruptedException{
    for(Worker worker : this.workers){
      new Thread(worker).start();
      System.out.println("thread start"+worker);
    }    
    this.mainBoard.waitFinalResult();
  } 

  public static void main(String[] args) throws InterruptedException{
    Board board = new Board(2);
    CellularAutomata ca = new CellularAutomata(board);
    ca.start();
    System.out.println("ok");
  }  
}