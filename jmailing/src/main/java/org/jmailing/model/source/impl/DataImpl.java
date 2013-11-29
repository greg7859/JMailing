package org.jmailing.model.source.impl;

import java.util.ArrayList;
import java.util.List;

import org.jmailing.model.source.Data;

public class DataImpl implements Data {
	List<String> data = new ArrayList<>();

	public DataImpl() {
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public String get(int index) {
		return data.get(index);
	}
	
	public void add(String text) {
		data.add(text);
	}
}
