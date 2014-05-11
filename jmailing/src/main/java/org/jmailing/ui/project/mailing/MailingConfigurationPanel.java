package org.jmailing.ui.project.mailing;

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
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;
import org.jmailing.model.project.MailingConfigurationPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.ui.filter.IntDocumentFilter;

public class MailingConfigurationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4623882503346852108L;

	public MailingConfigurationPanel() {
	}

	@Inject
	private void initPanel(MailingProject project) {
		MailingConfigurationPart mailingPart = project
				.getMailingConfigurationPart();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblNbMailPerHour = new JLabel("Number mail per hour");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.anchor = GridBagConstraints.EAST;
		gbc_lblTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTo.gridx = 0;
		gbc_lblTo.gridy = 0;
		add(lblNbMailPerHour, gbc_lblTo);

		JTextField nbMailPerHourField = new JTextField();
		PlainDocument doc = (PlainDocument) nbMailPerHourField.getDocument();
		doc.setDocumentFilter(new IntDocumentFilter());
		GridBagConstraints gbc_toField = new GridBagConstraints();
		gbc_toField.insets = new Insets(0, 0, 5, 5);
		gbc_toField.anchor = GridBagConstraints.NORTH;
		gbc_toField.fill = GridBagConstraints.HORIZONTAL;
		gbc_toField.gridx = 1;
		gbc_toField.gridy = 0;
		add(nbMailPerHourField, gbc_toField);
		nbMailPerHourField.setColumns(10);
		nbMailPerHourField.setText(String.valueOf(mailingPart
				.getNumberMailPerHour()));
		NumberPageActionListener npal = new NumberPageActionListener(mailingPart);
		nbMailPerHourField.addActionListener(npal);
		nbMailPerHourField.addFocusListener(npal);
	}
	
	class NumberPageActionListener implements ActionListener, FocusListener {
		MailingConfigurationPart mailingConfigPart = null;

		public NumberPageActionListener(MailingConfigurationPart mailingConfigPart) {
			this.mailingConfigPart = mailingConfigPart;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			updateAttachment(arg0.getSource());
		}

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			updateAttachment(arg0.getSource());
		}

		private void updateAttachment(Object o) {
			if (o instanceof JTextField) {
				int oldValue = mailingConfigPart.getNumberMailPerHour();
				int newValue = -1;
				JTextField tf = (JTextField) o;
				String txt = StringUtils.normalizeSpace(tf.getText());
				if (!StringUtils.isBlank(txt)) {
					newValue = Integer.parseInt(txt);
				}
				if (oldValue != newValue) {
					mailingConfigPart.setNumberMailPerHour(newValue);
				}
			}
		}
	}
}
