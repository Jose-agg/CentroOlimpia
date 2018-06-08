package igu.socio.reservas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
import logica.objetos.Instalacion;
import logica.programa.Programa;
import modelos.ModeloCeldas;
import modelos.ModeloTabla;


import java.awt.GridLayout;

import javax.swing.JTextField;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class PanelMostrarReservasSocios extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnMenu;
	private JLabel lblInstalacin;
	private JComboBox<Instalacion> cbxInstalaciones;
	private JPanel pnSelecInsta;
	private JPanel pnFecha;
	private JLabel lblFecha;
	private static JDateChooser dateChooser;
	private JButton btnVer;
	private JPanel pnInfo;
	private JScrollPane scrllTabla;
	private JTable tableDisponibilidad;
	private static Programa programa;
	private static String codU;
	private static String codI;

	public final static String[] HORAS = {"00:00","01:00","02:00","03:00","04:00","05:00","06:00",
			  "07:00","08:00","09:00","10:00","11:00","12:00","13:00",
			  "14:00","15:00","16:00","17:00","18:00","19:00","20:00",
			  "21:00","22:00","23:00"};
	public final static String[] HORAS_TERMINAR = {"00:00","01:00","02:00","03:00","04:00","05:00","06:00",
		  "07:00","08:00","09:00","10:00","11:00","12:00","13:00",
		  "14:00","15:00","16:00","17:00","18:00","19:00","20:00",
		  "21:00","22:00","23:00","24:00"};
	private JPanel pnReserva;
	private JLabel lblNsocio;
	private JTextField textFieldSocio;
	private JButton btnReservar;
	private JButton btnCancelarReserva;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public PanelMostrarReservasSocios(Programa programa) throws ParseException, SQLException {
		this.programa = programa;
		setBounds(100, 100, 1172, 702);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		this.add(getPnMenu(), BorderLayout.NORTH);
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
		}
		return lblInstalacin;
	}
	private JComboBox<Instalacion> getCbxInstalaciones() throws SQLException {
		if (cbxInstalaciones == null) {
			cbxInstalaciones = new JComboBox<Instalacion>();
			cbxInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dateChooser.setEnabled(true);
				}
			});
			DefaultComboBoxModel<Instalacion> modelo = new DefaultComboBoxModel<Instalacion>();
			for (Instalacion instalacion : DataBase.cargaInstalaciones()) {
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
			pnSelecInsta.setLayout(new GridLayout(0, 4, 0, 0));
			pnSelecInsta.add(getLblNsocio());
			pnSelecInsta.add(getTextFieldSocio());
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
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Selecciona una Fecha:");
		}
		return lblFecha;
	}
	private JDateChooser getDateChooser() throws ParseException {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setPreferredSize(new Dimension(125, 20));
			dateChooser.setVerifyInputWhenFocusTarget(false);
			dateChooser.setDateFormatString("dd/MM/yyyy");
			dateChooser.setEnabled(false);
			//SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
			//String strFecha ="09/10/2017";
			//Date fecha = null;
			//try{
			//fecha = formatoDelTexto.parse(strFecha);
			//}catch (ParseException ex) {
			//	System.err.println("Error en la fecha");
			//}

			//dateChooser.setMinSelectableDate(fecha);
			dateChooser.setMinSelectableDate(new Date());
			dateChooser.getCalendarButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnVer.setEnabled(true);
					btnReservar.setEnabled(true);
					btnCancelarReserva.setEnabled(true);
					Instalacion i = (Instalacion) cbxInstalaciones.getSelectedItem();
					setCodI(i.getCodInstalacion());
					setCodU(textFieldSocio.getText());
				}
			});
			dateChooser.setBounds(478, 30, 106, 20);
			
			
		}
		return dateChooser;
	}
	private JButton getBtnVer() {
		if (btnVer == null) {
			btnVer = new JButton("Comprobar Disponibilidad");
			btnVer.setEnabled(false);
			btnVer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textFieldSocio.getText().startsWith("S")){
						 if( dateChooser.getDate()==null){
								JOptionPane.showMessageDialog(pnInfo, "Seleccione una fecha.");
						 }else{
						setCodU(textFieldSocio.getText());
						Instalacion i = (Instalacion) cbxInstalaciones.getSelectedItem();
						setCodI(i.getCodInstalacion());
						String f = obtenerFecha();
						mostrarTabla(HORAS, i, f,String.valueOf(textFieldSocio.getText()));
						//textFieldSocio.setEditable(false);
						//textFieldSocio.setEnabled(false);
						 }
					}else{
						JOptionPane.showMessageDialog(pnInfo, "El número de socio es incorrecto.");
					}
					
				}
			});
		}
		return btnVer;
	}
	
	private static void setCodU(String codU) {
		PanelMostrarReservasSocios.codU = codU;
	}

	private static void setCodI(String codI) {
		PanelMostrarReservasSocios.codI = codI;
	}

	public static String obtenerFecha(){
	    Date today = dateChooser.getDate();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String folderName = formatter.format(today);
	    return folderName;
	}

	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			pnInfo.setLayout(new BorderLayout(0, 0));
			pnInfo.add(getScrllTabla(), BorderLayout.CENTER);
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
			tableDisponibilidad = new JTable();
			tableDisponibilidad.getTableHeader().setReorderingAllowed(false);
		}
		return tableDisponibilidad;
	}
	
	private void mostrarTabla(String[] h, Instalacion ins, String fecha,String codU){
		ModeloTabla model = new ModeloTabla(new String [] {"Hora","Instalación","Estado"},0);
		ModeloCeldas rr = new ModeloCeldas(2);
		String[] filas = new String[6];
		for (int i = 0; i < h.length; i++) {
			filas[0]= h[i];
			filas[1]= ins.getInombre();
			try {
				filas[2]= programa.buscarReservasSocios(codU, ins.getCodInstalacion(),fecha, h[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addRow(filas);
			
		}
		tableDisponibilidad.setModel(model);
		tableDisponibilidad.setDefaultRenderer(Object.class, rr);
	}
	private JPanel getPnReserva() {
		if (pnReserva == null) {
			pnReserva = new JPanel();
			GroupLayout gl_pnReserva = new GroupLayout(pnReserva);
			gl_pnReserva.setHorizontalGroup(
				gl_pnReserva.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnReserva.createSequentialGroup()
						.addContainerGap(62, Short.MAX_VALUE)
						.addGroup(gl_pnReserva.createParallelGroup(Alignment.LEADING, false)
							.addComponent(getBtnCancelarReserva(), Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(getBtnReservar(), Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)))
			);
			gl_pnReserva.setVerticalGroup(
				gl_pnReserva.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnReserva.createSequentialGroup()
						.addGap(12)
						.addComponent(getBtnReservar())
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(getBtnCancelarReserva())
						.addContainerGap(593, Short.MAX_VALUE))
			);
			pnReserva.setLayout(gl_pnReserva);
		}
		return pnReserva;
	}

	private JLabel getLblNsocio() {
		if (lblNsocio == null) {
			lblNsocio = new JLabel("n\u00BASocio");
		}
		return lblNsocio;
	}
	
	public static Programa getPrograma() {
		return programa;
	}

	public static String getCodU() {
		return codU;
	}


	public static String getCodI() {
		return codI;
	}

	
	private JTextField getTextFieldSocio() {
		if (textFieldSocio == null) {
			textFieldSocio = new JTextField();
			textFieldSocio.setColumns(10);
		}
		return textFieldSocio;
	}
	
	
	
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Solicitar Reserva");
			btnReservar.setEnabled(false);
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(textFieldSocio.getText().startsWith("S")){
						 if( dateChooser.getDate()==null){
								JOptionPane.showMessageDialog(pnInfo, "Seleccione una fecha.");
						 }else{
						setCodU(textFieldSocio.getText());
						Instalacion i = (Instalacion) cbxInstalaciones.getSelectedItem();
						setCodI(i.getCodInstalacion());
						//textFieldSocio.setEditable(false);
						//textFieldSocio.setEnabled(false);
						 }
					}else{
						JOptionPane.showMessageDialog(pnInfo, "El número de socio es incorrecto.");
					}
					
				
					 if( dateChooser.getDate()==null){
							JOptionPane.showMessageDialog(pnInfo, "Seleccione una fecha.");
						}else{
					Date d = sumarRestarDiasFecha(new Date(), 15);
					if(dateChooser.getDate().after(d)){
						JOptionPane.showMessageDialog(pnInfo, "Sólo se puede reservar 15 días antes.");
					}else{
						crearVentanaReservaSocio();
					}
				
						}
				}
			});
		}
		return btnReservar;
	}
	
	private void crearVentanaReservaSocio() {
		VentanaReservaSocio vr = new VentanaReservaSocio();
		vr.setLocationRelativeTo(this);
		vr.setModal(true);
		vr.setVisible(true);
	}
	
	 public Date sumarRestarDiasFecha(Date fecha, int dias){
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(fecha); // Configuramos la fecha que se recibe
		   calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
		    return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		
		
		  }
	 private void crearVentanaCancelarReservaSocio() throws SQLException {
			VentanaCancelarReservaSocio vr = new VentanaCancelarReservaSocio();
			vr.setLocationRelativeTo(this);
			vr.setModal(true);
			vr.setVisible(true);
		}
	 
	 
	 public static String nombreInstalacionSeleccionada() throws SQLException{
		 String ins = "";
		 for (Instalacion instalacion : DataBase.getInstalaciones()) {	
			 System.out.println(codI);
			if(instalacion.getCodInstalacion().equals(codI)){
				ins = instalacion.getInombre();
			}
		}
		
		 return ins;
	 }
	
	 private String formatearFechaActual() {
			Date today = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String folderName = formatter.format(today);
			return folderName;
		}
	 
	private JButton getBtnCancelarReserva() {
		if (btnCancelarReserva == null) {
			btnCancelarReserva = new JButton("Cancelar Reserva");
			btnCancelarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						if(obtenerFecha().equals(formatearFechaActual())){
							JOptionPane.showMessageDialog(pnInfo, "Sólo se puede cancelar hasta el día antes de la reserva.");
						}else if(!getPrograma().hayReserva(codU, codI, obtenerFecha())){
							JOptionPane.showMessageDialog(pnInfo, "No hay reservas para el día seleccionado.");
						}else{
							crearVentanaCancelarReservaSocio();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnCancelarReserva.setEnabled(false);
		}
		return btnCancelarReserva;
	}
}
