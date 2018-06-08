package modelos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.junit.experimental.theories.ParametersSuppliedBy;

import dataBase.DataBase;
import igu.socio.actividades.PanelMostrarActividadesSocio;
import logica.objetos.EventoActividad;
import logica.programa.Programa;

public class FormatoTablaActividadesSocio extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	Font normal = new Font("Arial", Font.PLAIN, 12);
	Font negrilla = new Font("Helvetica", Font.BOLD, 18);
	Font cursiva = new Font("Times new roman", Font.ITALIC, 12);
	private PanelMostrarActividadesSocio pn;
	
	public FormatoTablaActividadesSocio(PanelMostrarActividadesSocio pnMAS) {
		this.pn = pnMAS;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {
		setEnabled(table == null || table.isEnabled());

		JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, selected, focused, row, column);

		
		cell.setBackground(Color.WHITE);// color de fondo
		table.setFont(normal);// tipo de fuente
		table.setForeground(Color.black);// color de texto
		cell.setHorizontalAlignment(0);// derecha
		if(column==1) {
		table.setRowHeight(row, pn.getVentanaPrincipal().getPrograma().getAlturaFila(new String[] {(String)table.getValueAt(row, column),(String)table.getValueAt(row, column+1),(String)table.getValueAt(row, column+2),(String)table.getValueAt(row, column+3),(String)table.getValueAt(row, column+4),(String)table.getValueAt(row, column+5)}));
		}
		return this;
	}

	//

}