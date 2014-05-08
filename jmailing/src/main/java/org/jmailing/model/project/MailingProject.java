package org.jmailing.model.project;

public interface MailingProject {
	
	String getName();
	void setName(String name);

	SourceMailingProjectPart getSourceMailingProjectPart();

	AttachmentMailingProjectPart getAttachmentMailingProjectPart();

	EmailMailingProjectPart getEMailMailingProjectPart();
	
	MailingConfigurationPart getMailingConfigurationPart();
}
