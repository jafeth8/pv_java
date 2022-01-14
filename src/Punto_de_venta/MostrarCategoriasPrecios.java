package Punto_de_venta;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;

public class MostrarCategoriasPrecios extends JDialog {
	private JTable table;
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    public static int idProducto=0;
    public static String descripcion;
    public static String precio="";
    public static String precioBase;
    public static Object []datosBase = new Object [3];
    private JTextField textField_id;
    private JTextField textField_descripcion;
    private JTextField textField_precio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MostrarCategoriasPrecios dialog = new MostrarCategoriasPrecios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	 public void mostrardatos(String valor,int idProducto){
		    DefaultTableModel modelo= new DefaultTableModel();
		    modelo.addColumn("ID PRODUCTO");
		    modelo.addColumn("DESCRIPCION");
		    modelo.addColumn("PRECIO");
		    table.setModel(modelo);
		    String sql="";
		    if(valor.equals(""))
		    {
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
		    	//SELECT * FROM categoria_precios_productos WHERE fk_id_producto=1
		    	sql="SELECT fk_id_producto,descripcion,precio FROM categoria_precios_productos WHERE fk_id_producto='"+idProducto+"'";
		    }
		   
		    Object []datos = new Object [3];
		        try {
		            Statement st = cn.createStatement();
		            ResultSet rs = st.executeQuery(sql);
		            while(rs.next()){
		            	
		            	datos[0]=rs.getInt(1);
		                datos[1]=rs.getString(2);
		                datos[2]=rs.getDouble(3);
		                
		                modelo.addRow(datos);
		            }
		            modelo.addRow(datosBase);
		            table.setModel(modelo);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }finally {
		        	
					
				}
		    
		    }

	
	
	
	/**
	 * Create the dialog.
	 */
	public MostrarCategoriasPrecios() {
		setModal(true);
		setBounds(100, 100, 603, 421);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 161, 567, 170);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSeleccionar = new JButton("Seleccionar precio");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=table.getSelectedRow();
				if(fila>=0) {
					descripcion=table.getValueAt(fila,1).toString();
					precio=table.getValueAt(fila,2).toString();
					dispose();
				}else {
					JOptionPane.showMessageDialog(null,"no selecciono fila");
				}
			}
		});
		btnSeleccionar.setBounds(10, 351, 158, 23);
		getContentPane().add(btnSeleccionar);
		
		JLabel labelMostrarPrecioBase = new JLabel("El precio base de este producto es de: "+precioBase);
		labelMostrarPrecioBase.setFont(new Font("Tahoma", Font.PLAIN, 13));
		labelMostrarPrecioBase.setBounds(10, 117, 567, 33);
		getContentPane().add(labelMostrarPrecioBase);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLUE));
		panel.setBounds(10, 11, 567, 95);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setBounds(10, 28, 86, 20);
		panel.add(textField_id);
		textField_id.setColumns(10);
		
		textField_descripcion = new JTextField();
		textField_descripcion.setEnabled(true);
		textField_descripcion.setEditable(true);
		textField_descripcion.setText("");
		textField_descripcion.setBounds(118, 28, 145, 20);
		panel.add(textField_descripcion);
		textField_descripcion.setColumns(10);
		
		textField_precio = new JTextField();
		textField_precio.setBounds(273, 28, 150, 20);
		panel.add(textField_precio);
		textField_precio.setColumns(10);
		
		JButton btnNewButton = new JButton("Crear nueva categoria");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instancia=new SqlOperaciones();
				Metodos obj=new Metodos();
				int id=Integer.parseInt(textField_id.getText());
				String descripcionProd=textField_descripcion.getText();
				if(obj.isDouble(textField_precio.getText())==false) {
					JOptionPane.showMessageDialog(null,"el precio debe ser un numero valido");
				}
				double precio=Double.parseDouble(textField_precio.getText());
				instancia.insertTablaCategoriaPrecios(id,descripcionProd,precio);
				JOptionPane.showMessageDialog(null,"operacion realizada correctamente");
				mostrardatos("", id);
			}
		});
		btnNewButton.setBounds(10, 59, 209, 23);
		panel.add(btnNewButton);
		
		mostrardatos("",idProducto);
		textField_id.setText(""+idProducto);
		
		JLabel label_id = new JLabel("id");
		label_id.setHorizontalAlignment(SwingConstants.CENTER);
		label_id.setBounds(10, 11, 86, 14);
		panel.add(label_id);
		
		JLabel lblDescripcion = new JLabel("descripcion");
		lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcion.setBounds(118, 11, 145, 14);
		panel.add(lblDescripcion);
		
		JLabel lblNewLabel = new JLabel("precio");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(273, 11, 150, 14);
		panel.add(lblNewLabel);
	}
}
