package Punto_de_venta;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ComisionesSocios extends JDialog {
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComisionesSocios dialog = new ComisionesSocios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void mostrarComisionesSocios(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id cliente");
	    modelo.addColumn("Nombre");
	    modelo.addColumn("Comision correspondiente");
	    modelo.addColumn("Del mes");
	    modelo.addColumn("Del año");
	    
	    table.setModel(modelo);
	    String sql="";
	    if(valor.equals(""))
	    {
	        sql="SELECT clientes.id_cliente,clientes.nombre, SUM(ventas.comision),MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta) FROM "
	        		+ "ventas JOIN clientes ON ventas.fk_id_cliente=clientes.id_cliente WHERE clientes.fk_id_tipo_cliente=2 GROUP BY clientes.id_cliente,clientes.nombre,MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta)";
	    }
	    else{
	        //sql="SELECT * FROM productos WHERE codpro='"+valor+"'";
	        //sql="SELECT * FROM ventas WHERE id_venta like '%"+valor+"%'";
	    }
	 
	    String []datos = new String [5];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                
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
	public ComisionesSocios() {
		setBounds(100, 100, 800, 407);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 58, 764, 299);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		mostrarComisionesSocios("");
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 800, 407);
		getContentPane().add(label_10);
	}

}
