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
import javax.swing.text.JTextComponent;

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
		TextComponentListener toTal = new TextComponentListener(emailPart, FieldType.TO);
		toField.addActionListener(toTal);
		toField.addFocusListener(toTal);

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
		TextComponentListener ccTal = new TextComponentListener(emailPart, FieldType.CC);
		ccField.addActionListener(ccTal);
		ccField.addFocusListener(ccTal);

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
		TextComponentListener titleTal = new TextComponentListener(emailPart, FieldType.TITLE);
		textField.addActionListener(titleTal);
		textField.addFocusListener(titleTal);

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
		TextComponentListener bodyTal = new TextComponentListener(emailPart, FieldType.BODY);
		textPane.addFocusListener(bodyTal);
	}

	enum FieldType {
		TO, CC, TITLE, BODY;
	}

	class TextComponentListener implements ActionListener, FocusListener {

		private FieldType type;

		private EmailMailingProjectPart emailPart;

		TextComponentListener(EmailMailingProjectPart emailPart, FieldType type) {
			this.emailPart = emailPart;
			this.type = type;
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
			if (o instanceof JTextComponent) {
				String oldValue = getOldValue();
				String newValue = "";
				JTextComponent tf = (JTextComponent) o;
				String txt = StringUtils.normalizeSpace(tf.getText());
				if (!StringUtils.isBlank(txt)) {
					newValue = txt;
				}
				if (oldValue==null || oldValue.equals(newValue) == false) {
					setNewValue(newValue);
				}
			}
		}

		private String getOldValue() {
			String oldValue = null;
			switch (type) {
			case TO:
				oldValue = emailPart.getTo();
				break;
			case CC:
				oldValue = emailPart.getCc();
				break;
			case TITLE:
				oldValue = emailPart.getTitle();
				break;
			case BODY:
				oldValue = emailPart.getBody();
				break;
			}
			return oldValue;
		}

		private void setNewValue(String newValue) {
			switch (type) {
			case TO:
				emailPart.setTo(newValue);
				break;
			case CC:
				emailPart.setCc(newValue);
				break;
			case TITLE:
				emailPart.setTitle(newValue);
				break;
			case BODY:
				emailPart.setBody(newValue);
				break;
			}
		}
	}
}
