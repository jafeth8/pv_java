package Punto_de_venta;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import com.toedter.calendar.JDateChooser;

public class Insumos extends JDialog {
	private JTextField textFieldDescripcion;
	private JTextField textFieldCosto;
	private JTable tablaInsumos;
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    private JTextField textField_idInsumo;
    private JTextField textFieldDescripcionInsumo;
    private JTextField textFieldCostoInsumo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Insumos dialog = new Insumos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void mostrarInsumos(String valor){
	    DefaultTableModel modelo= new DefaultTableModel();
	    modelo.addColumn("Id insumo");
	    modelo.addColumn("Descripcion");
	    modelo.addColumn("Costo");
	    modelo.addColumn("Fecha");

	    String sql="";
	    //table.setModel(modelo);
	    tablaInsumos.setModel(modelo);
	    if(valor.equals(""))
	    {
	        sql="SELECT * FROM insumos";
	    }
	    else{
	        sql="SELECT * FROM insumos WHERE fecha='"+valor+"'";
	    }
	 
	    String []datos = new String [4];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                datos[3]=rs.getString(4);
	     
	                modelo.addRow(datos);
	            }
	            
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	
	/**
	 * Create the dialog.
	 */
	public Insumos() {
		setBounds(100, 100, 615, 404);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 573, 323);
		getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Registrar insumo", null, panel, null);
		panel.setLayout(null);
		
		JLabel labelDescripcion = new JLabel("DESCRIPCION DEL INSUMO");
		labelDescripcion.setForeground(Color.BLACK);
		labelDescripcion.setBounds(10, 11, 187, 14);
		panel.add(labelDescripcion);
		
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setColumns(10);
		textFieldDescripcion.setBounds(10, 36, 213, 20);
		panel.add(textFieldDescripcion);
		
		JLabel lblNewLabel = new JLabel("COSTO DEL INSUMO");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(10, 67, 147, 14);
		panel.add(lblNewLabel);
		
		textFieldCosto = new JTextField();
		textFieldCosto.setColumns(10);
		textFieldCosto.setBounds(10, 92, 213, 20);
		panel.add(textFieldCosto);
		
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDate(new Date());
		dateChooser.setBounds(10, 148, 147, 20);

		panel.add(dateChooser);
		
		JLabel labelFecha = new JLabel("FECHA");
		labelFecha.setBounds(10, 123, 78, 14);
		panel.add(labelFecha);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones operacion=new SqlOperaciones();
				String fecha="";
				try {
					SimpleDateFormat formatoFecha=new SimpleDateFormat("yyyy-MM-dd");
					fecha=formatoFecha.format(dateChooser.getDate());
				} catch (Exception vacio) {		
					System.out.println(vacio.getMessage());
				}

				String descripcion=textFieldDescripcion.getText();
				String costo=textFieldCosto.getText();
			
				
				
				if(descripcion.equals("")|| fecha.equals("") || costo.equals("")) {
					JOptionPane.showMessageDialog(null,"Verifique que todos los campos enten llenos","Error!",JOptionPane.WARNING_MESSAGE);
				}else {
					double costoDouble=Double.parseDouble(costo);
					operacion.insertTablaInsumos(descripcion, costoDouble, fecha);
					JOptionPane.showMessageDialog(null,"operacion realizada correctamente");
					dispose();
				}
			}
		});
		btnRegistrar.setBounds(10, 179, 89, 23);
		panel.add(btnRegistrar);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Editar o eliminar insumo", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 113, 568, 182);
		panel_1.add(scrollPane);
		
		tablaInsumos = new JTable();
		scrollPane.setViewportView(tablaInsumos);
		
		JLabel lblIdInsumo = new JLabel("Id insumo:");
		lblIdInsumo.setBounds(10, 11, 71, 14);
		panel_1.add(lblIdInsumo);
		
		textField_idInsumo = new JTextField();
		textField_idInsumo.setEnabled(false);
		textField_idInsumo.setBounds(10, 36, 50, 20);
		panel_1.add(textField_idInsumo);
		textField_idInsumo.setColumns(10);
		
		JLabel labelDescripcionInsumo = new JLabel("Descripcion");
		labelDescripcionInsumo.setHorizontalAlignment(SwingConstants.CENTER);
		labelDescripcionInsumo.setBounds(77, 11, 132, 14);
		panel_1.add(labelDescripcionInsumo);
		
		textFieldDescripcionInsumo = new JTextField();
		textFieldDescripcionInsumo.setBounds(75, 36, 134, 20);
		panel_1.add(textFieldDescripcionInsumo);
		textFieldDescripcionInsumo.setColumns(10);
		
		JLabel labelCostoInsumo = new JLabel("Costo");
		labelCostoInsumo.setHorizontalAlignment(SwingConstants.CENTER);
		labelCostoInsumo.setBounds(229, 11, 89, 14);
		panel_1.add(labelCostoInsumo);
		
		textFieldCostoInsumo = new JTextField();
		textFieldCostoInsumo.setBounds(227, 36, 91, 20);
		panel_1.add(textFieldCostoInsumo);
		textFieldCostoInsumo.setColumns(10);
		
		JLabel labelFechaInsumo = new JLabel("Fecha");
		labelFechaInsumo.setHorizontalAlignment(SwingConstants.CENTER);
		labelFechaInsumo.setBounds(349, 11, 111, 14);
		panel_1.add(labelFechaInsumo);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(349, 36, 111, 20);
		panel_1.add(dateChooser_1);
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemModificar = new JMenuItem("modificar");
		menuItemModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=tablaInsumos.getSelectedRow();
			    if(fila>=0){
			    	try {
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
			        textField_idInsumo.setText(tablaInsumos.getValueAt(fila, 0).toString());
			        textFieldDescripcionInsumo.setText(tablaInsumos.getValueAt(fila, 1).toString());
			        
			        textFieldCostoInsumo.setText(tablaInsumos.getValueAt(fila, 2).toString());
			        
			        String fecha=tablaInsumos.getValueAt(fila,3).toString();
			        //creamos el formato en el que deseamos mostrar la fecha
			        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
			        //creamos una variable tipo Date y la ponemos NULL
			        Date fechaN = null;
			        try {
			            //parseamos de String a Date usando el formato
			            fechaN = formatoDelTexto.parse(fecha);
			            //seteamos o mostramos la fecha en el JDateChooser
			            dateChooser_1.setDate(fechaN);
			        } catch (ParseException ex) {
			            ex.printStackTrace();
			        }
			        

			    }
			    else{
			    	JOptionPane.showMessageDialog(null,"no seleciono fila");
			    }
				
			}
		});
		
		
		JMenuItem menuItemEliminar = new JMenuItem("Eliminar");
		menuItemEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instancia=new SqlOperaciones();
				int fila=tablaInsumos.getSelectedRow();
				int id_insumo=Integer.parseInt(tablaInsumos.getValueAt(fila,0).toString());
				
			    if(fila>=0){
			    	//instancia.actualizarStateTablaClientes(id_cliente, 3); // 3 es eliminado en la tabla sstate
			    	instancia.eliminarRegistroInsumos(id_insumo);
					JOptionPane.showMessageDialog(null, "Insumo eliminado satisfactoriamente");
					mostrarInsumos("");
			    }
			    else{
			    JOptionPane.showMessageDialog(null,"no seleciono fila");
			    }
				
			}
		});
		
		
		popupMenu.add(menuItemModificar);
		popupMenu.add(menuItemEliminar);
		tablaInsumos.setComponentPopupMenu(popupMenu);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instanciaSql=new SqlOperaciones();
				int idInsumo=Integer.parseInt(textField_idInsumo.getText());
				String descripcion=textFieldDescripcionInsumo.getText();
				String costoString=textFieldCostoInsumo.getText();
				String fecha="";
				
				try {
					
					SimpleDateFormat formatoFecha=new SimpleDateFormat("yyyy-MM-dd");
					fecha=formatoFecha.format(dateChooser_1.getDate());
					
				}catch (NullPointerException vacio) {
					// TODO: handle exception
					System.out.println(vacio.getMessage());
				}
				if(descripcion.equals("")|| costoString.equals("") || fecha.equals("")) {
					JOptionPane.showMessageDialog(null,"Algunos Campos estan vacios","Error!",JOptionPane.WARNING_MESSAGE);
				}else {
					double costo=Double.parseDouble(costoString);
					instanciaSql.actualizarInsumos(idInsumo, descripcion, costo, fecha);
					mostrarInsumos("");
				}
			}
		});
		btnActualizar.setBounds(10, 79, 100, 23);
		panel_1.add(btnActualizar);
		
		JButton btnFiltrar = new JButton("Filtrar por fecha");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fecha="";
				
				try {
					
					SimpleDateFormat formatoFecha=new SimpleDateFormat("yyyy-MM-dd");
					fecha=formatoFecha.format(dateChooser_1.getDate());
					
				}catch (NullPointerException vacio) {
					// TODO: handle exception
					System.out.println(vacio.getMessage());
				}
				if(fecha.equals("")) {
					JOptionPane.showMessageDialog(null,"El campo fecha esta vacio");
				}else {
					mostrarInsumos(fecha);
				}
			}
		});
		btnFiltrar.setBounds(120, 79, 148, 23);
		panel_1.add(btnFiltrar);

		mostrarInsumos("");
	}
}
