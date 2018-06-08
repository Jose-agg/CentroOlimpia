package igu.admin.actividades;

import igu.socio.reservas.PanelMostrarReservasSocios;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import logica.objetos.Actividad;
import logica.objetos.Instalacion;
import logica.objetos.Reserva;
import modelos.ModeloCeldas;
import modelos.ModeloTabla;

import com.toedter.calendar.JDateChooser;

import dataBase.DataBase;
import date.DateComparable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableModel;

public class PanelCrearActividadesVariosDiasSemana extends JPanel {
	
	//Constantes
	public final static int DOMINGO = 1;
	public final static int LUNES = 2;
	public final static int MARTES = 3;
	public final static int MIERCOLES = 4;
	public final static int JUEVES = 5;
	public final static int VIERNES = 6;
	public final static int SABADO = 7;
	
	private JPanel pnCentral;
	private JPanel pnBotones;
	private JPanel pnDias;
	private JPanel pnDatos;
	private JPanel pnTabla;
	private JRadioButton rdbtnLunes;
	private JRadioButton rdbtnMartes;
	private JRadioButton rdbtnMircoles;
	private JRadioButton rdbtnJueves;
	private JRadioButton rdbtnViernes;
	private JRadioButton rdbtnSbado;
	private JRadioButton rdbtnDomingo;
	private JLabel lblInstalacin;
	private JComboBox comboInstalaciones;
	private JLabel lblSeleccioneHoraDe;
	private JComboBox comboHoraInicio;
	private JLabel lblSeleccioneHoraFinal;
	private JComboBox comboHoraFinal;
	private JLabel lblNombreDeLa;
	private JTextField textField;
	private JRadioButton rdbtnNmeroDePlazas;
	private JLabel lblSeleccioneNmeroDe;
	private JPanel pnPlazas;
	private JSpinner spinnerPlazas;
	private JPanel pnDatosActividad;
	private JButton btnAadir;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnBorrarActividadSeleccionada;
	private JLabel lblSeleccioneFechaDe;
	private JDateChooser dateChooser;
	private JLabel lblSeleccioneFechaFinal;
	private JDateChooser dateChooser_1;
	private JButton btnCrearActividad;
	private JButton btnCancelar;
	private JPanel pnFechas;
	private JPanel pnBoton;
	private PanelMostrarActividadesAdministrador pma;
	private ArrayList<String> filasTotales;
	private JPanel panel;
	private JLabel lblCreacinDeActividades;
	private VentanaCrearActividad vac;
	private ModeloTabla model;
	private boolean canceladaTransaccion;
	private ArrayList<Reserva> colisiones;
	
	
	/**
	 * Create the panel.
	 * @param 
	 * @throws SQLException 
	 */
	public PanelCrearActividadesVariosDiasSemana(
			PanelMostrarActividadesAdministrador pMA2,VentanaCrearActividad va) throws SQLException {
		this.colisiones = new ArrayList<Reserva>();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout(0, 0));
		this.add(getPnCentral(), BorderLayout.CENTER);
		add(getPnBotones(), BorderLayout.SOUTH);
		this.pma=pMA2;
		this.vac = va;
	}
	private JPanel getPnCentral() throws SQLException {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setLayout(new BorderLayout(0, 0));
			pnCentral.add(getPnDias(), BorderLayout.NORTH);
			pnCentral.add(getPnTabla(), BorderLayout.SOUTH);
			pnCentral.add(getPnDatos(), BorderLayout.CENTER);
		}
		return pnCentral;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new BorderLayout(0, 0));
			pnBotones.add(getPnFechas());
			pnBotones.add(getPnBoton(), BorderLayout.EAST);
			pnBotones.add(getPanel(), BorderLayout.SOUTH);
		}
		return pnBotones;
	}
	private JPanel getPnDias() {
		if (pnDias == null) {
			pnDias = new JPanel();
			pnDias.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnDias.add(getRdbtnLunes());
			pnDias.add(getRdbtnMartes());
			pnDias.add(getRdbtnMircoles());
			pnDias.add(getRdbtnJueves());
			pnDias.add(getRdbtnViernes());
			pnDias.add(getRdbtnSbado());
			pnDias.add(getRdbtnDomingo());
		}
		return pnDias;
	}
	private JPanel getPnDatos() throws SQLException {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setLayout(new GridLayout(0, 1, 0, 0));
			pnDatos.add(getPnDatosActividad());
		}
		return pnDatos;
	}
	private JPanel getPnTabla() {
		if (pnTabla == null) {
			pnTabla = new JPanel();
			pnTabla.setLayout(new BorderLayout(0, 0));
			pnTabla.add(getScrollPane(), BorderLayout.CENTER);
		}
		return pnTabla;
	}
	private JRadioButton getRdbtnLunes() {
		if (rdbtnLunes == null) {
			rdbtnLunes = new JRadioButton("Lunes");
		}
		return rdbtnLunes;
	}
	private JRadioButton getRdbtnMartes() {
		if (rdbtnMartes == null) {
			rdbtnMartes = new JRadioButton("Martes");

		}
		return rdbtnMartes;
	}
	private JRadioButton getRdbtnMircoles() {
		if (rdbtnMircoles == null) {
			rdbtnMircoles = new JRadioButton("Mi\u00E9rcoles");

		}
		return rdbtnMircoles;
	}
	private JRadioButton getRdbtnJueves() {
		if (rdbtnJueves == null) {
			rdbtnJueves = new JRadioButton("Jueves");

		}
		return rdbtnJueves;
	}
	private JRadioButton getRdbtnViernes() {
		if (rdbtnViernes == null) {
			rdbtnViernes = new JRadioButton("Viernes");

		}
		return rdbtnViernes;
	}
	private JRadioButton getRdbtnSbado() {
		if (rdbtnSbado == null) {
			rdbtnSbado = new JRadioButton("S\u00E1bado");
		}
		return rdbtnSbado;
	}
	private JRadioButton getRdbtnDomingo() {
		if (rdbtnDomingo == null) {
			rdbtnDomingo = new JRadioButton("Domingo");
		}
		return rdbtnDomingo;
	}
	private JLabel getLblInstalacin() {
		if (lblInstalacin == null) {
			lblInstalacin = new JLabel("Seleccione Instalaci\u00F3n:");
			lblInstalacin.setBounds(437, 44, 181, 14);
		}
		return lblInstalacin;
	}
	private JComboBox getComboInstalaciones() throws SQLException {
		if (comboInstalaciones == null) {
			comboInstalaciones = new JComboBox();
			DefaultComboBoxModel<Instalacion> modelo = new DefaultComboBoxModel<Instalacion>();
			for (Instalacion instalacion : DataBase.cargaInstalaciones()) {
				modelo.addElement(instalacion);
				
			}
			comboInstalaciones.setModel(modelo);
			comboInstalaciones.setBounds(583, 41, 167, 20);
		}
		return comboInstalaciones;
	}
	
	
	
	
	
	private JLabel getLblSeleccioneHoraDe() {
		if (lblSeleccioneHoraDe == null) {
			lblSeleccioneHoraDe = new JLabel("Seleccione Hora Inicial:");
			lblSeleccioneHoraDe.setBounds(65, 110, 174, 14);
		}
		return lblSeleccioneHoraDe;
	}
	private JComboBox getComboHoraInicio() {
		if (comboHoraInicio == null) {
			comboHoraInicio = new JComboBox();
			comboHoraInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comboHoraFinal.setEnabled(true);
					rellenarComboHorasFinales();
				}
			});
			String[] horaInicial = pma.HORAS;
			DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
			for (String hora : horaInicial) {
				modelo.addElement(hora);
				
			}
			comboHoraInicio.setModel(modelo);
			comboHoraInicio.setBounds(239, 107, 113, 20);
		}
		return comboHoraInicio;
	}
	
	// JOse
	private void rellenarComboHorasFinales() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		String[] horas = pma.HORAS_TERMINAR;
		boolean var=false;
		int cont=-1;
		for (String string : horas) {
			cont++;
			if(var) modelo.addElement(horas[cont]);
			if(comboHoraInicio.getSelectedItem().equals(string)) var = true;
		}
		comboHoraFinal.setModel(modelo);
	}
	private JLabel getLblSeleccioneHoraFinal() {
		if (lblSeleccioneHoraFinal == null) {
			lblSeleccioneHoraFinal = new JLabel("Seleccione Hora Final:");
			lblSeleccioneHoraFinal.setBounds(437, 110, 128, 14);
		}
		return lblSeleccioneHoraFinal;
	}
	private JComboBox getComboHoraFinal() {
		if (comboHoraFinal == null) {
			comboHoraFinal = new JComboBox();
			String[] horaInicial = pma.HORAS_TERMINAR;
			DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
			for (String hora : horaInicial) {
				modelo.addElement(hora);
				
			}
			comboHoraFinal.setModel(modelo);
			comboHoraFinal.setBounds(583, 107, 167, 20);
		}
		return comboHoraFinal;
	}
	private JLabel getLblNombreDeLa() {
		if (lblNombreDeLa == null) {
			lblNombreDeLa = new JLabel("Nombre de la Actividad:");
			lblNombreDeLa.setBounds(65, 44, 140, 14);
		}
		return lblNombreDeLa;
	}
	public JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(239, 41, 113, 20);
			textField.setColumns(10);
		}
		return textField;
	}
	private JRadioButton getRdbtnNmeroDePlazas() {
		if (rdbtnNmeroDePlazas == null) {
			rdbtnNmeroDePlazas = new JRadioButton("N\u00FAmero de Plazas");
			rdbtnNmeroDePlazas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(rdbtnNmeroDePlazas.isSelected()){
					spinnerPlazas.setEnabled(true);
					}else{
						spinnerPlazas.setEnabled(false);
					}
				
				}
			});
			rdbtnNmeroDePlazas.setBounds(32, 19, 128, 23);
		}
		return rdbtnNmeroDePlazas;
	}
	private JLabel getLblSeleccioneNmeroDe() {
		if (lblSeleccioneNmeroDe == null) {
			lblSeleccioneNmeroDe = new JLabel("Seleccione N\u00FAmero de Plazas:");
			lblSeleccioneNmeroDe.setBounds(42, 49, 193, 14);
		}
		return lblSeleccioneNmeroDe;
	}
	private JPanel getPnPlazas() {
		if (pnPlazas == null) {
			pnPlazas = new JPanel();
			pnPlazas.setBounds(10, 192, 407, 79);
			pnPlazas.setForeground(Color.GRAY);
			pnPlazas.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Plazas Actividad", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
			pnPlazas.setLayout(null);
			pnPlazas.add(getRdbtnNmeroDePlazas());
			pnPlazas.add(getLblSeleccioneNmeroDe());
			pnPlazas.add(getSpinnerPlazas());
		}
		return pnPlazas;
	}
	private JSpinner getSpinnerPlazas() {
		if (spinnerPlazas == null) {
			spinnerPlazas = new JSpinner();
			spinnerPlazas.setEnabled(false);
			spinnerPlazas.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			spinnerPlazas.setBounds(258, 46, 59, 20);
		}
		return spinnerPlazas;
	}
	private JPanel getPnDatosActividad() throws SQLException {
		if (pnDatosActividad == null) {
			pnDatosActividad = new JPanel();
			pnDatosActividad.setForeground(Color.GRAY);
			pnDatosActividad.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos Actividad", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnDatosActividad.setLayout(null);
			pnDatosActividad.add(getTextField());
			pnDatosActividad.add(getLblNombreDeLa());
			pnDatosActividad.add(getComboHoraInicio());
			pnDatosActividad.add(getLblSeleccioneHoraDe());
			pnDatosActividad.add(getComboInstalaciones());
			pnDatosActividad.add(getLblInstalacin());
			pnDatosActividad.add(getLblSeleccioneHoraFinal());
			pnDatosActividad.add(getComboHoraFinal());
			pnDatosActividad.add(getPnPlazas());
			pnDatosActividad.add(getBtnAadir());
			pnDatosActividad.add(getBtnBorrarActividadSeleccionada());
			
			JPanel pnPeriodicidad = new JPanel();
			pnPeriodicidad.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Periodicidad de la Actividad", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
			pnPeriodicidad.setBounds(437, 192, 355, 113);
			pnDatosActividad.add(pnPeriodicidad);
			pnPeriodicidad.setLayout(null);
			pnPeriodicidad.add(getDateChooser());
			pnPeriodicidad.add(getLblSeleccioneFechaDe());
			pnPeriodicidad.add(getLblSeleccioneFechaFinal());
			pnPeriodicidad.add(getDateChooser_1());
		}
		return pnDatosActividad;
	}
	private JButton getBtnAadir() {
		if (btnAadir == null) {
			btnAadir = new JButton("A\u00F1adir a la Lista");
			btnAadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				
					ArrayList<String> diasSeleccionados = new ArrayList<String>();
					Instalacion i = (Instalacion) comboInstalaciones.getSelectedItem();
					String horaInicial = (String) comboHoraInicio.getSelectedItem();
					String horaFinal = (String) comboHoraFinal.getSelectedItem();
					String numPlazas = "Ilimitadas";
					if(!textField.getText().isEmpty()){
						textField.setEditable(false);
						if(rdbtnNmeroDePlazas.isSelected()){
							 numPlazas = String.valueOf(spinnerPlazas.getValue());
						}
						
						
						if (!rdbtnLunes.isSelected() && !rdbtnMartes.isSelected()
								&& !rdbtnMircoles.isSelected()
								&& !rdbtnJueves.isSelected()
								&& !rdbtnViernes.isSelected()
								&& !rdbtnSbado.isSelected()
								&& !rdbtnDomingo.isSelected()) {
							JOptionPane.showMessageDialog(pnCentral,
									"Seleccione un día.");
						} else {
						if(rdbtnLunes.isSelected()){
							diasSeleccionados.add("L");
						}if(rdbtnMartes.isSelected()){
							diasSeleccionados.add("M");
						}if(rdbtnMircoles.isSelected()){
							diasSeleccionados.add("X");
						}if(rdbtnJueves.isSelected()){
							diasSeleccionados.add("J");
						}if(rdbtnViernes.isSelected()){
							diasSeleccionados.add("V");
						}if(rdbtnSbado.isSelected()){
							diasSeleccionados.add("S");
						}if(rdbtnDomingo.isSelected()){
							diasSeleccionados.add("D");
						}
						
						 if( dateChooser.getDate()==null || dateChooser_1.getDate()== null){
								JOptionPane.showMessageDialog(pnCentral, "Seleccione una fecha.");
						}else{
							
							Date di =sumarRestarHorasFecha(new Date(),1);
							String si = String.valueOf(di.getHours())+":00";
							if(pma.getVentanaPrincipal().getPrograma().comprobarHoras(String.valueOf(comboHoraInicio.getSelectedItem()),si)&& obtenerFecha().equals(formatearFechaActual())){
								JOptionPane.showMessageDialog(pnCentral, "Hora pasada.");
							
							}else{
						if(!comprobacionesTabla(horaInicial, horaFinal,diasSeleccionados)){
							JOptionPane.showMessageDialog(pnCentral, "Ya ha seleccionado ese día para esas horas.");
						}else{
							dateChooser.setEnabled(false);
							dateChooser_1.setEnabled(false);
						mostrarTabla(diasSeleccionados, i, horaInicial, horaFinal,  numPlazas);
						}
						}
						}
						}
					
					}else{
						JOptionPane.showMessageDialog(pnCentral, "Ingrese un nombre de actividad.");
					}
				}
					
			});
			btnAadir.setBounds(10, 282, 181, 23);
		}
		return btnAadir;
	}
	
	private String formatearFechaActual() {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String folderName = formatter.format(today);
		return folderName;
	}
	protected String obtenerFecha(){
	    Date hoy = dateChooser.getDate();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String fecha = formatter.format(hoy);
	    return fecha;
	}
	
	private boolean comprobacionesTabla(String horaInicial,String horaFinal, ArrayList<String> diasSeleccionados){
		for (int j = 0; j < table.getRowCount(); j++) {
			for (int i = 0; i < diasSeleccionados.size(); i++) {
				
				String[] d = String.valueOf(table.getValueAt(j, 0)).split(",");
				if(d.length>1){
				for (int k = 0; k < d.length; k++) {
//					System.out.println("INSTA");
//					System.out.println(String.valueOf(comboInstalaciones.getSelectedItem()));
//					System.out.println(String.valueOf(table.getRowCount()));
//					System.out.println(String.valueOf(table.getColumnCount()));
//					System.out.println("-----------------------------------------------------------");
					if(diasSeleccionados.get(i).equals(d[k])&& String.valueOf(comboInstalaciones.getSelectedItem()).equals(String.valueOf(table.getValueAt(j, 1)))){
//						System.out.println("ENTRA 1");
//						System.out.println(horaInicial);
//						System.out.println(horaFinal);
//						System.out.println(String.valueOf(table.getValueAt(j, 2)));
//						System.out.println(String.valueOf(table.getValueAt(j, 3)));
//						System.out.println(	pma.getVentanaPrincipal().getPrograma().validarHora(String.valueOf(table.getValueAt(j, 2)),String.valueOf(table.getValueAt(j, 3)), horaInicial, horaFinal));
						return pma.getVentanaPrincipal().getPrograma().validarHora(String.valueOf(table.getValueAt(j, 2)),String.valueOf(table.getValueAt(j, 3)), horaInicial, horaFinal);
					}
				}
				}else{
					if(diasSeleccionados.get(i).equals(d[0])&& String.valueOf(comboInstalaciones.getSelectedItem()).equals(String.valueOf(table.getValueAt(j, 1)))){
						//System.out.println("ENTRA 2");
						return pma.getVentanaPrincipal().getPrograma().validarHora(String.valueOf(table.getValueAt(j, 2)),String.valueOf(table.getValueAt(j, 3)), horaInicial, horaFinal);
					}
				}
				}
		
			}
			return true;
		
	}
	
	 public Date sumarRestarHorasFecha(Date fecha, int horas){
	       Calendar calendar = Calendar.getInstance();
	       calendar.setTime(fecha); // Configuramos la fecha que se recibe
	       calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
	       return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
	  
}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
			scrollPane.setPreferredSize(new Dimension(150, 170));
		}
		return scrollPane;
	}
	public JTable getTable() {
		if (table == null) {
			table = new JTable();
			model = new ModeloTabla(new String [] {"Días de la Semana","Instalación","Hora Inicial","Hora Final","Número de Plazas"},0);
			table.setModel(model);
			table.getTableHeader().setReorderingAllowed(false);
		}
		return table;
	}
	public ModeloTabla getModel() {
		return this.model;
	}
	private JButton getBtnBorrarActividadSeleccionada() {
		if (btnBorrarActividadSeleccionada == null) {
			btnBorrarActividadSeleccionada = new JButton("Borrar Actividad Seleccionada");
			btnBorrarActividadSeleccionada.setBounds(201, 282, 216, 23);
			btnBorrarActividadSeleccionada.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(table.getModel().getRowCount()>0){
						
						if(table.getSelectedRows().length>0){
					removeSelectedRows(table);
					filas();
					if(table.getRowCount()==0){
						dateChooser.setEnabled(true);
						dateChooser_1.setEnabled(true);
					
					}
						}else{
							JOptionPane.showMessageDialog(pnCentral, "Seleccione uno o varios elementos");
						}
					}else{
						dateChooser.setEnabled(true);
						dateChooser_1.setEnabled(true);
						JOptionPane.showMessageDialog(pnCentral, "La tabla esta vacía");
					}
					
						
					
				
				}
			});
		}
		return btnBorrarActividadSeleccionada;
	}
	
	public void removeSelectedRows(JTable table){
		   ModeloTabla model = (ModeloTabla) this.table.getModel();
		   int[] rows = table.getSelectedRows();
		   for(int i=0;i<rows.length;i++){
		     model.removeRow(rows[i]-i);
		   }
		 
		  
		}
	
	
	private JLabel getLblSeleccioneFechaDe() {
		if (lblSeleccioneFechaDe == null) {
			lblSeleccioneFechaDe = new JLabel("Seleccione Fecha Inicial:");
			lblSeleccioneFechaDe.setBounds(23, 38, 167, 14);
		}
		return lblSeleccioneFechaDe;
	}
	private JDateChooser getDateChooser() {
		if (dateChooser == null) {
			dateChooser = new JDateChooser();
			dateChooser.setBounds(189, 38, 95, 20);
			dateChooser.setVerifyInputWhenFocusTarget(false);
			dateChooser.setDateFormatString("dd/MM/yyyy");
			dateChooser.setMinSelectableDate(new Date());
			
			BorderLayout borderLayout = (BorderLayout) dateChooser.getLayout();
			
		}
		return dateChooser;
	}
	private JLabel getLblSeleccioneFechaFinal() {
		if (lblSeleccioneFechaFinal == null) {
			lblSeleccioneFechaFinal = new JLabel("Seleccione Fecha Final:");
			lblSeleccioneFechaFinal.setBounds(26, 71, 153, 14);
		}
		return lblSeleccioneFechaFinal;
	}
	private JDateChooser getDateChooser_1() {
		if (dateChooser_1 == null) {
			dateChooser_1 = new JDateChooser();
			dateChooser_1.setBounds(189, 65, 95, 20);
			dateChooser_1.setVerifyInputWhenFocusTarget(false);
			dateChooser_1.setDateFormatString("dd/MM/yyyy");
			dateChooser_1.setMinSelectableDate(new Date());
		}
		return dateChooser_1;
	}
	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("Crear Actividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(!textField.getText().isEmpty()){
						if(table.getModel().getRowCount()>0){
					String fechaInicial = formatearFecha(dateChooser.getDate());
					String fechaFinal = formatearFecha(dateChooser_1.getDate());
					String codigo = existsActividades();
					
					if (codigo.equals("-1")) {
						textField.setEditable(true);
					} else {
						procesaTabla();
						if (colisiones.size()>0) {
							ordenarColisiones();
							mostrarDialogoColision(colisiones);
							if (!canceladaTransaccion) {
								administrarCodigo(fechaInicial, fechaFinal, codigo);
							}
						} else {
							administrarCodigo(fechaInicial, fechaFinal, codigo);
						}
					}
						}else{
							JOptionPane.showMessageDialog(pnCentral,"Añada una actividad a la tabla.");
						}
				}else{
					JOptionPane.showMessageDialog(pnCentral,"Ingrese un nombre de actividad.");
				
				}
				}
			});
		}
		return btnCrearActividad;
	}
	
	private void administrarCodigo(String fechaInicial, String fechaFinal, String codigo) {	
		crearActividadesTabla(fechaInicial, fechaFinal, ((codigo.equals("-2") ? null : codigo)));
		int res = JOptionPane.showConfirmDialog(pnCentral,
				"Se ha creado la actividad. ¿Desea crear más actividades?", "Aceptar", JOptionPane.YES_NO_OPTION);
		if (res == 0) {
			borrarTodo();
		} else {
			borrarTodo();
			vac.dispose();
		}
	}
	
	private void borrarTodo(){
		textField.setText("");
		pma.getVentanaPrincipal().getPrograma().setCodA(null);
		pma.getVentanaPrincipal().getPrograma().setConta(0);
		textField.setEditable(true);
		filasTotales.removeAll(filasTotales);
		spinnerPlazas.setValue(1);
		rdbtnNmeroDePlazas.setSelected(false);
		rdbtnLunes.setSelected(false);
		rdbtnMartes.setSelected(false);
		rdbtnMircoles.setSelected(false);
		rdbtnJueves.setSelected(false);
		rdbtnViernes.setSelected(false);
		rdbtnSbado.setSelected(false);
		rdbtnDomingo.setSelected(false);
		dateChooser.setDate(new Date());
		dateChooser_1.setDate(new Date());
		table.setModel(new ModeloTabla(new String [] {"Días de la Semana","Instalación","Hora Inicial","Hora Final","Número de Plazas"},0));
		colisiones = new ArrayList<Reserva>();
		dateChooser.setEnabled(true);
		dateChooser_1.setEnabled(true);
	}
	

	
	protected void crearActividad(String fechaInicial, String fechaFinal, String[] dias, String codigo,String hI,String hF,String insta,int nP,int cont) {
		 try {
			
//			 System.out.println(fechaInicial);
//			 System.out.println(fechaFinal);
//			 System.out.println(getTextField().getText());
//			 System.out.println(nP);
//			 System.out.println(insta);
//			 System.out.println(pma.getVentanaPrincipal().getPrograma()
//										.codInsta(insta));
//			 System.out.println(hF);
//			 System.out.println(hI);
//			 System.out.println(dias);
//			 System.out.println(codigo);
			if (getRdbtnNmeroDePlazas().isSelected())
				pma.getVentanaPrincipal()
						.getPrograma()
						.crearActividadPeriodicaVariosDias(
								fechaInicial,
								fechaFinal,
								getTextField().getText(),
								nP,
								pma.getVentanaPrincipal().getPrograma()
										.codInsta(insta), hF, hI, dias, codigo,cont);
			else
				pma.getVentanaPrincipal()
						.getPrograma()
						.crearActividadPeriodicaVariosDias(
								fechaInicial,
								fechaFinal,
								getTextField().getText(),
								-1,
								pma.getVentanaPrincipal().getPrograma()
										.codInsta(insta), hF, hI, dias, codigo,cont);

		 }catch (SQLException e) {
			 e.printStackTrace();
		 }
	 }
	protected String comprobarNombre() {
		try {
			return pma.getVentanaPrincipal().getPrograma().comprobarActividad(getTextField().getText(),formatearFecha(getDateChooser().getDate()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-1";
	}
	
	 private String formatearFecha(Date t) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String folderName = formatter.format(t);
			return folderName;
		}
	
	
	
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					vac.dispose();
				}
			});
		}
		return btnCancelar;
	}
	private JPanel getPnFechas() {
		if (pnFechas == null) {
			pnFechas = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnFechas.getLayout();
		}
		return pnFechas;
	}
	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.add(getBtnCrearActividad());
			pnBoton.add(getBtnCancelar());
		}
		return pnBoton;
	}
	
	private void mostrarTabla(ArrayList<String> dias, Instalacion ins, String horaI,String horaF,String numPlazas){
		ModeloTabla model = (ModeloTabla) table.getModel();
		String diasS = "";
		for (int i = 0; i < dias.size(); i++) {
			diasS+=dias.get(i);
			if(i!=dias.size()-1){
				diasS+=","; 
			}
		}
		//System.out.println(comprobarNoExistaTabla("", ins.getInombre(), horaI, horaF, numPlazas));
		if(comprobarNoExistaTabla(diasS, ins.getInombre(), horaI, horaF, numPlazas).equals("Ya existe")){
			JOptionPane.showMessageDialog(pnCentral, "Ya ha insertado esos datos.");
		}else{
		model.addRow(new Object[]{diasS,ins.getInombre(),horaI,horaF,numPlazas});
		}
		table.setModel(model);
	
	}

	private String comprobarNoExistaTabla(String dias, String ins, String horaI,String horaF,String numPlazas){
		String cadena = dias+"-"+ins+"-"+horaI+"-"+horaF+"-"+numPlazas+"-";
		filas();
		for (String string : filasTotales) {
			//System.out.println(string.trim());
			if(string.trim().equals(cadena)){
				return "Ya existe";
			}
		}
		return "No existe";
	}
	
	
	private void filas(){
		String valor= "";
		filasTotales = new ArrayList<String>();
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				//System.out.print(table.getValueAt(i, j)+"-");
				valor+=table.getValueAt(i, j)+"-";
			}
			valor+="\n";
			//System.out.println();
			//System.out.print("1" + valor);
		
		}
		filasTotales.add(valor);
		
	}
	
	public void crearActividadesTabla(String fechaI, String fechaF, String codigo) {
	
		for (int i = 0; i < table.getRowCount(); i++) {
				String s = (String) table.getValueAt(i, 0);
					
					String[] dias = s.split(",");
					
						 if(getRdbtnNmeroDePlazas().isSelected() && !String.valueOf(table.getValueAt(i, 4)).equals("Ilimitadas")){
							 
							crearActividad(fechaI, fechaF, dias,codigo, String.valueOf( table.getValueAt(i, 2)),  String.valueOf(table.getValueAt(i, 3)),  String.valueOf(table.getValueAt(i, 1)), Integer.parseInt((String) table.getValueAt(i, 4)),i);
						   }else{
							  // System.out.println(codigo);
								crearActividad(fechaI, fechaF, dias,codigo, String.valueOf( table.getValueAt(i, 2)),  String.valueOf(table.getValueAt(i, 3)),  String.valueOf(table.getValueAt(i, 1)), -1,i);
 
						   }
						}
				}
				
		
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel.add(getLblCreacinDeActividades());
		}
		return panel;
	}
	private JLabel getLblCreacinDeActividades() {
		if (lblCreacinDeActividades == null) {
			lblCreacinDeActividades = new JLabel("    Creaci\u00F3n de Actividades  \r\n");
			lblCreacinDeActividades.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblCreacinDeActividades;
	}
	
	//-------------------------------------------------------------------------------------PELAYO COMPROBACIONES--------------------------------------------------------------------
	
	private void procesaTabla() {
		int nfilas = model.getRowCount();
		for (int i=0; i<nfilas; i++) {
			String cadena = (String) model.getValueAt(i, 0);
			String[] dias = cadena.split(",");
			String horaInicio = (String) model.getValueAt(i, 2);
			String horaFinal = (String) model.getValueAt(i, 3);
			for (int j=0; j<dias.length; j++) {
				compruebaColisiones(getDayOfWeek(dias[j]), horaInicio, horaFinal);
			}
		}
	}
	
	private void compruebaColisiones(int dayOfWeek, String horaInicio, String horaFinal){
		Calendar finalDate = getFinalDay(dayOfWeek);
		Calendar nextDate = getStartDay(dayOfWeek);
		ArrayList<Reserva> reservas = null;
		do {
			reservas = getReservas(nextDate, horaInicio, horaFinal);
			if (reservas!=null) {
				for (Reserva reserva : reservas)
					colisiones.add(reserva);
			}
			nextDate = getNextDay(nextDate, finalDate);
		} while (esMayor(nextDate, finalDate)>=0);
	}
	
	private ArrayList<Reserva> getReservas(Calendar date, String horaInicio, String horaFinal){
		int numberOfDay = date.get(Calendar.DAY_OF_MONTH);
		String day = (numberOfDay<10) ? "0"+numberOfDay : ""+numberOfDay;
		String month = ""+(date.get(Calendar.MONTH)+1);
		String fecha = day+"/"+month+"/"+date.get(Calendar.YEAR);
		
		Instalacion instalacion = null;
		ArrayList<Reserva> reservas = null;
		try {
			instalacion = (Instalacion)getComboInstalaciones().getSelectedItem();
			reservas = DataBase.getReservasInterseccion(fecha, horaInicio, horaFinal, instalacion.getCodInstalacion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reservas;
	}
	
	
	/**
	 * Metodo que devuelve la fecha en la semana siguiente de una fecha de inicio
	 * si esta es menor que la fecha final.
	 * @param startDate la fecha de comienzo
	 * @param finalDate la fecha final
	 * @return la nueva fecha
	 */
	private Calendar getNextDay(Calendar startDate, Calendar finalDate){
		Calendar nextDate = Calendar.getInstance();
		int day = startDate.get(Calendar.DAY_OF_MONTH);
		int month = startDate.get(Calendar.MONTH);
		int year = startDate.get(Calendar.YEAR);
		nextDate.set(year, month, day);
		nextDate.set(Calendar.DAY_OF_MONTH, nextDate.get(Calendar.DAY_OF_MONTH)+7);
		return nextDate;
	}
	
	private int esMayor(Calendar firstDate, Calendar finalDate){
		DateComparable startDate = new DateComparable(firstDate.get(Calendar.DAY_OF_MONTH), firstDate.get(Calendar.MONTH)+1, firstDate.get(Calendar.YEAR));
		return startDate.compareDates(finalDate.get(Calendar.YEAR), finalDate.get(Calendar.MONTH)+1, finalDate.get(Calendar.DAY_OF_MONTH));
	}
	
	
	private Calendar getFinalDay(int dayOfWeekToAchieve) {
		
		Calendar c = DateComparable.getFechaSeleccionada(obtenerFechaFinal());
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		
		while(dayOfWeek!=dayOfWeekToAchieve) {
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-1);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		}
		return c;
	}
	
	private Calendar getStartDay(int dayOfWeekToAchieve) {
	
		Calendar c = DateComparable.getFechaSeleccionada(obtenerFecha());
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		
		while(dayOfWeek!=dayOfWeekToAchieve) {
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		}
		return c;
	}
	
	/**
	 * Metodo que comprueba que no existen actividades del mismo nombre previas al introducido, o que si existen,
	 * no tienen eventos futuros.
	 * @return true si existe la actividad o eventos, false en caso contrario.
	 */
	private String existsActividades() {
		Actividad actividad = null;
		try {
			actividad = DataBase.getActividad(getTextField().getText());
		} catch (SQLException e) { e.printStackTrace(); }
		
		if (actividad!=null) {
			int day = Integer.valueOf(obtenerFecha().split("/")[0]);
			int month = Integer.valueOf(obtenerFecha().split("/")[1]);
			int year = Integer.valueOf(obtenerFecha().split("/")[2]);
			if (reservasAnterioresAFecha(actividad.getCodActividad(), day, month, year)) {
				return actividad.getCodActividad();
			} else {
				JOptionPane.showMessageDialog(this, "Existen eventos pendientes para la actividad " + actividad.getNombreA()
				+ ". Por favor, introduzca otro nombre para la actividad.");
				return "-1";
			}
		} else {
			return "-2";
		}
	}
	
	/**
	 * Metodo que comprueba que las reservas de una determinada actividad son anteriores al dia de hoy.
	 * Esto sera util para saber si una determinada Actividad puede volver a generarse o no.
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
					valido = compruebaHoras(reserva, (String) getComboHoraInicio().getSelectedItem());
				} else valido = false;
			}
		}
		return valido;
	}
	
	/**
	 * Metodo que se ocupa de comprobar si la hora a la que se quiere establecer la actividad es mayor
	 * que la hora a la que se encuentra reservada ya
	 * @param reserva la reserva a comparar
	 * @param hora la hora a la que se quiere reservar
	 * @return true si es mayor, false en caso contrario
	 */
	private boolean compruebaHoras(Reserva reserva, String hora) {
		int horaPanel = Integer.valueOf(((String) getComboHoraInicio().getSelectedItem()).split(":")[0]);
		int horaReserva = Integer.valueOf(reserva.getDesde().split(":")[0]);
		return horaPanel > horaReserva;
	}
	
	protected String obtenerFechaFinal(){
	    Date hoy = dateChooser_1.getDate();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String fecha = formatter.format(hoy);
	    return fecha;
	}
	
	private void mostrarDialogoColision(ArrayList<Reserva> reservas){
		ColisionesPeriodicasDialog vr = new ColisionesPeriodicasDialog(reservas, this);
		vr.setLocationRelativeTo(null);
		vr.setModal(true);
		vr.setVisible(true);
	}
	
	public PanelMostrarActividadesAdministrador getPMA(){
		return this.pma;
	}
	
	public void setCanceladaTransaccion(boolean cancelada) {
		this.canceladaTransaccion=cancelada;
	}
	
	private int getDayOfWeek(String day) {
		switch (day) {
			case "L":
				return LUNES;
			case "M":
				return MARTES;
			case "X":
				return MIERCOLES;
			case "J":
				return JUEVES;
			case "V":
				return VIERNES;
			case "S":
				return SABADO;
			case "D":
				return DOMINGO;
			default:
				return -1;
		}
	}
	
	public void removeArrayColisiones() {
		colisiones = new ArrayList<Reserva>();
	}
	
	public void ordenarColisiones() {
		ArrayList<Reserva> newColisiones = new ArrayList<Reserva>();
		for (int i=1; i<8; i++) {
			for (int j=0; j<colisiones.size(); j++) {
				Calendar c = DateComparable.getFechaSeleccionada(colisiones.get(j).getFechaReserva());
				if(i==7) {
					if (c.get(Calendar.DAY_OF_WEEK)==1)
						newColisiones.add(colisiones.get(j));
				} else {
					if (c.get(Calendar.DAY_OF_WEEK)==i+1) {
						newColisiones.add(colisiones.get(j));
					}
				}
			}
		}
		this.colisiones=newColisiones;
	}
	
}
