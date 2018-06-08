package igu.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.MatteBorder;

import igu.admin.actividades.PanelMostrarActividadesAdministrador;
import igu.admin.reservas.PanelMostrarReservasAdministrador;
import igu.admin.reservas.VentanaUtilizarReserva;
import igu.contable.PanelContable;
import igu.monitor.PanelMonitor;
import igu.socio.actividades.PanelMostrarActividadesSocio;
import igu.socio.reservas.PanelMostrarReservasSocios;
import logica.programa.Programa;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import javax.swing.JButton;

public class VentanaPrincipal extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Programa programa;
	private JPanel contentPane;
	private JPanel pnCentral;
	private JPanel pnNorte;
	private JLabel lblFoto;
	private JPanel pnUsuario;
	private JPanel pnBienvenida;
	private JPanel pnReservasSocio;
	private JPanel pnReservasAdministrador;
	private boolean socio;
	private JRadioButton rdbtnAdministrador;
	private JRadioButton rdbtnContable;
	private JRadioButton rdbtnSocio;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel pnContable;
	private JPanel pnSeleccion;
	private JButton btnActividades;
	private JButton btnInstalaciones;
	private PanelMostrarActividadesAdministrador pnActividadesAdmin;
	private JPanel pnMonitor;
	private JRadioButton rdbtnMonitor;
	private PanelMostrarActividadesSocio pnActividadesSocio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ParseException
	 */
	public VentanaPrincipal() throws SQLException, ParseException {
		setMinimumSize(new Dimension(1024, 678));
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/img/Logo_Olimpia.png")));
		setTitle("Centro Olimpia");
		this.programa = new Programa();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 678);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPnNorte(), BorderLayout.NORTH);
		contentPane.add(getPnCentral(), BorderLayout.CENTER);
	}

	public JPanel getPnCentral() {
		if (pnCentral == null) {
			pnCentral = new JPanel();
			pnCentral.setLayout(new BorderLayout(0, 0));
			try {
				pnCentral.removeAll();
				pnCentral.add(getPnReservasAdmin(), BorderLayout.CENTER);
				pnCentral.updateUI();
			} catch (ParseException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pnCentral;
	}

	private JPanel getPnNorte() {
		if (pnNorte == null) {
			pnNorte = new JPanel();
			pnNorte.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
			pnNorte.setBackground(Color.WHITE);
			pnNorte.setLayout(new BorderLayout(0, 0));
			pnNorte.add(getLblFoto(), BorderLayout.WEST);
			pnNorte.add(getPnUsuario(), BorderLayout.EAST);
			pnNorte.add(getPnBienvenida(), BorderLayout.CENTER);
			pnNorte.add(getPnSeleccion(), BorderLayout.SOUTH);
		}
		return pnNorte;
	}

	private JLabel getLblFoto() {
		if (lblFoto == null) {
			lblFoto = new JLabel("");
			lblFoto.setPreferredSize(new Dimension(300, 120));
			lblFoto.setEnabled(false);
			ImageIcon icono = redimensionarRotulo();
			lblFoto.setIcon(icono);
			lblFoto.setDisabledIcon(icono);
		}
		return lblFoto;
	}

	private ImageIcon redimensionarRotulo() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(VentanaPrincipal.class.getResource("/img/Rotulo_Olimpia.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(300, 120, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		return imageIcon;
	}

	private JPanel getPnUsuario() {
		if (pnUsuario == null) {
			pnUsuario = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnUsuario.getLayout(); // Para colocar los elementos donde se quiera
			flowLayout.setHgap(10);
			flowLayout.setVgap(45);
			pnUsuario.setBackground(Color.WHITE);
			pnUsuario.add(getRdbtnAdministrador());
			pnUsuario.add(getRdbtnContable());
			pnUsuario.add(getRdbtnMonitor());
			pnUsuario.add(getRdbtnSocio());
		}
		return pnUsuario;
	}

	public Programa getPrograma() {
		return this.programa;
	}

	private JPanel getPnBienvenida() {
		if (pnBienvenida == null) {
			pnBienvenida = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnBienvenida.getLayout(); // Para colocar los elementos donde se quiera
			flowLayout.setHgap(10);
			flowLayout.setVgap(24);
			pnBienvenida.setBackground(Color.WHITE);
		}
		return pnBienvenida;
	}
	public JPanel getPnReservasSocios() throws ParseException, SQLException {
		if (pnReservasSocio == null) {

			this.pnReservasSocio = new PanelMostrarReservasSocios(programa);

		}
		return pnReservasSocio;
	}

	public boolean isSocio() {
		return socio;
	}

	public void setSocio(boolean socio) {
		this.socio = socio;
	}
	private JPanel getPnReservasAdmin() throws ParseException, SQLException {
		if (pnReservasAdministrador == null) {
			this.pnReservasAdministrador = new PanelMostrarReservasAdministrador(this);
		}
		return pnReservasAdministrador;
	}
	private JRadioButton getRdbtnAdministrador() {
		if (rdbtnAdministrador == null) {
			rdbtnAdministrador = new JRadioButton("Administrador");
			rdbtnAdministrador.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					pnCentral.removeAll();
					pnCentral.updateUI();
					getPnSeleccion().setVisible(true);
				}

			});
			rdbtnAdministrador.setSelected(true);
			buttonGroup.add(rdbtnAdministrador);
			rdbtnAdministrador.setBackground(Color.WHITE);
			rdbtnAdministrador.setMnemonic('A');
		}
		return rdbtnAdministrador;
	}

	private JRadioButton getRdbtnContable() {
		if (rdbtnContable == null) {
			rdbtnContable = new JRadioButton("Contable");
			rdbtnContable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getPnSeleccion().setVisible(false);
					 try {
					 pnCentral.removeAll();
					 pnCentral.add(getPnContable(), BorderLayout.CENTER);
					 ((PanelContable) getPnContable()).actualizarTabla();
					 pnCentral.updateUI();
					 } catch (SQLException e1) {
					 // TODO Auto-generated catch block
					 e1.printStackTrace();
					 }
				}
			});
			buttonGroup.add(rdbtnContable);
			rdbtnContable.setBackground(Color.WHITE);
			rdbtnContable.setMnemonic('C');
		}
		return rdbtnContable;
	}

	private JPanel getPnContable() {
		if (pnContable == null) {
			 pnContable = new PanelContable(this);
		}
		return pnContable;
	}

	private JRadioButton getRdbtnSocio() {
		if (rdbtnSocio == null) {
			rdbtnSocio = new JRadioButton("Socio");
			rdbtnSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pnCentral.removeAll();
					pnCentral.updateUI();
					getPnSeleccion().setVisible(true);
				}
			});
			buttonGroup.add(rdbtnSocio);
			rdbtnSocio.setBackground(Color.WHITE);
			rdbtnSocio.setMnemonic('S');
		}
		return rdbtnSocio;
	}
	private JRadioButton getRdbtnMonitor() {
		if (rdbtnMonitor == null) {
			rdbtnMonitor = new JRadioButton("Monitor");
			rdbtnMonitor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					pnCentral.removeAll();
					pnCentral.add(getPnMonitor(), BorderLayout.CENTER);
					try {
						((PanelMonitor) getPnMonitor()).actualizarTabla();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getPnSeleccion().setVisible(false);
					pnCentral.updateUI();
				}
			});
			rdbtnMonitor.setMnemonic('M');
			buttonGroup.add(rdbtnMonitor);
			rdbtnMonitor.setBackground(Color.WHITE);
		}
		return rdbtnMonitor;
	}
	
	private JPanel getPnMonitor() {
		if (pnMonitor==null) {
			pnMonitor = new PanelMonitor(this);
		}
		return pnMonitor;
	}

	private JPanel getPnSeleccion() {
		if (pnSeleccion == null) {
			pnSeleccion = new JPanel();
			pnSeleccion.setBorder(new MatteBorder(2, 0, 0, 0, (Color) new Color(0, 0, 0)));
			pnSeleccion.setLayout(new GridLayout(1, 0, 0, 0));
			pnSeleccion.add(getBtnInstalaciones());
			pnSeleccion.add(getBtnActividades());
		}
		return pnSeleccion;
	}

	private JButton getBtnActividades() {
		if (btnActividades == null) {
			btnActividades = new JButton("Actividades");
			btnActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnSocio.isSelected()) {
						pnCentral.removeAll();
						pnCentral.add(getPnActividadesSocio(), BorderLayout.CENTER);
						pnCentral.updateUI();
					} else {
						pnCentral.removeAll();
						pnCentral.add(getPnActividadesAdmin(), BorderLayout.CENTER);
						pnCentral.updateUI();
					}
				}

			});
		}
		return btnActividades;
	}
	private PanelMostrarActividadesAdministrador getPnActividadesAdmin() {
		if(pnActividadesAdmin==null) {
			this.pnActividadesAdmin=new PanelMostrarActividadesAdministrador(this);
		}
		return pnActividadesAdmin;
	}
	private PanelMostrarActividadesSocio getPnActividadesSocio() {
		if(pnActividadesSocio==null) {
			this.pnActividadesSocio=new PanelMostrarActividadesSocio(this);
		}
		return pnActividadesSocio;
	}

	private JButton getBtnInstalaciones() {
		if (btnInstalaciones == null) {
			btnInstalaciones = new JButton("Instalaciones");
			btnInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (rdbtnSocio.isSelected()) {
						try {
							pnCentral.removeAll();
							pnCentral.add(getPnReservasSocios(), BorderLayout.CENTER);
							pnCentral.updateUI();
						} catch (ParseException | SQLException sq) {
							// TODO Auto-generated catch block
							sq.printStackTrace();
						}
					} else {
						try {
							pnCentral.removeAll();
							pnCentral.add(getPnReservasAdmin(), BorderLayout.CENTER);
							pnCentral.updateUI();
						} catch (ParseException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			rdbtnAdministrador.setSelected(true);
			buttonGroup.add(rdbtnAdministrador);
			rdbtnAdministrador.setBackground(Color.WHITE);
			rdbtnAdministrador.setMnemonic('A');
		}
		return btnInstalaciones;
	}
	
}