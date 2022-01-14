package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Invetarios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel contentPane;
	private JTable TablaInvetario;
	static Invetarios dialog = new Invetarios();
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
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

	public void mostrardatos(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id producto");
	    modelo.addColumn("Descripcion");
	    modelo.addColumn("Cantidad");
	    String sql="";
	    //table.setModel(modelo);
	    TablaInvetario.setModel(modelo);
	    
	    TableColumn columnaId=TablaInvetario.getColumn("Id producto");
		columnaId.setMinWidth(70);
		columnaId.setPreferredWidth(70);
		columnaId.setMaxWidth(75);
		TableColumn columnaCantidad=TablaInvetario.getColumn("Cantidad");
		columnaCantidad.setMinWidth(100);
		columnaCantidad.setPreferredWidth(100);
		columnaCantidad.setMaxWidth(105);
	    if(valor.equals(""))
	    {
	        sql="SELECT ID,DESCRIPCION,CANTIDAD FROM productos";
	    }
	    else{
	        sql="SELECT ID,DESCRIPCION,CANTIDAD FROM productos ORDER BY ID DESC";
	    }
	 
	    String []datos = new String [3];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                //datos[3]=rs.getString(4);
	                //datos[4]=rs.getString(5);
	                //datos[5]=rs.getString(6);
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
	public Invetarios() {
		setTitle("INVENTARIO");
		
		setBounds(100, 100, 592, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 556, 387);
		contentPane.add(scrollPane);
		
		TablaInvetario = new JTable();
		scrollPane.setViewportView(TablaInvetario);
				
		JLabel lblInventario = new JLabel("INVENTARIO");
		lblInventario.setHorizontalAlignment(SwingConstants.CENTER);
		lblInventario.setForeground(Color.RED);
		lblInventario.setBackground(Color.RED);
		lblInventario.setFont(new Font("Aharoni", Font.PLAIN, 17));
		lblInventario.setBounds(10, 11, 556, 19);
		contentPane.add(lblInventario);
		
		JButton btnDescendente = new JButton("Ultimos agregados");
		btnDescendente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrardatos("Descendente");
			}
		});
		btnDescendente.setBounds(10, 41, 143, 23);
		contentPane.add(btnDescendente);
		
		JButton btnAscendente = new JButton("Primeros agregados");
		btnAscendente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrardatos("");
			}
		});
		btnAscendente.setBounds(174, 41, 151, 23);
		contentPane.add(btnAscendente);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label.setBounds(0, 0, 576, 493);
		contentPane.add(label);
		mostrardatos("");
	}
}
