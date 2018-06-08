package igu.admin.actividades;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.toedter.calendar.JDateChooser;

import dataBase.DataBase;
import igu.admin.reservas.VentanaReservaSociosAdmin;
import igu.main.VentanaPrincipal;
import logica.objetos.Actividad;
import logica.objetos.Apuntados;
import logica.objetos.EventoActividad;
import logica.objetos.Instalacion;
import modelos.FormatoTabla;
import modelos.FormatoTablaActividades;
import modelos.ModeloCeldas;
import modelos.ModeloTabla;

import java.awt.GridBagLayout;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.GridLayout;



//import net.miginfocom.swing.MigLayout;

public class PanelMostrarActividadesAdministrador extends JPanel {

	private JPanel pnNombre;
	private JLabel lblDisponibilidadActividades;
	private JPanel pnCentro;
	private JPanel pnTabla;
	private JScrollPane scrollPane;
	private JTable table;
	private ModeloTabla modeloTabla;
	private VentanaPrincipal vp;
	private JDateChooser dateChooser;
	public final static String[] HORAS = {"00:00","01:00","02:00","03:00","04:00","05:00","06:00",
			  "07:00","08:00","09:00","10:00","11:00","12:00","13:00",
			  "14:00","15:00","16:00","17:00","18:00","19:00","20:00",
			  "21:00","22:00","23:00"};
	public final static String[] HORAS_TERMINAR = {"01:00","02:00","03:00","04:00","05:00","06:00",
		  "07:00","08:00","09:00","10:00","11:00","12:00","13:00",
		  "14:00","15:00","16:00","17:00","18:00","19:00","20:00",
		  "21:00","22:00","23:00","00:00"};
	public final static String[] HORAS_TABLA = {"00:00 - 01:00","01:00 - 02:00","02:00 - 03:00","03:00 - 04:00","04:00 - 05:00","05:00 - 06:00","06:00 - 07:00",
		  "07:00 - 08:00","08:00 - 09:00","09:00 - 10:00","10:00 - 11:00","11:00 - 12:00","12:00 - 13:00","13:00 - 14:00",
		  "14:00 - 15:00","15:00 - 16:00","16:00 - 17:00","17:00 - 18:00","18:00 - 19:00","19:00 - 20:00","20:00 - 21:00",
		  "21:00 - 22:00","22:00 - 23:00","23:00 - 24:00"};
	public static final String RESERVA = "Reserva";
	public static final String EVENTO = "Evento";
	
	private JPanel pnMenu;
	private JPanel pnFecha;
	private JLabel lblFecha;
	private JPanel pnSelecInsta;
	private JLabel lblActividad;
	private JComboBox<Actividad> cbxActividad;
	private JButton btnVer;
	private JPanel pnIntermedio;
	private JPanel pnInformacion;
	private EventoActividad[][] eventosActiviadad;
	private JPanel pnBotones;
	private JButton btnApuntarActividad;
	private JButton btnCancelarActividad;
	private String horaSeleccionada;
	private String diaSeleccionado;
	private JButton btnCrearActividad;
	private JButton btnCancelarActividadPuntual;
	private JButton btnBorrarActividad;


	// Metodos Jose para borrar
	private Actividad actividadParaBorrarP;
	private String fechaParaBorrarP;

	/**
	 * Create the panel.
	 */
	public PanelMostrarActividadesAdministrador(VentanaPrincipal vp) {
		this.vp=vp;
		setLayout(new BorderLayout(0, 0));
		add(getPnNombre(), BorderLayout.SOUTH);
		pnNombre.add(getLblDisponibilidadActividades());
		add(getPnCentro(), BorderLayout.CENTER);
		add(getPnMenu(), BorderLayout.NORTH);
		add(getPnBotones(), BorderLayout.EAST);

	}

	private JPanel getPnTabla() {
		if(pnTabla==null) {
			pnTabla = new JPanel();
			pnTabla.setLayout(new BorderLayout(0, 0));
			pnTabla.add(getScrollPane());
			pnTabla.add(getPnInformacion(), BorderLayout.SOUTH);
		}
		return pnTabla;
	}

	private JPanel getPnCentro() {
		if (pnCentro == null) {
			pnCentro = new JPanel();
			pnCentro.setLayout(new BorderLayout(0, 0));
			pnCentro.add(getPnIntermedio(), BorderLayout.CENTER);
		}
		return pnCentro;
	}

	private JLabel getLblDisponibilidadActividades() {
		if (lblDisponibilidadActividades == null) {
			lblDisponibilidadActividades = new JLabel("Disponibilidad Actividades");
			lblDisponibilidadActividades.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblDisponibilidadActividades;
	}

	private JPanel getPnNombre() {
		if (pnNombre == null) {
			pnNombre = new JPanel();
		}
		return pnNombre;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable(modeloTabla);
			//table.setRowHeight(20);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					pnInformacion.removeAll();
					pnInformacion.updateUI();
					//					pnInformacion.add(getPnDatos(), BorderLayout.CENTER);
					getPnInformacion().setVisible(true);
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					// Para habilitar o deshabilitar el boton borrar una actividad periodica solo cuando se selecione una celda no vacia
					int fila = table.rowAtPoint(arg0.getPoint());
					int columna = table.columnAtPoint(arg0.getPoint());
					rellenarParametros(fila, columna);
					boolean b = table.getValueAt(fila, columna).equals("");
					modificarBotonBorrarPeriodica(!b);
				}
			});
			table.getTableHeader().setReorderingAllowed(false);
		}
		return table;
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
					getBtnVer().setEnabled(true);
				}
			});
			dateChooser.setBounds(478, 30, 106, 20);
			
			
		}
		return dateChooser;
	}
	private void mostrarTabla(String[] h, Actividad a, String fecha) throws SQLException{
		String[] columns=new String [] {"Horas",fecha,sumarDiasFecha( dateChooser.getDate(), 1),sumarDiasFecha( dateChooser.getDate(), 2),sumarDiasFecha( dateChooser.getDate(), 3),sumarDiasFecha( dateChooser.getDate(), 4),sumarDiasFecha( dateChooser.getDate(), 5)};
		modeloTabla = new ModeloTabla(columns,0);
		//ModeloCeldas rr = new ModeloCeldas(2);
		FormatoTablaActividades t = new FormatoTablaActividades(this);
		String[] filas = new String[7];
		getVentanaPrincipal().getPrograma().actualizarListaEventos();
		for (int i = 0; i < h.length; i++) {
			Object[] e=new Object[2];
			filas[0]= HORAS_TABLA[i];
			//filas[1]= ins.getInombre();
			filas[1]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividades( a.getCodActividad(),fecha, h[i]);
			filas[2]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 1), h[i]);
			filas[3]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 2), h[i]);
			filas[4]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 3), h[i]);
			filas[5]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 4), h[i]);
			filas[6]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 5), h[i]);
			modeloTabla.addRow(filas);
			
			
//			this.eventosActiviadad[i][1]=getVentanaPrincipal().getPrograma().buscarEventoActividades( a.getCodActividad(),fecha, h[i]);
//			this.eventosActiviadad[i][2]=getVentanaPrincipal().getPrograma().buscarEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 1), h[i]);
//			this.eventosActiviadad[i][3]=getVentanaPrincipal().getPrograma().buscarEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 2), h[i]);
//			this.eventosActiviadad[i][4]=getVentanaPrincipal().getPrograma().buscarEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 3), h[i]);
//			this.eventosActiviadad[i][5]=getVentanaPrincipal().getPrograma().buscarEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 4), h[i]);
//			this.eventosActiviadad[i][6]=getVentanaPrincipal().getPrograma().buscarEventoActividades( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 5), h[i]);
			
		}
		
		table.setModel(modeloTabla);
		table.setDefaultRenderer(Object.class, t);
	}
	
	public String sumarDiasFecha(Date fecha, int dias){
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(fecha); // Configuramos la fecha que se recibe
		   calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
		    Date next = calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String folderName = formatter.format(next);
		    return folderName;
		
		  }
	public VentanaPrincipal getVentanaPrincipal() {
		return vp;
	}
	private JPanel getPnMenu() {
		if (pnMenu == null) {
			pnMenu = new JPanel();
			pnMenu.setLayout(new BorderLayout(0, 0));
			try {
				pnMenu.add(getPnSelecInsta(), BorderLayout.WEST);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnMenu.add(getPnFecha(), BorderLayout.CENTER);
			pnMenu.add(getBtnVer(), BorderLayout.EAST);
		}
		return pnMenu;
	}
	private JButton getBtnVer() {
		if (btnVer == null) {
			btnVer = new JButton("Comprobar Disponibilidad");
			btnVer.setMnemonic('C');
			btnVer.setEnabled(false);
			btnVer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					pnInformacion.removeAll();
					pnInformacion.updateUI();
					Actividad a = (Actividad) cbxActividad.getSelectedItem();
					try {
						mostrarTabla(HORAS_TABLA, a, obtenerFecha());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					btnApuntarActividad.setEnabled(true);
					btnCancelarActividad.setEnabled(true);
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
	private JPanel getPnSelecInsta() throws SQLException {
		if (pnSelecInsta == null) {
			pnSelecInsta = new JPanel();
			pnSelecInsta.add(getLblActividad());
			pnSelecInsta.add(getCbxActividad());
		}
		return pnSelecInsta;
	}
	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Actividad:");
			try {
				lblActividad.setLabelFor(getCbxActividad());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lblActividad.setDisplayedMnemonic('I');
		}
		return lblActividad;
	}
	JComboBox<Actividad> getCbxActividad() throws SQLException {
		if (cbxActividad == null) {
			cbxActividad = new JComboBox<Actividad>();
			rellenarCbxActividades();
			cbxActividad.setPreferredSize(new Dimension(125, 20));
		}
		return cbxActividad;
	}

	protected void rellenarCbxActividades() {
		DefaultComboBoxModel<Actividad> modelo = new DefaultComboBoxModel<Actividad>();
		for (Actividad actividad : getVentanaPrincipal().getPrograma().getActividades()) {
			modelo.addElement(actividad);
		}
		cbxActividad.setModel(modelo);
	}
	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.add(getLblFecha());
			try {
				pnFecha.add(getDateChooser());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pnFecha;
	}
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha");
		}
		return lblFecha;
	}
//	private JPanel getPnDatos() {
//			JTable jt= (JTable)table.getComponentAt(table.getSelectedRow(), table.getSelectedColumn());
//			System.out.println(jt);
//			EventoActividad ea=this.eventosActiviadad[table.getSelectedRow()][table.getSelectedColumn()][jt.getSelectedColumn()];
//			this.pnDatos= new PanelDatosActividadesAdministrador(this,ea);
//			return pnDatos;
//	}
	private JPanel getPnIntermedio() {
		if (pnIntermedio == null) {
			pnIntermedio = new JPanel();
			pnIntermedio.setLayout(new BorderLayout(0, 0));
			pnIntermedio.add(getPnTabla());
		}
		return pnIntermedio;
	}
	private JPanel getPnInformacion() {
		if (pnInformacion == null) {
			pnInformacion = new JPanel();
			pnInformacion.setLayout(new BorderLayout(0, 0));
			pnInformacion.setVisible(true);
		}
		return pnInformacion;
	}
	public EventoActividad[][] getEventosActividad(){
		return eventosActiviadad;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new GridLayout(0, 1, 0, 0));
			pnBotones.add(getBtnApuntarActividad());
			pnBotones.add(getBtnCancelarActividad());
			pnBotones.add(getBtnCrearActividad());
			pnBotones.add(getBtnCancelarActividadPuntual());
			pnBotones.add(getBtnBorrarActividad());
		}
		return pnBotones;
	}
	private JButton getBtnApuntarActividad() {
		if (btnApuntarActividad == null) {
			btnApuntarActividad = new JButton("Apuntar ");
			btnApuntarActividad.setEnabled(false);
			btnApuntarActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int r =	table.getSelectedRow();
					int c = table.getSelectedColumn();
					
					if(r>=0 && c>=0 && !table.getValueAt(r, c).equals("")){
			
				horaSeleccionada = (String) table.getValueAt(r, 0);
				diaSeleccionado = table.getColumnName(c);
				//System.out.println(horaSeleccionada);
				try {
					if(vp.getPrograma().buscarEventoActividades(getActividad().getCodActividad(),dia(), getHora().trim())!=null){
					EventoActividad ev = vp.getPrograma().buscarEventoActividades(getActividad().getCodActividad(),dia(), getHora().trim());
					String fechaActividad = ev.getFecha();
//				System.out.println("Fecha Actividad: " + ev.getFecha());
					
					
					Date d = pasarFechaDate(fechaActividad);
					Date hasta = sumarRestarDiasFecha(d, -1);
					String enFecha = formatearFecha(hasta);

					String fechaSeleccionada = obtenerFecha();
					
					
					
					if (!enFecha.equals(formatearFechaActual()) && !ev.getFecha().equals(fechaSeleccionada)) {
						JOptionPane
								.showMessageDialog(pnCentro,
										"Sólo se puede apuntar 1 día antes de la realización de la actividad.");
					}else{
						if(vp.getPrograma().comprobarHoras(getHora(),getHoraActual())&&diaSeleccionado.equals(formatearFechaActual())){
							JOptionPane.showMessageDialog(pnCentro, "Hora de la actividad pasada.");
						}else{
					try {
						mostrarVentanaApuntarSociosAdmin();
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						}
					}
					}else{
						JOptionPane.showMessageDialog(pnCentro, "Solo se puede apuntar al inicio de la actividad.");
					}
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}else{
					JOptionPane.showMessageDialog(pnCentro, "Seleccione una celda que no este vacía.");
				}
				}
			});
		}
		return btnApuntarActividad;
	}
	
	public  Actividad getActividad(){
		return (Actividad) cbxActividad.getSelectedItem();
	}
	
	public String getHora(){
		String[] s = horaSeleccionada.split("-");
		
		return s[0];
	}
	
	public String dia(){
		return diaSeleccionado;
	}
	
	public String getHoraActual(){
		Date hora =	sumarRestarHorasFecha(new Date(), 1);
		@SuppressWarnings("deprecation")
		String s1 = String.valueOf(hora.getHours())+":00";
		return s1;
	}
	 public Date sumarRestarHorasFecha(Date fecha, int horas){
	       Calendar calendar = Calendar.getInstance();
	       calendar.setTime(fecha); // Configuramos la fecha que se recibe
	       calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
	       return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
	  
}
	 
	 public Date sumarRestarDiasFecha(Date fecha, int dias){
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(fecha); // Configuramos la fecha que se recibe
		   calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
		    return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		
		
		  }
	
	 private String formatearFechaActual() {
			Date today = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String folderName = formatter.format(today);
			return folderName;
		}
	 
	
	
	private void mostrarVentanaApuntarSociosAdmin() throws SQLException {
		VentanaApuntarActividadAdmin vc = new VentanaApuntarActividadAdmin(this);
		if(vc.comboInsta()){
		vc.setLocationRelativeTo(this);
		vc.setModal(true);
		vc.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(pnCentro, "Sólo se puede apuntar al inicio de la actividad.");
		}
	
	}
	private void mostrarVentanaCancelarPlazaSociosAdmin() throws SQLException {
		VentanaCancelarPlazaActividadAdmin vc = new VentanaCancelarPlazaActividadAdmin(this);
		if(vc.comboInsta()){
		vc.setLocationRelativeTo(this);
		vc.setModal(true);
		vc.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(pnCentro, "Sólo se puede cancelar al inicio de la actividad.");
		}
	
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
	
	
	private JButton getBtnCancelarActividad() {
		if (btnCancelarActividad == null) {
			btnCancelarActividad = new JButton("<html>Cancelar<br>Apuntados</html>");
			btnCancelarActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int r =	table.getSelectedRow();
					int c = table.getSelectedColumn();
					if(r>=0 && c>=0 && !table.getValueAt(r, c).equals("")){
			
				horaSeleccionada = (String) table.getValueAt(r, 0);
				diaSeleccionado = table.getColumnName(c);
				//System.out.println(horaSeleccionada);
				if(vp.getPrograma().comprobarHoras(getHora(),getHoraActual())&&diaSeleccionado.equals(formatearFechaActual())){
					JOptionPane.showMessageDialog(pnCentro, "Hora de la actividad pasada.");
				}else{
				try {
					mostrarVentanaCancelarPlazaSociosAdmin();
					
				} catch (SQLException ev) {
					// TODO Auto-generated catch block
					ev.printStackTrace();
				}
				}
				}else{
					JOptionPane.showMessageDialog(pnCentro, "Seleccione una celda que no este vacía.");
				}
				}
			});
			btnCancelarActividad.setEnabled(false);
		}
		return btnCancelarActividad;
	}
	
	public void repintarTabla(){
		pnInformacion.removeAll();
		pnInformacion.updateUI();
		Actividad a = (Actividad) cbxActividad.getSelectedItem();
		try {
			mostrarTabla(HORAS_TABLA, a, obtenerFecha());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnApuntarActividad.setEnabled(true);
		btnCancelarActividad.setEnabled(true);
		
		
	}
	
	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("Crear");
			btnCrearActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarVentanaCrearActividad();
				}
			});
		}
		return btnCrearActividad;
	}
	
	private void mostrarVentanaCrearActividad(){
		VentanaCrearActividad vr = new VentanaCrearActividad(this);
		vr.setLocationRelativeTo(null);
		vr.setModal(true);
		vr.setVisible(true);
	} 

	private JButton getBtnCancelarActividadPuntual() {
		if (btnCancelarActividadPuntual == null) {
			btnCancelarActividadPuntual = new JButton(
					"<html>Cancelar<br>Actividad<br>Puntual<br></html>");
			btnCancelarActividadPuntual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int r = table.getSelectedRow();
					int c = table.getSelectedColumn();

					if (r >= 0 && c >= 0 && !table.getValueAt(r, c).equals("")) {
						horaSeleccionada = (String) table.getValueAt(r, 0);
						diaSeleccionado = table.getColumnName(c);
						try {
							if (vp.getPrograma().buscarEventoActividades(
									getActividad().getCodActividad(), dia(),
									getHora().trim()) != null) {
								String horaSeleccionada = (String) table
										.getValueAt(r, 0);
								String[] s = horaSeleccionada.split("-");
								String hS = s[0].trim();
								String diaSeleccionado = table.getColumnName(c);
								Actividad a = (Actividad) cbxActividad
										.getSelectedItem();
								// Instalacion in = (Instalacion)
								// cbxInstalaciones.getSelectedItem();
								// String i = in.getCodInstalacion();
								EventoActividad ev = vp.getPrograma()
										.buscarEventoActividades(
												a.getCodActividad(),
												diaSeleccionado, hS);
								Date hora = sumarRestarHorasFecha(new Date(), 1);
								@SuppressWarnings("deprecation")
								String s1 = String.valueOf(hora.getHours()) + ":00";

								if (vp.getPrograma().comprobarHoras(
										horaSeleccionada, s1)
										&& diaSeleccionado
												.equals(formatearFechaActual())) {
									JOptionPane.showMessageDialog(pnCentro,
											"Hora de la actividad pasada.");
								} else {

									if (s1.equals(hS)
											&& diaSeleccionado
													.equals(formatearFechaActual())) {
										System.out.println(diaSeleccionado
												.equals(formatearFechaActual()));
										System.out.println("hora Actual:" + s1);
										System.out.println("hora Actividad: " + hS);
										System.out.println(formatearFechaActual());
										System.out.println(diaSeleccionado);
										JOptionPane
												.showMessageDialog(pnCentro,
														"Solo se puede cancelar 1h antes del comienzo de la actividad.");
									} else {

										try {
											if (vp.getPrograma()
													.buscarActividadPuntual(
															a.getCodActividad(),
															ev.getFecha(),
															ev.getHoraInicio(),
															ev.getCodInstalacion(),
															ev.getCodUsuario(),
															a.getNombreA())) {
												System.out.println("Entro");
												int res = JOptionPane
														.showConfirmDialog(
																pnCentro,
																"¿Esta Seguro de cancelar la actividad?",
																"Cancelar",
																JOptionPane.YES_NO_OPTION);
												if (res == 0) {
													mostrarVentanaApuntadosAvisar(getApuntados(ev));
													if (vp.getPrograma()
															.borrarActividadPuntual(
																	a.getCodActividad(),
																	ev.getFecha(),
																	ev.getHoraInicio(),
																	ev.getCodInstalacion(),
																	ev.getCodUsuario(),
																	a.getNombreA())) {
														JOptionPane
																.showMessageDialog(
																		pnCentro,
																		"La actividad ha sido cancelada.");
														 repintarTabla();
													}else{
														JOptionPane
														.showMessageDialog(
																pnCentro,
																"La actividad no ha sido cancelada.");
													}
											
												} else {

												}

											}
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

									}
								}
							} else {
								JOptionPane
										.showMessageDialog(pnCentro,
												"Solo se puede cancelar al inicio de la actividad.");
							}
						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(pnCentro,
								"Seleccione una celda que no este vacía.");
					}
				}
			});
		}
		
		return btnCancelarActividadPuntual;
	}
	
	private JButton getBtnBorrarActividad() {
		if (btnBorrarActividad == null) {
			btnBorrarActividad = new JButton("Borrar Periodica");
			btnBorrarActividad.setEnabled(false);
			btnBorrarActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pedirPeticionBorrarActividadPeriodica();
				}
			});
		}
		return btnBorrarActividad;
	}

	// --- Jose - Borrar Peridodicas --- \\

	private void modificarBotonBorrarPeriodica(boolean b) {
		getBtnBorrarActividad().setEnabled(b);
	}
	
	private void rellenarParametros(int fila, int columna){
		actividadParaBorrarP = (Actividad) cbxActividad.getSelectedItem();
		fechaParaBorrarP = sumarDiasFecha( dateChooser.getDate(), columna-1);
	}
	
	private void pedirPeticionBorrarActividadPeriodica() {
		VentanaConfirmacionBorrarPeriodica vcb = new VentanaConfirmacionBorrarPeriodica(actividadParaBorrarP, fechaParaBorrarP, this);
		vcb.setLocationRelativeTo(this);
		vcb.setModal(true);
		vcb.setVisible(true);
	}
	public VentanaPrincipal getVP() {
		return vp;
	}

	public void mostrarVentanaApuntadosAvisar(List<HashMap<String, String>>apuntados) {
		if(apuntados.size()>0) {
		VentanaAviso va = new VentanaAviso(apuntados);
		va.setLocationRelativeTo(this);
		va.setModal(true);
		va.setVisible(true);
		}
	}
	public List<HashMap<String, String>> getApuntados(EventoActividad ea) {
		try {
			ArrayList<Apuntados> apuntados = DataBase.cargarApuntados();
			List<HashMap<String, String>> aux = new ArrayList<HashMap<String, String>>();
				for (Apuntados ap : apuntados) {
					if(ap.getCodActividad().equals(ea.getCodActividad()) &&
							ap.getCodInstalacion().equals(ea.getCodInstalacion()) &&
							ap.getFecha().equals(ea.getFecha()) &&
							ap.getHoraInicio().equals(ea.getHoraInicio())) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("codigo", ap.getCodUsuario());
						map.put("instalacion", ap.getCodInstalacion());
						map.put("hora", ap.getHoraInicio());
						map.put("fecha", ap.getFecha());
						map.put("motivo", "Se ha eliminado la actividad "+ DataBase.getNombreActividad(ap.getCodActividad()));
						aux.add(map);
					}
				}
			
			return aux;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
