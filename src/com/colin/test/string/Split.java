package com.colin.test.string;

import java.util.ArrayList;
import java.util.Collections;

public class Split {
	public static void main(String[] args){
		String[] strArray = "a b c d".split(" ");
		for (String s : strArray)
			System.out.println(s);
		ArrayList<String> strList = new ArrayList<String>();
//		strList.addAll(strArray);
		Collections.addAll(strList, strArray);
		for (String s : strList)
			System.out.println(s);
	}
}
