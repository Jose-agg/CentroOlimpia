package modelos;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ModeloCeldas extends DefaultTableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int columna ;

	public ModeloCeldas(int Colpatron)
	{
	    this.columna = Colpatron;
	}

	@Override
	public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
	{        
	    setBackground(Color.white);
	    table.setForeground(Color.black);
	    super.getTableCellRendererComponent(table, value, selected, focused, row, column);
	    if(table.getValueAt(row,columna).equals("Mi Reserva"))
	    {
	        this.setBackground(Color.RED);
	    }else if(table.getValueAt(row,columna).equals("Reservado")){
	        this.setBackground(Color.CYAN);
	    }else if(table.getValueAt(row, columna).equals("Libre")){
	        this.setBackground(Color.WHITE);
	    }else if(table.getValueAt(row,columna).equals("Administración"))
	    {
	        this.setBackground(Color.RED);
	    }
	    return this;
	  }
	  
	

}
