package org.jmailing.model.project;

public interface SourceVariable {
	
	static int NO_INDEX = -1;
	
	enum Type {
		MANDATORY, OPTIONAL
	};

	String getName();

	void setIndex(int index);

	int getIndex();

	Type getType();
	
}
