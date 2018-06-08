package igu.contable;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import dataBase.DataBase;
import date.DateComparable;
import igu.main.VentanaPrincipal;
import logica.objetos.Mensualidad;
import logica.objetos.Pago;
import logica.objetos.Reserva;
import modelos.ModeloTabla;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class PanelContable extends JPanel {
	
	//Constantes
	public final static int COMIENZO_MES = 20;
	public final static int FINAL_MES = 19;
	public final static String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

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
	private ArrayList<Reserva> reservasAdeudadas;
	private JPanel pnDatos;
	private JLabel lblNombre;
	private JTextField txfNombre;
	private JPanel pnSocio;
	private JLabel lblDni;
	private JTextField txfDNI;
	private JPanel pnCuota;
	private JPanel pnFinanzas;
	private JPanel pnBotones;
	private Reserva reservaSeleccionada;
	private JButton btnIncrementarCuota;
	private JLabel lblCuotaMensual;
	private JTextField txfCuota;
	private JLabel lblDeuda;
	private JTextField txfDeuda;
	private JLabel lblMensualidad;
	private JTextField txfMensualidad;
	private JPanel pnEncabezado;
	private JPanel pnMeses;
	private JLabel lblSeleccionaElMes;
	private JComboBox<String> cbxMeses;
	private JLabel lblSeleccionaElAo;
	private JSpinner spnYear;
	private SpinnerNumberModel spinnerModel;
	private JButton btnAplicar;

	/**
	 * Create the panel.
	 */
	public PanelContable(VentanaPrincipal vPrincipal) {
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
			pnInfo.add(getPnEncabezado(), BorderLayout.NORTH);
			pnInfo.add(getPnFinanzas(), BorderLayout.SOUTH);
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
			lblReservas = new JLabel("Reservas Adeudadas:");
			lblReservas.setBackground(Color.WHITE);
			lblReservas.setHorizontalAlignment(SwingConstants.CENTER);
			lblReservas.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblReservas;
	}
	private JTable getTableReservas() {
		if (tableReservas == null) {
			String[] nombreColumnas = {"Instalación", "Número de Socio", "Nombre", "DNI","Fecha", "Hora Inicio", "Hora Final", "Deuda Acumulada"};
			modeloTabla = new ModeloTabla(nombreColumnas, 0);
			tableReservas = new JTable(modeloTabla);
			tableReservas.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent arg0) {
					reservaSeleccionada = reservasAdeudadas.get(tableReservas.getSelectedRow());
					actualizarPnFacturacion();
					getPnFinanzas().setVisible(true);
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
		try {
			getTxfCuota().setText(String.valueOf(DataBase.getCuota(reservaSeleccionada.getCodUsuario())));
			getTxfDeuda().setText(String.valueOf(tableReservas.getValueAt(tableReservas.getSelectedRow(), 7)));
			Mensualidad mensualidad = DataBase.getMensualidad(reservaSeleccionada.getCodUsuario());
			getTxfMensualidad().setText((mensualidad!=null) ? String.valueOf(mensualidad.getmCantidad()) : getTxfCuota().getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actualizarTabla() throws SQLException { //actualizar01
		getPnFinanzas().setVisible(false);
		borrarContenidoTabla();
		reservasSocios = vPrincipal.getPrograma().getReservasSocios();
		reservasAdeudadas = new ArrayList<Reserva>();
		String mes = (String) getCbxMeses().getSelectedItem();
		int mesActual = obtieneNumeroMes(mes);
		int year = (int) spnYear.getModel().getValue();	
		
		Object[] nuevaFila = new Object[8];
		for (Reserva reserva : reservasSocios) {
			if (compruebaReserva(reserva, mesActual, year)){
				nuevaFila[0] = DataBase.getInstalacion(reserva.getCodInstalacion());
				nuevaFila[1] = reserva.getCodUsuario();
				nuevaFila[2] = DataBase.getNombre(reserva.getCodUsuario());
				nuevaFila[3] = DataBase.getDNI(reserva.getCodUsuario());
				nuevaFila[4] = reserva.getFechaReserva();
				nuevaFila[5] = reserva.getDesde();
				nuevaFila[6] = reserva.getHasta();
				nuevaFila[7] = (-1.0)*DataBase.getPago(reserva.getCodInstalacion(), reserva.getCodUsuario(), 
						reserva.getFechaReserva(), reserva.getDesde()).getCantidad();
				modeloTabla.addRow(nuevaFila);
				reservasAdeudadas.add(reserva);
			}
		}
	}
	
	/**
	 * Método que comprueba que una determinada reserva sea valida para mostrar en la tabla.
	 * Para ello, en primer lugar comprueba que exista un pago previo a dicha reserva.
	 * A continuación, comprueba que dicho pago tenga un valor negativo, es decir, esté adeudado
	 * En tercera lugar, que la fecha de la reserva esté entre el 19 del mes anterir, y el día 20 del mes actual
	 * @param reserva
	 * @return
	 * @throws SQLException 
	 */
	private boolean compruebaReserva(Reserva reserva, int mesActual, int year) throws SQLException {
		Pago pago = DataBase.getPago(reserva.getCodInstalacion(), reserva.getCodUsuario(), reserva.getFechaReserva(), reserva.getDesde()); //Tiene pagos previos
		if (pago!=null) { //Tiene pagos previos
			boolean con1 = pago.getCantidad()<0; //Son deudas
			DateComparable fechaReserva = new DateComparable(reserva.getDay(), reserva.getMonth(), reserva.getYear());
			boolean con2 = fechaReserva.isBetween(COMIENZO_MES, fechaReserva.getPreviousMonth(mesActual-1), FINAL_MES, fechaReserva.getPreviousMonth(mesActual)); //estan en el periodo de cobro actual
			boolean con3 = fechaReserva.getYear()==year;
			return con1 && con2 && con3;
		}
		return false;
	}

	/**
	 * Método que te devuelve el numero de mes de un determinado mes pasado como string
	 * @param mes
	 * @return
	 */
	private int obtieneNumeroMes(String mes) {
		for (int i=0; i<MESES.length; i++) {
			if (MESES[i].equals(mes))
				return i+1;
		}
		return 0;
	}
	
	private void borrarContenidoTabla() {
		int nfilas = modeloTabla.getRowCount();
		for (int i=0; i<nfilas; i++) {
			modeloTabla.removeRow(0);
		}
	}
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.add(getPnSocio());
			pnDatos.add(getPnCuota());
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
	private JPanel getPnCuota() {
		if (pnCuota == null) {
			pnCuota = new JPanel();
			pnCuota.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos de la Mensualidad:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnCuota.add(getLblCuotaMensual());
			pnCuota.add(getTxfCuota());
			pnCuota.add(getLblMensualidad());
			pnCuota.add(getTxfMensualidad());
			pnCuota.add(getLblDeuda());
			pnCuota.add(getTxfDeuda());
		}
		return pnCuota;
	}
	private JPanel getPnFinanzas() {
		if (pnFinanzas == null) {
			pnFinanzas = new JPanel();
			pnFinanzas.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Finanzas:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnFinanzas.setLayout(new BorderLayout(0, 0));
			pnFinanzas.add(getPnDatos());
			pnFinanzas.add(getPnBotones(), BorderLayout.SOUTH);
			pnFinanzas.setVisible(false);
		}
		return pnFinanzas;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnBotones.add(getBtnIncrementarCuota());
		}
		return pnBotones;
	}
	private JButton getBtnIncrementarCuota() {
		if (btnIncrementarCuota == null) {
			btnIncrementarCuota = new JButton("Incrementar Cuota");
			btnIncrementarCuota.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						DataBase.incrementarMensualidad(reservaSeleccionada.getCodUsuario(), Double.valueOf(getTxfDeuda().getText()), 
								obtieneNumeroMes((String) getCbxMeses().getSelectedItem()), (int) spnYear.getModel().getValue());
						DataBase.setPagoCobrado(reservaSeleccionada.getCodInstalacion(), 
								reservaSeleccionada.getCodUsuario(), reservaSeleccionada.getFechaReserva(), reservaSeleccionada.getDesde());
						DataBase.guardarDeuda(reservaSeleccionada.getCodUsuario(), Double.valueOf(getTxfDeuda().getText())*(-1.0));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					modeloTabla.removeRow(tableReservas.getSelectedRow());
					pnFinanzas.setVisible(false);
					JOptionPane.showMessageDialog(null, "La deuda se ha sumado a la siguiente mensualidad.");
				}
			});
			btnIncrementarCuota.setMnemonic('I');
		}
		return btnIncrementarCuota;
	}
	private JLabel getLblCuotaMensual() {
		if (lblCuotaMensual == null) {
			lblCuotaMensual = new JLabel("Cuota Mensual (\u20AC):");
		}
		return lblCuotaMensual;
	}
	private JTextField getTxfCuota() {
		if (txfCuota == null) {
			txfCuota = new JTextField();
			txfCuota.setEditable(false);
			txfCuota.setColumns(10);
		}
		return txfCuota;
	}
	private JLabel getLblDeuda() {
		if (lblDeuda == null) {
			lblDeuda = new JLabel("Deuda (\u20AC):");
		}
		return lblDeuda;
	}
	private JTextField getTxfDeuda() {
		if (txfDeuda == null) {
			txfDeuda = new JTextField();
			txfDeuda.setEditable(false);
			txfDeuda.setColumns(10);
		}
		return txfDeuda;
	}
	private JLabel getLblMensualidad() {
		if (lblMensualidad == null) {
			lblMensualidad = new JLabel("Mensualidad (\u20AC):");
			lblMensualidad.setLabelFor(getTxfMensualidad());
		}
		return lblMensualidad;
	}
	private JTextField getTxfMensualidad() {
		if (txfMensualidad == null) {
			txfMensualidad = new JTextField();
			txfMensualidad.setEditable(false);
			txfMensualidad.setColumns(10);
		}
		return txfMensualidad;
	}
	private JPanel getPnEncabezado() {
		if (pnEncabezado == null) {
			pnEncabezado = new JPanel();
			pnEncabezado.setLayout(new BorderLayout(0, 0));
			pnEncabezado.add(getLblReservas(), BorderLayout.WEST);
			pnEncabezado.add(getPnMeses(), BorderLayout.EAST);
		}
		return pnEncabezado;
	}
	private JPanel getPnMeses() {
		if (pnMeses == null) {
			pnMeses = new JPanel();
			pnMeses.add(getLblSeleccionaElMes());
			pnMeses.add(getCbxMeses());
			pnMeses.add(getLblSeleccionaElAo());
			pnMeses.add(getSpnYear());
			pnMeses.add(getBtnAplicar());
		}
		return pnMeses;
	}
	private JLabel getLblSeleccionaElMes() {
		if (lblSeleccionaElMes == null) {
			lblSeleccionaElMes = new JLabel("Selecciona el Mes:");
		}
		return lblSeleccionaElMes;
	}
	private JComboBox<String> getCbxMeses() {
		if (cbxMeses == null) {
			cbxMeses = new JComboBox<String>();
			cbxMeses.setModel(new DefaultComboBoxModel<String>(MESES));
			cbxMeses.setSelectedIndex(eligeMesInicio()-1);
			cbxMeses.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (compruebaMes(obtieneNumeroMes((String) cbxMeses.getSelectedItem()))) {
						getBtnAplicar().setEnabled(true);
					} else {
						getBtnAplicar().setEnabled(false);
					}
				}
			});
		}
		return cbxMeses;
	}
	
	private int eligeMesInicio() {
		int actualMonth = DateComparable.getActualMonth();
		if (compruebaMes(actualMonth+1)) {
			return actualMonth+1;
		} else if (compruebaMes(actualMonth))
			return actualMonth;
		else return actualMonth-1;			
	}
	
	private boolean compruebaMes(int mes) {
		int actualMonth = DateComparable.getActualMonth();
		int actualDay = DateComparable.getActualDay();
		if (mes==actualMonth+1) {
			return (actualDay>FINAL_MES);
		} else if (mes<=actualMonth)
			return true;
		else return false;
	}
	
	private JLabel getLblSeleccionaElAo() {
		if (lblSeleccionaElAo == null) {
			lblSeleccionaElAo = new JLabel("Selecciona el A\u00F1o:");
		}
		return lblSeleccionaElAo;
	}
	private JSpinner getSpnYear() {
		if (spnYear == null) {
			spnYear = new JSpinner();
			spinnerModel = new SpinnerNumberModel(DateComparable.getActualYear(), 2015, DateComparable.getActualYear(), 1);
			spnYear.setModel(spinnerModel);
		}
		return spnYear;
	}

	private JButton getBtnAplicar() {
		if (btnAplicar == null) {
			btnAplicar = new JButton("Aplicar");
			btnAplicar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						actualizarTabla();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return btnAplicar;
	}
	
}
