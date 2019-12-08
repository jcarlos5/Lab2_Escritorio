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
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
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

    public Integer obtenerCodigoProveedor(String nom) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("select codProveedor from proveedor where nombre=?");
            sentencia.setString(1, nom);
            rs=sentencia.executeQuery();
            if (rs.next()) return rs.getInt("codproveedor");
        } catch (Exception e) {
            throw new Exception("Error al obtener el Codigo del Proveedor");
        }
        return 0;
    }
    
    public String obtenerProveedor(int cod) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT proveedor.nombre as razonsocial FROM proveedor INNER JOIN producto ON producto.codproveedor=proveedor.codproveedor WHERE proveedor.codproveedor= ?");
            sentencia.setInt(1, cod);
            rs=sentencia.executeQuery();
            if (rs.next()) return rs.getString("razonsocial");
        } catch (Exception e) {
            throw new Exception("Error al obtener el nombre del Proveedor");
        }
        return null;
    }    
    
    
    public ResultSet listarProveedores() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM proveedor WHERE estado = true");
            rs=sentencia.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar Proveedores");
        }
    }   
    
    public Integer generarCodigoProveedor() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT COALESCE(max(codProveedor),0)+1 AS codigo FROM proveedor");
            rs=sentencia.executeQuery();
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
            CallableStatement sentencia = con.prepareCall("UPDATE proveedor set estado=false  WHERE codProveedor=?");
            sentencia.setInt(1, cod);
            sentencia.executeUpdate();
            
            sentencia = con.prepareCall("UPDATE producto set vigencia=false WHERE codproducto=(SELECT codproducto FROM almacen WHERE codProveedor= ?);");
            sentencia.setInt(1, cod);
            sentencia.executeUpdate();
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
            strSQL="";
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            CallableStatement sentencia = con.prepareCall("SELECT COUNT(*) AS cantidad FROM proveedor INNER JOIN almacen ON almacen.codproveedor=proveedor.codproveedor INNER JOIN producto on producto.codproducto=almacen.codproducto WHERE proveedor.codProveedor= ?");
            sentencia.setInt(1, cod);
            rs=sentencia.executeQuery();
            
            while(rs.next()){
                cantidad=rs.getInt("cantidad");
            }
 
            if(cantidad>0){
                sentencia = con.prepareCall("UPDATE proveedor set estado=false WHERE codProveedor= ?");
                sentencia.setInt(1, cod);
                sentencia.executeUpdate();
                
                sentencia = con.prepareCall("UPDATE producto set vigencia=false WHERE codproducto=(SELECT codproducto FROM almacen WHERE codProveedor= ?)");
                sentencia.setInt(1, cod);
                sentencia.executeUpdate();

            }else{

                sentencia = con.prepareCall("DELETE FROM proveedor WHERE codProveedor= ?");
                sentencia.setInt(1, cod);
                sentencia.executeUpdate();
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
            }    
            con.commit();
            
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar al Proveedor");
        }finally{
            objConectar.desconectar();
        } 
    }
 
    public ResultSet buscarProveedorRUC(String ruc) throws Exception{
        strSQL = "SELECT * FROM proveedor WHERE ruc = '" + ruc + "';";
        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar proveedor");
        }
    }
    
     public Integer buscarXruc_cod(String ruc) throws Exception{
        strSQL = "SELECT * FROM proveedor WHERE ruc = '" + ruc + "';";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codproveedor");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar el Proveedor");
        }
        return 0;
    }
     
}
