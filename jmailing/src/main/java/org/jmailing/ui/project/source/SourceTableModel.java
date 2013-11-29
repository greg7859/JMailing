package org.jmailing.ui.project.source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.jmailing.model.project.SourceVariable;
import org.jmailing.model.source.Data;

public class SourceTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5540431418365195218L;
	
	private Map<Integer, SourceVariable> variables = new HashMap<>();
	private List<Data> data=null;
	
	public SourceTableModel(SourceVariable[] variables) {
		for (SourceVariable sourceVariable : variables) {
			this.variables.put(sourceVariable.getIndex(), sourceVariable);
		}
	}
	
	public void changeSourceVariable(SourceVariable variable, int oldIndex) {
		this.variables.remove(oldIndex);
		this.variables.put(variable.getIndex(), variable);
		fireTableStructureChanged();
	}
	
	public void setData(List<Data> data) {
		this.data=data;
		fireTableStructureChanged();
	}
	
	@Override
	public int getColumnCount() {
		int count=0;
		if (data!=null) {
			count=data.get(0).size();
		}
		return count;
	}

	@Override
	public int getRowCount() {
		int count=0;
		if (data!=null) {
			count=data.size();
		}
		return count;
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		String s = "";
		if (data!=null) {
			s=data.get(rowIndex).get(colIndex);
		}
		return s;
	}
	
	@Override
   public String getColumnName(int columnIndex) {		
		String s=null;
		if (this.variables.containsKey(columnIndex)) {
			s=this.variables.get(columnIndex).getName();
		}
		else {
			s=String.valueOf(columnIndex+1);
		}

        return s;
    }

}
