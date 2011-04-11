package com.colin.test.thread.threadtestunit;

public class ClassWithThreadingProblem {
	int nextId;
	public int takeNextId(){
		return nextId++;
	}
}
