package org.jmailing.io.smtp;

import java.io.IOException;

import org.jmailing.model.smtp.Smtp;

public interface SmtpIO {

	void save() throws IOException;

	Smtp load() throws IOException;

}
