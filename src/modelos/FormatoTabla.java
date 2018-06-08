package modelos;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import dataBase.DataBase;
import logica.objetos.EventoActividad;
/**
 * @web http://jc-mouse.blogspot.com/
 * @author Mouse
 */
public class FormatoTabla extends DefaultTableCellRenderer{    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Font normal = new Font( "Arial",Font.PLAIN,12 );
    Font negrilla = new Font( "Helvetica",Font.BOLD,18 );
    Font cursiva = new Font( "Times new roman",Font.ITALIC,12 );

    @Override 
    public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column ) 
    {
        setEnabled(table == null || table.isEnabled()); 
        
        setBackground(Color.WHITE);//color de fondo
        table.setFont(normal);//tipo de fuente
        table.setForeground(Color.black);//color de texto
        setHorizontalAlignment(2);//derecha
                
        //si la celda esta vacia se reemplaza por el texto "<vacio>" y se rellena la celda de color negro y fuente color blanco
        if(String.valueOf(table.getValueAt(row,column)).equals("")||String.valueOf(table.getValueAt(row,column)).equals("<vacio>")){            
            table.setValueAt("vacio", row, column);
            setBackground(Color.black);                         
            table.setForeground(Color.white);
            table.setFont(cursiva);
        }
        /*--------*/
        if(String.valueOf(table.getValueAt(row,column)).equals("Reservado")){
        	
            setBackground(Color.cyan); 
            table.setFont(negrilla);                
            setHorizontalAlignment(0);//centro
            table.setForeground(Color.cyan);
            
        }                        
        /*--------*/
        if(String.valueOf(table.getValueAt(row,column)).equals("Libre")){
            setBackground(Color.white);         
            table.setFont(negrilla);                
            setHorizontalAlignment(0);//centro
            table.setForeground(Color.white);
            
        }
         /*--------*/   
        if(String.valueOf(table.getValueAt(row,column)).equals("Mi Reserva")){
        
            setBackground(Color.red);     
            setHorizontalAlignment(0);//centro
            setVisible(false);
            table.setForeground(Color.red);
            
        }        
        /*--------*/

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);         
        return this;
 }
    
 //

}