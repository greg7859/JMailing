package org.jmailing.ui.project;

import java.awt.BorderLayout;

import javax.inject.Inject;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jmailing.injector.annotation.Campaign;
import org.jmailing.injector.annotation.Csv;
import org.jmailing.injector.annotation.Email;
import org.jmailing.injector.annotation.Mailing;
import org.jmailing.injector.annotation.Pdf;

public class MailingProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3296779370121320000L;

	@Inject
	@Csv
	JPanel sourcePanel;
	
	@Inject
	@Pdf
	JPanel attachmentPanel;
	
	@Inject
	@Email
	JPanel emailPanel;
	
	@Inject
	@Mailing
	JPanel mailingPanel;
	
	@Inject
	@Campaign
	JPanel campaignPanel;

	/**
	 * Create the panel.
	 */
	public MailingProjectPanel() {
	}

	 @Inject
	public final void initPanel() {
		setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		tabbedPane.addTab("Source", sourcePanel);
		tabbedPane.addTab("Attachment", attachmentPanel);
		tabbedPane.addTab("EMail", emailPanel);
		tabbedPane.addTab("Mailing", mailingPanel);
		tabbedPane.addTab("Campaign", campaignPanel);

	}

}
