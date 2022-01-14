package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Font;

public class VerUtilidades extends JDialog {
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
	private final JPanel contentPanel = new JPanel();
	private JTable tableUtilidades;
	private JTable tableInsumos;
	private JTable tableInsumosDetalles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VerUtilidades dialog = new VerUtilidades();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarUtilidades(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Sumatoria por precio Unitario");
	    modelo.addColumn("Sumatoria por costo Unitario");
	    modelo.addColumn("Ganancias");
	    modelo.addColumn("Del mes");
	    modelo.addColumn("Del año");
	    
	    tableUtilidades.setModel(modelo);
	    String sql="";
	    if(valor.equals(""))
	    {
	        sql="SELECT SUM((precio_unitario)*cantidad) AS precio_unitario,SUM((costo_unitario)*cantidad) AS costo_unitario, SUM((precio_unitario)*cantidad)-SUM((costo_unitario)*cantidad) AS ganancias, MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta) FROM "
	        		+ "detalle_ventas JOIN ventas ON detalle_ventas.fk_id_venta=ventas.id_venta GROUP BY MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta)";
	    }
	    else{
	    	//WHERE YEAR(ventas.fecha_venta)=YEAR('2021-08-30') AND MONTH(ventas.fecha_venta)=MONTH('2021-08-30')
	    	sql="SELECT SUM((precio_unitario)*cantidad) AS precio_unitario,SUM((costo_unitario)*cantidad) AS costo_unitario, SUM((precio_unitario)*cantidad)-SUM((costo_unitario)*cantidad) AS ganancias, MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta) FROM "
	        		+ "detalle_ventas JOIN ventas ON detalle_ventas.fk_id_venta=ventas.id_venta WHERE YEAR(ventas.fecha_venta)=YEAR('"+valor+"') AND MONTH(ventas.fecha_venta)=MONTH('"+valor+"')"
	        		+ " GROUP BY MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta)";	
	    }
	 
	    String []datos = new String [5];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=""+rs.getDouble(1);
	                
	                datos[1]=""+rs.getDouble(2);
	                datos[2]=""+rs.getDouble(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                
	                
	                modelo.addRow(datos);
	            }
	            tableUtilidades.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	
	
	
	public void mostrarInsumos(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("gastos por insumo");
	    modelo.addColumn("DEL MES");
	    modelo.addColumn("DEL AÑO");

	    
	    tableInsumos.setModel(modelo);
	    String sql="";
	    if(valor.equals(""))
	    {
	        sql="SELECT SUM(costo),MONTH(fecha), YEAR(fecha) FROM insumos GROUP BY MONTH(fecha),YEAR(fecha)";
	    }
	    else{
	      sql="SELECT SUM(costo),MONTH(fecha), YEAR(fecha) FROM insumos "
	      		+ "WHERE MONTH(fecha)=MONTH('"+valor+"') and YEAR(fecha)=YEAR('"+valor+"') GROUP BY MONTH(fecha),YEAR(fecha)";
	    }
	 
	    String []datos = new String [6];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=""+rs.getDouble(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                
	                modelo.addRow(datos);
	            }
	            tableInsumos.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	
	public void mostrarInsumosDetalle(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("DESCRIPCION");
	    modelo.addColumn("GASTO");
	    modelo.addColumn("FECHA");

	    
	    tableInsumosDetalles.setModel(modelo);
	    String sql="";
	    if(valor.equals(""))
	    {
	        sql="SELECT descripcion,costo,fecha FROM insumos";
	    }
	    else{
	      sql="SELECT descripcion,costo,fecha FROM insumos WHERE MONTH(fecha)=MONTH('"+valor+"') and YEAR(fecha)=YEAR('"+valor+"')";
	    }
	 
	    String []datos = new String [3];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=""+rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                
	                modelo.addRow(datos);
	            }
	            tableInsumosDetalles.setModel(modelo);
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	
	/**
	 * Create the dialog.
	 */
	public VerUtilidades() {
		setBounds(100, 100, 860, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 824, 212);
		contentPanel.add(scrollPane);
		
		tableUtilidades = new JTable();
		scrollPane.setViewportView(tableUtilidades);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(86, 11, 111, 20);
		contentPanel.add(dateChooser);
		
		JLabel labelTituloBuscar = new JLabel("Buscar por:");
		labelTituloBuscar.setForeground(Color.RED);
		labelTituloBuscar.setBounds(10, 11, 68, 20);
		contentPanel.add(labelTituloBuscar);
		
		JButton btnBuscar = new JButton("Filtrar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fecha="";
				
				try {
					
					SimpleDateFormat formatoFecha=new SimpleDateFormat("yyyy-MM-dd");
					fecha=formatoFecha.format(dateChooser.getDate());
					mostrarUtilidades(fecha);
					mostrarInsumos(fecha);
					mostrarInsumosDetalle(fecha);
				}catch (NullPointerException vacio) {
					// TODO: handle exception
					//JOptionPane.showMessageDialog(null,"no seleccionaste una fecha: se seleccionara la fecha de hoy");
					//fecha=LocalDate.now().toString();
					mostrarUtilidades("");
					mostrarInsumos("");
					mostrarInsumosDetalle("");
						
				}
				catch (Exception e2) {
					// TODO: handle exception
				}			
			}
		});
		btnBuscar.setBounds(207, 11, 89, 23);
		contentPanel.add(btnBuscar);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 311, 400, 205);
		contentPanel.add(scrollPane_1);
		
		tableInsumos = new JTable();
		scrollPane_1.setViewportView(tableInsumos);
		
		JButton btnImprimir = new JButton("IMPRIMIR");
		btnImprimir.setVisible(false);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("evento lanzado");
			}
		});
		btnImprimir.setBounds(10, 527, 106, 23);
		contentPanel.add(btnImprimir);
		
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(428, 311, 406, 205);
		contentPanel.add(scrollPane_2);
		
		tableInsumosDetalles = new JTable();
		scrollPane_2.setViewportView(tableInsumosDetalles);
		
		JLabel lblNewLabel = new JLabel("GASTOS POR INSUMO");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 271, 824, 29);
		contentPanel.add(lblNewLabel);
		
		JLabel labelFondo = new JLabel("");
		labelFondo.setBounds(0, 0, 844, 561);
		labelFondo.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		contentPanel.add(labelFondo);
		mostrarUtilidades("");
		mostrarInsumos("");
		mostrarInsumosDetalle("");
	}
}
