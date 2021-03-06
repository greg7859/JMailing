package org.jmailing.ui.layout;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Utilities to compute the size of a column table.
 * <br/><br/>
 * <b>Source</b> :
 * http://www.java2s.com/Code/Java/Swing-Components/CalculatedColumnTable.htm
 *  
 */
public class TableUtilities {
	// Calculate the required width of a table column
	public static int calculateColumnWidth(JTable table, int columnIndex) {
		int width = 0; // The return value
		int rowCount = table.getRowCount();

		for (int i = 0; i < rowCount; i++) {
			TableCellRenderer renderer = table.getCellRenderer(i, columnIndex);
			Component comp = renderer.getTableCellRendererComponent(table,
					table.getValueAt(i, columnIndex), false, false, i,
					columnIndex);
			int thisWidth = comp.getPreferredSize().width;
			if (thisWidth > width) {
				width = thisWidth;
			}
		}

		return width;
	}

	// Set the widths of every column in a table
	public static void setColumnWidths(JTable table, Insets insets,
			boolean setMinimum, boolean setMaximum) {
		int columnCount = table.getColumnCount();
		TableColumnModel tcm = table.getColumnModel();
		int spare = (insets == null ? 0 : insets.left + insets.right);

		for (int i = 0; i < columnCount; i++) {
			int width = calculateColumnWidth(table, i);
			width += spare;

			TableColumn column = tcm.getColumn(i);
			column.setPreferredWidth(width);
			if (setMinimum == true) {
				column.setMinWidth(width);
			}
			if (setMaximum == true) {
				column.setMaxWidth(width);
			}
		}
	}

	// Sort an array of integers in place
	public static void sort(int[] values) {
		int length = values.length;
		if (length > 1) {
			for (int i = 0; i < length - 1; i++) {
				for (int j = i + 1; j < length; j++) {
					if (values[j] < values[i]) {
						int temp = values[i];
						values[i] = values[j];
						values[j] = temp;
					}
				}
			}
		}
	}
}
