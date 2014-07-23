package org.jmailing.ui.common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;

public class JTableButtonMouseListener extends MouseAdapter {
	private final JTable table;

	public JTableButtonMouseListener(JTable table) {
		this.table = table;
	}

	public void mouseClicked(MouseEvent e) {
		// get the index of the clumn
		int column = table.getColumnModel().getColumnIndexAtX(e.getX()); 
		int row = e.getY() / table.getRowHeight(); 

		/* Checking the row or column is valid or not */
		if (row < table.getRowCount() && row >= 0
				&& column < table.getColumnCount() && column >= 0) {
			Object value = table.getValueAt(row, column);
			if (value instanceof JButton) {
				/* perform a click event */
				((JButton) value).doClick();
			}
		}
	}

}
