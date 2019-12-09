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

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */
public class clsMarca {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con;
    Statement sent;
    
    public Integer generarCodigoMarca() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT COALESCE(max(codMarca),0)+1 AS codigo FROM marca;");
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("codigo");
            } 
        } catch (Exception e) {
            throw new Exception("Error al extraer los datos del cliente");
        }
        return 0;        
    }
    
    public void registrar(Integer cod, String nom, Boolean vig) throws Exception{
        //strSQL="INSERT INTO MARCA VALUES(" + cod + ",'" + nom + "'," + vig + ");";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("INSERT INTO MARCA VALUES(?,?,?);");
            sentencia.setInt(1,cod);
            sentencia.setString(2,nom);
            sentencia.setBoolean(3,vig);
            sentencia.executeUpdate();
            //objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la marca");
        }
    }
    
    public ResultSet buscarMarca(Integer cod) throws Exception{
        //strSQL = "SELECT * FROM marca WHERE codMarca=" + cod + ";";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM marca WHERE codMarca=?;");
            sentencia.setInt(1,cod);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
    /*
    public void eliminarMarca(Integer cod) throws Exception {
        strSQL="DELETE FROM marca WHERE codMarca=" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar la marca");
        }
    }
    */
    
    //Eliminar con transacción
    public void eliminarMarca(Integer cod) throws Exception {
        boolean logica = false;
        objConectar.conectar();
        con = objConectar.getConnection();
        con.setAutoCommit(false);
        try {
            CallableStatement sentencia = con.prepareCall("SELECT * FROM producto WHERE codmarca=?;");
            sentencia.setInt(1,cod);
            rs = sentencia.executeQuery();
            //strSQL="SELECT * FROM producto WHERE codmarca=" + cod + ";";
            //rs = objConectar.consultarBD(strSQL);
            if(rs.next()){
                logica = true;
            }
            if(logica){
                CallableStatement sentencia2 = con.prepareCall("UPDATE producto SET vigencia = false WHERE codMarca=?;");
                sentencia2.setInt(1,cod);
                sentencia2.executeUpdate();
                //Statement sent = con.createStatement();
                //strSQL="UPDATE producto SET vigencia = false WHERE codMarca=" + cod + ";";
                //sent.executeUpdate(strSQL);
                
                CallableStatement sentencia3 = con.prepareCall("UPDATE marca SET vigencia = false WHERE codMarca=?;");
                sentencia3.setInt(1,cod);
                sentencia3.executeUpdate();
                //strSQL="UPDATE marca SET vigencia = false WHERE codMarca=" + cod + ";";
                //sent.executeUpdate(strSQL);
            }else{
                CallableStatement sentencia2 = con.prepareCall("DELETE FROM marca WHERE codMarca=?;");
                sentencia2.setInt(1,cod);
                sentencia2.executeUpdate();
                //strSQL="DELETE FROM marca WHERE codMarca=" + cod + ";";
                //objConectar.ejecutarBD(strSQL);
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception(e.getMessage());
        }
    }

    public ResultSet listarMarcas() throws Exception{
        strSQL = "SELECT * FROM marca;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
//    public void modificarMarca(Integer cod, String nombre, boolean vigencia) throws Exception {
//        strSQL="UPDATE marca SET nommarca = '" + nombre + "', vigencia = " + vigencia + " WHERE codmarca =" + cod + ";";
//        try {
//            objConectar.ejecutarBD(strSQL);
//        } catch (Exception e) {
//            throw new Exception("Error al modificar la marca");
//        }
//    }
    
    //SEXTA TRANSACCIÓN DEL ÍTEM A
    public void modificarMarca(Integer cod, String nombre, boolean vigencia) throws Exception {
        try {
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            
            CallableStatement sentencia = con.prepareCall("UPDATE marca SET nommarca = ?, vigencia = ? WHERE codmarca = ?;");
            sentencia.setString(1,nombre);
            sentencia.setBoolean(2,vigencia);
            sentencia.setInt(3,cod);
            sentencia.executeUpdate();
            //strSQL="UPDATE marca SET nommarca = '" + nombre + "', vigencia = " + vigencia + " WHERE codmarca =" + cod + ";";
            //sent.executeUpdate(strSQL);
            
            sentencia = con.prepareCall("UPDATE producto SET vigencia = ? WHERE codMarca= ?");
            sentencia.setBoolean(1,vigencia);
            sentencia.setInt(2,cod);
            sentencia.executeUpdate();
            //strSQL="UPDATE producto SET vigencia = " + vigencia + " WHERE codMarca=" + cod + ";";
            //sent.executeUpdate(strSQL);
            
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al modificar la marca");
        }
    }
    
    //SÉPTIMA TRANSACCIÓN DEL ÍTEM A
    public void darDeBajaMarca(Integer cod) throws Exception {
        //strSQL="UPDATE marca SET vigencia = false WHERE codmarca =" + cod + ";";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            con.setAutoCommit(false);
            
            CallableStatement sentencia = con.prepareCall("UPDATE marca SET vigencia = false WHERE codmarca = ?;");
            sentencia.setInt(1,cod);
            sentencia.executeUpdate();
            
            sentencia = con.prepareCall("UPDATE producto SET vigencia = false WHERE codMarca=?;");
            sentencia.setInt(1,cod);
            sentencia.executeUpdate();
            
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al modificar la marca");
        }
    }
    
    public int getCodigo(String nombre) throws Exception{
        //strSQL = "SELECT codmarca FROM marca WHERE nommarca='"+nombre+"';" ;
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT codmarca FROM marca WHERE nommarca=?;");
            sentencia.setString(1,nombre);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("codmarca");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar marcas");
        }
        return 0;
    }
    
    public String getNombre(int codigo) throws Exception{
        //strSQL = "SELECT nommarca FROM marca WHERE codmarca='"+codigo+"';" ;
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT nommarca FROM marca WHERE codmarca=?;");
            sentencia.setInt(1,codigo);
            rs = sentencia.executeQuery();
            //rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getString("nommarca");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar marcas");
        }
        return null;
    }
}
