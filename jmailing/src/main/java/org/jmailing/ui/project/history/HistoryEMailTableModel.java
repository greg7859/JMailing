package org.jmailing.ui.project.history;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.StringUtils;
import org.jmailing.model.email.EMail;
import org.jmailing.ui.common.dialog.HtmlDialog;

public class HistoryEMailTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5540431418365195218L;

	private List<EMail> emails = null;

	final static private String[] HEADERS = { "", "Subject", "To", "Cc", "Bcc",
			"Body" };
	final static private int INDEX = 0;
	final static private int SUBJECT = 1;
	final static private int TO = 2;
	final static private int CC = 3;
	final static private int BCC = 4;
	final static private int BODY = 5;

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

			final JButton button = new JButton("See email");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new HtmlDialog(emails.get(rowIndex).getBody()).setVisible(true);
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
		if (columnIndex == BODY)
			return JButton.class;
		else
			return String.class;
	}
}
