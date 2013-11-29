package org.jmailing.ui.project.source;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.source.Data;

public class SourceTablePanel extends JPanel {
	private SourceTableModel model = null;
	private JTable table = null;;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6596013834728945735L;

	public SourceTablePanel(SourceVariable[] variables) {
		setLayout(new BorderLayout(0, 0));
		model = new SourceTableModel(variables);
		table = new JTable(model);
		add(new JScrollPane(table));
	}
	
	public void changeSourceVariable(SourceVariable variable, int oldIndex) {
		model.changeSourceVariable(variable, oldIndex);
	}
	
	public void setData(List<Data> data) {
		model.setData(data);
	}
}
