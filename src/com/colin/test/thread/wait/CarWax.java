package com.colin.test.thread.wait;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Car{
	private boolean waxOn = false;
	public synchronized void waxed(){
		// Ready to buff
		waxOn = true; 
		notifyAll();
	}
	
	public synchronized void buffed(){
		waxOn = false;
		notifyAll();
	}
	
	public synchronized void waitForWaxing() 
			throws InterruptedException{
		while(waxOn == false)
			wait();
	}
	
	public synchronized void waitForBuffing() 
			throws InterruptedException{
		while(waxOn == true)
			wait();
	}
}

class WaxOn implements Runnable {
	private Car car;
	public WaxOn(Car c){
		car = c;
	}
	public void run(){
		try {
			while(!Thread.interrupted()){
				System.out.println("Wax On! ");
				Thread.sleep(200);
				car.waxed();
				car.waitForBuffing();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Exiting via interrupt");
//			e.printStackTrace();
		}
		System.out.println("Ending Wax On task");
	}
}
class WaxOff implements Runnable {
	private Car car;
	public WaxOff(Car c){
		car = c;
	}
	public void run(){
		try {
			while(!Thread.interrupted()){
				car.waitForWaxing();
				System.out.println("Wax Off! ");
				Thread.sleep(200);
				car.buffed();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Exiting via interrupt");
//			e.printStackTrace();
		}
		System.out.println("Ending Wax Off task");
	}
}

public class CarWax {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOff(car));
		exec.execute(new WaxOn(car));
		exec.execute(new WaxOn(car));
		Thread.sleep(300);
		exec.shutdownNow();
	}
}
