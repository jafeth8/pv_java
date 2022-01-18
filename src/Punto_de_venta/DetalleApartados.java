package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetalleApartados extends JDialog {
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    public static int idApartado=0;
    public static VerApartados instanciaVerApartados=null;
	private final JPanel contentPanel = new JPanel();
	private JTable tablaRegistroApartado;
	private JTable tablaDetalleApartado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DetalleApartados dialog = new DetalleApartados();
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
	    tablaRegistroApartado.setModel(modelo);
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
	            tablaRegistroApartado.setModel(modelo);
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
	    modelo.addColumn("id_detalle_apartados");
	    modelo.addColumn("Fecha registro");
	    modelo.addColumn("Descuento");
	    tablaDetalleApartado.setModel(modelo);
	    
	    String sql="";
	   
	    sql="SELECT fk_id_apartado,fk_id_producto,productos.DESCRIPCION,detalle_apartados.precio_unitario,detalle_apartados.cantidad,id_detalle_apartados,fecha_registro,detalle_apartados.descuento FROM "
	    		+ "detalle_apartados JOIN productos ON detalle_apartados.fk_id_producto=productos.ID WHERE fk_id_apartado="+id+"";
	  
	    String []datos = new String [8];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	                datos[0]=rs.getString(1);
	                
	                datos[1]=rs.getString(2);
	                datos[2]=rs.getString(3);
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getString(5);
	                datos[5]=""+rs.getInt(6);
	                datos[6]=rs.getString(7);
	                datos[7]=""+rs.getDouble(8);
	                modelo.addRow(datos);
	            }
	            tablaDetalleApartado.setModel(modelo);
	            /*ocultamos la columna id detalle apartados*/
	            tablaDetalleApartado.getColumnModel().getColumn(5).setMaxWidth(0);
	            tablaDetalleApartado.getColumnModel().getColumn(5).setMinWidth(0);
	            tablaDetalleApartado.getColumnModel().getColumn(5).setPreferredWidth(0);
	            /*FIN DE ocultar la columna id detalle apartados*/
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	            System.out.println(ex.getMessage());
	        }
	    
	    }
	

	/**
	 * Create the dialog.
	 */
	public DetalleApartados() {
		setBounds(100, 100, 800, 419);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 29, 753, 76);
		contentPanel.add(scrollPane);
		
		tablaRegistroApartado = new JTable();
		scrollPane.setViewportView(tablaRegistroApartado);
		
		JLabel labelRegistroApartado = new JLabel("Apartado");
		labelRegistroApartado.setForeground(Color.WHITE);
		labelRegistroApartado.setBounds(10, 11, 105, 14);
		contentPanel.add(labelRegistroApartado);
		
		JLabel labelDetalleApartado = new JLabel("Detalles del apartado");
		labelDetalleApartado.setForeground(Color.WHITE);
		labelDetalleApartado.setBounds(10, 126, 122, 14);
		contentPanel.add(labelDetalleApartado);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 145, 753, 191);
		contentPanel.add(scrollPane_1);
		
		tablaDetalleApartado = new JTable();
		scrollPane_1.setViewportView(tablaDetalleApartado);
		
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemQuitarProducto = new JMenuItem("Quitar del apartado");
		menuItemQuitarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=tablaDetalleApartado.getSelectedRow();
				SqlOperaciones operacion=new SqlOperaciones();
				
				if(fila>=0) {
					int opcion=JOptionPane.showConfirmDialog(null,"esta seguro de quitar este producto del apartado?","Aviso!!",JOptionPane.YES_NO_OPTION);

					if(opcion==0) {
						int idApartado=Integer.parseInt(tablaRegistroApartado.getValueAt(0, 0).toString());//perfectamente se podria utiliza la variable estatica de la clase 'idApartado'
						double deudaOriginal=operacion.obtenerDeudaTablaApartados(idApartado);
						
						int idProducto=Integer.parseInt(tablaDetalleApartado.getValueAt(fila,1).toString());
						double precioUnitario=Double.parseDouble(tablaDetalleApartado.getValueAt(fila, 3).toString());
						double cantidadProducto=Double.parseDouble(tablaDetalleApartado.getValueAt(fila, 4).toString());
						
						if(precioUnitario<deudaOriginal) {
							//actualizamos el total y deuda del apartado
							double totalOriginal=operacion.obtenerTotalTablaApartados(idApartado);
							double totalPrecioUnitario=precioUnitario*cantidadProducto;
							operacion.disminuirTotal_y_DeudaTablaApartados(idApartado, totalOriginal, deudaOriginal, totalPrecioUnitario);
							
							//regresamos la cantidad de productos en la tabla PRODUCTOS
							double cantidadTablaProductos = operacion.obtenerCantidadTablaProducto(idProducto);
							operacion.actualizarCantidadProductosApartados(idProducto, cantidadTablaProductos, cantidadProducto);
							
							/* Eliminamos el registro de la tabla 'detallle_apartados' */
							//la columna para obtner el 'ID_DETALLE_APARTADO' se establecio OCULTA desde el metodo mostrarDetalleApartado()
							int id_detalle_apartado=Integer.parseInt(tablaDetalleApartado.getValueAt(fila,5).toString());
							operacion.eliminarRegistroDetalleApartado(id_detalle_apartado);
							//actuliazamos las tablas
							JOptionPane.showMessageDialog(null,"La deuda y total de este apartado fueron actualizados");
							mostrarApartado(DetalleApartados.idApartado);
							mostrarDetalleApartado(DetalleApartados.idApartado);
							ConexionTableModel conexion = new ConexionTableModel();
							conexion.mostrardatosProductos("",PuntoDeVenta.tablaProductos);
							VerApartados.dialog.mostrarApartados("");
							
						}else {
							JOptionPane.showMessageDialog(null,"no es posible quitar este elemento por seguridad");
						}
						
					}else {
						
						JOptionPane.showMessageDialog(null,"Operacion canceladada");
					}
					
					
				}else {
					JOptionPane.showMessageDialog(null,"no seleciono fila");
				}
			}
		});
		popupMenu.add(menuItemQuitarProducto);
		tablaDetalleApartado.setComponentPopupMenu(popupMenu);
		
		
		JButton btnCancelarTodo = new JButton("Cancelar todo el credito");
		btnCancelarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones operacion=new SqlOperaciones();
				//VerApartados apartados= new VerApartados();
				ConexionTableModel instancia=new ConexionTableModel();
				int confirmar;
				int idProducto;
				double cantidad;
				int idApartado=Integer.parseInt(tablaRegistroApartado.getValueAt(0, 0).toString());
				System.out.println("numero de filas " +tablaDetalleApartado.getRowCount());
				confirmar=JOptionPane.showConfirmDialog(null, "Esta seguro de cancelar este credito y todos sus registros?");
				if(confirmar==0) {
					for (int i = 0; i <tablaDetalleApartado.getRowCount(); i++) {
						idProducto=Integer.parseInt(tablaDetalleApartado.getValueAt(i, 1).toString());
						cantidad=Double.parseDouble(tablaDetalleApartado.getValueAt(i, 4).toString());
						double cantidadTablaProductos=operacion.obtenerCantidadTablaProducto(idProducto);
						operacion.actualizarCantidadProductosApartados(idProducto, cantidadTablaProductos, cantidad);
						
					}
					operacion.eliminarRegistroApartado(idApartado);
					JOptionPane.showMessageDialog(null,"El apartado ha sido eliminado correctamente");
					VerApartados.dialog.mostrarApartados("");
					dispose();
					instancia.mostrardatosProductos("",PuntoDeVenta.tablaProductos);
				}
			}
		});
		btnCancelarTodo.setBounds(10, 347, 201, 23);
		contentPanel.add(btnCancelarTodo);
		
		mostrarApartado(DetalleApartados.idApartado);
		mostrarDetalleApartado(DetalleApartados.idApartado);
		
		JButton btnAniadirProductosApartados = new JButton("A\u00F1adir productos");
		btnAniadirProductosApartados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id_Apartado=Integer.parseInt(tablaRegistroApartado.getValueAt(0, 0).toString());
				ProductosParaAniadir.idApartado=id_Apartado;
				ProductosParaAniadir instancia=new ProductosParaAniadir();
				instancia.setVisible(true);
				
				mostrarDetalleApartado(DetalleApartados.idApartado);
				mostrarApartado(idApartado);
				VerApartados.dialog.mostrarApartados("");
				
			}
		});
		btnAniadirProductosApartados.setToolTipText("a\u00F1adir productos a este apartado");
		btnAniadirProductosApartados.setBounds(599, 347, 164, 23);
		contentPanel.add(btnAniadirProductosApartados);
		
		
		JLabel label_10 = new JLabel("");
		label_10.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
		label_10.setBounds(0, 0, 774, 380);
		contentPanel.add(label_10);
	}
}
