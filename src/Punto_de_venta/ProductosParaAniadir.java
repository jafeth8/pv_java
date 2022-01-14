package Punto_de_venta;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ProductosParaAniadir extends JDialog {
	private JTable tablaProductos;
	private JTextField textFieldBuscador;
	public static int idApartado=0;
	static double total=0;
	//static double calcula=0;
	Statement stmnt=null;
	JLabel labelTituloTotal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductosParaAniadir dialog = new ProductosParaAniadir();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Conectar cc = new Conectar();
	Connection cn = cc.conexion();
	SqlOperaciones instanciaSqlOperaciones=new SqlOperaciones();
	ConexionTableModel ctm= new ConexionTableModel();
	private JTable tablaProductosAgregados;
	private JLabel labelCantidad;
	private JTextField CantidadProductos;
	JComboBox comboBox;
	private JTextField textFieldDescuento;
	private JLabel labelTotal;


	
	/**
	 * Create the dialog.
	 */
	public ProductosParaAniadir() {
		setModal(true);
		java.sql.Connection conn = null;

		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(Ruta.URL, Ruta.Usuario, Ruta.Contrasenia);
			stmnt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		instanciaSqlOperaciones.truncarTablaTcompras("TRUNCATE tcompras_additions");
		
		setBounds(100, 100, 900, 543);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 864, 189);
		getContentPane().add(scrollPane);
		
		tablaProductos = new JTable();
		scrollPane.setViewportView(tablaProductos);
		
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemAdd = new JMenuItem("Añadir");
		menuItemAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tablaProductos.getSelectedRow();
				Metodos instanciaMetodos= new Metodos();
		
				if (fila >= 0) {
					/*------------------------------------------------------------------------------------------*/
					try {
						instanciaMetodos.addCarrito(tablaProductos, tablaProductosAgregados, textFieldDescuento, textFieldBuscador, labelTotal,Ruta.nametablaTcomprasAdditions);
					} catch (SQLException exception) {
						// TODO Auto-generated catch block
						exception.printStackTrace();
					}
					/*------------------------------------------------------------------------------------------*/
					
				}else {
					JOptionPane.showMessageDialog(null,"no selecciono producto");
				}
				
				
				
			}
		});
		popupMenu.add(menuItemAdd);
		tablaProductos.setComponentPopupMenu(popupMenu);
		
		textFieldBuscador = new JTextField();
		textFieldBuscador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Metodos instanciaMetodo= new Metodos();
				instanciaMetodo.busquedaCodigoDeBarras(tablaProductos, tablaProductosAgregados, textFieldDescuento, textFieldBuscador, comboBox, CantidadProductos, labelTotal,Ruta.nametablaTcomprasAdditions);
			}
		});
		textFieldBuscador.setBounds(252, 11, 337, 20);
		getContentPane().add(textFieldBuscador);
		textFieldBuscador.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 284, 864, 151);
		getContentPane().add(scrollPane_1);
		
		tablaProductosAgregados = new JTable();
		scrollPane_1.setViewportView(tablaProductosAgregados);
		
		
		JPopupMenu popupMenu2 = new JPopupMenu();
		JMenuItem menuItemQuitar = new JMenuItem("Quitar");
		menuItemQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones operacion=new SqlOperaciones(); 
				int fila=tablaProductosAgregados.getSelectedRow();
				if(fila>=0) {
					total = (float) operacion.obtenerSumatoriaSubtotalTablaTcompras(Ruta.nametablaTcomprasAdditions);
					String descripcionProducto=tablaProductosAgregados.getValueAt(fila, 2).toString();
					String subtotaltabla=tablaProductosAgregados.getValueAt(fila, 4).toString();
					double calcula2 = (Double.parseDouble(subtotaltabla));
					total=(float) (total-calcula2);
					labelTotal.setText(""+total);
					operacion.eliminarRegistroTablaTcompras(descripcionProducto,Ruta.nametablaTcomprasAdditions);
					//mostrarProductosTcompras("");
					ConexionTableModel ctm=new ConexionTableModel();
					try {
						ctm.datosTablaTcompras("", tablaProductosAgregados,Ruta.nametablaTcomprasAdditions);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null,"no selecciono un producto");
				}
			}
		});
		
		popupMenu2.add(menuItemQuitar);
		
		tablaProductosAgregados.setComponentPopupMenu(popupMenu2);
		
		JButton btnOk = new JButton("Listo!");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConexionTableModel cmt= new ConexionTableModel();
				SqlOperaciones operacion=new SqlOperaciones();
				int filas=tablaProductosAgregados.getRowCount();
				
				if(filas>0) {
					
					if(idApartado==0) {
						JOptionPane.showMessageDialog(null,"Los productos a agregar no estan vinculados con ningun apartado");
						return;
					}
					//idApartado se inicializa desde la clase 'DetalleApartados'
					double totalOriginal =operacion.obtenerTotalTablaApartados(idApartado);
					double deudaOriginal=operacion.obtenerDeudaTablaApartados(idApartado);
					
					
					total=operacion.obtenerSumatoriaSubtotalTablaTcompras(Ruta.nametablaTcomprasAdditions);
					operacion.aumentarTotal_y_DeudaTablaApartados(idApartado, totalOriginal, deudaOriginal,total);
					
					String fechaRegistroDetalleApartado=LocalDate.now().toString();
					
					for (int i = 0; i <filas; i++) {
						int idProducto=Integer.parseInt(tablaProductosAgregados.getValueAt(i,0).toString());
						double cantidad=Double.parseDouble(tablaProductosAgregados.getValueAt(i,1).toString());
						String descripcion=tablaProductosAgregados.getValueAt(i,2).toString();
						double precioUnitario=Double.parseDouble(tablaProductosAgregados.getValueAt(i, 3).toString());
						double costoUnitario=operacion.obtenerCostoProductoTablaProducto(idProducto);
						double descuento=Double.parseDouble(tablaProductosAgregados.getValueAt(i,5).toString());
						operacion.insertDetalleApartados(idApartado,idProducto, costoUnitario, precioUnitario, cantidad,fechaRegistroDetalleApartado,descuento);
						
						
						double cantidadTablaProducto=operacion.obtenerCantidadTablaProducto(idProducto);
						operacion.actualizarCantidadProductos(idProducto, cantidadTablaProducto, cantidad);
					}
					
					
					
					JOptionPane.showMessageDialog(null,"Nuevos productos fueron agregados a este apartado");
					ConexionTableModel obj =new ConexionTableModel();
					try {
						//obj.mostrardatos("");
						obj.mostrardatosProductos("",PuntoDeVenta.JTResultado1);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					idApartado=0;
					total=0;
					dispose();
				}else {
					JOptionPane.showMessageDialog(null,"La tabla esta vacia");
				}
				
				instanciaSqlOperaciones.truncarTablaTcompras("TRUNCATE tcompras_additions");
			}
		});
		btnOk.setBounds(10, 470, 89, 23);
		getContentPane().add(btnOk);
		
		labelTituloTotal = new JLabel("Total:");
		labelTituloTotal.setBounds(759, 474, 34, 14);
		getContentPane().add(labelTituloTotal);
		
		labelCantidad = new JLabel("CANTIDAD");
		labelCantidad.setBounds(10, 11, 71, 20);
		getContentPane().add(labelCantidad);
		
		CantidadProductos = new JTextField();
		CantidadProductos.setForeground(Color.RED);
		CantidadProductos.setHorizontalAlignment(SwingConstants.CENTER);
		CantidadProductos.setText("1");
		CantidadProductos.setBounds(79, 11, 47, 20);
		getContentPane().add(CantidadProductos);
		CantidadProductos.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"DESCRIPCION", "CATEGORIA"}));
		comboBox.setBounds(136, 11, 106, 20);
		getContentPane().add(comboBox);
		
		textFieldDescuento = new JTextField();
		textFieldDescuento.setBounds(788, 11, 86, 20);
		getContentPane().add(textFieldDescuento);
		textFieldDescuento.setColumns(10);
		textFieldDescuento.setText("0.0");
		
		JLabel lblNewLabel = new JLabel("DESCUENTO");
		lblNewLabel.setBounds(707, 11, 71, 20);
		getContentPane().add(lblNewLabel);
		
		labelTotal = new JLabel("");
		labelTotal.setBounds(803, 474, 71, 14);
		getContentPane().add(labelTotal);
		
		
		
		try {
			ctm.mostrardatosProductos("",tablaProductos);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			ctm.datosTablaTcompras("", tablaProductosAgregados,Ruta.nametablaTcomprasAdditions);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	
	//------------------------------------------------------------------------------------------------------------------------------
}
