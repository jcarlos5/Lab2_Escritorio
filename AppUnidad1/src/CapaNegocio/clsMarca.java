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
public class clsMarca {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con=null;
    Statement sent;
    
    public Integer generarCodigoMarca() throws Exception{
        strSQL = "SELECT COALESCE(max(codMarca),0)+1 AS codigo FROM marca;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de marca");
        }
        return 0;
    }
    
    public void registrar(Integer cod, String nom, Boolean vig) throws Exception{
        strSQL="INSERT INTO MARCA VALUES(" + cod + ",'" + nom + "'," + vig + ");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la marca");
        }
    }
    
    public ResultSet buscarMarca(Integer cod) throws Exception{
        strSQL = "SELECT * FROM marca WHERE codMarca=" + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
    public void eliminarMarca(Integer cod) throws Exception {
        Integer cant=0;
        try {
            strSQL="select count(*) as total from PRODUCTO where codmarca=" + cod;
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            while(rs.next()){
                cant=rs.getInt("total");
            }
            if(cant>0){
                strSQL="update PRODUCTO set vigencia=false where codmarca=" + cod;
                sent.executeUpdate(strSQL);
                strSQL="update MARCA set vigencia=false where codmarca=" + cod;
                sent.executeUpdate(strSQL);
            }else{
                strSQL="DELETE FROM marca WHERE codmarca=" + cod + ";";
                sent.executeUpdate(strSQL);
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar la marca");
        }finally{
            objConectar.desconectar();
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
    
    public void modificarMarca(Integer cod, String nombre, boolean vigencia) throws Exception {
        try {
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            strSQL="UPDATE marca SET nommarca = '" + nombre + "', vigencia = " + vigencia + " WHERE codmarca =" + cod + ";";
            sent.executeUpdate(strSQL);
            strSQL="update PRODUCTO set vigencia=" + vigencia + " where codmarca=" + cod;
            sent.executeUpdate(strSQL);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al modificar la marca");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void darDeBajaMarca(Integer cod) throws Exception {
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            strSQL="UPDATE marca SET vigencia = false WHERE codmarca =" + cod + ";";
            sent.executeUpdate(strSQL);
            strSQL="update PRODUCTO set vigencia=false where codmarca=" + cod;
            sent.executeUpdate(strSQL);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al dar de baja a la categoría");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public int getCodigo(String nombre) throws Exception{
        strSQL = "SELECT codmarca FROM marca WHERE nommarca='"+nombre+"';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("codmarca");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar marcas");
        }
        return 0;
    }
    
    public String getNombre(int codigo) throws Exception{
        strSQL = "SELECT nommarca FROM marca WHERE codmarca='"+codigo+"';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getString("nommarca");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar marcas");
        }
        return null;
    }
}
