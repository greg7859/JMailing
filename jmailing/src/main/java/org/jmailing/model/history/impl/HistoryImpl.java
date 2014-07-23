package org.jmailing.model.history.impl;

import org.jmailing.model.history.History;

public class HistoryImpl implements History {
	private String ref = null;

	@Override
	public String getReference() {
		return ref;
	}

	@Override
	public void setReference(String ref) {
		this.ref = ref;
	}

}
