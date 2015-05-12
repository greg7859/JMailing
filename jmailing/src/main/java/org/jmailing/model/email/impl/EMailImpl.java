package org.jmailing.model.email.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.jmailing.model.email.Attachment;
import org.jmailing.model.email.EMail;
import org.jmailing.model.email.InvalidAddressException;

import com.google.common.collect.Iterables;

public class EMailImpl implements EMail {

	private List<String> tos = null;
	private List<String> ccs = null;
	private List<String> bccs = null;
	private String subject = null;
	private String body = null;
	private List<Attachment> attachments = null;

	public EMailImpl() {
		tos = new ArrayList<>();
		ccs = new ArrayList<>();
		bccs = new ArrayList<>();
		attachments = new ArrayList<>();
	}

	@Override
	public String[] getTo() {
		return Iterables.toArray(tos, String.class);
	}

	@Override
	public void addTo(String to) throws InvalidAddressException {
		addAddress(to, tos);
	}

	@Override
	public String[] getCc() {
		return Iterables.toArray(ccs, String.class);
	}

	@Override
	public void addBcc(String bcc) throws InvalidAddressException {
		addAddress(bcc, bccs);
	}

	@Override
	public String[] getBcc() {
		return Iterables.toArray(bccs, String.class);
	}

	@Override
	public void addCc(String cc) throws InvalidAddressException {
		addAddress(cc, ccs);
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public void addAttachment(Attachment attachment) {
		attachments.add(attachment);
	}

	@Override
	public Attachment[] getAttachments() {
		return Iterables.toArray(attachments, Attachment.class);
	}

	private void addAddress(String address, List<String> list)
			throws InvalidAddressException {
		if (EmailValidator.getInstance().isValid(address))
			list.add(address);
		else
			throw new InvalidAddressException();
	}
}
