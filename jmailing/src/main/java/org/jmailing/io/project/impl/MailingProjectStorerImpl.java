/**
 * 
 */
package org.jmailing.io.project.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import org.jmailing.config.ConfigHelper;
import org.jmailing.injector.annotation.Extension;
import org.jmailing.injector.annotation.ProjectPath;
import org.jmailing.io.project.MailingProjectStorer;
import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.EmailMailingProjectPart;
import org.jmailing.model.project.MailingConfigurationPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceVariable;

/**
 * @author gregory
 * 
 */
public class MailingProjectStorerImpl implements MailingProjectStorer {

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
	public MailingProjectStorerImpl() {
	}

	@Override
	public void save(String name) throws IOException {
		this.nameProject = name;
		saveProject();
		saveSource();
		saveAttachment();
		saveMail();
		saveMailing();
	}

	private void saveProject() throws IOException {
		// Create the file
		FileOutputStream fos = new FileOutputStream(getProjectFolder()
				+ extension);
		Properties prop = new Properties();
		prop.store(fos, " Mailing Project");
		fos.close();

		ConfigHelper.checkDirectory(getProjectFolder());

	}

	private void saveSource() throws IOException {
		// Create the file
		FileOutputStream fos = new FileOutputStream(getProjectFolder()
				+ File.separator + MailingProjectIOConstants.SOURCE_FILENAME + extension);
		Properties prop = new Properties();
		SourceVariable[] sourceVariables = project
				.getSourceMailingProjectPart().getSourceVariables();
		int counter = 0;
		for (SourceVariable sourceVariable : sourceVariables) {
			prop.setProperty(MailingProjectIOConstants.SOURCE_NAME+"." + counter, sourceVariable.getName());
			prop.setProperty(MailingProjectIOConstants.SOURCE_INDEX+"." + counter,
					Integer.toString(sourceVariable.getIndex()));
			counter++;
		}
		prop.store(fos, " Source Mailing Project");
		fos.close();
	}

	private void saveAttachment() throws IOException {
		// Create the file
		FileOutputStream fos = new FileOutputStream(getProjectFolder()
				+ File.separator + MailingProjectIOConstants.ATTACHMENT_FILENAME + extension);
		Properties prop = new Properties();
		AttachmentMailingProjectPart attachment = project
				.getAttachmentMailingProjectPart();
		prop.setProperty(MailingProjectIOConstants.ATTACHMENT_FORMAT, attachment.getFilenameFormat());
		prop.setProperty(MailingProjectIOConstants.ATTACHMENT_NUMBER,
				Integer.toString(attachment.getNumberOfPageOfSplit()));

		prop.store(fos, " Attachment Mailing Project");
		fos.close();
	}

	private void saveMail() throws IOException {
		BufferedWriter writer = null;
		try {
			// Create the file
			FileOutputStream fos = new FileOutputStream(getProjectFolder()
					+ File.separator + MailingProjectIOConstants.EMAIL_FILENAME + extension);
			Properties prop = new Properties();
			EmailMailingProjectPart email = project
					.getEMailMailingProjectPart();
			prop.setProperty(MailingProjectIOConstants.EMAIL_TO, email.getTo());
			prop.setProperty(MailingProjectIOConstants.EMAIL_CC, email.getCc());
			prop.setProperty(MailingProjectIOConstants.EMAIL_TITLE, email.getTitle());

			prop.store(fos, " EMail Mailing Project");
			fos.close();

			writer = new BufferedWriter(new FileWriter(getProjectFolder()
					+ File.separator + MailingProjectIOConstants.EMAIL_BODY + extension));
			writer.write(email.getBody());
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	private void saveMailing() throws IOException {
		// Create the file
		FileOutputStream fos = new FileOutputStream(getProjectFolder()
				+ File.separator + MailingProjectIOConstants.MAILING_FILENAME + extension);
		Properties prop = new Properties();
		MailingConfigurationPart mailing = project
				.getMailingConfigurationPart();
		prop.setProperty(MailingProjectIOConstants.MAILING_NUMBER,
				Integer.toString(mailing.getNumberMailPerHour()));

		prop.store(fos, " Source Mailing Project");
		fos.close();
	}

	private String getProjectFolder() {
		return path + File.separator + nameProject;
	}

}
