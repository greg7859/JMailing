package org.jmailing.ui.project.attachment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.jmailing.model.project.AttachmentMailingProjectPart;
import org.jmailing.model.project.ExtraAttachment;

public class ExtraAttachmentActionListener implements ActionListener,
		FocusListener {
	public enum FieldType {
		FILENAME, FILE_TO_ATTACH
	}

	private ExtraAttachment extraAttachment;
	private FieldType fieldType = FieldType.FILENAME;

	public ExtraAttachmentActionListener(ExtraAttachment extraAttachment,
			FieldType fieldType) {
		this.extraAttachment = extraAttachment;
		this.fieldType = fieldType;
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
			String oldValue = fieldType == FieldType.FILENAME ? extraAttachment
					.getFilename() : extraAttachment.getFileToAttach();
			String newValue = null;
			JTextField tf = (JTextField) o;
			String txt = StringUtils.normalizeSpace(tf.getText());
			if (!StringUtils.isBlank(txt)) {
				newValue = txt;
			}
			if (oldValue == null || oldValue.equals(newValue) == false) {
				if (fieldType == FieldType.FILENAME)
					extraAttachment.setFilename(newValue);
				else
					extraAttachment.setFileToAttach(newValue);
			}
		}
	}
}