package org.jmailing.model.project.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.EmailMailingProjectPart;
import org.jmailing.model.project.MailingConfigurationPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceMailingProjectPart;

@Singleton
public class MailingProjectImpl implements MailingProject {

	@Inject
	private SourceMailingProjectPart source;
	
	@Inject
	private AttachmentMailingProjectPart attachment;
	
	@Inject
	private EmailMailingProjectPart email;
	
	@Inject
	private MailingConfigurationPart mailing;
	
	private String name=null;

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
	
	@Override
	public MailingConfigurationPart getMailingConfigurationPart() {
		return mailing;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

}
