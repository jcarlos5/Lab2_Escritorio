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
    
    
    public void registrarProveedor(String cod, String nom, String ruc,String direccion, Boolean estado) throws Exception{
       try {
         //registar proveedor
           objConectar.conectar();
           con = objConectar.getConnection();
           CallableStatement sentencia = con.prepareCall("INSERT INTO proveedor VALUES(?,?,?,?,?)");
           sentencia.setInt(1,Integer.parseInt(cod));
           sentencia.setString(2, nom);
           sentencia.setString(3, ruc);// campo opcional 
           sentencia.setString(4, direccion); 
           sentencia.setBoolean(5, estado);
           sentencia.executeUpdate(); 

          JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
        } catch (Exception e) {
            throw new Exception("Error al registrar Proveedor");
        }finally{
            objConectar.desconectar();
        } 
        
    }
    
    public void modificarProveedor(String cod, String nom, String ruc,String direccion, Boolean estado) throws Exception{
       try {
         //registar proveedor
           objConectar.conectar();
           con = objConectar.getConnection();
           CallableStatement sentencia = con.prepareCall("UPDATE proveedor set nombre=?, ruc=?,direccion=?,estado=? WHERE codProveedor=?)");
           sentencia.setInt(1,Integer.parseInt(cod));
           sentencia.setString(2, nom);
           sentencia.setString(3, ruc);// campo opcional 
           sentencia.setString(4, direccion); 
           sentencia.setBoolean(5, estado);
           sentencia.executeUpdate(); 

          JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
        } catch (Exception e) {
            throw new Exception("Error al registrar Proveedor");
        }finally{
            objConectar.desconectar();
        } 
        
    }


    public Integer obtenerCodigoTipoProveedor(String nom) throws Exception{
        strSQL = "select codProveedor from proveedor where nombre='" + nom + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if (rs.next()) return rs.getInt("codproveedor");
        } catch (Exception e) {
            throw new Exception("Error al obtener el Codigo del Proveedor");
        }
        return 0;
    }
    
    public ResultSet listarProveedores() throws Exception{
        strSQL = "select * from proveedor WHERE estado=true" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Proveedores");
        }
    }  
    
    public Integer generarCodigoProveedor() throws Exception{
        strSQL = "SELECT COALESCE(max(codProveedor),0)+1 AS codigo FROM proveedor" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código del Proveedor");
        }
        return 0;
    }
    
    public ResultSet buscarProveedor(Integer cod) throws Exception{
       try {
         //registar proveedor
           objConectar.conectar();
           con = objConectar.getConnection();
           CallableStatement sentencia = con.prepareCall("SELECT * FROM proveedor WHERE codProveedor=?)");
           sentencia.setInt(1,cod);
           ResultSet rs = sentencia.executeQuery();
           return rs;
          //JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
        } catch (Exception e) {
            throw new Exception("Error al registrar Proveedor");
        }finally{
            objConectar.desconectar();
        } 
    }
    public void darbajaProveedor(Integer cod) throws Exception {
        try {
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            sent=con.createStatement();
            strSQL="UPDATE proveedor set estado=false WHERE codProveedor="+cod;
            sent.executeUpdate(strSQL);
            strSQL="UPDATE producto set vigencia=false WHERE codProveedor="+cod;
            sent.executeUpdate(strSQL);
            JOptionPane.showMessageDialog(null, "Eliminado Correctamente DB");   
            con.commit();
            
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar al Proveedor");
        }finally{
            objConectar.desconectar();
        } 
    }
    
 public void eliminarProveedor(Integer cod) throws Exception {
        Integer cantidad=0;
        try {
            strSQL="SELECT COUNT(*) AS cantidad FROM proveedor INNER JOIN producto on producto.codProveedor=proveedor.codProveedor WHERE proveedor.codProveedor="+cod+"";
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            
            while(rs.next()){
                cantidad=rs.getInt("cantidad");
            }
 
            if(cantidad>0){                
                String strSQL1;
                strSQL1="UPDATE proveedor set estado=false WHERE codProveedor="+cod;
                sent.executeUpdate(strSQL1);
                strSQL1="UPDATE producto set vigencia=false WHERE codProveedor="+cod;
                sent.executeUpdate(strSQL1);
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente DB");
            }else{
                String strSQL1="DELETE FROM proveedor WHERE codProveedor="+cod;
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
                sent.executeUpdate(strSQL1);
            }    
            con.commit();
            
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar al Proveedor");
        }finally{
            objConectar.desconectar();
        } 
    }
    
     
}
