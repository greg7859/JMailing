package org.jmailing.model.project.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceMailingProjectPart;

@Singleton
public class MailingProjectImpl implements MailingProject {

	@Inject
	SourceMailingProjectPart source;

	@Override
	public SourceMailingProjectPart getSourceMailingProjectPart() {
		return source;
	}

}
