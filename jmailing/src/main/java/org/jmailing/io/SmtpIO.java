package org.jmailing.io;

import org.jmailing.model.smtp.impl.SmtpImpl;

public interface SmtpIO {

	void save(SmtpImpl stmp);

	SmtpImpl load();

}
