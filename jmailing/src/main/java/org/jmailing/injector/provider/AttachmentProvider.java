package org.jmailing.injector.provider;

import javax.inject.Provider;

import org.jmailing.model.email.Attachment;
import org.jmailing.model.email.impl.AttachmentImpl;

public class AttachmentProvider implements Provider<Attachment> {

	@Override
	public Attachment get() {
		return new AttachmentImpl();
	}

}
