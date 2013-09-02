package org.jmailing.ui.project;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jmailing.model.project.MailingProject;
import org.jmailing.ui.project.source.SourcePanel;

public class MailingProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3296779370121320000L;

	private MailingProject project = null;
	
	/**
	 * Create the panel.
	 */
	public MailingProjectPanel(MailingProject project) {
		this.project=project;
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		tabbedPane.addTab("Source", new SourcePanel(this.project.getSourceMailingProjectPart()));

	}

}
