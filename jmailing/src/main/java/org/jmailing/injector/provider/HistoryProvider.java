package org.jmailing.injector.provider;

import javax.inject.Provider;

import org.jmailing.model.history.History;
import org.jmailing.model.history.impl.HistoryImpl;

public class HistoryProvider implements Provider<History> {

	@Override
	public History get() {
		return new HistoryImpl();
	}

}
