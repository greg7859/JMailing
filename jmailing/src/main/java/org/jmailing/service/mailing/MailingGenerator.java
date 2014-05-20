package org.jmailing.service.mailing;

import java.util.List;

import org.jmailing.model.email.EMail;
import org.jmailing.model.source.Data;

public interface MailingGenerator {
	
	static public int EVENT_SPLIT=0;
	static public int EVENT_EMAIL_SENT=1;
	static public int EVENT_EMAIL_UNSENT=2;
	static public int EVENT_DONE=3;
	
	void sendCampaign(List<Data> data, String filename);
	
	EMail generateEMail(Data data);
	String getAttachmentPath(int i);
	
	void addListener(MailingGeneratorListener listener);
	void reomoveAllListener();


}
