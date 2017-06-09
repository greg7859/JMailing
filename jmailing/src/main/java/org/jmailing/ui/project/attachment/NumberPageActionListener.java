package org.jmailing.ui.project.attachment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.jmailing.model.project.AttachmentMailingProjectPart;

public class NumberPageActionListener implements ActionListener, FocusListener {
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