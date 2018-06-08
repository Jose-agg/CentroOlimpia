package igu.admin.reservas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import dataBase.DataBase;
import igu.main.VentanaPrincipal;
import logica.objetos.Instalacion;
import logica.programa.Programa;
import modelos.ModeloCeldas;
import modelos.ModeloTabla;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PanelMostrarReservasAdministrador extends JPanel {
	
	//Constantes
	
	public final static String[] HORAS = {"00:00","01:00","02:00","03:00","04:00","05:00","06:00",
			  "07:00","08:00","09:00","10:00","11:00","12:00","13:00",
			  "14:00","15:00","16:00","17:00","18:00","19:00","20:00",
			  "21:00","22:00","23:00"};
	public final static String[] HORAS_TERMINAR = {"01:00","02:00","03:00","04:00","05:00","06:00",
			  "07:00","08:00","09:00","10:00","11:00","12:00","13:00",
			  "14:00","15:00","16:00","17:00","18:00","19:00","20:00",
			  "21:00","22:00","23:00","24:00"};
	
	//Atributos
	private static final long serialVersionUID = 1L;
	private JPanel pnMenu;
	private JLabel lblInstalacin;
	private JComboBox<Instalacion> cbxInstalaciones;
	private JPanel pnSelecInsta;
	private JPanel pnFecha;
	private JLabel lblFecha;
	private JDateChooser dateChooser;
	private JButton btnVer;
	private JPanel pnInfo;
	private JScrollPane scrllTabla;
	private JTable tableDisponibilidad;
	private VentanaPrincipal vPrincipal;
	private JButton btnReservar;
	private JPanel pnRegistrar;
	public final static String ADMINISTRADOR = "A-001";
	private JPanel pnReserva;
	private JButton btnReservaSocios;
	private ModeloTabla modeloTabla;
	private JButton btnRegistrarPago;
	private JLabel lblNewLabel;
	private JButton btnUtilizarReserva;
	private JButton btnCancelarReserva;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public PanelMostrarReservasAdministrador(VentanaPrincipal vPrincipal) throws ParseException, SQLException {
		this.vPrincipal = vPrincipal;
		setBounds(100, 100, 925, 573);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		add(getPnMenu(), BorderLayout.NORTH);
		this.add(getPnInfo(), BorderLayout.CENTER);
	}

	private JPanel getPnMenu() throws ParseException, SQLException {
		if (pnMenu == null) {
			pnMenu = new JPanel();
			pnMenu.setLayout(new BorderLayout(0, 0));
			pnMenu.add(getPnSelecInsta(), BorderLayout.WEST);
			pnMenu.add(getPnFecha(), BorderLayout.CENTER);
			pnMenu.add(getBtnVer(), BorderLayout.EAST);
		}
		return pnMenu;
	}
	private JLabel getLblInstalacin() {
		if (lblInstalacin == null) {
			lblInstalacin = new JLabel("Instalaci\u00F3n:");
			try {
				lblInstalacin.setLabelFor(getCbxInstalaciones());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lblInstalacin.setDisplayedMnemonic('I');
		}
		return lblInstalacin;
	}
	JComboBox<Instalacion> getCbxInstalaciones() throws SQLException {
		if (cbxInstalaciones == null) {
			cbxInstalaciones = new JComboBox<Instalacion>();
			cbxInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					btnUtilizarReserva.setEnabled(true);
				}
			});
			DefaultComboBoxModel<Instalacion> modelo = new DefaultComboBoxModel<Instalacion>();
			for (Instalacion instalacion : DataBase.getInstalaciones()) {
				modelo.addElement(instalacion);
				
			}
			cbxInstalaciones.setModel(modelo);
			cbxInstalaciones.setPreferredSize(new Dimension(125, 20));
		}
		return cbxInstalaciones;
	}
	private JPanel getPnSelecInsta() throws SQLException {
		if (pnSelecInsta == null) {
			pnSelecInsta = new JPanel();
			pnSelecInsta.add(getLblInstalacin());
			pnSelecInsta.add(getCbxInstalaciones());
		}
		return pnSelecInsta;
	}
	private JPanel getPnFecha() throws ParseException {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.add(getLblFecha());
			pnFecha.add(getDateChooser());
		}
		return pnFecha;
	}
	private JLabel getLblFecha() throws ParseException {
		if (lblFecha == null) {
			lblFecha = new JLabel("Selecciona una Fecha:");
			lblFecha.setDisplayedMnemonic('e');
			lblFecha.setLabelFor(getDateChooser());
		}
		return lblFecha;
	}
	private JDateChooser getDateChooser() throws ParseException {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setPreferredSize(new Dimension(125, 20));
			dateChooser.setVerifyInputWhenFocusTarget(false);
			
			dateChooser.setDateFormatString("dd/MM/yyyy");
			dateChooser.setMinSelectableDate(new Date());
			
			dateChooser.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnVer.setEnabled(true);
					getBtnCancelarReserva().setEnabled(true);
				}
			});
			dateChooser.setBounds(478, 30, 106, 20);
			
			
		}
		return dateChooser;
	}
	
	private JButton getBtnVer() {
		if (btnVer == null) {
			btnVer = new JButton("Comprobar Disponibilidad");
			btnVer.setMnemonic('C');
			btnVer.setEnabled(false);
			btnVer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Instalacion i = (Instalacion) cbxInstalaciones.getSelectedItem();
					getBtnReservar().setEnabled(true);
					getBtnReservaSocios().setEnabled(true);
					String f = obtenerFecha();
					mostrarTablaAdministrador(HORAS, i, f);
				}
			});
		}
		return btnVer;
	}
	
	protected String obtenerFecha(){
	    Date hoy = dateChooser.getDate();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String fecha = formatter.format(hoy);
	    return fecha;
	}

	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			pnInfo.setLayout(new BorderLayout(0, 0));
			pnInfo.add(getScrllTabla(), BorderLayout.CENTER);
			pnInfo.add(getLblNewLabel(), BorderLayout.SOUTH);
			pnInfo.add(getPnReserva(), BorderLayout.EAST);
		}
		return pnInfo;
	}
	private JScrollPane getScrllTabla() {
		if (scrllTabla == null) {
			scrllTabla = new JScrollPane();
			scrllTabla.setViewportView(getTableDisponibilidad());
		}
		return scrllTabla;
	}
	private JTable getTableDisponibilidad() {
		if (tableDisponibilidad == null) {
			modeloTabla = new ModeloTabla(new String [] {"Hora Inicio", "Hora Fin","Instalación","Estado"},0);
			tableDisponibilidad = new JTable(modeloTabla);
			tableDisponibilidad.getTableHeader().setReorderingAllowed(false);
		}
		return tableDisponibilidad;
	}
	
	protected void mostrarTablaAdministrador(String[] h, Instalacion ins, String fecha){
		borrarContenidoTabla();
		ModeloCeldas rr = new ModeloCeldas(3);
		String[] filas = new String[4];
		for (int i = 0; i < h.length; i++) {
			filas[0]= h[i];
			filas[1]= HORAS_TERMINAR[i];
			filas[2]= ins.getInombre();
			try {
				filas[3]= vPrincipal.getPrograma().buscarReservas(ADMINISTRADOR, ins.getCodInstalacion(),fecha, h[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			modeloTabla.addRow(filas);
			
		}
		tableDisponibilidad.setDefaultRenderer(Object.class, rr);
	}
	
	private void borrarContenidoTabla() {
		int nfilas = modeloTabla.getRowCount();
		for (int i=0; i<nfilas; i++) {
			modeloTabla.removeRow(0);
		}
	}
	private JPanel getPnReserva() {
		if (pnReserva == null) {
			pnReserva = new JPanel();
			pnReserva.setLayout(new GridLayout(0, 1, 0, 0));
			pnReserva.add(getBtnReservar());
			pnReserva.add(getBtnReservaSocios());
			pnReserva.add(getBtnRegistrarPago());
			pnReserva.add(getBtnUtilizarReserva());
			pnReserva.add(getBtnCancelarReserva());
		}
		return pnReserva;
	}
	
	private JButton getBtnReservar() {
		if (btnReservar  == null) {
			btnReservar = new JButton("<html><p ALIGN=center>R<u>e</u>serva <br>Administración<br></p></html>");
			btnReservar.setMnemonic('e');
			btnReservar.setEnabled(false);
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarReserva();
				}
			});
		}
		return btnReservar;
	}
	
	private void mostrarReserva() {
		VentanaReservaAdmin vr = new VentanaReservaAdmin(this);
		vr.setModal(true);
		vr.setVisible(true);
	}
	
	public Programa getPrograma(){
		return vPrincipal.getPrograma();
	}

	private JButton getBtnReservaSocios() {
		if (btnReservaSocios == null) {
			btnReservaSocios = new JButton("Reserva Socios");
			btnReservaSocios.setMnemonic('S');
			btnReservaSocios.setEnabled(false);
			btnReservaSocios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Date d = sumarRestarDiasFecha(new Date(), 7);
					if(dateChooser.getDate().after(d)){
						JOptionPane.showMessageDialog(pnInfo, "Sólo se puede reservar 7 días antes.");
					}else{
						String date = DateFormat.getDateInstance().format(dateChooser.getDate());
						mostrarReservaSocios();
					}
				}
			});
		}
		return btnReservaSocios;
	}
	public Date sumarRestarDiasFecha(Date fecha, int dias){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos


	}
	private void mostrarReservaSocios(){
		VentanaReservaSociosAdmin vr = new VentanaReservaSociosAdmin(this);
		vr.setLocationRelativeTo(this);
		vr.setModal(true);
		vr.setVisible(true);
	}
	public ModeloTabla getModel(){
		return modeloTabla;
	}
	private JButton getBtnRegistrarPago() {
		if (btnRegistrarPago == null) {
			btnRegistrarPago = new JButton("<html><p ALIGN=center>Re<u>g</u>istrar Pagos <br>Socios<br></p></html>");
			
			btnRegistrarPago.setMnemonic('g');
			btnRegistrarPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						vPrincipal.getPnCentral().removeAll();
						vPrincipal.getPnCentral().add(getPnRegistrarPago(), BorderLayout.CENTER);
						((PanelRegistrarPago) getPnRegistrarPago()).actualizarTabla();
						vPrincipal.getPnCentral().updateUI();
					} catch (ParseException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			});
		}
		return btnRegistrarPago;
	}
	
	private JPanel getPnRegistrarPago() throws ParseException, SQLException {
		if (pnRegistrar==null) {
			this.pnRegistrar = new PanelRegistrarPago(this.vPrincipal);
		}
		return pnRegistrar;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Disponibilidad de Instalaciones");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabel;
	}
	private JButton getBtnUtilizarReserva() {
		if (btnUtilizarReserva == null) {
			btnUtilizarReserva = new JButton("Utilizar Reserva");
			btnUtilizarReserva.setMnemonic('U');
			btnUtilizarReserva.setEnabled(false);
			btnUtilizarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						mostrarUtilizarReserva();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return btnUtilizarReserva;
	}
	
	private void mostrarUtilizarReserva() throws SQLException{
		VentanaUtilizarReserva vur = new VentanaUtilizarReserva(this);
		vur.setLocationRelativeTo(this);
		vur.setModal(true);
		vur.setVisible(true);
	}
	
	public Instalacion getInstalacionSeleccionada(){
		return (Instalacion) cbxInstalaciones.getSelectedItem();
	}
	private JButton getBtnCancelarReserva() {
		if (btnCancelarReserva == null) {
			btnCancelarReserva = new JButton("<html><p ALIGN=center>Cancelar Reserva</p></html>");
			btnCancelarReserva.setEnabled(false);
			btnCancelarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarCancelarReservaSocios();
				}
			});
		}
		return btnCancelarReserva;
	}
	private void mostrarCancelarReservaSocios() {
		VentanaCancelarReservaAdmin vcrsa = new VentanaCancelarReservaAdmin(this);
		vcrsa.setLocationRelativeTo(this);
		vcrsa.setModal(true);
		vcrsa.setVisible(true);
	}
}
