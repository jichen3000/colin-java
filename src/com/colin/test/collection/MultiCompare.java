package com.colin.test.collection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class MultiCompare {
//	public static showCollection
	
	public static void main(String[] args) {
		List<Item> list = new ArrayList<Item>();
		list.add(new Item("jc",10000));
		list.add(new Item("lm",5000));
		list.add(new Item("ljj",4000));
		System.out.println(list);
		
		SortedSet<Item> sortedByNameItems = new TreeSet<Item>(new ItemNameComparator());
		sortedByNameItems.addAll(list);
		System.out.println("sorted by item name:");
		System.out.println(sortedByNameItems);
		SortedSet<Item> set = new TreeSet<Item>(new ItemCountComparator());
		set.addAll(list);
		System.out.println("sorted by item count:");
		System.out.println(set);
		System.out.println("ok");
  }
}
class ItemNameComparator implements Comparator<Item>{
	@Override
  public int compare(Item arg0, Item arg1) {
		return arg0.getName().compareTo(arg1.getName());
  }
}
class ItemCountComparator implements Comparator<Item>{
	@Override
  public int compare(Item arg0, Item arg1) {
		return arg0.getCount()-arg1.getCount();
  }
	
}
class Item {
	private String name;
	private int count;
	public Item(String name, int count) {
	  super();
	  this.name = name;
	  this.count = count;
  }
	public String getName() {
  	return name;
  }
	public void setName(String name) {
  	this.name = name;
  }
	public int getCount() {
  	return count;
  }
	public void setCount(int count) {
  	this.count = count;
  }
	public String toString(){
		return "name:"+name+"\tcount:"+count;
	}
}
