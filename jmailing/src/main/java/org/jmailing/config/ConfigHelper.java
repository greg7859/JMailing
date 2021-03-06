package org.jmailing.config;

import java.io.File;

public class ConfigHelper {

	static public void checkDirectory(String folder) {
		File f = new File(folder);
		if (f.exists() == false) {
			f.mkdirs();
		}
	}

	static public String getConfigPath(String filename, boolean checkDir) {
		String folder = getRootStore() + File.separator + Constants.CONF_FOLDER
				+ File.separator;
		if (checkDir) {
			checkDirectory(folder);
		}
		return folder + filename;
	}

	// static public File getConfigPathFile(String filename, boolean checkDir) {
	// String folder = getRootStore() + File.separator + Constants.CONF_FOLDER
	// + File.separator;
	// if (checkDir) {
	// checkDirectory(folder);
	// }
	// return new File(folder+filename);
	// }

	static public String getProjectPath() {
		String folder = getRootStore() + File.separator
				+ Constants.PROJECT_FOLDER + File.separator;
		checkDirectory(folder);
		return folder;
	}

	static public File getProjectPathFile() {
		return new File(getProjectPath());
	}

	static public String getPath(String path) {
		return getRootStore() + File.separator + path;
	}

	static public String getRootStore() {
		return System.getProperty("user.home") + File.separator
				+ Constants.MAIN_FOLDER;
	}

}
