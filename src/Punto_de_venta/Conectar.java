package Punto_de_venta;


import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conectar {
    Connection conectar=null;
    public Connection conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar=DriverManager.getConnection("jdbc:mysql://localhost/general_pv","root","");
            //conectar=DriverManager.getConnection("jdbc:mysql://localhost/bd_tenmporal","root","");
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return conectar;
    }
}