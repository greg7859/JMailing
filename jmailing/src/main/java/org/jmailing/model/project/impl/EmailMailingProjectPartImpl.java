/**
 * 
 */
package org.jmailing.model.project.impl;

import org.jmailing.model.project.EmailMailingProjectPart;

/**
 * Define the content of the email.
 * 
 * @author Gregory Cochon
 * 
 */
public class EmailMailingProjectPartImpl implements EmailMailingProjectPart {

	private String to;
	private String cc;
	private String title;
	private String body;

	@Override
	public String getTo() {
		return to;
	}

	@Override
	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String getCc() {
		return cc;
	}

	@Override
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * 
	 */
	public EmailMailingProjectPartImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.project.EMailMailingProjectPart#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jmailing.model.project.EMailMailingProjectPart#setTitle(java.lang
	 * .String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jmailing.model.project.EMailMailingProjectPart#getBody()
	 */
	@Override
	public String getBody() {
		return body;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jmailing.model.project.EMailMailingProjectPart#setBody(java.lang.
	 * String)
	 */
	@Override
	public void setBody(String body) {
		this.body = body;
	}

}
