package org.jmailing.service.mailing;

import java.util.List;

import org.jmailing.model.email.EMail;
import org.jmailing.model.source.Data;

public interface MailingGenerator {
	
	void sendCampaign(List<Data> data, String filename);
	
	EMail generateEMail(Data data);
	String getAttachmentPath(int i);

}
