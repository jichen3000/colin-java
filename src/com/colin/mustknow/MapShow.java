package com.colin.mustknow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class MapShow {
	public static void insertEntryToMap(Map<Integer,String> map){
	  for (int i = 10; i >= 0; i--) {
		  map.put(i, String.valueOf(i));
    }
	}
	public static void showMap(Map<Integer,String> map){
		for(Iterator<Entry<Integer,String>> iterator = map.entrySet().iterator(); iterator.hasNext();){
			Entry<Integer,String> entry = (Entry<Integer,String>)iterator.next();
	    System.out.println("key\t"+entry.getKey()+": value\t"+entry.getValue());
		}
	}
	public static void treeMapShow(){
		//TreeMap实现SortMap接口，能够把它保存的记录根据键排序,默认是按键值的升序排序，
		//也可以指定排序的比较器，当用Iterator 遍历TreeMap时，得到的记录是排过序的。
	  Map<Integer,String> treeMap = new TreeMap<Integer,String>();
	  insertEntryToMap(treeMap);
		System.out.println("treeMapShow");
	  showMap(treeMap);
	}
	public static void hashMapShow(){
		//在Map 中插入、删除和定位元素，HashMap 是最好的选择。
		Map<Integer,String> hashMap = new HashMap<Integer,String>();
		insertEntryToMap(hashMap);
		System.out.println("hashMapShow");
		showMap(hashMap);
	}
	public static void linkedHashMapShow(){
		//LinkedHashMap保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，
		//先得到的记录肯定是先插入的.
		//也可以在构造时用带参数，按照应用次数排序。
		Map<Integer,String> linkedHashMap = new LinkedHashMap<Integer,String>();
		insertEntryToMap(linkedHashMap);
		System.out.println("linkedHashMapShow");
		showMap(linkedHashMap);
	}
	public static void main(String[] args) {
		hashMapShow();
		linkedHashMapShow();
		treeMapShow();
	  System.out.println("ok");
  }
}
