package Punto_de_venta;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Font;

public class VerVentas extends JDialog {
	JLabel labelGanacias;

	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    private JTable tablaProductos;
    private JTable tablaMasDetalles;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerVentas dialog = new VerVentas();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void mostrardatos(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id venta");
	    modelo.addColumn("Total de la venta");
	    modelo.addColumn("Fecha de la venta");
	    modelo.addColumn("Pago del cliente");
	    modelo.addColumn("Cambio Entregado al cliente");
	    modelo.addColumn("Tipo de venta");
	    String sql="";
	    //table.setModel(modelo);
	    tablaProductos.setModel(modelo);
	    if(valor.equals(""))
	    {
	        sql="SELECT * FROM ventas";
	    }
	    else{
	        sql="SELECT * FROM ventas WHERE fecha_venta='"+valor+"'";
	    }
	 
	    String []datos = new String [6];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                datos[5]=rs.getString(6);
	                modelo.addRow(datos);
	            }
	            
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	
	
	public void mostrarMasDetalles(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id venta");
	    modelo.addColumn("Total de la venta");
	    modelo.addColumn("Fecha de la venta");
	    modelo.addColumn("Descripcion producto");
	    modelo.addColumn("Precio unitario");
	    modelo.addColumn("Costo unitario");
	    modelo.addColumn("Descuento");
	    modelo.addColumn("Categoria");
	    modelo.addColumn("Cantidad");
	    String sql="";
	    //table.setModel(modelo);
	    tablaMasDetalles.setModel(modelo);
	    if(valor.equals(""))
	    {
	        sql="SELECT * FROM ventas";
	    }
	    else{
	        //sql="SELECT * FROM ventas WHERE fecha_venta='"+valor+"'";
	    	sql="SELECT ventas.id_venta,ventas.total_venta,ventas.fecha_venta,productos.DESCRIPCION,detalle_ventas.precio_unitario,detalle_ventas.costo_unitario,detalle_ventas.descuento, productos.CATEGORIA, detalle_ventas.cantidad "
	    			+ "FROM ventas JOIN detalle_ventas ON id_venta=detalle_ventas.fk_id_venta "
	    			+ "JOIN productos ON detalle_ventas.fk_id_producto=productos.ID WHERE ventas.fecha_venta='"+valor+"'";
	    }
	 
	    String []datos = new String [9];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                datos[5]=rs.getString(6);
	                datos[6]=rs.getString(7);
	                datos[7]=rs.getString(8);
	                datos[8]=rs.getString(9);
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
	public VerVentas() {
		setBounds(100, 100, 1083, 498);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ventas realizadas");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 55, 146, 18);
		getContentPane().add(lblNewLabel);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(167, 21, 118, 23);
		getContentPane().add(dateChooser);
		
		
		JButton btnNewButton = new JButton("filtrar por la fecha");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fecha = "";
				SqlOperaciones instancia=new SqlOperaciones();
				try {

					SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
					fecha = formatoFecha.format(dateChooser.getDate());
					mostrardatos(fecha);
					double ganancias =instancia.obtenerGanaciasPorDia(fecha);
					labelGanacias.setText("$ "+ganancias);
					
					mostrarMasDetalles(fecha);
					
				} catch (NullPointerException vacio) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "No selecciono una fecha, se mostraran todos los registros");
					mostrardatos("");
					

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 21, 135, 23);
		getContentPane().add(btnNewButton);
		
		JLabel labelTitulo = new JLabel("Ganancias por dia:");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTitulo.setBounds(673, 405, 135, 43);
		getContentPane().add(labelTitulo);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 75, 1047, 319);
		getContentPane().add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Ventas", null, scrollPane, null);
		
		tablaProductos = new JTable();
		scrollPane.setViewportView(tablaProductos);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Mas detalle de ganacias por dia", null, scrollPane_1, null);
		
		tablaMasDetalles = new JTable();
		scrollPane_1.setViewportView(tablaMasDetalles);
		
		mostrardatos("");
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemHistorialVenta = new JMenuItem("Ver historial de esta venta");
		menuItemHistorialVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=tablaProductos.getSelectedRow();
				int id_venta=Integer.parseInt(tablaProductos.getValueAt(fila,0).toString());
				HistorialVentas.idVenta=id_venta;
				HistorialVentas instancia =new HistorialVentas();
				instancia.setVisible(true);
			}
		});
		popupMenu.add(menuItemHistorialVenta);
		tablaProductos.setComponentPopupMenu(popupMenu);
		
		JPanel panel = new JPanel();
		panel.setBounds(818, 405, 239, 43);
		getContentPane().add(panel);
		
		labelGanacias = new JLabel("");
		panel.add(labelGanacias);
		labelGanacias.setForeground(Color.BLACK);
		labelGanacias.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 1067, 459);
		getContentPane().add(label_10);
		
		
	}
}
