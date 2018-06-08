package igu.admin.reservas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataBase.DataBase;
import igu.socio.reservas.PanelMostrarReservasSocios;
import logica.objetos.Instalacion;
import logica.objetos.Reserva;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Toolkit;

public class VentanaReservaSociosAdmin extends JDialog {

	private final JPanel pnlOpciones = new JPanel();
	private PanelMostrarReservasAdministrador panelAnterior;
	private JComboBox<String> cbxHoraInicio;
	private JComboBox<String> cbxHoraFinal;
	private JTextField txtFecha;
	private JLabel lblSocio;
	private JTextField txtSocio;
	private JLabel lblFecha;
	private JLabel lblHoraFinalizacion;
	private JLabel lblHoraInicio;

	private String fechaSeleccionada;
	private JRadioButton rdbSocios;
	private JRadioButton rdbNoSocios;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JLabel lblDNI;
	private JTextField txtDNI;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JLabel lblApellidos;
	private JTextField txtApellido;
	private JLabel lblTelefono;
	private JTextField txtTelefono;
	private JLabel lblCorreo;
	private JTextField txtCorreo;
	private JRadioButton rdbEfectivo;
	private JRadioButton rdbCuota;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnCancelar;
	private JButton btnReservar;

	/**
	 * Create the dialog.
	 */
	public VentanaReservaSociosAdmin(PanelMostrarReservasAdministrador pnlAnterior) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaReservaSociosAdmin.class.getResource("/img/Logo_Olimpia.png")));
		setBounds(100, 100, 379, 487);
		setResizable(false);
		setTitle("Centro Olimpia: Reserva de socios Admin");

		getContentPane().setLayout(new BorderLayout());
		pnlOpciones.setBorder(new EmptyBorder(5, 5, 5, 5));

		getContentPane().add(pnlOpciones, BorderLayout.CENTER);
		pnlOpciones.setLayout(null);

		panelAnterior = pnlAnterior;
		fechaSeleccionada = panelAnterior.obtenerFecha();
		pnlOpciones.add(getLblHoraFinalizacion());

		pnlOpciones.add(getCbxHoraInicio());

		pnlOpciones.add(getLblHoraInicio());
		pnlOpciones.add(getCbxHoraFinal());

		pnlOpciones.add(getLblFecha());
		pnlOpciones.add(getTxtFecha());
		pnlOpciones.add(getRdbSocios());
		pnlOpciones.add(getRdbNoSocios());
		pnlOpciones.add(getRdbEfectivo());
		pnlOpciones.add(getRdbCuota());
		pnlOpciones.add(getLblSocio());
		pnlOpciones.add(getTxtSocio());
		pnlOpciones.add(getLblDNI());
		pnlOpciones.add(getTxtDNI());
		pnlOpciones.add(getLblNombre());
		pnlOpciones.add(getTxtNombre());
		pnlOpciones.add(getLblApellidos());
		pnlOpciones.add(getTxtApellido());
		pnlOpciones.add(getLblTelefono());
		pnlOpciones.add(getTxtTelefono());
		pnlOpciones.add(getLblCorreo());
		pnlOpciones.add(getTxtCorreo());
		pnlOpciones.add(getBtnCancelar());
		pnlOpciones.add(getBtnReservar());

		cambiarEntreSocioNoSocio(false);
	}

	private Component getLblHoraFinalizacion() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora inicio");
			lblHoraInicio.setBounds(10, 60, 134, 14);
		}
		return lblHoraInicio;
	}

	private Component getLblHoraInicio() {
		if (lblHoraFinalizacion == null) {
			lblHoraFinalizacion = new JLabel("Hora finalizacion");
			lblHoraFinalizacion.setBounds(10, 94, 134, 14);
		}
		return lblHoraFinalizacion;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha (dd/mm/aa)");
			lblFecha.setBounds(10, 28, 134, 16);
		}
		return lblFecha;
	}

	public Date sumarRestarHorasFecha(Date fecha, int horas){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
		return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas

	}
	private String formatearFechaActual() {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String folderName = formatter.format(today);
		return folderName;
	}
	private boolean comprobarCamposVacios(){
		//		if(rdbSocios.isSelected()){
		//			return 
		//		}else{
		//			
		//		}
		return true;
	}

	private void ventanaTicket() throws SQLException{
		VentanaTicket vt= new VentanaTicket(panelAnterior.getPrograma().buscarPago(txtSocio.getText(),panelAnterior.getInstalacionSeleccionada().getCodInstalacion(),fechaSeleccionada,
				(String)cbxHoraInicio.getSelectedItem(),(String)cbxHoraFinal.getSelectedItem()).toString(false));
		vt.setLocationRelativeTo(this);
		vt.setModal(true);
		vt.setVisible(true);
	}

	private boolean comprobaciones(){
		if (txtSocio.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Rellene el codigo de usuario"); 
			return false;
		}

		String var = (String) cbxHoraInicio.getSelectedItem();
		String[] varArray = var.split(":");
		int horaInicio = Integer.parseInt(varArray[0]);

		var = (String) cbxHoraFinal.getSelectedItem();
		varArray = var.split(":");
		int horaFinal = Integer.parseInt(varArray[0]);

		if(horaInicio==horaFinal){
			JOptionPane.showMessageDialog(null, "Es necesaria una hora de diferencia entre la inicial y la final"); 
			return false;
		}
		if(horaInicio>horaFinal){
			JOptionPane.showMessageDialog(null, "Introduzca una hora de inicio y hora de finalizacion validas"); 
			return false;
		}
		if((horaInicio+2)<horaFinal){
			JOptionPane.showMessageDialog(null, "Solo se puede reservar un maximo de 2 horas"); 
			return false;
		}
		return true;
	}

	private JComboBox<String> getCbxHoraInicio() {
		if(cbxHoraInicio==null){
			cbxHoraInicio = new JComboBox<String>();
			cbxHoraInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cbxHoraFinal.setEnabled(true);
					rellenarHorasFinales();
				}
			});
			cbxHoraInicio.setBounds(245, 57, 116, 20);
			cbxHoraInicio.setModel(new DefaultComboBoxModel<String>(PanelMostrarReservasAdministrador.HORAS));
		}
		return cbxHoraInicio;
	}
	private void rellenarHorasFinales() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		String[] horas = PanelMostrarReservasSocios.HORAS_TERMINAR;
		int contador = 0;
		for (String string : horas) {
			contador++;
		
			if(cbxHoraInicio.getSelectedItem().equals(string)){
				if(contador == 24){
					contador--;
					modelo.addElement(horas[0]);
					modelo.addElement(horas[1]);
		
				}else{
					if(contador==23){
						modelo.addElement(horas[contador]);
					}else{
					modelo.addElement(horas[contador]);
					modelo.addElement(horas[contador+1]);
					}
	
				}
				
			}
		}
		cbxHoraFinal.setModel(modelo);
	}
	private JComboBox<String> getCbxHoraFinal() {
		if (cbxHoraFinal == null) {
			cbxHoraFinal = new JComboBox();
			cbxHoraFinal.setBounds(245, 90, 116, 22);
			cbxHoraFinal.setEnabled(false);
		}
		return cbxHoraFinal;
	}
	private JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setText(fechaSeleccionada);
			txtFecha.setEnabled(false);
			txtFecha.setEditable(false);
			txtFecha.setBounds(245, 25, 116, 22);
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}
	private JLabel getLblSocio() {
		if (lblSocio == null) {
			lblSocio = new JLabel("Codigo Socio");
			lblSocio.setBounds(10, 202, 134, 16);
		}
		return lblSocio;
	}
	private JTextField getTxtSocio() {
		if (txtSocio == null) {
			txtSocio = new JTextField();
			txtSocio.setBounds(245, 199, 116, 22);
			txtSocio.setColumns(10);
		}
		return txtSocio;
	}
	private JRadioButton getRdbSocios() {
		if (rdbSocios == null) {
			rdbSocios = new JRadioButton("Socio");
			rdbSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					txtSocio.setText("");
					cambiarEntreSocioNoSocio(false);
				}
			});
			rdbSocios.setSelected(true);
			buttonGroup_1.add(rdbSocios);
			rdbSocios.setBounds(10, 130, 65, 25);
		}
		return rdbSocios;
	}
	private void cambiarEntreSocioNoSocio(boolean var){
		txtSocio.setEditable(!var);
		txtSocio.setEnabled(!var);
		rdbCuota.setEnabled(!var);
		rdbEfectivo.setEnabled(!var);
		txtDNI.setEnabled(var);
		txtDNI.setEditable(var);
		txtNombre.setEnabled(var);
		txtNombre.setEditable(var);
		txtApellido.setEnabled(var);
		txtApellido.setEditable(var);
		txtTelefono.setEnabled(var);
		txtTelefono.setEditable(var);
		txtCorreo.setEnabled(var);
		txtCorreo.setEditable(var);
	}
	private JRadioButton getRdbNoSocios() {
		if (rdbNoSocios == null) {
			rdbNoSocios = new JRadioButton("No Socios");
			rdbNoSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					txtSocio.setText("NoSocio");
					cambiarEntreSocioNoSocio(true);
				}
			});
			buttonGroup_1.add(rdbNoSocios);
			rdbNoSocios.setBounds(102, 130, 91, 25);
		}
		return rdbNoSocios;
	}
	private JLabel getLblDNI() {
		if (lblDNI == null) {
			lblDNI = new JLabel("DNI");
			lblDNI.setBounds(10, 237, 134, 16);
		}
		return lblDNI;
	}
	private JTextField getTxtDNI() {
		if (txtDNI == null) {
			txtDNI = new JTextField();
			txtDNI.setColumns(10);
			txtDNI.setBounds(245, 234, 116, 22);
		}
		return txtDNI;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 269, 134, 16);
		}
		return lblNombre;
	}
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setColumns(10);
			txtNombre.setBounds(245, 266, 116, 22);
		}
		return txtNombre;
	}
	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellido");
			lblApellidos.setBounds(10, 301, 134, 16);
		}
		return lblApellidos;
	}
	private JTextField getTxtApellido() {
		if (txtApellido == null) {
			txtApellido = new JTextField();
			txtApellido.setColumns(10);
			txtApellido.setBounds(245, 298, 116, 22);
		}
		return txtApellido;
	}
	private JLabel getLblTelefono() {
		if (lblTelefono == null) {
			lblTelefono = new JLabel("Telefono");
			lblTelefono.setBounds(10, 336, 134, 16);
		}
		return lblTelefono;
	}
	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setColumns(10);
			txtTelefono.setBounds(245, 333, 116, 22);
		}
		return txtTelefono;
	}
	private JLabel getLblCorreo() {
		if (lblCorreo == null) {
			lblCorreo = new JLabel("Correo");
			lblCorreo.setBounds(10, 368, 134, 16);
		}
		return lblCorreo;
	}
	private JTextField getTxtCorreo() {
		if (txtCorreo == null) {
			txtCorreo = new JTextField();
			txtCorreo.setColumns(10);
			txtCorreo.setBounds(245, 365, 116, 22);
		}
		return txtCorreo;
	}
	private JRadioButton getRdbEfectivo() {
		if (rdbEfectivo == null) {
			rdbEfectivo = new JRadioButton("Efectivo");
			rdbEfectivo.setSelected(true);
			buttonGroup.add(rdbEfectivo);
			rdbEfectivo.setBounds(10, 168, 85, 25);
		}
		return rdbEfectivo;
	}
	private JRadioButton getRdbCuota() {
		if (rdbCuota == null) {
			rdbCuota = new JRadioButton("Cuota");
			buttonGroup.add(rdbCuota);
			rdbCuota.setBounds(102, 168, 91, 25);
		}
		return rdbCuota;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancelar.setActionCommand("Cancelar");
			btnCancelar.setBounds(264, 414, 97, 25);
		}
		return btnCancelar;
	}
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(comprobarCamposVacios()){
						Date di =sumarRestarHorasFecha(new Date(),1);
						String si = String.valueOf(di.getHours())+":00";
						if(panelAnterior.getPrograma().comprobarHoras(String.valueOf(cbxHoraInicio.getSelectedItem()),si)&& panelAnterior.obtenerFecha().equals(formatearFechaActual())){
							JOptionPane.showMessageDialog(null, "Hora pasada.");
						}else{
							Date d =sumarRestarHorasFecha(new Date(),1);
							String s = String.valueOf(d.getHours())+":00";
							if(cbxHoraInicio.getSelectedItem().equals(s)&& panelAnterior.obtenerFecha().equals(formatearFechaActual())){
								JOptionPane.showMessageDialog(null, "Solo se puede reservar 1h antes.");
							}else{
								if(rdbNoSocios.isSelected()){
									try {
										txtSocio.setText(panelAnterior.getPrograma().agregarNuevoNoSocio(txtDNI.getText(),txtNombre.getText(),txtApellido.getText(), txtTelefono.getText(), txtCorreo.getText()));

									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								if (comprobaciones()){
									try {
										String tipoPago ="";
										if(rdbSocios.isSelected()){
											if(rdbCuota.isSelected()){
												tipoPago = "Cuota";
											}else{
												tipoPago = "Efectivo";
											}
										}else{
											tipoPago ="null";
										}
										String var = panelAnterior.getPrograma().comprobarReservaSociosAdmin(txtSocio.getText(),panelAnterior.getInstalacionSeleccionada().getCodInstalacion(),fechaSeleccionada,
												(String) cbxHoraInicio.getSelectedItem(),(String) cbxHoraFinal.getSelectedItem(),rdbNoSocios.isSelected(), tipoPago);
										switch (var){
										case "ocupada":
											JOptionPane.showMessageDialog(null, "Esta instalacion a esta hora y dia esta ocupada."); 
											break;
										case "reservaRepetida":
											JOptionPane.showMessageDialog(null, "Ya se ha hecho esta reserva."); 
											break;
										case "excesoNumHoras":
											JOptionPane.showMessageDialog(null, "Contacte con el servicio tecnico."); 
											break;
										case "error":
											JOptionPane.showMessageDialog(null, "Error: pruebe otra vez"); 
											break;
										case "simultaneo":
											JOptionPane.showMessageDialog(null, "Este usuario ya tiene una reserva a esta hora este mismo dia."); 
											break;
										case "userNoEncontrado":
											JOptionPane.showMessageDialog(null, "Este codigo de socio no es valido"); 
											break;
										case "añadidos":
											JOptionPane.showMessageDialog(null, "Reserva realizada con exito.");
											panelAnterior.mostrarTablaAdministrador(panelAnterior.HORAS, (Instalacion) panelAnterior.getInstalacionSeleccionada(), fechaSeleccionada);
											break;
										case "AddNS":
											JOptionPane.showMessageDialog(null, "Reserva realizada con exito.");
											dispose();
											panelAnterior.mostrarTablaAdministrador(PanelMostrarReservasAdministrador.HORAS, (Instalacion) panelAnterior.getInstalacionSeleccionada(), fechaSeleccionada);
											ventanaTicket();
											break;
										case "actividadSolapada":
											JOptionPane.showMessageDialog(null, "Este socio esta apuntado a una actividad a esta hora"); 
											break;
										}
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}
						}
					}else{
						JOptionPane.showMessageDialog(null, "Es necesario rellenar todos los campos para hacer una reserva.");
					}
				}
			});
			btnReservar.setActionCommand("Reservar");
			getRootPane().setDefaultButton(btnReservar);
			btnReservar.setBounds(155, 414, 97, 25);
		}
		return btnReservar;
	}
}
