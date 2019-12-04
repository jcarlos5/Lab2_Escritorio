/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author JCarlos
 */
public class clsProveedor {
    
   //Crear instancia de la clase clsJDBC
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs;
    Connection con;
    Statement sent;
    
    
    public void registrar(String cod, String nom, String ape,String direccion, Boolean estado,String codP,String precio, String fecha, String cantidad) throws Exception{
       try {
         //registar proveedor
           objConectar.conectar();
           con = objConectar.getConnection();
           CallableStatement sentencia = con.prepareCall("INSERT INTO proveedor VALUES(?,?,?,?,?)");
           sentencia.setInt(1,Integer.parseInt(cod));
           sentencia.setString(2, nom);
           sentencia.setString(3, ape);// campo opcional 
           sentencia.setString(4, direccion); 
           sentencia.setBoolean(5, estado);
           sentencia.executeUpdate(); 
           
           
           CallableStatement sente = con.prepareCall("INSERT INTO almacen VALUES(?,?,?,?,?)");
           sente.setInt(1,Integer.parseInt(cod));
           sente.setInt(2,Integer.parseInt(codP));
           sente.setString(3, fecha);// campo opcional 
           sente.setInt(4, Integer.parseInt(cantidad));
           sente.setFloat(5,Float.parseFloat(precio));
           sente.executeUpdate(); 
           
           CallableStatement senten = con.prepareCall("UPDATE producto SET stock=stock+? ");
           senten.setInt(1,Integer.parseInt(cantidad));
           senten.executeUpdate();
          
          con.commit();
          JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al registrar Proveedor");
        }finally{
            objConectar.desconectar();
        } 
        
    }
    
    
    
    
    
    
}
