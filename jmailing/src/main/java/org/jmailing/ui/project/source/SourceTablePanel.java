package org.jmailing.ui.project.source;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.source.Data;
import org.jmailing.ui.layout.TableUtilities;

public class SourceTablePanel extends JPanel {
	private SourceTableModel model = null;
	private JTable table = null;;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6596013834728945735L;

	@Inject
	public SourceTablePanel(SourceVariable[] variables) {
		setLayout(new BorderLayout(0, 0));
		model = new SourceTableModel(variables);
		table = new JTable(model);
		   // Automatically configure the column widths
	    TableUtilities
	        .setColumnWidths(table, new Insets(0, 4, 0, 4), true, false);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		add(new JScrollPane(table));
	}
	
	public void changeSourceVariable(SourceVariable variable, int oldIndex) {
		model.changeSourceVariable(variable, oldIndex);
	}
	
	public void setData(List<Data> data) {
		model.setData(data);
	}
}
