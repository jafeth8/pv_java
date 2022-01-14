package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class ProductosAgotados extends JDialog {
	static ProductosAgotados dialog = new ProductosAgotados();
	static JTable ProductosAgotados;

	String Usuario="Alejandro";
	String Contrasenia="12345";
	String URL="jdbc:mysql://localhost/tienda2015";
	
    java.sql.Connection conn=null;
    Statement stmnt=null;
    ResultSet rs=null;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ProductosAgotados() {
		
		
		setTitle("PRODUCTOS AGOTADOS");
		
		setBounds(100, 100, 605, 328);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 50, 533, 179);
		getContentPane().add(scrollPane);
		
		ProductosAgotados = new JTable();
		ProductosAgotados.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"CANTIDAD", "PRODUCTO"
			}
		));
		scrollPane.setViewportView(ProductosAgotados);
		
		JLabel lblProductosAgotados = new JLabel("PRODUCTOS AGOTADOS");
		lblProductosAgotados.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblProductosAgotados.setForeground(Color.RED);
		lblProductosAgotados.setBounds(167, 25, 280, 14);
		getContentPane().add(lblProductosAgotados);
		
		try {
			conn=DriverManager.getConnection(URL,Usuario,Contrasenia);
			stmnt=conn.createStatement();
			ConexionTableModel ctm=new ConexionTableModel("Select CANTIDAD,DESCRIPCION from productos WHERE CANTIDAD='0'");
			ProductosAgotados.setModel(ctm.getTablemodel());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		JButton btnImprimir = new JButton("IMPRIMIR");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnImprimir.setBounds(257, 255, 89, 23);
		getContentPane().add(btnImprimir);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\\\"+Ruta.imagen+"\\\\Abarrotes El Atoron\\\\Imagenes\\\\fondo_inicio.jpg"));
		label.setBounds(0, 0, 589, 290);
		getContentPane().add(label);
	}
}
