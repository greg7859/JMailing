package org.jmailing.injector.provider;

import javax.inject.Provider;

import org.jmailing.model.source.Data;
import org.jmailing.model.source.impl.DataImpl;

public class DataProvider implements Provider<Data> {

	@Override
	public Data get() {
		return new DataImpl();
	}

}
