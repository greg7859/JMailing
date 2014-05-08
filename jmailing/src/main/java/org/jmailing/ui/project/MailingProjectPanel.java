package org.jmailing.ui.project;

import java.awt.BorderLayout;

import javax.inject.Inject;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jmailing.ui.project.attachment.AttachmentPanel;
import org.jmailing.ui.project.email.EmailPanel;
import org.jmailing.ui.project.mailing.MailingConfigurationPanel;
import org.jmailing.ui.project.source.SourcePanel;

public class MailingProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3296779370121320000L;

	@Inject
	SourcePanel sourcePanel;
	@Inject
	AttachmentPanel attachmentPanel;
	@Inject
	EmailPanel emailPanel;
	@Inject
	MailingConfigurationPanel mailingPanel;

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

	}

}
