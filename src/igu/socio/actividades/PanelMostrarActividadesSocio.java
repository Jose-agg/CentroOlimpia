package igu.socio.actividades;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.toedter.calendar.JDateChooser;

import dataBase.DataBase;
import date.DateComparable;
import igu.main.VentanaPrincipal;
import logica.objetos.Actividad;
import logica.objetos.EventoActividad;
import logica.objetos.Instalacion;
import modelos.FormatoTablaActividadesSocio;
import modelos.ModeloTabla;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class PanelMostrarActividadesSocio extends JPanel {

	private JPanel pnSur;
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
	private JPanel pnMenu;
	private JPanel pnFecha;
	private JLabel lblFecha;
	private JPanel pnDatos;
	private JLabel lblActividad;
	private JComboBox<Actividad> cbxActividad;
	private JButton btnVer;
	private JPanel pnIntermedio;
	private EventoActividad[][] eventosActiviadad;
	private JPanel pnCodSocio;
	private JPanel pnSelecionarActividad;
	private JLabel lblNumSocio;
	private JTextField txtNumSocio;
	private JButton btnReservarPlaza;
	private JPanel pnApuntarse;
	private JLabel lblInstalacion;
	private JComboBox<Instalacion> cbxInstalaciones;
	private JPanel pnInstalaciones;
	
	// --- Datos para apuntarse --- \\
	private String codSocio;
	private Actividad actividadSeleccionada;
	private String fechaSeleccionada;
	private String horaSeleccionada;
	
	
	
	private JButton btnCancelar;

	/**
	 * Create the panel.
	 */
	public PanelMostrarActividadesSocio(VentanaPrincipal vp) {
		this.vp=vp;
		setBounds(100, 100, 925, 573);
		setLayout(new BorderLayout(0, 0));
		add(getPnSur(), BorderLayout.SOUTH);
		getPnSur().setLayout(new BorderLayout(0, 0));
		getPnSur().add(getPnApuntarse(), BorderLayout.EAST);
		add(getPnCentro(), BorderLayout.CENTER);
		add(getPnMenu(), BorderLayout.NORTH);

	}

	private JPanel getPnTabla() {
		if(pnTabla==null) {
			pnTabla = new JPanel();
			pnTabla.setLayout(new BorderLayout(0, 0));
			pnTabla.add(getScrollPane());
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

	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
		}
		return pnSur;
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
				public void mousePressed(MouseEvent arg0) {
					int fila = table.rowAtPoint(arg0.getPoint());
					int columna = table.columnAtPoint(arg0.getPoint());
					rellenarDatos(); //<----------------------------------PELAYO
					rellenarParametros(fila, columna);
					if (!table.getValueAt(fila, columna).equals("")){
						System.out.println(codSocio);
						System.out.println(actividadSeleccionada.getNombreA());
						System.out.println(fechaSeleccionada);
						System.out.println(horaSeleccionada);
					}else {
						System.out.println("Esta libre");
					}
				}
			});
			table.getTableHeader().setReorderingAllowed(false);
		}
		return table;
	}
	private void rellenarParametros(int fila, int columna){
		actividadSeleccionada = (Actividad) cbxActividad.getSelectedItem();
		fechaSeleccionada = sumarDiasFecha( dateChooser.getDate(), columna-1);
		horaSeleccionada = this.HORAS[fila];
	}
	private JDateChooser getDateChooser() throws ParseException {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setEnabled(false);
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
	private void mostrarTabla(String[] h, Actividad a, String fecha) throws SQLException {
		String[] columns=new String [] {"Horas",fecha,sumarDiasFecha( dateChooser.getDate(), 1),sumarDiasFecha( dateChooser.getDate(), 2),sumarDiasFecha( dateChooser.getDate(), 3),sumarDiasFecha( dateChooser.getDate(), 4),sumarDiasFecha( dateChooser.getDate(), 5)};
		modeloTabla = new ModeloTabla(columns,0);
		//ModeloCeldas rr = new ModeloCeldas(2);
		FormatoTablaActividadesSocio t = new FormatoTablaActividadesSocio(this);
		String[] filas = new String[7];
		getVentanaPrincipal().getPrograma().actualizarListaEventos();
		for (int i = 0; i < h.length; i++) {
			Object[] e=new Object[2];
			filas[0]= HORAS_TABLA[i];
			//filas[1]= ins.getInombre();
			filas[1]= getVentanaPrincipal().getPrograma().buscarNombreEventoActividadesSocios( a.getCodActividad(),fecha, h[i], codSocio);
			filas[2]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividadesSocios( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 1), h[i],codSocio);
			filas[3]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividadesSocios( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 2), h[i],codSocio);
			filas[4]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividadesSocios( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 3), h[i],codSocio);
			filas[5]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividadesSocios( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 4), h[i],codSocio);
			filas[6]=getVentanaPrincipal().getPrograma().buscarNombreEventoActividadesSocios( a.getCodActividad(),sumarDiasFecha( dateChooser.getDate(), 5), h[i],codSocio);
			modeloTabla.addRow(filas);	
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
				pnMenu.add(getPnDatos(), BorderLayout.WEST);
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
					codSocio = txtNumSocio.getText();
					Actividad a = (Actividad) cbxActividad.getSelectedItem();
					try {
						vp.getPrograma().refrescarArray();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						try {
							mostrarTabla(HORAS_TABLA, a, obtenerFecha());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
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
	private JPanel getPnDatos() throws SQLException {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setLayout(new BorderLayout(0, 0));
			pnDatos.add(getPnCodSocio(), BorderLayout.WEST);
			pnDatos.add(getPnSelecionarActividad(), BorderLayout.EAST);
		}
		return pnDatos;
	}

	private JPanel getPnCodSocio() {
		if (pnCodSocio == null) {
			pnCodSocio = new JPanel();
			pnCodSocio.add(getLblNumSocio());
			pnCodSocio.add(getTxtNumSocio());
		}
		return pnCodSocio;
	}
	private JPanel getPnSelecionarActividad() throws SQLException {
		if (pnSelecionarActividad == null) {
			pnSelecionarActividad = new JPanel();
			pnSelecionarActividad.add(getLblActividad());
			pnSelecionarActividad.add(getCbxActividad());
		}
		return pnSelecionarActividad;
	}
	private JLabel getLblNumSocio() {
		if (lblNumSocio == null) {
			lblNumSocio = new JLabel("Num Socio");
		}
		return lblNumSocio;
	}
	private JTextField getTxtNumSocio() {
		if (txtNumSocio == null) {
			txtNumSocio = new JTextField();
			txtNumSocio.setColumns(10);
		}
		return txtNumSocio;
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
	private JComboBox<Actividad> getCbxActividad() throws SQLException {
		if (cbxActividad == null) {
			cbxActividad = new JComboBox<Actividad>();
			cbxActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(txtNumSocio.getText()!=""){
						dateChooser.setEnabled(true);
					}
				}
			});
			DefaultComboBoxModel<Actividad> modelo = new DefaultComboBoxModel<Actividad>();
			for (Actividad actividad : getVentanaPrincipal().getPrograma().getActividades()) {
				modelo.addElement(actividad);

			}
			cbxActividad.setModel(modelo);
			cbxActividad.setPreferredSize(new Dimension(125, 20));
		}
		return cbxActividad;
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
	private JPanel getPnIntermedio() {
		if (pnIntermedio == null) {
			pnIntermedio = new JPanel();
			pnIntermedio.setLayout(new BorderLayout(0, 0));
			pnIntermedio.add(getPnTabla());
		}
		return pnIntermedio;
	}
	public EventoActividad[][] getEventosActividad(){
		return eventosActiviadad;
	}
	//-------------------------------PELAYO INTERFAZ--------------------------------------
	
	private JButton getBtnReservarPlaza() {
		if (btnReservarPlaza == null) {
			btnReservarPlaza = new JButton("Reservar plaza");
			btnReservarPlaza.setEnabled(false);
			btnReservarPlaza.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (puedeApuntarse()) {
						apuntarSocio();
						JOptionPane.showMessageDialog(null, "Se te ha apuntado correctamente.");
						//JOSE AÑADE AQUI LO DE ACTUALIZAR LA TABLA UEUE
					} else 
						JOptionPane.showMessageDialog(null, "No puedes apuntarte.");
				}
			});
		}
		return btnReservarPlaza;
	}
	
	private JPanel getPnApuntarse() {
		if (pnApuntarse == null) {
			pnApuntarse = new JPanel();
			pnApuntarse.add(getPnInstalaciones());
			pnApuntarse.add(getBtnReservarPlaza());
			pnApuntarse.add(getBtnCancelar());
		}
		return pnApuntarse;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Selecciona una instalaci\u00F3n:");
		}
		return lblInstalacion;
	}
	private JComboBox<Instalacion> getCbxInstalaciones() {
		if (cbxInstalaciones == null) {
			cbxInstalaciones = new JComboBox<Instalacion>();
			cbxInstalaciones.setEnabled(false);
		}
		return cbxInstalaciones;
	}
	private JPanel getPnInstalaciones() {
		if (pnInstalaciones == null) {
			pnInstalaciones = new JPanel();
			pnInstalaciones.add(getLblInstalacion());
			pnInstalaciones.add(getCbxInstalaciones());
		}
		return pnInstalaciones;
	}
	
	//-----------------------------------------------------Pelayo Logica-----------------------------------------
		private void habilitarSeleccion(boolean enabled) {
			getCbxInstalaciones().setEnabled(enabled);
			getBtnReservarPlaza().setEnabled(getCbxInstalaciones().getModel().getSize()!=0);
		}
		
		private void rellenarDatos() {
			getCbxInstalaciones().setModel(new DefaultComboBoxModel<Instalacion>(obtenerInstalaciones()));
			habilitarSeleccion(true);
		}
		
		private Instalacion[] obtenerInstalaciones() {
			ArrayList<Instalacion> instalaciones = null;
			String horaInicio = getHoraInicio();
			String fecha = modeloTabla.getColumnName(table.getSelectedColumn());
			try {
				instalaciones = DataBase.getInstalacionesEventos(((Actividad) getCbxActividad().getSelectedItem()).getCodActividad(), fecha, horaInicio);
			} catch (SQLException e) { e.printStackTrace(); }
			Instalacion[] nombresInstalaciones = new Instalacion[instalaciones.size()];
			for (int i=0; i<instalaciones.size(); i++) {
				nombresInstalaciones[i]=instalaciones.get(i);
			}
			return nombresInstalaciones;
		}
		
		private String getHoraInicio() {
			return ((String) modeloTabla.getValueAt(table.getSelectedRow(), 0)).split(" - ")[0];
		}
		
		private boolean puedeApuntarse() {
			boolean fechaCorrecta = comprobarFecha();
			boolean existsReserva = false;
			boolean existsApuntado = false;
			boolean hayPlazas = compruebaPlazas();
			String fecha = modeloTabla.getColumnName(table.getSelectedColumn());
			try {
				existsReserva = DataBase.existsReserva(getTxtNumSocio().getText(), fecha, getHoraInicio());
				existsApuntado = DataBase.existsApuntado(getTxtNumSocio().getText(), fecha, getHoraInicio());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return fechaCorrecta && !existsReserva && !existsApuntado && hayPlazas;
		}
		
		private boolean comprobarFecha() {
			DateComparable today = new DateComparable();
			int diaEvento = Integer.parseInt(modeloTabla.getColumnName(table.getSelectedColumn()).split("/")[0]);
			boolean diaAntes = today.getDay()+1 == diaEvento; //<-cambios
			boolean diaActual = today.getDay() == diaEvento;
			
			int horaActual = DateComparable.getActualHour();
			int horaEvento = Integer.parseInt(getHoraInicio().split(":")[0]);
			boolean horaAntes = horaActual < horaEvento-1;
			
			return diaAntes || (diaActual && horaAntes);
		}
		
		private boolean compruebaPlazas() {
			String codActividad = "";
			String codInstalacion = "";
			String fecha = modeloTabla.getColumnName(table.getSelectedColumn());
			String horaInicio = getHoraInicio();
			int plazasEvento = 0;
			int plazasOcupadas = 0;
			try {
				codActividad = ((Actividad)getCbxActividad().getSelectedItem()).getCodActividad();
				codInstalacion = ((Instalacion)getCbxInstalaciones().getSelectedItem()).getCodInstalacion();
				plazasEvento = DataBase.getPlazasEvento(codActividad, codInstalacion, fecha, horaInicio);
				plazasOcupadas = DataBase.countPlazasOcupadas(codActividad, fecha, horaInicio, codInstalacion);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return plazasEvento > plazasOcupadas;
		}
		
		private void apuntarSocio() {
			String codUsuario = getTxtNumSocio().getText();
			String codActividad = "";
			String fecha = modeloTabla.getColumnName(table.getSelectedColumn());
			String hora = getHoraInicio();
			String codInstalacion = "";
			try {
				codActividad = ((Actividad)getCbxActividad().getSelectedItem()).getCodActividad();
				codInstalacion = ((Instalacion)getCbxInstalaciones().getSelectedItem()).getCodInstalacion();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				DataBase.apuntarSocio(codUsuario, codActividad, codInstalacion, fecha, hora);
			} catch (SQLException e) {
				try {
					DataBase.actualizarSocioApuntado("true", codUsuario, codActividad, codInstalacion, fecha, hora);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		
		//---------------------------------------------------------------------------------------------------------------------------------
		
		
	private JButton getBtnCancelar () {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar plaza");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int r =	table.getSelectedRow();
					int c = table.getSelectedColumn();
					
					if (vp.getPrograma().buscarUsuarioS(txtNumSocio.getText())) {
						if(r>0 && c>0 && !table.getValueAt(r, c).equals("")){
							String horaSeleccionada = (String) table.getValueAt(r, 0);
							String[] s = horaSeleccionada.split("-");
							String hS = s[0].trim();
							String diaSeleccionado = table.getColumnName(c);
							Actividad a = (Actividad) cbxActividad.getSelectedItem();
							Instalacion in = (Instalacion) cbxInstalaciones.getSelectedItem();
							String i = in.getCodInstalacion();
							EventoActividad ev = null;
							try {
								ev = vp.getPrograma().buscarEventoActividades(a.getCodActividad(), diaSeleccionado, hS);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								Date hora =	sumarRestarHorasFecha(new Date(), 1);
								@SuppressWarnings("deprecation")
								String s1 = String.valueOf(hora.getHours())+":00";
							
							
								
								if(vp.getPrograma().comprobarHoras(horaSeleccionada,s1)&&diaSeleccionado.equals(formatearFechaActual() )){
									JOptionPane.showMessageDialog(pnCentro, "Hora de la actividad pasada.");
								}else{
									
								
								if(s1.equals(hS) && diaSeleccionado.equals(formatearFechaActual())){
									System.out.println(diaSeleccionado.equals(formatearFechaActual()));
									System.out.println("hora Actual:" + s1);
									System.out.println("hora Actividad: " +hS);
									System.out.println(formatearFechaActual());
									System.out.println(diaSeleccionado);
									JOptionPane.showMessageDialog(pnCentro, "Solo se puede cancelar 1h antes del comienzo de la actividad.");
								}else{
								
									
									
								
								try {
									if(vp.getPrograma().buscarApuntadoB(txtNumSocio.getText(),a.getCodActividad(),ev.getFecha(),ev.getHoraInicio(),i)){
										int res = JOptionPane.showConfirmDialog(pnCentro,"¿Esta Seguro de cancelar la plaza?", "Cancelar", JOptionPane.YES_NO_OPTION);
										if(res==0){
										
											if(vp.getPrograma().cancelarPlazaSocio(vp.getPrograma().buscarApuntado(txtNumSocio.getText(),a.getCodActividad(),ev.getFecha(),ev.getHoraInicio(),i)).equals("Cancelada")){
												JOptionPane.showMessageDialog(pnCentro, "La plaza ha sido cancelada.");
												
														//txtNumSocio.setText("");
														repintarTabla();
													}else{
												
													}
									
										}
										
										
									}else{
										JOptionPane.showMessageDialog(pnCentro, "No esta apuntado a esta actividad para este día y esta hora.");
									}
								} catch (HeadlessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								}
								}
							
							
						}else{
							JOptionPane.showMessageDialog(pnCentro, "Seleccione una celda que no este vacía.");
						}
						
						
						
					}else{
						JOptionPane.showMessageDialog(pnCentro, "Número incorrecto.");
					}
						
					
					
					}
				
			});
			
		}
		return btnCancelar;
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
	 
	 
	 
	public void repintarTabla(){
		Actividad a = (Actividad) cbxActividad.getSelectedItem();
		try {
			vp.getPrograma().refrescarArray();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			try {
				mostrarTabla(HORAS_TABLA, a, obtenerFecha());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	}
	
	

		
