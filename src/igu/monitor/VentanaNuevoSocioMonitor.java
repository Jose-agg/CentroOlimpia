package igu.monitor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaNuevoSocioMonitor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNumSocio;
	private JComboBox cbxNumSocio;
	private JButton btnAgregar;
	private PanelMonitor pm;

	/**
	 * Create the dialog.
	 */
	public VentanaNuevoSocioMonitor(PanelMonitor pm) {
		this.pm=pm;
		setTitle("Agregar nuevo Socio");
		setBounds(100, 100, 450, 132);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPanel.add(getLblNumSocio());
		contentPanel.add(getCbxNumSocio());
		contentPanel.add(getBtnAgregar());
		cbxNumSocio.setModel(new DefaultComboBoxModel<String>(getSociosNoApuntados()));
	}

	private JLabel getLblNumSocio() {
		if (lblNumSocio == null) {
			lblNumSocio = new JLabel("Escoja entro los siguientes socios ");
		}
		return lblNumSocio;
	}
	private JComboBox getCbxNumSocio() {
		if (cbxNumSocio == null) {
			cbxNumSocio = new JComboBox();
			cbxNumSocio.setModel(new DefaultComboBoxModel(new String[] {""}));
		}
		return cbxNumSocio;
	}
	private String[] getSociosNoApuntados() {
		String[] s = null;
		s= pm.getVP().getPrograma().getSociosNoApuntadosActividad(pm.getComboActividad(),pm.getInstalacionActividad(),pm.getFechaActual(),pm.getHoraInicio());
		return s;
	}
	private JButton getBtnAgregar() {
		if (btnAgregar == null) {
			btnAgregar = new JButton("Agregar");
			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						pm.getVP().getPrograma().apuntarNuevoSocioMonitor((String) cbxNumSocio.getSelectedItem(), pm.getComboActividad(),pm.getInstalacionActividad(),pm.getFechaActual(),pm.getHoraInicio());
						pm.actualizarPlazas();
						pm.actualizarTabla();
						dispose();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		return btnAgregar;
	}
}
