package org.jmailing.injector;

import org.jasypt.encryption.StringEncryptor;
import org.jmailing.config.ConfigHelper;
import org.jmailing.config.Constants;
import org.jmailing.injector.annotation.SmtpConf;
import org.jmailing.io.adapter.impl.StringEncryptorImpl;
import org.jmailing.io.smtp.SmtpIO;
import org.jmailing.io.smtp.impl.SmtpIOImpl;
import org.jmailing.model.smtp.Smtp;
import org.jmailing.model.smtp.impl.SmtpImpl;
import org.jmailing.service.mail.EmailService;
import org.jmailing.service.mail.impl.EmailServiceImpl;

import com.google.inject.AbstractModule;

public class JMailingModule extends AbstractModule {

	@Override
	protected void configure() {
		// Model
		bind(Smtp.class).to(SmtpImpl.class);

		// Service
		bind(SmtpIO.class).to(SmtpIOImpl.class);
		bind(EmailService.class).to(EmailServiceImpl.class);

		// Security
		bind(StringEncryptor.class).to(StringEncryptorImpl.class);

		// Config
		String smtpConf = ConfigHelper.getConfigPath(
				Constants.EMAIL_CFG_RESOURCE, true);
		bind(String.class).annotatedWith(SmtpConf.class).toInstance(smtpConf);

	}
}
