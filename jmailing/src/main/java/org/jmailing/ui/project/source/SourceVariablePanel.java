package org.jmailing.ui.project.source;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;
import org.jmailing.model.project.SourceVariable;
import org.jmailing.ui.filter.IntDocumentFilter;
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
	@Inject
	public SourceVariablePanel(SourceVariable[] variables) {
		this.variables = variables;
		setLayout(new SpringLayout());
		setBorder(BorderFactory.createTitledBorder("Variables"));
		for (SourceVariable variable : variables) {
			JLabel label = new JLabel(variable.getName() + ":");
			add(label);

			JTextField textField = null;
			if (variable.getIndex() == SourceVariable.NO_INDEX) {
				textField = new JTextField();
			} else {
				textField = new JTextField(
						Integer.toString(variable.getIndex()+1));
			}
			TextFieldActionListener l = new TextFieldActionListener(variable);
			textField.addActionListener(l);
			textField.addFocusListener(l);
			PlainDocument doc = (PlainDocument) textField.getDocument();
			doc.setDocumentFilter(new IntDocumentFilter());
			add(textField);

			JLabel type = new JLabel(variable.getType().name());
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

	class TextFieldActionListener implements ActionListener, FocusListener {
		SourceVariable v = null;

		public TextFieldActionListener(SourceVariable variable) {
			v = variable;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			updateVariable(arg0.getSource());
		}

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			updateVariable(arg0.getSource());
		}

		private void updateVariable(Object o) {
			if (o instanceof JTextField) {
				int oldValue = v.getIndex();
				int newValue = SourceVariable.NO_INDEX;
				JTextField tf = (JTextField) o;
				String txt = StringUtils.normalizeSpace(tf.getText());
				if (!StringUtils.isBlank(txt)) {
					newValue = Integer.parseInt(txt)-1;
				}
				if (oldValue != newValue) {
					v.setIndex(newValue);
					for (SourceVariablePanelListener listener : listeners) {
						listener.variableIndexChanged(v, oldValue);
					}
				}
			}
		}
	}
}
