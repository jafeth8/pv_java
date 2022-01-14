package Punto_de_venta;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*------jafeth8-------------*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
/*----------jafeth8---------*/
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;



public class ConexionTableModel {
	
	
	
	/*----------------------------------jafeth8----------------------------------------------*/
	public ConexionTableModel(){
		
	}
	
	
	Conectar cc= new Conectar();
    Connection cn= cc.conexion();
	  public void mostrardatosProductos(String valor,JTable tablaProductos) throws SQLException{
		    DefaultTableModel modelo= new DefaultTableModel() {
		    	@Override
		    	public boolean isCellEditable(int row, int column) {
		    		// TODO Auto-generated method stub
		    		return false;
		    	}
		    };
		    modelo.addColumn("ID");
		    modelo.addColumn("CODIGO_BARRA");
		    modelo.addColumn("CANTIDAD");
		    modelo.addColumn("DESCRIPCION");
		    modelo.addColumn("PRECIO UNITARIO");
		    modelo.addColumn("COSTO PRODUCTO");
		    modelo.addColumn("CATEGORIA");
		   
		    tablaProductos.setModel(modelo);
		    /*-----------------------------tamaño de columnas*------------------------------*/
		    establecerTamanioJTable();
		    /*----------------------------fin de tamaño para columnas----------------------*/
		    String sql="";
		    if(valor.equals(""))
		    {
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
		    	sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE fk_id_state=1";
		    }
		    else{
		    	sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE descripcion like '%"+valor+"%' AND fk_id_state=1 ";
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%'";
		    }
		 
		    Object []datos = new Object [7];
		        try {
		            Statement st = cn.createStatement();
		            ResultSet rs = st.executeQuery(sql);
		            while(rs.next()){
		            	
		            	datos[0]=rs.getInt(1);
		                datos[1]=rs.getString(2);
		               
		                datos[3]=rs.getString(4);
		                datos[4]=rs.getDouble(5);
		                datos[5]=rs.getDouble(6);
		                datos[6]=rs.getString(7);
		                if(datos[6].equals("unidades")) {
		                	datos[2]=rs.getDouble(3);
		                }else {
		                	datos[2]=rs.getInt(3);
		                }
		                
		                
		                modelo.addRow(datos);
		            }
		            tablaProductos.setModel(modelo);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }finally {
		        	
					
					
				}
		    
		    }
	  
	  
	  public void mostrardatosCategorias(String valor,JTable tablaProductos) throws SQLException{
	    DefaultTableModel modelo= new DefaultTableModel() {
	    	@Override
	    	public boolean isCellEditable(int row, int column) {
	    		// TODO Auto-generated method stub
	    		return false;
	    	}
	    };
	    modelo.addColumn("ID");
	    modelo.addColumn("CODIGO_BARRA");
	    modelo.addColumn("CANTIDAD");
	    modelo.addColumn("DESCRIPCION");
	    modelo.addColumn("PRECIO UNITARIO");
	    modelo.addColumn("COSTO PRODUCTO");
	    modelo.addColumn("CATEGORIA");
	    tablaProductos.setModel(modelo);
	    /*-----------------------------tamaño de columnas*------------------------------*/
	    establecerTamanioJTable();
	    /*----------------------------fin de tamaño para columnas----------------------*/
	    String sql="";
	    if(valor.equals(""))
	    {
	        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
	    	sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE fk_id_state=1";
	    }
	    else{
	    	sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE categoria like '%"+valor+"%' AND fk_id_state=1 ";
	        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%'";
	    }
	 
	    Object []datos = new Object [7];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	            	
	            	datos[0]=rs.getInt(1);
	                datos[1]=rs.getDouble(2);
	                
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getDouble(5);
	                datos[5]=rs.getDouble(6);
	                datos[6]=rs.getString(7);
	                if(datos[6].equals("unidades")) {
	                	datos[2]=rs.getDouble(3);
	                }else {
	                	datos[2]=rs.getInt(3);
	                }
	                
	                modelo.addRow(datos);
	            }
	            tablaProductos.setModel(modelo);
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }finally {
	        	
				
				
			}
	    
	 }
  
	  
	
	
	public void mostrarProductoCodigoDeBarra(String valorBuscador,JTable tablaProductos) {
		DefaultTableModel modelo= new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	    modelo.addColumn("ID");
	    modelo.addColumn("CODIGO_BARRA");
	    modelo.addColumn("CANTIDAD");
	    modelo.addColumn("DESCRIPCION");
	    modelo.addColumn("PRECIO UNITARIO");
	    modelo.addColumn("COSTO PRODUCTO");
	    modelo.addColumn("CATEGORIA");
	    tablaProductos.setModel(modelo);
	    /*-----------------------------tamaño de columnas*------------------------------*/
	    establecerTamanioJTable();
	    /*----------------------------fin de tamaño para columnas----------------------*/
	    String sql="";
	    if(valorBuscador.equals(""))
	    {
	        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
	    	
	    }
	    else{
	    	//sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE categoria like '%"+valor+"%' AND fk_id_state=1 ";
	        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%'";
	    	sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA from productos where CODIGO_BARRA='"+valorBuscador+"' AND fk_id_state=1";
	    }
	 
	    Object []datos = new Object [7];
	        try {
	            Statement st = cn.createStatement();
	            ResultSet rs = st.executeQuery(sql);
	            while(rs.next()){
	            	
	            	datos[0]=rs.getInt(1);
	                datos[1]=rs.getDouble(2);
	                
	                datos[3]=rs.getString(4);
	                datos[4]=rs.getDouble(5);
	                datos[5]=rs.getDouble(6);
	                datos[6]=rs.getString(7);
	                if(datos[6].equals("unidades")) {
	                	datos[2]=rs.getDouble(3);
	                }else {
	                	datos[2]=rs.getInt(3);
	                }
	                
	                modelo.addRow(datos);
	            }
	            tablaProductos.setModel(modelo);
	            /*tamanio de las celdas*/
	            establecerTamanioJTable();
	            /*fin del tamanio de las celdas*/
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }finally {
				
			}
	        

	}
	
	
	  public void mostrarDatosTablaTcompras(String valor) throws SQLException{
		    DefaultTableModel modelo= new DefaultTableModel() {
		    	@Override
		    	public boolean isCellEditable(int row, int column) {
		    		// TODO Auto-generated method stub
		    		return false;
		    	}
		    };
		    modelo.addColumn("ID");
		    modelo.addColumn("CANTIDAD");
		    modelo.addColumn("DESCRIPCION");
		    modelo.addColumn("PRECIO UNITARIO");
		    modelo.addColumn("SUB_TOTAL");
		    modelo.addColumn("DESCUENTO");
		   
		    PuntoDeVenta.table.setModel(modelo);
		    /*-----------------------------tamaño de columnas*------------------------------*/
		    TableColumn columnaId=PuntoDeVenta.table.getColumn("ID");
			columnaId.setMinWidth(60);
			columnaId.setPreferredWidth(60);
			columnaId.setMaxWidth(65);
			
		    TableColumn columnaCantidad=PuntoDeVenta.table.getColumn("CANTIDAD");
			columnaCantidad.setMinWidth(100);
			columnaCantidad.setPreferredWidth(100);
			columnaCantidad.setMaxWidth(100);
			
		    TableColumn columnaPrecioUnitario=PuntoDeVenta.table.getColumn("PRECIO UNITARIO");
			columnaPrecioUnitario.setMinWidth(200);
			columnaPrecioUnitario.setPreferredWidth(200);
			columnaPrecioUnitario.setMaxWidth(200);
			
		    TableColumn columnaSubTotal=PuntoDeVenta.table.getColumn("SUB_TOTAL");
			columnaSubTotal.setMinWidth(200);
			columnaSubTotal.setPreferredWidth(200);
			columnaSubTotal.setMaxWidth(200);
			
		    TableColumn columnaDescuento=PuntoDeVenta.table.getColumn("DESCUENTO");
			columnaDescuento.setMinWidth(80);
			columnaDescuento.setPreferredWidth(80);
			columnaDescuento.setMaxWidth(85);
			
			
		    /*----------------------------fin de tamaño para columnas----------------------*/
		    String sql="";
		    if(valor.equals(""))
		    {
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
		    	sql="select * from tcompras";
		    }
		    else{
		    	//sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE descripcion like '%"+valor+"%' AND fk_id_state=1 ";
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%'";
		    }
		 
		    Object []datos = new Object [6];
		        try {
		            Statement st = cn.createStatement();
		            ResultSet rs = st.executeQuery(sql);
		            while(rs.next()){
		            	
		            	datos[0]=rs.getInt(1);
		                datos[1]=rs.getDouble(2);
		                datos[2]=rs.getString(3);
		                datos[3]=rs.getDouble(4);
		                datos[4]=rs.getDouble(5);
		                datos[5]=rs.getDouble(6);
		                
		                
		                modelo.addRow(datos);
		            }
		            PuntoDeVenta.table.setModel(modelo);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }finally {
		        	
					
					
				}
		    
		    }
	  
	  
	  
	  public void datosTablaTcompras(String valor,JTable table,String nameTablaTcompras) throws SQLException{
		    DefaultTableModel modelo= new DefaultTableModel() {
		    	@Override
		    	public boolean isCellEditable(int row, int column) {
		    		// TODO Auto-generated method stub
		    		return false;
		    	}
		    };
		    modelo.addColumn("ID");
		    modelo.addColumn("CANTIDAD");
		    modelo.addColumn("DESCRIPCION");
		    modelo.addColumn("PRECIO UNITARIO");
		    modelo.addColumn("SUB_TOTAL");
		    modelo.addColumn("DESCUENTO");
		   
		    table.setModel(modelo);
		    /*-----------------------------tamaño de columnas*------------------------------*/
		    TableColumn columnaId=table.getColumn("ID");
			columnaId.setMinWidth(60);
			columnaId.setPreferredWidth(60);
			columnaId.setMaxWidth(65);
			
		    TableColumn columnaCantidad=table.getColumn("CANTIDAD");
			columnaCantidad.setMinWidth(100);
			columnaCantidad.setPreferredWidth(100);
			columnaCantidad.setMaxWidth(100);
			
		    TableColumn columnaPrecioUnitario=table.getColumn("PRECIO UNITARIO");
			columnaPrecioUnitario.setMinWidth(200);
			columnaPrecioUnitario.setPreferredWidth(200);
			columnaPrecioUnitario.setMaxWidth(200);
			
		    TableColumn columnaSubTotal=table.getColumn("SUB_TOTAL");
			columnaSubTotal.setMinWidth(200);
			columnaSubTotal.setPreferredWidth(200);
			columnaSubTotal.setMaxWidth(200);
			
		    TableColumn columnaDescuento=table.getColumn("DESCUENTO");
			columnaDescuento.setMinWidth(80);
			columnaDescuento.setPreferredWidth(80);
			columnaDescuento.setMaxWidth(85);
			
			
		    /*----------------------------fin de tamaño para columnas----------------------*/
		    String sql="";
		    if(valor.equals(""))
		    {
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
		    	sql="select * from "+nameTablaTcompras+"";
		    }
		    else{
		    	//sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE descripcion like '%"+valor+"%' AND fk_id_state=1 ";
		        //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%'";
		    }
		 
		    Object []datos = new Object [6];
		        try {
		            Statement st = cn.createStatement();
		            ResultSet rs = st.executeQuery(sql);
		            while(rs.next()){
		            	
		            	datos[0]=rs.getInt(1);
		                datos[1]=rs.getDouble(2);
		                datos[2]=rs.getString(3);
		                datos[3]=rs.getDouble(4);
		                datos[4]=rs.getDouble(5);
		                datos[5]=rs.getDouble(6);
		                
		                
		                modelo.addRow(datos);
		            }
		            table.setModel(modelo);
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }finally {
		        	
					
					
				}
		    
		    }
	
	/*--------------------------------------------------------------------------------*/
	
	DefaultTableModel tablemodel;
	
    public ConexionTableModel(String query) throws SQLException {
		// TODO Auto-generated constructor stub
		tablemodel=new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		tablemodel= EjecutaQuery(query);

	}

	public DefaultTableModel getTablemodel() {
		return tablemodel;
		
	}

	public void setTablemodel(DefaultTableModel tablemodel) {
		this.tablemodel = tablemodel;
	}
		
	public DefaultTableModel EjecutaQuery(String query) throws
	SQLException
	{
		DefaultTableModel dftable=new DefaultTableModel();
		
		Connection conn = null;
		Statement stmnt= null;
		ResultSet rs= null;
	
		try{
     	conn=(Connection) DriverManager.getConnection(Ruta.URL,Ruta.Usuario,Ruta.Contrasenia);
		stmnt=conn.createStatement();
		rs=stmnt.executeQuery(query);
		if(rs!=null)
		{
			int columnas=rs.getMetaData().getColumnCount();
			//System.out.println("NO COLUMNAS----- "+columnas);
			for (int i = 0; i < columnas; i++) {
			dftable.addColumn(rs.getMetaData().getColumnLabel(i+1));
			//System.out.println("NOMBRE COLUMNA-------"+rs.getMetaData().getColumnLabel(i+1));
			}
			
			Object [] fila =new Object[columnas];
			while(rs.next())
			{
				for (int i = 0; i < columnas; i++) {
					//System.out.println("-------"+ rs.getMetaData());
					//rs.getMetaData().getColumnName(i);
					//System.out.println("-------"+rs.getMetaData().getColumnName(i));
					fila[i]=rs.getObject(i+1);
					
				}
				dftable.addRow(fila);
			}
			return dftable;
			}
			
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(rs!=null)
				rs.close();
			if(stmnt!=null)
				stmnt.close();
			if(conn!=null)
			
			conn.close();
		}
	return null;
	
 }
	
 public void establecerTamanioJTable() {
	    TableColumn columnaId=PuntoDeVenta.JTResultado1.getColumn("ID");
		columnaId.setMinWidth(60);
		columnaId.setPreferredWidth(60);
		columnaId.setMaxWidth(65);
		
		TableColumn columnaCodigoBarra=PuntoDeVenta.JTResultado1.getColumn("CODIGO_BARRA");
		columnaCodigoBarra.setMinWidth(150);
		columnaCodigoBarra.setPreferredWidth(150);
		columnaCodigoBarra.setMaxWidth(150);
		
		TableColumn columnaCantidad=PuntoDeVenta.JTResultado1.getColumn("CANTIDAD");
		columnaCantidad.setMinWidth(60);
		columnaCantidad.setPreferredWidth(60);
		columnaCantidad.setMaxWidth(65);
		
		TableColumn columnaDescripcion=PuntoDeVenta.JTResultado1.getColumn("DESCRIPCION");
		columnaDescripcion.setMinWidth(600);
		columnaDescripcion.setPreferredWidth(600);
		columnaDescripcion.setMaxWidth(605);
		
		TableColumn columnaPrecioUnitario=PuntoDeVenta.JTResultado1.getColumn("PRECIO UNITARIO");
		columnaPrecioUnitario.setMinWidth(120);
		columnaPrecioUnitario.setPreferredWidth(120);
		columnaPrecioUnitario.setMaxWidth(125);
		
		TableColumn columnaCostoProducto=PuntoDeVenta.JTResultado1.getColumn("COSTO PRODUCTO");
		columnaCostoProducto.setMinWidth(120);
		columnaCostoProducto.setPreferredWidth(120);
		columnaCostoProducto.setMaxWidth(125);
		
		TableColumn columnaCategoria=PuntoDeVenta.JTResultado1.getColumn("CATEGORIA");
		columnaCategoria.setMinWidth(140);
		columnaCategoria.setPreferredWidth(140);
		columnaCategoria.setMaxWidth(145);
		
 }
	
//select CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO from productos	
}
	
	

