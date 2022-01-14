package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.omg.DynamicAny._DynEnumStub;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Porcentaje extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldPorcentaje;
	private JLabel labelMostrarPorcentaje;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Porcentaje frame = new Porcentaje();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void mostrarPorcentaje() {
		SqlOperaciones instancia=new SqlOperaciones();
		int porcentaje=instancia.obtenerPorcentajeComision();
		labelMostrarPorcentaje.setText(""+porcentaje+" %");
	}

	/**
	 * Create the frame.
	 */
	public Porcentaje() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 415, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelTituloPorcentaje = new JLabel("PORCENTAJE ACTUAL ASIGNADA A CADA COMPRA:");
		labelTituloPorcentaje.setHorizontalAlignment(SwingConstants.CENTER);
		labelTituloPorcentaje.setBounds(10, 11, 365, 14);
		contentPane.add(labelTituloPorcentaje);
		
		labelMostrarPorcentaje = new JLabel("");
		labelMostrarPorcentaje.setHorizontalAlignment(SwingConstants.CENTER);
		labelMostrarPorcentaje.setBounds(10, 36, 365, 14);
		contentPane.add(labelMostrarPorcentaje);
		/************************************/
		mostrarPorcentaje();
		/************************************/
		JLabel lblNewLabel = new JLabel("ACTUALIZAR PORCENTAJE A:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 61, 365, 14);
		contentPane.add(lblNewLabel);
		
		textFieldPorcentaje = new JTextField();
		textFieldPorcentaje.setBounds(80, 86, 238, 20);
		contentPane.add(textFieldPorcentaje);
		textFieldPorcentaje.setColumns(10);
		
		JButton btnActualizar = new JButton("ACTUALIZAR");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instancia = new SqlOperaciones();
				int cantidad;
				String cantidadTexto=textFieldPorcentaje.getText();
				cantidad=Integer.parseInt(cantidadTexto);
				instancia.ActualizarPorcentajeComision(cantidad);
				JOptionPane.showMessageDialog(null,"operacion realizada exitosamente");
				dispose();
			}
		});
		btnActualizar.setBounds(80, 117, 238, 23);
		contentPane.add(btnActualizar);
	}
}
