package Punto_de_venta;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;


public class RutaRespaldoAutomatico extends JDialog {
	
	SqlOperaciones instanciaSql=new SqlOperaciones();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RutaRespaldoAutomatico dialog = new RutaRespaldoAutomatico();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public RutaRespaldoAutomatico() {
		setBounds(100, 100, 497, 275);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 29, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 22, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 300, SpringLayout.WEST, getContentPane());
		panel.setBorder(new CompoundBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ruta respaldo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), UIManager.getBorder("Button.border")));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JTextPane textPaneRuta = new JTextPane();
		textPaneRuta.setEditable(false);
		textPaneRuta.setBounds(10, 23, 258, 57);
		panel.add(textPaneRuta);
		
		JButton btnRegistrar = new JButton("Registrar ruta");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instanciaSql =new SqlOperaciones();
				JFileChooser selectorCarpeta = new JFileChooser();
				selectorCarpeta.setCurrentDirectory(new File("."));
				selectorCarpeta.setDialogTitle("Seleccione la carpeta donde seran respaldado tus datos");
				//selectorCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				selectorCarpeta.setAcceptAllFileFilterUsed(false);
				selectorCarpeta.showSaveDialog(null);
				File guarda = selectorCarpeta.getSelectedFile();

				if (guarda != null) {
					
					/*
					 * guardamos el archivo y le damos el formato directamente, si queremos que se
					 * guarde en formato doc lo definimos como .doc
					 */
					// FileWriter save=new FileWriter(guarda+".txt");
					System.out.println(guarda.toString());
					instanciaSql.registrarRutaRespaldo(guarda.toString());
					textPaneRuta.setText(guarda.toString());
				} else {
					JOptionPane.showMessageDialog(null, "operacion cancelada");
				}
				
				if(instanciaSql.registro_ruta_respaldo()) {//el ejecutarse el evento
					btnRegistrar.setEnabled(false);
				}
			}
		});
		if(instanciaSql.registro_ruta_respaldo()) {//al iniciar el jdialog
			btnRegistrar.setEnabled(false);
		}
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -27, SpringLayout.NORTH, btnRegistrar);
		springLayout.putConstraint(SpringLayout.NORTH, btnRegistrar, 147, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnRegistrar, -66, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnRegistrar, 22, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnRegistrar, 153, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnRegistrar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SqlOperaciones instanciaSql =new SqlOperaciones();
				JFileChooser selectorCarpeta = new JFileChooser();
				selectorCarpeta.setCurrentDirectory(new File("."));
				selectorCarpeta.setDialogTitle("Seleccione la carpeta donde seran respaldado tus datos");
				//selectorCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				selectorCarpeta.setAcceptAllFileFilterUsed(false);
				selectorCarpeta.showSaveDialog(null);
				File guarda = selectorCarpeta.getSelectedFile();

				if (guarda != null) {
					
					/*
					 * guardamos el archivo y le damos el formato directamente, si queremos que se
					 * guarde en formato doc lo definimos como .doc
					 */
					// FileWriter save=new FileWriter(guarda+".txt");
					System.out.println(guarda.toString());
					instanciaSql.actualizar_RutaRespaldo(guarda.toString());
					textPaneRuta.setText(guarda.toString());
				} else {
					JOptionPane.showMessageDialog(null, "operacion cancelada");
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnEditar, 27, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, btnEditar, 15, SpringLayout.EAST, btnRegistrar);
		springLayout.putConstraint(SpringLayout.SOUTH, btnEditar, -66, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnEditar, -181, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnEditar);
		
		textPaneRuta.setText(instanciaSql.obtenerRutaRespaldo());

	}
}
