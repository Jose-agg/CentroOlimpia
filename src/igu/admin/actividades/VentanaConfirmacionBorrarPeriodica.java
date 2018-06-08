package igu.admin.actividades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.objetos.Actividad;
import logica.objetos.Apuntados;
import logica.programa.CancelarActividadPeriodica;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.TextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaConfirmacionBorrarPeriodica extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnDatos;
	private TextArea textArea;
	private JLabel lblActividad;
	private JTextField txtActividad;
	private PanelMostrarActividadesAdministrador pma;
	private JPanel pnBotones;
	private JButton btnBorrar;
	private JButton btnCancelar;
	private JPanel pnActividad;
	private JLabel lblInformacion;
	private CancelarActividadPeriodica listaGlobal;
	
	public VentanaConfirmacionBorrarPeriodica(Actividad actividadParaBorrarP, String fechaParaBorrarP,PanelMostrarActividadesAdministrador panelMostrarActividadesAdministrador) {
		setResizable(false);
		this.pma=panelMostrarActividadesAdministrador;
		this.listaGlobal=crearListaActividades(actividadParaBorrarP, fechaParaBorrarP);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaConfirmacionBorrarPeriodica.class.getResource("/img/Logo_Olimpia.png")));
		setTitle("Borrar Actividades Periodicas");
		setBounds(100, 100, 450, 476);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getPnDatos(), BorderLayout.NORTH);
		getContentPane().add(getTextArea(), BorderLayout.CENTER);
		getContentPane().add(getPnBotones(), BorderLayout.SOUTH);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		crearArea();
	}
	private CancelarActividadPeriodica crearListaActividades(Actividad actividadParaBorrarP, String fechaParaBorrarP) {
		return pma.getVP().getPrograma().borrarActividadPeriodica(actividadParaBorrarP, fechaParaBorrarP);
	}
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setLayout(new BorderLayout(0, 0));
			pnDatos.add(getPnActividad(), BorderLayout.SOUTH);
			pnDatos.add(getLblInformacion(), BorderLayout.NORTH);
		}
		return pnDatos;
	}
	private TextArea getTextArea() {
		if (textArea == null) {
			textArea = new TextArea();
			textArea.setEditable(false);
			textArea.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		return textArea;
	}
	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad");
			lblActividad.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblActividad.setForeground(Color.BLACK);
		}
		return lblActividad;
	}
	private JTextField getTxtActividad() {
		if (txtActividad == null) {
			txtActividad = new JTextField();
			txtActividad.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtActividad.setForeground(Color.BLACK);
			txtActividad.setDisabledTextColor(Color.BLACK);
			txtActividad.setEnabled(false);
			txtActividad.setEditable(false);
			txtActividad.setColumns(10);
		}
		return txtActividad;
	}
	private void crearArea() {
		int cont=0;
		String s = "";
		String instalacion="";
		for (Map<String, String> map : listaGlobal.getEstadoLista()) {
			if(cont==0) {
				txtActividad.setText(devolverNombreActividad(map.get("codActividad")));
				cont++;
			}
			s="";
			if(instalacion.equals(devolverNombreInstalacion(map.get("codInstalacion")))) {
				s+="\tFecha: "+map.get("fecha")+" - ";
				s+="Hora: "+map.get("horaInicio")+".\n";
			}else {
				instalacion = devolverNombreInstalacion(map.get("codInstalacion"));
				s+="Instalacion: "+instalacion+"\n";
				s+="\tFecha: "+map.get("fecha")+" - ";
				s+="Hora: "+map.get("horaInicio")+".\n";
			}
			textArea.append(s);
		}
	}
	private String devolverNombreInstalacion(String cod) {
		return pma.getVP().getPrograma().devolverNombreInstalacion(cod);
	}
	private String devolverNombreActividad(String cod) {
		return pma.getVP().getPrograma().devolverNombreActividad(cod);
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.add(getBtnBorrar());
			pnBotones.add(getBtnCancelar());
		}
		return pnBotones;
	}
	private JButton getBtnBorrar() {
		if (btnBorrar == null) {
			btnBorrar = new JButton("Borrar");
			btnBorrar.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(null, "¿Esta seguro de que quiere borrar estas actividades?")==JOptionPane.YES_OPTION) {
						// En listaGlobal tienes un metodo getEstadoLista que devuelve una lista de HashMaps con todos los datos de las actividades 
						// que se van a borrar, guardados en HashMaps de la siguiente forma:
						// Key: "codActividad", Value: el codigo de la actividad
						// Key: "codInstalacion", Value: el codigo de la instalacion
						// Key: "fecha", Value: la fecha de esa actividad
						// Key: "horaInicio", Value: la hora de inicio de esa actividad
						// Asi que tendrias que coger las HashMaps de hoy y mañana y ver si tienen gente apuntada y a esa gente enviarle un mensaje o hacerlo como quieras
						//
						mostrarApuntadosAvisar();
						//
						// Despues de este comentario se borraran todas las personas apuntadas a una actividad, se borraran todas las actividades y se cancelará la reserva.
						listaGlobal.borrarDatos();
						dispose();
					}else;
				}
			});
		}
		return btnBorrar;
	}
	protected void mostrarApuntadosAvisar() {
		List<HashMap<String, String>> apuntados = listaGlobal.getApuntados();
		this.pma.mostrarVentanaApuntadosAvisar(apuntados);
		
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 15));
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancelar;
	}
	private JPanel getPnActividad() {
		if (pnActividad == null) {
			pnActividad = new JPanel();
			pnActividad.add(getLblActividad());
			pnActividad.add(getTxtActividad());
		}
		return pnActividad;
	}
	private JLabel getLblInformacion() {
		if (lblInformacion == null) {
			lblInformacion = new JLabel("Estas son las actividades a borrar:");
			lblInformacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblInformacion;
	}
}
