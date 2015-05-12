package org.jmailing.ui.project;

import java.awt.BorderLayout;

import javax.inject.Inject;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jmailing.injector.annotation.Campaign;
import org.jmailing.injector.annotation.Email;
import org.jmailing.injector.annotation.Excel;
import org.jmailing.injector.annotation.Mailing;
import org.jmailing.injector.annotation.Pdf;
import org.jmailing.ui.project.history.HistoryPanel;

public class MailingProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3296779370121320000L;

	@Inject
	@Excel
	private JPanel sourcePanel;

	@Inject
	@Pdf
	private JPanel attachmentPanel;

	@Inject
	@Email
	private JPanel emailPanel;

	@Inject
	@Mailing
	private JPanel mailingPanel;

	@Inject
	@Campaign
	private JPanel campaignPanel;

	@Inject
	private HistoryPanel historyPanel;

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
		tabbedPane.addTab("History", historyPanel);

		tabbedPane.addChangeListener(new MailingProjectTabChangeListener());
	}

	class MailingProjectTabChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			Object source = e.getSource();
			if (source instanceof JTabbedPane) {
				JTabbedPane tabbedPane = (JTabbedPane) source;
				if (tabbedPane.getSelectedIndex() == 5) {
					historyPanel.refresh();
				}
			}
		}

	}

}
