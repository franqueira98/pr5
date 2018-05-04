package es.ucm.fdi.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.model.simobject.SimObject;

@SuppressWarnings("serial")
public class GUITable<T extends Describable> extends JPanel{
	JTable table;
	String[] fieldNames;
	List<T> elements;
	ListOfMapsTableModel model;
	public GUITable(String[] columnas,List<T> v){
		super(new BorderLayout());
		this.fieldNames=columnas;
		this.elements=v;
		model=new ListOfMapsTableModel();
		table=new JTable(model);
		add(new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
	public List<SimObject> getSelectedObjects(){
		List<SimObject> selected=new ArrayList<>();
		if(table.getSelectedRowCount() > 0){
			for(int i : table.getSelectedRows()){
				selected.add((SimObject)elements.get(i));
			}
		}
		return selected;
	}
	public void setBorder(String c){
		setBorder(BorderFactory.createTitledBorder(c));
	
	}
	public void setElements(List<T> elements){
		this.elements=elements;
		model.fireTableDataChanged();
	}
	private class ListOfMapsTableModel extends AbstractTableModel {
		public String getColumnName(int columnIndex) {
			return fieldNames[columnIndex];
		}
		// elements contiene la lista de elementos
		public int getRowCount() {
			return elements.size();
		}
		
		public int getColumnCount() {
			return fieldNames.length;
		}
		public Object getValueAt(int rowIndex,int columnIndex){
			HashMap<String,String> m=new HashMap<String,String>();
			elements.get(rowIndex).describe(m);
			return(
					fieldNames[columnIndex].equals("#") ? 
					""+rowIndex:m.get(fieldNames[columnIndex]));
			
		}
		// ineficiente: ¿puedes mejorarlo?
		/*public Object getValueAt(int rowIndex, int columnIndex) {
		return elements.get(rowIndex)
		.describe(new HashMap<String, String>())
		.get(fieldNames[columnIndex]);
		}*/

	}
}
