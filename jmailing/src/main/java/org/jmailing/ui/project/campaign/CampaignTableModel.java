package org.jmailing.ui.project.campaign;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.source.Data;

import com.google.common.collect.Lists;

public class CampaignTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5540431418365195218L;

	private List<SourceVariable> variables = new ArrayList<>();
	private List<Data> data = null;

	@Inject
	public CampaignTableModel(SourceVariable[] variables) {
		this.variables = Lists.newArrayList(variables);
	}

	public void setData(List<Data> data) {
		this.data = data;
		fireTableStructureChanged();
	}

	@Override
	public int getColumnCount() {
		return variables.size();
	}

	@Override
	public int getRowCount() {
		int count = 0;
		if (data != null) {
			count = data.size();
		}
		return count;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int colIndex) {
		String s = "";
		if (colIndex < variables.size()) {
			if (data != null) {
				s = data.get(rowIndex).get(variables.get(colIndex).getIndex());
			}
		} else {
			final JButton button = new JButton("See email");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(
							JOptionPane.getFrameForComponent(button),
							"Button clicked for row " + rowIndex);
				}
			});
			return button;
		}
		return s;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex < variables.size()) {
	return this.variables.get(columnIndex).getName();
		}
		else {
			return "Action";
		}
	}

}
