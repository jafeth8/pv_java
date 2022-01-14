package Punto_de_venta;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GananciasPorCategoria extends JDialog {
	private JTable table;
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GananciasPorCategoria dialog = new GananciasPorCategoria();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void mostrarGananciasCategoria(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Precio Unitario");
	    modelo.addColumn("Costo Unitario");
	    modelo.addColumn("Ganancias");
	    modelo.addColumn("Mas vendidos");
	    modelo.addColumn("Del mes");
	    modelo.addColumn("Del año");
	    
	    table.setModel(modelo);
	    String sql="";
	    if(valor.equals(""))
	    {
	        /*
	    	sql="SELECT SUM((detalle_ventas.precio_unitario)*detalle_ventas.cantidad) AS precio_unitario,SUM((detalle_ventas.costo_unitario)*detalle_ventas.cantidad) AS costo_unitario, "
	        		+ "SUM((detalle_ventas.precio_unitario)*detalle_ventas.cantidad)-SUM((detalle_ventas.costo_unitario)*detalle_ventas.cantidad) AS ganancias,productos.CATEGORIA AS mas_vendidos, MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta) FROM detalle_ventas "
	        		+ "JOIN ventas ON detalle_ventas.fk_id_venta=ventas.id_venta JOIN productos ON detalle_ventas.fk_id_producto=productos.ID GROUP BY MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta),productos.CATEGORIA ORDER BY ganancias DESC";
	        */
	    	sql="SELECT SUM((detalle_ventas.precio_unitario)*detalle_ventas.cantidad) AS precio_unitario,SUM((detalle_ventas.costo_unitario)*detalle_ventas.cantidad) AS costo_unitario, "
	        		+ "SUM((detalle_ventas.precio_unitario)*detalle_ventas.cantidad)-SUM((detalle_ventas.costo_unitario)*detalle_ventas.cantidad) AS ganancias,productos.CATEGORIA AS mas_vendidos, MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta) FROM detalle_ventas "
	        		+ "JOIN ventas ON detalle_ventas.fk_id_venta=ventas.id_venta JOIN productos ON detalle_ventas.fk_id_producto=productos.ID GROUP BY MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta),productos.CATEGORIA ORDER BY ganancias DESC";
	    }
	    else{
	    	sql="SELECT SUM((detalle_ventas.precio_unitario)*detalle_ventas.cantidad) AS precio_unitario,SUM((detalle_ventas.costo_unitario)*detalle_ventas.cantidad) AS costo_unitario, "
	    			+ "SUM((detalle_ventas.precio_unitario)*detalle_ventas.cantidad)-SUM((detalle_ventas.costo_unitario)*detalle_ventas.cantidad) AS ganancias,productos.CATEGORIA AS mas_vendidos, MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta) FROM detalle_ventas "
	    			+ "JOIN ventas ON detalle_ventas.fk_id_venta=ventas.id_venta JOIN productos ON detalle_ventas.fk_id_producto=productos.ID WHERE MONTH(ventas.fecha_venta)=MONTH('"+valor+"') AND YEAR(ventas.fecha_venta)=YEAR('"+valor+"') "
	    			+ "GROUP BY MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta),productos.CATEGORIA ORDER BY ganancias DESC";
	    }
	 
	    String []datos = new String [6];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=""+rs.getDouble(1);
	                
	                datos[1]=""+rs.getDouble(2);
	                datos[2]=""+rs.getDouble(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                datos[5]=rs.getString(6);
	                
	                modelo.addRow(datos);
	            }
	            table.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	
	
	/**
	 * Create the dialog.
	 */
	public GananciasPorCategoria() {
		setBounds(100, 100, 613, 502);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 97, 577, 355);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblGananciasPorCategoria = new JLabel("Ganancias por categoria");
		lblGananciasPorCategoria.setBounds(10, 72, 145, 14);
		getContentPane().add(lblGananciasPorCategoria);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(10, 11, 145, 23);
		getContentPane().add(dateChooser);
		
		JButton btnNewButton = new JButton("filtrar por la fecha");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fecha = "";
				try {
					SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
					fecha = formatoFecha.format(dateChooser.getDate());
					mostrarGananciasCategoria(fecha);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "No selecciono una fecha, se mostraran todos los registros");
					mostrarGananciasCategoria("");
				}
			}
		});
		btnNewButton.setBounds(165, 11, 135, 23);
		getContentPane().add(btnNewButton);
		
		mostrarGananciasCategoria("");

	}
}
