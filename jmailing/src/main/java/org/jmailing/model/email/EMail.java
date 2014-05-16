package org.jmailing.model.email;

public interface EMail {

	String[] getTo();

	void addTo(String to);

	String[] getCc();

	void addCc(String c);

	String getSubject();

	void setSubject(String subject);
	
	String getBody();

	void setBody(String body);
	
	void addAttachment(Attachment attachment);
	Attachment[] getAttachments();

}
