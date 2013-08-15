package org.jmailing.config;

import java.io.File;

public class ConfigHelper {

	static private void checkDirectory(String folder) {
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
		return folder+filename;
	}
	
//	static public File getConfigPathFile(String filename, boolean checkDir) {
//		String folder = getRootStore() + File.separator + Constants.CONF_FOLDER
//				+ File.separator;
//		if (checkDir) {
//			checkDirectory(folder);
//		}
//		return new File(folder+filename);
//	}

	static public String getPath(String path) {
		return getRootStore() + File.separator + path;
	}

	static public String getRootStore() {
		return System.getProperty("user.home") + File.separator
				+ Constants.MAIN_FOLDER;
	}

}
