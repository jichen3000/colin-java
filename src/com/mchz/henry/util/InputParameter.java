package com.mchz.henry.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.mchz.henry.service.SoitSaver;

public class InputParameter {

	private int afnStart;

	private int afnCount;

	private int maxMassCount;

	private int dbId;

	private int dbVersion;

	private String soitFullPath;

	private String loitDir;

	private Collection loitFileNames = new ArrayList();

	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}

	public int getDbVersion() {
		return dbVersion;
	}

	public void setDbVersion(int dbVersion) {
		this.dbVersion = dbVersion;
	}

	public int getAfnStart() {
		return afnStart;
	}

	public void setAfnStart(int afnStart) {
		this.afnStart = afnStart;
	}

	public int getAfnCount() {
		return afnCount;
	}

	public void setAfnCount(int afnCount) {
		this.afnCount = afnCount;
	}

	public int getMaxMassCount() {
		return maxMassCount;
	}

	public void setMaxMassCount(int maxMassCount) {
		this.maxMassCount = maxMassCount;
	}

	public String getSoitFullPath() {
		return soitFullPath;
	}

	public void setSoitFullPath(String soitFullPath) {
		this.soitFullPath = soitFullPath;
	}

	public String getLoitDir() {
		return loitDir;
	}

	public void setLoitDir(String loitDir) {
		this.loitDir = loitDir;
	}

	public Collection getLoitFileNames() {
		return loitFileNames;
	}

	public void setLoitFileNames(Collection loitFileNames) {
		this.loitFileNames = loitFileNames;
	}

	public static InputParameter getInstance(String path) throws IOException {
		InputParameter inputParameter = new InputParameter();
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.indexOf("afn_start") > -1) {
				inputParameter.afnStart = Integer.valueOf(line.split("=")[1])
						.intValue();
			}
			if (line.indexOf("afn_count") > -1) {
				inputParameter.afnCount = Integer.valueOf(line.split("=")[1])
						.intValue();
			}
			if (line.indexOf("max_mass_count") > -1) {
				inputParameter.maxMassCount = Integer.valueOf(
						line.split("=")[1]).intValue();
			}
			if (line.indexOf("db_id") > -1) {
				inputParameter.dbId = Integer.valueOf(line.split("=")[1])
						.intValue();
			}
			if (line.indexOf("db_version") > -1) {
				inputParameter.dbVersion = Integer.valueOf(line.split("=")[1])
						.intValue();
			}
			if (line.indexOf("soit_fullpath") > -1) {
				inputParameter.soitFullPath = line.split("=")[1];
			}
			if (line.indexOf("loit_dir") > -1) {
				inputParameter.loitDir = line.split("=")[1];
			}
			if (line.indexOf("loit_filenames") > -1) {
				processLoitFilenames(reader, inputParameter);
			}
		}
		return inputParameter;
	}

	private static void processLoitFilenames(BufferedReader reader,
			InputParameter inputParameter) throws IOException {
		String line = null;
		while ((line = reader.readLine()) != null) {
			inputParameter.loitFileNames.add(line);
		}
	}

	public long getSoitDataStartOffset() {
		return SoitSaver.SOIT_HEADER1_SIZE + this.afnCount
				* SoitSaver.SOIT_HEADER2_ITEM_SIZE;
	}

	public File[] getLoitFiles() {
		Collection files = new ArrayList();
		for (Iterator it = this.loitFileNames.iterator(); it.hasNext();) {
			String fileName = (String) it.next();
			File file = new File(this.loitDir + fileName);
			files.add(file);
		}
		return (File[]) files.toArray(new File[files.size()]);
	}

}
