package org.jmailing.config;

import java.io.File;

public class ConfigHelper {

	static public void checkDirectory(String filename) {
		File fname = new File(filename);
		File parent = fname.getParentFile();
		if (parent.exists() == false) {
			parent.mkdirs();
		}
	}

	static public File getConfigPath(String path, boolean checkDir) {
		String filename = getRootStore() + File.separator + "conf"
				+ File.separator + path;
		if (checkDir) {
			checkDirectory(filename);
		}
		return new File(filename);
	}

	static public String getPath(String path) {
		return getRootStore() + File.separator + path;
	}

	static public String getRootStore() {
		return System.getProperty("user.home") + File.separator
				+ Constants.MAIN_FOLDER;
	}

}
