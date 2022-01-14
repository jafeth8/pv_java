package Punto_de_venta;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class CargandoBarra extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static CargandoBarra dialog = new CargandoBarra();
	static JProgressBar barra;

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
	public CargandoBarra() {
		setBounds(500, 300, 396, 97);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			barra = new JProgressBar();
			barra.setBounds(10, 11, 364, 31);
			contentPanel.add(barra);
		}
		{
			JLabel label = new JLabel("");
			label.setIcon(new ImageIcon("C:\\Program Files\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
			label.setBounds(0, 0, 380, 59);
			contentPanel.add(label);
		}
	}

}
