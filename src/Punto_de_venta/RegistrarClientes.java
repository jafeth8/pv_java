package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class RegistrarClientes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldDireccion;
	private JTextField textFieldTelefono;
	static JComboBox Tipodeclientes ;
	static Vector<String>  tipos = new Vector<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarClientes dialog = new RegistrarClientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarClientes() {
		setTitle("Registrar Clientes");
		setBounds(100, 100, 393, 353);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel labelNombre = new JLabel("Nombre");
		labelNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelNombre.setForeground(Color.WHITE);
		labelNombre.setBounds(10, 23, 46, 14);
		contentPanel.add(labelNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(10, 48, 223, 20);
		contentPanel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel labelApellidos = new JLabel("Apellidos");
		labelApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelApellidos.setBounds(10, 79, 120, 14);
		labelApellidos.setForeground(Color.WHITE);
		contentPanel.add(labelApellidos);
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setBounds(10, 104, 223, 20);
		contentPanel.add(textFieldApellidos);
		textFieldApellidos.setColumns(10);
		
		JLabel labelDireccion = new JLabel("Direccion");
		labelDireccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelDireccion.setBounds(10, 135, 86, 14);
		labelDireccion.setForeground(Color.WHITE);
		contentPanel.add(labelDireccion);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setBounds(10, 160, 223, 20);
		contentPanel.add(textFieldDireccion);
		textFieldDireccion.setColumns(10);
		
		JLabel labelTelefono = new JLabel("Telefono");
		labelTelefono.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelTelefono.setBounds(10, 191, 86, 14);
		labelTelefono.setForeground(Color.WHITE);
		contentPanel.add(labelTelefono);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(10, 216, 223, 20);
		contentPanel.add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		
		JButton btnRegistrar = new JButton("Registrar cliente");
		btnRegistrar.setBounds(235, 280, 132, 23);
		contentPanel.add(btnRegistrar);
		
		
		
		btnRegistrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (textFieldNombre.getText().equals("") || textFieldApellidos.getText().equals("") || textFieldDireccion.getText().equals("") || textFieldTelefono.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Deves de rellenar todo los campos","Mensaje de aviso",JOptionPane.ERROR_MESSAGE);
				}else {
				
					String tipos ="1";// 1 es cliente de tipo no
					
					try {
						RegistrarCliente(textFieldNombre.getText(),"", textFieldApellidos.getText(), textFieldDireccion.getText(), textFieldTelefono.getText(), tipos);
						JOptionPane.showMessageDialog(null, "SE REGISTRO CORRECTAMENTE AL CLIENTE","mensaje de aviso",JOptionPane.INFORMATION_MESSAGE);
						limpiarCampos();
						dispose();
					} catch (IOException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			  }
			}
			
		});
		
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 377, 321);
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
