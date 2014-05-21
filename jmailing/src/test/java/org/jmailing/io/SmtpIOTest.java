package org.jmailing.io;

import java.io.IOException;

import org.jmailing.injector.JMailingModule;
import org.jmailing.io.smtp.SmtpIO;
import org.jmailing.model.smtp.Smtp;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class SmtpIOTest {
	@Test
	public void ioTest() {

		String login = "cochon";
		String password = "APassW0Rd#4Me";
		String host = "MyHost";
		String port = "25";
		String email = "noreply@example.org";
		String label = "No Reply";
		boolean ssl = false;
		boolean auth = true;

		Injector injector = Guice.createInjector(new JMailingModule());

		Smtp smtp = injector.getInstance(Smtp.class);
		smtp.setLogin(login);
		smtp.setPassword(password);
		smtp.setHost(host);
		smtp.setPort(port);
		smtp.setFromAddress(email);
		smtp.setFromLabel(label);
		smtp.setSSL(ssl);
		smtp.setAuthentication(auth);

		Smtp loadedSmtp = null;
		try {
			SmtpIO io = injector.getInstance(SmtpIO.class);
			io.save();

			loadedSmtp = io.load();

		} catch (IOException e) {

		}
		Assert.assertEquals(login, loadedSmtp.getLogin());
		Assert.assertEquals(password, loadedSmtp.getPassword());
		Assert.assertEquals(host, loadedSmtp.getHost());
		Assert.assertEquals(port, loadedSmtp.getPort());
		Assert.assertEquals(email, loadedSmtp.getFromAddress());
		Assert.assertEquals(label, loadedSmtp.getFromLabel());
		Assert.assertEquals(ssl, loadedSmtp.getSSL());
		Assert.assertEquals(auth, loadedSmtp.getAuthentication());
	}

}
