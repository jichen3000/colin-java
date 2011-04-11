package com.mchz.henry.service;

import java.io.IOException;
import java.util.List;

import com.mchz.henry.domain.LoitRecord;

public interface LoitRecordService {

	public static final int LOIT_HEADER_SIZE = 1024;

	public abstract LoitRecord read() throws IOException;

	public abstract List splitLoitRecordToLoitItem(LoitRecord loitRecord);

	public abstract void close();

}