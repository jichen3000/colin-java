package com.mchz.henry.stream.apply.domain;

public interface IDbraFile {
	public static final int firstHeaderSize = 512;
	public void readFirstHeader();
}
