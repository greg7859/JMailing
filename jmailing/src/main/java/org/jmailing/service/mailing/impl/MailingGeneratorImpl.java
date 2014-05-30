package org.jmailing.service.mailing.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.swing.SwingWorker;

import org.jmailing.config.ConfigHelper;
import org.jmailing.injector.annotation.Pdf;
import org.jmailing.injector.annotation.ProjectPath;
import org.jmailing.injector.annotation.VariablePrefix;
import org.jmailing.injector.annotation.VariableSuffix;
import org.jmailing.injector.provider.AttachmentProvider;
import org.jmailing.injector.provider.EMailProvider;
import org.jmailing.io.email.EMailIO;
import org.jmailing.model.email.Attachment;
import org.jmailing.model.email.EMail;
import org.jmailing.model.project.EmailMailingProjectPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.source.Data;
import org.jmailing.service.logger.FileLogger;
import org.jmailing.service.mail.EmailService;
import org.jmailing.service.mail.EmailServiceException;
import org.jmailing.service.mailing.AttachmentSplitter;
import org.jmailing.service.mailing.AttachmentSplitterException;
import org.jmailing.service.mailing.MailingGenerator;
import org.jmailing.service.mailing.MailingGeneratorEvent;
import org.jmailing.service.mailing.MailingGeneratorListener;
import org.jmailing.utilities.FileNameUtilities;

import com.google.common.base.Splitter;

public class MailingGeneratorImpl implements MailingGenerator {
	final static String SPLITTED_FILENAME = "PDF-";

	List<MailingGeneratorListener> listeners = new ArrayList<>();

	@Inject
	@VariablePrefix
	private String prefix;

	@Inject
	@VariableSuffix
	private String suffix;

	@Inject
	@Pdf
	private String pdfExtension;

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

	@Inject
	private FileLogger logger;

	@Inject
	private FileNameUtilities filenameUtilities;

	@Inject
	private EMailIO emailIO;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd-HH-mm-ss");

	@Override
	public void sendCampaign(final List<Data> data, final String filename) {
		SwingWorker<Integer, MailingGeneratorEvent> worker = new SwingWorker<Integer, MailingGeneratorEvent>() {

			@Override
			protected Integer doInBackground() throws Exception {
				int nbPage = project.getAttachmentMailingProjectPart()
						.getNumberOfPageOfSplit();
				try {
					String dateStr = dateFormat.format(Calendar.getInstance()
							.getTime());
					String pathProject = path + File.separator
							+ project.getName() + File.separator + dateStr;
					ConfigHelper.checkDirectory(pathProject);
					logger.start(pathProject);
					logger.log("Begin campaign");
					// Split Attachment
					logger.log("Start Split");
					attSplitter.split(filename, pathProject, SPLITTED_FILENAME,
							nbPage);
					logger.log("Start Split done");
					String format = project.getAttachmentMailingProjectPart()
							.getFilenameFormat();
					int sleepTime = 3600000 / project
							.getMailingConfigurationPart()
							.getNumberMailPerHour();

					/* publish the progress. */
					int progress = 20;
					publish(new MailingGeneratorEvent(EVENT_SPLIT, progress, 0));
					int size = data.size();
					int step = 80 / size;

					int index = 0;
					for (Data itemData : data) {
						logger.log("Prepare email no" + (index + 1));
						EMail email = generateEMail(itemData);
						Attachment attachment = generateAttachment(index,
								itemData, format, pathProject);
						email.addAttachment(attachment);
						logger.log("Email no" + (index + 1) + " is sent");
						emailIO.save(pathProject, email, index + 1);
						try {
							progress += step;
							emailSvc.sendHtmlMessage(email);
							publish(new MailingGeneratorEvent(EVENT_EMAIL_SENT,
									progress, index));

						} catch (EmailServiceException e1) {
							publish(new MailingGeneratorEvent(
									EVENT_EMAIL_UNSENT, progress, index));
							logger.log("Email no" + (index + 1) + " isn't sent");
							logger.log(e1.getMessage());
						}
						index++;
						if (index != size)
							Thread.sleep(sleepTime);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AttachmentSplitterException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.log("Mailing done");
				logger.stop();
				return new Integer(1);
			}

			@Override
			public void process(List<MailingGeneratorEvent> list) {
				for (MailingGeneratorEvent event : list)
					for (MailingGeneratorListener listener : listeners) {
						if (event.getEventType() == EVENT_SPLIT
								|| event.getEventType() == EVENT_EMAIL_SENT)
							listener.progress(event.getIndex(),
									event.getProgress(), true);
						else if (event.getEventType() == EVENT_EMAIL_UNSENT)
							listener.progress(event.getIndex(),
									event.getProgress(), false);
						else if (event.getEventType() == EVENT_DONE)
							listener.done();
					}
			}

			@Override
			public void done() {
				publish(new MailingGeneratorEvent(EVENT_DONE, 100, -1));
			}

		};
		worker.execute();
	}

	@Override
	public String getAttachmentPath(int i) {
		// Not yet implemented
		return null;
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
		
		// BCC
		String bccs = replaceVariables(eMailMailingProjectPart.getBcc(), data,
				variables);
		splited = Splitter.on(';').trimResults().omitEmptyStrings().split(bccs);
		for (String bcc : splited) {
			email.addBcc(bcc);
		}

		String title = replaceVariables(eMailMailingProjectPart.getTitle(),
				data, variables);
		email.setSubject(title);

		String body = replaceVariables(eMailMailingProjectPart.getBody(), data,
				variables);
		email.setBody(body);

		return email;
	}

	private Attachment generateAttachment(int index, Data data,
			String filenameFormat, String pathProject) {
		SourceVariable[] variables = project.getSourceMailingProjectPart()
				.getSourceVariables();
		String filename = replaceVariables(filenameFormat, data, variables);

		Attachment attachment = attachmentProvider.get();
		attachment.setName(filename);
		String file = filenameUtilities.build(pathProject, SPLITTED_FILENAME,
				index + 1, pdfExtension);
		attachment.setPath(file);

		return attachment;
	}

	private String replaceVariables(String template, Data data,
			SourceVariable[] variables) {
		String result = template;

		for (SourceVariable sourceVariable : variables) {
			if (sourceVariable.getIndex() != SourceVariable.NO_INDEX) {
				result = result.replace(prefix + sourceVariable.getName()
						+ suffix, data.get(sourceVariable.getIndex()));
			}
		}

		return result;
	}

	@Override
	public void addListener(MailingGeneratorListener listener) {
		listeners.add(listener);
	}

	@Override
	public void reomoveAllListener() {
		listeners.clear();
	}

}
