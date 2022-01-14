package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RespaldoAutomatico extends JDialog {

	private final JPanel contentPanel = new JPanel();
	SqlOperaciones instanciaSql=new SqlOperaciones();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RespaldoAutomatico dialog = new RespaldoAutomatico();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RespaldoAutomatico() {
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(36, 201, 357, 23);
		progressBar.setStringPainted(true);
		contentPanel.add(progressBar);

		JLabel lblNewLabel = new JLabel("Creando Repaldo de datos..");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(36, 158, 357, 21);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPanel.add(lblNewLabel);
		
		JLabel labelPictureBackup = new JLabel("");
		labelPictureBackup.setHorizontalAlignment(SwingConstants.CENTER);
		labelPictureBackup.setBounds(36, 50, 357, 96);
		contentPanel.add(labelPictureBackup);
		labelPictureBackup.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\backup.png"));
		
		/*-----------RESPALDO DE DATOS AUTOMATICO----------------------*/
		String ruta=instanciaSql.obtenerRutaRespaldo();
		System.err.println("ruta prro: "+ ruta);
		ProcesoRespaldoBd procesoRespaldo=new ProcesoRespaldoBd(ruta);
		EventoProgreso eventoPropertyListener=new EventoProgreso(progressBar);
		procesoRespaldo.addPropertyChangeListener(eventoPropertyListener);
		procesoRespaldo.execute();
		

		/*-----------FIN DE RESPALDO DE DATOS AUTOMATICO---------------*/
	}
}
