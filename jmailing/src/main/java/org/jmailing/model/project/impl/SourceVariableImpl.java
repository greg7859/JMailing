package org.jmailing.model.project.impl;

import org.jmailing.model.project.SourceVariable;

public class SourceVariableImpl implements SourceVariable {

	private String name = null;
	Type type = Type.MANDATORY;
	private int index = -1;

	public SourceVariableImpl(String name, Type type) {
		this.name = name;
		this.type=type;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	@Override
	public Type getType() {
		return this.type;
	}

}
