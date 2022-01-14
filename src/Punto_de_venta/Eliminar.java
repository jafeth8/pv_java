package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class Eliminar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static Eliminar dialog = new Eliminar();
	Statement stmnt=null;
	private JPasswordField ContraseniaAdmin;
	static JTable TablaUsuarios;
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
	public Eliminar() {

	    java.sql.Connection conn=null;
	   
	    ResultSet rs=null;
	   
		try {
			conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
			stmnt=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setTitle("ELIMINAR USUARIO");
		setBounds(100, 100, 455, 356);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setLayout(null);
		
		final JComboBox UsuariosBorra = new JComboBox(AgregarUsuarios.vee);
		UsuariosBorra.setBounds(20, 48, 272, 20);
		contentPanel.add(UsuariosBorra);
		
		JLabel lblSelecciona = new JLabel("SELECCIONA EL USUARIO A BORRAR");
		lblSelecciona.setFont(new Font("Aharoni", Font.PLAIN, 17));
		lblSelecciona.setBounds(10, 23, 311, 14);
		lblSelecciona.setForeground(Color.WHITE);
		contentPanel.add(lblSelecciona);
		
		JLabel lblIngresaContraselaDe = new JLabel("INGRESA CONTRASEÑA DE ADMINISTRADOR");
		lblIngresaContraselaDe.setFont(new Font("Aharoni", Font.PLAIN, 15));
		lblIngresaContraselaDe.setBounds(10, 79, 323, 14);
		lblIngresaContraselaDe.setForeground(Color.WHITE);
		contentPanel.add(lblIngresaContraselaDe);
		
		ContraseniaAdmin = new JPasswordField();
		ContraseniaAdmin.setBounds(20, 111, 272, 20);
		contentPanel.add(ContraseniaAdmin);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String a;
				a=(String)UsuariosBorra.getSelectedItem();
				if (a.equals("Administrador" ) && ContraseniaAdmin.getText().equals("JESUS")) {
					JOptionPane.showMessageDialog(null,"NO PUEDES BORRAR AL ADMINISTRADOR","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					ContraseniaAdmin.setText("");
				}else{
				if (ContraseniaAdmin.getText().equals("JESUS")) {
					try {
						stmnt.executeUpdate("DELETE FROM  `"+Ruta.database+"`.`usuarios` WHERE  `usuarios`.`USUARIOS` =  '"+a+"';");
						ConexionTableModel ctm=new ConexionTableModel("select * from usuarios");
						Login.JTResultadoUser.setModel(ctm.getTablemodel());
						ContraseniaAdmin.setText("");
						JOptionPane.showMessageDialog(null, "SE A BORRADO EL USUARIO CORRECTAMENTE");
						 try {
								final ConexionTableModel ctm1=new ConexionTableModel("SELECT USUARIOS FROM usuarios");
								TablaUsuarios.setModel(ctm1.getTablemodel());
								AgregarUsuarios.vee.removeAllElements();
								
								for (int i = 0; i <Login.JTResultadoUser.getRowCount(); i++) {
							      	String Usuariosss = Login.JTResultadoUser.getValueAt(i, 0).toString();
							  		AgregarUsuarios.vee.add(Usuariosss);
								}
							} catch (Exception e1) {
								// TODO: handle exception
								e1.printStackTrace();
							}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null,"LA CONTRASEï¿½A INCORRECTA","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					ContraseniaAdmin.setText("");
				}
			}
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\images.jpg"));
		btnNewButton.setBounds(331, 23, 98, 108);
		contentPanel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 159, 391, 135);
		contentPanel.add(scrollPane);
		
		TablaUsuarios = new JTable();
		scrollPane.setViewportView(TablaUsuarios);
		
		try {
			final ConexionTableModel ctm=new ConexionTableModel("SELECT USUARIOS FROM usuarios");
			TablaUsuarios.setModel(ctm.getTablemodel());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label.setBounds(0, 0, 439, 318);
		contentPanel.add(label);
	}
}