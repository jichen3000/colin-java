package com.colin.test.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DirList {
	public static void main(String[] args){
		File path = new File("D:/tmp");
		String[] list = path.list(new MyFilter(".*\\.sh"));
//		String[] list = path.list();
		for(String dirItem : list)
			System.out.println(dirItem);
		// sort list
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		System.out.println("ok");
	}
}

class MyFilter implements FilenameFilter {
	private Pattern pattern;
	public MyFilter(String regex){
		pattern = Pattern.compile(regex);
	}
	public boolean accept(File dir, String name){
		return pattern.matcher(name).matches();
	}
}