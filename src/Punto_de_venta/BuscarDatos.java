package Punto_de_venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuscarDatos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField producto;
	static BuscarDatos dialog = new BuscarDatos();

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
	public BuscarDatos() {
		setTitle("Buscar Producto");
		setBounds(100, 100, 236, 192);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblIngreseElProducto = new JLabel("INGRESE EL PRODUCTO A BUSCAR");
			lblIngreseElProducto.setBounds(10, 18, 200, 14);
			contentPanel.add(lblIngreseElProducto);
		}
		{
			producto = new JTextField();
			producto.setBounds(10, 43, 200, 20);
			contentPanel.add(producto);
			producto.setColumns(10);
		}
		{
			JButton button = new JButton("");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						ConexionTableModel ctm=new ConexionTableModel("select * from productos where DESCRIPCION='"+PuntoDeVenta.CodigoBarra.getText()+"'");
						PuntoDeVenta.JTResultado1.setModel(ctm.getTablemodel());
						producto.setText("");
						BuscarDatos.dialog.setVisible(false);
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			});
			button.setIcon(new ImageIcon("C:\\Program Files\\Abarrotes El Atoron\\Imagenes\\lupa.png"));
			button.setBounds(63, 74, 91, 69);
			contentPanel.add(button);
		}
		{
			JLabel label = new JLabel("");
			label.setIcon(new ImageIcon("C:\\Program Files\\Abarrotes El Atoron\\Imagenes\\fondo_inicio.jpg"));
			label.setBounds(0, 0, 220, 154);
			contentPanel.add(label);
		}
	}

}
