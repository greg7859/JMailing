package org.jmailing.service.mail;

import org.jmailing.model.smtp.Smtp;

public interface EmailService {

	void initSmtp(Smtp smtp);

	void sendHtmlMessage(String[] to, String subject, String body) throws EmailServiceException;

}
