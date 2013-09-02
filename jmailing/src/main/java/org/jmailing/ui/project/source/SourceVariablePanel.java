package org.jmailing.ui.project.source;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jmailing.model.project.SourceVariable;
import org.jmailing.ui.layout.SpringUtilities;

public class SourceVariablePanel extends JPanel {
	SourceVariable[] variables = null;
	private ArrayList<SourceVariablePanelListener> listeners = new ArrayList<>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 6787200708948009588L;

	/**
	 * Create the panel.
	 */
	public SourceVariablePanel(SourceVariable[] variables) {
		this.variables = variables;
		setLayout(new SpringLayout());
		setBorder(BorderFactory.createTitledBorder("Variables"));
		for (int i = 0; i < variables.length; i++) {
			JLabel label = new JLabel(variables[i].getName() + ":");
			JTextField textField = new JTextField(Integer.toString(i));
			JLabel type = new JLabel(variables[i].getType().name());
			add(label);
			add(textField);
			add(type);
		}

		SpringUtilities.makeGrid(this, variables.length, 3, // rows, cols
				5, 5, // initialX, initialY
				5, 5);// xPad, yPad

	}

	public void addListener(SourceVariablePanelListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	public void removeListener(SourceVariablePanelListener listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
	}

}
