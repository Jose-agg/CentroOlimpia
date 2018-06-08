package igu.admin.reservas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataBase.DataBase;
import logica.objetos.Instalacion;
import logica.objetos.Reserva;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Toolkit;

public class VentanaUtilizarReserva extends JDialog {

	private final JPanel pnlOpciones = new JPanel();
	private PanelMostrarReservasAdministrador panelAnterior;
	private JTextField txtFecha;
	private JLabel lblSocio;
	private JTextField txtSocio;
	private JLabel lblFecha;
	private JLabel lblHoraDevolucion;
	private JLabel lblHoraRecogida;
	
	private String fechaSeleccionada;
	private JTextField txtInstalacion;
	private JTextField txtHoraRecogida;
	private JTextField txtHoraDevolucion;
	private JLabel lblInstalacion;
	private JLabel lblDNI;
	private JTextField txtDNI;
	private JRadioButton rdbSocios;
	private JRadioButton rdbNoSocios;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblSeleccionReserva;
	private ArrayList<Reserva> reservasHoy;
	private JTextField txtSeleccionReserva;
	private JButton btnCancelar;
	private JButton btnGuardar;

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public VentanaUtilizarReserva(PanelMostrarReservasAdministrador pnlAnterior) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaUtilizarReserva.class.getResource("/img/Logo_Olimpia.png")));
		setBounds(100, 100, 465, 374);
		setResizable(false);
		setTitle("Centro Olimpia: Actualizacion de Reservas");
		
		getContentPane().setLayout(new BorderLayout());
		pnlOpciones.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(pnlOpciones, BorderLayout.CENTER);
		pnlOpciones.setLayout(null);
		
		panelAnterior = pnlAnterior;
		comprobarReservas();
		
		pnlOpciones.add(getLblFecha());
		pnlOpciones.add(getTxtFecha());
		pnlOpciones.add(getLblInstalacion());
		pnlOpciones.add(getTxtInstalacion());
		pnlOpciones.add(getLblSeleccionReserva());
		pnlOpciones.add(getTxtSeleccionReserva());
		pnlOpciones.add(getLblHoraFinalizacion());
		pnlOpciones.add(getTxtHoraRecogida());
		
		pnlOpciones.add(getLblHoraInicio());
		pnlOpciones.add(getTxtHoraDevolucion());
		pnlOpciones.add(getRdbSocios());
		pnlOpciones.add(getRdbNoSocios());
		pnlOpciones.add(getLblSocio());
		pnlOpciones.add(getTxtSocio());
		pnlOpciones.add(getLblDNI());
		pnlOpciones.add(getTxtDNI());
		pnlOpciones.add(getBtnGuardar());
		pnlOpciones.add(getBtnCancelar());

		cambiarEntreRadioButtons(false);
	}
	
	private void comprobarReservas() throws SQLException{
		Date myDate = new Date();
		String dia = new SimpleDateFormat("dd/MM/yyyy").format(myDate);
		reservasHoy = panelAnterior.getPrograma().getReservasParaUnaInstalacionEnUnDia(panelAnterior.getInstalacionSeleccionada().getCodInstalacion(), dia);
	}

	private Component getLblHoraFinalizacion() {
		if (lblHoraRecogida == null) {
			lblHoraRecogida = new JLabel("Hora recogida llaves");
			lblHoraRecogida.setBounds(10, 124, 214, 14);
		}
		return lblHoraRecogida;
	}
	
	private Component getLblHoraInicio() {
		if (lblHoraDevolucion == null) {
			lblHoraDevolucion = new JLabel("Hora devolucion llaves");
			lblHoraDevolucion.setBounds(10, 158, 214, 14);
		}
		return lblHoraDevolucion;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha (dd/mm/aa)");
			lblFecha.setBounds(10, 28, 134, 16);
		}
		return lblFecha;
	}
	
	private JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setText(fechaSeleccionada);
			txtFecha.setEnabled(false);
			txtFecha.setEditable(false);
			Date myDate = new Date();
			txtFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(myDate));
			txtFecha.setBounds(331, 25, 116, 22);
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}
	private JLabel getLblSocio() {
		if (lblSocio == null) {
			lblSocio = new JLabel("Codigo Socio");
			lblSocio.setBounds(10, 229, 134, 16);
		}
		return lblSocio;
	}
	private JTextField getTxtSocio() {
		if (txtSocio == null) {
			txtSocio = new JTextField();
			txtSocio.setBounds(331, 226, 116, 22);
			txtSocio.setColumns(10);
		}
		return txtSocio;
	}
	private void cambiarEntreSocioNoSocio(boolean var){
		txtSocio.setEditable(false);
		txtSocio.setEnabled(false);
		txtInstalacion.setEnabled(var);
		txtInstalacion.setEditable(var);
	}
	private JTextField getTxtInstalacion() {
		if (txtInstalacion == null) {
			txtInstalacion = new JTextField();
			txtInstalacion.setEnabled(false);
			txtInstalacion.setEditable(false);
			txtInstalacion.setText(panelAnterior.getInstalacionSeleccionada().getInombre());
			txtInstalacion.setColumns(10);
			txtInstalacion.setBounds(331, 57, 116, 22);
		}
		return txtInstalacion;
	}
	private JTextField getTxtHoraRecogida() {
		if (txtHoraRecogida == null) {
			txtHoraRecogida = new JTextField();
			txtHoraRecogida.setEnabled(true);
			txtHoraRecogida.setEditable(true);
			txtHoraRecogida.setColumns(10);
			txtHoraRecogida.setBounds(331, 123, 116, 22);
		}
		return txtHoraRecogida;
	}
	private JTextField getTxtHoraDevolucion() {
		if (txtHoraDevolucion == null) {
			txtHoraDevolucion = new JTextField();
			txtHoraDevolucion.setEnabled(true);
			txtHoraDevolucion.setEditable(true);
			txtHoraDevolucion.setColumns(10);
			txtHoraDevolucion.setBounds(331, 157, 116, 22);
		}
		return txtHoraDevolucion;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalacion");
			lblInstalacion.setBounds(10, 61, 134, 14);
		}
		return lblInstalacion;
	}
	private JLabel getLblDNI() {
		if (lblDNI == null) {
			lblDNI = new JLabel("DNI");
			lblDNI.setBounds(10, 261, 134, 16);
		}
		return lblDNI;
	}
	private JTextField getTxtDNI() {
		if (txtDNI == null) {
			txtDNI = new JTextField();
			txtDNI.setColumns(10);
			txtDNI.setBounds(331, 258, 116, 22);
		}
		return txtDNI;
	}
	private JRadioButton getRdbSocios() {
		if (rdbSocios == null) {
			rdbSocios = new JRadioButton("Socio");
			rdbSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cambiarEntreRadioButtons(false);
				}
			});
			buttonGroup.add(rdbSocios);
			rdbSocios.setSelected(true);
			rdbSocios.setBounds(10, 193, 65, 25);
		}
		return rdbSocios;
	}
	private JRadioButton getRdbNoSocios() {
		if (rdbNoSocios == null) {
			rdbNoSocios = new JRadioButton("No Socios");
			rdbNoSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cambiarEntreRadioButtons(true);
				}
			});
			buttonGroup.add(rdbNoSocios);
			rdbNoSocios.setBounds(79, 193, 91, 25);
		}
		return rdbNoSocios;
	}
	
	private void cambiarEntreRadioButtons(boolean var){
		txtSocio.setText("");
		txtDNI.setText("");
		txtSocio.setEnabled(!var);
		txtSocio.setEditable(!var);
		txtDNI.setEnabled(var);
		txtDNI.setEditable(var);
	}
	private JLabel getLblSeleccionReserva() {
		if (lblSeleccionReserva == null) {
			lblSeleccionReserva = new JLabel("Seleccione reserva contratada");
			lblSeleccionReserva.setBounds(10, 95, 214, 16);
		}
		return lblSeleccionReserva;
	}
	private JTextField getTxtSeleccionReserva() {
		if (txtSeleccionReserva == null) {
			txtSeleccionReserva = new JTextField();
			txtSeleccionReserva.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					txtHoraRecogida.setEnabled(true);
					txtHoraRecogida.setEditable(true);
					boolean valido = false;
					Reserva res = null;
					for (Reserva reserva : reservasHoy) {
						if(reserva.getDesde().equals(txtSeleccionReserva.getText())){
							res = reserva;
							valido = true;
						}
					}
					if(!valido){
						JOptionPane.showMessageDialog(null, "Seleccione una hora a la que si exista una reserva");
					}else{
						txtHoraRecogida.setText(res.getRecogidaLlaves());
					}
				}
			});
			txtSeleccionReserva.setBounds(331, 92, 116, 22);
			txtSeleccionReserva.setColumns(10);
		}
		return txtSeleccionReserva;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancelar.setBounds(350, 301, 97, 25);
		}
		return btnCancelar;
	}
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton("Guardar");
			btnGuardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Reserva res = cogerReservaSeleccionada();
					if(rdbSocios.isSelected()){
						if(!res.getCodUsuario().equals(txtSocio.getText())){
							JOptionPane.showMessageDialog(null, "Esta reserva no esta a nombre de este socio.");
						}else{
							try {
								apuntarHorasModificadas();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else{
						if(!res.getCodUsuario().equals(txtDNI.getText())){
							JOptionPane.showMessageDialog(null, "Esta reserva no se hizo con este dni.");
						}else{
							try {
								apuntarHorasModificadas();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			});
			btnGuardar.setBounds(241, 301, 97, 25);
		}
		return btnGuardar;
	}
	private Reserva cogerReservaSeleccionada(){
		Reserva res = null;
		for (Reserva reserva : reservasHoy) {
			if(reserva.getDesde().equals(txtSeleccionReserva.getText())){
				res = reserva;
			}
		}
		return res;
	}
	
	private void apuntarHorasModificadas() throws SQLException{
		String horaRecogida = txtHoraRecogida.getText();
		String horaDevolucion = txtHoraDevolucion.getText();
		Reserva res = cogerReservaSeleccionada();
		panelAnterior.getPrograma().modificarHorasLlaves(horaRecogida, horaDevolucion, res);
		
	}
}
