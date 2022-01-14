package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class EliminarDatos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static JTextField producto;
	static EliminarDatos dialog = new EliminarDatos();
	private JButton btnElminaer;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EliminarDatos() {
		setTitle("Eliminar Producto");
		setBounds(100, 100, 242, 151);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("INGRESE EL PRODUCTO A BORRAR");
			lblNewLabel.setBounds(10, 25, 223, 14);
			lblNewLabel.setForeground(Color.WHITE);
			contentPanel.add(lblNewLabel);
		}
		{
			producto = new JTextField();
			producto.setHorizontalAlignment(SwingConstants.CENTER);
			producto.setBounds(10, 50, 206, 20);
			contentPanel.add(producto);
			producto.setColumns(10);
		}
		{
			btnElminaer = new JButton("BORRAR");
			btnElminaer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (producto.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"VERIFICA QUE LOS CAMPOS ESTEN LLENOS","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					}
					
					
				    java.sql.Connection conn=null;
				    Statement stmnt=null;
				    ResultSet rs=null;
				    
					try{
					conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
					stmnt=conn.createStatement();
					stmnt.executeUpdate("delete from productos where DESCRIPCION='"+producto.getText()+"' or CODIGO_BARRA='"+producto.getText()+"' ;");
					ConexionTableModel ctm=new ConexionTableModel("select * from productos");
					PuntoDeVenta.JTResultado1.setModel(ctm.getTablemodel());
					JOptionPane.showMessageDialog(null, "EL PRODUCTO FUE BORRADO CON EXITO");
					producto.setText("");
					PuntoDeVenta.CodigoBarra.setText("");
					EliminarDatos.dialog.setVisible(false);
					ConexionTableModel ctm2=new ConexionTableModel("select CANTIDAD,DESCRIPCION from productos WHERE CANTIDAD='0'");
					ProductosAgotados.ProductosAgotados.setModel(ctm2.getTablemodel());
					
					}catch (SQLException e1){
						e1.printStackTrace();
					}catch (ClassCastException e1) {
						// TODO: handle exception
						e1.printStackTrace();
					}finally
					{
						if(rs!=null)
							try {
								rs.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						if(conn!=null)
							try {
								conn.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						if(conn !=null)
							try {
								conn.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
				  }
				
				
				
				
			});
			btnElminaer.setBounds(127, 79, 89, 23);
			contentPanel.add(btnElminaer);
		}
		{
			label = new JLabel("");
			label.setIcon(new ImageIcon("C:\\"+Ruta.imagen+"\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
			label.setBounds(0, 0, 233, 113);
			contentPanel.add(label);
		}
	}

}
