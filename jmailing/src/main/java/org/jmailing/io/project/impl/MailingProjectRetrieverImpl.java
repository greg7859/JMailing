/**
 * 
 */
package org.jmailing.io.project.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import org.jmailing.injector.annotation.Extension;
import org.jmailing.injector.annotation.ProjectPath;
import org.jmailing.io.project.MailingProjectRetriever;
import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.EmailMailingProjectPart;
import org.jmailing.model.project.MailingConfigurationPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceMailingProjectPart;
import org.jmailing.model.project.SourceVariable;

/**
 * @author gregory
 * 
 */
public class MailingProjectRetrieverImpl implements MailingProjectRetriever {

	@Inject
	@Extension
	String extension;

	@Inject
	MailingProject project;

	@Inject
	@ProjectPath
	String path;

	private String nameProject;

	/**
	 * 
	 */
	public MailingProjectRetrieverImpl() {
	}

	@Override
	public void load(String name) throws IOException {
		this.nameProject = name;
		loadProject();
		loadSource();
		loadAttachment();
		loadMail();
		loadMailing();
	}

	private void loadProject() throws IOException {
		// Create the file
		FileInputStream fis = new FileInputStream(getProjectFolder()
				+ extension);
		Properties prop = new Properties();
		prop.load(fis);
		fis.close();
		
		project.setName(this.nameProject);
	}

	private void loadSource() throws IOException {
		// Create the file
		FileInputStream fis = new FileInputStream(getProjectFolder()
				+ File.separator + MailingProjectIOConstants.SOURCE_FILENAME
				+ extension);
		Properties prop = new Properties();
		prop.load(fis);
		fis.close();

		SourceMailingProjectPart source = project.getSourceMailingProjectPart();
		String name = null;
		for (int counter = 0; prop
				.getProperty(MailingProjectIOConstants.SOURCE_NAME + "."
						+ counter) != null; counter++) {
			name = prop.getProperty(MailingProjectIOConstants.SOURCE_NAME + "."
					+ counter);
			SourceVariable sv = source.getSourceVariable(name);
			int index = Integer.parseInt(prop
					.getProperty(MailingProjectIOConstants.SOURCE_INDEX + "."
							+ counter));
			sv.setIndex(index);
		}
	}

	private void loadAttachment() throws IOException {
		// Open the file
		FileInputStream fis = new FileInputStream(getProjectFolder()
				+ File.separator
				+ MailingProjectIOConstants.ATTACHMENT_FILENAME + extension);
		Properties prop = new Properties();
		prop.load(fis);
		fis.close();

		AttachmentMailingProjectPart attachment = project
				.getAttachmentMailingProjectPart();
		attachment.setFilenameFormat(prop
				.getProperty(MailingProjectIOConstants.ATTACHMENT_FORMAT));
		int index = Integer.parseInt(prop
				.getProperty(MailingProjectIOConstants.ATTACHMENT_NUMBER));
		attachment.setNumberOfPageOfSplit(index);
	}

	private void loadMail() throws IOException {
		BufferedReader reader = null;
		try {
			// Open the file
			FileInputStream fis = new FileInputStream(getProjectFolder()
					+ File.separator
					+ MailingProjectIOConstants.EMAIL_FILENAME + extension);
			Properties prop = new Properties();
			prop.load(fis);
			fis.close();

			EmailMailingProjectPart email = project
					.getEMailMailingProjectPart();
			email.setTo(prop.getProperty(MailingProjectIOConstants.EMAIL_TO));
			email.setCc(prop.getProperty(MailingProjectIOConstants.EMAIL_CC));
			email.setTitle(prop
					.getProperty(MailingProjectIOConstants.EMAIL_TITLE));
			reader = new BufferedReader(new FileReader(getProjectFolder()
					+ File.separator + MailingProjectIOConstants.EMAIL_BODY
					+ extension));
			String line = null;
			StringBuffer buf = new StringBuffer();
			do {
				line = reader.readLine();
				if (line != null)
					buf.append(line);
			} while (line != null);
			email.setBody(buf.toString());

		} finally {
			if (reader != null)
				reader.close();
		}
	}

	private void loadMailing() throws IOException {
		// Create the file
		FileInputStream fis = new FileInputStream(getProjectFolder()
				+ File.separator + MailingProjectIOConstants.MAILING_FILENAME + extension);
		Properties prop = new Properties();
		prop.load(fis);
		fis.close();
		MailingConfigurationPart mailing = project
				.getMailingConfigurationPart();
		int index = Integer.parseInt(prop.getProperty(MailingProjectIOConstants.MAILING_NUMBER));
		mailing.setNumberMailPerHour( index); 
	}

	private String getProjectFolder() {
		return path + File.separator + nameProject;
	}

}
