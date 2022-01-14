package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PruebasJframe extends JFrame {

	private JPanel contentPane;
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PruebasJframe frame = new PruebasJframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void mostrardatos(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id venta");
	    modelo.addColumn("Total de la venta");
	    modelo.addColumn("Fecha de la venta");
	    modelo.addColumn("Pago del cliente");
	    modelo.addColumn("Cambio Entregado al cliente");
	    modelo.addColumn("Tipo de venta");
	    table.setModel(modelo);
	    String sql="";
	    if(valor.equals(""))
	    {
	        sql="SELECT * FROM ventas";
	    }
	    else{
	        sql="SELECT * FROM ventas WHERE fecha_venta='"+valor+"'";
	    }
	 
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
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }

	/**
	 * Create the frame.
	 */
	public PruebasJframe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int option=JOptionPane.showConfirmDialog(null, "ocpon");
		System.out.println("***********"+option);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(10, 11, 95, 20);
		contentPane.add(dateChooser);
		
		JMonthChooser monthChooser = new JMonthChooser();
		monthChooser.setBounds(115, 11, 99, 20);
		contentPane.add(monthChooser);
		
		JYearChooser yearChooser = new JYearChooser();
		yearChooser.setBounds(224, 11, 47, 20);
		contentPane.add(yearChooser);
		
		JButton btnFecha = new JButton("fecha");
		btnFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat fecha=new SimpleDateFormat("yyyy-MM-dd");
				String mostrarFecha=fecha.format(dateChooser.getDate());
				System.out.println(mostrarFecha);
				int mes=monthChooser.getMonth();
				System.out.println("mes: "+(mes+1));
				System.out.println("anio "+yearChooser.getYear());
				//JOptionPane.showMessageDialog(null, mostrarFecha);
			}
		});
		btnFecha.setBounds(335, 8, 89, 23);
		contentPane.add(btnFecha);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 52, 500, 291);
		contentPane.add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("New tab", null, scrollPane, null);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnFile = new JButton("File");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre="";
				  JFileChooser file=new JFileChooser();
				  file.showSaveDialog(null);
				  File guarda =file.getSelectedFile();

				  if(guarda !=null)
				  {
				   /*guardamos el archivo y le damos el formato directamente,
				    * si queremos que se guarde en formato doc lo definimos como .doc*/
				    //FileWriter  save=new FileWriter(guarda+".txt");
					System.out.println(guarda);  
				    }else {
				    	JOptionPane.showMessageDialog(null,"operacion cancelada");
				    }
				 }
			
		});
		btnFile.setBounds(434, 8, 89, 23);
		contentPane.add(btnFile);
		
		mostrardatos("");
		

	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
