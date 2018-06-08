package igu.monitor;

import javax.swing.JPanel;

import dataBase.DataBase;
import date.DateComparable;
import igu.main.VentanaPrincipal;
import logica.objetos.Actividad;
import logica.objetos.Evento;
import logica.objetos.Reserva;
import logica.objetos.Usuario;
import modelos.ModeloTabla;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

public class PanelMonitor extends JPanel {
	//Constantes
	public final static String APUNTADO = "Apuntado";
	public final static String CANCELADO = "Cancelado";
	public final static String ASISTIDO = "Asistió";
	public final static String FALTADO = "Faltó";								

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VentanaPrincipal vPrincipal;
	private JPanel pnEncabezado;
	private JPanel pnMenu;
	private JLabel lblActividad;
	private JComboBox<String> cbxActividad;
	private JPanel pnActividades;
	private JPanel pnFecha;
	private JLabel lblFecha;
	private JPanel pnEventos;
	private JLabel lblClases;
	private JComboBox<String> cbxEventos;
	private JPanel pnCentral;
	private JScrollPane scrllTabla;
	private JTable tablaSocios;
	private Evento eventoSeleccionado;
	private JLabel lblPlazas;
	private JTextField txfPlazas;
	private JLabel lblPlazasOcupadas;
	private JTextField txfPlazasOcupadas;
	private ArrayList<Evento> eventos;
	private ArrayList<Usuario> apuntados;
	private ModeloTabla modeloTabla;
	private JPanel pnBotones;
	private JButton btnAsistido;
	private JButton btnNoAsistido;
	private JButton btnNuevoSocio;
	private JPanel pnSur;
	private JLabel lblInstalaciones;
	private JComboBox<String> cbxInstalaciones;
	private JButton btnBuscar;
	private VentanaNuevoSocioMonitor vNSM;

	/**
	 * Create the panel.
	 */
	public PanelMonitor(VentanaPrincipal vPrincipal) {
		this.vPrincipal = vPrincipal;
		setBounds(100, 100, 925, 573);
		setLayout(new BorderLayout(0, 0));
		add(getPnEncabezado(), BorderLayout.NORTH);
		add(getPnCentral(), BorderLayout.CENTER);
		add(getPnSur(), BorderLayout.SOUTH);
	}

	private JPanel getPnEncabezado() {
		if (pnEncabezado == null) {
			pnEncabezado = new JPanel();
			pnEncabezado.setLayout(new BorderLayout(0, 0));
			pnEncabezado.add(getPnMenu(), BorderLayout.NORTH);
		}
		return pnEncabezado;
	}
	private JPanel getPnMenu() {
		if (pnMenu == null) {
			pnMenu = new JPanel();
			pnMenu.setLayout(new BorderLayout(0, 0));
			pnMenu.add(getPnActividades(), BorderLayout.WEST);
			pnMenu.add(getBtnBuscar(), BorderLayout.EAST);
		}
		return pnMenu;
	}
	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("Seleccione una Actividad:");
		}
		return lblActividad;
	}
	private JComboBox<String> getCbxActividad() {
		if (cbxActividad == null) {
			cbxActividad = new JComboBox<String>();
			cbxActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actualizarEventos();
				}
			});
			cbxActividad.setModel(new DefaultComboBoxModel<String>(obtenerActividades()));
		}
		return cbxActividad;
	}
	
	private String[] obtenerActividades() {
		ArrayList<Actividad> actividades = null;
		try {
			actividades = DataBase.getActividades();
		} catch (SQLException e) { e.printStackTrace(); }
		String[] nombresActividades = new String[actividades.size()];
		for (int i=0; i<actividades.size(); i++) {
			nombresActividades[i]=actividades.get(i).getNombreA();
		}
		return nombresActividades;
		
	}
	private JPanel getPnActividades() {
		if (pnActividades == null) {
			pnActividades = new JPanel();
			pnActividades.add(getLblActividad());
			pnActividades.add(getCbxActividad());
			pnActividades.add(getLblClases());
			pnActividades.add(getCbxEventos());
			pnActividades.add(getLblInstalaciones());
			pnActividades.add(getCbxInstalaciones());
		}
		return pnActividades;
	}
	private JPanel getPnFecha() {
		if (pnFecha == null) {
			pnFecha = new JPanel();
			pnFecha.add(getLblFecha());
		}
		return pnFecha;
	}
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha: " + DateComparable.getActualDate());
			lblFecha.setFont(new Font("Tahoma", Font.BOLD, 12));
		}
		return lblFecha;
	}
	private JPanel getPnEventos() {
		if (pnEventos == null) {
			pnEventos = new JPanel();
			pnEventos.add(getLblPlazas());
			pnEventos.add(getTxfPlazas());
			pnEventos.add(getLblPlazasOcupadas());
			pnEventos.add(getTxfPlazasOcupadas());
		}
		return pnEventos;
	}
	private JLabel getLblClases() {
		if (lblClases == null) {
			lblClases = new JLabel("Clases disponibles para hoy:");
		}
		return lblClases;
	}
	private JComboBox<String> getCbxEventos() {
		if (cbxEventos == null) {
			cbxEventos = new JComboBox<String>();
			cbxEventos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actualizarInstalaciones();
				}
			});
			actualizarEventos();
		}
		return cbxEventos;
	}
	
	private void actualizarEventos() {
		String[] eventos = obtenerEventos();
		getCbxEventos().setModel(new DefaultComboBoxModel<String>(eventos));
		boolean vacio = eventos.length==0;
		getCbxEventos().setEnabled(!vacio);
		getCbxInstalaciones().setEnabled(!vacio);
		getBtnBuscar().setEnabled(!vacio);
		getTxfPlazas().setText("0");
		getTxfPlazasOcupadas().setText("0");
		if (vacio)
			borrarContenidoTabla();
	}
	
	private void actualizarInstalaciones() {
		ArrayList<String> inst = null;
		try {
			inst = DataBase.getInstalacionEvento((String)getCbxActividad().getSelectedItem(), DateComparable.getActualDate(), (String)getCbxEventos().getSelectedItem());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] instalaciones = new String[inst.size()];
		for (int i=0; i<inst.size(); i++)
			instalaciones[i]=inst.get(i);
		getCbxInstalaciones().setModel(new DefaultComboBoxModel<String>(instalaciones));
		
	}
	
	public void actualizarPlazas() {
		if (eventoSeleccionado != null) {
			getTxfPlazas().setText(String.valueOf(eventoSeleccionado.getPlazas()));
			try {
				getTxfPlazasOcupadas().setText(
						String.valueOf(DataBase.countPlazasOcupadas(eventoSeleccionado.getActividad().getCodActividad(),
								DateComparable.getActualDate(), (String) getCbxEventos().getSelectedItem(), eventoSeleccionado.getCodInstalacion())));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			;
		} else {
			getTxfPlazas().setText("0");
			getTxfPlazasOcupadas().setText("0");
		}
	}
	
	private String[] obtenerEventos() {
		try {
			eventos = DataBase.getEventos((String) getCbxActividad().getSelectedItem(), DateComparable.getActualDate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<String> horasSinRepetir = obtenerTamaño();
		String[] horasEventos = new String[horasSinRepetir.size()];
		if (eventos.size() > 0) {
			for (int i = 0; i < horasSinRepetir.size(); i++) {
				horasEventos[i] = horasSinRepetir.get(i);
			}
		}
		return horasEventos;
	}
	
	private ArrayList<String> obtenerTamaño() {
		ArrayList<String> horas = new ArrayList<String>();
		if (eventos.size() > 0) {
			for (int i=0; i<eventos.size(); i++) {
				if (!horas.contains(eventos.get(i).getHoraInicio())) {
					horas.add(eventos.get(i).getHoraInicio());
				}
			}
		}
		return horas;
	}
	
	
	private void setEventoSeleccionado() {
		String codInstalacion = "";
		try {
			codInstalacion = DataBase.getCodInstalacion((String) getCbxInstalaciones().getSelectedItem());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i=0; i<eventos.size(); i++) {
			Evento evento = eventos.get(i);
			if (evento.getActividad().getNombreA().equals(getCbxActividad().getSelectedItem()) && 
					evento.getHoraInicio().equals(getCbxEventos().getSelectedItem()) && 
					evento.getCodInstalacion().equals(codInstalacion)){
				eventoSeleccionado = evento;	
			}
		}
	}
	
	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setLayout(new BorderLayout(0, 0));
			pnCentral.add(getScrllTabla(), BorderLayout.CENTER);
		}
		return pnCentral;
	}
	private JScrollPane getScrllTabla() {
		if (scrllTabla == null) {
			scrllTabla = new JScrollPane();
			scrllTabla.setViewportView(getTablaSocios());
		}
		return scrllTabla;
	}
	private JTable getTablaSocios() {
		if (tablaSocios == null) {
			String[] nombreColumnas = {"Codigo de Socio", "Nombre", "Apellidos", "DNI", "Estado"};
			modeloTabla = new ModeloTabla(nombreColumnas, 0);
			tablaSocios = new JTable(modeloTabla);
			tablaSocios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tablaSocios.getTableHeader().setReorderingAllowed(false);
		}
		return tablaSocios;
	}
	
	public void actualizarTabla() throws SQLException { 
		if (eventoSeleccionado != null) {
			borrarContenidoTabla();
			apuntados = DataBase.getApuntados(eventoSeleccionado.getActividad().getCodActividad(),
					eventoSeleccionado.getCodInstalacion(), eventoSeleccionado.getFecha(),
					eventoSeleccionado.getHoraInicio());
			Object[] nuevaFila = new Object[8];
			for (Usuario usuario : apuntados) {
				nuevaFila[0] = usuario.getCodUsuario();
				nuevaFila[1] = usuario.getuNombre();
				nuevaFila[2] = usuario.getApellido();
				nuevaFila[3] = usuario.getDni();
				nuevaFila[4] = getEstado(usuario.getCodUsuario());
				modeloTabla.addRow(nuevaFila);
			}
		}
	}
	
	private String getEstado(String codUser) throws SQLException {
		String s = vPrincipal.getPrograma().getEstadoSocioActividad(codUser, getComboActividad(),getInstalacionActividad(),getFechaActual(),getHoraInicio());
		switch(s) {
		case "true":
			s= APUNTADO;
			break;
		case "false":
			s= CANCELADO;
			break;
		case "var1":
			s= ASISTIDO;
			break;
		case "var2":
			s= FALTADO;
		}
		return s;
	}
	
	public void actualizarTabla(String tipo) throws SQLException { 
		if (eventoSeleccionado != null) {
			borrarContenidoTabla();
			apuntados = DataBase.getApuntados(eventoSeleccionado.getActividad().getCodActividad(),
					eventoSeleccionado.getCodInstalacion(), eventoSeleccionado.getFecha(),
					eventoSeleccionado.getHoraInicio());
			Object[] nuevaFila = new Object[8];
			for (Usuario usuario : apuntados) {
				nuevaFila[0] = usuario.getCodUsuario();
				nuevaFila[1] = usuario.getuNombre();
				nuevaFila[2] = usuario.getApellido();
				nuevaFila[3] = usuario.getDni();
				nuevaFila[4] = tipo;
				modeloTabla.addRow(nuevaFila);
			}
		}
	}
	
	private void borrarContenidoTabla() {
		int nfilas = getTablaSocios().getModel().getRowCount();
		for (int i=0; i<nfilas; i++) {
			modeloTabla.removeRow(0);
		}
	}
	
	private JLabel getLblPlazas() {
		if (lblPlazas == null) {
			lblPlazas = new JLabel("N\u00FAmero de Plazas:");
		}
		return lblPlazas;
	}
	private JTextField getTxfPlazas() {
		if (txfPlazas == null) {
			txfPlazas = new JTextField();
			txfPlazas.setText("0");
			txfPlazas.setEditable(false);
			txfPlazas.setColumns(4);
		}
		return txfPlazas;
	}
	private JLabel getLblPlazasOcupadas() {
		if (lblPlazasOcupadas == null) {
			lblPlazasOcupadas = new JLabel("Ocupadas:");
		}
		return lblPlazasOcupadas;
	}
	private JTextField getTxfPlazasOcupadas() {
		if (txfPlazasOcupadas == null) {
			txfPlazasOcupadas = new JTextField();
			txfPlazasOcupadas.setText("0");
			txfPlazasOcupadas.setEditable(false);
			txfPlazasOcupadas.setColumns(4);
		}
		return txfPlazasOcupadas;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
			pnBotones.add(getBtnAsistido());
			pnBotones.add(getBtnNoAsistido());
			pnBotones.add(getBtnNuevoSocio());
		}
		return pnBotones;
	}
	private JButton getBtnAsistido() {
		if (btnAsistido == null) {
			btnAsistido = new JButton("Marcar como Asistido");
			btnAsistido.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						cambiarEstadoAsistidos(tablaSocios.getSelectedRows());
						actualizarPlazas();
						actualizarTabla();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnAsistido.setEnabled(false);										   
		}
		return btnAsistido;
	}
	private void cambiarEstadoAsistidos(int[] indices) throws SQLException {
		ArrayList<String> codUsuarios = new ArrayList<String>();
		for (int i = 0; i < indices.length; i++) {
			codUsuarios.add((String)tablaSocios.getValueAt(indices[i], 0));
		}
		 vPrincipal.getPrograma().marcarSociosComoAsistidos(codUsuarios, getComboActividad(),getInstalacionActividad(),getFechaActual(),getHoraInicio());
	}
	private JButton getBtnNoAsistido() {
		if (btnNoAsistido == null) {
			btnNoAsistido = new JButton("Marcar como No Asistido");
			btnNoAsistido.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						cambiarEstadoFalto(tablaSocios.getSelectedRows());
						actualizarPlazas();
						actualizarTabla();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnNoAsistido.setEnabled(false);											 
		}
		return btnNoAsistido;
	}
	private void cambiarEstadoFalto(int[] indices) throws SQLException {
		ArrayList<String> codUsuarios = new ArrayList<String>();
		for (int i = 0; i < indices.length; i++) {
			codUsuarios.add((String)tablaSocios.getValueAt(indices[i], 0));
		}
		 vPrincipal.getPrograma().marcarSociosComoFalto(codUsuarios, getComboActividad(),getInstalacionActividad(),getFechaActual(),getHoraInicio());
	}
	private JButton getBtnNuevoSocio() {
		if (btnNuevoSocio == null) {
			btnNuevoSocio = new JButton("Nuevo Socio");
			btnNuevoSocio.setEnabled(false);
			btnNuevoSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mostrarVentanaNuevoSocio();
				}
			});					   
		}
		return btnNuevoSocio;
	}
	private void mostrarVentanaNuevoSocio() {
		vNSM = new VentanaNuevoSocioMonitor(this);
		vNSM.setLocationRelativeTo(this);
		vNSM.setModal(true);
		vNSM.setVisible(true);
	}
	private JPanel getPnSur() {
		if (pnSur == null) {
			pnSur = new JPanel();
			pnSur.setLayout(new BorderLayout(0, 0));
			pnSur.add(getPnEventos(), BorderLayout.WEST);
			pnSur.add(getPnBotones(), BorderLayout.EAST);
			pnSur.add(getPnFecha(), BorderLayout.CENTER);
		}
		return pnSur;
	}
	private JLabel getLblInstalaciones() {
		if (lblInstalaciones == null) {
			lblInstalaciones = new JLabel("Instalaciones dedicadas:");
		}
		return lblInstalaciones;
	}
	private JComboBox<String> getCbxInstalaciones() {
		if (cbxInstalaciones == null) {
			cbxInstalaciones = new JComboBox<String>();
			actualizarInstalaciones();
		}
		return cbxInstalaciones;
	}
	private JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnAsistido.setEnabled(true);
					btnNoAsistido.setEnabled(true);
					btnNuevoSocio.setEnabled(true);			  
					setEventoSeleccionado();
					actualizarPlazas();
					try {
						actualizarTabla();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnBuscar.setEnabled(false);
		}
		return btnBuscar;
	}
	public VentanaPrincipal getVP() {
		return vPrincipal;
	}
	public String getComboActividad() {
		return (String) cbxActividad.getSelectedItem();
	}
	public String getHoraInicio() {
		return (String) cbxEventos.getSelectedItem();
	}
	public String getInstalacionActividad() {
		return (String) cbxInstalaciones.getSelectedItem();
	}
	public String getFechaActual() {
		return DateComparable.getActualDate();
	}
	
}
