package Punto_de_venta;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;

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
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class HistorialVentas extends JDialog {
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
	private JTable tablaHistorialVenta;
	public static int idVenta=0;
	private JTable tablaVenta;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HistorialVentas dialog = new HistorialVentas();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void mostrarHistorialVentas(int id){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id venta");
	    modelo.addColumn("Id Producto");
	    modelo.addColumn("Descripcion");
	    modelo.addColumn("Precio Unitario");
	    modelo.addColumn("Cantidad");
	    modelo.addColumn("Descuento");
	    tablaHistorialVenta.setModel(modelo);
	    /*establecer tamanio de columnas*/
		TableColumn columnaIdVenta = tablaHistorialVenta.getColumn("Id venta");
		columnaIdVenta.setMinWidth(10);
		columnaIdVenta.setPreferredWidth(10);
		
		TableColumn columnaIdProducto= tablaHistorialVenta.getColumn("Id Producto");
		columnaIdProducto.setMinWidth(10);
		columnaIdProducto.setPreferredWidth(10);
		
		TableColumn columnaPrcioUnitario= tablaHistorialVenta.getColumn("Precio Unitario");
		columnaPrcioUnitario.setMinWidth(10);
		columnaPrcioUnitario.setPreferredWidth(10);
		
		TableColumn ColumnaCantidad= tablaHistorialVenta.getColumn("Cantidad");
		ColumnaCantidad.setMinWidth(10);
		ColumnaCantidad.setPreferredWidth(10);
		
		TableColumn columnaDescuento= tablaHistorialVenta.getColumn("Descuento");
		columnaDescuento.setMinWidth(10);
		columnaDescuento.setPreferredWidth(10);
	    /*fin de establecer tamanio de columnas*/
	    String sql="";
	   
	    sql="SELECT fk_id_venta,fk_id_producto,productos.DESCRIPCION,detalle_ventas.precio_unitario,detalle_ventas.cantidad,detalle_ventas.descuento FROM detalle_ventas "
	    		+ "JOIN productos ON detalle_ventas.fk_id_producto=productos.ID WHERE fk_id_venta="+id+"";
	  
	    String []datos = new String [6];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                datos[5]=rs.getString(6);
	                
	                modelo.addRow(datos);
	            }
	            tablaHistorialVenta.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
		
	
	
	public void mostrarVenta(int ventaId){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id venta");
	    modelo.addColumn("Total de la venta");
	    modelo.addColumn("Fecha de la venta");
	    modelo.addColumn("Pago del cliente");
	    modelo.addColumn("Cambio al cliente");
	    modelo.addColumn("Nombre cliente");
	    modelo.addColumn("Tipo de venta");
	    //modelo.addColumn("Tipo de venta");
	    tablaVenta.setModel(modelo);
	    String sql="";

	    sql="SELECT * FROM ventas WHERE id_venta="+ventaId+"";
	 
	    String []datos = new String [5];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                //datos[5]=rs.getString(6);
	                modelo.addRow(datos);
	            }
	            tablaVenta.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	/*--------------METODO MOSTRAR VENTA CON RELACIONES-----------------------*/
	//este metodo no seria necesario si se asignara el id del usuario en la ventana de apartado desde un principio al 
	//relizar la compra(apartado)
	public void mostrarVentaConRelaciones(int ventaId){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id venta");
	    modelo.addColumn("Total de la venta");
	    modelo.addColumn("Fecha de la venta");
	    modelo.addColumn("Pago del cliente");
	    modelo.addColumn("Cambio al cliente");
	    modelo.addColumn("Nombre cliente");
	    modelo.addColumn("Tipo de venta");
	    //modelo.addColumn("Tipo de venta");
	    tablaVenta.setModel(modelo);
	    String sql="";

	    //sql="SELECT * FROM ventas WHERE id_venta="+ventaId+"";
	    sql="SELECT id_venta,total_venta,fecha_venta,pago_del_cliente,cambio_del_cliente,clientes.nombre,tipo_venta FROM ventas "
	    		+ " LEFT JOIN clientes ON ventas.fk_id_cliente=clientes.id_cliente WHERE ventas.id_venta="+ventaId+"";
	 
	    String []datos = new String [7];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                datos[5]=rs.getString(6);
	                if(rs.getString(6)==null) {
	                	datos[5]="No asignado";
	                }
	                datos[6]=rs.getString(7);
	                modelo.addRow(datos);
	            }
	            tablaVenta.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	/*--------------FIN DE METODO MOSTRAR VENTA CON RELACIONES-----------------------*/
	
	
	/**
	 * Create the dialog.
	 */
	public HistorialVentas() {
		setResizable(false);
		setBounds(100, 100, 880, 430);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 122, 843, 209);
		getContentPane().add(scrollPane);
		
		tablaHistorialVenta = new JTable();
		scrollPane.setViewportView(tablaHistorialVenta);
		
		JLabel labelTituloHistorialVenta = new JLabel("Historial de la venta:");
		labelTituloHistorialVenta.setForeground(Color.WHITE);
		labelTituloHistorialVenta.setBounds(10, 101, 182, 19);
		getContentPane().add(labelTituloHistorialVenta);
		
		JButton btnImprimir = new JButton("Imprimir venta");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int opcion=JOptionPane.showConfirmDialog(null," imprimir venta?");
				if(opcion==0) {
					//System.out.println(opcion);
					try {
						imprimir();
						JOptionPane.showMessageDialog(null, "se imprimio correctamente","mensaje de aviso",JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "CANCELADO","Mensaje de aviso",JOptionPane.CANCEL_OPTION);
				}
				//System.out.println(opcion);
			}
		});
		btnImprimir.setBounds(10, 342, 119, 41);
		getContentPane().add(btnImprimir);
		
		JLabel labelTituloVenta = new JLabel("Venta");
		labelTituloVenta.setForeground(Color.WHITE);
		labelTituloVenta.setBounds(10, 9, 119, 14);
		getContentPane().add(labelTituloVenta);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 25, 843, 65);
		getContentPane().add(scrollPane_1);
		
		tablaVenta = new JTable();
		scrollPane_1.setViewportView(tablaVenta);
		mostrarVentaConRelaciones(idVenta);
		if(tablaVenta.getRowCount()==0) {
			mostrarVenta(HistorialVentas.idVenta);
		}
		
		mostrarHistorialVentas(HistorialVentas.idVenta);
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 875, 401);
		getContentPane().add(label_10);
		
	}
	
	public void imprimir() throws IOException
	{
		String total = tablaVenta.getValueAt(0, 1).toString(); 
		String pagocliente =  tablaVenta.getValueAt(0, 3).toString();
		String cambio = tablaVenta.getValueAt(0, 4).toString();
		String fecha = tablaVenta.getValueAt(0, 2).toString();
		
	  
		//System.out.println("campo tipoVenta : " +tablaVenta.getValueAt(0, 6));
		
		//System.out.println("campo nombre : " +tablaVenta.getValueAt(0, 5));
		//String tipoVenta=tablaVenta.getValueAt(0, 6).toString();
		//String nombreCliente=tablaVenta.getValueAt(0, 5).toString();
		
			String nombreCliente="Sin nombre";
			String tipoVenta="Al Contado";
		if(tablaVenta.getValueAt(0, 5)!= null){
			tipoVenta=tablaVenta.getValueAt(0, 6).toString();
			nombreCliente=tablaVenta.getValueAt(0, 5).toString();
		}

		//System.out.println("NOOOOMBVRE: "+nombreCliente);

		
	
		
		//JOptionPane.showMessageDialog(null, nombreCliente);
		
			
		try {
			 PrinterMatrix printer = new PrinterMatrix();
			 Extenso e1 = new Extenso();

			   e1.setNumber(10);
			    //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
			   int filas = tablaHistorialVenta.getRowCount();
			   int tamanio = filas+15;
			   printer.setOutSize(tamanio, 80);

			    //Imprimir = 1ra linea de la columa de 1 a 32
			    printer.printTextWrap(0, 1, 5, 80, "=======================================================");
			    printer.printTextWrap(1, 1, 40, 80, "La soledad No 6"); //Nombre establecimiento
			    printer.printTextWrap(1, 1, 5, 80, "Materiales Fabio"); //Barrio
			    printer.printTextWrap(2, 1, 40, 80, "Tel. 423-525-0138"); //Direccion
			    printer.printTextWrap(2, 1, 10, 80, "Aranza,Mich."); //Codigo Postal
			    
			    printer.printTextWrap(3, 1, 0, 40, "Fecha: "+fecha); //Aqui va la fecha de recibo
			    //printer.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo
			    
			   
			    //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
			    //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
			    printer.printTextWrap(4,1, 0, 80, "Cant.   Producto    P/U   Sub.T");
			    //printer.printTextWrap(12,1, 0, 80, "## ");

			    for (int i = 0; i < filas; i++) {
			        int p = 5+i; //Fila

			        printer.printTextWrap(p , 1, 0, 19 , tablaHistorialVenta.getValueAt(i,4).toString());
			        printer.printTextWrap(p , 1, 5, 42 , tablaHistorialVenta.getValueAt(i,2).toString());
			        printer.printTextWrap(p , 1, 20, 49, tablaHistorialVenta.getValueAt(i,3).toString());
			        double preciounitario = Double.parseDouble(tablaHistorialVenta.getValueAt(i, 3).toString());
			        double cantidad  = Double.parseDouble(tablaHistorialVenta.getValueAt(i, 4).toString());
			        double sub = cantidad*preciounitario;
			        String subtotal = String.valueOf(sub);
			        //String pre1= printer.alinharADireita(10, tablaHistorialVenta.getValueAt(i,4).toString());
			        printer.printTextWrap(p , 1, 54, 80, subtotal);

			        //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
			        //printer.printTextWrap(p , 1, 25, 32, inp);
			    }
			    
			    DecimalFormat formateador = new DecimalFormat("#.###");
			   
			    printer.printTextWrap(filas+6, 1, 5, 80, "Subtotal: ");
			    printer.printTextWrap(filas+6, 1, 20, 80, "$"+total);

			   // String tot= printer.alinharADireita(10, total);
			    printer.printTextWrap(filas+7, 1, 5, 80, "Total a pagar: ");
			    printer.printTextWrap(filas+7, 1, 20, 80, "$"+total);

			    //String efe= printer.alinharADireita(10,90);
			    printer.printTextWrap(filas+8, 1, 5, 80, "Efectivo : ");
			    printer.printTextWrap(filas+8, 1, 20, 80, "$"+pagocliente);
			    
			    //String cam= printer.alinharADireita(10,9);
			    printer.printTextWrap(filas+9, 1, 5, 80, "Cambio : ");
			    printer.printTextWrap(filas+9, 1, 20, 80, "$"+ cambio);

			    
			    printer.printTextWrap(filas+10, 1, 5, 80, "Nombre cliente: "+nombreCliente);
			    printer.printTextWrap(filas+11, 1, 5, 80, "Estado : pagado ");
			    printer.printTextWrap(filas+12, 1, 20, 80, "Tipo de venta: "+ tipoVenta);
			    
			    //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
			   // printer.printTextWrap(filas+1, 1, 5,80, "!Gracias por su Compra!");
			    //printer.printTextWrap(filas+11, 1, 5, 80, "Materiales Fabio");
			   // printer.printTextWrap(filas+12, 1, 5, 80, "Atendido por : "+UsuarioLabel.getText());
			    //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
			    printer.printTextWrap(filas+13, 1, 3,80, "===================================================================");

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
