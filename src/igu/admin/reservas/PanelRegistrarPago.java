package igu.admin.reservas;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import dataBase.DataBase;
import date.DateComparable;
import igu.main.VentanaPrincipal;
import logica.objetos.Reserva;
import modelos.ModeloTabla;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelRegistrarPago extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vPrincipal;
	private JPanel pnInfo;
	private JScrollPane scrllTabla;
	private JLabel lblReservas;
	private JTable tableReservas;
	private ModeloTabla modeloTabla;
	private ArrayList<Reserva> reservasSocios;
	private ArrayList<Reserva> reservasSinPagar;
	private JPanel pnDatos;
	private JLabel lblNombre;
	private JTextField txfNombre;
	private JPanel pnSocio;
	private JLabel lblDni;
	private JTextField txfDNI;
	private JPanel pnReserva;
	private JLabel lblInstalacion;
	private JTextField txfInstalacion;
	private JLabel lblPrecioPorHora;
	private JTextField txfPrecioHora;
	private JLabel lblTotal;
	private JTextField txfTotal;
	private JPanel pnFactura;
	private JPanel pnBotones;
	private JButton btnRegistrarPago;
	private JButton btnPagoAdeudado;
	private Reserva reservaSeleccionada;

	/**
	 * Create the panel.
	 */
	public PanelRegistrarPago(VentanaPrincipal vPrincipal) {
		this.vPrincipal = vPrincipal;
		setBounds(100, 100, 925, 573);
		setLayout(new BorderLayout(0, 0));
		add(getPnInfo(), BorderLayout.CENTER);
	}
	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			pnInfo.setBorder(null);
			pnInfo.setBackground(Color.WHITE);
			pnInfo.setLayout(new BorderLayout(0, 0));
			pnInfo.add(getScrllTabla(), BorderLayout.CENTER);
			pnInfo.add(getLblReservas(), BorderLayout.NORTH);
			pnInfo.add(getPnFactura(), BorderLayout.SOUTH);
		}
		return pnInfo;
	}
	private JScrollPane getScrllTabla() {
		if (scrllTabla == null) {
			scrllTabla = new JScrollPane();
			scrllTabla.setViewportView(getTableReservas());
		}
		return scrllTabla;
	}
	private JLabel getLblReservas() {
		if (lblReservas == null) {
			lblReservas = new JLabel("Reservas Pendientes de Pago:");
			lblReservas.setBackground(Color.WHITE);
			lblReservas.setHorizontalAlignment(SwingConstants.CENTER);
			lblReservas.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblReservas;
	}
	private JTable getTableReservas() {
		if (tableReservas == null) {
			String[] nombreColumnas = {"Instalación", "Número de Socio", "Nombre", "DNI","Fecha", "Hora Inicio", "Hora Final"};
			modeloTabla = new ModeloTabla(nombreColumnas, 0);
			tableReservas = new JTable(modeloTabla);
			tableReservas.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent arg0) {
					reservaSeleccionada = reservasSinPagar.get(tableReservas.getSelectedRow());
					actualizarPnFacturacion();
					getPnFactura().setVisible(true);
				}
			});
			tableReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableReservas.getTableHeader().setReorderingAllowed(false);
		}
		return tableReservas;
	}
	
	private void actualizarPnFacturacion() {
		getTxfNombre().setText((String) tableReservas.getValueAt(tableReservas.getSelectedRow(), 2));
		getTxfDNI().setText((String) tableReservas.getValueAt(tableReservas.getSelectedRow(), 3));
		getTxfInstalacion().setText((String) tableReservas.getValueAt(tableReservas.getSelectedRow(), 0));
		try {
			getTxfPrecioHora().setText(String.valueOf(DataBase.getPrecioInstalacion(getTxfInstalacion().getText())));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getTxfTotal().setText(String.valueOf(getPrecioFinal()));
	}
	
	public void actualizarTabla() throws SQLException {
		getPnFactura().setVisible(false);
		borrarContenidoTabla();
		reservasSocios = vPrincipal.getPrograma().getReservasSocios();
		reservasSinPagar = new ArrayList<Reserva>();
		Object[] nuevaFila = new Object[7];
		for (Reserva reserva : reservasSocios) {
			if (compruebaReserva(reserva)){
				nuevaFila[0] = DataBase.getInstalacion(reserva.getCodInstalacion());
				nuevaFila[1] = reserva.getCodUsuario();
				nuevaFila[2] = DataBase.getNombre(reserva.getCodUsuario());
				nuevaFila[3] = DataBase.getDNI(reserva.getCodUsuario());
				nuevaFila[4] = reserva.getFechaReserva();
				nuevaFila[5] = reserva.getDesde();
				nuevaFila[6] = reserva.getHasta();
				modeloTabla.addRow(nuevaFila);
				reservasSinPagar.add(reserva);
			}
		}
	}
	
	/**
	 * Método que comprueba que una determinada reserva sea valida para mostrar en la tabla.
	 * Para ello, en primer lugar comprueba que no exista un pago previo a dicha reserva.
	 * En segundo lugar, que se hayan recogido las llaves en caso de que el dia de la reserva sea el actual
	 * En tercera lugar, que el dia de la reserva sea anterior o el dia de hoy.
	 * @param reserva
	 * @return
	 * @throws SQLException 
	 */
	private boolean compruebaReserva(Reserva reserva) throws SQLException {
		boolean con1 = DataBase.getPago(reserva.getCodInstalacion(), reserva.getCodUsuario(), reserva.getFechaReserva(), reserva.getDesde())==null; //No tiene pagos previos
		boolean con2 = reserva.getRecogidaLlaves()==null; //No se han entregado las llaves
		DateComparable today = new DateComparable();
		int con3 = today.compareDates(reserva.getYear(), reserva.getMonth(), reserva.getDay());
		if (con3==0) { //Si la fecha de la reserva es hoy
			return con1 && !con2;
		} else if (con3<0){ //Si la fecha de la reserva es anterior a hoy
			return con1;
		} else
			return false;
	}
	
	private void borrarContenidoTabla() {
		int nfilas = modeloTabla.getRowCount();
		for (int i=0; i<nfilas; i++) {
			modeloTabla.removeRow(0);
		}
	}
	
	private double calculaTotal() {
		String horaInicio = (String) tableReservas.getValueAt(tableReservas.getSelectedRow(), 5);
		String horaFinal = (String) tableReservas.getValueAt(tableReservas.getSelectedRow(), 6);
		int incio = Integer.valueOf(horaInicio.split(":")[0]);
		int fin = Integer.valueOf(horaFinal.split(":")[0]);
		return Double.valueOf(getTxfPrecioHora().getText()) * (fin - incio);
	}
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.add(getPnSocio());
			pnDatos.add(getPnReserva());
		}
		return pnDatos;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
		}
		return lblNombre;
	}
	private JTextField getTxfNombre() {
		if (txfNombre == null) {
			txfNombre = new JTextField();
			txfNombre.setEditable(false);
			txfNombre.setColumns(10);
		}
		return txfNombre;
	}
	private JPanel getPnSocio() {
		if (pnSocio == null) {
			pnSocio = new JPanel();
			pnSocio.setBorder(new TitledBorder(null, "Datos Personales:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnSocio.add(getLblNombre());
			pnSocio.add(getTxfNombre());
			pnSocio.add(getLblDni());
			pnSocio.add(getTxfDNI());
		}
		return pnSocio;
	}
	private JLabel getLblDni() {
		if (lblDni == null) {
			lblDni = new JLabel("DNI:");
		}
		return lblDni;
	}
	private JTextField getTxfDNI() {
		if (txfDNI == null) {
			txfDNI = new JTextField();
			txfDNI.setEditable(false);
			txfDNI.setColumns(10);
		}
		return txfDNI;
	}
	private JPanel getPnReserva() {
		if (pnReserva == null) {
			pnReserva = new JPanel();
			pnReserva.setBorder(new TitledBorder(null, "Datos de la reserva:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnReserva.add(getLblInstalacion());
			pnReserva.add(getTxfInstalacion());
			pnReserva.add(getLblPrecioPorHora());
			pnReserva.add(getTxfPrecioHora());
			pnReserva.add(getLblTotal());
			pnReserva.add(getTxfTotal());
		}
		return pnReserva;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalaci\u00F3n:");
		}
		return lblInstalacion;
	}
	private JTextField getTxfInstalacion() {
		if (txfInstalacion == null) {
			txfInstalacion = new JTextField();
			txfInstalacion.setEditable(false);
			txfInstalacion.setColumns(10);
		}
		return txfInstalacion;
	}
	private JLabel getLblPrecioPorHora() {
		if (lblPrecioPorHora == null) {
			lblPrecioPorHora = new JLabel("Precio por hora:");
		}
		return lblPrecioPorHora;
	}
	private JTextField getTxfPrecioHora() {
		if (txfPrecioHora == null) {
			txfPrecioHora = new JTextField();
			txfPrecioHora.setEditable(false);
			txfPrecioHora.setColumns(10);
		}
		return txfPrecioHora;
	}
	private JLabel getLblTotal() {
		if (lblTotal == null) {
			lblTotal = new JLabel("Total:");
			lblTotal.setDisplayedMnemonic('t');
			lblTotal.setLabelFor(getTxfTotal());
		}
		return lblTotal;
	}
	private JTextField getTxfTotal() {
		if (txfTotal == null) {
			txfTotal = new JTextField();
			txfTotal.setColumns(10);
		}
		return txfTotal;
	}
	private JPanel getPnFactura() {
		if (pnFactura == null) {
			pnFactura = new JPanel();
			pnFactura.setBorder(new TitledBorder(null, "Facturaci\u00F3n:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			pnFactura.setLayout(new BorderLayout(0, 0));
			pnFactura.add(getPnDatos());
			pnFactura.add(getPnBotones(), BorderLayout.SOUTH);
			pnFactura.setVisible(false);
		}
		return pnFactura;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.add(getBtnRegistrarPago());
			pnBotones.add(getBtnPagoAdeudado());
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
		}
		return pnBotones;
	}
	private JButton getBtnRegistrarPago() {
		if (btnRegistrarPago == null) {
			btnRegistrarPago = new JButton("Registrar Pago");
			btnRegistrarPago.setMnemonic('R');
			btnRegistrarPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					registrarPago(Double.valueOf(getTxfTotal().getText()));
					guardarFactura();
					modeloTabla.removeRow(tableReservas.getSelectedRow());
					pnFactura.setVisible(false);
					JOptionPane.showMessageDialog(null, "El pago se ha guardado correctamente.");
				}
			});
		}
		return btnRegistrarPago;
	}
	
	/**
	 * Método que guarda en la base de datos el pago generado.
	 */
	private void registrarPago(double pago) {
		try {
			DataBase.guardarPago(reservaSeleccionada.getCodInstalacion(), reservaSeleccionada.getCodUsuario(), 
					reservaSeleccionada.getFechaReserva(), reservaSeleccionada.getDesde(), getCodPago(), pago);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Guarda la factura en un fichero para el cliente.
	 */
	public void guardarFactura(){
		try {
			BufferedWriter fichero = new BufferedWriter(new FileWriter("FacturaEII-"+getCodPago()));
			fichero.write(generaFactura());
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "El archivo no se ha encontrado");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
	}
	
	/**
	 * Método que genera una factura para el cliente
	 * @return String la factura.
	 */
	private String generaFactura() {
		StringBuffer factura = new StringBuffer();
		factura.append("\t\tSPORT LINE - EII INSTALACIONES DEPORTTIVAS - " + reservaSeleccionada.getFechaReserva()+
				"\n----------------------------------------------------------------------------------\n"
				+ "Nombre: " + getTxfNombre().getText() + " " + "\tNIF: "+ getTxfDNI().getText() + 
				"\n----------------------------------------------------------------------------------\n\n");
		factura.append("Instalación: " + getTxfInstalacion().getText() + ".............Precio por hora: " + getTxfPrecioHora().getText() + "€\n");
		factura.append("Total horas:.............................................."+calculaTotal() + "€\n");
		factura.append("Tasa impositiva IVA (21%):................................"+getIVA()+"€\n");
		factura.append("TOTAL....................................................."+getTxfTotal().getText()+"€");
		return factura.toString();
	}
	
	/**
	 * Calcula el iva sobre el total del precio de las instalaciones
	 * @return double iva
	 */
	private double getIVA() {
		return calculaTotal()*0.21;
	}
	
	/**
	 * Calcula el precio final. Este se calcula con el total más el iva
	 * @return double el precio final
	 */
	private double getPrecioFinal() {
		double total = calculaTotal()+getIVA();
		return ((double)Math.round(total * 100d) / 100d);
	}
	
	/**
	 * Generador de códigos de pago que se utilizará para guardar la factura y como clave primaria en la base de datos
	 * Los 8 primeros numeros se corresponden con la fecha actual, los dos siguientes con la hora y los tres ultimos con el numero de codUsuario.
	 * @return String el codigo de pago
	 */
	private String getCodPago() {
		String[] fecha = reservaSeleccionada.getFechaReserva().split("/");
		String[] hora = reservaSeleccionada.getDesde().split(":");
		String[] usuario = reservaSeleccionada.getCodUsuario().split("-");
		return fecha[0]+fecha[1]+fecha[2]+hora[0]+usuario[0]+usuario[1];
	}
	private JButton getBtnPagoAdeudado() {
		if (btnPagoAdeudado == null) {
			btnPagoAdeudado = new JButton("Pago Adeudado");
			btnPagoAdeudado.setMnemonic('P');
			btnPagoAdeudado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						DataBase.guardarDeuda(reservaSeleccionada.getCodUsuario(), Double.valueOf(getTxfTotal().getText()));
					} catch (NumberFormatException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					registrarPago(Double.valueOf(getTxfTotal().getText())*(-1));
					modeloTabla.removeRow(tableReservas.getSelectedRow());
					pnFactura.setVisible(false);
					JOptionPane.showMessageDialog(null, "La deuda se ha guardado correctamente.");
				}
			});
		}
		return btnPagoAdeudado;
	}
}
