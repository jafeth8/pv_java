package Punto_de_venta;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MostrarSocios extends JDialog {
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
					MostrarSocios dialog = new MostrarSocios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void visualizarSocios() {
		Statement st=null;
		ResultSet rs=null;
    	DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id cliente");
        modelo.addColumn("Nombre");
        modelo.addColumn("Segundo nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Direccion");
        modelo.addColumn("Telefono");
       
        String sql;
        sql="SELECT * FROM clientes where fk_id_tipo_cliente=2";
        String []datos = new String [6];
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()){
            	
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                modelo.addRow(datos);
               
            }
            table.setModel(modelo);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }finally {
        	try {
        		st.close();
        		rs.close();
				cn.close();
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
    }
	

	/**
	 * Create the dialog.
	 */
	public MostrarSocios() {
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 700, 438);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 664, 297);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnAsignarCompra = new JButton("Asignar compra");
		btnAsignarCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=table.getSelectedRow();
				if (fila>=0) {
					
					PuntoDeVenta.setClienteId=Integer.parseInt(table.getValueAt(fila, 0).toString());
					dispose();
					
				}else {
					JOptionPane.showMessageDialog(null, "no selecciono cliente");
					
				}
			}
		});
		btnAsignarCompra.setBounds(10, 351, 163, 37);
		getContentPane().add(btnAsignarCompra);
		visualizarSocios();

	}

}
