package org.jmailing.utilities;

import java.io.File;

import javax.inject.Singleton;

@Singleton
public class FileNameUtilities {

	public String build(String path, String prefix, int index, String extension) {
		return path + File.separator + prefix + String.format("%03d", index)
				+ extension;

	}

}
