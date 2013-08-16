package org.jmailing.service.mail.impl;

import javax.inject.Inject;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.jmailing.model.smtp.Smtp;
import org.jmailing.service.mail.EmailService;
import org.jmailing.service.mail.EmailServiceException;

public class EmailServiceImpl implements EmailService {

	Smtp smtp = null;

	@Inject
	@Override
	public void initSmtp(Smtp smtp) {
		this.smtp = smtp;
	}

	@Override
	public void sendHtmlMessage(String[] to, String subject, String body)
			throws EmailServiceException {
		try {
			// create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtp.getHost());
			email.setSmtpPort(Integer.parseInt(smtp.getPort()));
			email.setFrom(smtp.getFromAddress(), smtp.getFromLabel());

			if (smtp.getAuthentication()) {
				email.setAuthenticator(new DefaultAuthenticator(
						smtp.getLogin(), smtp.getPassword()));

			}
			email.setSSLOnConnect(smtp.getSSL());
			for (String address : to) {
				email.addTo(address);
			}
			email.setSubject(subject);

			// set the html message
			email.setHtmlMsg(body);

			// set the alternative message
			email.setTextMsg("Your email client does not support HTML messages");

			// send the email
			email.send();
		} catch (EmailException e) {
			throw new EmailServiceException(e.getMessage());
		}
	}
}
