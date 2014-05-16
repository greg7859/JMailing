package org.jmailing.model.email.impl;

import org.jmailing.model.email.Attachment;

public class AttachmentImpl implements Attachment {
	private String path;
	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.email.Attachment#getPath()
	 */
	@Override
	public String getPath() {
		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.email.Attachment#setPath(java.lang.String)
	 */
	@Override
	public void setPath(String path) {
		this.path = path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.email.Attachment#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.email.Attachment#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

}
