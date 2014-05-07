package org.jmailing.ui.project.attachment;

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
import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.MailingProject;
import org.jmailing.ui.filter.IntDocumentFilter;

public class AttachmentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -970171901037894495L;
	private JTextField nbOfPage;
	private JTextField filenameFormat;
	
	public AttachmentPanel() {
	}
	
	@Inject
	private   void initPanel(MailingProject project) {
		AttachmentMailingProjectPart attachmentPart = project.getAttachmentMailingProjectPart();

		// Group File and variable
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_panel);
		
		JLabel lblNumberOfPage = new JLabel("Number Of page to split");
		GridBagConstraints gbc_lblNumberOfPage = new GridBagConstraints();
		gbc_lblNumberOfPage.anchor = GridBagConstraints.EAST;
		gbc_lblNumberOfPage.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfPage.gridx = 0;
		gbc_lblNumberOfPage.gridy = 0;
		add(lblNumberOfPage, gbc_lblNumberOfPage);
		
		nbOfPage = new JTextField();
		PlainDocument doc = (PlainDocument) nbOfPage.getDocument();
		doc.setDocumentFilter(new IntDocumentFilter());
		nbOfPage.setText(String.valueOf(attachmentPart.getNumberOfPageOfSplit()));
		GridBagConstraints gbc_nbOfPage = new GridBagConstraints();
		gbc_lblNumberOfPage.anchor = GridBagConstraints.EAST;
		gbc_nbOfPage.fill = GridBagConstraints.HORIZONTAL;
		gbc_nbOfPage.insets = new Insets(0, 0, 5, 0);
		gbc_nbOfPage.gridx = 1;
		gbc_nbOfPage.gridy = 0;
		add(nbOfPage, gbc_nbOfPage);
		nbOfPage.setColumns(10);
		NumberPageActionListener npal = new NumberPageActionListener(attachmentPart);
		nbOfPage.addActionListener(npal);
		nbOfPage.addFocusListener(npal);

		
		JLabel lblNewLabel = new JLabel("Filename format");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		filenameFormat = new JTextField();
		filenameFormat.setText(attachmentPart.getFilenameFormat());
		GridBagConstraints gbc_filenameFormat = new GridBagConstraints();
		gbc_filenameFormat.anchor = GridBagConstraints.NORTH;
		gbc_filenameFormat.fill = GridBagConstraints.HORIZONTAL;
		gbc_filenameFormat.gridx = 1;
		gbc_filenameFormat.gridy = 1;
		add(filenameFormat, gbc_filenameFormat);
		filenameFormat.setColumns(20);
		FormatActionListener fal = new FormatActionListener(attachmentPart);
		filenameFormat.addActionListener(fal);
		filenameFormat.addFocusListener(fal);
	}
	
	class NumberPageActionListener implements ActionListener, FocusListener {
		AttachmentMailingProjectPart attPart = null;

		public NumberPageActionListener(AttachmentMailingProjectPart attachmentPart) {
			attPart = attachmentPart;
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
				int oldValue = attPart.getNumberOfPageOfSplit();
				int newValue = -1;
				JTextField tf = (JTextField) o;
				String txt = StringUtils.normalizeSpace(tf.getText());
				if (!StringUtils.isBlank(txt)) {
					newValue = Integer.parseInt(txt);
				}
				if (oldValue != newValue) {
					attPart.setNumberOfPageOfSplit(newValue);
				}
			}
		}
	}
	
	class FormatActionListener implements ActionListener, FocusListener {
		AttachmentMailingProjectPart attPart = null;

		public FormatActionListener(AttachmentMailingProjectPart attachmentPart) {
			attPart = attachmentPart;
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
				String oldValue = attPart.getFilenameFormat();
				String newValue = null;
				JTextField tf = (JTextField) o;
				String txt = StringUtils.normalizeSpace(tf.getText());
				if (!StringUtils.isBlank(txt)) {
					newValue = txt;
				}
				if (oldValue.equals(newValue)==false) {
					attPart.setFilenameFormat(newValue);
				}
			}
		}
	}
}
