package igu.socio.reservas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.objetos.Reserva;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Toolkit;

public class VentanaCancelarReservaSocio extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblFecha;
	private JTextField textFieldFecha;
	private JLabel lblSeleccioneLaReserva;
	private JComboBox<String> comboBoxReservas;
	private JLabel lblInstalacinSeleccionada;
	private JTextField textFieldInstalacion;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private ArrayList<Reserva> reservasDelDia;


	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public VentanaCancelarReservaSocio() throws SQLException {
		setTitle("Centro Olimpia: Ventana Cancelar");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaCancelarReservaSocio.class.getResource("/img/Logo_Olimpia.png")));
		setBounds(100, 100, 549, 384);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelCentral = new JPanel();
			contentPanel.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setLayout(null);
			panelCentral.add(getLblFecha());
			panelCentral.add(getTextFieldFecha());
			panelCentral.add(getLblSeleccioneLaReserva());
			panelCentral.add(getComboBoxReservas());
			panelCentral.add(getLblInstalacinSeleccionada());
			panelCentral.add(getTextFieldInstalacion());
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.add(getBtnAceptar());
			buttonPane.add(getBtnCancelar());
		}
	}
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha Seleccionada:");
			lblFecha.setBounds(48, 44, 167, 14);
		}
		return lblFecha;
	}
	private JTextField getTextFieldFecha() {
		if (textFieldFecha == null) {
			textFieldFecha = new JTextField();
			textFieldFecha.setEditable(false);
			textFieldFecha.setBounds(263, 41, 118, 20);
			textFieldFecha.setColumns(10);
			textFieldFecha.setText(PanelMostrarReservasSocios.obtenerFecha());
		}
		return textFieldFecha;
	}
	private JLabel getLblSeleccioneLaReserva() {
		if (lblSeleccioneLaReserva == null) {
			lblSeleccioneLaReserva = new JLabel("Seleccione la hora de la Reserva:");
			lblSeleccioneLaReserva.setBounds(48, 161, 205, 14);
		}
		return lblSeleccioneLaReserva;
	}
	private JComboBox<String> getComboBoxReservas() throws SQLException {
		if (comboBoxReservas == null) {
			comboBoxReservas = new JComboBox<String>();
			comboBoxReservas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnAceptar.setEnabled(true);
					
				}
			});
			rellenarComboReserva();
			comboBoxReservas.setBounds(263, 158, 118, 20);
		}
		return comboBoxReservas;
	}
	private void rellenarComboReserva() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		String codUs = PanelMostrarReservasSocios.getCodU();
		String codIns = PanelMostrarReservasSocios.getCodI();
		String fecha = PanelMostrarReservasSocios.obtenerFecha();
		reservasDelDia = new ArrayList<Reserva>();
		for (Reserva reserva : PanelMostrarReservasSocios.getPrograma().getReservas()) {
			if(reserva.getCodUsuario().equals(codUs) && reserva.getCodInstalacion().equals(codIns) && reserva.getFechaReserva().equals(fecha)&&reserva.getCancelada().equals("false")){
			modelo.addElement(reserva.getDesde());
			reservasDelDia.add(reserva);
		
			}
		}
		comboBoxReservas.setModel(modelo);
	}
	private JLabel getLblInstalacinSeleccionada() {
		if (lblInstalacinSeleccionada == null) {
			lblInstalacinSeleccionada = new JLabel("Instalaci\u00F3n Seleccionada:");
			lblInstalacinSeleccionada.setBounds(48, 101, 167, 14);
		}
		return lblInstalacinSeleccionada;
	}
	private JTextField getTextFieldInstalacion() throws SQLException {
		if (textFieldInstalacion == null) {
			textFieldInstalacion = new JTextField();
			textFieldInstalacion.setEditable(false);
			textFieldInstalacion.setBounds(263, 98, 118, 20);
			textFieldInstalacion.setColumns(10);
			textFieldInstalacion.setText(PanelMostrarReservasSocios.nombreInstalacionSeleccionada());
		}
		return textFieldInstalacion;
	}
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
			btnAceptar.setEnabled(false);
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(PanelMostrarReservasSocios.getPrograma().cancelarReserva(reservasDelDia,comboBoxReservas.getSelectedItem().toString())){
						JOptionPane.showMessageDialog(contentPanel, "Reserva cancelada.");
						rellenarComboReserva();
						if(comboBoxReservas.getItemCount()==0){
							JOptionPane.showMessageDialog(contentPanel, "No hay mas reservas.");
							dispose();
						
						}
						
					}
				}
			});
		}
		return btnAceptar;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancelar;
	}
}
