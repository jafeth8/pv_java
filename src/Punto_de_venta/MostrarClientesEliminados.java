package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;

public class MostrarClientesEliminados extends JDialog {
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MostrarClientesEliminados dialog = new MostrarClientesEliminados();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void visualizarClientes() {
		Statement st=null;
		ResultSet rs=null;
    	DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id cliente");
        modelo.addColumn("Nombre");
        
        modelo.addColumn("apellidos");
        modelo.addColumn("Direccion");
        modelo.addColumn("Telefono");
        //modelo.addColumn("Tipo Cliente");
       
        String sql;
        sql="SELECT id_cliente,clientes.nombre,apellidos,direccion,numero_telefono, ctipo_clientes.nombre "
        		+ "FROM clientes JOIN ctipo_clientes ON clientes.fk_id_tipo_cliente=ctipo_clientes.id_tipo_cliente WHERE fk_id_state=3";
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
                //datos[6]=rs.getString(7);
                
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
				
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
    }
	
	
	/**
	 * Create the dialog.
	 */
	public MostrarClientesEliminados() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 732, 433);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 24, 696, 289);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		JPopupMenu popupMenu = new JPopupMenu();
	
		JMenuItem menuItemEliminar = new JMenuItem("Reactivar Cliente");
		menuItemEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instancia=new SqlOperaciones();
				int fila=table.getSelectedRow();
				int id_cliente=Integer.parseInt(table.getValueAt(fila,0).toString());
				
			    if(fila>=0){
			    	instancia.actualizarStateTablaClientes(id_cliente, 1); // 3 es eliminado en la tabla sstate
					JOptionPane.showMessageDialog(null, "Cliente Reactivado satisfactoriamente");
					visualizarClientes();
			    }
			    else{
			    JOptionPane.showMessageDialog(null,"no seleciono fila");
			    }
				
			}
		});
		
		
		
		popupMenu.add(menuItemEliminar);
		table.setComponentPopupMenu(popupMenu);
		
		
		visualizarClientes();
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 716, 401);
		contentPanel.add(label_10);
	}
}
