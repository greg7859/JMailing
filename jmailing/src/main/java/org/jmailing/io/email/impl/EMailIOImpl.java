/**
 * 
 */
package org.jmailing.io.email.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.jmailing.injector.annotation.Extension;
import org.jmailing.injector.annotation.Html;
import org.jmailing.injector.provider.AttachmentProvider;
import org.jmailing.injector.provider.EMailProvider;
import org.jmailing.io.email.EMailIO;
import org.jmailing.model.email.Attachment;
import org.jmailing.model.email.EMail;
import org.jmailing.utilities.FileNameUtilities;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

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

	@Inject
	private EMailProvider emailProvider;

	@Inject
	private AttachmentProvider attachmentProvider;

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
					EMailIOConstants.EMAIL_FILENAME, index, jmailingExtension);
			FileOutputStream fos = new FileOutputStream(filename);
			Properties prop = new Properties();
			saveArray(prop, EMailIOConstants.EMAIL_TO, email.getTo());
			saveArray(prop, EMailIOConstants.EMAIL_CC, email.getCc());
			saveArray(prop, EMailIOConstants.EMAIL_BCC, email.getBcc());
			String tmp = email.getSubject();
			prop.setProperty(EMailIOConstants.EMAIL_TITLE, tmp != null ? tmp
					: "");
			for (int i = 0; i < email.getAttachments().length; i++) {
				prop.setProperty(
						EMailIOConstants.ATTACHMENT_FILENAME + "." + i,
						email.getAttachments()[i].getPath());
				prop.setProperty(EMailIOConstants.ATTACHMENT_NAME + "." + i,
						email.getAttachments()[i].getName());
			}
			prop.store(fos, " Email no" + index);
			fos.close();

			filename = filenameUtilities.build(path,
					EMailIOConstants.EMAIL_BODY, index, htmlExtension);
			writer = Files.newWriter(new File(filename), Charsets.ISO_8859_1);
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
	public EMail retrieve(String path, int index) throws IOException {
		// Create the file
		String filename = filenameUtilities.build(path,
				EMailIOConstants.EMAIL_FILENAME, index, jmailingExtension);
		FileInputStream fis = new FileInputStream(filename);
		Properties prop = new Properties();
		prop.load(fis);
		fis.close();

		EMail email = emailProvider.get();

		List<String> tos = loadArray(prop, EMailIOConstants.EMAIL_TO);
		for (String string : tos) {
			email.addTo(string);
		}
		List<String> ccs = loadArray(prop, EMailIOConstants.EMAIL_CC);
		for (String string : ccs) {
			email.addCc(string);
		}
		List<String> bccs = loadArray(prop, EMailIOConstants.EMAIL_BCC);
		for (String string : bccs) {
			email.addBcc(string);
		}

		email.setSubject(prop.getProperty(EMailIOConstants.EMAIL_TITLE));
		List<String> paths = loadArray(prop,
				EMailIOConstants.ATTACHMENT_FILENAME);
		List<String> names = loadArray(prop, EMailIOConstants.ATTACHMENT_NAME);
		if (paths.size() == names.size()) {
			for (int i = 0; i < paths.size(); i++) {
				Attachment att = attachmentProvider.get();
				att.setPath(paths.get(i));
				att.setName(names.get(i));
				email.addAttachment(att);
			}

		}

		filename = filenameUtilities.build(path, EMailIOConstants.EMAIL_BODY,
				index, htmlExtension);
		String body = Files.toString(new File(filename), Charsets.ISO_8859_1);
		email.setBody(body);
		return email;
	}
	
	private void saveArray(Properties prop, String key, String[] array) {
		if (array == null)
			return;
		for (int i = 0; i < array.length; i++) {
			prop.setProperty(key + "." + i, array[i]);
		}
	}

	private List<String> loadArray(Properties prop, String key) {
		List<String> list = new ArrayList<String>();
		String value = null;
		int i = 0;
		do {
			value = prop.getProperty(key + "." + i);
			i++;
			if (value != null)
				list.add(value);
		} while (value != null);
		return list;
	}
}
