package igu.admin.actividades;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;

import igu.socio.reservas.PanelMostrarReservasSocios;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaCrearActividad extends JDialog {
	private JPanel pnNorte;
	private JRadioButton rdbtnPuntual;
	private JRadioButton rdbtnPeriodica;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel pnCentral;
	private PanelMostrarActividadesAdministrador pMA;
	private JPanel pnCrearActPuntual;
	private JPanel pnVariosDias;
	/**
	 * Create the dialog.
	 */
	public VentanaCrearActividad(PanelMostrarActividadesAdministrador pMA) {
		this.pMA=pMA;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				rellenarCbxActividades();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaCrearActividad.class.getResource("/img/Logo_Olimpia.png")));
		setTitle("Crear Actividad");
		setBounds(100, 100, 835, 692);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnNorte(), BorderLayout.NORTH);
		getContentPane().add(getPnCentral(), BorderLayout.CENTER);
		try {
			cargarPanelCrearActividadPuntual();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void rellenarCbxActividades() {
		pMA.rellenarCbxActividades();
	}

	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setBorder(new LineBorder(new Color(0, 0, 0)));
			pnNorte.add(getRdbtnPuntual());
			pnNorte.add(getRdbtnPeriodica());
		}
		return pnNorte;
	}
	private JRadioButton getRdbtnPuntual() {
		if (rdbtnPuntual == null) {
			rdbtnPuntual = new JRadioButton("Puntual");
			rdbtnPuntual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						cargarPanelCrearActividadPuntual();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			rdbtnPuntual.setFont(new Font("Arial", Font.PLAIN, 19));
			rdbtnPuntual.setSelected(true);
			buttonGroup.add(rdbtnPuntual);
		}
		return rdbtnPuntual;
	}
	private JRadioButton getRdbtnPeriodica() {
		if (rdbtnPeriodica == null) {
			rdbtnPeriodica = new JRadioButton("Periodica");
			rdbtnPeriodica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					try {
						pnCentral.removeAll();
						pnCentral.add(getPnVariosDias(), BorderLayout.CENTER);
						pnCentral.updateUI();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			});
			rdbtnPeriodica.setFont(new Font("Arial", Font.PLAIN, 19));
			buttonGroup.add(rdbtnPeriodica);
		}
		return rdbtnPeriodica;
	}
	private JPanel getPnVariosDias() throws SQLException {
		if (pnVariosDias==null) {
			pnVariosDias = new PanelCrearActividadesVariosDiasSemana(pMA,this);
		}
		return pnVariosDias;
	}
	
	private JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setLayout(new BorderLayout(0, 0));
		}
		return pnCentral;
	}
	private void cargarPanelCrearActividadPuntual() throws SQLException {
		pnCentral.removeAll();
		pnCentral.add(getPnCrearActPuntual(), BorderLayout.CENTER);
		pnCentral.updateUI();
	}
	public JPanel getPnCrearActPuntual() throws SQLException{
		if (pnCrearActPuntual == null) {
			this.pnCrearActPuntual = new PanelCrearActividadPuntual(this);
		}
		return pnCrearActPuntual;
	}
	public PanelMostrarActividadesAdministrador getPanelMostrarActividadesAdministrador() {
		return pMA;
	}
}
