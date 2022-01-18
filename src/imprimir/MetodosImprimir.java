package imprimir;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;


public class MetodosImprimir {
	
	public void imprimir(String pagar,JTable tablaCompras,JLabel TOTAL,JLabel Cambio,JLabel UsuarioLabel)
	{
		
		Calendar fecha = new GregorianCalendar();
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);	
		
		try {
			 PrinterMatrix printer = new PrinterMatrix();
			 Extenso e1 = new Extenso();

			 
			   e1.setNumber(10);
			    //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
			    int filas = tablaCompras.getRowCount();
			    int tamanio = filas+15;
			    printer.setOutSize(tamanio, 80);

			    //Imprimir = 1ra linea de la columa de 1 a 32
			    printer.printTextWrap(0, 1, 5, 80, "=======================================================");
			    printer.printTextWrap(1, 1, 40, 80, "La soledad No 6"); //Nombre establecimiento
			    printer.printTextWrap(1, 1, 5, 80, "Materiales  Fabio"); //Barrio
			    printer.printTextWrap(2, 1, 40, 80, "Tel. 423-525-0138"); //Direccion
			    printer.printTextWrap(2, 1, 10, 80, "Aranza,Mich."); //Codigo Postal
			    
			    printer.printTextWrap(3, 1, 0, 40, "Fecha: "+dia+"/"+mes+"/"+anio); //Aqui va la fecha de recibo
			    printer.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo
			    
			   
			    //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
			    //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
			    printer.printTextWrap(4,1, 0, 80, "Cant.   Producto    P/U   Sub.T");
			    //printer.printTextWrap(12,1, 0, 80, "## ");

			    for (int i = 0; i < filas; i++) {
			        int p = 5+i; //Fila

			        printer.printTextWrap(p , 1, 0, 19 , tablaCompras.getValueAt(i,1).toString());
			        printer.printTextWrap(p , 1, 5, 42 , tablaCompras.getValueAt(i,2).toString());
			        printer.printTextWrap(p , 1, 20, 49, tablaCompras.getValueAt(i,3).toString());

			        String pre1= printer.alinharADireita(10, tablaCompras.getValueAt(i,4).toString());
			        printer.printTextWrap(p , 1, 54, 80, pre1);

			        //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
			        //printer.printTextWrap(p , 1, 25, 32, inp);
			    }
			    
			    DecimalFormat formateador = new DecimalFormat("#.###");

			   
			    printer.printTextWrap(filas+6, 1, 5, 80, "Subtotal: ");
			    printer.printTextWrap(filas+6, 1, 20, 80, "$"+TOTAL.getText());

			   // String tot= printer.alinharADireita(10, total);
			    printer.printTextWrap(filas+7, 1, 5, 80, "Total a pagar: ");
			    printer.printTextWrap(filas+7, 1, 20, 80, "$"+TOTAL.getText());

			    //String efe= printer.alinharADireita(10,90);
			    printer.printTextWrap(filas+8, 1, 5, 80, "Efectivo : ");
			    printer.printTextWrap(filas+8, 1, 20, 80, "$"+pagar);

			    //String cam= printer.alinharADireita(10,9);
			    printer.printTextWrap(filas+9, 1, 5, 80, "Cambio : ");
			    printer.printTextWrap(filas+9, 1, 20, 80, "$"+ Cambio.getText());

			    //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
			    printer.printTextWrap(filas+10, 1, 5,80, "!Gracias por su Compra!");
			    printer.printTextWrap(filas+11, 1, 5, 80, "Materiales Fabio");
			    printer.printTextWrap(filas+12, 1, 2, 80, "Atendido por : "+UsuarioLabel.getText());
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
			e2.printStackTrace();
			JOptionPane.showInternalMessageDialog(null," "+e2.getMessage());
		}
		
	}
	
	
	public void imprimirCotizacion(JTable tablaCompras,JLabel TOTAL,JLabel UsuarioLabel) throws IOException
	{
		Calendar fecha = new GregorianCalendar();
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);	
		
		try {
			 PrinterMatrix printer = new PrinterMatrix();
			 Extenso e1 = new Extenso();

			 
			   e1.setNumber(10);
			    //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
			    int filas = tablaCompras.getRowCount();
			    int tamanio = filas+15;
			    printer.setOutSize(tamanio, 80);

			    //Imprimir = 1ra linea de la columa de 1 a 32
			    printer.printTextWrap(0, 1, 5, 80, "=======================================================");
			    printer.printTextWrap(1, 1, 40, 80, "La soledad No 6"); //Nombre establecimiento
			    printer.printTextWrap(1, 1, 5, 80, "Materiales  Fabio"); //Barrio
			    printer.printTextWrap(2, 1, 40, 80, "Tel. 423-525-0138"); //Direccion
			    printer.printTextWrap(2, 1, 10, 80, "Aranza,Mich."); //Codigo Postal
			    
			    printer.printTextWrap(3, 1, 0, 40, "Fecha: "+dia+"/"+mes+"/"+anio); //Aqui va la fecha de recibo
			    printer.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo
			    
			   
			    //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
			    //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
			    printer.printTextWrap(4,1, 0, 80, "Cant.   Producto    P/U   Sub.T");
			    //printer.printTextWrap(12,1, 0, 80, "## ");

			    for (int i = 0; i < filas; i++) {
			        int p = 5+i; //Fila

			        printer.printTextWrap(p , 1, 0, 19 , tablaCompras.getValueAt(i,1).toString());
			        printer.printTextWrap(p , 1, 5, 42 , tablaCompras.getValueAt(i,2).toString());
			        printer.printTextWrap(p , 1, 20, 49, tablaCompras.getValueAt(i,3).toString());

			        String pre1= printer.alinharADireita(10, tablaCompras.getValueAt(i,4).toString());
			        printer.printTextWrap(p , 1, 54, 80, pre1);

			        //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
			        //printer.printTextWrap(p , 1, 25, 32, inp);
			    }
			    
			    DecimalFormat formateador = new DecimalFormat("#.###");

			 

			    //String cam= printer.alinharADireita(10,9);
			    printer.printTextWrap(filas+6, 1, 5, 80, "Cotizacion  : ");
			    printer.printTextWrap(filas+6, 1, 20, 80, "$"+ TOTAL.getText());

			    //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
			    printer.printTextWrap(filas+7, 1, 5,80, "!Formato de cotizacion!");
			    printer.printTextWrap(filas+8, 1, 5, 80, "Materiales FABIO");
			    printer.printTextWrap(filas+9, 1, 3, 80, "Cotizado por : "+UsuarioLabel.getText());
			    //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
			    printer.printTextWrap(filas+10, 1, 3,80, "===================================================================");

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
			e2.printStackTrace();
			JOptionPane.showInternalMessageDialog(null," "+e2.getMessage());
		}
	}
	
	
	public void imprimirApartado(JTable tablaCompras,JLabel TOTAL,JLabel UsuarioLabel,String NombreClienteApartados) throws IOException
	{
		Calendar fecha = new GregorianCalendar();
		int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);	
		
		try {
			 PrinterMatrix printer = new PrinterMatrix();
			 Extenso e1 = new Extenso();

			 
			   e1.setNumber(10);
			    //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
			    int filas = tablaCompras.getRowCount();
			    int tamanio = filas+15;
			    printer.setOutSize(tamanio, 80);

			    //Imprimir = 1ra linea de la columa de 1 a 32
			    printer.printTextWrap(0, 1, 5, 80, "=======================================================");
			    printer.printTextWrap(1, 1, 40, 80, "La soledad No 6"); //Nombre establecimiento
			    printer.printTextWrap(1, 1, 5, 80, "Materiales  Fabio"); //Barrio
			    printer.printTextWrap(2, 1, 40, 80, "Tel. 423-525-0138"); //Direccion
			    printer.printTextWrap(2, 1, 10, 80, "Aranza,Mich."); //Codigo Postal
			    
			    printer.printTextWrap(3, 1, 0, 40, "Fecha: "+dia+"/"+mes+"/"+anio); //Aqui va la fecha de recibo
			    printer.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo
			    
			   
			    //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
			    //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
			    printer.printTextWrap(4,1, 0, 80, "Cant.   Producto    P/U   Sub.T");
			    //printer.printTextWrap(12,1, 0, 80, "## ");

			    for (int i = 0; i < filas; i++) {
			        int p = 5+i; //Fila

			        printer.printTextWrap(p , 1, 0, 19 , tablaCompras.getValueAt(i,1).toString());
			        printer.printTextWrap(p , 1, 5, 42 , tablaCompras.getValueAt(i,2).toString());
			        printer.printTextWrap(p , 1, 20, 49, tablaCompras.getValueAt(i,3).toString());

			        String pre1= printer.alinharADireita(10, tablaCompras.getValueAt(i,4).toString());
			        printer.printTextWrap(p , 1, 54, 80, pre1);

			        //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
			        //printer.printTextWrap(p , 1, 25, 32, inp);
			    }
			    
			    DecimalFormat formateador = new DecimalFormat("#.###");

			 

			    //String cam= printer.alinharADireita(10,9);
			    printer.printTextWrap(filas+6, 1, 5, 80, "Deuda Total  : ");
			    printer.printTextWrap(filas+6, 1, 20, 80, "$"+ TOTAL.getText());
			    
			    printer.printTextWrap(filas+7, 1, 0, 80, "Nombre cliente: ");
			    printer.printTextWrap(filas+7, 1, 20, 80, " "+NombreClienteApartados);
			    System.out.println("nombre del cliente--- "+NombreClienteApartados);

			    //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
			    printer.printTextWrap(filas+8, 1, 5,80, "!Formato de apartado!");
			    printer.printTextWrap(filas+9, 1, 5, 80, "Materiales FABIO");
			    printer.printTextWrap(filas+10, 1, 3, 80, "Cotizado por : "+UsuarioLabel.getText());
			    //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
			    printer.printTextWrap(filas+11, 1, 3,80, "===================================================================");

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
			e2.printStackTrace();
			JOptionPane.showInternalMessageDialog(null," "+e2.getMessage());
		}
		
	}
}
