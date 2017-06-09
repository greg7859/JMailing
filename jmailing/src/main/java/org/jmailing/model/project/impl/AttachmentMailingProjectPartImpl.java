package org.jmailing.model.project.impl;

import java.util.ArrayList;
import java.util.List;

import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.ExtraAttachment;

public class AttachmentMailingProjectPartImpl implements
		AttachmentMailingProjectPart {

	private int numberOfPageOfSplit = 1;
	private String format = "file.pdf";
	private List<ExtraAttachment> extraAttachments = new ArrayList<>();

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

	@Override
	public void addExtraAttachment(ExtraAttachment extraAttachment) {
		extraAttachments.add(extraAttachment);
	}

	@Override
	public ExtraAttachment getExtraAttachment(int index) {
		return index < extraAttachments.size() ? extraAttachments.get(index) : null;
	}

	@Override
	public void removeAllExtraAttachment() {
		extraAttachments.clear();
	};
	
	@Override
	public int getExtraAttachmentSize() {
		return extraAttachments.size();
	}

}
