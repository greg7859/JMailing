/**
 * 
 */
package org.jmailing.io.impl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jmailing.config.ConfigHelper;
import org.jmailing.config.Constants;
import org.jmailing.io.SmtpIO;
import org.jmailing.model.smtp.impl.SmtpImpl;

/**
 * @author Gregory Cochon
 * 
 */
public class SmtpIOImpl implements SmtpIO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.io.SmtpIO#save(org.jmailing.model.Smtp)
	 */
	@Override
	public void save(SmtpImpl smtp) {
		try {
			// JAXB load the mail servers configuration
			JAXBContext context = JAXBContext.newInstance(SmtpImpl.class);

			Marshaller marshalller = context.createMarshaller();
			marshalller.marshal(smtp, ConfigHelper.getConfigPath(
					Constants.EMAIL_CFG_RESOURCE, true));
		} catch (JAXBException e) {
			System.err.println("error: " + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.io.SmtpIO#load()
	 */
	@Override
	public SmtpImpl load() {
		SmtpImpl configuration = null;
		try {
			// JAXB load the mail servers configuration
			JAXBContext context = JAXBContext.newInstance(SmtpImpl.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			configuration = (SmtpImpl) unmarshaller.unmarshal(ConfigHelper
					.getConfigPath(Constants.EMAIL_CFG_RESOURCE, true));
		} catch (JAXBException e) {
			System.err.println("error: " + e.getMessage());
		}
		return configuration;
	}

}
