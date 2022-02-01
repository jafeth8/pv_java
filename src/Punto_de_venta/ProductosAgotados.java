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
import java.sql.Connection;
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
	static JTable tablaProductosAgotados;
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    
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
	

	public void mostrarProductosAgotados(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("CANTIDAD");
	    modelo.addColumn("PRODUCTO");
	    
	    String sql="";
	    tablaProductosAgotados.setModel(modelo);
	    if(valor.equals(""))
	    {
	        sql="Select CANTIDAD,DESCRIPCION from productos WHERE CANTIDAD='0'";
	    }
	    else{
	        sql="";
	    }
	 
	    String []datos = new String [2];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                modelo.addRow(datos);
            }
            
        } catch (SQLException ex) {
        	ex.printStackTrace();
            System.out.println(ex.getMessage());
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
		
		tablaProductosAgotados = new JTable();

		scrollPane.setViewportView(tablaProductosAgotados);
		
		JLabel lblProductosAgotados = new JLabel("PRODUCTOS AGOTADOS");
		lblProductosAgotados.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblProductosAgotados.setForeground(Color.RED);
		lblProductosAgotados.setBounds(167, 25, 280, 14);
		getContentPane().add(lblProductosAgotados);
		
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
		mostrarProductosAgotados("");
		
	}
}
