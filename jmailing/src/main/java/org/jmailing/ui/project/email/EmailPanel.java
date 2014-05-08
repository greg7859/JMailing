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
	private JTextField toField;
	private JTextField ccField;

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
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblTo = new JLabel("To");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTo.gridx = 0;
		gbc_lblTo.gridy = 0;
		add(lblTo, gbc_lblTo);

		toField = new JTextField();
		GridBagConstraints gbc_toField = new GridBagConstraints();
		gbc_toField.insets = new Insets(0, 0, 5, 5);
		gbc_toField.anchor = GridBagConstraints.NORTH;
		gbc_toField.fill = GridBagConstraints.HORIZONTAL;
		gbc_toField.gridx = 1;
		gbc_toField.gridy = 0;
		add(toField, gbc_toField);
		toField.setColumns(10);

		JLabel lblCC = new JLabel("CC");
		GridBagConstraints gbc_lblCC = new GridBagConstraints();
		gbc_lblCC.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblCC.insets = new Insets(0, 0, 5, 5);
		gbc_lblCC.gridx = 0;
		gbc_lblCC.gridy = 1;
		add(lblCC, gbc_lblCC);

		ccField = new JTextField();
		GridBagConstraints gbc_ccField = new GridBagConstraints();
		gbc_ccField.insets = new Insets(0, 0, 5, 5);
		gbc_ccField.anchor = GridBagConstraints.NORTH;
		gbc_ccField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ccField.gridx = 1;
		gbc_ccField.gridy = 1;
		add(ccField, gbc_ccField);
		ccField.setColumns(10);

		JLabel lblTitle = new JLabel("Title");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 2;
		add(lblTitle, gbc_lblTitle);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(10);
		textField.setText(emailPart.getTitle());
		TitleActionListener tal = new TitleActionListener(emailPart);
		textField.addActionListener(tal);
		textField.addFocusListener(tal);

		JLabel lblBody = new JLabel("Body");
		GridBagConstraints gbc_lblBody = new GridBagConstraints();
		gbc_lblBody.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblBody.fill = GridBagConstraints.BOTH;
		gbc_lblBody.insets = new Insets(0, 0, 5, 5);
		gbc_lblBody.gridx = 0;
		gbc_lblBody.gridy = 3;
		add(lblBody, gbc_lblBody);

		JTextPane textPane = new JTextPane();
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.anchor = GridBagConstraints.NORTH;
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 1;
		gbc_textPane.gridy = 3;
		add(textPane, gbc_textPane);
		textPane.setText(emailPart.getBody());
	}

	class TitleActionListener implements ActionListener, FocusListener {
		EmailMailingProjectPart emailPart;

		TitleActionListener(EmailMailingProjectPart emailPart) {
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
