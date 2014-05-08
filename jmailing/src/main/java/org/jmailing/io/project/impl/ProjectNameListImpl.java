package org.jmailing.io.project.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.jmailing.injector.annotation.Extension;
import org.jmailing.injector.annotation.ProjectPath;
import org.jmailing.io.project.ProjectNameList;

public class ProjectNameListImpl implements ProjectNameList {
	@Inject
	@Extension
	String extension;

	@Inject
	@ProjectPath
	String path;

	@Override
	public Collection<String> list() {

		File folder = new File(path);
		String[] files = folder.list(new ExtensionFilter());

		ArrayList<String> projectNames = new ArrayList<>();
		for (String string : files) {
			projectNames.add(string.replace(extension, ""));
		}

		return projectNames;
	}

	class ExtensionFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			if (name.lastIndexOf('.') > 0) {
				// get last index for '.' char
				int lastIndex = name.lastIndexOf('.');

				// get extension
				String str = name.substring(lastIndex);

				// match path name extension
				if (str.equals(extension)) {
					return true;
				}
			}
			return false;
		}

	}

}
