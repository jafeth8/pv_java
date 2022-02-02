package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AgregarDatos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static AgregarDatos dialog = new AgregarDatos();
	private JTextField producto;
	private JTextField precio;
	private JTextField preciocosto;
	private JTextField CANTIDAD;
	private JTextField Codigo_BarraProductos;
	private JTextField textFieldCategoria;

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
	public AgregarDatos() {
		setAutoRequestFocus(false);
		setTitle("Agregar Producto");
		setBounds(100, 100, 300, 504);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel labelDescripcionProductoExistente = new JLabel("");
		labelDescripcionProductoExistente.setForeground(Color.RED);
		labelDescripcionProductoExistente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelDescripcionProductoExistente.setBounds(10, 149, 200, 14);
		contentPanel.add(labelDescripcionProductoExistente);
		
		JLabel labelCodigoDeBarrasExistente = new JLabel("");
		labelCodigoDeBarrasExistente.setForeground(Color.RED);
		labelCodigoDeBarrasExistente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelCodigoDeBarrasExistente.setBounds(10, 67, 200, 14);
		contentPanel.add(labelCodigoDeBarrasExistente);
		
		JLabel lblIngreseProducto = new JLabel("INGRESE LA DESCRIPCION DEL PRODUCTO:");
		lblIngreseProducto.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblIngreseProducto.setForeground(Color.WHITE);
		
		lblIngreseProducto.setBounds(10, 92, 260, 14);
		contentPanel.add(lblIngreseProducto);
		
		producto = new JTextField();
		producto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if(descripcionProductoYaExistente(producto.getText())) {
						labelDescripcionProductoExistente.setText("Esta descripcion ya existe!");
					}else {
						labelDescripcionProductoExistente.setText("");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		producto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					if(descripcionProductoYaExistente(producto.getText())) {
						labelDescripcionProductoExistente.setText("Esta descripcion ya existe!");
					}else {
						labelDescripcionProductoExistente.setText("");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		producto.setHorizontalAlignment(SwingConstants.CENTER);
		producto.setBounds(10, 118, 260, 20);
		contentPanel.add(producto);
		producto.setColumns(10);
		
		JLabel lblIngresePrecio = new JLabel("INGRESE PRECIO UNITARIO");
		lblIngresePrecio.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblIngresePrecio.setBounds(10, 292, 260, 14);
		lblIngresePrecio.setForeground(Color.WHITE);
		contentPanel.add(lblIngresePrecio);
		
		
		precio = new JTextField();
		precio.setHorizontalAlignment(SwingConstants.CENTER);
		precio.setBounds(10, 317, 260, 20);
		contentPanel.add(precio);
		precio.setColumns(10);
		
		JLabel lblIngreseCosto = new JLabel("INGRESE PRECIO COSTO");
		lblIngreseCosto.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblIngreseCosto.setBounds(10, 236, 260, 14);
		lblIngreseCosto.setForeground(Color.WHITE);
		contentPanel.add(lblIngreseCosto);
		
		
		preciocosto = new JTextField();
		preciocosto.setHorizontalAlignment(SwingConstants.CENTER);
		preciocosto.setBounds(10, 261, 260, 20);
		contentPanel.add(preciocosto);
		preciocosto.setColumns(10);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Metodos validar=new Metodos();
			    java.sql.Connection conn=null;
			    Statement stmnt=null;
			    ResultSet rs=null;
			    
			    if (producto.getText().equals("") || precio.getText().equals("")||CANTIDAD.getText().equals("")||Codigo_BarraProductos.getText().equals("")||preciocosto.getText().equals("")) {
					 JOptionPane.showMessageDialog(null,"DATOS INCOMPLETOS ASEGURE QUE TODO LOS CAMPOS ESTEN RELLENOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
			    }
				else
				{
				try{
				conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
				stmnt=conn.createStatement();
				//para insertar 
				if (validarProductos(Codigo_BarraProductos.getText(),producto.getText()) || codigoDeBarrasYaExistente(Codigo_BarraProductos.getText()) ||  descripcionProductoYaExistente(producto.getText()))  {
					JOptionPane.showMessageDialog(null,"EL PRODUCTO YA EXISTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
		
				}else{
					
					if(textFieldCategoria.getText().toLowerCase().equals("unidades")==false) {
						if(validar.isNumeric(CANTIDAD.getText())==false) {
							JOptionPane.showMessageDialog(null,"solos los productos con categoria ' UNIDADES ' pueden tener numero decimal en el apartado de la cantidad");
							return;
						}
					}
					
					/*-----VERIFICAR QUE EL PRECIO UNITARIO SEA MAYOR O IGUL AL COSTO VENTA PARA OBTENER GANANCIAS-----*/
					Metodos instanciaMetodos=new Metodos();
					if(instanciaMetodos.isDouble(precio.getText()) && instanciaMetodos.isDouble(preciocosto.getText()) ) {
						float precioUnitario=Float.parseFloat(precio.getText());
						float costoUnitario=Float.parseFloat(preciocosto.getText());
						if(precioUnitario<costoUnitario) {
							JOptionPane.showMessageDialog(null,"El precio unitario debe ser mayor al costo unitario para "
									+ "obtener ganacias","Actualizacion Abortada",JOptionPane.ERROR_MESSAGE);
							return;
						}else if(precioUnitario<=costoUnitario) {
							int opcion=JOptionPane.showConfirmDialog(null,"El precio unitario es igual al costo unitario por lo tanto no "
									+ "obtendra ninguna ganancia desea continuar de todos modos?","Aviso!",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
							if(opcion!=0) {
								JOptionPane.showMessageDialog(null,"Operacion Cancelada");
								return;
							}
						}
					}
					//float precioUnitario=Float.parseFloat(Costounitario);
					/*-----FIN DE VERIFICSION DEL PRECIO UNITARIO------------------------------------------------------*/
					
					stmnt.executeUpdate("INSERT INTO `"+Ruta.database+"`.`productos` (`ID`, `CODIGO_BARRA`, `CANTIDAD`, `DESCRIPCION`, `PRECIO_UNITARIO`, `COSTO_UNITARIO`,`CATEGORIA`) VALUES (NULL, '"+Codigo_BarraProductos.getText()+"', '"+CANTIDAD.getText()+"', '"+producto.getText()+"', '"+precio.getText()+"', '"+preciocosto.getText()+"','"+textFieldCategoria.getText().toLowerCase()+"');");
					//INSERT INTO `tienda2015-2`.`productos` (`ID`, `CODIGO_BARRA`, `CANTIDAD`, `DESCRIPCION`, `PRECIO_UNITARIO`, `COSTO_UNITARIO`) VALUES (NULL, '9202', '11', 'ckjdfnjdf', '1090', '90.00');
					//insert into productos(CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO)values(\'"+Codigo_BarraProductos.getText()+"\',\'"+CANTIDAD.getText()+"\',\'"+producto.getText()+"\',"+precio.getText()+"\","+preciocosto.getText()+");"
					
					ConexionTableModel ctm=new ConexionTableModel();
					if(PuntoDeVenta.codigoBarra.getText().equals("")) {
						ctm.mostrardatosProductos("",PuntoDeVenta.tablaProductos);
					}else {
						
						ctm.mostrardatosProductos(PuntoDeVenta.codigoBarra.getText(),PuntoDeVenta.tablaProductos);
					}

					JOptionPane.showMessageDialog(null, "EL PRODUCTO FUE AGREGADO CON EXITO");
					producto.setText("");
					precio.setText("");
					CANTIDAD.setText("");
					Codigo_BarraProductos.setText("");
					preciocosto.setText("");
					textFieldCategoria.setText("");
					AgregarDatos.dialog.setVisible(false);
				
				}
				//para borrar
				//stmnt.executeUpdate("delete from productos where id_producto=12;");
				}catch (SQLException e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Verifique que los campos introducidos sean validos","Error!!",JOptionPane.ERROR_MESSAGE);
				}catch (ClassCastException e1) {
					// TODO: handle exception
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally
				{
					if(rs!=null)
						try {
							rs.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					if(conn!=null)
						try {
							conn.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					if(conn !=null)
						try {
							conn.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			}
			}
		});
		button.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\anadir-registro-icono-8419-32.png"));
		button.setBounds(60, 404, 150, 50);
		contentPanel.add(button);
		
		JLabel lblCantidad = new JLabel("CANTIDAD:");
		lblCantidad.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblCantidad.setForeground(Color.WHITE);
		lblCantidad.setBounds(10, 180, 69, 14);
		contentPanel.add(lblCantidad);
		
		CANTIDAD = new JTextField();
		CANTIDAD.setHorizontalAlignment(SwingConstants.CENTER);
		CANTIDAD.setBounds(10, 205, 260, 20);
		contentPanel.add(CANTIDAD);
		CANTIDAD.setColumns(10);
		
		JLabel lblCodigodebarra = new JLabel("CODIGO_DE_BARRA");
		lblCodigodebarra.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblCodigodebarra.setBounds(10, 11, 141, 14);
		lblCodigodebarra.setForeground(Color.WHITE);
		contentPanel.add(lblCodigodebarra);
		
		Codigo_BarraProductos = new JTextField();
		Codigo_BarraProductos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if(codigoDeBarrasYaExistente(Codigo_BarraProductos.getText())) {
						labelCodigoDeBarrasExistente.setText("Este codigo de barras ya existe!");
					}else {
						labelCodigoDeBarrasExistente.setText("");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Codigo_BarraProductos.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					if(codigoDeBarrasYaExistente(Codigo_BarraProductos.getText())) {
						labelCodigoDeBarrasExistente.setText("Este codigo de barras ya existe!");
					}else {
						labelCodigoDeBarrasExistente.setText("");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Codigo_BarraProductos.setHorizontalAlignment(SwingConstants.CENTER);
		Codigo_BarraProductos.setBounds(10, 36, 260, 20);
		contentPanel.add(Codigo_BarraProductos);
		Codigo_BarraProductos.setColumns(10);
		
		JLabel labelCategoria = new JLabel("CATEGORIA");
		labelCategoria.setForeground(Color.WHITE);
		labelCategoria.setFont(new Font("Aharoni", Font.PLAIN, 13));
		labelCategoria.setBounds(10, 348, 169, 14);
		contentPanel.add(labelCategoria);
		
		textFieldCategoria = new JTextField();
		textFieldCategoria.setBounds(10, 373, 260, 20);
		contentPanel.add(textFieldCategoria);
		textFieldCategoria.setColumns(10);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label.setBounds(0, 0, 284, 465);
		contentPanel.add(label);
	    }
	boolean validarProductos(String des,String asd)  throws IOException
    {
    	try
		{
    		java.sql.Connection conn=null;
    	    Statement stmnt=null;
    	    ResultSet rs=null;
    	    
    		conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
    	    stmnt=conn.createStatement();
            ResultSet resultadosConsulta = stmnt.executeQuery ("SELECT * FROM productos WHERE CODIGO_BARRA='"+des+"'AND DESCRIPCION='"+asd+"'");
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
	
	boolean codigoDeBarrasYaExistente(String des)  throws IOException
    {
    	try
		{
    		java.sql.Connection conn=null;
    	     Statement stmnt=null;
    	    ResultSet rs=null;
    	    conn=(Connection) DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
    	    stmnt=conn.createStatement();
            ResultSet resultadosConsulta = stmnt.executeQuery ("SELECT * FROM productos WHERE CODIGO_BARRA='"+des+"'");
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
	
	boolean descripcionProductoYaExistente(String des)  throws IOException
    {
    	try
		{
    		java.sql.Connection conn=null;
    	     Statement stmnt=null;
    	    ResultSet rs=null;
    	    conn=(Connection) DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
    	    stmnt=conn.createStatement();
            ResultSet resultadosConsulta = stmnt.executeQuery ("SELECT * FROM productos WHERE DESCRIPCION='"+des+"'");
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

