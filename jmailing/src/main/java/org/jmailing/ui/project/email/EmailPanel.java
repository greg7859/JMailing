package org.jmailing.ui.project.email;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.inject.Inject;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.commons.lang.StringUtils;
import org.jmailing.model.project.EmailMailingProjectPart;
import org.jmailing.model.project.MailingProject;

public class EmailPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5349291409952055313L;
	private JTextField textField;

	public EmailPanel() {
	}

	@Inject
	private void initPanel(MailingProject project) {
		EmailMailingProjectPart emailPart = project
				.getEMailMailingProjectPart();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblTitle = new JLabel("Title");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.EAST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(textField, gbc_textField);
		textField.setColumns(10);
		textField.setText(emailPart.getTitle());

		JLabel lblBody = new JLabel("Body");
		GridBagConstraints gbc_lblBody = new GridBagConstraints();
		gbc_lblBody.anchor = GridBagConstraints.NORTH;
		gbc_lblBody.insets = new Insets(0, 0, 0, 5);
		gbc_lblBody.gridx = 0;
		gbc_lblBody.gridy = 1;
		add(lblBody, gbc_lblBody);

		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 1;
		gbc_textPane.gridy = 1;
		add(textPane, gbc_textPane);
		textPane.setText(emailPart.getBody());
	}

	class TitledActionListener implements ActionListener, FocusListener {
		EmailMailingProjectPart emailPart;

		TitledActionListener(EmailMailingProjectPart emailPart) {
			this.emailPart = emailPart;
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
				String oldValue = emailPart.getTitle();
				String newValue = "";
				JTextField tf = (JTextField) o;
				String txt = StringUtils.normalizeSpace(tf.getText());
				if (!StringUtils.isBlank(txt)) {
					newValue = txt;
				}
				if (oldValue.equals(newValue) == false) {
					emailPart.setTitle(newValue);
				}
			}
		}
	}
}
