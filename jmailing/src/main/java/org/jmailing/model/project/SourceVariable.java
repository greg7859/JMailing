package org.jmailing.model.project;

public interface SourceVariable {
	enum Type {
		MANDATORY, OPTIONAL
	};

	String getName();

	void setIndex(int index);

	int getIndex();

	Type getType();
	
}
