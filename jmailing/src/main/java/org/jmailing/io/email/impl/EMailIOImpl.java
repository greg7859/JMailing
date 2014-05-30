/**
 * 
 */
package org.jmailing.io.email.impl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import org.jmailing.injector.annotation.Extension;
import org.jmailing.injector.annotation.Html;
import org.jmailing.io.email.EMailIO;
import org.jmailing.model.email.EMail;
import org.jmailing.utilities.FileNameUtilities;

/**
 * @author gregory
 * 
 */
public class EMailIOImpl implements EMailIO {

	@Inject
	@Extension
	private String jmailingExtension;

	@Inject
	@Html
	private String htmlExtension;

	@Inject
	private FileNameUtilities filenameUtilities;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.io.email.EMailIO#save(java.lang.String,
	 * org.jmailing.model.email.EMail, int)
	 */
	@Override
	public void save(String path, EMail email, int index) {
		BufferedWriter writer = null;
		try {
			// Create the file
			String filename = filenameUtilities.build(path,
					EMailIOConstants.EMAIL_FILENAME, index,
					jmailingExtension);
			FileOutputStream fos = new FileOutputStream(filename);
			Properties prop = new Properties();
			saveArray(prop, EMailIOConstants.EMAIL_TO, email.getTo());
			saveArray(prop, EMailIOConstants.EMAIL_CC, email.getCc());
			saveArray(prop, EMailIOConstants.EMAIL_BCC, email.getBcc());
			String tmp = email.getSubject();
			prop.setProperty(EMailIOConstants.EMAIL_TITLE,
					tmp != null ? tmp : "");
			for(int i=0; i<email.getAttachments().length;i++) {
				prop.setProperty(EMailIOConstants.ATTACHMENT_FILENAME + "." + i,email.getAttachments()[i].getPath());
				prop.setProperty(EMailIOConstants.ATTACHMENT_NAME + "." + i,email.getAttachments()[i].getName());			
			}
			prop.store(fos, " Email no" + index);
			fos.close();

			filename = filenameUtilities.build(path,
					EMailIOConstants.EMAIL_BODY, index, htmlExtension);
			writer = new BufferedWriter(new FileWriter(filename));
			tmp = email.getBody();
			writer.write(tmp != null ? tmp : "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.io.email.EMailIO#retrieve(java.lang.String, int)
	 */
	@Override
	public EMail retrieve(String path, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	private void saveArray(Properties prop, String key, String[] array) {
		if (array == null)
			return;
		for (int i = 0; i < array.length; i++) {
			prop.setProperty(key + "." + i, array[i]);
		}
	}

}
