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
public class clsCategoria {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con;
    Statement sent;
    
    public Integer generarCodigoCategoria() throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT COALESCE(max(codcategoria),0)+1 AS codigo FROM categoria;");
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
    
    public void registrar(Integer cod, String nom, String descrip, Boolean vig) throws Exception{
        //strSQL="INSERT INTO categoria VALUES(" + cod + ",'" + nom + "','" + descrip + "'," + vig + ");";
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("INSERT INTO categoria VALUES(?,?,?,?);");
            sentencia.setInt(1,cod);
            sentencia.setString(2,nom);
            sentencia.setString(3,descrip);
            sentencia.setBoolean(4, vig);
            rs = sentencia.executeQuery();
            //objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la categoria");
        }
    }
    
    public ResultSet buscarCategoria(Integer cod) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM categoria WHERE codcategoria=?");
            sentencia.setInt(1, cod);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    /*
    public void eliminarCategoria(Integer cod) throws Exception {
        strSQL="DELETE FROM categoria WHERE codcategoria=" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar la categoria");
        }
    }
    */
    
    public void eliminarCategoria(Integer cod) throws Exception {
        try {
            boolean logica = false;
            CallableStatement sentencia = con.prepareCall("SELECT * FROM producto WHERE codcategoria= ?;");
            sentencia.setInt(1, cod);
            rs = sentencia.executeQuery();
            //strSQL="SELECT * FROM producto WHERE codcategoria=" + cod + ";";
            //rs = objConectar.consultarBD(strSQL);
            if(rs.next()){
                logica = true;
            }
            if(logica){
                objConectar.conectar();
                con = objConectar.getConnection();
                con.setAutoCommit(false);
                
                sentencia = con.prepareCall("UPDATE producto SET vigencia = false WHERE codcategoria= ?;");
                sentencia.setInt(1, cod);
                sentencia.executeUpdate();
                //Statement sent = objConectar.getConnection().createStatement();
                //strSQL="UPDATE producto SET vigencia = false WHERE codcategoria=" + cod + ";";
                //sent.executeUpdate(strSQL);
                
                sentencia = con.prepareCall("UPDATE categoria SET vigencia = false WHERE codcategoria= ?;");
                sentencia.setInt(1, cod);
                sentencia.executeUpdate();
                //sent = objConectar.getConnection().createStatement();
                //strSQL="UPDATE categoria SET vigencia = false WHERE codcategoria=" + cod + ";";
                //sent.executeUpdate(strSQL);
                
                con.commit();
            }else{
                sentencia = con.prepareCall("DELETE FROM categoria WHERE codcategoria= ?;");
                sentencia.setInt(1, cod);
                sentencia.executeUpdate();
                //strSQL="DELETE FROM categoria WHERE codcategoria=" + cod + ";";
                //objConectar.ejecutarBD(strSQL);
            }
        } catch (Exception e) {
            con.rollback();
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }

    public ResultSet listarCategorias() throws Exception{
         try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT * FROM categoria;");
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    //TERCERA TRANSACCIÓN DEL ITEM A
    public void modificarCategoria(Integer cod, String nombre, String descrip, boolean vigencia) throws Exception {
        try {
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            
            CallableStatement sentencia = con.prepareCall("UPDATE categoria SET nomcategoria = ?, vigencia = ?, descripcion = ? WHERE codcategoria = ?;");
            sentencia.setString(1, nombre);
            sentencia.setBoolean(2, vigencia);
            sentencia.setString(3, descrip);
            sentencia.setInt(4, cod);
            sentencia.executeUpdate();
            //strSQL="UPDATE categoria SET nomcategoria = '" + nombre + "', vigencia = " + vigencia + ", descripcion = '" + descrip + "' WHERE codcategoria =" + cod + ";";
            //sent.executeUpdate(strSQL);
            
            sentencia = con.prepareCall("UPDATE producto SET vigencia = ? WHERE codcategoria= ?;");
            sentencia.setBoolean(1, vigencia);
            sentencia.setInt(2, cod);
            sentencia.executeUpdate();
            //strSQL="UPDATE producto SET vigencia = " + vigencia + " WHERE codcategoria=" + cod + ";";
            //sent.executeUpdate(strSQL);
            
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al modificar la categoria");
        }
    }
    
    //CUARTA TRANSACCIÓN DEL ITEM A
    public void darDeBajaCategoria(Integer cod) throws Exception {
        try {
            objConectar.conectar();
            con=objConectar.getConnection();
            con.setAutoCommit(false);
            
            CallableStatement sentencia = con.prepareCall("UPDATE categoria SET vigencia = false WHERE codcategoria = ?;");
            sentencia.setInt(1, cod);
            sentencia.executeUpdate();
            //strSQL="UPDATE categoria SET vigencia = false WHERE codcategoria =" + cod + ";";
            //sent.executeUpdate(strSQL);
            
            sentencia = con.prepareCall("UPDATE producto SET vigencia = false WHERE codcategoria= ?;");
            sentencia.setInt(1, cod);
            sentencia.executeUpdate();
            //strSQL="UPDATE producto SET vigencia = false WHERE codcategoria=" + cod + ";";
            //sent.executeUpdate(strSQL);
            
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al modificar la categoria");
        }
    }
    
    public int getCodigo(String nombre) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT codcategoria FROM categoria WHERE nomcategoria=?");
            sentencia.setString(1, nombre);
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                return resultado.getInt("codcategoria");
            }
        } catch (Exception e) {
            throw new Exception("Error ");
        }finally{
            objConectar.desconectar();
        }
        return 0;
    }
    
    public String getNombre(int codigo) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getConnection();
            CallableStatement sentencia = con.prepareCall("SELECT nomcategoria FROM categoria WHERE codcategoria=?");
            sentencia.setInt(1, codigo);
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                return resultado.getString("codcategoria");
            }
        } catch (Exception e) {
            throw new Exception("Error ");
        }finally{
            objConectar.desconectar();
        }
        return null;
    }
}
