package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class VerRegistrosApartados extends JDialog {
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	public static int idApartado=0;
	private JTable tablaMostrarApartado;
	private JTable tablaDetalleApartado;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerRegistrosApartados dialog = new VerRegistrosApartados();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void mostrarApartado(int id_apartado){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id Apartado");
	    modelo.addColumn("Id Cliente");
	    modelo.addColumn("Nombre");
	    modelo.addColumn("Total Compra");
	    modelo.addColumn("Fecha Apartado");
	    modelo.addColumn("Total abono");
	    modelo.addColumn("Deuda");
	    //modelo.addColumn("Tipo de venta");
	    tablaMostrarApartado.setModel(modelo);
	    String sql="";

	    sql="SELECT id_apartado,fk_id_cliente,clientes.nombre,total,fecha_de_apartado,total_abono,deuda "
	    		+ "FROM apartados JOIN clientes ON apartados.fk_id_cliente=clientes.id_cliente WHERE id_apartado ="+id_apartado+"";
	 
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
	                datos[6]=rs.getString(7);
	                //datos[5]=rs.getString(6);
	                modelo.addRow(datos);
	            }
	            tablaMostrarApartado.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	
	
	public void mostrarDetalleApartado(int id){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id Apartado");
	    modelo.addColumn("Id Producto");
	    modelo.addColumn("Descripcion");
	    modelo.addColumn("Precio unitario");
	    modelo.addColumn("Cantidad");
	    tablaDetalleApartado.setModel(modelo);
	    String sql="";
	   
	    sql="SELECT fk_id_apartado,fk_id_producto,productos.DESCRIPCION,detalle_apartados.precio_unitario,detalle_apartados.cantidad FROM "
	    		+ "detalle_apartados JOIN productos ON detalle_apartados.fk_id_producto=productos.ID WHERE fk_id_apartado="+id+"";
	  
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
	                
	                modelo.addRow(datos);
	            }
	            tablaDetalleApartado.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	
	public void mostrarRegistroApartado(int idApartado) {
		Statement st=null;
		ResultSet rs=null;
    	DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id apartado");
        modelo.addColumn("Dinero recibido");
        modelo.addColumn("Abono");
        modelo.addColumn("Cambio Entregado");
        modelo.addColumn("Fecha del Abono");
       
        String sql;
        sql="SELECT fk_id_apartado,dinero_recibido,abono,cambio_entregado,fecha_abono FROM registro_apartados WHERE fk_id_apartado="+idApartado+"";
        String []datos = new String [5];
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()){
            	
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
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
				cn.close();
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
    }
	
	/**
	 * Create the dialog.
	 */
	
	public VerRegistrosApartados() {
		setResizable(false);
		setBounds(100, 100, 632, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 325, 596, 201);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel labelTituloRegistroApartados = new JLabel("Registro del apartado");
		labelTituloRegistroApartados.setForeground(Color.WHITE);
		labelTituloRegistroApartados.setBounds(10, 300, 167, 14);
		contentPanel.add(labelTituloRegistroApartados);
		
		JButton btnImprimirComprobante = new JButton("Imprimir comprobante");
		btnImprimirComprobante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=table.getSelectedRow();
				if(fila>=0) {
					try {
						imprimircomprobante();
						JOptionPane.showMessageDialog(null, "se imprimio correctamente","mensaje de aviso",JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}else {
					JOptionPane.showMessageDialog(null,"no seleccionaste un registro del apartado");
				}
			}
		});
		btnImprimirComprobante.setBounds(10, 537, 178, 23);
		contentPanel.add(btnImprimirComprobante);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 33, 596, 54);
		contentPanel.add(scrollPane_1);
		
		tablaMostrarApartado = new JTable();
		scrollPane_1.setViewportView(tablaMostrarApartado);
		
		JLabel labelApartado = new JLabel("Apartado");
		labelApartado.setForeground(Color.WHITE);
		labelApartado.setBackground(Color.WHITE);
		labelApartado.setBounds(10, 8, 67, 14);
		contentPanel.add(labelApartado);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 123, 596, 166);
		contentPanel.add(scrollPane_2);
		
		tablaDetalleApartado = new JTable();
		scrollPane_2.setViewportView(tablaDetalleApartado);
		
		JLabel labelDetalleApartado = new JLabel("Detalle apartado");
		labelDetalleApartado.setForeground(Color.WHITE);
		labelDetalleApartado.setBounds(10, 98, 95, 14);
		contentPanel.add(labelDetalleApartado);
		mostrarApartado(VerRegistrosApartados.idApartado);
		mostrarDetalleApartado(VerRegistrosApartados.idApartado);
		mostrarRegistroApartado(VerRegistrosApartados.idApartado);
		
		JButton btnImprimirCompras = new JButton("Imprimir compras");
		btnImprimirCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					imprimirCompra();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImprimirCompras.setBounds(428, 537, 178, 23);
		contentPanel.add(btnImprimirCompras);
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 632, 600);
		contentPanel.add(label_10);
		
	}
	
	public void imprimircomprobante() throws IOException
	{
		int fila=table.getSelectedRow();
		
		String dinerorecibido = table.getValueAt(fila, 1).toString();
		String abono = table.getValueAt(fila, 2).toString();
		String cambio  =  table.getValueAt(fila, 3).toString();
		String fecha = table.getValueAt(fila, 4).toString();
	
        String deuda = tablaMostrarApartado.getValueAt(0, 6).toString();
			
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
			    printer.printTextWrap(3, 1, 0, 40, "Fecha: "+fecha);
			    
			    printer.printTextWrap(4, 1, 0, 40, "Dinero Recibido: "+dinerorecibido);
			    printer.printTextWrap(5, 1, 0, 40, "Abono: "+abono);
			    printer.printTextWrap(6, 1, 0, 40, "Cambio : "+cambio);
			    //printer.printTextWrap(5, 1, 0, 40, "Dinero Recibido");
			    printer.printTextWrap(7, 1, 0, 40, "Deuda : "+ deuda);
			   
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
	
	
	public void imprimirCompra() throws IOException
	{
		
		String dinerorecibido = tablaMostrarApartado.getValueAt(0, 1).toString();
		String nombre = tablaMostrarApartado.getValueAt(0, 2).toString();
		String totalabono = tablaMostrarApartado.getValueAt(0, 5).toString();
		String totalcompra  =  tablaMostrarApartado.getValueAt(0, 3).toString();
		String fechaparatado = tablaMostrarApartado.getValueAt(0, 4).toString();
		String DeudaTotal = tablaMostrarApartado.getValueAt(0, 6).toString();

		Calendar fecha = new GregorianCalendar();
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);	
		
		try {
			 PrinterMatrix printernew = new PrinterMatrix();
			 Extenso e2 = new Extenso();

			 
			   e2.setNumber(10);
			    //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
			    int filas = tablaDetalleApartado.getRowCount();
			    int tamanio = filas+15;
			    printernew.setOutSize(tamanio, 80);

			    //Imprimir = 1ra linea de la columa de 1 a 32
			    printernew.printTextWrap(0, 1, 5, 80, "=======================================================");
			    printernew.printTextWrap(1, 1, 40, 80, "La soledad No 6"); //Nombre establecimiento
			    printernew.printTextWrap(1, 1, 5, 80, "Materiales  Fabio"); //Barrio
			    printernew.printTextWrap(2, 1, 40, 80, "Tel. 423-525-0138"); //Direccion
			    printernew.printTextWrap(2, 1, 10, 80, "Aranza,Mich."); //Codigo Postal
			    
			    printernew.printTextWrap(3, 1, 0, 40, "Fecha: "+dia+"/"+mes+"/"+anio); //Aqui va la fecha de recibo
			    printernew.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo
			    
			   
			    //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
			    //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
			    printernew.printTextWrap(4,1, 0, 80, "Producto           Sub.T");
			    //printer.printTextWrap(12,1, 0, 80, "## ");

			    for (int i = 0; i < filas; i++) {
			        int p = 5+i; //Fila
			        
			        printernew.printTextWrap(p , 1, 20, 49, tablaDetalleApartado.getValueAt(i,4).toString());
			        printernew.printTextWrap(p , 1, 0, 19 , tablaDetalleApartado.getValueAt(i,2).toString());
			       // printernew.printTextWrap(p , 1, 5, 42 , tablaDetalleApartado.getValueAt(i,3).toString());
			        

			        //String pre1= printernew.alinharADireita(10, table.getValueAt(i,5).toString());
			        //printernew.printTextWrap(p , 1, 54, 80, pre1);

			        //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
			        //printer.printTextWrap(p , 1, 25, 32, inp);
			    }
			    
			    DecimalFormat formateador = new DecimalFormat("#.###");

			   
			    printernew.printTextWrap(filas+6, 1, 5, 80, "Subtotal: ");
			    printernew.printTextWrap(filas+6, 1, 20, 80, "$"+totalcompra);

			   // String tot= printer.alinharADireita(10, total);
			    printernew.printTextWrap(filas+7, 1, 5, 80, "Total a pagar: ");
			    printernew.printTextWrap(filas+7, 1, 20, 80, "$"+totalcompra);

			    //String efe= printer.alinharADireita(10,90);
			    printernew.printTextWrap(filas+8, 1, 5, 80, "Total Abono : ");
			    printernew.printTextWrap(filas+8, 1, 20, 80, "$"+totalabono);

			    //String cam= printer.alinharADireita(10,9);
			    printernew.printTextWrap(filas+9, 1, 5, 80, "Deuda : ");
			    printernew.printTextWrap(filas+9, 1, 20, 80, "$"+DeudaTotal);

			    //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
			    printernew.printTextWrap(filas+10, 1, 5,80, "!Gracias por su Compra!");
			    printernew.printTextWrap(filas+11, 1, 5, 80, "Materiales Fabio");
			    //printer.printTextWrap(filas+12, 1, 2, 80, "Atendido por : "+UsuarioLabel.getText());
			    //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
			    printernew.printTextWrap(filas+12, 1, 3,80, "===================================================================");

		       printernew.toFile("impresion.txt");
		       
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
