package org.jmailing.model.source;

public interface Data {
	int size();
	String get(int index);
	void add(String text);
}
