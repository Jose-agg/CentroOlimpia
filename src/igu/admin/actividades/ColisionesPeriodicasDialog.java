package igu.admin.actividades;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dataBase.DataBase;
import date.DateComparable;
import igu.main.VentanaPrincipal;
import logica.objetos.Actividad;
import logica.objetos.Apuntados;
import logica.objetos.Evento;
import logica.objetos.EventoActividad;
import logica.objetos.Instalacion;
import logica.objetos.Reserva;
import logica.objetos.Usuario;
import modelos.ModeloTabla;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ColisionesPeriodicasDialog extends JDialog {
	
	//Constantes
	public final static String[] NOMBRE_COLUMNAS = {"Día", "Hora Inicio", "Hora Final", "Fecha", "Instalación", "Usuario"};
	public final static String[] DIAS_SEMANA = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel pnCentral = new JPanel();
	private PanelCrearActividadesVariosDiasSemana pnAnterior1;
	private PanelCrearActividadPuntual pnAnterior2;
	private JPanel pnOpciones;
	private JLabel lblFoto;
	private JPanel pnInfo;
	private JButton btnCancelar;
	private JButton btnContinuar;
	private JTextPane txtInfo;
	private JPanel pnColisiones;
	private JPanel pnOmitir;
	private JPanel pnEliminar;
	private JLabel lblReservasASobreescribir;
	private JScrollPane scrllEliminar;
	private JTable tablaEliminar;
	private ModeloTabla modeloTablaEliminar;
	private ModeloTabla modeloTablaOmitir;
	private JLabel lblReservasAOmitir;
	private JScrollPane scrllOmitir;
	private JTable tablaOmitir;
	private JPanel pnIntercambio;
	private JButton btnAddOmitir;
	private JButton btnAddEliminar;
	private ArrayList<Reserva> sobreescribir;
	private ArrayList<Reserva> omitir;

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public ColisionesPeriodicasDialog(ArrayList<Reserva> colisiones, JPanel pnAnterior) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onClose(true);
			}
		});
		setResizable(false);
		this.sobreescribir = colisiones;
		this.omitir = new ArrayList<Reserva>();
		if (pnAnterior instanceof PanelCrearActividadesVariosDiasSemana)
			this.pnAnterior1 = (PanelCrearActividadesVariosDiasSemana) pnAnterior;
		else
			this.pnAnterior2 = (PanelCrearActividadPuntual) pnAnterior;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ColisionesPeriodicasDialog.class.getResource("/img/Logo_Olimpia.png")));
		setBounds(100, 100, 727, 561);
		setTitle("Colision con reserva previa");
		
		getContentPane().setLayout(new BorderLayout());
		pnCentral.setBackground(Color.WHITE);
		pnCentral.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(pnCentral, BorderLayout.CENTER);
		pnCentral.setLayout(new BorderLayout(0, 0));
		pnCentral.add(getPnOpciones(), BorderLayout.SOUTH);
		pnCentral.add(getPnInfo(), BorderLayout.NORTH);
		pnCentral.add(getPnColisiones(), BorderLayout.CENTER);
	}
	
	private JPanel getPnOpciones() {
		if (pnOpciones == null) {
			pnOpciones = new JPanel();
			pnOpciones.setBorder(new MatteBorder(2, 0, 0, 0, (Color) new Color(0, 0, 0)));
			pnOpciones.setBackground(Color.WHITE);
			pnOpciones.add(getBtnContinuar());
			pnOpciones.add(getBtnCancelar());
		}
		return pnOpciones;
	}
	
	private JLabel getLblFoto() {
		if (lblFoto == null) {
			lblFoto = new JLabel("");
			lblFoto.setIcon(new ImageIcon(ColisionesPeriodicasDialog.class.getResource("/img/Warning_Img.png")));
		}
		return lblFoto;
	}
	private JPanel getPnInfo() {
		if (pnInfo == null) {
			pnInfo = new JPanel();
			pnInfo.setBackground(Color.WHITE);
			pnInfo.setLayout(new BorderLayout(0, 0));
			pnInfo.add(getLblFoto(), BorderLayout.WEST);
			pnInfo.add(getTxtInfo(), BorderLayout.CENTER);
		}
		return pnInfo;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					onClose(true);
					dispose();
				}
			});
		}
		return btnCancelar;
	}
	private JButton getBtnContinuar() {
		if (btnContinuar == null) {
			btnContinuar = new JButton("Continuar");
			btnContinuar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaApuntadosAvisar(getApuntados());
					removeEventos();
					cancelarReservas();
					onClose(false);
					dispose();
				}
			});
		}
		return btnContinuar;
	}

	private JTextPane getTxtInfo() {
		if (txtInfo == null) {
			txtInfo = new JTextPane();
			txtInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
			txtInfo.setEditable(false);
			txtInfo.setText("\nSe han encontrado reservas para las instalaciones que deseas ocupar.\r\nElige cuales deseeas sobreescribir, y cuales deseas omitir.\r\nAquellas reservas que se omitan no dejar\u00E1n sus instalaciones libres por lo que no se crear\u00E1n las actividades solicitadas.");		
			StyledDocument doc = txtInfo.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);
		}
		return txtInfo;
	}
	private JPanel getPnColisiones() {
		if (pnColisiones == null) {
			pnColisiones = new JPanel();
			pnColisiones.setBackground(Color.WHITE);
			pnColisiones.setBorder(new MatteBorder(2, 0, 0, 0, (Color) new Color(0, 0, 0)));
			pnColisiones.setLayout(new GridLayout(2, 0, 0, 0));
			pnColisiones.add(getPnEliminar());
			pnColisiones.add(getPnOmitir());
		}
		return pnColisiones;
	}
	private JPanel getPnOmitir() {
		if (pnOmitir == null) {
			pnOmitir = new JPanel();
			pnOmitir.setBackground(Color.WHITE);
			pnOmitir.setLayout(new BorderLayout(0, 0));
			pnOmitir.add(getPnIntercambio(), BorderLayout.NORTH);
			pnOmitir.add(getScrllOmitir(), BorderLayout.CENTER);
		}
		return pnOmitir;
	}
	private JPanel getPnEliminar() {
		if (pnEliminar == null) {
			pnEliminar = new JPanel();
			pnEliminar.setBackground(Color.WHITE);
			pnEliminar.setLayout(new BorderLayout(0, 0));
			pnEliminar.add(getLblReservasASobreescribir(), BorderLayout.NORTH);
			pnEliminar.add(getScrllEliminar(), BorderLayout.CENTER);
		}
		return pnEliminar;
	}
	private JLabel getLblReservasASobreescribir() {
		if (lblReservasASobreescribir == null) {
			lblReservasASobreescribir = new JLabel("Reservas a Sobreescribir:");
			lblReservasASobreescribir.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblReservasASobreescribir.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblReservasASobreescribir;
	}
	private JScrollPane getScrllEliminar() {
		if (scrllEliminar == null) {
			scrllEliminar = new JScrollPane();
			scrllEliminar.setBackground(Color.WHITE);
			scrllEliminar.setViewportView(getTablaEliminar());
		}
		return scrllEliminar;
	}
	private JTable getTablaEliminar() {
		if (tablaEliminar == null) {
			modeloTablaEliminar = new ModeloTabla(NOMBRE_COLUMNAS, 0);
			tablaEliminar = new JTable(modeloTablaEliminar);
			tablaEliminar.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tablaEliminar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					getBtnAddOmitir().setEnabled(true);
				}
			});
			try {
				actualizarTabla(sobreescribir, modeloTablaEliminar);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tablaEliminar.setBackground(Color.WHITE);
			tablaEliminar.getTableHeader().setReorderingAllowed(false);
		}
		return tablaEliminar;
	}
	private JLabel getLblReservasAOmitir() {
		if (lblReservasAOmitir == null) {
			lblReservasAOmitir = new JLabel("Reservas a Omitir:");
			lblReservasAOmitir.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblReservasAOmitir.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblReservasAOmitir;
	}
	private JScrollPane getScrllOmitir() {
		if (scrllOmitir == null) {
			scrllOmitir = new JScrollPane();
			scrllOmitir.setBackground(Color.WHITE);
			scrllOmitir.setViewportView(getTablaOmitir());
		}
		return scrllOmitir;
	}
	private JTable getTablaOmitir() {
		if (tablaOmitir == null) {
			modeloTablaOmitir = new ModeloTabla(NOMBRE_COLUMNAS, 0);
			tablaOmitir = new JTable(modeloTablaOmitir);
			tablaOmitir.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					getBtnAddEliminar().setEnabled(true);
				}
			});
			tablaOmitir.setBackground(Color.WHITE);
			tablaOmitir.getTableHeader().setReorderingAllowed(false);
		}
		return tablaOmitir;
	}
	

	public void actualizarTabla(ArrayList<Reserva> reservas, ModeloTabla modelo) throws SQLException {
		borrarContenidoTabla(modelo);
		
		Object[] nuevaFila = new Object[6];
		for (Reserva reserva : reservas) {
			nuevaFila[0] = getDayOfWeek(reserva);
			nuevaFila[1] = reserva.getDesde();
			nuevaFila[2] = reserva.getHasta();
			nuevaFila[3] = reserva.getFechaReserva();
			nuevaFila[4] = DataBase.getInstalacion(reserva.getCodInstalacion());
			nuevaFila[5] = ((reserva.getCodUsuario().equals("A-001"))? "Administración" : DataBase.getNombre(reserva.getCodUsuario()));

			modelo.addRow(nuevaFila);
		}
	}
	
	private void borrarContenidoTabla(ModeloTabla modelo) {
		int nfilas = modelo.getRowCount();
		for (int i=0; i<nfilas; i++) {
			modelo.removeRow(0);
		}
	}
	
	private String getDayOfWeek(Reserva reserva) {
		Calendar c = DateComparable.getFechaSeleccionada(reserva.getFechaReserva());
		int dia = c.get(Calendar.DAY_OF_WEEK);
		return DIAS_SEMANA[dia-1];
	}
	private JPanel getPnIntercambio() {
		if (pnIntercambio == null) {
			pnIntercambio = new JPanel();
			pnIntercambio.setBackground(Color.WHITE);
			pnIntercambio.add(getLblReservasAOmitir());
			pnIntercambio.add(getBtnAddEliminar());
			pnIntercambio.add(getBtnAddOmitir());
		}
		return pnIntercambio;
	}
	private JButton getBtnAddOmitir() {
		if (btnAddOmitir == null) {
			btnAddOmitir = new JButton("\u25BC");
			btnAddOmitir.setEnabled(false);
			btnAddOmitir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					intercambiar(tablaEliminar.getSelectedRow(), sobreescribir, omitir, modeloTablaEliminar, modeloTablaOmitir);
					btnAddOmitir.setEnabled(false);
					getBtnAddEliminar().setEnabled(false);
				}
			});
			btnAddOmitir.setBackground(Color.WHITE);
		}
		return btnAddOmitir;
	}
	private JButton getBtnAddEliminar() {
		if (btnAddEliminar == null) {
			btnAddEliminar = new JButton("\u25B2");
			btnAddEliminar.setEnabled(false);
			btnAddEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					intercambiar(tablaOmitir.getSelectedRow(), omitir, sobreescribir, modeloTablaOmitir, modeloTablaEliminar);
					btnAddEliminar.setEnabled(false);
					getBtnAddOmitir().setEnabled(false);
				}
			});
			btnAddEliminar.setBackground(Color.WHITE);
		}
		return btnAddEliminar;
	}

	/**
	 * Método que intercambia los datos entre las tablas de eliminar y la de omitir
	 * @param index el índice de las filas a intercambiar
	 * @param toRemove la lista de la que borrar
	 * @param toAdd la lista a la que añadir
	 * @param mRemove el modelo del que borrar
	 * @param mAdd el modelo al que añadir
	 */
	private void intercambiar(int index, ArrayList<Reserva> toRemove, ArrayList<Reserva> toAdd, ModeloTabla mRemove, ModeloTabla mAdd) {
		Reserva reserva = toRemove.get(index);
		toRemove.remove(index);
		toAdd.add(reserva);
		mRemove.removeRow(index);
		try {
			actualizarTabla(toAdd, mAdd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void removeEventos() {
		for (Reserva reserva : sobreescribir) {
			if (reserva.getCodUsuario().equals("A-001")) {
				try {
					Evento evento = DataBase.getEvento(reserva.getCodInstalacion(), reserva.getFechaReserva(), reserva.getDesde());
					if (evento!=null) {
						//mostrarVentanaApuntadosAvisar(getApuntados(DataBase._getEvento(reserva.getCodInstalacion(), reserva.getFechaReserva(), reserva.getDesde())), PanelMostrarActividadesAdministrador.EVENTO);
						DataBase.removeApuntados(evento.getActividad().getCodActividad(), evento.getCodInstalacion(), evento.getFecha(), evento.getHoraInicio());
						DataBase.removeEvento(reserva.getCodInstalacion(), reserva.getFechaReserva(), reserva.getDesde());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void cancelarReservas() {
		for (Reserva reserva : sobreescribir) {
			try {
				//mostrarVentanaApuntadosAvisar(, PanelMostrarActividadesAdministrador.RESERVA);
				DataBase.cancelarReserva(reserva.getFechaReserva(), reserva.getCodInstalacion(), 
						reserva.getCodUsuario(), reserva.getDesde(), reserva.getHasta(), "Motivos del centro");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void onClose(boolean closed) {
		if (pnAnterior1!=null) {
			pnAnterior1.setCanceladaTransaccion(closed);
			pnAnterior1.removeArrayColisiones();
		} else {
			pnAnterior2.setCanceladaTransaccion(closed);
			pnAnterior2.removeArrayColisiones();
		}
	}
	
	public void mostrarVentanaApuntadosAvisar(List<HashMap<String, String>>apuntados) {
		if(apuntados.size()>0) {
		VentanaAviso va = new VentanaAviso(apuntados);
		va.setLocationRelativeTo(this);
		va.setModal(true);
		va.setVisible(true);
		}
	}
	public ArrayList<Object> getApuntados(EventoActividad ea) {
		try {
			ArrayList<Apuntados> apuntados = DataBase.cargarApuntados();
			ArrayList<Object> aux = new ArrayList<Object>();
				for (Apuntados ap : apuntados) {
					if(ap.getCodActividad().equals(ea.getCodActividad()) &&
							ap.getCodInstalacion().equals(ea.getCodInstalacion()) &&
							ap.getFecha().equals(ea.getFecha()) &&
							ap.getHoraInicio().equals(ea.getHoraInicio())) {
						aux.add(ap);
					}
				}
			
			return aux;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	

	protected List<HashMap<String, String>> getApuntados() {
		try {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		for(Reserva reserva :sobreescribir) {
			EventoActividad ea = DataBase._getEvento(reserva.getCodInstalacion(), reserva.getFechaReserva(), reserva.getDesde());
			if(ea!=null){
				for(Apuntados ap: DataBase.cargarApuntados()) {
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
					lista.add(map);
					}
				}
			}else {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("codigo", reserva.getCodUsuario());
				map.put("instalacion", reserva.getCodInstalacion());
				map.put("hora", reserva.getDesde() +"-"+ reserva.getHasta());
				map.put("fecha", reserva.getFechaReserva());
				map.put("motivo", "Reserva de instalacion cancelada");
				lista.add(map);
			}
		}
		return lista;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
