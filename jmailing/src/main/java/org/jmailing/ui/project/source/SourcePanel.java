package org.jmailing.ui.project.source;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JPanel;

import org.jmailing.injector.annotation.Excel;
import org.jmailing.io.csv.DataFileReader;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.source.Data;
import org.jmailing.ui.common.panel.FilePanel;
import org.jmailing.ui.common.panel.FilePanelListener;

public class SourcePanel extends JPanel implements FilePanelListener,
		SourceVariablePanelListener {

	@Inject
	private DataFileReader reader;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6472655569320727476L;

	@Inject
	private SourceTablePanel sourceTablePanel = null;

	@Inject
	SourceVariablePanel sourceVariablePanel;
	
	@Inject @Excel
	FilePanel sourceFilePanel;

	/**
	 * Create the panel.
	 */
	public SourcePanel() {
	}

	@Inject
	public void init(MailingProject project) {
		setLayout(new BorderLayout(0, 0));

		// Group File and variable
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		sourceVariablePanel.addListener(this);
		panel.add(sourceVariablePanel, BorderLayout.NORTH);

		sourceFilePanel.addListener(this);
		panel.add(sourceFilePanel, BorderLayout.CENTER);

		panel.add(sourceTablePanel, BorderLayout.SOUTH);

	}

	@Override
	public void fileSelected(String selectedFile) {
		try {
			List<Data> data = reader.readAll(selectedFile, false);
			sourceTablePanel.setData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void variableIndexChanged(SourceVariable variable, int oldValue) {
		sourceTablePanel.changeSourceVariable(variable, oldValue);
	}

}
