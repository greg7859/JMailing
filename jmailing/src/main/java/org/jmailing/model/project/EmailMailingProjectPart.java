package org.jmailing.model.project;

public interface EmailMailingProjectPart {

	String getTo();

	void setTo(String to);

	String getCc();

	void setCc(String cc);

	String getBcc();

	void setBcc(String bcc);

	String getTitle();

	void setTitle(String title);

	String getBody();

	void setBody(String body);

}
