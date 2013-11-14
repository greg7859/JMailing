package org.jmailing.ui.project.source;

import javax.swing.JPanel;

import org.jmailing.model.project.SourceMailingProjectPart;
import org.jmailing.model.project.SourceVariable;

import java.awt.BorderLayout;

public class SourcePanel extends JPanel implements SourceFilePanelListener, SourceVariablePanelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6472655569320727476L;

	/**
	 * Create the panel.
	 */
	public SourcePanel(SourceMailingProjectPart source) {
		setLayout(new BorderLayout(0, 0));
		
		// Group File and variable
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		
		SourceVariablePanel sourceVariablePanel = new SourceVariablePanel(source.getSourceVariables());
		sourceVariablePanel.addListener(this);
		panel.add(sourceVariablePanel, BorderLayout.NORTH);
		
		SourceFilePanel sourceFilePanel = new SourceFilePanel();
		sourceFilePanel.addListener(this);
		panel.add(sourceFilePanel, BorderLayout.CENTER);
		
		
	}

	@Override
	public void fileSelected(String selectedFile) {
		//TODO add update
		System.out.println("Fichier selectionne:"+selectedFile);
	}

	@Override
	public void variableIndexChanged(SourceVariable variable, int oldValue) {
		System.out.println("Vairaible:"+variable.getName() + " new="+variable.getIndex() + " old="+oldValue);
	}

}
