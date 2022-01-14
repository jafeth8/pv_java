package Punto_de_venta;

public class Ruta {
	public static String imagen="Program Files (x86)";
   public static String Usuario="root";
   public static String Contrasenia="";
   public static String URL="jdbc:mysql://localhost/ferreteria_aranza";
   public static String database = "ferreteria_aranza";
   public static String query = "SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE fk_id_state=1";
   public static String nametablaTcompras="tcompras";
   public static String nametablaTcomprasAdditions="tcompras_additions";
}

//public static String imagen="Program Files (x86)";
//public static String imagen="Program Files";