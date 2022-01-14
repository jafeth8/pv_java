package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jpedal.examples.viewer.commands.Exit;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class VerApartados extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static JTable table;
	public static VerApartados dialog = new VerApartados();
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    private JTextField textFieldBusqueda;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			//VerApartados dialog = new VerApartados();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarApartados(String nombreCliente) {
		
		Statement st=null;
		ResultSet rs=null;
    	DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id apartado");
        modelo.addColumn("Fecha de Apartado");
        modelo.addColumn("Id Cliente");
        modelo.addColumn("Nombre");
       
        modelo.addColumn("Total de la compra");
        modelo.addColumn("Deuda");
       
        
        String sql="";
        if(nombreCliente.equals("")) {
	        sql="SELECT id_apartado,fecha_de_apartado,fk_id_cliente,clientes.nombre,total,deuda FROM "
		    + "apartados JOIN clientes ON apartados.fk_id_cliente=clientes.id_cliente WHERE apartados.estado='en deuda'";
        }else {
        	sql="SELECT id_apartado,fecha_de_apartado,fk_id_cliente,clientes.nombre,total,deuda FROM apartados JOIN clientes ON apartados.fk_id_cliente=clientes.id_cliente "
        			+ "WHERE clientes.nombre LIKE '%"+nombreCliente+"%' AND apartados.estado='en deuda'";
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
                //datos[6]=rs.getString(7);
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
	public VerApartados() {
		setResizable(false);
		setBounds(100, 100, 800, 417);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 75, 764, 255);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//para scroll horinzontal
		scrollPane.setViewportView(table);
		
		JLabel labelTituloApartados = new JLabel("Productos apartados");
		labelTituloApartados.setForeground(Color.WHITE);
		labelTituloApartados.setBounds(10, 11, 123, 14);
		contentPanel.add(labelTituloApartados);
		
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemHistorialVenta = new JMenuItem("Ver detalle de este apartado");
		menuItemHistorialVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=table.getSelectedRow();
				int id_Apartado=Integer.parseInt(table.getValueAt(fila,0).toString());
				DetalleApartados.idApartado=id_Apartado;
				DetalleApartados instancia =new DetalleApartados();
				instancia.setVisible(true);
				
			}
		});
		popupMenu.add(menuItemHistorialVenta);
		table.setComponentPopupMenu(popupMenu);
		
		
		JButton btnIngresarAbono = new JButton("Ingresar Abono ");
		btnIngresarAbono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Metodos validar=new Metodos();
				SqlOperaciones operacion =new SqlOperaciones();
				int fila=table.getSelectedRow();
				String entradaDineroRecibido,entradaCantidadAbono;
				
				
				boolean cantidadAbono=true,CantidadDineroRecibido=true,cantidadesCorrectas=true;
				int idApartado;//variable para ingresar a la tabla registro_apartados
				double dineroRecibido,abono,cambio;//variables para ingresar a la tabla registro_apartados
				String fecha;//variable para ingresar a la tabla registro_apartados
				
				
				if (fila>=0) {
					try {// evita que se caiga la ejecucion si las entradas son nulas a causa de que se cierren los JOptionPane
						do {
							int idApartadoAuxliar=Integer.parseInt(table.getValueAt(fila, 0).toString());//esta linea tiene que estar dentro del try catch ya que al no seleccionar ninguna fila el resultado sera null y lanzara un excepcion
							
							do {
								entradaDineroRecibido = JOptionPane.showInputDialog("Dinero recibido");
								
							} while (validar.validarEntradaApartados(entradaDineroRecibido) == false);
							
							do {
								entradaCantidadAbono = JOptionPane.showInputDialog("Cantidad que sera abonada");
								if(validar.isDouble(entradaCantidadAbono)) {
									cantidadAbono=validar.validarCantidadAbono(entradaCantidadAbono,idApartadoAuxliar);
								}
							} while (validar.validarEntradaApartados(entradaCantidadAbono)==false || cantidadAbono==false);
						} while (validar.entradasValidas(entradaDineroRecibido,entradaCantidadAbono)==false);
					
						/*---------------ENTRADA VALIDA:CONTINUAR-----------------------------*/
						double precioTotal;
						double abonoTotal;
						double updateAbonoTotal;
						double updateDeuda;
						//double abonoAcumulado;
						//double deuda;
						idApartado=Integer.parseInt(table.getValueAt(fila, 0).toString());
						int id_cliente=Integer.parseInt(table.getValueAt(fila, 2).toString());//variable para determinar el tipo de cliente que generara la compra
						abono=Double.parseDouble(entradaCantidadAbono);
						dineroRecibido=Double.parseDouble(entradaDineroRecibido);
						cambio=dineroRecibido-abono;
						fecha=LocalDate.now().toString();
						operacion.insertTablaRegistroApartados(idApartado,dineroRecibido, abono, cambio, fecha);
						
						
						precioTotal=operacion.obtenerPrecioTotalTablaApartados(idApartado);
						abonoTotal=operacion.obtenerTotalAbonoTablaApartados(idApartado);
						updateAbonoTotal=abonoTotal+abono;
					    operacion.actualizarTotalAbonoTablaApartados(idApartado,updateAbonoTotal);
					    updateDeuda=precioTotal-updateAbonoTotal;
					    operacion.actualizarDeudaTablaApartados(idApartado, updateDeuda);
					    if(updateDeuda==0) {
					    	String fechaLiquidacion=LocalDate.now().toString();
					    	operacion.actualizarEstadoTablaApartados(idApartado,"Pagado");
					    	operacion.actualizarFechaLiquidacionTablaApartados(idApartado, fechaLiquidacion);
					    	operacion.insertVentas(precioTotal, fechaLiquidacion, updateAbonoTotal, 0, "por apartado");
					    	int idVenta=operacion.obtenerIdTablaVentas();
					    	//int tipoCliente=operacion.obtenerIdTipoClienteTablaClientes(id_cliente);
					    	/********************09-02-2021******************************/
					    	//se relaciona el id del cliente independentemente si es socio o normal
					    	//el objetivo es tener registro de las compras del cliente dichos registros deben ser vizulizados
					    	//en la tabla ventas y tablas relacioonadas
					    	operacion.actualizarIdClienteTablaVentas(idVenta, id_cliente);
					    	/********************FIN 09-02-2021**************************/
					    	/*if(tipoCliente==2) {

					    	}*/
					    	
					    	
					    	String detalleApartados[][];
					    	detalleApartados=operacion.obtenerDatosDetalleApartados(idApartado);
					    	for (int i = 0; i < detalleApartados.length; i++) {
					    		String idProducto=detalleApartados[i][0];
					    		String costoUnitario=detalleApartados[i][1];
								String precioUnitario=detalleApartados[i][2];
								String cantidad=detalleApartados[i][3];
								String descuentoString=detalleApartados[i][4];
									
								int idProductoInteger=Integer.parseInt(idProducto);
								double costo_unitario=Double.parseDouble(costoUnitario);
								double precioUnitarioDouble=Double.parseDouble(precioUnitario);
								double cantidadDouble=Double.parseDouble(cantidad);
								double descuento=Double.parseDouble(descuentoString);
								operacion.insertDetalleVentas(idVenta, idProductoInteger, costo_unitario, precioUnitarioDouble, cantidadDouble,descuento);
								
							}
					    	
					    	JOptionPane.showMessageDialog(null,"Este Cliente ha completado los pagos :)");
					    	
					    	int option=JOptionPane.showConfirmDialog(null, "Desea imprimir el ticket de abono? ","mensaje", JOptionPane.YES_NO_OPTION);
					    	
					    	if(option==0) {
					    		imprimircomprobante(String.valueOf(dineroRecibido), String.valueOf(abono), String.valueOf(cambio));
					    	}
					    }else {
					    	imprimircomprobante(String.valueOf(dineroRecibido), String.valueOf(abono), String.valueOf(cambio));
					    }
					    
					    JOptionPane.showMessageDialog(null,"operacion realizada correctamente");
					    mostrarApartados("");
					    JOptionPane.showMessageDialog(null, "El cambio es de: "+cambio+"");
					    

						
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}

				}else {
					JOptionPane.showMessageDialog(null,"no selecciono ningun registro","Error",JOptionPane.ERROR_MESSAGE );
				}
				//dineroRecibido=Double.parseDouble(JOptionPane.showInputDialog("Dinero recibido"));
				
			}
		});
		btnIngresarAbono.setBounds(10, 341, 140, 36);
		contentPanel.add(btnIngresarAbono);
		
		JButton btnRegistroApartados = new JButton("Ver registros");
		btnRegistroApartados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=table.getSelectedRow();
				if(fila>=0) {
					int id_apartado=Integer.parseInt(table.getValueAt(fila,0).toString());
					VerRegistrosApartados.idApartado=id_apartado;
					VerRegistrosApartados instancia = new VerRegistrosApartados();
					instancia.setVisible(true);
					
					
				}else {
					JOptionPane.showMessageDialog(null,"No selecciono ningun registro");
				}
				
			}
		});
		btnRegistroApartados.setBounds(651, 341, 123, 36);
		contentPanel.add(btnRegistroApartados);
		
		mostrarApartados("");
		
		JButton btnBuscar = new JButton("Filtrar por nombre del cliente");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarApartados(textFieldBusqueda.getText().toString());
			}
		});
		btnBuscar.setToolTipText("para mostrar todos los registros deje el campo de texto vacio");
		btnBuscar.setBounds(10, 41, 202, 23);
		contentPanel.add(btnBuscar);
		
		textFieldBusqueda = new JTextField();
		textFieldBusqueda.setToolTipText("busqueda por nombre del cliente");
		textFieldBusqueda.setBounds(222, 41, 173, 23);
		contentPanel.add(textFieldBusqueda);
		textFieldBusqueda.setColumns(10);
		JLabel label_10 = new JLabel("");
		label_10.setToolTipText("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 800, 383);
		contentPanel.add(label_10);
	}
	
	public void imprimircomprobante(String dineroRecibido,String abono,String cambio) throws IOException
	{
		int fila=table.getSelectedRow();
		
		//String dinerorecibido = table.getValueAt(fila, 1).toString();
		//String abono = table.getValueAt(fila, 2).toString();
		//String cambio  =  table.getValueAt(fila, 3).toString();
		Calendar fecha = new GregorianCalendar();
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
		//String fecha = table.getValueAt(fila, 4).toString();
        String fechaVenta=LocalDate.now().toString();
		int n=0;
		String deuda = table.getValueAt(fila, 5).toString();
		
		//bloque comentando para fines de testing
		/*
		if(n==0) {//esta condicional se uso para poder asignar un return; que detuviera el flujo de la funcion
	        Double DeudaFinal2 = Double.parseDouble(deuda) - Double.parseDouble(abono);
	        System.out.println("Deuda: "+deuda);
	        System.out.println("Abono: "+abono);
	        System.err.println(String.valueOf(DeudaFinal2));
	        return;
		}
        */
		//fin de bloque comentado para fines de testing
        
		try {
			 PrinterMatrix printer = new PrinterMatrix();
			 Extenso e1 = new Extenso();

			   e1.setNumber(10);
			    //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
			   // int filas = tablaHistorialVenta.getRowCount();
			   //int tamanio = filas+15;
			   printer.setOutSize(10, 80);

			    //Imprimir = 1ra linea de la columa de 1 a 32
			   
			   printer.printTextWrap(0, 1, 5, 80, "=======================================================");
			    printer.printTextWrap(1, 1, 40, 80, "La soledad No 6"); //Nombre establecimiento
			    printer.printTextWrap(1, 1, 5, 80, "Materiales Fabio"); //Barrio
			    printer.printTextWrap(2, 1, 40, 80, "Tel. 423-525-0138"); //Direccion
			    printer.printTextWrap(2, 1, 10, 80, "Aranza,Mich."); //Codigo Postal
			    printer.printTextWrap(3, 1, 0, 40, "Fecha abono: "+fechaVenta);
			    
			    printer.printTextWrap(4, 1, 0, 40, "Dinero Recibido: "+dineroRecibido);
			    printer.printTextWrap(5, 1, 0, 40, "Abono: "+abono);
			    printer.printTextWrap(6, 1, 0, 40, "Cambio : "+cambio);
			    Double DeudaFinal = Double.parseDouble(deuda) - Double.parseDouble(abono);
			    printer.printTextWrap(7, 1, 0, 40, "Deuda : "+ String.valueOf(DeudaFinal));
			   
			    DecimalFormat formateador = new DecimalFormat("#.###");
			   
			    printer.printTextWrap(8, 1, 5,80,"!Comprobante de pago!");
			   
		
			    printer.printTextWrap(9, 1, 3,80, "===================================================================");

		       printer.toFile("impresion.txt");
		       
		      FileInputStream inputStream = null;
		        try {
		            inputStream = new FileInputStream("impresion.txt");
		        } catch (FileNotFoundException ex) {
		            ex.printStackTrace();
		        }
		        if (inputStream == null) {
		            return;
		        }

		        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
		        Doc document = new SimpleDoc(inputStream, docFormat, null);

		        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

		        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();


		        if (defaultPrintService != null) {
		            DocPrintJob printJob = defaultPrintService.createPrintJob();
		            try {
		                printJob.print(document, attributeSet);

		            } catch (Exception ex) {
		                ex.printStackTrace();
		            }
		        } else {
		            System.err.println("No existen impresoras instaladas");
		        }

		        //inputStream.close();
				
				
			
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
	}
}
