package org.jmailing.ui.project.campaign;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jmailing.model.source.Data;
import org.jmailing.ui.layout.TableUtilities;

public class CampaignTablePanel extends JPanel {
	private CampaignTableModel model = null;
	private JTable table = null;;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6596013834728945735L;

	@Inject
	public CampaignTablePanel(CampaignTableModel model) {
		setLayout(new BorderLayout(0, 0));
		this.model = model;
		table = new JTable(model);
//		TableCellRenderer buttonRenderer = new JTableButtonRenderer();
//        TableColumn col = table.getColumn(5);
//        col.setCellRenderer(buttonRenderer);
		// Automatically configure the column widths
		TableUtilities.setColumnWidths(table, new Insets(0, 4, 0, 4), true,
				false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		add(new JScrollPane(table));
	}

	public void setData(List<Data> data) {
		model.setData(data);
		TableUtilities.setColumnWidths(table, new Insets(0, 4, 0, 4), true,
				false);
	}
	
//	private static class JTableButtonRenderer implements TableCellRenderer {        
//        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            JButton button = (JButton)value;
//            return button;  
//        }
//    }
	
//	private static class JTableButtonMouseListener extends MouseAdapter {
//        private final JTable table;
//
//        public JTableButtonMouseListener(JTable table) {
//            this.table = table;
//        }
//
//        public void mouseClicked(MouseEvent e) {
//            int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
//            int row    = e.getY()/table.getRowHeight(); //get the row of the button
//
//                    /*Checking the row or column is valid or not*/
//            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
//                Object value = table.getValueAt(row, column);
//                if (value instanceof JButton) {
//                    /*perform a click event*/
//                    ((JButton)value).doClick();
//                }
//            }
//        }
//    }
	
}
