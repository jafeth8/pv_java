package Punto_de_venta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class SqlOperaciones {
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
    
    
   public void truncarTablaTcompras(String querySql) {
	    try {
	        PreparedStatement pst = cn.prepareStatement(querySql);
	        pst.executeUpdate();
	    } catch (Exception e) {
	    }
	}
    
    public double obtenerCostoProductoTablaProducto(int idProducto) {
   	 
    	String sql="SELECT COSTO_UNITARIO FROM productos WHERE ID = '"+idProducto+"'";
		double costo=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                costo=rs.getInt(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return costo;
    }
    
    public String obtenerCategoriaTablaProducto(int idProducto) {   //ESTE METODO NO SE OCUPO XD
      	 
    	String sql="SELECT CATEGORIA FROM productos WHERE ID = '"+idProducto+"'";
		String categoria="";    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                categoria=rs.getString(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return categoria;
    }
    
    public void insertVentas(double total_venta,String fechaVenta,double pagoCliente,double cambioCliente,String tipoVenta) {
    	try {
    		PreparedStatement pst = cn.prepareStatement("INSERT INTO ventas"
    				+ "(total_venta,fecha_venta,pago_del_cliente,cambio_del_cliente,tipo_venta) VALUES (?,?,?,?,?)");
    		        pst.setDouble(1, total_venta);
    		        pst.setString(2, fechaVenta);
    		        pst.setDouble(3, pagoCliente);
    		        pst.setDouble(4, cambioCliente);
    		        pst.setString(5, tipoVenta);
    		        pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		}finally {
			//Aqui no se cierra la conexion para permitir mas operaciones
		}
    }
    
    public void insertDetalleVentas(int fkIdVenta,int fkIdProducto,double costoUnitario,double precioUnitario,double cantidad,double descuento) {
    	try {
    		PreparedStatement pst = cn.prepareStatement("INSERT INTO detalle_ventas"
    				+ "(fk_id_venta,fk_id_producto,costo_unitario,precio_unitario,cantidad,descuento) VALUES (?,?,?,?,?,?)");
    		        pst.setInt(1, fkIdVenta);
    		        pst.setInt(2, fkIdProducto);
    		        pst.setDouble(3, costoUnitario);
    		        pst.setDouble(4, precioUnitario);
    		        pst.setDouble(5, cantidad);
    		        pst.setDouble(6, descuento);
    		        pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		}finally {
			//Aqui no se cierra la conexion para permitir mas operaciones
		}
    }
    
    public int obtenerIdTablaVentas() {
    	String sql="SELECT MAX(id_venta) FROM ventas";
		int id=-1;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                id=rs.getInt(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return id;
    }
    /*mofificado*/
    public void insertApartados(int idCliente,double total,String fecha_apartado,double deudaInicial,String estado) {
    	try {
    		PreparedStatement pst = cn.prepareStatement("INSERT INTO apartados"
    				+ "(fk_id_cliente,total,fecha_de_apartado,deuda,estado) VALUES (?,?,?,?,?)");
    		        pst.setInt(1, idCliente);
    		        pst.setDouble(2, total);
    		        pst.setString(3, fecha_apartado);
    		        pst.setDouble(4, deudaInicial);
    		        pst.setString(5, estado);
    		        pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		}finally {
			//Aqui no se cierra la conexion para permitir mas operaciones
		}
    }
    /*agregado*/
    public int obtenerIdTablaApartado() {
    	String sql="SELECT MAX(id_apartado) FROM apartados";
		int id=-1;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                id=rs.getInt(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return id;
    }
    /*agregado*/
    public void insertDetalleApartados(int fkIdApartado,int fkIdProducto,double costoUnitario,double precioUnitario,double cantidad,String fechaRegistro,double descuento) {
    	try {
    		PreparedStatement pst = cn.prepareStatement("INSERT INTO detalle_apartados"
    				+ "(fk_id_apartado,fk_id_producto,costo_unitario,precio_unitario,cantidad,fecha_registro,descuento) VALUES (?,?,?,?,?,?,?)");
    		        pst.setInt(1, fkIdApartado);
    		        pst.setInt(2, fkIdProducto);
    		        pst.setDouble(3, costoUnitario);
    		        pst.setDouble(4, precioUnitario);
    		        pst.setDouble(5, cantidad);
    		        pst.setString(6, fechaRegistro);
    		        pst.setDouble(7, descuento);
    		        pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		}finally {
			//Aqui no se cierra la conexion para permitir mas operaciones
		}
    }
    
    public void actualizarCantidadProductos(int idProducto,double CantidadTablaProductos,double  cantidadDeApartados) {
    	double cantidadRestante=CantidadTablaProductos-cantidadDeApartados;
    	PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE productos SET cantidad='"+cantidadRestante+"' WHERE ID='"+idProducto+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public double obtenerCantidadTablaProducto(int idProducto) {  
		    	 
    	String sql="SELECT CANTIDAD FROM productos WHERE ID = '"+idProducto+"'";
		double cantidad=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                cantidad=rs.getDouble(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return cantidad;
    }
    
    public String obtenerCantidadProductoDescripcion(String descripcion) {  
   	 
    	String sql="SELECT CANTIDAD,CATEGORIA FROM productos WHERE DESCRIPCION = '"+descripcion+"'";
		String cantidad="";    	 
		String categoria="";    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
            	if(rs.getString(2).equals("unidades")) {
            		cantidad=""+rs.getDouble(1);
            	}else {
            		cantidad=""+rs.getInt(1);
            	}
                
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return cantidad;
    }
    
    public void actualizarStateTablaProductos(int id_producto,int idState) {
		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE productos SET fk_id_state='"+idState+"' WHERE id='"+id_producto+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	public double obtenerPrecioTotalTablaApartados(int id_apartado) {
		String sql="SELECT total FROM apartados WHERE id_apartado = '"+id_apartado+"'";
		double total = 0;
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				total = rs.getDouble(1);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}

		return total;

	}
	
	public double obtenerDeudaTablaApartados(int id_apartado) {
		String sql="SELECT deuda FROM apartados WHERE id_apartado = '"+id_apartado+"'";
		double total = 0;
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				total = rs.getDouble(1);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}

		return total;

	}
	
	public double obtenerTotalAbonoTablaApartados(int id_apartado) {
		String sql="SELECT total_abono FROM apartados WHERE id_apartado = '"+id_apartado+"'";
		double total = 0;
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				total = rs.getDouble(1);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}

		return total;

	}
	
	public void actualizarTotalAbonoTablaApartados(int id_apartatado,double updateAbonoTotal) {
		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE apartados SET total_abono='"+updateAbonoTotal+"' WHERE id_apartado='"+id_apartatado+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actualizarDeudaTablaApartados(int id_apartatado,double updateDeuda) {
		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE apartados SET deuda='"+updateDeuda+"' WHERE id_apartado='"+id_apartatado+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void actualizarEstadoTablaApartados(int id_apartado,String pagado) {
		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE apartados SET estado='"+pagado+"' WHERE id_apartado='"+id_apartado+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actualizarFechaLiquidacionTablaApartados(int id_apartado,String fecha){
		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE apartados SET fecha_liquidacion='"+fecha+"' WHERE id_apartado='"+id_apartado+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertTablaRegistroApartados(int fk_id_apartado,double dineroRecibido,double abono,double cambioEntregado, String fechaAbono) {
		try {
    		PreparedStatement pst = cn.prepareStatement("INSERT INTO registro_apartados"
    				+ "(fk_id_apartado,dinero_recibido,abono,cambio_entregado,fecha_abono) VALUES (?,?,?,?,?)");
    		        pst.setInt(1, fk_id_apartado);
    		        pst.setDouble(2, dineroRecibido);
    		        pst.setDouble(3, abono);
    		        pst.setDouble(4, cambioEntregado);
    		        pst.setString(5, fechaAbono);
    		        pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		}finally {
			
		}
	}

	public String[][] obtenerDatosDetalleApartados(int idApartado) {
	
	    String sql;
	    int cantidadRegistros=cantidadRegistros(idApartado);
	    String resultado[][]=new String[cantidadRegistros][5];
	    sql="SELECT fk_id_producto,costo_unitario,precio_unitario,cantidad,descuento FROM detalle_apartados WHERE fk_id_apartado="+idApartado+"";
	   
	        int contadorRegistro=0;
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	              
	                resultado[contadorRegistro][0]=rs.getString(1);
	                resultado[contadorRegistro][1]=rs.getString(2);
	                resultado[contadorRegistro][2]=rs.getString(3);
	                resultado[contadorRegistro][3]=rs.getString(4);
	                resultado[contadorRegistro][4]=rs.getString(5);
	                contadorRegistro++;	            
	           }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return resultado;
		
		
	}
	
	public int cantidadRegistros (int idApartado) {
		
		//SELECT COUNT(fk_id_apartado) FROM detalle_apartados WHERE fk_id_apartado=24
		String sql="SELECT COUNT(fk_id_apartado) FROM detalle_apartados WHERE fk_id_apartado= '"+idApartado+"'";
		int cantidad=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                cantidad=rs.getInt(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return cantidad;
	}
	public int obtenerIdTipoClienteTablaClientes(int idCliente) {
		String sql="SELECT fk_id_tipo_cliente FROM clientes WHERE id_cliente = '"+idCliente+"'";
		int idTipoCliente=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                idTipoCliente=rs.getInt(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		} 
        return idTipoCliente;
	}
	
	public void actualizarIdClienteTablaVentas(int idVenta,int idCliente) {
		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE ventas SET fk_id_cliente='"+idCliente+"' WHERE id_venta='"+idVenta+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actualizarComisionTablaVentas(int idVenta,double comision) {
		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE ventas SET comision='"+comision+"' WHERE id_venta='"+idVenta+"'");
			pst.executeUpdate();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public Double obtenerGanaciasSocios(String fecha) {
		
		//SELECT valor FROM cporcentaje_comision
		String sql="SELECT SUM((precio_unitario)*cantidad)-SUM((costo_unitario)*cantidad) as ganancias FROM "
				+ "detalle_ventas JOIN ventas ON detalle_ventas.fk_id_venta=ventas.id_venta JOIN clientes ON ventas.fk_id_cliente=clientes.id_cliente WHERE clientes.fk_id_tipo_cliente=2 GROUP BY MONTH("+fecha+"),YEAR("+fecha+")";
				
		Double ganancias=0.0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                ganancias=rs.getDouble(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		} 
        return ganancias;
	}
	*/
	public int obtenerPorcentajeComision() {

		// SELECT valor FROM cporcentaje_comision
		String sql = "SELECT valor FROM cporcentaje_comision";

		int valor =1;

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				valor = rs.getInt(1);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}
		return valor;
	}
	
	public  void ActualizarPorcentajeComision(int porcentaje) {
		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE cporcentaje_comision SET valor='"+porcentaje+"' WHERE id_porcentaje='1'");
			pst.executeUpdate();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public double obtenerTotalVentaTablaVentas(int idVenta) {

		// SELECT valor FROM cporcentaje_comision
		String sql = "SELECT total_venta FROM ventas where id_venta="+idVenta+"";

		double totalVentas =0;

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				totalVentas = rs.getDouble(1);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}
		return totalVentas;
	}
	
	public String obtenerCategoriaProducto(int idProducto) {

		// SELECT valor FROM cporcentaje_comision
		String sql = "SELECT CATEGORIA FROM productos where ID="+idProducto+"";

		String categoria ="";

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				categoria = rs.getString(1);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}
		return categoria;
	}
	
	public void insertTablaInsumos(String descripcion,double costo, String fecha) {
		try {
    		PreparedStatement pst = cn.prepareStatement("INSERT INTO insumos"
    				+ "(descripcion,costo,fecha) VALUES (?,?,?)");
    		        pst.setString(1,descripcion);
    		        pst.setDouble(2,costo);
    		        pst.setString(3,fecha);
    		        pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		}finally {
			
		}
	}
	
	public boolean busquedaDescripcion(String valor) {

		String sql = "SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%' AND fk_id_state=1";

		String categoria ="";

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if( rs.first() )        
                return true;        
            else
                return false; 

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}
		return false;
				
	}
	
	public boolean busquedaCategoria(String valor) {
		
		String sql = "SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE categoria like '%"+valor+"%' AND fk_id_state=1";
		String sql2="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO from productos where CODIGO_BARRA='"+valor+"' AND fk_id_state=1";

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if( rs.first() )        
                return true;        
            else
                return false; 

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}
		return false;
				
	}
	
	public boolean busquedaPorCodigoBarra(String valor) {
		String sql = "SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO from productos where CODIGO_BARRA='"+valor+"' AND fk_id_state=1";

		String categoria ="";

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if( rs.first() )        
                return true;        
            else
                return false; 

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}
		return false;
				
	}
	
	
	public double obtenerPrecioProducto(int idProducto) {

		// SELECT valor FROM cporcentaje_comision
		String sql = "SELECT PRECIO_UNITARIO FROM productos where ID="+idProducto+"";

		double precio =0;

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				precio = rs.getDouble(1);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}
		return precio;
	}
	
	public void insertTablaCategoriaPrecios(int fkIdProducto,String descripcion,double precio) {
		try {
    		PreparedStatement pst = cn.prepareStatement("INSERT INTO categoria_precios_productos"
    				+ "(fk_id_producto,descripcion,precio) VALUES (?,?,?)");
    		        pst.setInt(1,fkIdProducto);
    		        pst.setString(2,descripcion);
    		        pst.setDouble(3,precio);
    		        pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		}finally {
			
		}
	}
	
	public void actualizarTablaCategoriaPrecios(int idRegistro,String descripcion,double precio) {
    	
    	PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE categoria_precios_productos SET descripcion='"+descripcion+"', precio='"+precio+"' WHERE id='"+idRegistro+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	
	public void actualizarDatosClientes(String id, String nombre,String segundoNombre,String apellidos,String direccion,String numeroTelefono) {

		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE clientes SET nombre='" + nombre
					+ "', segundo_nombre='" + segundoNombre + "' , apellidos='"+apellidos+"' , direccion='"+direccion+"'"
							+ " , numero_telefono='"+numeroTelefono+"' WHERE id_cliente='" + id + "'");
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "datos actualizados :)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	 public void actualizarCantidadProductosApartados(int idProducto,double cantidadTablaProductos,double  cantidadDeProductosApartados) {
	    	double cantidadActualizada=cantidadTablaProductos+cantidadDeProductosApartados;
	    	PreparedStatement pst;
			try {
				pst = cn.prepareStatement("UPDATE productos SET cantidad='"+cantidadActualizada+"' WHERE ID='"+idProducto+"'");
				pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	 
	 public void eliminarRegistroApartado(int idApartado) {
		    try {
		        PreparedStatement pst = cn.prepareStatement("DELETE FROM apartados WHERE  id_apartado='"+idApartado+"'");
		        pst.executeUpdate();
		    } catch (Exception e) {
		    }
		}
	 
	    public void actualizarStateTablaClientes(int idCliente,int idState) {
			PreparedStatement pst;
			try {
				pst = cn.prepareStatement("UPDATE clientes SET fk_id_state='"+idState+"' WHERE id_cliente='"+idCliente+"'");
				pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	boolean existenciaEnComprasPorDescripcion(String des,String nameTablaCompras) {
		try {
			java.sql.Connection conn = null;
			Statement stmnt = null;
			ResultSet rs = null;

			conn = (Connection) DriverManager.getConnection(Ruta.URL, Ruta.Usuario, Ruta.Contrasenia);
			stmnt = conn.createStatement();
			ResultSet resultadosConsulta = stmnt.executeQuery("SELECT * FROM "+nameTablaCompras+" WHERE DESCRIPCION='" + des + "'");
			if (resultadosConsulta.first())
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	 public double obtenerTotalTablaApartados(int idApartado) {   
	   	 
	    	String sql="SELECT total FROM apartados WHERE id_apartado = '"+idApartado+"'";
			double costo=0;    	 
			    
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	            	
	                costo=rs.getInt(1);
	              
	            }
	           
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }finally {
	        	//Aqui no se cierra la conexion para permitir mas operaciones
			}
	        
	        return costo;
	    }
	
	
	public void aumentarTotal_y_DeudaTablaApartados(int idApartado,double totalOriginal,double deudaOriginal,double totalAgregado) {
    	double resultadoTotal=totalOriginal+totalAgregado;
    	double resultadoDeuda=deudaOriginal+totalAgregado;
    	PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE apartados SET total='"+resultadoTotal+"',deuda='"+resultadoDeuda+"' WHERE id_apartado='"+idApartado+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	
	public void disminuirTotal_y_DeudaTablaApartados(int idApartado,double totalOriginal,double deudaOriginal,double totalPrecioUnitario) {
    	double resultadoTotal=totalOriginal-totalPrecioUnitario;
    	double resultadoDeuda=deudaOriginal-totalPrecioUnitario;
    	PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE apartados SET total='"+resultadoTotal+"',deuda='"+resultadoDeuda+"' WHERE id_apartado='"+idApartado+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
	public void eliminarRegistroDetalleApartado(int id_DetalleApartado) {
	    try {
	        PreparedStatement pst = cn.prepareStatement("DELETE FROM detalle_apartados WHERE  id_detalle_apartados='"+id_DetalleApartado+"'");
	        pst.executeUpdate();
	    } catch (Exception e) {
	    }
	}
	
	public void eliminarRegistroTablaTcompras(String descripcion,String nameTablaCompras) {
	    try {
	        PreparedStatement pst = cn.prepareStatement("DELETE FROM "+nameTablaCompras+" WHERE  DESCRIPCION='"+descripcion+"'");
	        pst.executeUpdate();
	    } catch (Exception e) {
	    }
	}
	
	public double obtenerGanaciasPorDia(String fecha) {
	   	 
		//String sql="SELECT SUM(total_venta) FROM `ventas`WHERE fecha_venta='2021-01-22'"
    	//String sql="SELECT SUM(total_venta) FROM `ventas` WHERE fecha_venta='"+fecha+"'";
    	String sql="SELECT SUM(detalle_ventas.precio_unitario*detalle_ventas.cantidad)-SUM(detalle_ventas.costo_unitario*detalle_ventas.cantidad) FROM "
    			+ "ventas JOIN detalle_ventas ON id_venta=detalle_ventas.fk_id_venta WHERE ventas.fecha_venta='"+fecha+"'";
		double ganancia=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                ganancia=rs.getDouble(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return ganancia;
    }
	
	
    public float obtenerSumatoriaSubtotalTablaTcompras(String nameTablaCompras) {
      	 
    	String sql="SELECT SUM(SUB_TOTAL) FROM "+nameTablaCompras+"";
		float sumatoria=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                sumatoria=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
		}
        
        return sumatoria;
    }
	
    public void actualizarInsumos(int idInsumo,String descripcion,double costo,String fecha) {
    	
    	PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE insumos SET descripcion='"+descripcion+"', costo='"+costo+"', fecha='"+fecha+"' WHERE id_insumo='"+idInsumo+"'");
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

	public void eliminarRegistroInsumos(int idInsumo) {
		try {
			PreparedStatement pst = cn.prepareStatement("DELETE FROM insumos WHERE  id_insumo='" + idInsumo + "'");
			pst.executeUpdate();
		} catch (Exception e) {
		}
	}

	boolean registro_ruta_respaldo() {
		try {
			String sql = "SELECT * FROM ruta_respaldo";
			Statement st = cn.createStatement();
			ResultSet resultadosConsulta = st.executeQuery(sql);
			if (resultadosConsulta.next())
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public void registrarRutaRespaldo(String ruta) {
		try {
			PreparedStatement pst = cn.prepareStatement("INSERT INTO ruta_respaldo" + "(ruta) VALUES (?)");
			pst.setString(1, ruta);
			pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		} finally {

		}
	}

	public String obtenerRutaRespaldo() {

		// SELECT valor FROM cporcentaje_comision
		String sql = "SELECT ruta FROM ruta_respaldo where id='1'";

		String categoria = "";

		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				categoria = rs.getString(1);

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			// Aqui no se cierra la conexion para permitir mas operaciones
		}
		return categoria;
	}

	public void actualizar_RutaRespaldo(String ruta) {

		PreparedStatement pst;
		try {
			pst = cn.prepareStatement("UPDATE ruta_respaldo SET ruta=? WHERE id='1'");
			pst.setString(1, ruta);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
