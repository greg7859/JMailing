package org.jmailing.io;

import java.io.IOException;

import org.jmailing.injector.JMailingModule;
import org.jmailing.io.smtp.SmtpIO;
import org.jmailing.io.smtp.impl.SmtpIOImpl;
import org.jmailing.model.smtp.Smtp;
import org.jmailing.model.smtp.impl.SmtpImpl;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class SmtpIOTest {
	@Test
	public void ioTest() {

		String login = "cochon";
		String password = "motDePasse#4_";
		String host = "maMAchine";
		String port = "25";
		String email = "noreply@example.org";
		String label = "No Repley";

		Injector injector = Guice.createInjector(new JMailingModule());

		Smtp smtp = injector.getInstance(Smtp.class);
		smtp.setLogin(login);
		smtp.setPassword(password);
		smtp.setHost(host);
		smtp.setPort(port);
		smtp.setFromAddress(email);
		smtp.setFromLabel(label);

		
		Smtp loadedSmtp = null;
		try {
			SmtpIO io = injector.getInstance(SmtpIO.class);
			io.save();

			 loadedSmtp = io.load();

		} catch (IOException e) {

		}
		Assert.assertEquals(login, loadedSmtp.getLogin());
	}

}
