package Punto_de_venta;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import imprimir.*;
public class PuntoDeVenta extends JFrame {
	
	
	private JLabel usuarioLabel;
	private JTextField cantidadProductos;
	private JLabel cambio;
	private JTextField textFieldDescuento;
	/*-------variables importantes: se ocupan para otras clases o ventanas******************----*/
	public static int setClienteId=0;
	public static String NombreClienteApartados="";
	/*------------******************-----------------------------------*/
    static JTextField codigoBarra;//debe ser statico ya que se ocupa en otras clases: Actualizar y AgregarDatos 
	static PuntoDeVenta frame = new PuntoDeVenta();	
	static JTable tablaCompras;
	public static JTable tablaProductos;
	public static Login frameLogin=new Login();
	JComboBox<String> comboBox;
	SqlOperaciones instanciaSql=new SqlOperaciones();
	/**
	 * Launch the application.
	 */
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
	/**
	 * Create the frame.
	 */
	public PuntoDeVenta() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				if(instanciaSql.registro_ruta_respaldo()!=true) {
					JOptionPane.showMessageDialog(frame,"No hay ninguna ruta registrada para el respaldo automatico","Advertencia",JOptionPane.WARNING_MESSAGE);
				}
			}
			@Override
			public void windowClosed(WindowEvent e) {
				if(instanciaSql.registro_ruta_respaldo()) {
					RespaldoAutomatico instancia=new RespaldoAutomatico();
					instancia.setVisible(true);
				}
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\ordenador-icono-8301-96.png"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1300, 734);
		JPanel getcontentPane = new JPanel();
		getcontentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(getcontentPane);
	    getcontentPane.setLayout(null);
	    
	    JLabel TOTAL = new JLabel(" $     0.0");
	    TOTAL.setBounds(195, 564, 240, 98);
	    TOTAL.setFont(new Font("Dialog", Font.PLAIN, 40));
		TOTAL.setForeground(Color.BLACK);
		TOTAL.setBackground(new Color(0, 0, 0));
		getContentPane().add(TOTAL);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(27,377, 1243, 187);
		scrollPane_1.setToolTipText("Mostrar Todo los productos");
		getContentPane().add(scrollPane_1);
		
		tablaCompras = new JTable();
		try {
			SqlOperaciones operacion=new SqlOperaciones();
			
			operacion.truncarTablaTcompras("TRUNCATE tcompras");/*---------------Query para que la tabla no quede con productos al iniciar el programa*/
			
			ConexionTableModel ctm1 =new ConexionTableModel();
			ctm1.mostrarDatosTablaTcompras("");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		scrollPane_1.setViewportView(tablaCompras);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 183, 1243, 125);
		getContentPane().add(scrollPane);
		
		tablaProductos = new JTable();
		tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaProductos.setToolTipText("Tabla de productos");
		scrollPane.setViewportView(tablaProductos);
	
		tablaProductos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		ConexionTableModel conexionTableModel = new ConexionTableModel();
		conexionTableModel.mostrardatosProductos("", tablaProductos); //al inicializarse la tablaProductos, se le rellena con sus respectivos datos
		
		JButton btnAgregar = new JButton("AGREGAR PRODUCTO");
		btnAgregar.setBounds(27, 79, 193, 52);
		btnAgregar.setToolTipText("Agrega Productos ");
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AgregarDatos.dialog.setVisible(true);
			}
		});
		btnAgregar.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\anadir-registro-icono-8419-32.png"));
		getContentPane().add(btnAgregar);
		/*----------------jafeth8:btnApartar------------------------------*/
		JButton btnApartar = new JButton("A CREDITO");
		btnApartar.setBounds(369, 79, 224, 52);
		getContentPane().add(btnApartar);
		btnApartar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SqlOperaciones instanciaSqlOperaciones =new SqlOperaciones();
				ConexionTableModel modeloTabla=new ConexionTableModel();
				MetodosImprimir instanciaImprimir=new MetodosImprimir();
				float total=0;
				/*---VARIABLES: PARA INSERTAR EN TABLA APARTADOS*/
				int idCliente;// perfectamente se podria utilizar (setClienteId) la variable estatica de la clase, pero para no revolver variables la dejo XD
				/*---FIN DE VARIABLES:PARA INSERTAR EN TABLA APARTADOS */
				total=(float) instanciaSqlOperaciones.obtenerSumatoriaSubtotalTablaTcompras(Ruta.nametablaTcompras);
				
				if(total==0) {
					JOptionPane.showMessageDialog(null,"Primero debes agregar productos al carrito");
				}
				else {
					
					JOptionPane.showMessageDialog(null,"enseguida seleccione al cliente que hara el apartado");
					MostrarClientes datosClientes=new MostrarClientes();
					datosClientes.setVisible(true);
				    
					idCliente=setClienteId;//setIdCliente es una variable estatica para recibir datos de la ventana MostrarClientes
					if(idCliente==0) {//si id cliente es igual a cero significa que no se establecio un idCliente, por lo tanto la ventana fue cerrada
						JOptionPane.showMessageDialog(null, "operacion cancelada" ,"NO SELECCIONO AL CLIENTE!!", JOptionPane.WARNING_MESSAGE);
					}else {
						
						String fechaApartado=LocalDate.now().toString();
						
						SqlOperaciones operacion =new SqlOperaciones();
						
						operacion.insertApartados(idCliente,total,fechaApartado,total,"en deuda");
						int idApartado=operacion.obtenerIdTablaApartado();
						for (int i = 0; i < tablaCompras.getRowCount(); i++) {
							String idProducto=tablaCompras.getValueAt(i,0).toString();
							String precioUnitario=tablaCompras.getValueAt(i,3).toString();
							String cantidad=tablaCompras.getValueAt(i, 1).toString();
							
							int idProductoInteger=Integer.parseInt(idProducto);
							String categoriaProducto=operacion.obtenerCategoriaTablaProducto(idProductoInteger);
							double costo_unitario=operacion.obtenerCostoProductoTablaProducto(idProductoInteger);
							double precioUnitarioDouble=Double.parseDouble(precioUnitario);
							//int cantidadInteger=Integer.parseInt(cantidad);
							float cantidadDouble=Float.parseFloat(cantidad);
							double descuento=Double.parseDouble(tablaCompras.getValueAt(i,5).toString());
							
							operacion.insertDetalleApartados(idApartado, idProductoInteger, costo_unitario, precioUnitarioDouble, cantidadDouble,fechaApartado,descuento);
							//int cantidadTablaProducto=operacion.obtenerCantidadTablaProducto(idProductoInteger);
							float cantidadTablaProducto=operacion.obtenerCantidadTablaProducto(idProductoInteger);
							
							operacion.actualizarCantidadProductos(idProductoInteger, cantidadTablaProducto, cantidadDouble);
							
						}//fin del ciclo for
						
						JOptionPane.showMessageDialog(null, "operacion realizada correctamente");
						int opcion;
						opcion=JOptionPane.showConfirmDialog(null,"desea imprimir el apartado?","" ,JOptionPane.YES_NO_OPTION);
						if(opcion==0) {
							JOptionPane.showMessageDialog(null,"Apartado realizado correctamente");
							for (int i = 0; i <2; i++) {
								//la variable NombreClienteApartados es inicializada por la clase o ventana 'MostrarClientes'
								instanciaImprimir.imprimirApartado(tablaCompras, TOTAL, usuarioLabel,NombreClienteApartados);
							}
						}else {
							JOptionPane.showMessageDialog(null,"Apartado realizado correctamente");
						}
						
						operacion.truncarTablaTcompras("TRUNCATE "+Ruta.nametablaTcompras+"");
						TOTAL.setText(" $ 0.0");
						cambio.setText(" $ 0.0");
						
					}//fin del segundo else
					
				}//fin del primer else
				
				setClienteId=0;
				NombreClienteApartados="";

				modeloTabla.mostrardatosProductos("",tablaProductos);
				modeloTabla.datosTablaTcompras("", tablaCompras, Ruta.nametablaTcompras);

			}//llaveActionPerformand
		});
		/*----------------jafeth8: fin de btnApartar-----------------------*/
		JButton btnEliminar = new JButton("ELIMINAR PRODUCTO");
		btnEliminar.setBounds(694, 79, 224, 52);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SqlOperaciones instancia = new SqlOperaciones();
				ConexionTableModel instancia2 = new ConexionTableModel();
				final int flsel = tablaProductos.getSelectedRow();
				int idProducto = Integer.parseInt(tablaProductos.getValueAt(flsel, 0).toString());
				if (flsel == -1) {
					JOptionPane.showMessageDialog(null, "DEBE SELECCIONAR UN PRODUCTO", "Mensaje de Error",JOptionPane.ERROR_MESSAGE);
				} else {
					int opcion=JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar este producto","Advertencia",JOptionPane.YES_NO_OPTION);
					System.out.println("opcion " +opcion);
					if(opcion==0) {
						instancia.actualizarStateTablaProductos(idProducto, 3); // 3 es eliminado en la tabla sstate
						JOptionPane.showMessageDialog(null, "producto eliminado satisfactoriamente");
						instancia2.mostrardatosProductos("", tablaProductos);
					}	
				}
			}
		});
		btnEliminar.setToolTipText("Eliminar Productos");
		
		btnEliminar.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\ic_menu_trash_holo_light.png"));
		getContentPane().add(btnEliminar);
		
		JButton agregarCarrito = new JButton("AGREGAR AL CARRITO");
		agregarCarrito.setBounds(371, 319, 222, 47);
		agregarCarrito.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Metodos instanciaMetodos= new Metodos();
				try {
					instanciaMetodos.addCarrito(tablaProductos, tablaCompras, textFieldDescuento, codigoBarra, TOTAL,Ruta.nametablaTcompras);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		agregarCarrito.setToolTipText("Agrega Productos a tu compra");
		agregarCarrito.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\carrito-de-compras-de-comercio-electronico-comprar-icono-4574-48.png"));
		getContentPane().add(agregarCarrito);
		
		JLabel lblAbarrotesDonPepe = new JLabel("MATERIALES FABIO");
		lblAbarrotesDonPepe.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbarrotesDonPepe.setFont(new Font("Stencil", Font.PLAIN, 50));
		lblAbarrotesDonPepe.setBounds(246, 11, 753, 68);
		getContentPane().add(lblAbarrotesDonPepe);
		
		JButton botonPagar = new JButton("");
		botonPagar.setBounds(496, 596, 136, 43);
		botonPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float total;
				float pagoIngresado;
		        
		        SqlOperaciones operacion =new SqlOperaciones();
		        ConexionTableModel modeloTabla=new ConexionTableModel();
		        total= operacion.obtenerSumatoriaSubtotalTablaTcompras(Ruta.nametablaTcompras);
		        String inputPagar;
		        
				if(total==0)
				{
					JOptionPane.showMessageDialog(null,"DEBES GENERAR UNA VENTA PARA PAGAR","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
				}else {
				
					inputPagar=JOptionPane.showInputDialog("Ingrese con cuanto va a pagar");
					pagoIngresado=Float.parseFloat(inputPagar);
					
				if (total>pagoIngresado){
					JOptionPane.showMessageDialog(null,"PAGO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
				}else
				{
					double cambioDelCliente;
					cambioDelCliente=pagoIngresado-total;
					int idProductoCompras;
					String valorCantidadCompras;
					String valorDescripcionCompras;			
					String descripcionProductos;
					String valorCantidadProductos = null;
							
					for (int j = 0; j < tablaCompras.getRowCount(); j++) {
						
						idProductoCompras = Integer.parseInt(tablaCompras.getValueAt(j,0).toString());
						valorCantidadCompras=tablaCompras.getValueAt(j, 1).toString();
						valorDescripcionCompras=tablaCompras.getValueAt(j,2).toString();
						
						modeloTabla.mostrardatosProductos("",tablaProductos);//se deben mostrar todos los productos para comparar las descripciones entre las dos tablas (importante no omitir este metodo)
						for (int i = 0; i < tablaProductos.getRowCount(); i++) {
							descripcionProductos=tablaProductos.getValueAt(i,3).toString();
							if (valorDescripcionCompras.equals(descripcionProductos)) {
								valorCantidadProductos=tablaProductos.getValueAt(i,2).toString();
								float cantidadCompras=Float.parseFloat(valorCantidadCompras);
								float cantidadProductos=Float.parseFloat(valorCantidadProductos);
								operacion.actualizarCantidadProductos(idProductoCompras, cantidadProductos, cantidadCompras);//se establece la existencia de cantidad en el producto
							}
						}							
					}
						    					
					/*----------------------: REGISTRAR HISTORIAL DE VENTAS-----------------*/
					String fechaVenta=LocalDate.now().toString();
					
					operacion.insertVentas(total,fechaVenta,pagoIngresado,cambioDelCliente ,"al contado");
					int idVenta=operacion.obtenerIdTablaVentas();
					
					for (int i = 0; i < tablaCompras.getRowCount(); i++) {
						
						String idProducto=tablaCompras.getValueAt(i,0).toString();
						String precioUnitario=tablaCompras.getValueAt(i,3).toString();
						String cantidad=tablaCompras.getValueAt(i, 1).toString();
						String descuentoString=tablaCompras.getValueAt(i, 5).toString();
						
						int idProductoInteger=Integer.parseInt(idProducto);
						double costo_unitario=operacion.obtenerCostoProductoTablaProducto(idProductoInteger);
						double precioUnitarioDouble=Double.parseDouble(precioUnitario);
						float cantidadFloat=Float.parseFloat(cantidad);
						double descuento=Double.parseDouble(descuentoString);
	
						operacion.insertDetalleVentas(idVenta, idProductoInteger, costo_unitario,precioUnitarioDouble, cantidadFloat,descuento);
					}
					
					/*----------------------JAFETH8: FIN DE REGISTRAR HISTORIAL DE VENTAS-----------------*/
					
					cambio.setText("$ "+cambioDelCliente+"");
							
					total=0;
					modeloTabla.mostrardatosProductos("",tablaProductos);
											
					int pre=JOptionPane.showConfirmDialog(null, "DESEA IMPRIMIR TICKET ?","",JOptionPane.YES_NO_OPTION);
					if (pre==0) {							
						MetodosImprimir instanciaImprimir= new MetodosImprimir();
						instanciaImprimir.imprimir(inputPagar, tablaCompras, TOTAL,cambio, usuarioLabel);
					}else{
						JOptionPane.showMessageDialog(null, "GRACIAS POR SU COMPRA"); 
					}
							
				    operacion.truncarTablaTcompras("TRUNCATE "+Ruta.nametablaTcompras);
				    modeloTabla.datosTablaTcompras("", tablaCompras,"tcompras");
					
					TOTAL.setText(" $ 0.0");
					cambio.setText(" $ 0.0");
			  }
			 }		
			}
		});
			
		botonPagar.setToolTipText("PAGAR EL TOTAL");
		
		botonPagar.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\boton-pagar.jpg"));
		getContentPane().add(botonPagar);
		
		JButton btnNewButton = new JButton("NUEVA VENTA");
		btnNewButton.setBounds(27, 316, 190, 52);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConexionTableModel ctm=new ConexionTableModel();
				SqlOperaciones operacion = new SqlOperaciones();
				operacion.truncarTablaTcompras("TRUNCATE tcompras");
				
				TOTAL.setText("$      0.0");
				cambio.setText("$  0.0");
	
				JOptionPane.showMessageDialog(null, "SE GENERO UNA NUEVA VENTA :D");
				try {
					ctm.mostrarDatosTablaTcompras("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
		
		JButton cotizar= new JButton("COTIZAR");
		cotizar.setBounds(694, 319, 224, 47);
		cotizar.setToolTipText("Cotizar Compra");
		getContentPane().add(cotizar);
		cotizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instanciaSqlOperaciones = new SqlOperaciones();
				MetodosImprimir instanciaImprimir = new MetodosImprimir();
				float total=0;
				total = instanciaSqlOperaciones.obtenerSumatoriaSubtotalTablaTcompras(Ruta.nametablaTcompras);
				
				if (total == 0) {
					JOptionPane.showMessageDialog(null, "DEBES GENERAR UNA VENTA PARA COTIZAR", "Mensaje de Error",JOptionPane.ERROR_MESSAGE);
				} else {
					int pre = JOptionPane.showConfirmDialog(null, "DESEA IMPRIMIR LA COTIZACION ?", "",JOptionPane.YES_NO_OPTION);
					if (pre == 0) {
						instanciaImprimir.imprimirCotizacion(tablaCompras, TOTAL, usuarioLabel);
					} else {
						JOptionPane.showMessageDialog(null, "Cancelado", "Mensaje de aviso", JOptionPane.CANCEL_OPTION);
					}
				}

			}
		});
		
		btnNewButton.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\22editar.png"));
		getContentPane().add(btnNewButton);
		
		JLabel labelTituloTotal = new JLabel("TOTAL :");
		labelTituloTotal.setBounds(27, 575, 174, 77);
		labelTituloTotal.setForeground(Color.BLACK);
		labelTituloTotal.setFont(new Font("Dialog", Font.PLAIN, 38));
		labelTituloTotal.setBackground(Color.BLACK);
		getContentPane().add(labelTituloTotal);
		
		JButton btnActualizar = new JButton("ACTUALIZAR PRODUCTO");
		btnActualizar.setBounds(1014, 79, 256, 52);
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final int flsel=tablaProductos.getSelectedRow();
				SqlOperaciones instancia=new SqlOperaciones();
				String codigo;
				String categoria="";
				String cantidadProductos;
				String descripcion;
				String precio;
				String precioCosto;
				if (flsel==-1) {
					JOptionPane.showMessageDialog(null,"DEBE SELECCIONAR UN PRODUCTO","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
				}else{
					int idProducto= Integer.parseInt(tablaProductos.getValueAt(flsel, 0).toString().toString());
					Actualizar.actualizar.setVisible(true);
					codigo = tablaProductos.getValueAt(flsel, 1).toString();
					descripcion = tablaProductos.getValueAt(flsel, 3).toString();
					precio=tablaProductos.getValueAt(flsel, 4).toString();
					precioCosto=tablaProductos.getValueAt(flsel, 5).toString();
					categoria=instancia.obtenerCategoriaProducto(idProducto);
					cantidadProductos=instancia.obtenerCantidadProductoDescripcion(descripcion);
					Actualizar.BARRA.setText(""+codigo);
					Actualizar.Nombre.setText(""+descripcion);
					Actualizar.Precio.setText(""+precio);
					Actualizar.Costounitario.setText(""+precioCosto);
					Actualizar.textFieldCategoria.setText(""+categoria);
					Actualizar.textPaneCantidadActual.setText(cantidadProductos);
					
				}
			}
		});
		btnActualizar.setToolTipText("Actulizacion de productos");
		
		btnActualizar.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\22editar.png"));
		getContentPane().add(btnActualizar);
		
		JButton btnCancelarProducto = new JButton("CANCELAR PRODUCTO");
		btnCancelarProducto.setBounds(1014, 316, 256, 50);
		btnCancelarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final int seleccionado=tablaCompras.getSelectedRow();
				SqlOperaciones operacion=new SqlOperaciones(); 	
				if (seleccionado==-1) {
					JOptionPane.showMessageDialog(null,"NO SELECCIONO PRODUCTO","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
				}else{
					float total=0;
					total = (float) operacion.obtenerSumatoriaSubtotalTablaTcompras(Ruta.nametablaTcompras);
					String descripcionProducto=tablaCompras.getValueAt(seleccionado, 2).toString();
					String subtotaltabla=tablaCompras.getValueAt(seleccionado, 4).toString();
					float subTotal = Float.parseFloat(subtotaltabla);
					total=total-subTotal;
					TOTAL.setText(""+total);
					operacion.eliminarRegistroTablaTcompras(descripcionProducto,Ruta.nametablaTcompras);
					ConexionTableModel ctm=new ConexionTableModel();
					ctm.datosTablaTcompras("", tablaCompras,Ruta.nametablaTcompras);
				}
			}
		});
		
		btnCancelarProducto.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\ic_menu_trash_holo_light.png"));
		getContentPane().add(btnCancelarProducto);
		
		
		JLabel lblCambio = new JLabel("CAMBIO :");
		lblCambio.setBounds(803, 587, 180, 52);
		lblCambio.setFont(new Font("Dialog", Font.PLAIN, 38));
		lblCambio.setBackground(Color.BLACK);
		lblCambio.setForeground(Color.BLACK);
		getContentPane().add(lblCambio);
		
		cambio = new JLabel("$  0.0");
		cambio.setBounds(1046, 575, 224, 77);
		cambio.setFont(new Font("Dialog", Font.PLAIN, 40));
		cambio.setForeground(Color.BLACK);
		getContentPane().add(cambio);
		
		cantidadProductos = new JTextField();
		cantidadProductos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cantidadProductos.setForeground(Color.RED);
		cantidadProductos.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadProductos.setText("1");
		cantidadProductos.setBounds(129, 142, 84, 30);
		getcontentPane.add(cantidadProductos);
		cantidadProductos.setColumns(10);
		
		JLabel lblCantidad = new JLabel("CANTIDAD");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCantidad.setForeground(Color.BLACK);
		lblCantidad.setBounds(27,144, 86, 24);
		getcontentPane.add(lblCantidad);
		
		
		usuarioLabel = new JLabel("");
		usuarioLabel.setForeground(Color.RED);
		usuarioLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usuarioLabel.setBounds(27,11,193, 30);
		getcontentPane.add(usuarioLabel);
		
		usuarioLabel.setText(""+Login.usuarioadentro);
		
		JLabel labelDescuento = new JLabel("DESCUENTO: ");
		labelDescuento.setForeground(Color.BLACK);
		labelDescuento.setFont(new Font("Tahoma", Font.PLAIN, 17));
		labelDescuento.setBounds(1064, 141, 106, 30);
		getcontentPane.add(labelDescuento);
		
		textFieldDescuento = new JTextField();
		textFieldDescuento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldDescuento.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDescuento.setText("0.0");
		textFieldDescuento.setBounds(1184, 142, 86, 30);
		getcontentPane.add(textFieldDescuento);
		textFieldDescuento.setColumns(10);
		
		codigoBarra = new JTextField();
		codigoBarra.setForeground(Color.BLUE);
		codigoBarra.setFont(new Font("Tahoma", Font.PLAIN, 17));
		codigoBarra.setHorizontalAlignment(SwingConstants.CENTER);
		codigoBarra.setBounds(371, 142, 648, 30);
		codigoBarra.setToolTipText("Ingrese el Codigo De barras del producto a buscar");
		getContentPane().add(codigoBarra);
		codigoBarra.setColumns(10);
		
		codigoBarra.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Metodos instanciaMeotodos=new Metodos();
				instanciaMeotodos.busquedaCodigoDeBarras(tablaProductos,tablaCompras, textFieldDescuento, codigoBarra, comboBox, cantidadProductos, TOTAL,Ruta.nametablaTcompras);	
			}
		});					
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("DESCRIPCION");
		comboBox.addItem("CATEGORIA");
		comboBox.setBounds(223, 142, 138, 30);
		getcontentPane.add(comboBox);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("ARCHIVO");
		mnArchivo.setFont(new Font("Andalus", Font.BOLD, 15));
		mnArchivo.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\presidencia\\Imagenes\\ICONOS\\inicio.png"));
		menuBar.add(mnArchivo);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");		
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int pre=JOptionPane.showConfirmDialog(null, "DESEAS SALIR DEL PROGRAMA ?");
				 if (pre==0) {
					 frame.dispose();
				 }
			}
		});
		mnArchivo.add(mntmSalir);
		
		JMenu menuProductosUnidades = new JMenu("                                          ");
		menuProductosUnidades.setEnabled(false);
		menuProductosUnidades.setFont(new Font("Andalus", Font.BOLD, 15));
		menuBar.add(menuProductosUnidades);
		
		JMenu mnUsuarios= new JMenu("USUARIOS");
		mnUsuarios.setFont(new Font("Andalus", Font.BOLD, 15));
		mnUsuarios.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\presidencia\\Imagenes\\ICONOS\\usuario-icono-9502-16.png"));
		
		menuBar.add(mnUsuarios);
		
		JMenuItem mntmAgregar= new JMenuItem("Agregar");
		mntmAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				 try {
						final ConexionTableModel ctm1=new ConexionTableModel("SELECT USUARIOS FROM usuarios");
						AgregarUsuarios.tableUser.setModel(ctm1.getTablemodel());
						
					} catch (Exception e1) {
						// TODO: handle exception
						e1.printStackTrace();
					}
				AgregarUsuarios.dialog.setVisible(true);
				Eliminar.dialog.dispose();		
			}
		});
		mnUsuarios.add(mntmAgregar);
		
		JMenuItem eliminarUsuarios = new JMenuItem("Eliminar");
        eliminarUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AgregarUsuarios.vee.removeAllElements();
							
				for (int i = 1; i <Login.JTResultadoUser.getRowCount(); i++) {
			      	String Usuariosss = Login.JTResultadoUser.getValueAt(i, 0).toString();
			  		AgregarUsuarios.vee.add(Usuariosss);
				}
				Eliminar instanciaEliminar = new Eliminar();
				instanciaEliminar.setVisible(true);	
			}
		});
        
		mnUsuarios.add(eliminarUsuarios);
		
		JMenu mnNewMenu = new JMenu("                                                        ");
		mnNewMenu.setEnabled(false);
		menuBar.add(mnNewMenu);
		/*------------JAFETH8:JMENU CLIENTES------------------------------*/
		JMenu jmenuClientes = new JMenu("CLIENTES");
		jmenuClientes.setFont(new Font("Andalus", Font.BOLD, 15));
		jmenuClientes.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\presidencia\\Imagenes\\ICONOS\\anadir-carrito-icono-3759-32.png"));
		menuBar.add(jmenuClientes);
		
		JMenuItem itemAgregarClientes = new JMenuItem("AGREGAR CLIENTES");
		jmenuClientes.add(itemAgregarClientes);
		itemAgregarClientes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				RegistrarClientes instancia = new RegistrarClientes();
				instancia.setVisible(true);
			}
		});
		
		JMenuItem itemRegitrarImporte = new JMenuItem("REGISTRAR ABONO DE CREDITOS");
		itemRegitrarImporte.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				VerApartados instancia = new VerApartados();
				instancia.setVisible(true);
			}
		});
		jmenuClientes.add(itemRegitrarImporte);
		
		JMenuItem itemMostrarClientes = new JMenuItem("VER CLIENTES");
		itemMostrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarClientes2 instancia = new MostrarClientes2();
				instancia.setVisible(true);
			}
		});
		jmenuClientes.add(itemMostrarClientes);
		
		JMenuItem itemClientesEliminados = new JMenuItem("CLIENTES ELIMINADOS");
		itemClientesEliminados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarClientesEliminados instancia = new MostrarClientesEliminados();
				instancia.setVisible(true);
			}
		});
		jmenuClientes.add(itemClientesEliminados);
		
		/*------------FIN DE JMENU CLIENTES-------------------------------*/
		
		JMenu menu_3 = new JMenu("                                                   ");
		menu_3.setEnabled(false);
		menuBar.add(menu_3);
		JMenu mnFacturas = new JMenu("REPORTES");
		mnFacturas.setFont(new Font("Andalus", Font.BOLD, 15));
		mnFacturas.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\presidencia\\Imagenes\\ICONOS\\rep.png"));
		menuBar.add(mnFacturas);
		
		JMenuItem mntmInventario = new JMenuItem("INVENTARIO");
		mntmInventario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Invetarios.dialog.setVisible(true);
			}
		});
		mnFacturas.add(mntmInventario);
		
		JMenuItem mntmProductosAgotados = new JMenuItem("PRODUCTOS AGOTADOS");
		mntmProductosAgotados.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ProductosAgotados.dialog.setVisible(true);
			}
		});
		
		JMenuItem mntmRegistrarInsumos = new JMenuItem("REGISTRAR INSUMOS");
		mntmRegistrarInsumos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Insumos instancia=new Insumos();
				instancia.setVisible(true);
			}
		});
		mnFacturas.add(mntmRegistrarInsumos);
		mnFacturas.add(mntmProductosAgotados);
		
		JMenuItem mntmHistorialVentas = new JMenuItem("HISTORIAL DE VENTAS");
		mntmHistorialVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerVentas instancia=new VerVentas();
				instancia.setVisible(true);
			}
		});
		mnFacturas.add(mntmHistorialVentas);
		
		JMenuItem mntmVerUtilidades = new JMenuItem("VER UTILIDADES");
		mntmVerUtilidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerUtilidades instancia = new VerUtilidades();
				instancia.setVisible(true);
			}
		});
		mnFacturas.add(mntmVerUtilidades);
		
		JMenuItem mntmVerGananciasCategorias = new JMenuItem("VER GANANCIAS POR CATEGORIAS");
		mntmVerGananciasCategorias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GananciasPorCategoria instancia=new GananciasPorCategoria();
				instancia.setVisible(true);
			}
		});
		mnFacturas.add(mntmVerGananciasCategorias);
		
		JMenuItem mntmVerProductosEliminados = new JMenuItem("VER PRODUCTOS ELIMINADOS");
		mntmVerProductosEliminados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerProductosEliminados instancia=new VerProductosEliminados();
				instancia.setVisible(true);
			}
		});
		mnFacturas.add(mntmVerProductosEliminados);
		
		JMenuItem mntmRespaldo = new JMenuItem("CREAR RESPALDO");
		mntmRespaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Metodos instancia=new Metodos();
				try {
					instancia.crearRespaldoBd();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFacturas.add(mntmRespaldo);
		
		JMenuItem mntmRegistrarRutaRespaldo = new JMenuItem("REGISTRAR RUTA RESPALDO");
		mntmRegistrarRutaRespaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RutaRespaldoAutomatico instancia=new RutaRespaldoAutomatico();
				instancia.setVisible(true);
			}
		});
		mnFacturas.add(mntmRegistrarRutaRespaldo);
	
		JMenu menu_4 = new JMenu("                                                      ");
		menu_4.setEnabled(false);
		menuBar.add(menu_4);
		
		JMenu mnCerrarSesion = new JMenu("CERRAR SESION");
		mnCerrarSesion.setFont(new Font("Andalus", Font.BOLD, 15));
		mnCerrarSesion.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\presidencia\\Imagenes\\ICONOS\\e.png"));
		menuBar.add(mnCerrarSesion);
		
		JMenuItem mntmSalir_1 = new JMenuItem("Salir");
		mnCerrarSesion.add(mntmSalir_1);
		
		mntmSalir_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int pre=JOptionPane.showConfirmDialog(null, "DESEA CERRAR SESION ?");
				if (pre==0) {
					frame.dispose();
					
					frameLogin.setVisible(true);
					usuarioLabel.setText("");
	
					TOTAL.setText("$      0.0");
					cambio.setText("$  0.0");
					AgregarUsuarios.vee.removeAllElements();
					
					for (int i = 0; i <Login.JTResultadoUser.getRowCount(); i++) {
				      	String Usuariosss = Login.JTResultadoUser.getValueAt(i, 0).toString();
				  		AgregarUsuarios.vee.add(Usuariosss);
					}
				 }
			}
		});
		/*FONDO DE PANTALLA AQUI!!--------------------------------------------------------------------------*/
		JLabel label_4 = new JLabel("");
		label_4.setBounds(0, 0, 1294, 679);
		label_4.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		getContentPane().add(label_4);
		/*----------------------------FIN DE FONDO DE PANTALLA--------------------------------------------------------*/
	}//termina constructor	
}