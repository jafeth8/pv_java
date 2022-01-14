package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.ListSelectionModel;

public class MostrarClientes extends JDialog {
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldDireccion;
	private JTextField textFieldTelefono;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MostrarClientes dialog = new MostrarClientes();
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
	public MostrarClientes() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 790, 501);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 154, 754, 251);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JButton btnAsignar = new JButton("Asignar Compra");
		btnAsignar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=table.getSelectedRow();
				if (fila>=0) {
					
					PuntoDeVenta.setClienteId=Integer.parseInt(table.getValueAt(fila, 0).toString());
					PuntoDeVenta.NombreClienteApartados=table.getValueAt(fila, 1).toString();	
					//JOptionPane.showMessageDialog(null, PuntoDeVenta.NombreClienteApartados);
					dispose();
					
				}else {
					JOptionPane.showMessageDialog(null, "no selecciono cliente");
					
				}
			}
		});
		btnAsignar.setBounds(10, 416, 126, 35);
		contentPanel.add(btnAsignar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new CompoundBorder(null, new CompoundBorder(new BevelBorder(BevelBorder.RAISED, new Color(240, 240, 240), new Color(255, 255, 255), new Color(105, 105, 105), new Color(160, 160, 160)), new LineBorder(new Color(180, 180, 180), 5))), "Crear nuevo cliente", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(128, 0, 0)));
		panel.setBounds(10, 15, 754, 128);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel labelNombre = new JLabel("Nombre");
		labelNombre.setBounds(20, 22, 86, 14);
		panel.add(labelNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(20, 43, 126, 20);
		panel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel labelApellidos = new JLabel("Apellidos");
		labelApellidos.setBounds(156, 22, 126, 14);
		panel.add(labelApellidos);
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setBounds(156, 43, 126, 20);
		panel.add(textFieldApellidos);
		textFieldApellidos.setColumns(10);
		
		JLabel label_direccion = new JLabel("Direccion");
		label_direccion.setBounds(292, 22, 145, 14);
		panel.add(label_direccion);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setBounds(291, 43, 203, 20);
		panel.add(textFieldDireccion);
		textFieldDireccion.setColumns(10);
		
		JLabel labelTelefono = new JLabel("Numero de telefono");
		labelTelefono.setBounds(504, 22, 145, 14);
		panel.add(labelTelefono);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(504, 43, 145, 20);
		panel.add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		JButton btnRegistrar = new JButton("Registrar cliente");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldNombre.getText().equals("") || textFieldApellidos.getText().equals("") || textFieldDireccion.getText().equals("") || textFieldTelefono.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Debes de rellenar todos los campos","Mensaje de aviso",JOptionPane.ERROR_MESSAGE);
				}else {
				
					String tipos ="1";// 1 es cliente de tipo no
					
					try {
						RegistrarCliente(textFieldNombre.getText(),"", textFieldApellidos.getText(), textFieldDireccion.getText(), textFieldTelefono.getText(), tipos);
						JOptionPane.showMessageDialog(null, "SE REGISTRO CORRECTAMENTE AL CLIENTE","mensaje de aviso",JOptionPane.INFORMATION_MESSAGE);
						limpiarCampos();
						visualizarClientes("");
					} catch (IOException | SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
			  }
			}
		});
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRegistrar.setBounds(20, 85, 136, 32);
		panel.add(btnRegistrar);
		
		JButton btnBuscarPorNombre = new JButton("Buscar por nombre");
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
		btnBuscarPorNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBuscarPorNombre.setBounds(168, 85, 162, 32);
		panel.add(btnBuscarPorNombre);
		
		JButton btnMostrarTodo = new JButton("Mostrar todo");
		btnMostrarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualizarClientes("");
			}
		});
		btnMostrarTodo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarTodo.setBounds(340, 85, 162, 32);
		panel.add(btnMostrarTodo);
		
		visualizarClientes("");
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 774, 462);
		contentPanel.add(label_10);
	}
	
	
	
	public void limpiarCampos() {
		textFieldApellidos.setText("");
		textFieldDireccion.setText("");
		textFieldNombre.setText("");
		
		textFieldTelefono.setText("");
		
	}
	
	public void RegistrarCliente(String nombre,String segundoNombre,String apellidos,String direccion, String numero,String tipocliente) throws IOException, SQLException
	{
		 int tipoclientes = Integer.parseInt(tipocliente);
		 java.sql.Connection conn=null;
		 Statement stmnt=null;
		 //ResultSet rs=null;
		 conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
		 stmnt=conn.createStatement();
		 stmnt.executeUpdate("INSERT INTO `"+Ruta.database+"`.`clientes`"
		 		+ " (`id_cliente`, `nombre`, `segundo_nombre`, `apellidos`, `direccion`,"
		 		+ " `numero_telefono`, `fk_id_tipo_cliente`) VALUES"
		 		+ " (NULL, '"+nombre+"',"
		 		+ " '"+segundoNombre+"', "
		 		+ "'"+apellidos+"',"
		 		+ " '"+direccion+"', "
		 		+ "'"+numero+"',"
		 		+ " '"+tipoclientes+"');");
		 
		}
}
