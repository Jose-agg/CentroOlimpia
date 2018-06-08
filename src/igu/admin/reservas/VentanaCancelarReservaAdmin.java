package igu.admin.reservas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataBase.DataBase;
import logica.objetos.Instalacion;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import modelos.ModeloTabla;

import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaCancelarReservaAdmin extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private PanelMostrarReservasAdministrador pnAnterior;
	private JLabel lblMotivo;
	private JComboBox<String> cbxMotivo;

	public final static String MOTIVO_1 = "Bajo petición usuario";
	public final static String MOTIVO_2 = "Cuestion meteorológica";
	public final static String MOTIVO_3 = "Necesidad del centro";
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panel;
	private JRadioButton rdbtnSocio;
	private JRadioButton rdbtnNoSocio;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField txtFecha;
	private JLabel lblFecha;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFin;
	private JComboBox<String> cbxInicio;
	private JComboBox<String> cbxFin;
	private JTable table;
	private JScrollPane scrollPane;
	private ModeloTabla modeloTabla;
	private JButton btnComprobar;

	/**
	 * Create the dialog.
	 */
	public VentanaCancelarReservaAdmin(PanelMostrarReservasAdministrador pnAnterior) {
		setTitle("Cancelar Reserva Socio");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VentanaCancelarReservaAdmin.class.getResource("/img/Logo_Olimpia.png")));
		this.pnAnterior = pnAnterior;
		setResizable(false);
		setBounds(100, 100, 450, 408);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblMotivo());
		contentPanel.add(getCbxMotivo());
		contentPanel.add(getPanel());
		contentPanel.add(getLblFecha());
		contentPanel.add(getTxtFecha());
		contentPanel.add(getLblUsuario());
		contentPanel.add(getTxtUsuario());
		contentPanel.add(getScrollPane());
		contentPanel.add(getBtnComprobar());
		getContentPane().add(getButtonPane(), BorderLayout.SOUTH);
		buttonPane.add(getOkButton());
		buttonPane.add(getCancelButton());
		try {
			mostrarTablaAdmin();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JButton getBtnComprobar() {
		if (btnComprobar == null) {
			btnComprobar = new JButton("Comprobar");
			btnComprobar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						if (getRdbtnSocio().isSelected()) {
							mostrarTablaSocios();
							// okButton.setEnabled(true);
						} else
							mostrarTablaNoSocios();
						// okButton.setEnabled(true);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnComprobar.setBounds(318, 167, 116, 23);
			btnComprobar.setVisible(false);
		}
		return btnComprobar;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(27, 95, 407, 240);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			modeloTabla = new ModeloTabla(new String[] { "Hora Inicio", "Hora Fin" }, 0);
			table = new JTable(modeloTabla);
			table.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					okButton.setEnabled(true);
				}
			});
			table.getTableHeader().setReorderingAllowed(false);
		}
		return table;
	}

	private void mostrarTablaSocios() throws SQLException {
		borrarContenidoTabla();
		String[] filas = new String[2];
		ArrayList<String[]> lista = DataBase.getHorasReservaSociosAdmin(pnAnterior.obtenerFecha(),
				((Instalacion) pnAnterior.getCbxInstalaciones().getSelectedItem()).getCodInstalacion(),
				getTxtUsuario().getText());
		for (int i = 0; i < lista.size(); i++) {
			filas[0] = lista.get(i)[0];
			filas[1] = lista.get(i)[1];
			modeloTabla.addRow(filas);
		}
	}

	private void mostrarTablaNoSocios() throws SQLException {
		borrarContenidoTabla();
		String[] filas = new String[2];
		ArrayList<String[]> lista = DataBase.getHorasReservaNoSociosAdmin(pnAnterior.obtenerFecha(),
				((Instalacion) pnAnterior.getCbxInstalaciones().getSelectedItem()).getCodInstalacion(),
				getTxtUsuario().getText());
		for (int i = 0; i < lista.size(); i++) {
			filas[0] = lista.get(i)[0];
			filas[1] = lista.get(i)[1];
			modeloTabla.addRow(filas);
		}
	}

	private void mostrarTablaAdmin() throws SQLException {
		borrarContenidoTabla();
		String[] filas = new String[2];
		ArrayList<String[]> lista = DataBase.getHorasReservaAdmin(pnAnterior.obtenerFecha(),
				((Instalacion) pnAnterior.getCbxInstalaciones().getSelectedItem()).getCodInstalacion());
		for (int i = 0; i < lista.size(); i++) {
			filas[0] = lista.get(i)[0];
			filas[1] = lista.get(i)[1];
			modeloTabla.addRow(filas);
		}
	}

	private void borrarContenidoTabla() {
		int nfilas = modeloTabla.getRowCount();
		for (int i = 0; i < nfilas; i++) {
			modeloTabla.removeRow(0);
		}
	}

	private JTextField getTxtFecha() {
		if (txtFecha == null) {
			txtFecha = new JTextField();
			txtFecha.setText(getPnAnterior().obtenerFecha());
			txtFecha.setEditable(false);
			txtFecha.setBounds(149, 25, 116, 20);
			txtFecha.setColumns(10);
		}
		return txtFecha;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha");
			lblFecha.setBounds(27, 28, 112, 14);
		}
		return lblFecha;
	}

	private PanelMostrarReservasAdministrador getPnAnterior() {
		return pnAnterior;
	}

	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (getRdbtnSocio().isSelected() && getCbxMotivo().getSelectedItem().equals(MOTIVO_1)) {
							getPnAnterior().getPrograma().cancelarReservaSocioAdmin(pnAnterior.obtenerFecha(),
									((Instalacion) pnAnterior.getCbxInstalaciones().getSelectedItem())
											.getCodInstalacion(),
									getTxtUsuario().getText(),
									(String) modeloTabla.getValueAt(table.getSelectedRow(), 0),
									(String) modeloTabla.getValueAt(table.getSelectedRow(), 1), MOTIVO_1);
							dispose();
						} else if (getRdbtnNoSocio().isSelected()
								&& getCbxMotivo().getSelectedItem().equals(MOTIVO_1)) {
							getPnAnterior().getPrograma().cancelarReservaNoSocioAdministracion(
									pnAnterior.obtenerFecha(),
									((Instalacion) pnAnterior.getCbxInstalaciones().getSelectedItem())
											.getCodInstalacion(),
									getTxtUsuario().getText(),
									(String) modeloTabla.getValueAt(table.getSelectedRow(), 0),
									(String) modeloTabla.getValueAt(table.getSelectedRow(), 1), MOTIVO_1);
							ventanaTicket(null);
							dispose();
						} else {
							String usuario = getPnAnterior().getPrograma().cancelarReservaAdmin(
									pnAnterior.obtenerFecha(),
									((Instalacion) pnAnterior.getCbxInstalaciones().getSelectedItem())
											.getCodInstalacion(),
									(String) modeloTabla.getValueAt(table.getSelectedRow(), 0),
									(String) modeloTabla.getValueAt(table.getSelectedRow(), 1),
									(String) getCbxMotivo().getSelectedItem());
							if (usuario != null && usuario.split("-").equals("NS")) {
								ventanaTicket(usuario);
							}
							dispose();
						}
						pnAnterior.mostrarTablaAdministrador(pnAnterior.HORAS, (Instalacion) pnAnterior.getInstalacionSeleccionada(), pnAnterior.obtenerFecha());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});
			okButton.setEnabled(false);
			okButton.setActionCommand("OK");

			getRootPane().setDefaultButton(okButton);
		}
		return okButton;

	}

	private JPanel getButtonPane() {
		if (buttonPane == null) {
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		}
		return buttonPane;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tipo de Usuario",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel.setBounds(27, 95, 407, 54);
			panel.add(getRdbtnSocio());
			panel.add(getRdbtnNoSocio());
			panel.setVisible(false);
		}
		return panel;
	}

	private JRadioButton getRdbtnNoSocio() {
		if (rdbtnNoSocio == null) {
			rdbtnNoSocio = new JRadioButton("No socio");
			rdbtnNoSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getLblUsuario().setText("DNI");
					getTxtUsuario().setText("");
				}
			});
			buttonGroup.add(rdbtnNoSocio);
		}
		return rdbtnNoSocio;
	}

	private JRadioButton getRdbtnSocio() {
		if (rdbtnSocio == null) {
			rdbtnSocio = new JRadioButton("Socio");
			rdbtnSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getLblUsuario().setText("N\u00FAmero de socio");
					getTxtUsuario().setText("");
				}
			});
			rdbtnSocio.setSelected(true);
			buttonGroup.add(rdbtnSocio);
		}
		return rdbtnSocio;
	}

	private JComboBox<String> getCbxMotivo() {
		if (cbxMotivo == null) {
			cbxMotivo = new JComboBox<String>();
			cbxMotivo.setModel(new DefaultComboBoxModel<String>(new String[] { MOTIVO_2, MOTIVO_3, MOTIVO_1 }));
			cbxMotivo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if (((String) cbxMotivo.getSelectedItem()).equals(MOTIVO_1)) {
						if(!pnAnterior.getPrograma().coincideFecha(pnAnterior.obtenerFecha())) {
						getPanel().setVisible(true);
						getLblUsuario().setVisible(true);
						getTxtUsuario().setVisible(true);
						getBtnComprobar().setVisible(true);
						borrarContenidoTabla();
						getOkButton().setEnabled(false);
						getScrollPane().setBounds(27, 209, 407, 126);
						}else {
							cbxMotivo.setSelectedIndex(0);
							JOptionPane.showMessageDialog(null, "Para cancelar una reserva "+MOTIVO_1.toLowerCase()+" tiene que ser un día antes");
						}
					} else {
						getPanel().setVisible(false);
						getLblUsuario().setVisible(false);
						getTxtUsuario().setVisible(false);
						getBtnComprobar().setVisible(false);
						getOkButton().setEnabled(false);
						getScrollPane().setBounds(27, 95, 407, 240);
						try {
							mostrarTablaAdmin();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			});
			cbxMotivo.setBounds(149, 64, 285, 20);
			contentPanel.add(cbxMotivo);
		}
		return cbxMotivo;
	}

	private JLabel getLblMotivo() {
		if (lblMotivo == null) {
			lblMotivo = new JLabel("Motivo");
			lblMotivo.setBounds(27, 67, 112, 14);
			contentPanel.add(lblMotivo);
		}
		return lblMotivo;
	}

	private JLabel getLblUsuario() {
		if (lblUsuario == null) {
			lblUsuario = new JLabel("N\u00FAmero de socio");
			lblUsuario.setBounds(27, 171, 116, 14);
			lblUsuario.setVisible(false);
		}
		return lblUsuario;
	}

	private JTextField getTxtUsuario() {
		if (txtUsuario == null) {
			txtUsuario = new JTextField();
			txtUsuario.setBounds(153, 168, 112, 20);
			txtUsuario.setColumns(10);
			txtUsuario.setVisible(false);
		}
		return txtUsuario;
	}

	private void ventanaTicket(String usuario) throws SQLException {
		VentanaTicket vt;
		if (usuario == null)
			vt = new VentanaTicket(pnAnterior.getPrograma()
					.buscarPago(txtUsuario.getText(), pnAnterior.getInstalacionSeleccionada().getCodInstalacion(),
							pnAnterior.obtenerFecha(), (String) modeloTabla.getValueAt(table.getSelectedRow(), 0),
							(String) modeloTabla.getValueAt(table.getSelectedRow(), 1))
					.toString(true));
		else
			vt = new VentanaTicket(pnAnterior.getPrograma()
					.buscarPago(usuario, pnAnterior.getInstalacionSeleccionada().getCodInstalacion(),
							pnAnterior.obtenerFecha(), (String) modeloTabla.getValueAt(table.getSelectedRow(), 0),
							(String) modeloTabla.getValueAt(table.getSelectedRow(), 1))
					.toString(true));
		vt.setLocationRelativeTo(this);
		vt.setModal(true);
		vt.setVisible(true);
	}
}
