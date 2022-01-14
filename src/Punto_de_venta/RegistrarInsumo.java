package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class RegistrarInsumo extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDescripcion;
	private JTextField textFieldCosto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrarInsumo frame = new RegistrarInsumo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegistrarInsumo() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 424, 304);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelDescripcion = new JLabel("DESCRIPCION DEL INSUMO");
		labelDescripcion.setForeground(Color.WHITE);
		labelDescripcion.setBounds(10, 11, 187, 14);
		contentPane.add(labelDescripcion);
		
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setBounds(10, 36, 213, 20);
		contentPane.add(textFieldDescripcion);
		textFieldDescripcion.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("COSTO DEL INSUMO");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 67, 147, 14);
		contentPane.add(lblNewLabel);
		
		textFieldCosto = new JTextField();
		textFieldCosto.setBounds(10, 92, 213, 20);
		contentPane.add(textFieldCosto);
		textFieldCosto.setColumns(10);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones operacion=new SqlOperaciones();
				String fecha=LocalDate.now().toString();
				String descripcion=textFieldDescripcion.getText();
				String costo=textFieldCosto.getText();
				
				double costoDouble=Double.parseDouble(costo);
				operacion.insertTablaInsumos(descripcion, costoDouble, fecha);
				JOptionPane.showMessageDialog(null,"operacion realizada correctamente");
				dispose();
				
				
			}
		});
		btnRegistrar.setBounds(10, 138, 89, 23);
		contentPane.add(btnRegistrar);
		
		JLabel labelFondo = new JLabel("");
		labelFondo.setBounds(0, 0, 418, 275);
		labelFondo.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		contentPane.add(labelFondo);
	}
}
