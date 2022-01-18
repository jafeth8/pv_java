package Punto_de_venta;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Metodos {
	Statement stmnt=null;
	
	
	public void crearRespaldoBd() throws IOException {

		Process p = Runtime.getRuntime().exec("mysqldump -u root "+Ruta.database+"");
		
		new HiloLector(p.getErrorStream()).start();
		
		InputStream is = p.getInputStream();// Pedimos la entrada
		/*------jafeth8-----*/
		JFileChooser file=new JFileChooser();
		file.showSaveDialog(null);
		File guarda =file.getSelectedFile();
		/*------------------*/
		if(guarda!=null) {
			FileOutputStream fos = new FileOutputStream(guarda+".sql"); // creamos el archivo para le respaldo
			byte[] buffer = new byte[1000]; // Creamos una variable de tipo byte para el buffer

			int leido = is.read(buffer); // Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
			while (leido > 0) {
				fos.write(buffer, 0, leido);// Buffer de caracteres, Desplazamiento de partida para empezar a escribir
											// caracteres, Número de caracteres para escribir
				leido = is.read(buffer);
			}
	 
			fos.close();//Cierra respaldo
		}else {
			JOptionPane.showMessageDialog(null,"La operacion fue cancelada!","AVISO",JOptionPane.WARNING_MESSAGE);
		}
		
	}
	

	public boolean validarEntradaCantidad(String entrada) {
		boolean resultado=true;
		if (entrada.equals("")) {
			JOptionPane.showMessageDialog(null, "El campo esta vacio","error",JOptionPane.ERROR_MESSAGE);
			resultado= false;
		}else if(isNumeric(entrada)) {
		    
			int numero=Integer.parseInt(entrada);
		    if(numero<=0) {
		       JOptionPane.showMessageDialog(null, "Numero no valido: debe ser mayor a cero","error",JOptionPane.ERROR_MESSAGE);	
			   resultado=false;
		    }
		}else {
		   JOptionPane.showMessageDialog(null, "Ingresa un numero y entero, no decimal!","error",JOptionPane.ERROR_MESSAGE);
		   return resultado=false;
		}
		
		return resultado;
	}
	public boolean validarCantidadProducto(String entrada, int cantidadTabla) {
		boolean resultado=true;
		int cantidad=Integer.parseInt(entrada);
		if(cantidad>cantidadTabla) {
			JOptionPane.showMessageDialog(null,"Producto insuficiente","Aviso!",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return resultado;
	}
	
	public boolean validarEntradaApartados(String entrada) {
		boolean resultado=true;
		if (entrada.equals("")) {
			JOptionPane.showMessageDialog(null, "El campo esta vacio","error",JOptionPane.ERROR_MESSAGE);
			resultado= false;
		}
		else if(isDouble(entrada)==false) {
			resultado=false;
			JOptionPane.showMessageDialog(null, "Ups,al parecer no ingresaste un numero o escribiste una , entre los numeros","error",JOptionPane.ERROR_MESSAGE);
		}else if(isDouble(entrada)) {
			if(Double.parseDouble(entrada)<=0) {
				   JOptionPane.showMessageDialog(null, "El numero es negativo o igual a cero","error",JOptionPane.ERROR_MESSAGE);
				   resultado=false;
			   }
		}
		return resultado;
	}
	
   public boolean validarCantidadAbono(String entrada,int id_apartado) {
	   Conectar cc= new Conectar();
	   Connection cn= cc.conexion();
	   SqlOperaciones instancia =new SqlOperaciones();
	   boolean resultado=true;
	   
	   double precioTotal=instancia.obtenerPrecioTotalTablaApartados(id_apartado);
	   double abono=Double.parseDouble(entrada);
	   double abonoTotal=instancia.obtenerTotalAbonoTablaApartados(id_apartado);
	   double abonoAcumulado;
	   double deuda=instancia.obtenerDeudaTablaApartados(id_apartado);
	   
	   abonoAcumulado=abonoTotal+abono;
	   if(abonoAcumulado>precioTotal) {
		   JOptionPane.showMessageDialog(null, "Tan solo la deuda es de: "+deuda+" ingresa la cantidad que corresponde al abono","error",JOptionPane.ERROR_MESSAGE);
		   resultado=false;
	   }
	  
	   return resultado;
   }
   

   
     public boolean entradasValidas(String entrada1,String entrada2) {
    	 boolean resultado=true;
    	 double entradaDineroRecibido=Double.parseDouble(entrada1);
    	 double entradaCantidadAbono=Double.parseDouble(entrada2);
    	 if(entradaDineroRecibido<entradaCantidadAbono) {
    		 JOptionPane.showMessageDialog(null,"El dinero recibido del cliente es menor a la cantidad que va a abonar","Atencion",JOptionPane.WARNING_MESSAGE);
    		 resultado=false;
    	 }
    	 return resultado;
     }
	
	public boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	public boolean isDouble(String cadena){
		try {
			Double.parseDouble((cadena));
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	public boolean limitePorcentaje(int porcentaje) {
		boolean limite=false;
		if(porcentaje>50) {
			JOptionPane.showMessageDialog(null,"El porcentaje es de mas del 50% cuidado!","Atencion",JOptionPane.WARNING_MESSAGE);
			limite=true;
		}else {
			limite=false;
		}
		return limite;
	}
	
	public boolean entradaVacia(String entrada) {
		boolean vacio=false;
		if(entrada.contentEquals("")) {
			JOptionPane.showMessageDialog(null,"El campo esta vacio","Atencion",JOptionPane.WARNING_MESSAGE);
			vacio=true;
		}else {
			vacio=false;
		}
		return vacio;
	}
	
	
	boolean validarExistenciaEnTablaTcompras(String des,String nameTablaTcompras)  throws IOException
    {
    	try
		{
    		java.sql.Connection conn=null;
    	     Statement stmnt=null;
    	    ResultSet rs=null;
    	    
    	    conn=(Connection) DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
    	    stmnt=conn.createStatement();
            ResultSet resultadosConsulta = stmnt.executeQuery ("SELECT * FROM "+nameTablaTcompras+" WHERE DESCRIPCION='"+des+"'");
            if( resultadosConsulta.first() )        
                return true;        
            else
                return false;                  
		} catch (Exception e)
		{
   			e.printStackTrace();
            return false;
		}
    }
	
	
	public void busquedaCodigoDeBarras(JTable tablaProductos,JTable tablaCompras,JTextField jtextFieldDescuento,JTextField buscador,JComboBox comboBoxTipoBusqueda,JTextField cantidadProductos,JLabel labelTotal,String nameTablaCompras){
		ConexionTableModel ctm = new ConexionTableModel();
		SqlOperaciones instanciaSqlOperaciones=new SqlOperaciones();
		String MetodoBusqueda=(String)comboBoxTipoBusqueda.getSelectedItem();
		int Nproductos;
		int cantidadJtexfiel;
		double x;
		double calcula;
		double total=0;
		
		java.sql.Connection conn=null;    
		ResultSet rs=null;
		try {
			conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
			stmnt=conn.createStatement();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		if (cantidadProductos.getText().equals("")) 
		{
			JOptionPane.showMessageDialog(null,"DEBES DE PONER UNA CANTIDAD","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
		}else {
			
			/*---------------VALIACION PARA EL CAMPO DESCUENTO----------------------------*/
			if(isDouble(jtextFieldDescuento.getText())==false) {
				JOptionPane.showMessageDialog(null, "El valor del campo descuento debe ser un numero","Aviso!",JOptionPane.WARNING_MESSAGE);
				jtextFieldDescuento.setText("0.0");
				buscador.setText("");
				return;
			}
			
			if(jtextFieldDescuento.getText().equals("")) {
				JOptionPane.showMessageDialog(null,"El campo descuento esta vacio");
				return;
			}
			
			/*------------FIN DE VALIDACION DE CAMPO DESCUENTO----------------------------*/
			
			if(instanciaSqlOperaciones.busquedaPorCodigoBarra(buscador.getText())){
			
				//mostrarProductoCodigoDeBarra("");
				ctm.mostrarProductoCodigoDeBarra(buscador.getText(), tablaProductos);
				/*------------------------------jafeth8-------------------------------------------*/
				final int flsel=0;
				try{
				
						
				Nproductos=Integer.parseInt(cantidadProductos.getText());
				
				cantidadJtexfiel=Nproductos;
				//int cb=Nproductos;

				int idProducto=Integer.parseInt(tablaProductos.getValueAt(flsel, 0).toString());
				String cantidad=tablaProductos.getValueAt(flsel, 2).toString();
				String descripcion = tablaProductos.getValueAt(flsel, 3).toString();
				String precio=tablaProductos.getValueAt(flsel, 4).toString();
				String categoria=tablaProductos.getValueAt(flsel, 6).toString();
				double descuento=Double.parseDouble((jtextFieldDescuento.getText().toString()));
				
				if(categoria.equals("unidades")) {
					JOptionPane.showMessageDialog(null,"Los productos de categoria 'unidades' no debe ser buscado por metodo de codigo de barras,debido a cuestiones tecnicas, por favor busque este producto por descripcion o categoria","ERRORR",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int cantidadEntablaProductos = Integer.parseInt(cantidad);
				if (cantidadJtexfiel<=0) {
					JOptionPane.showMessageDialog(null,"EL DATO ES INCORRECTO","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					//ConexionTableModel ctm1 = new ConexionTableModel(Ruta.query);
					//JTResultado1.setModel(ctm1.getTablemodel());
				}else{
				if (cantidadJtexfiel>cantidadEntablaProductos) {
					JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					//ConexionTableModel ctm1 = new ConexionTableModel(Ruta.query);
					//JTResultado1.setModel(ctm1.getTablemodel());
				}
				else{
						if(instanciaSqlOperaciones.existenciaEnComprasPorDescripcion(descripcion,nameTablaCompras) )
	              		{
							for (int j = 0; j < tablaCompras.getRowCount(); j++) {
									
									String cantidadTablaProductosAgregados=tablaCompras.getValueAt(j, 1).toString();
									String descripcioncompras=tablaCompras.getValueAt(j, 2).toString();
									double descuentoTablaCompras=Double.parseDouble(tablaCompras.getValueAt(j, 5).toString());
									double precioTablaProductos = Double.parseDouble(precio.toString());//------------
	              			 		double resultadoDescuento = precioTablaProductos-descuento;//----------
									if (descripcioncompras.equals(descripcion)) {
										
										/*---------------------VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
										if(descuento!=descuentoTablaCompras) {
											JOptionPane.showMessageDialog(null, "El producto: "+descripcioncompras+" ya tiene un descuento de: "+descuentoTablaCompras+
											" y usted esta reasignando un descuento de: "+descuento+" \n**el descuento debe ser el mismo para este producto**"+
											"\n'si quiere reasignar un descuento a este producto quitelo del carrito de compras y vuelva a gregarlo con el descuento deseado'","Aviso!",JOptionPane.WARNING_MESSAGE );
											jtextFieldDescuento.setText("0.0");
											buscador.setText("");
											return;
										}
										/*---------------------FIN VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
									    x=(Double.parseDouble(cantidadTablaProductosAgregados)+cantidadJtexfiel);
									    double a=((Double.parseDouble(cantidadTablaProductosAgregados)+cantidadJtexfiel)*resultadoDescuento);
										if (cantidadEntablaProductos<x) {
											JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
											return;
										}else{
											stmnt.executeUpdate("UPDATE  `"+Ruta.database+"`.`"+nameTablaCompras+"` SET  `CANTIDAD` =  '"+x+"',`SUB_TOTAL` =  '"+a+"' WHERE DESCRIPCION='"+descripcion+"'");

											//mostrarProductosTcompras("");
											ctm.datosTablaTcompras("",tablaCompras,nameTablaCompras);
											//calcula = (resultadoDescuento*cantidadJtexfiel);
											total=instanciaSqlOperaciones.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
											buscador.setText("");
											
										}
									  }
									}
									//textFieldDescuento.setText("0");
									jtextFieldDescuento.setText("0.0");
									cantidadProductos.setText("1");
	              		 }else{

			              			double precioTablaProductos = Double.parseDouble(precio.toString());//------------
	              			 		double resultadoDescuento = precioTablaProductos-descuento;//----------
	              			 		
	              			 		/*--------------------VALIDACIONES-----------------*/
	              			 		
	              			 		if(descuento<0) {
	              			 			JOptionPane.showMessageDialog(null, "El descuento no debe ser un numero negativo","Aviso!",JOptionPane.WARNING_MESSAGE);
	              			 			jtextFieldDescuento.setText("0.0");
	              			 			buscador.setText("");
	              			 			return;
	              			 		}
	              			 		if(descuento>precioTablaProductos) {
	              			 			JOptionPane.showMessageDialog(null, "El descuento no debe ser mayor al precio del producto","Aviso!",JOptionPane.WARNING_MESSAGE);
	              			 			jtextFieldDescuento.setText("0.0");
	              			 			buscador.setText("");
	              			 			return;
	              			 		}
	              			 		/*--------------------FIN DE VALIDACIONES-----------------*/
	              			 		
	              		  			x=(cantidadJtexfiel*resultadoDescuento);//-----------------
	              		  			
	              		  			String Sub_Total= String.valueOf(x);
	              		  			stmnt.executeUpdate("INSERT INTO `"+Ruta.database+"`.`"+nameTablaCompras+"` (`ID`,`CANTIDAD`, `DESCRIPCION`, `PRECIO_UNITARIO`, `SUB_TOTAL`,`DESCUENTO`) VALUES ('"+idProducto+"','"+cantidadJtexfiel+"', '"+descripcion+"', '"+resultadoDescuento+"', '"+Sub_Total+"','"+jtextFieldDescuento.getText()+"');");
	              		  			//mostrarProductosTcompras("");
	              		  			ctm.datosTablaTcompras("", tablaCompras,nameTablaCompras);
	              		  			//calcula = (resultadoDescuento*cantidadJtexfiel);
	              		  		    total=instanciaSqlOperaciones.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
		    					    
	              		  		    buscador.setText("");				    					    
		    					    jtextFieldDescuento.setText("0.0");
		    					    cantidadProductos.setText("1");
					          }
				labelTotal.setText("$ "+total);
				}
				}
				}
			 catch (Exception e2) {
				 e2.printStackTrace();
			}	
		    /*-----------------------------------JAFETH8---------------------------------------*/
			}//FIN DEL IF CODIGO DE BARRAS
			else if (MetodoBusqueda.equals("DESCRIPCION")) {
				ctm.mostrardatosProductos(buscador.getText(), tablaProductos);
				if(instanciaSqlOperaciones.busquedaDescripcion(buscador.getText())==false) {
					JOptionPane.showMessageDialog(null,"El valor no coincide con ningun tipo busqueda");
				}
			}else if (MetodoBusqueda.equals("CATEGORIA")) {
				try {
					ctm.mostrardatosCategorias(buscador.getText(), tablaProductos);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(instanciaSqlOperaciones.busquedaCategoria(buscador.getText())==false) {
					JOptionPane.showMessageDialog(null,"el valor no coincide con ningun tipo busqueda");
				}
			}else {
				JOptionPane.showMessageDialog(null,"el valor no coincide con ningun tipo busqueda");
				
			}
		}
	}
	
	
	public void addCarrito(JTable tablaProductos,JTable tablaCompras,JTextField jtextFieldDescuento,JTextField buscador,JLabel labelTotal,String nameTablaCompras) throws SQLException {
		//el parametro nameTablaCompras se va pasando de manera "escalonada" en los metodos:
		//obtenerSumatoriaSubtotalTablaTcompras y ctm.datosTablaTcompras
		ConexionTableModel ctm;

		ctm = new ConexionTableModel();
		
		int fila = tablaProductos.getSelectedRow();
		
		Metodos instancia = new Metodos();
		SqlOperaciones operacion=new SqlOperaciones();
		String Nproductos,cantidad,descripcion,precio,Sub_Total;
		int cantidadProductosIngresada;
		float total=0;
		
		double x;
		double calcula=0;
		
		java.sql.Connection conn=null;    
		ResultSet rs=null;
		conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
		stmnt=conn.createStatement();
		
		if (fila >= 0) {
			/*------------------------------------------------------------------------------------------*/
			String categoria=tablaProductos.getValueAt(fila, 6).toString();
			/*---------METODODO PARA AGREGAR AL CARRITO PRODUCTOS DE CATEGORIA 'UNIDADES' */
			if(categoria.equals("unidades")) {
				
				try {
					instancia.addCarritoUnidades(tablaProductos, tablaCompras, jtextFieldDescuento, buscador, labelTotal,nameTablaCompras);
				} catch (NumberFormatException | HeadlessException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
			/*--------- FIN DE METODODO PARA AGREGAR AL CARRITO PRODUCTOS DE CATEGORIA 'UNIDADES' */
			try {
			do{
				Nproductos=JOptionPane.showInputDialog("INGRESA LA CANTIDAD DE PRODUCTOS");
				
				/*----------------------------JAFETH8:CODIGO AGREGADO-------------------------------------------*/
				if(instancia.isNumeric(Nproductos)==false && !Nproductos.equals("")) {
					JOptionPane.showMessageDialog(null, "Igrese un numero!","error",JOptionPane.ERROR_MESSAGE);
					
				}
				/*------------------------FIN CODIGO AGREGADO-----------------------------------------------*/
			}while(Nproductos.equals("") || instancia.isNumeric(Nproductos)==false);//jafeth8:metodo agregado isNumeric	
			//cb-->cantidad de productos agregados
			cantidadProductosIngresada=Integer.parseInt(Nproductos);
			//model = (DefaultTableModel) JTResultado1.getModel();//probablemente no se ocupa
			/*---------------VALIACION PARA EL CAMPO DESCUENTO----------------------------*/
			if(instancia.isDouble(jtextFieldDescuento.getText())==false) {
				JOptionPane.showMessageDialog(null, "El valor del campo descuento debe ser un numero","Aviso!",JOptionPane.WARNING_MESSAGE);
				jtextFieldDescuento.setText("0.0");
				return;
			}
			/*------------FIN DE VALIDACION DE CAMPO DESCUENTO----------------------------*/
			double descuento=Double.parseDouble((jtextFieldDescuento.getText().toString()));
			cantidad=tablaProductos.getValueAt(fila, 2).toString();
			descripcion = tablaProductos.getValueAt(fila, 3).toString();
			precio=tablaProductos.getValueAt(fila, 4).toString();
			double costo=Double.parseDouble(tablaProductos.getValueAt(fila, 5).toString());
			
			int cantidadEnTabla = Integer.parseInt(cantidad);
			if (cantidadProductosIngresada<=0) {
				JOptionPane.showMessageDialog(null,"EL DATO ES INCORRECTO","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
			}else{
			if (cantidadProductosIngresada>cantidadEnTabla) {
				JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
			}
			else{
					
					if(instancia.validarExistenciaEnTablaTcompras(descripcion,nameTablaCompras) )
              		{
						
						for (int j = 0; j < tablaCompras.getRowCount(); j++) {
								String cantidadTablaCompras=tablaCompras.getValueAt(j, 1).toString();
								String descripcioncompras=tablaCompras.getValueAt(j, 2).toString();
								double descuentoTablaCompras=Double.parseDouble(tablaCompras.getValueAt(j, 5).toString());
								double precioTablaProductos = Double.parseDouble(precio.toString());//------------
              			 		double resultadoDescuento = precioTablaProductos-descuento;//----------
								if (descripcioncompras.equals(descripcion)) {
									/*---------------------VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
									if(descuento!=descuentoTablaCompras) {
										JOptionPane.showMessageDialog(null, "El producto: "+descripcioncompras+" ya tiene un descuento de: "+descuentoTablaCompras+
										" y usted esta reasignando un descuento de: "+descuento+" \n**el descuento debe ser el mismo para este producto**"+
										"\n'si quiere reasignar un descuento a este producto quitelo del carrito de compras y vuelva a gregarlo con el descuento deseado'","Aviso!",JOptionPane.WARNING_MESSAGE );
										jtextFieldDescuento.setText("0.0");
										return;
									}
									/*---------------------FIN VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
								    x=(Double.parseDouble(cantidadTablaCompras)+cantidadProductosIngresada);
								    double a=((Double.parseDouble(cantidadTablaCompras)+cantidadProductosIngresada)*resultadoDescuento);//simplificar linea
									if (cantidadEnTabla<x) {
										JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
										return;
									}else{
										stmnt.executeUpdate("UPDATE  `"+Ruta.database+"`.`"+nameTablaCompras+"` SET  `CANTIDAD` =  '"+x+"',`SUB_TOTAL` =  '"+a+"' WHERE DESCRIPCION='"+descripcion+"'");
										
										ctm.datosTablaTcompras("", tablaCompras,nameTablaCompras);
										//calcula = (resultadoDescuento*cantidadProductosIngresada);
										total=operacion.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
					
										buscador.setText("");
									}
								 }
								}
						jtextFieldDescuento.setText("0.0");
              		 }else{
              			 		int idProducto=Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());
              		
              			 		double precioTablaProductos = Double.parseDouble(precio.toString());//------------
              			 		double resultadoDescuento = precioTablaProductos-descuento;//----------
              			 		/*--------------------VALIDACIONES-----------------*/
              			 		if(descuento<0) {
              			 			JOptionPane.showMessageDialog(null, "El descuento no debe ser un numero negativo","Aviso!",JOptionPane.WARNING_MESSAGE);
              			 			jtextFieldDescuento.setText("0.0");
              			 			return;
              			 		}
              			 		if(descuento>precioTablaProductos) {
              			 			JOptionPane.showMessageDialog(null, "El descuento no debe ser mayor al precio del producto","Aviso!",JOptionPane.WARNING_MESSAGE);
              			 			jtextFieldDescuento.setText("0.0");
              			 			return;
              			 		}
              			 		double diferenciaPrecioDescuento=precioTablaProductos-descuento;
              			 		double descuentoPermitido=precioTablaProductos-costo;
								if(diferenciaPrecioDescuento<costo) {
									JOptionPane.showMessageDialog(null, "El producto: "+descripcion+" tiene un costo de : "+costo+
									" y el descuento resultante al precio del producto es de : "+diferenciaPrecioDescuento+" \nA este producto solo pude darle un descuento maximo de: "+descuentoPermitido+
									"\n'...'","Aviso!",JOptionPane.WARNING_MESSAGE );
									jtextFieldDescuento.setText("0.0");
									return;
								}
              			 		/*--------------------FIN DE VALIDACIONES-----------------*/
              		  			x=(cantidadProductosIngresada*resultadoDescuento);//-----------------
              		  			Sub_Total= String.valueOf(x);
              		  			String stringPrecioUnitario = String.valueOf(resultadoDescuento);
              		  			stmnt.executeUpdate("INSERT INTO `"+Ruta.database+"`.`"+nameTablaCompras+"` (`ID`,`CANTIDAD`, `DESCRIPCION`, `PRECIO_UNITARIO`, `SUB_TOTAL`,`DESCUENTO`) VALUES ('"+idProducto+"','"+cantidadProductosIngresada+"', '"+descripcion+"', '"+stringPrecioUnitario+"', '"+Sub_Total+"','"+jtextFieldDescuento.getText()+"');");
              		  		
              		  			ctm.datosTablaTcompras("", tablaCompras,nameTablaCompras);
              		  			
              		  			//calcula = (resultadoDescuento*cantidadProductosIngresada);
              		  		    total=operacion.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
	    					    buscador.setText("");
	    					    				    					    
	    					    jtextFieldDescuento.setText("0.0");
	    					    
				          }
			labelTotal.setText("$ "+total);
			System.out.println(total);
			}
			}
			
		} catch (Exception e3) {
			e3.printStackTrace();
		}
			/*------------------------------------------------------------------------------------------*/
			
		}else {
			JOptionPane.showMessageDialog(null,"no selecciono producto");
		}
		
	}
	public void addCarritoUnidades(JTable tablaProductos,JTable tablaCompras,JTextField jtextFieldDescuento,JTextField buscador,JLabel labelTotal,String nameTablaCompras) throws SQLException, NumberFormatException, HeadlessException, IOException {
		SqlOperaciones operacion=new SqlOperaciones();
		int opcion;
		opcion=JOptionPane.showConfirmDialog(null,"vendera por kilos o metros?","OPCION", JOptionPane.YES_NO_OPTION);
		if(opcion==1) {
			addCarritoUnidadesPorDinero(tablaProductos,tablaCompras,jtextFieldDescuento, buscador, labelTotal,nameTablaCompras);
			return;
		}
		//JOptionPane.showMessageDialog(null, "si fue por kilos XD");
		final int flsel=tablaProductos.getSelectedRow();
		ConexionTableModel ctm=new ConexionTableModel();
		String Nproductos;
		double xCantidadFinal;
		float cantidadProductosAgregados;
		float total=0;
		Metodos instancia = new Metodos();
		String Sub_Total;
		
		java.sql.Connection conn=null;    
		ResultSet rs=null;
		conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
		stmnt=conn.createStatement();
		
		do{
			Nproductos=JOptionPane.showInputDialog("INGRESA LA CANTIDAD");
			/*----------------------------JAFETH8:CODIGO AGREGADO-------------------------------------------*/
			if(instancia.isDouble(Nproductos)==false && !Nproductos.equals("")) {
				JOptionPane.showMessageDialog(null, "Igrese un numero valido!","error",JOptionPane.ERROR_MESSAGE);
			}
			/*------------------------FIN CODIGO AGREGADO-----------------------------------------------*/
		}while(Nproductos.equals("") || instancia.isDouble(Nproductos)==false);	
		//cb-->cantidad de productos agregados
		cantidadProductosAgregados=Float.parseFloat(Nproductos);
		
		/*---------------VALIACION PARA EL CAMPO DESCUENTO----------------------------*/
		if(instancia.isDouble(jtextFieldDescuento.getText())==false) {
			JOptionPane.showMessageDialog(null, "El valor del campo descuento debe ser un numero","Aviso!",JOptionPane.WARNING_MESSAGE);
			jtextFieldDescuento.setText("0.0");
			return;
		}
		/*------------FIN DE VALIDACION DE CAMPO DESCUENTO----------------------------*/
		
		double descuento=Double.parseDouble((jtextFieldDescuento.getText().toString()));
		String cantidad=tablaProductos.getValueAt(flsel, 2).toString();
		String descripcion =tablaProductos.getValueAt(flsel, 3).toString();
		String precio=tablaProductos.getValueAt(flsel, 4).toString();
		double costo=Double.parseDouble(tablaProductos.getValueAt(flsel,5).toString());
		
		float tablaEnTabla = Float.parseFloat(cantidad);
		if (cantidadProductosAgregados<=0) {
			JOptionPane.showMessageDialog(null,"EL DATO ES INCORRECTO","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
		}else{
		if (cantidadProductosAgregados>tablaEnTabla) {
			JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
		}
		else{
				if(validarExistenciaEnTablaTcompras(descripcion,nameTablaCompras) )
	      		{
					for (int j = 0; j < tablaCompras.getRowCount(); j++) {
							String cantidadTablaCompras=tablaCompras.getValueAt(j, 1).toString();
							String descripcioncompras=tablaCompras.getValueAt(j, 2).toString();
							double descuentoTablaCompras=Double.parseDouble(tablaCompras.getValueAt(j, 5).toString());
							double precioTablaProductos = Double.parseDouble(precio.toString());//------------
	      			 		double resultadoDescuento = precioTablaProductos-descuento;//----------
							if (descripcioncompras.equals(descripcion)) {
								/*---------------------VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
								if(descuento!=descuentoTablaCompras) {
									JOptionPane.showMessageDialog(null, "El producto: "+descripcioncompras+" ya tiene un descuento de: "+descuentoTablaCompras+
									" y usted esta reasignando un descuento de: "+descuento+" \n**el descuento debe ser el mismo para este producto**"+
									"\n'si quiere reasignar un descuento a este producto quitelo del carrito de compras y vuelva a gregarlo con el descuento deseado'","Aviso!",JOptionPane.WARNING_MESSAGE );
									jtextFieldDescuento.setText("0.0");
									return;
								}
								/*---------------------FIN VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
							    xCantidadFinal=(Double.parseDouble(cantidadTablaCompras)+cantidadProductosAgregados);
							    //double a=((Double.parseDouble(cantidadTablaCompras)+cantidadProductosAgregados)*resultadoDescuento);
							    double aSubTotal=((xCantidadFinal)*resultadoDescuento);
								if (tablaEnTabla<xCantidadFinal) {
									JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
									return;
								}else{
									stmnt.executeUpdate("UPDATE  `"+Ruta.database+"`.`"+nameTablaCompras+"` SET  `CANTIDAD` =  '"+xCantidadFinal+"',`SUB_TOTAL` =  '"+aSubTotal+"' WHERE DESCRIPCION='"+descripcion+"'");

									ctm.datosTablaTcompras("",tablaCompras,nameTablaCompras);
									//calcula = (resultadoDescuento*cantidadProductosAgregados);
									//totalAcumulador=(float) (totalAcumulador+calcula);
									total= (float) operacion.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
									buscador.setText("");
								}
							}
							}
					jtextFieldDescuento.setText("0.0");
	      		 }else{
	      			 		
	      			        int idProducto=Integer.parseInt(tablaProductos.getValueAt(flsel, 0).toString());
	      			 		Double xSubtotal;
	      			 		
	      			 		//System.out.println("el id del producto es: "+idProducto);
	      			 		double precioTablaProductos = Double.parseDouble(precio.toString());//------------
	      			 		double resultadoDescuento = precioTablaProductos-descuento;//----------
	      			 		/*--------------------VALIDACIONES-----------------*/
	      			 		if(descuento<0) {
	      			 			JOptionPane.showMessageDialog(null, "El descuento no debe ser un numero negativo","Aviso!",JOptionPane.WARNING_MESSAGE);
	      			 			jtextFieldDescuento.setText("0.0");
	      			 			return;
	      			 		}
	      			 		if(descuento>precioTablaProductos) {
	      			 			JOptionPane.showMessageDialog(null, "El descuento no debe ser mayor al precio del producto","Aviso!",JOptionPane.WARNING_MESSAGE);
	      			 			jtextFieldDescuento.setText("0.0");
	      			 			return;
	      			 		}
	      			 		
          			 		double diferenciaPrecioDescuento=precioTablaProductos-descuento;
          			 		double descuentoPermitido=precioTablaProductos-costo;
							if(diferenciaPrecioDescuento<costo) {
								JOptionPane.showMessageDialog(null, "El producto: "+descripcion+" tiene un costo de : "+costo+
								" y el descuento resultante al precio del producto es de : "+diferenciaPrecioDescuento+" \nA este producto solo pude darle un descuento maximo de: "+descuentoPermitido+
								"\n'...'","Aviso!",JOptionPane.WARNING_MESSAGE );
								jtextFieldDescuento.setText("0.0");
								return;
							}
	      			 		/*--------------------FIN DE VALIDACIONES-----------------*/
	      		  			xSubtotal=(cantidadProductosAgregados*resultadoDescuento);//-----------------
	      		  			Sub_Total= String.valueOf(xSubtotal);
	      		  			String stringPrecioUnitario = String.valueOf(resultadoDescuento);
	      		  			stmnt.executeUpdate("INSERT INTO `"+Ruta.database+"`.`"+nameTablaCompras+"` (`ID`,`CANTIDAD`, `DESCRIPCION`, `PRECIO_UNITARIO`, `SUB_TOTAL`,`DESCUENTO`) VALUES ('"+idProducto+"','"+cantidadProductosAgregados+"', '"+descripcion+"', '"+stringPrecioUnitario+"', '"+Sub_Total+"',"+jtextFieldDescuento.getText()+");");
	      		  			
	      		  			ctm.datosTablaTcompras("", tablaCompras,nameTablaCompras);
	      		  			//calcula = (resultadoDescuento*cantidadProductosAgregados);
	      		  		    //totalAcumulador=(float) (totalAcumulador+calcula);
	      		  			total=(float) operacion.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
	      		  		    buscador.setText("");
						    
						    jtextFieldDescuento.setText("0.0");
						    
			          }
		labelTotal.setText("$ "+total);
		
		}
		}
	}
	
	public void addCarritoUnidadesPorDinero(JTable tablaProductos,JTable tablaCompras, JTextField textFieldDescuento,JTextField buscador, JLabel labelTotal,String nameTablaCompras) throws SQLException, NumberFormatException, HeadlessException, IOException {
		
		SqlOperaciones operacion = new SqlOperaciones(); 
		final int flsel=tablaProductos.getSelectedRow();
		ConexionTableModel ctm=new ConexionTableModel();
		String dinero;
		double xCantidadFinal,xSubtotal;
		float total=0;
		String Sub_Total;
		
	  //float cantidadProductosAgregados
		float cantidadProductos;
		Metodos instancia = new Metodos();
		
		
		java.sql.Connection conn=null;    
		ResultSet rs=null;
		conn=DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
		stmnt=conn.createStatement();
		
		do{
			//Nproductos=JOptionPane.showInputDialog("INGRESA LA CANTIDAD DE DINERO");
			  dinero=JOptionPane.showInputDialog("INGRESA LA CANTIDAD DE DINERO");
			/*----------------------------JAFETH8:CODIGO AGREGADO-------------------------------------------*/
			if(isDouble(dinero)==false && !dinero.equals("")) {
				JOptionPane.showMessageDialog(null, "Ingrese un numero valido!","error",JOptionPane.ERROR_MESSAGE);
				
			}
			/*------------------------FIN CODIGO AGREGADO---------------------------------------------------*/
		}while(dinero.equals("") || isDouble(dinero)==false);//jafeth8:metodo agregado isNumeric	
		//cb-->cantidad de productos agregados
		
		/*---------------VALIACION PARA EL CAMPO DESCUENTO----------------------------*/
		if(isDouble(textFieldDescuento.getText())==false) {
			JOptionPane.showMessageDialog(null, "El valor del campo descuento debe ser un numero","Aviso!",JOptionPane.WARNING_MESSAGE);
			textFieldDescuento.setText("0.0");
			return;
		}
		/*------------FIN DE VALIDACION DE CAMPO DESCUENTO----------------------------*/
		float dineroFloat=Float.parseFloat(dinero);

		double descuento=Double.parseDouble((textFieldDescuento.getText().toString()));
		String cantidad=tablaProductos.getValueAt(flsel, 2).toString();
		String descripcion =tablaProductos.getValueAt(flsel, 3).toString();
		String precio=tablaProductos.getValueAt(flsel, 4).toString();
		double costo=Double.parseDouble(tablaProductos.getValueAt(flsel,5).toString());
		/*convertimos la catidad de dinero a cantidad de producto indempendientemente de la unidad de medida*/
		float precioDouble=Float.parseFloat(precio);
		cantidadProductos=dineroFloat/precioDouble;
		/*----FIN DE BLOQUE para convertir la catidad de dinero a cantidad de producto--------------------------*/
		float cantidadEnTabla = Float.parseFloat(cantidad);
		if (cantidadProductos<=0){
			JOptionPane.showMessageDialog(null,"EL DATO ES INCORRECTO","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
		}else{
		if (cantidadProductos>cantidadEnTabla){
			JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
		}
		else{
				if(validarExistenciaEnTablaTcompras(descripcion,nameTablaCompras) )
          		{ 
					for (int j = 0; j < tablaCompras.getRowCount(); j++) {
							String cantidadTablaCompras=tablaCompras.getValueAt(j, 1).toString();
							String descripcioncompras=tablaCompras.getValueAt(j, 2).toString();
							double descuentoTablaCompras=Double.parseDouble(tablaCompras.getValueAt(j, 5).toString());
							double precioTablaProductos = Double.parseDouble(precio.toString());//------------
          			 		double resultadoDescuento = precioTablaProductos-descuento;//----------
							if (descripcioncompras.equals(descripcion)) {
								
								/*---------------------VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
								if(descuento!=descuentoTablaCompras) {
									JOptionPane.showMessageDialog(null, "El producto: "+descripcioncompras+" ya tiene un descuento de: "+descuentoTablaCompras+
									" y usted esta reasignando un descuento de: "+descuento+" \n**el descuento debe ser el mismo para este producto**"+
									"\n'si quiere reasignar un descuento a este producto quitelo del carrito de compras y vuelva a gregarlo con el descuento deseado'","Aviso!",JOptionPane.WARNING_MESSAGE );
									textFieldDescuento.setText("0.0");
									return;
								}
								/*---------------------FIN VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
								
							    xCantidadFinal=(Double.parseDouble(cantidadTablaCompras)+cantidadProductos);
							    double a=((Double.parseDouble(cantidadTablaCompras)+cantidadProductos)*resultadoDescuento);
								if (cantidadEnTabla<xCantidadFinal) {
									JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
									return;
								}else{
									stmnt.executeUpdate("UPDATE  `"+Ruta.database+"`.`"+nameTablaCompras+"` SET  `CANTIDAD` =  '"+xCantidadFinal+"',`SUB_TOTAL` =  '"+a+"' WHERE DESCRIPCION='"+descripcion+"'");

									ctm.datosTablaTcompras("",tablaCompras,nameTablaCompras);
									//calcula = (resultadoDescuento*cantidadProductos);
									//total=(float) (PuntoDeVenta.total+PuntoDeVenta.calcula);
									total=(float) operacion.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
									buscador.setText("");
								}
							}
							}
					textFieldDescuento.setText("0.0");
          		 }else{
          			 		int idProducto=Integer.parseInt(tablaProductos.getValueAt(flsel, 0).toString());
          			 		
          			 		double precioTablaProductos = Double.parseDouble(precio.toString());//------------
          			 		double resultadoDescuento = precioTablaProductos-descuento;//----------
          			 		/*--------------------VALIDACIONES-----------------*/
          			 		if(descuento<0) {
          			 			JOptionPane.showMessageDialog(null, "El descuento no debe ser un numero negativo","Aviso!",JOptionPane.WARNING_MESSAGE);
          			 			textFieldDescuento.setText("0.0");
          			 			return;
          			 		}
          			 		if(descuento>precioTablaProductos) {
          			 			JOptionPane.showMessageDialog(null, "El descuento no debe ser mayor al precio del producto","Aviso!",JOptionPane.WARNING_MESSAGE);
          			 			textFieldDescuento.setText("0.0");
          			 			return;
          			 		}
          			 		
          			 		double diferenciaPrecioDescuento=precioTablaProductos-descuento;
          			 		double descuentoPermitido=precioTablaProductos-costo;
							if(diferenciaPrecioDescuento<costo) {
								JOptionPane.showMessageDialog(null, "El producto: "+descripcion+" tiene un costo de : "+costo+
								" y el descuento resultante al precio del producto es de : "+diferenciaPrecioDescuento+" \nA este producto solo pude darle un descuento maximo de: "+descuentoPermitido+
								"\n'...'","Aviso!",JOptionPane.WARNING_MESSAGE );
								textFieldDescuento.setText("0.0");
								return;
							}
          			 		/*--------------------FIN DE VALIDACIONES-----------------*/
          		  			xSubtotal=(cantidadProductos*resultadoDescuento);//-----------------
          		  			Sub_Total= String.valueOf(xSubtotal);
          		  			String stringde = String.valueOf(resultadoDescuento);
          		  			stmnt.executeUpdate("INSERT INTO `"+Ruta.database+"`.`"+nameTablaCompras+"` (`ID`,`CANTIDAD`, `DESCRIPCION`, `PRECIO_UNITARIO`, `SUB_TOTAL`,`DESCUENTO`) VALUES ('"+idProducto+"','"+cantidadProductos+"', '"+descripcion+"', '"+stringde+"', '"+Sub_Total+"','"+textFieldDescuento.getText()+"');");
          		  			
          		  			ctm.datosTablaTcompras("", tablaCompras,nameTablaCompras);;
          		  			//calcula = (resultadoDescuento*cantidadProductos);
          		  		    //total=(float) (PuntoDeVenta.total+PuntoDeVenta.calcula);
          		  			total=(float) operacion.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
    					    buscador.setText("");
    					    
    					    textFieldDescuento.setText("0.0");
    					    
			          }
		labelTotal.setText("$ "+total);
		}
		}
	}
	
	
	public boolean cantidadProductoValido(String entrada) {
		boolean numero=true;
		if(isDouble(entrada)) {
			numero=true;
		}else {
			JOptionPane.showMessageDialog(null,"la entrada no es valida en la cantidad del producto");
			numero=false;
		}
		return numero;
	}

	
}
