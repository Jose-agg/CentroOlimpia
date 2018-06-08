package igu.admin.actividades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;

import dataBase.DataBase;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import logica.objetos.Actividad;
import logica.objetos.EventoActividad;
import logica.objetos.Instalacion;
import logica.objetos.Reserva;
import logica.programa.Programa;

public class VentanaCancelarPlazaActividadAdmin extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblSeleccioneFecha;
	private JLabel lblSeleccioneLaActividad;
	private JLabel lblHoraDeLa;
	private JLabel lblNmeroDeSocio;
	private JTextField textFieldSocio;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private static String codU;
	private ArrayList<EventoActividad> horaInicioActividad;
	private EventoActividad ev;
	Programa p = new Programa();
	private JLabel lblSeleccioneLaIntalacin;
	private JComboBox<String> comboInstalaciones;
	private ArrayList<EventoActividad> eventosInsta ;
	private ArrayList<String> instalaciones;
	private JTextField textFieldActividad;
	private JTextField textFieldHoraInicio;
	private JTextField textFieldFecha;
	private PanelMostrarActividadesAdministrador pm;


	/**
	 * Create the dialog.
	 * @param panelMostrarActividadesAdministrador 
	 * @throws SQLException 
	 */
	public VentanaCancelarPlazaActividadAdmin(PanelMostrarActividadesAdministrador panelMostrarActividadesAdministrador) throws SQLException {
		setTitle("Ventana Admin: Cancelar plazas de socios a actividades.");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaCancelarPlazaActividadAdmin.class.getResource("/img/Logo_Olimpia.png")));
		setBounds(100, 100, 664, 356);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		pm = panelMostrarActividadesAdministrador;

		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			panel.add(getLblSeleccioneFecha());
			panel.add(getLblSeleccioneLaActividad());
			panel.add(getLblHoraDeLa());
			panel.add(getLblNmeroDeSocio());
			panel.add(getTextFieldSocio());
			panel.add(getLblSeleccioneLaIntalacin());
			panel.add(getComboInstalaciones());
			panel.add(getTextFieldActividad());
			panel.add(getTextFieldHoraInicio());
			panel.add(getTextFieldFecha());
		
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.add(getBtnAceptar());
			buttonPane.add(getBtnCancelar());
		}
		cargaInstalaciones();
	}
	private JLabel getLblSeleccioneFecha() {
		if (lblSeleccioneFecha == null) {
			lblSeleccioneFecha = new JLabel("Fecha Seleccionada:");
			lblSeleccioneFecha.setBounds(315, 115, 119, 20);
		}
		return lblSeleccioneFecha;
	}
	
	
	private void cargaInstalaciones() throws SQLException{
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		instalaciones = p.buscarInstalacionActividad(pm.getHora(), pm.dia(), pm.getActividad());
	
		for (String in : instalaciones) {			
		
					modelo.addElement(in);
	}

	comboInstalaciones.setModel(modelo);
	}
	
	
	
	
	private JLabel getLblSeleccioneLaActividad() {
		if (lblSeleccioneLaActividad == null) {
			lblSeleccioneLaActividad = new JLabel("Actividad Seleccionada:");
			lblSeleccioneLaActividad.setBounds(315, 42, 154, 14);
		}
		return lblSeleccioneLaActividad;
	}
	private JLabel getLblHoraDeLa() {
		if (lblHoraDeLa == null) {
			lblHoraDeLa = new JLabel("Hora de la Actividad:");
			lblHoraDeLa.setBounds(39, 115, 119, 20);
		}
		return lblHoraDeLa;
	}
	private JLabel getLblNmeroDeSocio() {
		if (lblNmeroDeSocio == null) {
			lblNmeroDeSocio = new JLabel("N\u00FAmero de Socio:");
			lblNmeroDeSocio.setBounds(39, 39, 125, 20);
		}
		return lblNmeroDeSocio;
	}
	// Numero de Socio no sea null
	private JTextField getTextFieldSocio() {
		if (textFieldSocio == null) {
			textFieldSocio = new JTextField();
			textFieldSocio.setBounds(170, 39, 95, 20);
			textFieldSocio.setColumns(10);
		}
		return textFieldSocio;
	}

	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Comprobar codigo socio
					if (p.buscarUsuarioS(textFieldSocio.getText())) {
						setCodU(textFieldSocio.getText());
						try {
							ev = p.buscarEventoActividades(pm.getActividad()
									.getCodActividad(), pm.dia(), pm.getHora()
									.trim());
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
							Actividad a = (Actividad) pm.getActividad();
							String h = textFieldHoraInicio.getText().trim();
							if (h != null) {
								String i = "";
								try {
									i = p.codInsta((String) comboInstalaciones
											.getSelectedItem());
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
//								System.out.println("HOLA");
//								System.out.println(i);
//								System.out.println(getCodU());
//								System.out.println(a.getCodActividad());
//								System.out.println(h);
//								System.out.println(fechaSeleccionada);
//								System.out.println(ev.getPlazas());
//								System.out.println(ev);
//								System.out.println(a.getNombreA());
								// PanelMostrarReservasSocios.getPrograma()
									
									try {
										if(p.buscarApuntadoB(getCodU(),a.getCodActividad(),ev.getFecha(),ev.getHoraInicio(),i)){
											try {
												if(p.cancelarPlazaSocio(p.buscarApuntado(getCodU(),a.getCodActividad(),ev.getFecha(),ev.getHoraInicio(),i)).equals("Cancelada")){
													int res = JOptionPane.showConfirmDialog(rootPane,"La plaza ha sido cancelada. ¿Desea hacer más cancelaciones?", "Cancelar", JOptionPane.YES_NO_OPTION);
														if(res==0){
															textFieldSocio.setText("");
															pm.repintarTabla();
														}else{
													pm.repintarTabla();
													dispose();
														}
												}
											} catch (HeadlessException
													| SQLException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}else{
											JOptionPane.showMessageDialog(rootPane, "No esta apuntado a esta actividad para este día y esta hora.");
										}
									} catch (HeadlessException | SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
								}
					
				}else{
					JOptionPane.showMessageDialog(rootPane, "Número incorrecto.");
				}
					}
				}
			
			);
		}
		return btnAceptar;
	}
	
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
		}
		return btnCancelar;
	}

	public static String getCodU() {
		return codU;
	}

	public static void setCodU(String codU) {
		VentanaCancelarPlazaActividadAdmin.codU = codU;
	}
	

	
	 private String formatearFechaActual() {
			Date today = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String folderName = formatter.format(today);
			return folderName;
		}
	 public Date sumarRestarDiasFecha(Date fecha, int dias){
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(fecha); // Configuramos la fecha que se recibe
		   calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
		    return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		
		
		  }
	 
	 public Date pasarFechaDate(String fe){
		 SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
	
		 Date fecha = null;
		 	try{
			fecha = formatoDelTexto.parse(fe);
			}catch (ParseException ex) {
				System.err.println("Error en la fecha");
			}
		 	return fecha;
	 }
	 private String formatearFecha(Date t) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String folderName = formatter.format(t);
			return folderName;
		}
	private JLabel getLblSeleccioneLaIntalacin() {
		if (lblSeleccioneLaIntalacin == null) {
			lblSeleccioneLaIntalacin = new JLabel("Seleccione la intalaci\u00F3n:");
			lblSeleccioneLaIntalacin.setBounds(39, 208, 189, 14);
		}
		return lblSeleccioneLaIntalacin;
	}
	private JComboBox getComboInstalaciones() {
		if (comboInstalaciones == null) {
			comboInstalaciones = new JComboBox();
			
			comboInstalaciones.setBounds(244, 205, 142, 20);
		}
		return comboInstalaciones;
	}
	
	
	
	private JTextField getTextFieldActividad() {
		if (textFieldActividad == null) {
			textFieldActividad = new JTextField();
			textFieldActividad.setText(pm.getActividad().getNombreA());
			textFieldActividad.setEditable(false);
			textFieldActividad.setBounds(495, 39, 119, 20);
			textFieldActividad.setColumns(10);
		}
		return textFieldActividad;
	}
	private JTextField getTextFieldHoraInicio() {
		if (textFieldHoraInicio == null) {
			textFieldHoraInicio = new JTextField();
			textFieldHoraInicio.setEditable(false);
			textFieldHoraInicio.setText(pm.getHora());
			textFieldHoraInicio.setBounds(168, 115, 97, 20);
			textFieldHoraInicio.setColumns(10);
		}
		return textFieldHoraInicio;
	}
	private JTextField getTextFieldFecha() {
		if (textFieldFecha == null) {
			textFieldFecha = new JTextField();
			textFieldFecha.setEditable(false);
			textFieldFecha.setText(pm.obtenerFecha());
			textFieldFecha.setBounds(487, 115, 127, 20);
			textFieldFecha.setColumns(10);
		}
		return textFieldFecha;
	}
	
	public boolean comboInsta(){
		if(comboInstalaciones.getItemCount()>0){
			return true;
		}
		return false;
	} 
}
