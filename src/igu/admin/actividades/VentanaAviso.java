package igu.admin.actividades;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dataBase.DataBase;
import igu.main.VentanaPrincipal;
import logica.objetos.Apuntados;
import logica.objetos.Reserva;
import logica.objetos.Usuario;
import modelos.ModeloTabla;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;



public class VentanaAviso extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel lblAvisos;
	private JScrollPane scrollPane;
	private JPanel pnDatos;
	private JTable table;
	private ModeloTabla modelo;
	private List<HashMap<String, String>> apuntados;
	private JPanel pnNombre;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JLabel lblDatos;
	private JPanel pnContacto;
	private JLabel lblTelfono;
	private JLabel lblEmail;
	private JTextField txtTelefono;
	private JTextField txtEmail;

	
	/**
	 * Create the dialog.
	 */
	public VentanaAviso(List<HashMap<String, String>>  apuntados) {
		setTitle("Usuarios a avisar");
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaAviso.class.getResource("/img/Logo_Olimpia.png")));
		this.apuntados=apuntados;
		setBounds(100, 100, 713, 411);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getLblAvisos(), BorderLayout.NORTH);
		contentPanel.add(getScrollPane(), BorderLayout.CENTER);
		contentPanel.add(getPnDatos(), BorderLayout.SOUTH);
		getContentPane().add(getButtonPane(), BorderLayout.SOUTH);
		getButtonPane().add(getOkButton());
	}

	private JButton getOkButton() {
		if(okButton==null) {
			okButton = new JButton("Aceptar");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		return okButton;
	}
	

	public JPanel getButtonPane() {
		if(buttonPane==null) {
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		}
		return buttonPane;
	}
	private JLabel getLblAvisos() {
		if (lblAvisos == null) {
			lblAvisos = new JLabel("Avisos");
			lblAvisos.setFont(new Font("Arial", Font.PLAIN, 18));
		}
		return lblAvisos;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
				scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JPanel getPnDatos() {
		if (pnDatos == null) {
			pnDatos = new JPanel();
			pnDatos.setLayout(new BorderLayout(0, 0));
			pnDatos.add(getPanel_1(), BorderLayout.WEST);
			pnDatos.add(getLblDatos(), BorderLayout.NORTH);
			pnDatos.add(getPnContacto(), BorderLayout.EAST);
			pnDatos.setVisible(false);
		}
		return pnDatos;
	}
	private JTable getTable() {
		if (table == null) {
			modelo = new ModeloTabla(new String[] {"Codigo","Instalacion","Hora","Fecha","Motivo"},0);
			RowSorter<ModeloTabla> sorter = new TableRowSorter<ModeloTabla>(modelo);
			table = new JTable();
			table.getTableHeader().setReorderingAllowed(false) ;
			for(int i=0; i<this.apuntados.size();i++) {
				String[] filas = new String[5];
					try {
					filas[0]=apuntados.get(i).get("codigo");
					filas[1]=DataBase.getNombreInstalacion(apuntados.get(i).get("instalacion"));
					filas[2]= apuntados.get(i).get("hora");
					filas[3]=apuntados.get(i).get("fecha");
					filas[4]=apuntados.get(i).get("motivo");
					modelo.addRow(filas);
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			
			table.setModel(modelo);
			table.setRowSorter(sorter);
			table.getRowSorter().toggleSortOrder(0);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					mostrarDatos((String)table.getValueAt(table.getSelectedRow(), 0));
				}

			});
		}
		return table;
	}
	private void mostrarDatos(String codUsuario) {
		try {
			Usuario u = DataBase.getUsuario(codUsuario);
			getTxtNombre().setText(u.getuNombre());
			getTxtApellidos().setText(u.getApellido());
			getTxtEmail().setText(u.getEmail());
			getTxtTelefono().setText(""+u.getTelefono());
			pnDatos.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JPanel getPanel_1() {
		if (pnNombre == null) {
			pnNombre = new JPanel();
			pnNombre.setLayout(new GridLayout(2, 2, 0, 0));
			pnNombre.add(getLblNombre());
			pnNombre.add(getTxtNombre());
			pnNombre.add(getLblApellidos());
			pnNombre.add(getTxtApellidos());
		}
		return pnNombre;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre");
			lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblNombre;
	}
	private JLabel getLblApellidos() {
		if (lblApellidos == null) {
			lblApellidos = new JLabel("Apellidos");
			lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
		return lblApellidos;
	}
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setEditable(false);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JTextField getTxtApellidos() {
		if (txtApellidos == null) {
			txtApellidos = new JTextField();
			txtApellidos.setEditable(false);
			txtApellidos.setColumns(10);
		}
		return txtApellidos;
	}
	private JLabel getLblDatos() {
		if (lblDatos == null) {
			lblDatos = new JLabel("Datos de contacto");
			lblDatos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDatos;
	}
	private JPanel getPnContacto() {
		if (pnContacto == null) {
			pnContacto = new JPanel();
			pnContacto.setLayout(new GridLayout(2, 2, 0, 0));
			pnContacto.add(getLblTelfono());
			pnContacto.add(getTxtTelefono());
			pnContacto.add(getLblEmail());
			pnContacto.add(getTxtEmail());
		}
		return pnContacto;
	}
	private JLabel getLblTelfono() {
		if (lblTelfono == null) {
			lblTelfono = new JLabel("Tel\u00E9fono:");
		}
		return lblTelfono;
	}
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Email:");
		}
		return lblEmail;
	}
	private JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setEditable(false);
			txtTelefono.setColumns(10);
		}
		return txtTelefono;
	}
	private JTextField getTxtEmail() {
		if (txtEmail == null) {
			txtEmail = new JTextField();
			txtEmail.setEditable(false);
			txtEmail.setColumns(10);
		}
		return txtEmail;
	}
}
