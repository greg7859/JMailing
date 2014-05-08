package org.jmailing.injector;

import org.jasypt.encryption.StringEncryptor;
import org.jmailing.config.ConfigHelper;
import org.jmailing.config.Constants;
import org.jmailing.injector.annotation.SmtpConf;
import org.jmailing.injector.provider.DataProvider;
import org.jmailing.io.adapter.impl.StringEncryptorImpl;
import org.jmailing.io.csv.CsvFileReader;
import org.jmailing.io.csv.DataFileReader;
import org.jmailing.io.smtp.SmtpIO;
import org.jmailing.io.smtp.impl.SmtpIOImpl;
import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.EmailMailingProjectPart;
import org.jmailing.model.project.MailingConfigurationPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceMailingProjectPart;
import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.project.impl.AttachmentMailingProjectPartImpl;
import org.jmailing.model.project.impl.EmailMailingProjectPartImpl;
import org.jmailing.model.project.impl.MailingConfigurationPartImpl;
import org.jmailing.model.project.impl.MailingProjectImpl;
import org.jmailing.model.project.impl.SourceMailingProjectPartImpl;
import org.jmailing.model.smtp.Smtp;
import org.jmailing.model.smtp.impl.SmtpImpl;
import org.jmailing.model.source.Data;
import org.jmailing.service.mail.EmailService;
import org.jmailing.service.mail.impl.EmailServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class JMailingModule extends AbstractModule {

	@Override
	protected void configure() {
		// Model
		bind(Smtp.class).to(SmtpImpl.class);
		bind(MailingProject.class).to(MailingProjectImpl.class);
		bind(SourceMailingProjectPart.class).to(SourceMailingProjectPartImpl.class);
		bind(AttachmentMailingProjectPart.class).to(AttachmentMailingProjectPartImpl.class);
		bind(EmailMailingProjectPart.class).to(EmailMailingProjectPartImpl.class);
		bind(MailingConfigurationPart.class).to(MailingConfigurationPartImpl.class);
		
		bind(Data.class).toProvider(DataProvider.class);
		
		// Service
		bind(SmtpIO.class).to(SmtpIOImpl.class);
		bind(EmailService.class).to(EmailServiceImpl.class);
		bind(DataFileReader.class).to(CsvFileReader.class);

		// Security
		bind(StringEncryptor.class).to(StringEncryptorImpl.class);

		// Config
		String smtpConf = ConfigHelper.getConfigPath(
				Constants.EMAIL_CFG_RESOURCE, true);
		bind(String.class).annotatedWith(SmtpConf.class).toInstance(smtpConf);

	}
	
	@Provides 
	SourceVariable[] provideSourceMailingProjectPartt(MailingProject project) { 
	    return project.getSourceMailingProjectPart().getSourceVariables();
	  }
}
