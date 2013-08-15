/**
 * 
 */
package org.jmailing.io.smtp.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import org.jasypt.encryption.StringEncryptor;
import org.jmailing.injector.annotation.SmtpConf;
import org.jmailing.io.smtp.SmtpIO;
import org.jmailing.model.smtp.Smtp;

/**
 * @author Gregory Cochon
 * 
 */
public class SmtpIOImpl implements SmtpIO {
	@Inject
	Smtp smtp = null;

	@Inject
	StringEncryptor encryptor = null;

	@Inject
	@SmtpConf
	String smtpConf = null;

	final static private String HOST = "smpt.host";
	final static private String PORT = "smtp.port";
	final static private String USERNAME = "smtp.username";
	final static private String PASSWORD = "smtp.password";
	final static private String FROM_ADDRESS = "smtp.fromaddress";
	final static private String FROM_LABEL = "smtp.fromLabel";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.io.SmtpIO#save(org.jmailing.model.Smtp)
	 */
	@Override
	public void save() throws IOException {
		FileOutputStream fos = new FileOutputStream(smtpConf);
		Properties prop = new Properties();
		prop.put(HOST, smtp.getHost());
		prop.put(PORT, smtp.getPort());
		prop.put(USERNAME, encryptor.encrypt(smtp.getLogin()));
		prop.put(PASSWORD, encryptor.encrypt(smtp.getPassword()));
		prop.put(FROM_ADDRESS, smtp.getFromAddress());
		prop.put(FROM_LABEL, smtp.getFromLabel());

		prop.store(fos, "Smtp Configuration");
		fos.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.io.SmtpIO#load()
	 */
	@Override
	public Smtp load() throws IOException {
		FileInputStream fis;
		try {
			fis = new FileInputStream(smtpConf);
		} catch (FileNotFoundException e) {
			throw new IOException("File not found :" + smtpConf);
		}
		Properties prop = new Properties();
		prop.load(fis);
		fis.close();
		smtp.setHost((String) prop.get(HOST));
		smtp.setPort((String) prop.get(PORT));
		smtp.setLogin(encryptor.decrypt((String) prop.get(USERNAME)));
		smtp.setPassword(encryptor.decrypt((String) prop.get(PASSWORD)));
		smtp.setFromAddress((String) prop.get(FROM_ADDRESS));
		smtp.setFromLabel((String) prop.get(FROM_LABEL));
		return smtp;
	}
}
