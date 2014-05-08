package org.jmailing.model.project;

public interface SourceMailingProjectPart {

	String getFilename();
	void setFilename(String filename);
	
	SourceVariable[] getSourceVariables();
	SourceVariable getSourceVariable(String name);
	
}
