package com.colin.test.method;

import java.util.LinkedList;

class Item{
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Item(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	private String name;
	public String toString(){
		return "id:" + id + "\tname:"+name;
	}
}
public class PrameterTest {
	/**
	 * @param items
	 * @param lastItem
	 * @return
	 */
	public static int getList(LinkedList<Item> items, Item lastItem, Item alertItem, int vari){
		int result = 0;
		
		for(int i = 0; i<10; i++){
			items.add(new Item(i, "jc"+i));
		}
		
		lastItem = items.getLast();
		alertItem.setName("mm");
		result = items.size();
		vari = 11;
		return result;
	}
	public static void main(String[] args){
		Item lastItem=null;
		Item alertItem=new Item(100,"jc");
		LinkedList<Item> items = new LinkedList<Item>();
		items.add(alertItem);
		int i = 10;
		
		int count = PrameterTest.getList(items,lastItem,alertItem,i);
		
		for(Item item : items){
			System.out.println(item);
		}
		System.out.println("last:");
		System.out.println(lastItem);
		System.out.println(alertItem);
		System.out.println(i);
	}
}
