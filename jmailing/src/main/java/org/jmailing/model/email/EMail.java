package org.jmailing.model.email;

import java.util.List;

public interface EMail {

	String[] getTo();

	void addTo(String to) throws InvalidAddressException;

	String[] getCc();

	void addCc(String c) throws InvalidAddressException;

	String[] getBcc();

	void addBcc(String c) throws InvalidAddressException;

	String getSubject();

	void setSubject(String subject);

	String getBody();

	void setBody(String body);

	void addAttachment(Attachment attachment);

	void addAttachments(List<Attachment> attachments);

	Attachment[] getAttachments();

}
