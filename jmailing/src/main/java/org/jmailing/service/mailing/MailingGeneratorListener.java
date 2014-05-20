package org.jmailing.service.mailing;

public interface MailingGeneratorListener {
	
	void done();
	
	void progress(int index, int progress, boolean state);
	
}
