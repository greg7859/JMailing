package org.jmailing.injector.provider;

import javax.inject.Provider;

import org.jmailing.model.project.ExtraAttachment;
import org.jmailing.model.project.impl.ExtraAttachmentImpl;

public class ExtraAttachmentProvider implements Provider<ExtraAttachment> {

	@Override
	public ExtraAttachment get() {
		return new ExtraAttachmentImpl();
	}
}
