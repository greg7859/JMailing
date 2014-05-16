package org.jmailing.service.mailing.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.jmailing.config.ConfigHelper;
import org.jmailing.injector.annotation.ProjectPath;
import org.jmailing.injector.annotation.VariablePrefix;
import org.jmailing.injector.annotation.VariableSuffix;
import org.jmailing.injector.provider.AttachmentProvider;
import org.jmailing.injector.provider.EMailProvider;
import org.jmailing.model.email.Attachment;
import org.jmailing.model.email.EMail;
import org.jmailing.model.project.EmailMailingProjectPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.source.Data;
import org.jmailing.service.mail.EmailService;
import org.jmailing.service.mail.EmailServiceException;
import org.jmailing.service.mailing.AttachmentSplitter;
import org.jmailing.service.mailing.AttachmentSplitterException;
import org.jmailing.service.mailing.MailingGenerator;

import com.google.common.base.Splitter;

public class MailingGeneratorImpl implements MailingGenerator {
	final static String SPLITTED_FILENAME = "PDF-";
	@Inject
	@VariablePrefix
	private String prefix;

	@Inject
	@VariableSuffix
	private String suffix;

	@Inject
	@ProjectPath
	private String path;

	@Inject
	private MailingProject project;

	@Inject
	private EMailProvider emailProvider;

	@Inject
	private AttachmentProvider attachmentProvider;

	@Inject
	private AttachmentSplitter attSplitter;

	@Inject
	private EmailService emailSvc;

	@Override
	public void sendCampaign(List<Data> data, String filename) {
		int nbPage = project.getAttachmentMailingProjectPart()
				.getNumberOfPageOfSplit();
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd-HH-mm-ss");
			String dateStr = dateFormat.format(cal.getTime());
			String pathProject = path + File.separator + project.getName()
					+ File.separator + dateStr;
			ConfigHelper.checkDirectory(pathProject);
			attSplitter.split(filename, pathProject, SPLITTED_FILENAME, nbPage);
			int index = 0;
			String format = project.getAttachmentMailingProjectPart()
					.getFilenameFormat();
			int sleepTime = 3600000 / project.getMailingConfigurationPart()
					.getNumberMailPerHour();
			for (Data itemData : data) {
				EMail email = generateEMail(itemData);
				Attachment attachment = generateAttachment(index, itemData,
						format, pathProject);
				email.addAttachment(attachment);
				emailSvc.sendHtmlMessage(email);
				Thread.sleep(sleepTime);
				index++;
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AttachmentSplitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public EMail generateEMail(Data data) {
		EmailMailingProjectPart eMailMailingProjectPart = project
				.getEMailMailingProjectPart();
		SourceVariable[] variables = project.getSourceMailingProjectPart()
				.getSourceVariables();

		EMail email = emailProvider.get();
		// TO
		String tos = replaceVariables(eMailMailingProjectPart.getTo(), data,
				variables);
		Iterable<String> splited = Splitter.on(';').trimResults()
				.omitEmptyStrings().split(tos);
		for (String to : splited) {
			email.addTo(to);
		}

		// CC
		String ccs = replaceVariables(eMailMailingProjectPart.getCc(), data,
				variables);
		splited = Splitter.on(';').trimResults().omitEmptyStrings().split(ccs);
		for (String cc : splited) {
			email.addCc(cc);
		}

		String title = replaceVariables(eMailMailingProjectPart.getTitle(),
				data, variables);
		email.setSubject(title);

		String body = replaceVariables(eMailMailingProjectPart.getBody(), data,
				variables);
		email.setBody(body);

		return email;
	}

	@Override
	public String getAttachmentPath(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private Attachment generateAttachment(int index, Data data,
			String filenameFormat, String pathProject) {
		SourceVariable[] variables = project.getSourceMailingProjectPart()
				.getSourceVariables();
		String filename = replaceVariables(filenameFormat, data, variables);

		Attachment attachment = attachmentProvider.get();
		attachment.setName(filename);
		String file = pathProject + File.separator + SPLITTED_FILENAME
				+ String.format("%03d", index + 1) + ".pdf";
		attachment.setPath(file);

		return attachment;
	}

	private String replaceVariables(String template, Data data,
			SourceVariable[] variables) {
		String result = template;

		for (SourceVariable sourceVariable : variables) {
			result = result.replace(prefix + sourceVariable.getName() + suffix,
					data.get(sourceVariable.getIndex()));
		}

		return result;
	}

}
