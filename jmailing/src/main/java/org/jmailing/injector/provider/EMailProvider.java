package org.jmailing.injector.provider;

import javax.inject.Provider;

import org.jmailing.model.email.EMail;
import org.jmailing.model.email.impl.EMailImpl;

public class EMailProvider implements Provider<EMail> {

	@Override
	public EMail get() {
		return new EMailImpl();
	}

}
