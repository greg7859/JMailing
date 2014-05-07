package org.jmailing.model.project.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.EmailMailingProjectPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceMailingProjectPart;

@Singleton
public class MailingProjectImpl implements MailingProject {

	@Inject
	SourceMailingProjectPart source;
	
	@Inject
	AttachmentMailingProjectPart attachment;
	
	@Inject
	EmailMailingProjectPart email;

	@Override
	public SourceMailingProjectPart getSourceMailingProjectPart() {
		return source;
	}
	
	@Override
	public AttachmentMailingProjectPart getAttachmentMailingProjectPart() {
		return attachment;
	}

	@Override
	public EmailMailingProjectPart getEMailMailingProjectPart() {
		return email;
	}

}
