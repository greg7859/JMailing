package org.jmailing.model.project.impl;

import java.util.ArrayList;

import org.jmailing.model.project.SourceMailingProjectPart;
import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.project.SourceVariable.Type;

public class SourceMailingProjectPartImpl implements SourceMailingProjectPart {

	private String filename = null;

	private ArrayList<SourceVariable> variables = new ArrayList<>();

	public SourceMailingProjectPartImpl() {
		registerSourceVariable("NAME", Type.MANDATORY) ;
		registerSourceVariable("FIRST_NAME", Type.MANDATORY) ;
		registerSourceVariable("EMAIL1", Type.MANDATORY) ;
		registerSourceVariable("EMAIL2", Type.OPTIONAL) ;
		registerSourceVariable("EMAIL3", Type.OPTIONAL) ;
		registerSourceVariable("EMAIL4", Type.OPTIONAL) ;
		registerSourceVariable("CATEGORY", Type.OPTIONAL) ;
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

	@Override
	public SourceVariable getSourceVariable(String name) {
		for (SourceVariable sv : variables) {
			if (name.equals(sv.getName()))
				return sv;
		}
		return registerSourceVariable(name, Type.OPTIONAL) ;
	}
	
	private SourceVariable registerSourceVariable(String name, Type type) {
		SourceVariable s = new SourceVariableImpl(name, type);
		variables.add(s);
		return s ;
	}

}
