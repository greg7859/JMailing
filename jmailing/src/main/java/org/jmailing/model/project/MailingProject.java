package org.jmailing.model.project;

public interface MailingProject {

	SourceMailingProjectPart getSourceMailingProjectPart();

	AttachmentMailingProjectPart getAttachmentMailingProjectPart();

	EmailMailingProjectPart getEMailMailingProjectPart();
	
	MailingConfigurationPart getMailingConfigurationPart();
}
