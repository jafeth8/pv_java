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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerProductosEliminados extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerProductosEliminados dialog = new VerProductosEliminados();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	  public void mostrardatos(String valor) throws SQLException{
		    DefaultTableModel modelo= new DefaultTableModel();
		    modelo.addColumn("ID");
		    modelo.addColumn("CODIGO_BARRA");
		    modelo.addColumn("CANTIDAD");
		    modelo.addColumn("DESCRIPCION");
		    modelo.addColumn("PRECIO UNITARIO");
		    modelo.addColumn("COSTO PRODUCTO");
		    table.setModel(modelo);
		    String sql="";
		    if(valor.equals(""))
		    {
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
		    	sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO FROM productos WHERE fk_id_state=3";
		    }
		    else{
		    	sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%' AND fk_id_state=3 ";
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%'";
		    }
		 
		    Object []datos = new Object [6];
		        try {
		            Statement st = cn.createStatement();
		            ResultSet rs = st.executeQuery(sql);
		            while(rs.next()){
		            	
		            	datos[0]=rs.getInt(1);
		                datos[1]=rs.getDouble(2);
		                datos[2]=rs.getInt(3);
		                datos[3]=rs.getString(4);
		                datos[4]=rs.getDouble(5);
		                datos[5]=rs.getDouble(6);
		                
		                modelo.addRow(datos);
		            }
		            table.setModel(modelo);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }finally {
		        	
					
				}
		    
		    }

	/**
	 * Create the dialog.
	 */
	public VerProductosEliminados() {
		setBounds(100, 100, 800, 458);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 38, 764, 370);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 119, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar por descripcion");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String texto=textField.getText();
				try {
					mostrardatos(texto);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(139, 10, 178, 23);
		contentPanel.add(btnBuscar);
		try {
			mostrardatos("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemHistorialVenta = new JMenuItem("Reactivar producto");
		menuItemHistorialVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instancia=new SqlOperaciones();
				int fila=table.getSelectedRow();
				int id_producto=Integer.parseInt(table.getValueAt(fila,0).toString());
				instancia.actualizarStateTablaProductos(id_producto, 1);
				try {
					mostrardatos("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		popupMenu.add(menuItemHistorialVenta);
		table.setComponentPopupMenu(popupMenu);
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 800, 458);
		contentPanel.add(label_10);
		
	}
}
