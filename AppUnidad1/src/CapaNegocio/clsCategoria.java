/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsJDBC;
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
    Connection con=null;
    Statement sent;
    
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
    
    public void eliminarCategoria(Integer cod) throws Exception {
        Integer cant=0;
        try {
            strSQL="select count(*) as total from PRODUCTO where codCategoria=" + cod;
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            while(rs.next()){
                cant=rs.getInt("total");
            }
            if(cant>0){
                strSQL="update PRODUCTO set vigencia=false where codCategoria=" + cod;
                sent.executeUpdate(strSQL);
                strSQL="update CATEGORIA set vigencia=false where codCategoria=" + cod;
                sent.executeUpdate(strSQL);
            }else{
                strSQL="delete from CATEGORIA where codCategoria=" + cod;
                sent.executeUpdate(strSQL);
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar la categoría");
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
        try {
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            strSQL="update CATEGORIA set nomCategoria='" + nombre + "',descripcion='" + descrip + "', vigencia=" + vigencia + " where codCategoria=" + cod;
            objConectar.ejecutarBD(strSQL);
            strSQL="update PRODUCTO set vigencia=" + vigencia + " where codCategoria=" + cod;
            objConectar.ejecutarBD(strSQL);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al modificar la categoría");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void darDeBajaCategoria(Integer cod) throws Exception {
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            strSQL="update CATEGORIA set vigencia=false where codCategoria=" + cod;
            objConectar.ejecutarBD(strSQL);
            strSQL="update PRODUCTO set vigencia=false where codCategoria=" + cod;
            objConectar.ejecutarBD(strSQL);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al dar de baja a la categoría");
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
