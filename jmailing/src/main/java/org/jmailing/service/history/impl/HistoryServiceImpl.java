/**
 * 
 */
package org.jmailing.service.history.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.jmailing.injector.annotation.Extension;
import org.jmailing.injector.annotation.Log;
import org.jmailing.injector.annotation.ProjectPath;
import org.jmailing.injector.provider.HistoryProvider;
import org.jmailing.io.email.EMailIO;
import org.jmailing.model.email.EMail;
import org.jmailing.model.history.History;
import org.jmailing.model.project.MailingProject;
import org.jmailing.service.history.HistoryService;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * @author gregory
 * 
 */
public class HistoryServiceImpl implements HistoryService {
	@Inject
	private HistoryProvider historyProvider;

	@Inject
	@ProjectPath
	private String path;

	@Inject
	private MailingProject project;

	@Inject
	@Extension
	String extension;

	@Inject
	@Log
	private String logExtension;

	@Inject
	private EMailIO emailIO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.service.history.HistoryService#load()
	 */
	@Override
	public List<History> load() {
		String pathProject = path + File.separator + project.getName();
		File f = new File(pathProject);

		String[] l = f.list();
		List<History> histories = new ArrayList<>();
		History h;
		for (String file : l) {
			if (StringUtils.endsWith(file, extension) == false) {
				h = historyProvider.get();
				h.setReference(file);
				histories.add(h);
			}
		}

		return histories;
	}

	@Override
	public String loadLog(History h) throws IOException {
		String pathProject = path + File.separator + project.getName()
				+ File.separator + h.getReference() + File.separator + "log"
				+ logExtension;
		File f = new File(pathProject);
		return Files.toString(f, Charsets.ISO_8859_1);
	}

	@Override
	public List<EMail> loadEMails(History h) throws IOException {
		String pathProject = path + File.separator + project.getName()
				+ File.separator + h.getReference();
		List<EMail> list = new ArrayList<EMail>();
		EMail email = null;
		try {
			int index = 1;
			do {
				email = emailIO.retrieve(pathProject, index);
				index++;
				if (email != null)
					list.add(email);
			} while (email != null);
		} catch (IOException e) {

		}
		return list;
	}

}
