package igu.admin.reservas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.objetos.Instalacion;
import logica.objetos.Pago;
import logica.objetos.Usuario;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class VentanaReservaAdmin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private PanelMostrarReservasAdministrador panelAnterior;
	private JComboBox<String> cbxHoraInicio;
	private JComboBox<String> cbxHoraFinal;
	private JCheckBox chckbxAllDay;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFinalizacion;

	/**
	 * Create the dialog.
	 */
	public VentanaReservaAdmin(PanelMostrarReservasAdministrador panelAnterior) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaReservaAdmin.class.getResource("/img/Logo_Olimpia.png")));
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setTitle("Centro Olimpia: Reserva de la administracion");
		this.panelAnterior=panelAnterior;
		this.setLocationRelativeTo(panelAnterior);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getCbxHoraInicio());
		contentPanel.add(getLblHoraInicio());
		contentPanel.add(getCbxHoraFinal());
		contentPanel.add(getLblHoraFinalizacion());
		contentPanel.add(getChckbxAllDay());
		this.setLocationRelativeTo(panelAnterior);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Reservar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(chckbxAllDay.isSelected()){
							try {
								reservar();
							} catch (ParseException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else if(compara((String)getCbxHoraInicio().getModel().getSelectedItem(),(String)getCbxHoraFinal().getModel().getSelectedItem())){
							try {
								reservar();
							} catch (ParseException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else{
							JOptionPane.showMessageDialog(null,"Las horas no son validas, la hora de inicio debe ser menor que la hora de finalización" );
						}
					}

					private boolean compara(String inicio, String finale) {
						return getPanelMostrarReservasAdministrador().getPrograma().comprobarHoras(inicio,finale);
					}

					
				});
				okButton.setActionCommand("Reservar");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancelar");
				buttonPane.add(cancelButton);
			}
		}
	}
	private void reservar() throws ParseException, SQLException {
		String codUsuario=PanelMostrarReservasAdministrador.ADMINISTRADOR;
		String codInstalacion=((Instalacion)getPanelMostrarReservasAdministrador().getCbxInstalaciones().getModel().getSelectedItem()).getCodInstalacion();
		String desde= (String) getCbxHoraInicio().getSelectedItem();
		String hasta= (String)getCbxHoraFinal().getSelectedItem();
		String estado= "0";
		String fecha= getPanelMostrarReservasAdministrador().obtenerFecha();
		getPanelMostrarReservasAdministrador().getPrograma().crearReserva(codUsuario,codInstalacion,desde,hasta,estado,fecha);
		getPanelMostrarReservasAdministrador().mostrarTablaAdministrador(PanelMostrarReservasAdministrador.HORAS, ((Instalacion)getPanelMostrarReservasAdministrador().getCbxInstalaciones().getModel().getSelectedItem()), fecha);
		dispose();
	}

	private JLabel getLblHoraFinalizacion() {
		if(lblHoraFinalizacion==null){
			lblHoraFinalizacion = new JLabel("Hora finalizacion");
			lblHoraFinalizacion.setBounds(10, 124, 134, 14);
		}
		return lblHoraFinalizacion;
	}

	private JLabel getLblHoraInicio() {
		if(lblHoraInicio==null){
			lblHoraInicio = new JLabel("Hora Inicio");
			lblHoraInicio.setBounds(10, 53, 134, 14);
		}
		return lblHoraInicio;
	}

	private PanelMostrarReservasAdministrador getPanelMostrarReservasAdministrador() {
		return panelAnterior;
	}
	private JComboBox<String> getCbxHoraInicio() {
		if(cbxHoraInicio==null){
		cbxHoraInicio = new JComboBox<String>();
		cbxHoraInicio.setBounds(154, 50, 241, 20);
		cbxHoraInicio.setModel(new DefaultComboBoxModel<String>(PanelMostrarReservasAdministrador.HORAS));
		}
		return cbxHoraInicio;
	}
	
	private JComboBox<String> getCbxHoraFinal(){
		if(cbxHoraFinal==null){
			this.cbxHoraFinal = new JComboBox<String>();
			cbxHoraFinal.setBounds(154, 121, 241, 20);
			cbxHoraFinal.setModel(new DefaultComboBoxModel<String>(PanelMostrarReservasAdministrador.HORAS_TERMINAR));
		}
		return cbxHoraFinal;
	}
	private JCheckBox getChckbxAllDay(){
		if(chckbxAllDay==null){
			chckbxAllDay = new JCheckBox("Reservar todo el d\u00EDa");
			chckbxAllDay.addActionListener(new ActionListener() {
				private String inicio;
				private String terminar;
				public void actionPerformed(ActionEvent arg0) {
					if(chckbxAllDay.isSelected()){
						this.inicio=(String)getCbxHoraInicio().getModel().getSelectedItem();
						this.terminar=(String)getCbxHoraFinal().getModel().getSelectedItem();
						getCbxHoraInicio().getModel().setSelectedItem(PanelMostrarReservasAdministrador.HORAS[0]);
						getCbxHoraFinal().getModel().setSelectedItem(PanelMostrarReservasAdministrador.HORAS_TERMINAR[PanelMostrarReservasAdministrador.HORAS.length-1]);
					}else{
						getCbxHoraInicio().getModel().setSelectedItem(this.inicio);
						getCbxHoraFinal().getModel().setSelectedItem(this.terminar);
					}
				}
			});
			chckbxAllDay.setBounds(154, 172, 241, 23);
		}
		return chckbxAllDay;
		
	}
}

