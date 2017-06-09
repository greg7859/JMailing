package org.jmailing.model.project.impl;

import org.jmailing.model.project.ExtraAttachment;

public class ExtraAttachmentImpl implements ExtraAttachment {

	private String filename;
	private String fileToAttach;

	@Override
	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String getFilename() {
		return this.filename;
	}

	@Override
	public void setFileToAttach(String file) {
		this.fileToAttach = file;
	}

	@Override
	public String getFileToAttach() {
		return fileToAttach;
	}

}
