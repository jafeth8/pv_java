package Punto_de_venta;

import java.util.concurrent.CompletableFuture;

import javax.swing.JOptionPane;

public class pruebas {

	public static void main(String[] args) {
		//MostrarClientes i=new MostrarClientes();
		//i.setVisible(true);
		SqlOperaciones instancia = new SqlOperaciones();
		
		JOptionPane.showMessageDialog(null,"la sumatoria es: "+instancia.obtenerSumatoriaSubtotalTablaTcompras(Ruta.nametablaTcomprasAdditions));
//		int resultado = instancia.obtenerCantidadTablaProducto(1);
//		System.out.println(resultado);
//		instancia.actualizarCantidadProductos(1, 3, resultado);
		int registros =instancia.cantidadRegistros(28);
/*		instancia.obtenerDatosDetalleApartados(28);
		String matriz[][];
		matriz=instancia.obtenerDatosDetalleApartados(28);
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.print(matriz[i][j]+" ");
				
			}
			System.out.println();
		}*/
		//int tipoCliente=instancia.obtenerIdTipoClienteTablaClientes(5);
		//System.out.println("el tipo de cliente es: "+tipoCliente);
		
		//instancia.actualizarIdClienteTablaVentas(25,5);
		
		/*int porcentaje =instancia.obtenerPorcentajeComision();
		double p=porcentaje;
		System.out.println("el porcentaje para la comision es: "+p/100);
		double totalVenta=instancia.obtenerTotalVentaTablaVentas(32);
		System.out.println("el total de la venta es :"+totalVenta);
		double resultado=(totalVenta)*p/100;
		System.out.println("la comision es de: "+resultado);
		instancia.actualizarComisionTablaVentas(32,resultado);*/
		
		//boolean var= instancia.busquedaPorCodigoBarra("msi");
		int var;
		var=JOptionPane.showConfirmDialog(null,"vendera por unidades?","Mensaje",JOptionPane.YES_NO_OPTION);
		
		System.out.println(var);
		
				
		
	}
}
