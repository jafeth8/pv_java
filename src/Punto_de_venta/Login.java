package Punto_de_venta;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.color.CMMException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;


import Punto_de_venta.AgregarUsuarios;

import javax.swing.JProgressBar;
import java.awt.Color;

public class Login extends JFrame {

	private JTextField TFQuery;
	private JPanel contentPane;
	static Login frame = new Login();
	static JPasswordField contrasenia;
    static JComboBox Usuarios ;
    static JLabel LabelVerificacion;
    static JProgressBar progressBar;
    static JLabel Porcentaje;
    
	static String a;
	static java.sql.Connection conn=null;
    static Statement stmnt=null;
    static ResultSet rs=null;
    
	static JTable JTResultadoUser;
	static String usuarioadentro;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Login() {
	    //System.out.println(Ruta.imagen);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\as.jpg"));
		setTitle("Iniciar Sesion");
		setBounds(50, 50, 762, 498);
		contentPane.setLayout(null);
		
		
		JList<Object> lista2=new JList<>();
		lista2.setListData(AgregarUsuarios.vee);
	    
		JButton btnEntrar = new JButton("Iniciar Sesion");
		btnEntrar.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\presidencia\\Imagenes\\ICONOS\\ent.png"));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				
				try
                {                    
                	a=(String)Usuarios.getSelectedItem();
                    //chekar si el usuario escrbio el nombre de usuario y pw
                    if (contrasenia.getText().length() > 0 )
                    {
                        // Si el usuario si fue validado correctamente
                        if( validarUsuario( a, contrasenia.getText() ) )    //enviar datos a validar
                        {
                            // Codigo para mostrar la ventana principal
                        	if (a.equals("Administrador")) {
        
							    new Thread(new Hilo2()).start();
							}else{
								new Thread(new Hilo2()).start();
							}    
                        	
                        }
                     else
                        {
                            
                            JOptionPane.showMessageDialog(null,"EL NOMBRE DE USUARIO Y/O CONTRASELA NO SON VALIDOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                            contrasenia.setText("");        
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"LOS CAMPOS ESTAN VACIOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception e)
                {
                	
                    e.printStackTrace();
                }
				
			}
		});
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\logotoron.png"));
		label.setBounds(282, 31, 230, 178);
		contentPane.add(label);
		btnEntrar.setBounds(494, 359, 205, 41);
		contentPane.add(btnEntrar);
		
		contrasenia = new JPasswordField();
		contrasenia.setBounds(282, 301, 429, 20);
		contrasenia.setToolTipText("Escriba su nombre de usuario");
		
		contentPane.add(contrasenia);
		
		contrasenia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try
                {                    
                	a=(String)Usuarios.getSelectedItem();
                    //chekar si el usuario escrbio el nombre de usuario y pw
                    if (contrasenia.getText().length() > 0 )
                    {
                        // Si el usuario si fue validado correctamente
                        if( validarUsuario( a, contrasenia.getText() ) )    //enviar datos a validar
                        {
                            // Codigo para mostrar la ventana principal
                        	if (a.equals("Administrador")) {
							    new Thread(new Hilo2()).start();
							}else{
								new Thread(new Hilo2()).start();
							}                     	
                        }
                     else
                        {
                            JOptionPane.showMessageDialog(null,"EL NOMBRE DE USUARIO Y/O CONTRASELA NO SON VALIDOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                            contrasenia.setText("");        
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"LOS CAMPOS ESTAN VACIOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
			}
		});
	
		Usuarios = new JComboBox(AgregarUsuarios.vee);
		Usuarios.setBounds(270, 245, 429, 20);
		Usuarios.setToolTipText("ESCOJA UNA OPCION");
		contentPane.add(Usuarios);
		
		Usuarios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				usuarioadentro = (String) Usuarios.getSelectedItem();
				//JOptionPane.showMessageDialog(null, "EL PRODUCTO FUE AGREGADO CON EXITO"+usuarioadentro);
				//PuntoDeVenta.UsuarioLabel.setText(""+usuarioadentro);
			}
		});
		
		JTResultadoUser = new JTable();
		JTResultadoUser.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"USUARIOS", "CONTRASE\u00D1AS"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		try {
			final ConexionTableModel ctm=new ConexionTableModel("SELECT * FROM usuarios");
			JTResultadoUser.setModel(ctm.getTablemodel());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		JTResultadoUser.getColumnModel().getColumn(0).setResizable(false);
		JTResultadoUser.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		JTResultadoUser.setBounds(0, 0, 9, 5);
		JTResultadoUser.setVisible(false);
		contentPane.add(JTResultadoUser);
	
		
		
		try {
		java.sql.Connection conn=null;
   	    Statement stmnt=null;
   	    conn=(Connection) DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
   	    stmnt=conn.createStatement();
   	    
   	    ResultSet resultadosConsulta = stmnt.executeQuery ("SELECT * FROM usuarios");
   	     
   	    
   	    for (int i = 0; i < JTResultadoUser.getRowCount(); i++) {
      	String Usuariosss = JTResultadoUser.getValueAt(i, 0).toString();
  		AgregarUsuarios.vee.add(Usuariosss);
        }
   	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Andalus", Font.PLAIN, 30));
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setBounds(138, 235, 108, 30);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Andalus", Font.PLAIN, 30));
		lblContrasea.setForeground(Color.WHITE);
		lblContrasea.setBounds(120, 293, 171, 28);
		contentPane.add(lblContrasea);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\LOGIN.png"));
		lblNewLabel.setBounds(-16, 171, 150, 150);
		contentPane.add(lblNewLabel);
		
		progressBar = new JProgressBar();
		progressBar.setToolTipText("");
		progressBar.setBounds(48, 372, 390, 14);
		contentPane.add(progressBar);
		
		LabelVerificacion = new JLabel("VALIDAR DATOS  .....");
		LabelVerificacion.setForeground(Color.BLACK);
		LabelVerificacion.setFont(new Font("Consolas", Font.PLAIN, 13));
		LabelVerificacion.setBounds(48, 359, 241, 14);
		contentPane.add(LabelVerificacion);
		
		Porcentaje = new JLabel("0 %");
		Porcentaje.setBounds(228, 386, 46, 14);
		contentPane.add(Porcentaje);
		
				JLabel label_1 = new JLabel("");
				
				//ultimo
				label_1.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
				label_1.setBounds(0, 0, 756, 469);
				contentPane.add(label_1);
	}
	
	static boolean validarUsuario(String elUsr, String elPw)  throws IOException
    {
    	try
		{
    		java.sql.Connection conn=null;
    	    Statement stmnt=null;
    	    ResultSet rs=null;
    	    
    	    conn=(Connection) DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
    	    stmnt=conn.createStatement();
            ResultSet resultadosConsulta = stmnt.executeQuery ("SELECT * FROM usuarios WHERE USUARIOS='"+elUsr+"' AND CONTRASEÑAS='"+ elPw+"'");
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
	public static class Hilo2 implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i <= 100; i++) {
				
				if (i==1) {
					LabelVerificacion.setText("VALIDANDO DATOS......");
				}
				
				progressBar.setValue(i);
				progressBar.repaint();
				
				Porcentaje.setText(""+i+" %");
				
				try {
					Thread.sleep(15);
				} catch (Exception e) {
					// TODO: handle exception
				}
                if (i==35) {
					LabelVerificacion.setText("VALIDANDO USUARIO......");
				}
                if (i==70) {
					LabelVerificacion.setText("VALIDANDO CONTRASEï¿½A......");
				}
                if (i==100) {
					 try {
						if( validarUsuario( a, contrasenia.getText()))
						 {
							LabelVerificacion.setText("VERIFICACION EXITOSA");						     
						    frame.setVisible(false);
						    PuntoDeVenta.frame1.setVisible(false);
                     		PuntoDeVenta.frame.setVisible(true);
                     		contrasenia.setText("");
						 }
              else
						 {
						     JOptionPane.showMessageDialog(null,"EL NOMBRE DE USUARIO Y/O CONTRASELA NO SON VALIDOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
						     contrasenia.setText("");
						     LabelVerificacion.setText("ERROR EN VERIFICAR USUARIO");
						 }
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
		
	}
}
