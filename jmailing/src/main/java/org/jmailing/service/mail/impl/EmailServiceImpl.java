package org.jmailing.service.mail.impl;

import javax.inject.Inject;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.jmailing.injector.provider.EMailProvider;
import org.jmailing.model.email.Attachment;
import org.jmailing.model.email.EMail;
import org.jmailing.model.email.InvalidAddressException;
import org.jmailing.model.smtp.Smtp;
import org.jmailing.service.mail.EmailService;
import org.jmailing.service.mail.EmailServiceException;

public class EmailServiceImpl implements EmailService {

	private Smtp smtp = null;
	@Inject
	private EMailProvider emailProvider;

	@Inject
	@Override
	public void initSmtp(Smtp smtp) {
		this.smtp = smtp;
	}

	@Override
	public void sendHtmlMessage(String[] to, String subject, String body)
			throws EmailServiceException {
		// Create the email
		EMail email = emailProvider.get();
		for (String address : to) {
			try {
				email.addTo(address);
			} catch (InvalidAddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		email.setSubject(subject);
		email.setBody(body);

		// send it
		sendHtmlMessage(email);
	}

	@Override
	public void sendHtmlMessage(EMail email) throws EmailServiceException {
		try {
			// create the email message
			HtmlEmail htmlEmail = new HtmlEmail();
			htmlEmail.setHostName(smtp.getHost());
			htmlEmail.setSmtpPort(Integer.parseInt(smtp.getPort()));
			htmlEmail.setFrom(smtp.getFromAddress(), smtp.getFromLabel());

			if (smtp.getAuthentication()) {
				htmlEmail.setAuthenticator(new DefaultAuthenticator(smtp
						.getLogin(), smtp.getPassword()));

			}
			htmlEmail.setSSLOnConnect(smtp.getSSL());
			for (String to : email.getTo()) {
				htmlEmail.addTo(to);
			}
			for (String cc : email.getCc()) {
				htmlEmail.addCc(cc);

			}
			for (String bcc : email.getBcc()) {
				htmlEmail.addBcc(bcc);
			}
			htmlEmail.setSubject(email.getSubject());

			// set the html message
			htmlEmail.setHtmlMsg(email.getBody());

			// set the alternative message
			htmlEmail
					.setTextMsg("Your email client does not support HTML messages");

			for (Attachment attachment : email.getAttachments()) {
				// Create the attachment
				EmailAttachment emailAttachment = new EmailAttachment();
				emailAttachment.setPath(attachment.getPath());
				emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
				emailAttachment.setDescription("");
				emailAttachment.setName(attachment.getName());

				htmlEmail.attach(emailAttachment);
			}

			// send the email
			htmlEmail.send();
		} catch (EmailException e) {
			throw new EmailServiceException(e.getMessage());
		}

	}
}
