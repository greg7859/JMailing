package org.jmailing.ui.project.history;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.StringUtils;
import org.jmailing.model.email.Attachment;
import org.jmailing.model.email.EMail;
import org.jmailing.ui.common.dialog.HtmlDialog;

public class HistoryEMailTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5540431418365195218L;

	private List<EMail> emails = null;

	final static private String[] HEADERS = { "", "Subject", "To", "Cc", "Bcc",
			"Body", "PDF" };
	final static private int INDEX = 0;
	final static private int SUBJECT = 1;
	final static private int TO = 2;
	final static private int CC = 3;
	final static private int BCC = 4;
	final static private int BODY = 5;
	final static private int PDF = 6;

	public void setEMail(List<EMail> emails) {
		this.emails = emails;
		fireTableStructureChanged();
	}

	@Override
	public int getColumnCount() {
		return HEADERS.length;
	}

	@Override
	public int getRowCount() {
		int count = 0;
		if (emails != null) {
			count = emails.size();
		}
		return count;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int colIndex) {
		String s = "";
		if (colIndex == INDEX)
			return new Integer(rowIndex + 1);
		else if (colIndex == SUBJECT) {
			return emails.get(rowIndex).getSubject();
		} else if (colIndex == TO) {
			return StringUtils.join(emails.get(rowIndex).getTo(), ",");
		} else if (colIndex == CC) {
			return StringUtils.join(emails.get(rowIndex).getCc(), ",");
		} else if (colIndex == BCC) {
			return StringUtils.join(emails.get(rowIndex).getBcc(), ",");
		} else if (colIndex == BODY) {

			final JButton button = new JButton("Show email");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new HtmlDialog(emails.get(rowIndex).getBody())
							.setVisible(true);
				}
			});
			return button;
		} else if (colIndex == PDF) {
			final JButton button = new JButton("Show PDF");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (Desktop.isDesktopSupported()) {
						Desktop desktop = Desktop.getDesktop();
						Attachment[] atts = emails.get(rowIndex)
								.getAttachments();
						if (atts.length > 0) {
							File file = new File(atts[0].getPath());
							if (desktop.isSupported(Desktop.Action.OPEN)) {
								try {
									desktop.open(file);
								} catch (IOException ioe) {
									JOptionPane.showMessageDialog(JOptionPane
											.getFrameForComponent(button), ioe
											.getMessage(), "Error PDF opening",
											JOptionPane.ERROR_MESSAGE);
									ioe.printStackTrace();
								}
							}
						}
					}
				}
			});
			return button;
		}
		return s;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return HEADERS[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == BODY || columnIndex == PDF)
			return JButton.class;
		else
			return String.class;
	}
}
