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

public class MostrarClientes2 extends JDialog {
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private final JPanel panel = new JPanel();
	private final JLabel lblId = new JLabel("Id");
	private final JTextField textField_id = new JTextField();
	private final JLabel labelNombre = new JLabel("Nombre");
	private final JTextField textFieldNombre = new JTextField();
	private final JLabel labelApellidos = new JLabel("Apellidos");
	private final JTextField textFieldApellidos = new JTextField();
	private final JLabel labelDireccion = new JLabel("Direccion");
	private final JTextField textField_direccion = new JTextField();
	private final JLabel labelTelefono = new JLabel("Telefono");
	private JTextField textFieldTelefono;
	private final JButton btnBuscarPorNombre = new JButton("Buscar por nombre");
	private final JButton btnMostrarTodo = new JButton("Mostrar todo");
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MostrarClientes2 dialog = new MostrarClientes2();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void visualizarClientes(String nombreCliente) {
		Statement st=null;
		ResultSet rs=null;
    	DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id cliente");
        modelo.addColumn("Nombre");
        
        modelo.addColumn("apellidos");
        modelo.addColumn("Direccion");
        modelo.addColumn("Telefono");
        modelo.addColumn("Tipo Cliente");
       
        String sql="";
        if(nombreCliente.equals("")) {
        	sql="SELECT id_cliente,clientes.nombre,apellidos,direccion,numero_telefono, ctipo_clientes.nombre FROM "
        	+ "clientes JOIN ctipo_clientes ON clientes.fk_id_tipo_cliente=ctipo_clientes.id_tipo_cliente WHERE fk_id_state=1";
        }else {
        	sql="SELECT id_cliente,clientes.nombre,apellidos,direccion,numero_telefono, ctipo_clientes.nombre "
            + "FROM clientes JOIN ctipo_clientes ON clientes.fk_id_tipo_cliente=ctipo_clientes.id_tipo_cliente WHERE clientes.nombre LIKE '%"+nombreCliente+"%' AND fk_id_state=1";
        }

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
				
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
    }
	
	
	/**
	 * Create the dialog.
	 */
	public MostrarClientes2() {
		textField_direccion.setBounds(303, 36, 220, 20);
		textField_direccion.setColumns(10);
		textFieldApellidos.setBounds(188, 36, 105, 20);
		textFieldApellidos.setColumns(10);
		textFieldNombre.setBounds(71, 36, 107, 20);
		textFieldNombre.setColumns(10);
		textField_id.setEditable(false);
		textField_id.setBounds(10, 36, 51, 20);
		textField_id.setColumns(10);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 732, 527);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 188, 696, 289);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 696, 155);
		
		contentPanel.add(panel);
		panel.setLayout(null);
		lblId.setBounds(10, 11, 46, 14);
		
		panel.add(lblId);
		
		panel.add(textField_id);
		labelNombre.setBounds(73, 11, 105, 14);
		
		panel.add(labelNombre);
		
		panel.add(textFieldNombre);
		labelApellidos.setBounds(188, 11, 105, 14);
		
		panel.add(labelApellidos);
		
		panel.add(textFieldApellidos);
		labelDireccion.setBounds(303, 11, 115, 14);
		
		panel.add(labelDireccion);
		
		panel.add(textField_direccion);
		labelTelefono.setBounds(533, 11, 105, 14);
		
		panel.add(labelTelefono);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(533, 36, 105, 20);
		panel.add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		JButton btnActualizarDatos = new JButton("Actualizar Datos");
		btnActualizarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instancia = new SqlOperaciones();
				if(textFieldNombre.getText().equals("")|| textFieldApellidos.getText().equals("")|| textField_direccion.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"asegurese de que algunos campos no esten vacios campos no esten vacios");
				}else {
					instancia.actualizarDatosClientes(textField_id.getText(), textFieldNombre.getText(), 
							"", textFieldApellidos.getText(), textField_direccion.getText(), textFieldTelefono.getText());
					visualizarClientes("");
				}
			}
		});
		btnActualizarDatos.setBounds(10, 83, 168, 23);
		panel.add(btnActualizarDatos);
		btnBuscarPorNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre=textFieldNombre.getText().toString();
				if(nombre.equals("")) {
					JOptionPane.showMessageDialog(null,"El campo nombre esta vacio","Mensaje",JOptionPane.WARNING_MESSAGE);
				}else {
					visualizarClientes(textFieldNombre.getText().toString());
				}
			}
		});
		btnBuscarPorNombre.setBounds(188, 83, 168, 23);
		
		panel.add(btnBuscarPorNombre);
		btnMostrarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualizarClientes("");
			}
		});
		btnMostrarTodo.setBounds(366, 83, 168, 23);
		
		panel.add(btnMostrarTodo);
		
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemModificar = new JMenuItem("modificar");
		menuItemModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=table.getSelectedRow();
				//int id_producto=Integer.parseInt(table.getValueAt(fila,0).toString());
				
			    if(fila>=0){
			    	try {
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
			        textField_id.setText(table.getValueAt(fila, 0).toString());
			        textFieldNombre.setText(table.getValueAt(fila, 1).toString());
			        
			        textFieldApellidos.setText(table.getValueAt(fila, 2).toString());
			        textField_direccion.setText(table.getValueAt(fila, 3).toString());
			        textFieldTelefono.setText(table.getValueAt(fila, 4).toString());
			    }
			    else{
			    JOptionPane.showMessageDialog(null,"no seleciono fila");
			    }
				
			}
		});
		
		
		JMenuItem menuItemEliminar = new JMenuItem("Eliminar");
		menuItemEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instancia=new SqlOperaciones();
				int fila=table.getSelectedRow();
				int id_cliente=Integer.parseInt(table.getValueAt(fila,0).toString());
				
			    if(fila>=0){
			    	instancia.actualizarStateTablaClientes(id_cliente, 3); // 3 es eliminado en la tabla sstate
					JOptionPane.showMessageDialog(null, "Cliente eliminado satisfactoriamente");
					visualizarClientes("");
			    }
			    else{
			    JOptionPane.showMessageDialog(null,"no seleciono fila");
			    }
				
			}
		});
		
		
		popupMenu.add(menuItemModificar);
		popupMenu.add(menuItemEliminar);
		table.setComponentPopupMenu(popupMenu);
		
		
		visualizarClientes("");
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 716, 488);
		contentPanel.add(label_10);
	}
}
