package org.jmailing.io;

import org.jmailing.io.impl.SmtpIOImpl;
import org.jmailing.model.smtp.impl.SmtpImpl;
import org.junit.Assert;
import org.junit.Test;

public class SmtpIOTest {
	@Test
	public void ioTest() {

		String login = "cochon";
		String password = "motDePasse#4_";
		String host = "maMAchine";
		String port = "25";
		String email = "noreply@example.org";
		SmtpImpl smtp = new SmtpImpl();
		smtp.setLogin(login);
		smtp.setPassword(password);
		smtp.setHost(host);
		smtp.setPort(port);
		smtp.setFromAddress(email);

		SmtpIO io = new SmtpIOImpl();
		io.save(smtp);

		SmtpImpl loadedSmtp = io.load();

		Assert.assertEquals(login, loadedSmtp.getLogin());
	}

}
