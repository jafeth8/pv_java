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
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class Actualizar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static JTextField BARRA = new JTextField();
	static JTextField Nombre = new JTextField();
	static JTextField Precio = new JTextField();
	static JTextField Costounitario = new JTextField();
	static Actualizar actualizar = new Actualizar();
	static JTextField jtextFieldSumar;
	private JTable tableforModificar;
	static JTextField textFieldCategoria;
	static JTextPane textPaneCantidadActual;
	private JTextField textFieldRestar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			actualizar.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			actualizar.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Actualizar() {
		setResizable(false);
		setTitle("ACTUALIZAR DATOS");
		setBounds(100, 100, 581, 412);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel bARRA = new JLabel("CODIGO_BARRA");
		bARRA.setFont(new Font("Aharoni", Font.PLAIN, 13));
		bARRA.setForeground(Color.WHITE);
		bARRA.setBounds(10, 11, 147, 14);
		contentPanel.add(bARRA);
		BARRA.setEditable(false);
		BARRA.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		BARRA.setBounds(10, 38, 259, 20);
		contentPanel.add(BARRA);
		BARRA.setColumns(10);
		BARRA.setToolTipText("ingresa el codigo");
		
		JLabel lblIn = new JLabel("PRODUCTO");
		lblIn.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblIn.setForeground(Color.WHITE);
		lblIn.setBounds(10, 69, 75, 14);
		contentPanel.add(lblIn);
		Nombre.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		Nombre.setBounds(10, 94, 259, 20);
		contentPanel.add(Nombre);
		Nombre.setColumns(10);
		
		JLabel lblPrecio = new JLabel("PRECIO UNITARIO");
		lblPrecio.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblPrecio.setForeground(Color.WHITE);
		lblPrecio.setBounds(10, 237, 150, 14);
		contentPanel.add(lblPrecio);
		Precio.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		Precio.setBounds(10, 262, 259, 20);
		contentPanel.add(Precio);
		Precio.setColumns(10);
		
		JLabel lblCostoPrecio = new JLabel("COSTO UNITARIO");
		lblCostoPrecio.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblCostoPrecio.setForeground(Color.WHITE);
		lblCostoPrecio.setBounds(10, 181, 150, 14);
		contentPanel.add(lblCostoPrecio);
		Costounitario.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		Costounitario.setBounds(10, 206, 259, 20);
		contentPanel.add(Costounitario);
		Costounitario.setColumns(10);
		
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			SqlOperaciones instancia = new SqlOperaciones();
			Metodos validar=new Metodos();
			int cantidad;
			public void actionPerformed(ActionEvent arg0) {
				if (Nombre.getText().equals("") || Precio.getText().equals("") || Costounitario.getText().equals("") || textFieldCategoria.getText().equals("")){
					JOptionPane.showMessageDialog(null,"VERIFICA QUE LOS CAMPO ESTEN LLENOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
			    java.sql.Connection conn=null;
			    Statement stmnt=null;
			    ResultSet rs=null;
			    try {
			    	conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
					stmnt=conn.createStatement();
					//para insertar
					String categoria=textFieldCategoria.getText();
					String cantidadProducto;
					double cantidadDouble,cantidadRestaDouble;
					double cantidadDoubleActual;
					double sumaCantidades=0;
					double restaCantidades;
					boolean campoSumar;//determina si la catidad debe ser evaluada como un numero o no en caso de que el campo Cantidad este vacio
					boolean campoRestar=false;
					if(jtextFieldSumar.getText().equals("")) {
						cantidadProducto=textPaneCantidadActual.getText();
						campoSumar=false;
					}else {
						cantidadProducto=jtextFieldSumar.getText();
						campoSumar =true;
						
						
						cantidadDouble=Double.parseDouble(jtextFieldSumar.getText());
						cantidadDoubleActual=Double.parseDouble(textPaneCantidadActual.getText());
						sumaCantidades=cantidadDoubleActual+cantidadDouble;
						
						cantidadProducto=""+sumaCantidades;
					}
					
					/////CAMPO textFieldRestar Depende del campo jtextFieldSumar
					if(textFieldRestar.getText().equals("")&& campoSumar==false) {
						cantidadProducto=textPaneCantidadActual.getText();
					}else if(textFieldRestar.getText().equals("")&& campoSumar) {
						cantidadProducto=""+sumaCantidades;
						campoRestar=false;
					}else if(!textFieldRestar.getText().equals("") && campoSumar){
						campoRestar=true;
						cantidadRestaDouble=Double.parseDouble(textFieldRestar.getText());
						restaCantidades=sumaCantidades-cantidadRestaDouble;
						if(restaCantidades<0) {
							JOptionPane.showMessageDialog(null,"el resultado no puede ser menor a cero verifique el campo resta");
							return;
						}
						cantidadProducto=""+restaCantidades;
						
					}else if(!textFieldRestar.getText().equals("") && campoSumar==false) {
						campoRestar=true;
						cantidadRestaDouble=Double.parseDouble(textFieldRestar.getText());
						cantidadDoubleActual=Double.parseDouble(textPaneCantidadActual.getText());
						restaCantidades=cantidadDoubleActual-cantidadRestaDouble;
						if(restaCantidades<0) {
							JOptionPane.showMessageDialog(null,"el resultado no puede ser menor a cero verifique campo resta");
							return;
						}
						cantidadProducto=""+restaCantidades;
					}
					
					if(categoria.equals("unidades")==false && campoSumar) {
						if(validar.isNumeric(jtextFieldSumar.getText())==false) {
							JOptionPane.showMessageDialog(null,"solos los productos con categoria ' UNIDADES ' pueden tener numero decimal en el apartado de la cantidad para sumar");
							return;
						}
					}
					
					if(categoria.equals("unidades")==false && campoRestar) {
						if(validar.isNumeric(textFieldRestar.getText())==false) {
							JOptionPane.showMessageDialog(null,"solos los productos con categoria ' UNIDADES ' pueden tener numero decimal en el apartado de la cantidad para restar");
							return;
						}
					}
					
					
					/*-----VERIFICAR QUE EL PRECIO UNITARIO SEA MAYOR O IGUL AL COSTO VENTA PARA OBTENER GANANCIAS-----*/
					Metodos instanciaMetodos=new Metodos();
					if(instanciaMetodos.isDouble(Precio.getText()) && instanciaMetodos.isDouble(Costounitario.getText()) ) {
						float precioUnitario=Float.parseFloat(Precio.getText());
						float costoUnitario=Float.parseFloat(Costounitario.getText());
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
					
					
					stmnt.executeUpdate("UPDATE `"+Ruta.database+"`.`productos` SET  `CANTIDAD` =  '"+cantidadProducto+"',`DESCRIPCION` =  '"+Nombre.getText()+"',`PRECIO_UNITARIO` =  '"+Precio.getText()+"',`COSTO_UNITARIO` =  '"+Costounitario.getText()+"',`CATEGORIA`='"+textFieldCategoria.getText().toLowerCase()+"' WHERE  `productos`.`CODIGO_BARRA` ="+BARRA.getText()+";");
					ConexionTableModel ctm=new ConexionTableModel();
					
					if(PuntoDeVenta.CodigoBarra.getText().equals("")) {
						ctm.mostrardatosProductos("",PuntoDeVenta.tablaProductos);
					}else {
						ctm.mostrardatosProductos(PuntoDeVenta.CodigoBarra.getText(),PuntoDeVenta.tablaProductos);
					}
					
					Nombre.setText("");
					Precio.setText("");
					jtextFieldSumar.setText("");
					textFieldRestar.setText("");
					BARRA.setText("");
					Costounitario.setText("");
					textFieldCategoria.setText("");
					PuntoDeVenta.CodigoBarra.setText("");
					actualizar.setVisible(false);
					JOptionPane.showMessageDialog(null, "SE A ACTUALIZADO CORRECTAMENTE EL PRODUCTO"); 
					
					
					ConexionTableModel ctm2=new ConexionTableModel("select CANTIDAD,DESCRIPCION from productos WHERE CANTIDAD='0'");
					ProductosAgotados.ProductosAgotados.setModel(ctm2.getTablemodel());
					
					
				} catch (SQLException e1){
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Verifique que los campos introducidos sean validos","Error!!",JOptionPane.ERROR_MESSAGE);
				}catch (ClassCastException e1) {
					// TODO: handle exception
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
			
		});
		btnNewButton.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\editar.jpg"));
		btnNewButton.setBounds(438, 114, 89, 123);
		contentPanel.add(btnNewButton);
		
		JLabel labelSumar = new JLabel("SUMAR :");
		labelSumar.setFont(new Font("Aharoni", Font.PLAIN, 13));
		labelSumar.setForeground(Color.WHITE);
		labelSumar.setBounds(136, 125, 54, 14);
		contentPanel.add(labelSumar);
		
		jtextFieldSumar = new JTextField();
		jtextFieldSumar.setHorizontalAlignment(SwingConstants.CENTER);
		jtextFieldSumar.setBounds(136, 150, 60, 20);
		contentPanel.add(jtextFieldSumar);
		jtextFieldSumar.setColumns(10);
		
		tableforModificar = new JTable();
		tableforModificar.setBounds(379, 56, 5, 2);
		try {
			ConexionTableModel ctm=new ConexionTableModel("select * from productos");
			tableforModificar.setModel(ctm.getTablemodel());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		contentPanel.add(tableforModificar);
		
		JLabel labelCategoria = new JLabel("CATEGORIA");
		labelCategoria.setForeground(Color.WHITE);
		labelCategoria.setFont(new Font("Aharoni", Font.PLAIN, 13));
		labelCategoria.setBounds(10, 295, 116, 20);
		contentPanel.add(labelCategoria);
		
		textFieldCategoria = new JTextField();
		textFieldCategoria.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCategoria.setBounds(10, 318, 259, 20);
		contentPanel.add(textFieldCategoria);
		textFieldCategoria.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CANTIDAD ACTUAL");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Aharoni", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 125, 128, 14);
		contentPanel.add(lblNewLabel);
		
		textPaneCantidadActual = new JTextPane();
		textPaneCantidadActual.setEditable(false);
		textPaneCantidadActual.setBounds(10, 150, 116, 20);
		contentPanel.add(textPaneCantidadActual);
		
		
		JLabel labelRestar = new JLabel("RESTAR :");
		labelRestar.setForeground(Color.WHITE);
		labelRestar.setFont(new Font("Aharoni", Font.PLAIN, 13));
		labelRestar.setBounds(206, 125, 60, 14);
		contentPanel.add(labelRestar);
		
		textFieldRestar = new JTextField();
		textFieldRestar.setBounds(206, 150, 63, 20);
		contentPanel.add(textFieldRestar);
		textFieldRestar.setColumns(10);
		
		JLabel labelFondo = new JLabel("");
		labelFondo.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		labelFondo.setBounds(0, 0, 575, 383);
		contentPanel.add(labelFondo);
	}
	boolean validarProductos(String des)  throws IOException
    {
    	try
		{
    		java.sql.Connection conn=null;
    	     Statement stmnt=null;
    	    ResultSet rs=null;
   
    		conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
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
}