package org.jmailing.model.project.impl;

import org.jmailing.model.project.AttachmentMailingProjectPart;

public class AttachmentMailingProjectPartImpl implements
		AttachmentMailingProjectPart {

	private int numberOfPageOfSplit = 1;
	private String format = "file.pdf";

	@Override
	public void setNumberOfPageOfSplit(int nbPage) {
		this.numberOfPageOfSplit = nbPage;
	}

	@Override
	public int getNumberOfPageOfSplit() {
		return this.numberOfPageOfSplit;
	}

	@Override
	public void setFilenameFormat(String format) {
		this.format = format;
	}

	@Override
	public String getFilenameFormat() {
		return this.format;
	}

}
