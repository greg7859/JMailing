package org.jmailing.model.project.impl;

import java.util.ArrayList;

import org.jmailing.model.project.SourceMailingProjectPart;
import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.project.SourceVariable.Type;

public class SourceMailingProjectPartImpl implements SourceMailingProjectPart {

	private String filename = null;

	private ArrayList<SourceVariable> variables = new ArrayList<>();

	public SourceMailingProjectPartImpl() {
		SourceVariable s = new SourceVariableImpl("NAME", Type.MANDATORY);
		variables.add(s);
		s = new SourceVariableImpl("FIRST_NAME", Type.MANDATORY);
		variables.add(s);
		s = new SourceVariableImpl("EMAIL", Type.MANDATORY);
		variables.add(s);
		s = new SourceVariableImpl("EMAIL1", Type.OPTIONAL);
		variables.add(s);
		s = new SourceVariableImpl("EMAIL2", Type.OPTIONAL);
		variables.add(s);
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public SourceVariable[] getSourceVariables() {
		return variables.toArray(new SourceVariable[variables.size()]);
	}

}
