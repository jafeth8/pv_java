package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;

public class Imprimir extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static Imprimir dialog = new Imprimir();
	static JProgressBar barra ;
	private final JLabel label = new JLabel("");
	static JLabel Por ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Imprimir() {
		setBounds(500, 300, 495, 130);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			barra = new JProgressBar();
			barra.setBounds(22, 40, 382, 38);
			contentPanel.add(barra);
		}
		{
			JLabel lblImprimiendoTicket = new JLabel("IMPRIMIENDO TICKET");
			lblImprimiendoTicket.setFont(new Font("Aharoni", Font.PLAIN, 15));
			lblImprimiendoTicket.setBounds(133, 15, 180, 14);
			contentPanel.add(lblImprimiendoTicket);
		}
		{
			Por = new JLabel("");
			Por.setFont(new Font("Aharoni", Font.PLAIN, 19));
			Por.setBounds(414, 52, 46, 14);
			contentPanel.add(Por);
		}
		{
			label.setIcon(new ImageIcon("C:\\Program Files\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
			label.setBounds(0, 0, 479, 89);
			contentPanel.add(label);
		}
	}

}
