package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;

import javax.swing.JTextField;

import org.jpedal.PdfDecoder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;



public class Facturas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static Facturas dialog = new Facturas();
	String Fecha_DE_Compra;  
	String PAGO_TOTAL;
	static JTable TFacturas;
	
	static java.sql.Connection conn=null;
    static Statement stmnt=null;
    static ResultSet rs=null;
    String Usuario="Alejandro";
    static JLabel todo;
    static double Contador=0;
    
	String Contrasenia="12345";
	String URL="jdbc:mysql://localhost/tienda2015";
	private JTextField Tfecha;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the dialog.
	 */
	
	public Facturas() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Program Files\\Abarrotes El Atoron\\Imagenes\\caja.jpg"));
		setTitle("TICKETS");
		setBounds(100, 100, 426, 472);
		getContentPane().setLayout(null);
		
		JLabel lblFacturas = new JLabel("TICKETS");
		lblFacturas.setBounds(117, 0, 160, 32);
		lblFacturas.setForeground(Color.RED);
		lblFacturas.setFont(new Font("Trebuchet MS", Font.PLAIN, 40));
		getContentPane().add(lblFacturas);
		
		JButton btnImprimir = new JButton("IMPRIMIR");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Contador=0;
				
				for (int i = 0; i < TFacturas.getRowCount(); i++) {
					String n= TFacturas.getValueAt(i, 3).toString();
					Double fac = Double.parseDouble(n);
					Contador=Contador+fac;
				}
				
				try {
			    	Document doc = new Document();
					PdfWriter.getInstance(doc, new FileOutputStream("D:\\Facturas.pdf"));		            
		            doc.open();
		            PdfPTable pdfTable = new PdfPTable(TFacturas.getColumnCount());
		            
		            //adding table headers
		            for (int i = 0; i < TFacturas.getColumnCount(); i++) {
		                pdfTable.addCell(TFacturas.getColumnName(i));
		            }
		            //extracting data from the JTable and inserting it to PdfPTable
		            for (int rows = 0; rows < TFacturas.getRowCount(); rows++) {
		                for (int cols = 0; cols < TFacturas.getColumnCount(); cols++) {
		                    pdfTable.addCell(TFacturas.getModel().getValueAt(rows, cols).toString());
		                }
		            }
		           
		       
		            
		            doc.add(new Paragraph("       "));
		            doc.add(new Paragraph("       "));
		            doc.add(new Paragraph("       "));
		            doc.add(new Paragraph("                                                                  Fecha : "+Tfecha.getText()+""));
		            doc.add(new Paragraph("       "));
		            doc.add(pdfTable);
		            doc.add(new Paragraph("       "));
		            doc.add(new Paragraph("              TOTAL DE VENTA AL DIA ...................... $ " +
		            		""+Contador));
		            
	                doc.close();
	                
	                int pre=JOptionPane.showConfirmDialog(null, "� DESEA IMPRIMIR TICKETS ?");
					 if (pre==0) {
				     imprimir();
				     Imprimir.dialog.setVisible(true);
					 new Thread(new Hilo2()).start();
					 }else
				     {
						 JOptionPane.showMessageDialog(null, "GRACIAS POR CUIDAR EL MEDIO HAMBIENTE");
				     }
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			    
			}
		});
		btnImprimir.setBounds(311, 400, 89, 23);
		getContentPane().add(btnImprimir);
		
		todo = new JLabel("");
		todo.setBounds(10, 400, 300, 23);
		todo.setForeground(Color.RED);
		todo.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		getContentPane().add(todo);
		
		
		Tfecha = new JTextField();
		Tfecha.setBounds(10, 40, 202, 20);
		getContentPane().add(Tfecha);  
		Tfecha.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 390, 315);
		getContentPane().add(scrollPane);
		
		TFacturas = new JTable();
		TFacturas.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(TFacturas);
		ConexionTableModel ctm;
		try {
			
			
			ctm = new ConexionTableModel("SELECT * FROM facturas");
			TFacturas.setModel(ctm.getTablemodel());
			Calendar fecha = new GregorianCalendar();
			int anio = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH);
	        int dia = fecha.get(Calendar.DAY_OF_MONTH);
			Tfecha.setText(""+anio+"-"+(mes+1)+"-"+dia+"");
			
			Contador=0;
			
			for (int i = 0; i < TFacturas.getRowCount(); i++) {
				String n= TFacturas.getValueAt(i, 3).toString();
				Double fac = Double.parseDouble(n);
				Contador=Contador+fac;
			}
			
			todo.setText("Ganancias total de $ "+ Contador);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton btnBuscarFecha = new JButton("BUSCAR FECHA");
		btnBuscarFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				if (Tfecha.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"DEBES INGRESAR UNA FECHA","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
				}else{
				try {
					ConexionTableModel ctm=new ConexionTableModel("select * from facturas where Fecha='"+Tfecha.getText()+"'");
					TFacturas.setModel(ctm.getTablemodel());
				} catch (Exception e) {
					// TODO: handle exception
				}
				}
				
				Contador=0;
				
				for (int i = 0; i < TFacturas.getRowCount(); i++) {
					String n= TFacturas.getValueAt(i, 3).toString();
					Double fac = Double.parseDouble(n);
					Contador=Contador+fac;
				}
				
				todo.setText("Ganancias del dia de hoy $ "+ Contador);
			}
		});
		btnBuscarFecha.setBounds(236, 42, 150, 23);
		getContentPane().add(btnBuscarFecha);
				
				JDateChooser dateChooser = new JDateChooser();
				dateChooser.setBounds(273, 11, 81, 20);
				//getContentPane().add(dateChooser);
				
			
					JLabel label = new JLabel("");
					label.setIcon(new ImageIcon("C:\\Program Files\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
					label.setBounds(0, 0, 408, 434);
					getContentPane().add(label);
		
	}
	
	

	public void imprimir() throws IOException
	{
		FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("D:\\Facturas.pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
 
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }
 
        inputStream.close();
	}
	
	public static class Hilo2 implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i <= 101; i++) {
				Imprimir.barra.setValue(i);
				Imprimir.barra.repaint();
				Imprimir.Por.setText("% "+i);
				if (i==101) {
					Imprimir.dialog.setVisible(false);
					
				}
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}
	}
}
