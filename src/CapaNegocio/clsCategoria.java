/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
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
    String strSQL, strSQL1;
    ResultSet rs=null;
    
    public Integer generarCodigoCategoria() throws Exception{
        strSQL = "SELECT COALESCE(max(codcategoria),0)+1 AS codigo FROM categoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de categoria");
        }
        return 0;
    }
    
    public void registrar(Integer cod, String nom, String descrip, Boolean vig) throws Exception{
        strSQL="INSERT INTO categoria VALUES(" + cod + ",'" + nom + "','" + descrip + "'," + vig + ");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la categoria");
        }
    }
    
    public ResultSet buscarCategoria(Integer cod) throws Exception{
        strSQL = "SELECT * FROM categoria WHERE codcategoria=" + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoria");
        }
    }
    
    //public void eliminarCategoria(Integer cod) throws Exception {
        //strSQL="DELETE FROM categoria WHERE codcategoria=" + cod + ";";
        //try {
            //objConectar.ejecutarBD(strSQL);
        //} catch (Exception e) {
            //throw new Exception("Error al eliminar la categoria");
        //}
    //}
    
    public void eliminarCategoria(Integer cod) throws Exception {
        try {
            boolean logica = false;
            strSQL="SELECT * FROM producto WHERE codcategoria=" + cod + ";";
            rs = objConectar.consultarBD(strSQL);
            while(rs.next()){
                logica = true;
            }
            if(logica){
                objConectar.conectar();
                objConectar.getConnection().setAutoCommit(false);
                
                Statement sent = objConectar.getConnection().createStatement();
                strSQL="UPDATE producto SET vigencia = false WHERE codcategoria=" + cod + ";";
                sent.executeUpdate(strSQL);
                
                sent = objConectar.getConnection().createStatement();
                strSQL="UPDATE categoria SET vigencia = false WHERE codcategoria=" + cod + ";";
                sent.executeUpdate(strSQL);
                
                objConectar.getConnection().commit();
            }else{
                strSQL="DELETE FROM categoria WHERE codcategoria=" + cod + ";";
                objConectar.ejecutarBD(strSQL);
            }
        } catch (Exception e) {
            objConectar.getConnection().rollback();
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void darDeAltaCategoria(Integer cod) throws Exception {
        try {            
            objConectar.conectar();
            objConectar.getConnection().setAutoCommit(false);

            Statement sent = objConectar.getConnection().createStatement();
            strSQL="UPDATE producto SET vigencia = true WHERE codcategoria=" + cod + ";";
            sent.executeUpdate(strSQL);

            sent = objConectar.getConnection().createStatement();
            strSQL="UPDATE categoria SET vigencia = true WHERE codcategoria=" + cod + ";";
            sent.executeUpdate(strSQL);

            objConectar.getConnection().commit();
        } catch (Exception e) {
            objConectar.getConnection().rollback();
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }

    public ResultSet listarCategorias() throws Exception{
        strSQL = "SELECT * FROM categoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar categorias");
        }
    }
    
    public void modificarCategoria(Integer cod, String nombre, String descrip, boolean vigencia) throws Exception {
        strSQL="UPDATE categoria SET nomcategoria = '" + nombre + "', descripcion = '" + descrip + "' WHERE codcategoria =" + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
            if (vigencia){
                darDeAltaCategoria(cod);
            }else{
                darDeBajaCategoria(cod);
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar la categoria");
        }
    }
    
    public void darDeBajaCategoria(Integer cod) throws Exception {
        try {
            objConectar.conectar();
            objConectar.getConnection().setAutoCommit(false);

            Statement sent = objConectar.getConnection().createStatement();
            strSQL="UPDATE producto SET vigencia = false WHERE codcategoria=" + cod + ";";
            sent.executeUpdate(strSQL);

            sent = objConectar.getConnection().createStatement();
            strSQL="UPDATE categoria SET vigencia = false WHERE codcategoria=" + cod + ";";
            sent.executeUpdate(strSQL);

            objConectar.getConnection().commit();
        } catch (Exception e) {
            objConectar.getConnection().rollback();
            throw new Exception(e.getMessage());
        }finally{
            objConectar.desconectar();
        }
    }
    
    public int getCodigo(String nombre) throws Exception{
        strSQL = "SELECT codcategoria FROM categoria WHERE nomcategoria='"+nombre+"';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("codcategoria");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar codigo categoria");
        }
        return 0;
    }
    
    public String getNombre(int codigo) throws Exception{
        strSQL = "SELECT nomcategoria FROM categoria WHERE codcategoria='"+codigo+"';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getString("nomcategoria");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar categorias");
        }
        return null;
    }
}
