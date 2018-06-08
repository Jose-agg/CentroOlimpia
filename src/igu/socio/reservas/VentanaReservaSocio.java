package igu.socio.reservas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;

public class VentanaReservaSocio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> comboHoraInicial;
	private JComboBox<String> comboBoxHoraFinal;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFin;
	private JTextField textFieldFecha;
	private JLabel lblFecha;
	private JLabel lblFormaDePago;
	private JComboBox comboBoxPago;
	private JButton btnReservar;
	private JButton btnCancelar;

//PanelMostrarReservasSocios.getPrograma().ReservarParaSocio(String.valueOf(comboHoraInicial.getSelectedItem()), String.valueOf(comboBoxHoraFinal.getSelectedItem()), "02:00",PanelMostrarReservasSocios.getCodU() ,PanelMostrarReservasSocios.getCodI() , PanelMostrarReservasSocios.obtenerFecha());
	/**
	 * Create the dialog.
	 */
	public VentanaReservaSocio() {
		setTitle("Centro Olimpia: Ventana de reservas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaReservaSocio.class.getResource("/img/Logo_Olimpia.png")));
		setBounds(100, 100, 516, 363);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(null);
			panel.add(getComboHoraInicial());
			panel.add(getComboBoxHoraFinal());
			panel.add(getLblHoraInicio());
			panel.add(getLblHoraFin());
			panel.add(getTextFieldFecha());
			panel.add(getLblFecha());
			panel.add(getLblFormaDePago());
			panel.add(getComboBoxPago());
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.add(getBtnReservar());
			buttonPane.add(getBtnCancelar());
		}
	}
	private JComboBox<String> getComboHoraInicial() {
		if (comboHoraInicial == null) {
			comboHoraInicial = new JComboBox<String>();
			comboHoraInicial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					comboBoxHoraFinal.setEnabled(true);
					rellenarComboHorasFinales();
				}

				
			});
			
			comboHoraInicial.setModel(new DefaultComboBoxModel<String>(PanelMostrarReservasSocios.HORAS));
			comboHoraInicial.setBounds(161, 83, 103, 20);
		}
		return comboHoraInicial;
	}
	
	/**
	 * Método que solo deja elegir la hora siguiente
	 */
	private void rellenarComboHorasFinales() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
		String[] horas = PanelMostrarReservasSocios.HORAS_TERMINAR;
		int contador = 0;
		for (String string : horas) {
			contador++;
		
			if(comboHoraInicial.getSelectedItem().equals(string)){
			
				if(contador == 24){
					modelo.addElement(horas[contador]);
		
				}else{
					if(contador==23){
						modelo.addElement(horas[contador]);
					}else{
					modelo.addElement(horas[contador]);
					modelo.addElement(horas[contador+1]);
					}
	
				}
				
			}
		}
		comboBoxHoraFinal.setModel(modelo);
	}
	
	
	
	private JComboBox<String> getComboBoxHoraFinal() {
		if (comboBoxHoraFinal == null) {
			comboBoxHoraFinal = new JComboBox<String>();
			comboBoxHoraFinal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					comboBoxPago.setEnabled(true);
					
				}
			});
			
	
			comboBoxHoraFinal.setEnabled(false);
			comboBoxHoraFinal.setBounds(161, 133, 104, 20);
		}
		return comboBoxHoraFinal;
	}
	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora Inicio:");
			lblHoraInicio.setBounds(34, 86, 79, 14);
		}
		return lblHoraInicio;
	}
	private JLabel getLblHoraFin() {
		if (lblHoraFin == null) {
			lblHoraFin = new JLabel("Hora Fin:");
			lblHoraFin.setBounds(34, 136, 67, 14);
		}
		return lblHoraFin;
	}
	private JTextField getTextFieldFecha() {
		if (textFieldFecha == null) {
			textFieldFecha = new JTextField();
			textFieldFecha.setEnabled(false);
			textFieldFecha.setEditable(false);
			textFieldFecha.setText(PanelMostrarReservasSocios.obtenerFecha());
			textFieldFecha.setBounds(161, 33, 103, 20);
			textFieldFecha.setColumns(10);
		}
		return textFieldFecha;
	}
	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setBounds(34, 36, 46, 14);
		}
		return lblFecha;
	}
	private JLabel getLblFormaDePago() {
		if (lblFormaDePago == null) {
			lblFormaDePago = new JLabel("Forma de Pago:");
			lblFormaDePago.setBounds(34, 190, 90, 14);
		}
		return lblFormaDePago;
	}
	@SuppressWarnings("unchecked")
	private JComboBox<Object> getComboBoxPago() {
		if (comboBoxPago == null) {
			comboBoxPago = new JComboBox<Object>();
			comboBoxPago.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					btnReservar.setEnabled(true);
					
				}
			});
			comboBoxPago.setModel(new DefaultComboBoxModel<Object>(new String[] {"Efectivo", "Cuota Mensual"}));
			comboBoxPago.setEnabled(false);
			comboBoxPago.setBounds(161, 187, 103, 20);
		}
		return comboBoxPago;
	}
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					//	System.out.println(sumarRestarHorasFecha(new Date(),1));
					Date di =sumarRestarHorasFecha(new Date(),1);
					String si = String.valueOf(di.getHours())+":00";
					if(PanelMostrarReservasSocios.getPrograma().comprobarHoras(String.valueOf(comboHoraInicial.getSelectedItem()),si)&& PanelMostrarReservasSocios.obtenerFecha().equals(formatearFechaActual())){
						JOptionPane.showMessageDialog(contentPanel, "Hora pasada.");
					}else{
						Date d =sumarRestarHorasFecha(new Date(),1);
						String s = String.valueOf(d.getHours())+":00";
						if(comboHoraInicial.getSelectedItem().equals(s)&& PanelMostrarReservasSocios.obtenerFecha().equals(formatearFechaActual())){
							JOptionPane.showMessageDialog(contentPanel, "Solo se puede reservar 1h antes.");
						}else{
							if(PanelMostrarReservasSocios.getPrograma().ReservarParaSocio(String.valueOf(comboHoraInicial.getSelectedItem()), String.valueOf(comboBoxHoraFinal.getSelectedItem()),PanelMostrarReservasSocios.getCodU() ,PanelMostrarReservasSocios.getCodI() , PanelMostrarReservasSocios.obtenerFecha(),String.valueOf(comboBoxPago.getSelectedItem())).equals("Reserva")){
								JOptionPane.showMessageDialog(contentPanel, "Reserva Realizada.");

							}else if(PanelMostrarReservasSocios.getPrograma().ReservarParaSocio(String.valueOf(comboHoraInicial.getSelectedItem()), String.valueOf(comboBoxHoraFinal.getSelectedItem()),PanelMostrarReservasSocios.getCodU() ,PanelMostrarReservasSocios.getCodI() , PanelMostrarReservasSocios.obtenerFecha(),String.valueOf(comboBoxPago.getSelectedItem())).equals("Reserva Simultánea en otra instalación misma hora.")){
								JOptionPane.showMessageDialog(contentPanel, "Reserva Simultánea en otra instalación misma hora.");
							}else if(PanelMostrarReservasSocios.getPrograma().ReservarParaSocio(String.valueOf(comboHoraInicial.getSelectedItem()), String.valueOf(comboBoxHoraFinal.getSelectedItem()),PanelMostrarReservasSocios.getCodU() ,PanelMostrarReservasSocios.getCodI() , PanelMostrarReservasSocios.obtenerFecha(),String.valueOf(comboBoxPago.getSelectedItem())).equals("Hay una reserva de otra persona.")){
								JOptionPane.showMessageDialog(contentPanel,"Hay una reserva de otra persona.");
							}else if(PanelMostrarReservasSocios.getPrograma().ReservarParaSocio(String.valueOf(comboHoraInicial.getSelectedItem()), String.valueOf(comboBoxHoraFinal.getSelectedItem()),PanelMostrarReservasSocios.getCodU() ,PanelMostrarReservasSocios.getCodI() , PanelMostrarReservasSocios.obtenerFecha(),String.valueOf(comboBoxPago.getSelectedItem())).equals("Ya reservo para esa hora.")){
								JOptionPane.showMessageDialog(contentPanel,"Ya reservo para esa hora.");
							}else if(PanelMostrarReservasSocios.getPrograma().ReservarParaSocio(String.valueOf(comboHoraInicial.getSelectedItem()), String.valueOf(comboBoxHoraFinal.getSelectedItem()),PanelMostrarReservasSocios.getCodU() ,PanelMostrarReservasSocios.getCodI() , PanelMostrarReservasSocios.obtenerFecha(),String.valueOf(comboBoxPago.getSelectedItem())).equals("Ya tiene una actividad para esa hora y esa fecha.")){
								JOptionPane.showMessageDialog(contentPanel,"Ya tiene una actividad para esa hora y esa fecha.");
							}
							//System.out.println(String.valueOf(comboHoraInicial.getSelectedItem())+ String.valueOf(comboBoxHoraFinal.getSelectedItem())+PanelMostrarReservasSocios.getCodU() +PanelMostrarReservasSocios.getCodI()+ PanelMostrarReservasSocios.obtenerFecha());
						}
					}

				}


			});
			btnReservar.setEnabled(false);
		}
		return btnReservar;
	}
	
	private String formatearFechaActual() {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String folderName = formatter.format(today);
		return folderName;
	}
	
	
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
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
	
	
}
