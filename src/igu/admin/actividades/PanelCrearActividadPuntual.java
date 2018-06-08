package igu.admin.actividades;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import dataBase.DataBase;
import date.DateComparable;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;

import logica.objetos.Actividad;
import logica.objetos.Instalacion;
import logica.objetos.Reserva;

import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class PanelCrearActividadPuntual extends JPanel {

	private static final long serialVersionUID = 1L;
	public final static String[] HORAS = {"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
	public final static String[] HORAS_TERMINAR = {"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00","24:00"};
	private VentanaCrearActividad vCA;
	private JPanel pnNombreActividad;
	private JPanel pnDatosActividad;
	private JLabel lblNombreActividad;
	private JTextField txtNombreActividad;
	private JPanel pnInstalacion;
	private JPanel pnHorario;
	private JPanel pnFecha;
	private JPanel pnHoraInicio;
	private JPanel pnHoraFinal;
	private JLabel lblInstalacion;
	private JLabel lblFecha;
	private JLabel lblHoraInicio;
	private JComboBox<String> cbxHoraInicio;
	private JLabel lblHoraFinal;
	private JPanel pnHoras;
	private JComboBox<String> cbxHoraFinal;
	private JPanel pnSur;
	private JPanel pnPlazas;
	private JPanel pnBotones;
	private JRadioButton rdbPlazas;
	private JLabel lblPlazas;
	private JTextField txtPlazas;
	private JButton btnCrear;
	private JButton btnCancelar;
	private JComboBox<Instalacion> cbxInstalacion;
	private JDateChooser dateChooser;
	private JButton btnComprobar;
	private ArrayList<Reserva> colisiones;
	private boolean canceladaTransaccion;
	
	
	public PanelCrearActividadPuntual(VentanaCrearActividad ventanaCrearActividad) throws SQLException {
		vCA=ventanaCrearActividad;
		setBounds(100, 100, 925, 573);
		setLayout(new BorderLayout(0, 0));
		add(getPnNombreActividad(), BorderLayout.NORTH);
		add(getPnDatosActividad(), BorderLayout.CENTER);
		add(getPnSur(), BorderLayout.SOUTH);
		modificarPanel(false);
	}

	private JPanel getPnNombreActividad() {
		if (pnNombreActividad == null) {
			pnNombreActividad = new JPanel();
			pnNombreActividad.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Nombre de la Actividad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnNombreActividad.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
			pnNombreActividad.add(getLblNombreActividad());
			pnNombreActividad.add(getTxtNombreActividad());
			//pnNombreActividad.add(getBtnComprobar());
		}
		return pnNombreActividad;
	}
	private JPanel getPnDatosActividad() throws SQLException {
		if (pnDatosActividad == null) {
			pnDatosActividad = new JPanel();
			pnDatosActividad.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Datos de la Actividad", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			pnDatosActividad.setLayout(new BorderLayout(0, 0));
			pnDatosActividad.add(getPnInstalacion(), BorderLayout.NORTH);
			pnDatosActividad.add(getPnHorario(), BorderLayout.CENTER);
		}
		return pnDatosActividad;
	}
	private JLabel getLblNombreActividad() {
		if (lblNombreActividad == null) {
			lblNombreActividad = new JLabel("Nombre de la actividad");
		}
		return lblNombreActividad;
	}
	public JTextField getTxtNombreActividad() {
		if (txtNombreActividad == null) {
			txtNombreActividad = new JTextField();
			txtNombreActividad.setDisabledTextColor(Color.BLACK);
			txtNombreActividad.setColumns(10);
		}
		return txtNombreActividad;
	}

	private JButton getBtnComprobar() {
		if (btnComprobar == null) {
			btnComprobar = new JButton("Comprobar");
			btnComprobar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int val = comprobarNombreActividad();
					switch (val) {
					case -1:
						JOptionPane.showMessageDialog(null, "Este nombre de actividad ya esta en uso, selecione otro nuevo");
						break;
					case 0:
						modificarPanel(true);
						txtNombreActividad.setEnabled(false);
						txtNombreActividad.setEditable(false);
						btnComprobar.setEnabled(false);
						break;
					case 1:
						JOptionPane.showMessageDialog(null, "Escriba un nombre para comprobar la actividad");
						break;
					default:
						JOptionPane.showMessageDialog(null, "Error desconocido, pongase en contacto con el servicio tecnico");
						break;
					}
				}
			});
		}
		return btnComprobar;
	}
	private JPanel getPnInstalacion() throws SQLException {
		if (pnInstalacion == null) {
			pnInstalacion = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnInstalacion.getLayout();
			flowLayout.setVgap(10);
			flowLayout.setHgap(15);
			flowLayout.setAlignment(FlowLayout.LEFT);
			pnInstalacion.add(getLblInstalacion());
			pnInstalacion.add(getCbxInstalacion());
		}
		return pnInstalacion;
	}
	private JPanel getPnHorario() {
		if (pnHorario == null) {
			pnHorario = new JPanel();
			pnHorario.setLayout(new BorderLayout(0, 0));
			pnHorario.add(getPnFecha(), BorderLayout.NORTH);
			pnHorario.add(getPnHoras(), BorderLayout.CENTER);
		}
		return pnHorario;
	}
	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnFecha.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setVgap(10);
			flowLayout.setHgap(15);
			pnFecha.add(getLblFecha());
			pnFecha.add(getDateChooser());
		}
		return pnFecha;
	}
	private JPanel getPnHoraInicio() {
		if (pnHoraInicio == null) {
			pnHoraInicio = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnHoraInicio.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setVgap(10);
			flowLayout.setHgap(15);
			pnHoraInicio.add(getLblHoraInicio());
			pnHoraInicio.add(getCbxHoraInicio());
		}
		return pnHoraInicio;
	}
	private JPanel getPnHoraFinal() {
		if (pnHoraFinal == null) {
			pnHoraFinal = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnHoraFinal.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setVgap(10);
			flowLayout.setHgap(15);
			pnHoraFinal.add(getLblHoraFinal());
			pnHoraFinal.add(getCbxHoraFinal());
		}
		return pnHoraFinal;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalacion");
		}
		return lblInstalacion;
	}

	private JComboBox<Instalacion> getCbxInstalacion() throws SQLException {
		if (cbxInstalacion == null) {
			cbxInstalacion = new JComboBox<Instalacion>();
			rellenarCbxInstalaciones();
		}
		return cbxInstalacion;
	}
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha");
		}
		return lblFecha;
	}

	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setPreferredSize(new Dimension(125, 20));
			dateChooser.setVerifyInputWhenFocusTarget(false);
			
			dateChooser.setDateFormatString("dd/MM/yyyy");
			dateChooser.setMinSelectableDate(new Date());
			dateChooser.setBounds(478, 30, 106, 20);
		}
		return dateChooser;
	}
	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora de inicio");
		}
		return lblHoraInicio;
	}
	private JComboBox<String> getCbxHoraInicio() {
		if (cbxHoraInicio == null) {
			cbxHoraInicio = new JComboBox<String>();
			cbxHoraInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cbxHoraFinal.setEnabled(true);
					getBtnCrear().setEnabled(true);
					rellenarCbxHorasFinales();
				}
			});
			cbxHoraInicio.setModel(new DefaultComboBoxModel<String>(HORAS));
		}
		return cbxHoraInicio;
	}
	private JLabel getLblHoraFinal() {
		if (lblHoraFinal == null) {
			lblHoraFinal = new JLabel("Hora final      ");
		}
		return lblHoraFinal;
	}
	private JPanel getPnHoras() {
		if (pnHoras == null) {
			pnHoras = new JPanel();
			pnHoras.setLayout(new BorderLayout(0, 0));
			pnHoras.add(getPnHoraInicio(), BorderLayout.NORTH);
			pnHoras.add(getPnHoraFinal());
		}
		return pnHoras;
	}
	private JComboBox<String> getCbxHoraFinal() {
		if (cbxHoraFinal == null) {
			cbxHoraFinal = new JComboBox<String>();
			cbxHoraFinal.setEnabled(false);
		}
		return cbxHoraFinal;
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setLayout(new BorderLayout(0, 0));
			pnSur.add(getPnPlazas(), BorderLayout.WEST);
			pnSur.add(getPnBotones(), BorderLayout.EAST);
		}
		return pnSur;
	}
	private JPanel getPnPlazas() {
		if (pnPlazas == null) {
			pnPlazas = new JPanel();
			pnPlazas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Numero de plazas", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			pnPlazas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnPlazas.add(getRdbPlazas());
			pnPlazas.add(getLblPlazas());
			pnPlazas.add(getTxtPlazas());
		}
		return pnPlazas;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnBotones.add(getBtnCrear());
			pnBotones.add(getBtnCancelar());
		}
		return pnBotones;
	}
	private JRadioButton getRdbPlazas() {
		if (rdbPlazas == null) {
			rdbPlazas = new JRadioButton("Limite de plazas");
			rdbPlazas.setSelected(true);
			rdbPlazas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(rdbPlazas.isSelected()) {
						txtPlazas.setEnabled(true);
						txtPlazas.setEditable(true);
					}else {
						txtPlazas.setEnabled(false);
						txtPlazas.setEditable(false);
					}
				}
			});
		}
		return rdbPlazas;
	}
	private JLabel getLblPlazas() {
		if (lblPlazas == null) {
			lblPlazas = new JLabel("         Cantidad");
		}
		return lblPlazas;
	}
	public JTextField getTxtPlazas() {
		if (txtPlazas == null) {
			txtPlazas = new JTextField();
			txtPlazas.setColumns(10);
		}
		return txtPlazas;
	}
	private JButton getBtnCrear() {
		if (btnCrear == null) {
			btnCrear = new JButton("Crear");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int comp = comprobaciones();
					switch (comp) {
					case -1:
						JOptionPane.showMessageDialog(null, "Introduzca un nombre de actividad");
						break;
					case 0:
						JOptionPane.showMessageDialog(null, "Selecione una instalacion");
						break;
					case 1:
						JOptionPane.showMessageDialog(null, "Selecione una fecha");
						break;
					case 2:
						JOptionPane.showMessageDialog(null, "Selecione una hora de inicio");
						break;
					case 3:
						JOptionPane.showMessageDialog(null, "Selecione una hora final");
						break;
					case 4:
						JOptionPane.showMessageDialog(null, "Selecione un numero de plazas");
						break;
					case 5:
						if (!existsActividades()) {
							colisiones = compruebaColisiones();
							if (colisiones.size()>0) {
								mostrarDialogoColision(colisiones); //<-Con colisiones, actualizar la reserva a true, y crear el evento
								if (!canceladaTransaccion) {
									crearActividad();
								}
							} else {
								crearActividad(); //<-Sin colisiones, se crea normal.
							}
						}
						break;
					default:
						JOptionPane.showMessageDialog(null, "Error desconocido, pongase en contacto con el servicio tecnico");
						break;
					}
				}
			});
		}
		return btnCrear;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					vCA.dispose();
				}
			});
		}
		return btnCancelar;
	}
	
	// --- Mis metodos --- \\
	private int comprobarNombreActividad(){
		if(txtNombreActividad.getText().equals("")) return 1;
		return 0;
//		try {
//			return vCA.getPanelMostrarActividadesAdministrador().getVentanaPrincipal().getPrograma().comprobarNombreActividad(txtNombreActividad.getText(),fechaHoy());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return -2;
	}
	private String fechaHoy() {
		Date hoy = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String fecha = formatter.format(hoy);
	    return fecha;
	}
	protected String obtenerFecha(){
	    Date hoy = dateChooser.getDate();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String fecha = formatter.format(hoy);
	    return fecha;
	}
	private void modificarPanel(boolean b) {
		btnCrear.setEnabled(b);
	}
	private int comprobaciones() {
		if (getTxtNombreActividad().getText().equals("") || getTxtNombreActividad().getText().equals(" ")) return -1;
		if(cbxInstalacion.getSelectedItem()==null) return 0;
		if(dateChooser.getDate()==null) return 1;
		if(cbxHoraInicio.getSelectedItem()==null) return 2;
		if(cbxHoraFinal.getSelectedItem()==null) return 3;
		if(rdbPlazas.isSelected() && txtPlazas.getText().equals("")) return 4;
		return 5;
	}
	private void rellenarCbxHorasFinales() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		String[] horas = HORAS_TERMINAR;
		boolean var=false;
		int cont=-1;
		for (String string : horas) {
			cont++;
			if(var) modelo.addElement(horas[cont]);
			if(cbxHoraInicio.getSelectedItem().equals(string)) var = true;
		}
		cbxHoraFinal.setModel(modelo);
	}
	private void rellenarCbxInstalaciones() throws SQLException {
		DefaultComboBoxModel<Instalacion> modelo = new DefaultComboBoxModel<Instalacion>();
		for (Instalacion instalacion : DataBase.getInstalaciones()) {
			modelo.addElement(instalacion);
			
		}
		cbxInstalacion.setModel(modelo);
	}
	public void crearActividad() {
		String nombreActividad = txtNombreActividad.getText();
		Instalacion instalacion = (Instalacion) cbxInstalacion.getSelectedItem();
		String fecha = obtenerFecha();
		String horaInicio = (String) cbxHoraInicio.getSelectedItem();
		String horaFinal = (String) cbxHoraFinal.getSelectedItem();
		String numPlazas = "-1";
		if(rdbPlazas.isSelected()) numPlazas = txtPlazas.getText();
		int var = vCA.getPanelMostrarActividadesAdministrador().getVentanaPrincipal().getPrograma().crearActividadPuntual(nombreActividad, instalacion, fecha, horaInicio, horaFinal, numPlazas);
		if(var!=0) {
			JOptionPane.showMessageDialog(null, "Se ha creado el evento con exito");
			vCA.dispose();
		}else {
			JOptionPane.showMessageDialog(null, "No se ha podido crear el evento");
		}
	}
	
	//--------------------------------------------------------------Pelayo----------------------------------------------------------------------
	/**
	 * Método que comprueba las colisiones para una determinada reserva
	 * @return True si no existen colisiones, false en caso contrario
	 */
	private ArrayList<Reserva> compruebaColisiones() {
		ArrayList<Reserva> reserva = null;
		try {
			reserva = DataBase.getReservasInterseccion(obtenerFecha(), (String) getCbxHoraInicio().getSelectedItem(), 
					(String)getCbxHoraFinal().getSelectedItem(), ((Instalacion)getCbxInstalacion().getSelectedItem()).getCodInstalacion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reserva;
	}
	
	/**
	 * Método que comprueba que no existen actividades del mismo nombre previas al introducido, o que si existen,
	 * no tienen eventos futuros.
	 * @return true si existe la actividad o eventos, false en caso contrario.
	 */
	private boolean existsActividades() {
		Actividad actividad = null;
		try {
			actividad = DataBase.getActividad(getTxtNombreActividad().getText());
		} catch (SQLException e) { e.printStackTrace(); }
		
		if (actividad!=null) {
			int day = Integer.valueOf(obtenerFecha().split("/")[0]);
			int month = Integer.valueOf(obtenerFecha().split("/")[1]);
			int year = Integer.valueOf(obtenerFecha().split("/")[2]);
			if (reservasAnterioresAFecha(actividad.getCodActividad(), day, month, year)) {
				return false;
			} else {
				JOptionPane.showMessageDialog(this, "Existen eventos pendientes para la actividad " + actividad.getNombreA()
				+ ". Por favor, introduzca otro nombre para la actividad.");
				return true;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Método que comprueba que las reservas de una determinada actividad son anteriores al día de hoy.
	 * Esto será útil para saber si una determinada Actividad puede volver a generarse o no.
	 * @return true si puede volver a generarse, false en caso contrario
	 */
	private boolean reservasAnterioresAFecha(String codActividad, int day, int month, int year) {
		ArrayList<Reserva> reservas = null;
		try {
			reservas = DataBase.getReservasEventosNoCancelados(codActividad);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean valido = true;
		DateComparable today = new DateComparable(day, month, year);
		for (Reserva reserva : reservas) {
			if (valido) {
				int fechaCorrecta = (today.compareDates(reserva.getYear(), reserva.getMonth(), reserva.getDay()));
				if (fechaCorrecta==-1) {
					valido = true;
				} else if (fechaCorrecta==0) {
					valido = compruebaHoras(reserva, (String) getCbxHoraInicio().getSelectedItem());
				} else valido = false;
			}
		}
		return valido;
	}
	
	/**
	 * Método que se ocupa de comprobar si la hora a la que se quiere establecer la actividad es mayor
	 * que la hora a la que se encuentra reservada ya
	 * @param reserva la reserva a comparar
	 * @param hora la hora a la que se quiere reservar
	 * @return true si es mayor, false en caso contrario
	 */
	private boolean compruebaHoras(Reserva reserva, String hora) {
		int horaPanel = Integer.valueOf(((String) getCbxHoraInicio().getSelectedItem()).split(":")[0]);
		int horaReserva = Integer.valueOf(reserva.getDesde().split(":")[0]);
		return horaPanel > horaReserva;
	}
	
	private void mostrarDialogoColision(ArrayList<Reserva> reserva){
		ColisionesPeriodicasDialog vr = new ColisionesPeriodicasDialog(reserva, this);
		vr.setLocationRelativeTo(null);
		vr.setModal(true);
		vr.setVisible(true);
	}
	
	public VentanaCrearActividad getPnAnterior() {
		return this.vCA;
	}
		
	public void setCanceladaTransaccion(boolean cancelada) {
		this.canceladaTransaccion=cancelada;
	}
	
	public void removeArrayColisiones() {
		colisiones = new ArrayList<Reserva>();
	}
}
