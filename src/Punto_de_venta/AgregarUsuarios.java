package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;


import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

public class AgregarUsuarios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static JTextField Usuarios;
	static JPasswordField Contrasenia;
	static JPasswordField Contrasenia2;
	
	static Vector<String> vee = new Vector<String>();
	static AgregarUsuarios dialog = new AgregarUsuarios();
	static JTable tableUser;

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

	/**
	 * Create the dialog.
	 */
	public AgregarUsuarios() {
		
		setTitle("AGREGAR USUARIOS");
		setBounds(100, 100, 662, 252);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
        
		
		JLabel lblIngresa = new JLabel("NOMBRE DE USUARIO: ");
		lblIngresa.setFont(new Font("Andalus", Font.PLAIN, 13));
		lblIngresa.setBounds(10, 49, 211, 14);
		lblIngresa.setForeground(Color.WHITE);
		contentPanel.add(lblIngresa);
		
		Usuarios = new JTextField();
		Usuarios.setHorizontalAlignment(SwingConstants.CENTER);
		Usuarios.setBounds(197, 47, 165, 20);
		contentPanel.add(Usuarios);
		Usuarios.setColumns(10);
		
		JLabel lblIngresaUnaContrasea = new JLabel("INGRESA UNA CONTRASEÑA:");
		lblIngresaUnaContrasea.setFont(new Font("Andalus", Font.PLAIN, 13));
		lblIngresaUnaContrasea.setBounds(10, 90, 192, 14);
		lblIngresaUnaContrasea.setForeground(Color.WHITE);
		contentPanel.add(lblIngresaUnaContrasea);
		
		JLabel lblConfirmaTuContrasea = new JLabel("CONFIRMA TU CONTRASEÑA:");
		lblConfirmaTuContrasea.setFont(new Font("Andalus", Font.PLAIN, 13));
		lblConfirmaTuContrasea.setBounds(10, 132, 192, 14);
		lblConfirmaTuContrasea.setForeground(Color.WHITE);
		contentPanel.add(lblConfirmaTuContrasea);
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				 if (Usuarios.getText().equals("") && Contrasenia.getText().equals("") && Contrasenia2.getText().equals("") || Usuarios.getText().equals("") || Contrasenia.getText().equals("") || Contrasenia2.getText().equals("")) {
					 JOptionPane.showMessageDialog(null,"VERIFIQUE QUE TODO LOS CAMPOS ESTEN LLENOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					    AgregarUsuarios.Contrasenia.setText("");
					    Contrasenia2.setText("");
				}else{
					
				    java.sql.Connection conn=null;
				    Statement stmnt=null;
				    ResultSet rs=null;
				    try{
				    	conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
						stmnt=conn.createStatement();
										
				if( validarUsuario(Usuarios.getText()  ) )    //enviar datos a validar
	              {
					JOptionPane.showMessageDialog(null,"EL USUARIO YA EXISTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
	                   Usuarios.setText("");
					   AgregarUsuarios.Contrasenia.setText("");
					   Contrasenia2.setText("");
	      
	               }else
	               {
						if (AgregarUsuarios.Contrasenia.getText().equals(Contrasenia2.getText())) {
						stmnt.executeUpdate("INSERT INTO  `"+Ruta.database+"`.`usuarios` (`USUARIOS` ,`CONTRASEÑAS`)VALUES ('"+Usuarios.getText()+"',  '"+Contrasenia2.getText()+"');");
						ConexionTableModel ctm=new ConexionTableModel("select * from usuarios");
						Login.JTResultadoUser.setModel(ctm.getTablemodel());
						JOptionPane.showMessageDialog(null, "EL USUARIO SE A CREADO CORRECTAMENTE");
						for (int i = 0; i <Login.JTResultadoUser.getRowCount(); i++) {
					      	String Usuariosss = Login.JTResultadoUser.getValueAt(i, 0).toString();
					  		AgregarUsuarios.vee.add(Usuariosss);
						}
						String a=Usuarios.getText();
					    Usuarios.setText("");
					    AgregarUsuarios.Contrasenia.setText("");
					    Contrasenia2.setText("");
					    
					    try {
							final ConexionTableModel ctm1=new ConexionTableModel("SELECT USUARIOS FROM usuarios");
							tableUser.setModel(ctm1.getTablemodel());
							
							ConexionTableModel ct2=new ConexionTableModel("SELECT USUARIOS FROM usuarios");
							Eliminar.TablaUsuarios.setModel(ct2.getTablemodel());
						} catch (Exception e1) {
							// TODO: handle exception
							e1.printStackTrace();
						}
						}else
						{
						    AgregarUsuarios.Contrasenia.setText("");
						    Contrasenia2.setText("");
							JOptionPane.showMessageDialog(null,"LA CONTRASEÑA NO COINCIDE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
						}
						//para borrar
						//stmnt.executeUpdate("delete from productos where id_producto=12;");
	               }
						}catch (SQLException e1){
							e1.printStackTrace();
						}catch (ClassCastException e1) {
							
							e1.printStackTrace();
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}finally
						{
							if(rs!=null)
								try {
									rs.close();
								} catch (SQLException e1) {
									
									e1.printStackTrace();
								}
							if(conn!=null)
								try {
									conn.close();
								} catch (SQLException e1) {
									
									e1.printStackTrace();
								}
							if(conn !=null)
								try {
									conn.close();
								} catch (SQLException e1) {
									
									e1.printStackTrace();
								}
						}
				    
				}
			
			
		}
		});
		btnNewButton.setBounds(259, 160, 98, 44);
		contentPanel.add(btnNewButton);
		
		Contrasenia = new JPasswordField();
		Contrasenia.setHorizontalAlignment(SwingConstants.CENTER);
		Contrasenia.setBounds(197, 86, 165, 20);
		contentPanel.add(Contrasenia);
		
		Contrasenia2 = new JPasswordField();
		Contrasenia2.setHorizontalAlignment(SwingConstants.CENTER);
		Contrasenia2.setBounds(197, 129, 165, 20);
		contentPanel.add(Contrasenia2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(404, 49, 219, 154);
		contentPanel.add(scrollPane);
		

		
		
		tableUser = new JTable();
		tableUser.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(tableUser);
		
		try {
			final ConexionTableModel ctm=new ConexionTableModel("SELECT USUARIOS FROM usuarios");
			tableUser.setModel(ctm.getTablemodel());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		JLabel lblAgregarUsuarios = new JLabel("AGREGAR USUARIOS");
		lblAgregarUsuarios.setFont(new Font("Andalus", Font.PLAIN, 18));
		lblAgregarUsuarios.setBounds(238, 22, 214, 14);
		lblAgregarUsuarios.setForeground(Color.white);
		contentPanel.add(lblAgregarUsuarios);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		lblNewLabel.setBounds(0, 0, 646, 214);
		contentPanel.add(lblNewLabel);
	}
	boolean validarUsuario(String elUsr)  throws IOException
    {
    	try
		{
    		java.sql.Connection conn=null;
    	     Statement stmnt=null;
    	    ResultSet rs=null;
    	    conn=(Connection) DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
    	    stmnt=conn.createStatement();
            ResultSet resultadosConsulta = stmnt.executeQuery ("SELECT * FROM usuarios WHERE USUARIOS='"+elUsr+"'");
            if( resultadosConsulta.first() )        
                return true;        
            else
                return false;                  
		} catch (Exception e)
		{
   			e.printStackTrace();
            return false;
		}
    }
}
