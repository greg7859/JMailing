package org.jmailing.ui.project.attachment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.ExtraAttachment;
import org.jmailing.model.project.MailingProject;
import org.jmailing.ui.filter.IntDocumentFilter;
import org.jmailing.ui.project.attachment.ExtraAttachmentActionListener.FieldType;

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
	private void initPanel(MailingProject project) {
		AttachmentMailingProjectPart attachmentPart = project
				.getAttachmentMailingProjectPart();
		// Group File and variable
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0,0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0,Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
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
		gbc_nbOfPage.insets = new Insets(0, 0, 5, 5);
		gbc_nbOfPage.gridx = 1;
		gbc_nbOfPage.gridy = 0;
		gbc_nbOfPage.gridwidth = GridBagConstraints.REMAINDER;
		add(nbOfPage, gbc_nbOfPage);
		nbOfPage.setColumns(10);
		NumberPageActionListener npal = new NumberPageActionListener(
				attachmentPart);
		nbOfPage.addActionListener(npal);
		nbOfPage.addFocusListener(npal);

		JLabel lblNewLabel = new JLabel("Filename format");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		filenameFormat = new JTextField();
		filenameFormat.setText(attachmentPart.getFilenameFormat());
		GridBagConstraints gbc_filenameFormat = new GridBagConstraints();
		gbc_filenameFormat.anchor = GridBagConstraints.NORTH;
		gbc_filenameFormat.fill = GridBagConstraints.HORIZONTAL;
		gbc_filenameFormat.insets = new Insets(0, 0, 0, 5);
		gbc_filenameFormat.gridx = 1;
		gbc_filenameFormat.gridy = 1;
		gbc_filenameFormat.gridwidth = GridBagConstraints.REMAINDER;
		add(filenameFormat, gbc_filenameFormat);
		filenameFormat.setColumns(20);
		FormatActionListener fal = new FormatActionListener(attachmentPart);
		filenameFormat.addActionListener(fal);
		filenameFormat.addFocusListener(fal);

		for (int i = 0; i < attachmentPart.getExtraAttachmentSize(); i++) {
			 buildExtraPanel(attachmentPart.getExtraAttachment(i), 2+2*i, i+1);
		}
	}

	private void buildExtraPanel(ExtraAttachment extraAttachment, int row, int value) {
		int index = row;
//		JPanel extraAttachmentPanel = new JPanel();
//		GridBagLayout gbl_panel = new GridBagLayout();
//		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
//		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
//		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0,
//				Double.MIN_VALUE };
//		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
//		extraAttachmentPanel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("Extra Filename");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = index;
		//extraAttachmentPanel.add(lblNewLabel, gbc_lblNewLabel);
		add(lblNewLabel, gbc_lblNewLabel);

		JTextField extraFilename = new JTextField();
		extraFilename.setText(extraAttachment.getFilename());
		GridBagConstraints gbc_filenameFormat = new GridBagConstraints();
		gbc_filenameFormat.anchor = GridBagConstraints.NORTH;
		gbc_filenameFormat.fill = GridBagConstraints.HORIZONTAL;
		gbc_filenameFormat.insets = new Insets(0, 0, 0, 5);
		gbc_filenameFormat.gridx = 1;
		gbc_filenameFormat.gridy = index;
		gbc_filenameFormat.gridwidth = GridBagConstraints.REMAINDER;
//		extraAttachmentPanel.add(extraFilename, gbc_filenameFormat);
		add(extraFilename, gbc_filenameFormat);
		extraFilename.setColumns(20);
		ExtraAttachmentActionListener fal = new ExtraAttachmentActionListener(
				extraAttachment, FieldType.FILENAME);
		extraFilename.addActionListener(fal);
		extraFilename.addFocusListener(fal);

		JLabel lblExtraAtt1 = new JLabel("Extra attachment " + value);
		GridBagConstraints gbc_lblExtraAtt1 = new GridBagConstraints();
		gbc_lblExtraAtt1.anchor = GridBagConstraints.EAST;
		gbc_lblExtraAtt1.insets = new Insets(0, 0, 0, 5);
		gbc_lblExtraAtt1.gridx = 0;
		gbc_lblExtraAtt1.gridy = 1 + index;
//		extraAttachmentPanel.add(lblExtraAtt1, gbc_lblExtraAtt1);
		add(lblExtraAtt1, gbc_lblExtraAtt1);

		JTextField extra1 = new JTextField();
		extra1.setText(extraAttachment.getFileToAttach());
		GridBagConstraints gbc_extra1 = new GridBagConstraints();
		gbc_extra1.anchor = GridBagConstraints.NORTH;
		gbc_extra1.fill = GridBagConstraints.HORIZONTAL;
		gbc_extra1.insets = new Insets(0, 0, 0, 5);
		gbc_extra1.gridx = 1;
		gbc_extra1.gridy = 1 + index;
//		extraAttachmentPanel.add(extra1, gbc_extra1);
		add(extra1, gbc_extra1);
		ExtraAttachmentActionListener eaal = new ExtraAttachmentActionListener(
				extraAttachment, FieldType.FILE_TO_ATTACH);
		extra1.addActionListener(eaal);
		extra1.addFocusListener(eaal);

		JButton b = new JButton("Select");
		GridBagConstraints gbc_b = new GridBagConstraints();
		gbc_b.anchor = GridBagConstraints.NORTH;
		gbc_b.fill = GridBagConstraints.HORIZONTAL;
		gbc_b.insets = new Insets(0, 0, 0, 5);
		gbc_b.gridx = 2;
		gbc_b.gridy = 1 + index;
//		extraAttachmentPanel.add(b, gbc_b);
		add(b, gbc_b);

//		GridBagConstraints gbc_Panel = new GridBagConstraints();
//		gbc_Panel.anchor = GridBagConstraints.CENTER;
//		gbc_Panel.fill = GridBagConstraints.HORIZONTAL;
//		gbc_Panel.insets = new Insets(0, 0, 0, 5);
//		gbc_Panel.gridx = 0;
//		gbc_Panel.gridy = row;
//		gbc_Panel.gridwidth = GridBagConstraints.REMAINDER;
//		add(extraAttachmentPanel, gbc_Panel);
	}

}
