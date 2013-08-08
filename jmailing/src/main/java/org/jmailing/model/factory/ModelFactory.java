package org.jmailing.model.factory;

import org.jmailing.model.smtp.Smtp;
import org.jmailing.model.smtp.impl.SmtpImpl;

public class ModelFactory {

	static Smtp createSmtp() {
		return new SmtpImpl();
	}
}
