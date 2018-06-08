package igu.admin.reservas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Toolkit;

public class VentanaTicket extends JDialog {

	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public VentanaTicket(String ticket) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaTicket.class.getResource("/img/Logo_Olimpia.png")));
		setTitle("Centro Olimpia: Ticket");
		setBounds(100, 100, 450, 476);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				JTextPane textPane = new JTextPane();
				scrollPane.setViewportView(textPane);
				textPane.setText(ticket);
			}
		}
	}

}
