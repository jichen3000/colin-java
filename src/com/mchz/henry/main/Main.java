package com.mchz.henry.main;

import java.io.File;

import com.mchz.henry.service.LoitBitSortService;
import com.mchz.henry.service.LoitRecordService;
import com.mchz.henry.service.LoitRecordServiceImpl;
import com.mchz.henry.service.SoitSaver;
import com.mchz.henry.service.SoitSaverImpl;
import com.mchz.henry.util.InputParameter;

public class Main {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("missing input file argument");
			System.exit(1);
		}
		File file = new File(args[0]);
		if (!file.exists() && !file.isFile()) {
			System.err.println("no such input file");
			System.exit(1);
		}
		LoitBitSortService loitBitSortService = null;
		try {
			InputParameter inputParameter = InputParameter.getInstance(file
					.getAbsolutePath());
			File output = new File(inputParameter.getSoitFullPath());
			LoitRecordService loitRecordService = new LoitRecordServiceImpl(
					inputParameter.getLoitFiles());
			SoitSaver soitSaver = new SoitSaverImpl(output, inputParameter);
			loitBitSortService = new LoitBitSortService(loitRecordService,
					soitSaver, inputParameter);
			loitBitSortService.sort();
			loitBitSortService.saveOptimised();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		} finally {
			loitBitSortService.close();
		}
	}
}
